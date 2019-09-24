package com.qa.framework;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.qa.keywords.Keywords;
import com.qa.utils.UtilLibrary;

public class StartUp {

	protected WebDriver driver;
	protected Keywords keyword;

	/*
	 * This function loads the config files, sets up the report and logging
	 */
	@BeforeSuite(alwaysRun = true)
	public void oneTimeSetup(ITestContext context) throws IOException {		
		ExecLog.initLog();		
		Config.loadConfig();
		ApiConfig.loadConfig();
		DataReader.loadData();
		ExtentReport.reportSetup();
		String platform = context.getCurrentXmlTest().getParameter("platform");
		if(platform.equalsIgnoreCase("mobile")){
			com.qa.utils.UtilLibrary.startAppium();
		}		
	}

	/*
	 * This method loads the object repo and creates driver 
	 */
	@Parameters({ "platform", "os", "browser" })
	@BeforeClass(alwaysRun = true)
	public void setup(String platform, String os, String browser) {
		ElementLocator.loadRepo(platform, os);
		DriverFactory.getInstance().createDriver(platform, os, Browsers.valueOf(browser));
		driver = DriverFactory.getInstance().getDriver();
		if (platform.equalsIgnoreCase("web")){
			driver.get(Config.BASEURL);
		}
		keyword = new Keywords(driver);
		ExtentTestManager.createParentTest(getClass().getSimpleName());
	}

//	@Parameters({ "platform", "os", "browser" })
//	@BeforeMethod(alwaysRun = true)
//	public void preTest(String platform, String os, String browser) {
//		ElementLocator.loadRepo(platform, os);
//		DriverFactory.getInstance().createDriver(platform, os, browser);
//		driver = DriverFactory.getInstance().getDriver();
//		keyword = new Keywords(driver);
//	}

	/*
	 * This method evaluates the test result and adds it to the Extent Report
	 * */
	@Test
	@AfterMethod(alwaysRun = true)
	public void testResult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			String path = System.getProperty("user.dir") + "/Reports/Screenshots";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}
			DateFormat df = new SimpleDateFormat("ddMMyyHHmmss");
			Calendar calobj = Calendar.getInstance();
			String fileName = result.getName() + df.format(calobj.getTime());
			String scrnshot = path + "//" + fileName;
			UtilLibrary.takeScreenshot(scrnshot);
			ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());
			ExtentTestManager.getTest().log(Status.FAIL, "Screenshot: \n",
					MediaEntityBuilder.createScreenCaptureFromPath(scrnshot + ".jpg").build());
			ExtentTestManager.getTest().log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
		} else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.YELLOW));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentTestManager.getTest().log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
		}
	}

	/*
	 * This method closes the driver, saves the report and terminates the appium service
	 * */
	@AfterMethod
	@AfterClass(alwaysRun = true)
	public void tearDown(ITestContext context) {		
		// Quit Driver
		ExecLog.info("Quitting Driver");
		DriverFactory.getInstance().quitDriver();		
		// After Suite
		ExtentReport.extent.flush();
		// Terminate Appium
		String platform = context.getCurrentXmlTest().getParameter("platform");
		if(platform.equalsIgnoreCase("mobile")){
			UtilLibrary.terminateAppium();
		}
		
	}
}
