package com.qa.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.qa.framework.ApiConfig;
import com.qa.framework.ExecLog;
import com.qa.framework.ExtentTestManager;

public class APIUtility {
	static URL url;
	static HttpURLConnection conn;
	static String response;
	static String twoHyphens = "--";
    static String boundary = "*****";
    static String lineEnd = "\r\n";
    
    private static ExtentTestManager report = new ExtentTestManager();
    
//	public static void main(String args[]) {
//		ApiConfig.loadConfig();
//		String body = "{\"username\":\"tradeseller1@yopmail.com\",\"password\":\"Cel@1234\",\"deviceToken\":\"random_access_token_1\",\"role\":\"seller\"}";
//		userLogin(ApiConfig.LOGIN, body);
//		// parseResponse();
//	}

	public static String userLogin(String endpoint, String body) {
		String output;
		String authToken = null;
		try {
			url = new URL("http://" + ApiConfig.URL + endpoint);
			ExecLog.info("HTTP Request being made to: " + url);
			report.info("HTTP Request being made to: "+ url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.flush();
			
			if (conn.getResponseCode() != 200) {
				report.fail("HTTP error code: " + conn.getResponseCode());
				throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
			}
			report.pass("Response Code: "+conn.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;			
			}
			ExecLog.info("Respone: "+response);
			report.pass("Response: "+response);
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		} catch (IOException e) {			
			e.printStackTrace();
			report.fail(e.getMessage());
		}
		return authToken;
	}

	public static void get(String endpoint, String authToken) {
		String output;
		try {
			url = new URL("http://" + ApiConfig.URL + endpoint);
			ExecLog.info("HTTP Request being made to: " + url);
			report.info("HTTP Request being made to: "+ url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", authToken);
			if (conn.getResponseCode() != 200) {
				report.fail("HTTP error code: " + conn.getResponseCode());
				throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
			}
			report.pass("Response Code: "+conn.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			ExecLog.info("Respone: "+response);
			report.pass("Response: "+response);			
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		}
	}

	public static void get(String endpoint) {
		String output;
		try {
			url = new URL("http://" + ApiConfig.URL + endpoint);
			ExecLog.info("HTTP Request being made to: " + url);
			report.info("HTTP Request being made to: "+ url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			if (conn.getResponseCode() != 200) {
				report.fail("HTTP error code: " + conn.getResponseCode());
				throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
			}
			report.pass("Response Code: "+conn.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				response = output;
			}
			ExecLog.info("Respone: "+response);
			report.pass("Response: "+response);	
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		}
	}

	/*
	 * The method accepts 3 parameters (in the given sequence) 
	 * 1. endpoint 
	 * 2. body 
	 * 3. authToken
	 */
	public static void post(String endpoint, String... params) {
		String output;
		String body = params[0];

		try {
			url = new URL("http://" + ApiConfig.URL + endpoint);
			ExecLog.info("HTTP Request being made to: " + url);
			report.info("HTTP Request being made to: "+ url);			
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			if (params.length == 2) {
				String authToken = params[1];
				conn.setRequestProperty("Authorization", authToken);
			}
			
			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.flush();

			if (conn.getResponseCode() != 200) {
				report.fail("HTTP error code: " + conn.getResponseCode());
				throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
			}
			report.pass("Response Code: "+conn.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				response = output;
			}
			ExecLog.info("Respone: "+response);
			report.pass("Response: "+response);
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		}
	}

	public static void post(String endpoint, String token, Map<?,?> body) {
		String output;
		try {
			url = new URL("http://" + ApiConfig.URL + endpoint);
			ExecLog.info("HTTP Request being made to: " + url);
			report.info("HTTP Request being made to: "+ url);	
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			conn.setConnectTimeout(15000);
			conn.setReadTimeout(15000);
			conn.setRequestProperty("Authorization", token);
			DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
			
			Set keyset = body.keySet();
			List keys = new ArrayList();
			keys.addAll(keyset);			
			for(int i=0;i<keys.size();i++){
				outputStream.writeBytes(twoHyphens + boundary + lineEnd);
				outputStream.writeBytes("Content-Disposition: form-data; name=\"" + keys.get(i) + "\""+ lineEnd);
				outputStream.writeBytes("Content-Type: text/plain; charset=UTF-8" + lineEnd);
				outputStream.writeBytes(lineEnd);
				outputStream.writeBytes(body.get(keys.get(i))+ lineEnd);
			}
			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary +
	                twoHyphens + lineEnd);

			outputStream.flush();
			outputStream.close();
	        
			if (conn.getResponseCode() != 200) {
				report.fail("HTTP error code: " + conn.getResponseCode());
				throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
			}
			report.pass("Response Code: "+conn.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			ExecLog.info("Respone: "+response);
			report.pass("Response: "+response);
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		}
	}
	
	/*
	 * This method accepts 3 parameters: 1. endpoint 2. authToken 3. Body in
	 * json format
	 */
	public static void put(String endpoint, String... params) {
		String output;
		try {
			url = new URL("http://" + ApiConfig.URL + endpoint);
			ExecLog.info("HTTP Request being made to: " + url);
			report.info("HTTP Request being made to: "+ url);	
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", params[0]);

			String body = params[1];

			OutputStream os = conn.getOutputStream();
			os.write(body.getBytes());
			os.flush();

			if (conn.getResponseCode() != 200) {
				report.fail("HTTP error code: " + conn.getResponseCode());
				throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
			}
			report.pass("Response Code: "+conn.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((output = br.readLine()) != null) {
				response = output;
			}
			ExecLog.info("Respone: "+response);
			report.pass("Response: "+response);
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		}
	}

	public static void delete(String endpoint, String authToken) {
		String output;
		try {
			url = new URL("http://" + ApiConfig.URL + endpoint);
			ExecLog.info("HTTP Request being made to: " + url);
			report.info("HTTP Request being made to: "+ url);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", authToken);

			if (conn.getResponseCode() != 200) {
				report.fail("HTTP error code: " + conn.getResponseCode());
				throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
			}
			report.pass("Response Code: "+conn.getResponseCode());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				response = output;
			}
			ExecLog.info("Respone: "+response);
			report.pass("Response: "+response);			
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			report.fail(e.getMessage());
		}
	}
	
	public static String parseResponse(String key) throws ParseException {
		JSONParser parse = new JSONParser();
		
		JSONObject jobj = (JSONObject)parse.parse(response);
		String keyValue = (String)jobj.get(key);
		System.out.println(keyValue);
		return keyValue;
	}
	
	public static Object parseResponse(String parentKey, String key) throws ParseException {
		JSONParser parse = new JSONParser();
		
		JSONObject parentobj = (JSONObject)parse.parse(response);
		JSONObject childobj = (JSONObject)parentobj.get(parentKey);
		
		Object keyValue = childobj.get(key);
		System.out.println(keyValue);
		return keyValue;
	}
}
