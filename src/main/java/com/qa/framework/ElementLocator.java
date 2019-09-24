package com.qa.framework;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;

public class ElementLocator {
	static FileInputStream fin = null;
	static Properties prop = null;
	static String objectFile = null;

	public ElementLocator(String FileName) {
		try {
			fin = new FileInputStream(FileName);
			prop = new Properties();
			prop.load(fin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method loads the object repository based on the platform and OS specified
	 * */
	public static void loadRepo(String platform, String os) {
		if (platform.equalsIgnoreCase("web")) {
			objectFile = System.getProperty("user.dir") + "/ObjectRepo/WebRepo.properties";
		} else if (platform.equalsIgnoreCase("mobile")) {
			if (os.equalsIgnoreCase("android")) {
				objectFile = System.getProperty("user.dir") + "/ObjectRepo/AndroidRepo.properties";
			} else if (os.equalsIgnoreCase("ios")) {
				objectFile = System.getProperty("user.dir") + "/ObjectRepo/iosRepo.properties";
			}
			
		}		
		@SuppressWarnings("unused")
		ElementLocator loadrepo = new ElementLocator(objectFile);
		ExecLog.info("Repository Loaded");
	}

	/*
	 * This method returns the By object based on the locatorName specified
	 * */
	public static By getLocator(String locatorName) {

		try {
			fin = new FileInputStream(objectFile);
			prop = new Properties();
			prop.load(fin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		By by = null;
		try {
			String locator = prop.getProperty(locatorName);
			String arr[] = locator.split(">");
			String locatorType = arr[0];
			String locatorvalue = arr[1];
			if (locatorType.equalsIgnoreCase("name")) {
				by = By.name(locatorvalue);
			} else if (locatorType.equalsIgnoreCase("id")) {
				by = By.id(locatorvalue);
			} else if (locatorType.equalsIgnoreCase("xpath")) {
				by = By.xpath(locatorvalue);
			} else if (locatorType.equalsIgnoreCase("linktext")) {
				by = By.linkText(locatorvalue);
			} else if(locatorType.equalsIgnoreCase("css")){
				by = By.cssSelector(locatorvalue);
			} else if(locatorType.equalsIgnoreCase("classname")){
				by = By.className(locatorvalue);
			}
			else {
				System.out.println("there was invalid locator has passed to a getLocator method...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return by;
	}
}
