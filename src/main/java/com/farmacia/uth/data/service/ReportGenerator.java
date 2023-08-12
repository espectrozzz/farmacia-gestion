package com.farmacia.uth.data.service;

import java.io.File;
import java.sql.Connection;
import java.util.Map;

import org.springframework.util.ResourceUtils;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ReportGenerator {
	private String rute;
	
	public boolean reportGeneratorPDF(String reportName, Map<String, Object> parameters, JRDataSource datasource) {
		boolean status = false;
		try {
			JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile(fetchReportPath(reportName+".jasper"));
			JasperPrint impresora = JasperFillManager.fillReport(reporte, parameters, datasource);
			String rutePDF =  generateReportSavePath() + reportName + ".pdf";
			this.rute = rutePDF;
			JasperExportManager.exportReportToPdfFile(impresora, rutePDF);
			status = true;
		}catch (Exception error) {
			error.printStackTrace();
			status = false;
		}
		return status;
	}

	private String generateReportSavePath() {
		String path = null;
		try {
			path = File.createTempFile("temp", ".pdf").getAbsolutePath();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	private String fetchReportPath(String reportName) {
		String path = null;
		try {
			path = ResourceUtils.getFile("classpath:"+"Reports/"+reportName).getAbsolutePath();
		}catch(Exception error) {
			error.printStackTrace();
		}
		return path;
	}

	public String getRute() {
		return rute;
	}

	public void setRute(String rute) {
		this.rute = rute;
	}
	
}
