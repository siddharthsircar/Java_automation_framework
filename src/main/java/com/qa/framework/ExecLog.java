package com.qa.framework;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

public class ExecLog {
	public static Logger log = Logger.getLogger("LogReport");

	/*
	 * This method initiates the log
	 * */
	public static void initLog() throws IOException {
		String path = System.getProperty("user.dir")+"/Reports/Logs";
		File file = new File(path);
		if (!file.exists()) {            
            file.mkdir();
            System.out.println("Report Folder Created");
        }
		else
			System.out.println("Folder Exists");
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/config/log4j.properties");
	}
	
	public static void info(String message){
		//System.out.println(message);
		log.info(message);
	}
	
	public static void error(String message){
		//System.out.println(message);
		log.error(message);
	}
}
