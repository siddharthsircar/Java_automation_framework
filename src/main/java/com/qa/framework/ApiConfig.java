package com.qa.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiConfig {
	public static String URL;
	public static String LOGIN;
	public static String DELUSER;
	public static String GETOTC;
	public static String ADDBASICINFO;
	/*
	 * This method loads all the API specific configurations
	 * */
	public static void loadConfig() {
		Properties CONFIG = new Properties();
		try {
			FileInputStream cn = new FileInputStream(System.getProperty("user.dir") + "/config/AppConfig.properties");
			CONFIG.load(cn);
			// DRIVER_LOC = CONFIG.getProperty("driverpath");
			URL = CONFIG.getProperty("envip");
			LOGIN = CONFIG.getProperty("loginendpnt");
			DELUSER = CONFIG.getProperty("delendpnt");
			GETOTC = CONFIG.getProperty("getotcendpnt");
			ADDBASICINFO = CONFIG.getProperty("addbasicinfo");
			ExecLog.info("API Configurations loaded");			
		} catch (IOException e) {
			ExecLog.error("AppConfig.loadConfig :: Exception Caught: \n");
			e.printStackTrace();
		}
	}
}
