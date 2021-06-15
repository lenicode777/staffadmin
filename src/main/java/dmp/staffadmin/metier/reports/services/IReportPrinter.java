package dmp.staffadmin.metier.reports.services;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.util.List;
import java.util.Map;

public interface IReportPrinter
{
    JasperPrint printReport(String reportSourcePath, List<Object> datas, Map<String, Object> parameters) throws JRException;
}
