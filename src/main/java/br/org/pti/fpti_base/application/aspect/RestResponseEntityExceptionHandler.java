package br.org.pti.fpti_base.application.aspect;

import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     *
     */
    private static final Logger LOGGER = Logger.getLogger(RestResponseEntityExceptionHandler.class.getName());

    /**
     *
     */
    private MessageSource messageSource;

    /**
     * @param messageSource
     */
    @Autowired
    public RestResponseEntityExceptionHandler(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Trata exceções de Constraint geradas pelo PostgreSQL
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleException(final org.springframework.dao.DataIntegrityViolationException exception, final WebRequest request) {
        String message = this.messageSource.getMessage("repository.dataIntegrityViolation", null, LocaleContextHolder.getLocale());

        final Error error = new Error();

        if (exception.getCause() instanceof ConstraintViolationException) {
            final ConstraintViolationException cause = (ConstraintViolationException) exception.getCause();
            final PSQLException sqlException = (PSQLException) cause.getSQLException();

            final String detail = sqlException.getServerErrorMessage().getDetail();

            String key;
            //Verifica o código do erro gerado pelo PostgreSQL
            if (cause.getSQLState().equals("23503")) {//violação de exclusão quando chave primaria de registro é referenciado por outro
                key = detail.substring(detail.indexOf('"'), detail.indexOf('.'));
                message = this.messageSource.getMessage("repository.foreignKeyViolation", new String[]{snakeCaseToHumanReadable(key)}, LocaleContextHolder.getLocale());
            } else if (cause.getSQLState().equals("23505")) { //violação de unicidade
                key = detail.substring(detail.indexOf('(') + 1, detail.indexOf(')'));
                if (key.startsWith("lower(")) {
                    key = key.replace("lower(", "");
                    key = key.replace("::text", "");
                }
                message = this.messageSource.getMessage("repository.uniqueViolation", new String[]{snakeCaseToHumanReadable(key)}, LocaleContextHolder.getLocale());
            } else if (cause.getSQLState().equals("23502")) {//violação de nulidade
                LOGGER.info(detail); //TODO
                LOGGER.info("Not null violation.");
                throw exception;
            } else {
                message = this.messageSource.getMessage("repository.uniqueViolation", new String[]{cause.getSQLState()}, LocaleContextHolder.getLocale());
            }
        }

        error.setMessage(message);
        return handleExceptionInternal(new Exception(message), error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Trata exceções geradas pelo Hibernate antes de enviar para o banco
     *
     * @param exceptionwe
     * @param request
     * @return
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(final javax.validation.ConstraintViolationException exception, final WebRequest request) {
        final StringBuilder message = new StringBuilder();
        //for (ConstraintViolation<?> constraint : exception.getConstraintViolations()) {
        ConstraintViolation<?> constraint = exception.getConstraintViolations().iterator().next();
            final String annotationType = constraint.getConstraintDescriptor().getAnnotation().annotationType().getName();

            /**
             * Dependendo da versão do spring a validação pode estar no pacote 
             * "javax.validation.constraints.*" ou "org.hibernate.validator.constraints.*"
             */
            if (annotationType.equals("javax.validation.constraints.NotNull") 
            		|| annotationType.equals("javax.validation.constraints.NotEmpty")
            			|| annotationType.equals("org.hibernate.validator.constraints.NotEmpty")
            				|| annotationType.equals("org.hibernate.validator.constraints.NotBlank")
            						|| annotationType.equals("javax.validation.constraints.NotBlank")) {
            	
            	//converte camel case para legivel
				String readablePath = camelCaseToHumanReadable(constraint.getPropertyPath().toString());
                message.append("\nO campo " + readablePath + " deve ser informado.");
            } else {
                message.append("\n" + constraint.getMessage());
            }
            
        //}

        return handleExceptionInternal(new Exception(message.toString()), new Error(message.toString()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Object> handleException(final DuplicateKeyException exception, final WebRequest request) {
        final String message = this.messageSource.getMessage("repository.duplicatedKey", null, LocaleContextHolder.getLocale());
        return handleExceptionInternal(exception, new Error(message), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleException(final org.springframework.dao.EmptyResultDataAccessException exception, final WebRequest request) {
        final String message = messageSource.getMessage("repository.emptyResult", null, LocaleContextHolder.getLocale());
        return handleExceptionInternal(exception, new Error(message), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Trata exceções de acesso negado
     *
     * @param exception
     * @param request
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Object> handleException(final org.springframework.security.access.AccessDeniedException exception, final WebRequest request) {
        return handleExceptionInternal(exception, new Error(this.messageSource.getMessage("security.accessDenied", null, LocaleContextHolder.getLocale())), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
    	
    	LOGGER.warning("Caught on Aspect: "+ex.getLocalizedMessage());
    	
    	return super.handleExceptionInternal(ex, body, headers, status, request);

    }
    /**
     * Entidade auxiliar utilizada para serializar os erros para o front-end
     */
    public class Error {
        private String message;

        public Error() {
        }

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * Converte de camel case para legível/literal
     * @param phrase
     * @return
     */
    private String camelCaseToHumanReadable(String phrase) {
    	String[] words = StringUtils.splitByCharacterTypeCamelCase( phrase );
    	
    	@SuppressWarnings("deprecation")
		String readablePath = (words.length == 1) ? phrase : StringUtils.capitaliseAllWords( StringUtils.join(words," ") );
    	return readablePath;
    }
    
    /**
     * Converte de snake case para legível/literal
     * @param phrase
     * @return
     */
    private String snakeCaseToHumanReadable(String phrase) {
    	String[] words = StringUtils.splitByWholeSeparator( phrase, "_" );
    	
    	@SuppressWarnings("deprecation")
		String readable = (words.length == 1) ? phrase : StringUtils.capitaliseAllWords( StringUtils.join(words," ") );
    	return readable;
    }   
}