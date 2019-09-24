package com.qa.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

	public static String DRIVER_LOC;
	public static String REMADDR;
	public static String REMPORT;
	public static String APPPACKAGE;
	public static String APPACTIVITY;
	public static String BUNDLEID;
	public static String PROJECT;
	public static String BASEURL;
	/*
	 * This method loads all the framework specific configurations
	 * */
	public static void loadConfig() {
		Properties CONFIG = new Properties();
		try {
			FileInputStream cn = new FileInputStream(System.getProperty("user.dir") + "/config/config.properties");
			CONFIG.load(cn);
			DRIVER_LOC = CONFIG.getProperty("driverpath");
			APPPACKAGE = CONFIG.getProperty("appackage");
			APPACTIVITY = CONFIG.getProperty("appactivity");
			REMADDR = CONFIG.getProperty("remoteaddress");
			REMPORT = CONFIG.getProperty("remoteport");
			BUNDLEID = CONFIG.getProperty("bundleid");
			PROJECT = CONFIG.getProperty("projectname");
			BASEURL = CONFIG.getProperty("baseurl");
			
			ExecLog.info("Config file loaded");
		} catch (IOException e) {
			ExecLog.error("AppConfig.loadConfig :: Exception Caught: \n");
			e.printStackTrace();
		}
	}

}
