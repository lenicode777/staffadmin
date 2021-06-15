package dmp.staffadmin.metier.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

//import dmp.staffadmin.entities.Employe;
//import dmp.staffadmin.metier.impl.EmployeMetier;
//import dmp.staffadmin.metier.interfaces.IEmployeMetier;
//import dmp.staffadmin.metier.utilities.reports.DmdCongeReportObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;

//@Service
@Deprecated
public class ReportServiceHold
{
	/*
	@Autowired
	IEmployeMetier empMet;

	public String exportReport(String format) throws FileNotFoundException, JRException {
		String reprotDir = System.getProperty("user.dir") + "\\report";
		// String filePath = System.getProperty("user.dir")+
		// "\\src\\main\\resources\\dmd_conge.jrxml";
		String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\dmdConge.jrxml";

		List<Employe> emps = empMet.findAll();
		// File file = ResourceUtils.getFile("classpath:dmd_conge.jrxml");
		File file = new File(filePath);
		if (file.exists()) {
			System.out.println("===============FOUND==========");
			JasperReport jReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(emps);
			Map<String, Object> parametres = new HashMap<>();
			parametres.put("createdBy", "Lenicode.dev");
			JasperPrint jPrint = JasperFillManager.fillReport(jReport, parametres, dataSource);
			if (format.equalsIgnoreCase("html")) {
				JasperExportManager.exportReportToHtmlFile(jPrint, reprotDir + "\\emps.html");
			}
			if (format.equalsIgnoreCase("pdf")) {
				JasperExportManager.exportReportToPdfFile(jPrint, reprotDir + "\\employes.pdf");
			}
		} else {
			System.out.println("=====================FILE NOT FOUND==========");
		}
		return "";
	}

	public String exportReport(List ReportDataSource, String ReportSourcefilePath, String format, String reportDir, String reportFileName)
			throws FileNotFoundException, JRException 
	{
		//
		// String reprotDir = System.getProperty("user.dir") + "\\report";String filePath = System.getProperty("user.dir")+
		// "\\src\\main\\resources\\dmd_conge.jrxml";
		/*
		 * String filePath = System.getProperty("user.dir")+
		 * "\\src\\main\\resources\\dmdConge.jrxml";
		 * 
		 * List<Employe> emps = empMet.findAll();
		 */
		// File file = ResourceUtils.getFile("classpath:dmd_conge.jrxml");
	/*
		File file = new File(ReportSourcefilePath);
		if (file.exists()) {
			System.out.println("===============FOUND==========");
			JasperReport jReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ReportDataSource);

			
			  Map<String, Object> parametres = new HashMap<>(); parametres.put("createdBy",
			  "Lenicode.dev");
			 
			JasperPrint jPrint = JasperFillManager.fillReport(jReport, parametres, dataSource);
			if (format.equalsIgnoreCase("html")) 
			{
				JasperExportManager.exportReportToHtmlFile(jPrint, reportDir + "\\" + reportFileName + ".html" );
			}
			if (format.equalsIgnoreCase("pdf")) 
			{
				JasperExportManager.exportReportToPdfFile(jPrint, reportDir + "\\" + reportFileName +".pdf");
			}
		} 
		else 
		{
			System.out.println("=====================REPORT FILE NOT FOUND==========");
			throw new RuntimeException("Une erreur inatendue s'est produite lors de la génération du rapport");
		}
		return "";
		
	}
	*/
}
