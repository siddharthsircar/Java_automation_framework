package com.qa.utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;
import com.qa.framework.Config;
import com.qa.framework.DriverFactory;
import com.qa.framework.ExecLog;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class UtilLibrary {

    private static AppiumDriverLocalService appiumService = null;
    private static String appiumServiceUrl = null;
    
    /*
	 * This method is used to takeScreenshot
	 * */
	public static void takeScreenshot(String fileName){		
		WebDriver driver = DriverFactory.getInstance().getDriver();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			Files.copy(scrFile, new File(fileName + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ExecLog.error("Failed to take screenshot");
			e.printStackTrace();			
		}
		ExecLog.error("Screenshot Saved");
	}
	
	/*
	 * This method is used to start Appium service
	 * */
	public static void startAppium(){
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress(Config.REMADDR);
		int port =Integer.parseInt(Config.REMPORT);
		builder.usingPort(port);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
		appiumService = AppiumDriverLocalService.buildService(builder);
		
//		appiumService = AppiumDriverLocalService.buildDefaultService();
		appiumService.start();

		appiumServiceUrl = appiumService.getUrl().toString();        
        ExecLog.info("==========================");
        ExecLog.info("Appium Started");
        ExecLog.info("Appium Service Address : - "+ appiumServiceUrl);
        ExecLog.info("==========================");
	}
	
	/*
	 * This method is used to terminate Appium service
	 * */
	public static void terminateAppium(){
		appiumService.stop();
		ExecLog.info("==========================");
        ExecLog.info("Appium Terminated");
        ExecLog.info("==========================");
	}
}
