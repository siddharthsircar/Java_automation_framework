package com.qa.framework;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

	private static ExtentTest parentTest;
    private static ExtentTest childTest;
    @SuppressWarnings("unused")
	private ExtentReport report = new ExtentReport();
    
    /*
     * This method creates a parent node in the HTML Report
     * */
    public static ExtentTest createParentTest(String testName)
    {
        parentTest = ExtentReport.extent.createTest(testName);
        return parentTest;
    }
    
    /*
     * This method creates a child node attached to the Parent Node
     * */
    public static ExtentTest createTest(String testName)
    {
        childTest = parentTest.createNode(testName);
        return childTest;
    }
    
    /*
     * This method returns the parent instance
     * */
    public static ExtentTest getParent(){
    	return parentTest;
    }
    
    /*
     * This method returns the child instance
     * */
    public static ExtentTest getTest()
    {
        return childTest;
    }

    public void info(String message)
    {
    	getTest().info(message);
    }
    
    public void pass(String message)
    {
    	getTest().pass(message);
    }
    
    public void fail(String message)
    {
    	getTest().fail(message);
    }
    
    public void warning(String message)
    {
    	getTest().warning(message);       
    }

    public void Skip(String message)
    {
    	getTest().skip(message);   
    }
}
