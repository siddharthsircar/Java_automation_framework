package com.qa.framework;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReport {

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;

	/*
	 * This method sets the configurations of the report
	 * */
	public static void reportSetup() {
		DateFormat df = new SimpleDateFormat("ddMMyyHHmmss");
		Calendar calobj = Calendar.getInstance();
	    htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/Reports/ExtentReport"+ df.format(calobj.getTime()) +".html");		
		htmlReporter.config().setChartVisibilityOnOpen(false);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle("Automation Report");
//		htmlReporter.config().setReportName(Config.PROJECT + " Automation Report");
		htmlReporter.config().setProtocol(Protocol.HTTPS);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		extent = new ExtentReports();
//		extent.setSystemInfo("Project", Config.PROJECT);// get from config																
		extent.setSystemInfo("OS", Config.REMPORT);// get from system
		extent.setSystemInfo("Environment", "QA"); // get from config	
		extent.attachReporter(htmlReporter);
		ExecLog.info("Reporting Started");
	}
}