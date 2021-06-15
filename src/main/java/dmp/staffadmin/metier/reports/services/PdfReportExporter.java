package dmp.staffadmin.metier.reports.services;

import net.sf.jasperreports.engine.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service("pdfReporter")
@Primary
public class PdfReportExporter implements IReportExporter
{
    private final IReportPrinter reportPrinter;

    public PdfReportExporter(IReportPrinter reportPrinter)
    {
        this.reportPrinter = reportPrinter;
    }

    @Override
    public File exportReport(JasperPrint jasperPrint, String destinationFilePath) throws JRException
    {
        JasperExportManager.exportReportToPdfFile(jasperPrint, destinationFilePath);
        return new File(destinationFilePath);
    }

    @Override
    public File exportReport(String reportSourcePath, List<Object> datas, Map<String, Object> parameters, String destinationFilePath) throws JRException
    {
        JasperPrint jasperPrint = reportPrinter.printReport(reportSourcePath, datas,parameters);
        return exportReport(jasperPrint,destinationFilePath);
    }
}
