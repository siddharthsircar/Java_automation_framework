package com.qa.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class DataReader {

	String fileName;
	static YamlReader reader;
	static Map<?, ?> dataMap = null;
	static Map<String,Map<?,?>> data = new HashMap<String, Map<?,?>>();
	
	public DataReader(String fileName){
		
		dataMap = data.get(fileName);
		
//		this.fileName = fileName;
//		try {
//			reader = new YamlReader(new FileReader(System.getProperty("user.dir") + "\\TestData\\"+ fileName+".yaml"));
//			Object object = reader.read();
//			dataMap = (Map<?, ?>)object;
//			ExecLog.info("YAML Data: "+dataMap.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExecLog.error(e.getMessage());
//		}
//		dataMap = data.get(fileName);
//		ExecLog.info("YAML Data: "+dataMap.toString());
	}
	
	/*
	 * This function parses all the data files and stores them in a map<key,value> structure
	 * */
	public static void loadData(){
		File folder = new File(System.getProperty("user.dir") + "/TestData/");
		File[] listOfFiles = folder.listFiles();
		String fileName = null;		
		ExecLog.info("Loading Data");
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	fileName = file.getName().toString();		        
		    	String dataFile[] = fileName.split("\\.");
		    	fileName = dataFile[0];
		        try {
					reader = new YamlReader(new FileReader(System.getProperty("user.dir") + "/TestData/"+ file.getName()));
					Object object = reader.read();
					data.put(fileName, (Map<?, ?>)object);	
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					ExecLog.error(e.getMessage());
				} catch (YamlException e) {
					e.printStackTrace();
					ExecLog.error(e.getMessage());
				}			
		    }		    
		}
		ExecLog.info("Data Loaded");
	}

	/*
	 * This function returns the data from the data file. 
	 * Further parsing will be required to fetch specific values 
	 */
	public Map<?, ?> getData(){
		try {
			ExecLog.info("YAML Data: "+dataMap.toString());
		} catch (Exception e) {
			e.printStackTrace();
			ExecLog.error(e.getMessage());
		} 
		return dataMap;
	}
	
	/*
	 * This function returns the list of values associated with the key provided
	 * */
	public List<?> getValues(String key){
		List<?> values = null;
		try {
			values = (List<?>) dataMap.get(key);
			ExecLog.info(key+ ": ");
			for(Object val : values){
				System.out.println(" - " +val);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExecLog.error(e.getMessage());
		}
		return values;
	}
	
	/*
	 * This function returns the value of the key provided 
	 * */
	public String getValue(String key){
		String value = null;
		try {
			value = (String) dataMap.get(key);
			System.out.println(key + " : " + value);			
		} catch (Exception e) {
			e.printStackTrace();
			ExecLog.error(e.getMessage());
		}
		return value;
	}
}
