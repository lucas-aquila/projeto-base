package br.org.pti.fpti_base.application.controller.proxy;

import static org.springframework.http.HttpHeaders.USER_AGENT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.pti.fpti_base.application.http.HTTPContextHolder;

@RestController
public class ProxyResource {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ProxyResource.class);

    /**
     * @param request
     * @param response
     */
    @RequestMapping("**/api/**")
    public void proxy(HttpServletRequest request, HttpServletResponse response) {

        try {
            execute(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param request a requisição
     * @return a URL tratada e extraída da requisição
     */
    private static String extractURL(HttpServletRequest request) {
        final String path = request.getRequestURI().substring(0, request.getRequestURI().indexOf("/api"));
        if (path.length() > 0) {
            final String url = HTTPContextHolder.getServerURL() /*+ request.getContextPath() */ + request.getRequestURI().replace(path, "") + ";jsessionid=" + request.getSession().getId() + "?" + request.getQueryString();

            LOGGER.info("Redirecionamento de URL: {}", url);

            return url;
        } else {
            throw new RuntimeException("Não foi possível extrair a URL da requisição");
        }
    }

    /**
     * @param request  requisição
     * @param response resposta
     */
    private static void execute(HttpServletRequest request, HttpServletResponse response) throws ProtocolException, IOException, MalformedURLException, KeyManagementException, NoSuchAlgorithmException {
        response.setCharacterEncoding("UTF-8");

        /**
         * Extrai e tratae a URL da requisição
         */
        final URL url = new URL(extractURL(request));

        /**
         * Extrai o JSON da requisição
         */
        final String body = readBody(request.getInputStream()).toString();

        /**
         * Abre a conexão
         */
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Content-type", request.getContentType() == null ? MediaType.APPLICATION_JSON_VALUE : request.getContentType());

        LOGGER.info("Tipo da requisição: {}", conn.getRequestProperty("Content-type"));
        conn.setRequestMethod(request.getMethod());

        // Disable TLS 1.0
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(new SSLSupport());
        }

        /**
         * Caso a requisição tenha body escreve-os na mensagem TODO só suporta JSON por enquanto
         */
        if (body != null && body.length() > 0) {
            conn.setDoOutput(true);

            // Suporta caracteres especiais
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write(body);
            writer.close();
            wr.flush();
            wr.close();
        }

        int responseCode = conn.getResponseCode();

        if (responseCode >= 200 && responseCode < 299) {

            if (conn.getContentType() != null) {
                if (conn.getContentType().contains("image")) {
                    response.setContentType("image/png");
                    extractFile(conn, response);
                    return;
                }

                if (conn.getContentType().contains("pdf")) {
                    response.setContentType("application/pdf");
                    extractFile(conn, response);
                    return;
                }
            }

            final StringBuilder responseBody = readBody(conn.getInputStream());
            final Writer wr = response.getWriter();
            wr.write(responseBody.toString());
            wr.flush();
            wr.close();

        } else if (responseCode >= 400) {

            final StringBuilder responseBody = readBody(conn.getErrorStream());

            response.setStatus(responseCode);
            final Writer wr = response.getWriter();
            wr.write(responseBody.toString());
            wr.flush();
            wr.close();
        }
    }

    /**
     * Estrai o arquivo do retorno da requisição
     *
     * @param conn conexão
     * @param resp resposta
     * @throws IOException exceção de IO não tratada
     */
    private static void extractFile(HttpURLConnection conn, HttpServletResponse resp) throws IOException {

        resp.setContentLength(conn.getInputStream().available());

        InputStream in = conn.getInputStream();
        OutputStream out = resp.getOutputStream();

        // Copy the contents of the file to the output stream
        byte[] buf = new byte[1024];
        int count;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }

        out.close();
        in.close();
    }


    /**
     * @param inputStream o inputSream da mensagem
     * @return retorna o JSON da mensagem
     */
    private static StringBuilder readBody(final InputStream inputStream) {
        try {
            final StringBuilder body = new StringBuilder();
            final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                body.append(inputLine);
            }
            in.close();

            return body;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível a leitura do corpo da mensagem");
        }
    }
}
