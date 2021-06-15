package dmp.staffadmin.metier.reports.services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportPrinterImpl implements IReportPrinter
{
    @Override
    public JasperPrint printReport(String reportSourcePath, List<Object> datas, Map<String, Object> parameters) throws JRException {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datas);
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSourcePath);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return jasperPrint;
    }
}
