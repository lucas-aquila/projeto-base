package br.org.pti.fpti_base.domain.repository.impl;
//package br.org.pti.basi.domain.repository.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import br.org.pti.basi.domain.entity.usuario.User;
//import br.org.pti.basi.domain.repository.IFichaReportRepository;
//import br.org.pti.basi.infrastructure.report.IReportManager;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// *
// */
//@Component
//public class IFichaReportRepositoryImpl implements IFichaReportRepository {
//
//    /**
//     *
//     */
//    private static final String FICHA_REPORT_PATH = "/reports/fichas/ficha.jasper";
//
//    /**
//     *
//     */
//    @Autowired
//    private IReportManager reportManager;
//
//    @Override
//    public ByteArrayOutputStream generateFicha(String documento, String tipoResiduo, String codigo, ByteArrayInputStream qrcode) {
//        return null;
//    }
//
//    /**
//     *
//     * @param documento
//     * @param tipoResiduo
//     * @param codigo
//     * @param qrcodeList
//     * @return
//     */
//    @Override
//    public ByteArrayOutputStream generateFicha(final String documento, final String tipoResiduo, final String codigo, final List<ByteArrayInputStream> qrcodeList) {
//        final Map<String, Object> parameters = new HashMap<>();
//
//
//        parameters.put("documentoGerador", User.formatDocumento(documento));
//        parameters.put("tipoResiduo", tipoResiduo);
//        parameters.put("codigo", codigo);
//        parameters.put("qrcodeList", qrcodeList);
//
//        return this.reportManager.exportToPDF(parameters, FICHA_REPORT_PATH);
//    }
//}
