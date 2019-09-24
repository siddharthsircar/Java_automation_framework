package com.qa.keywords;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.framework.ElementLocator;
import com.qa.framework.ExecLog;
import com.qa.framework.ExtentTestManager;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class Keywords {

	static WebDriver driver;
	private static ExtentTestManager report = new ExtentTestManager();
	static WebDriverWait wait;
	public Keywords(WebDriver driver){
		Keywords.driver = driver;
		wait = new WebDriverWait(driver, 30);
	}

	
	/*
	 * This method is used for wait
	 * */ 
	public static void waitThread(int t) {
		try {
			Thread.sleep(t);
			ExecLog.info("Waited for "+ t +" seconds");
			report.pass("Waited for "+ t +" seconds");
		} catch (Exception e) {
			e.printStackTrace();
			ExecLog.error("Failed to wait");
			report.fail("Exception: " + e);
		}
	}

    /* summary:
     * This method Taps on a control 
     * param: locatorName
     * */
	public static void click(String locatorName) {
		
		By element = ElementLocator.getLocator(locatorName);
		if (element != null)
        {
			try {				
				wait.until(ExpectedConditions.elementToBeClickable(element));
				WebElement webel = driver.findElement(element);
				webel.click();
				report.pass("Clicked on: "+locatorName);
				ExecLog.info("Clicked on: "+locatorName);
			} catch (WebDriverException e) {
				ExecLog.error("Could not click on: "+locatorName+"\n Exception: " + e);
				report.fail("Could not click on: "+locatorName);
				report.fail("Exception: " + e);
			}
        } else
        {
            report.fail(locatorName + " not found in repository");
            ExecLog.error(locatorName + " not found in repository");
        }
	}
	
	 /* summary:
     * This method Taps on a control 
     * param: locatorName
     * */
	public static void jsClick(String locatorName) {
		
		By element = ElementLocator.getLocator(locatorName);
		if (element != null)
        {
			try {
				WebElement webel = driver.findElement(element);
				wait.until(ExpectedConditions.visibilityOf(webel));
				((JavascriptExecutor)driver).executeScript("arguments[0].click()", webel);
				report.pass("Clicked on: "+locatorName);
				ExecLog.info("Clicked on: "+locatorName);
			} catch (WebDriverException e) {
				ExecLog.error("Could not click on: "+locatorName+"\n Exception: " + e);
				report.fail("Could not click on: "+locatorName);
				report.fail("Exception: " + e);
			}
        } else
        {
            report.fail(locatorName + " not found in repository");
            ExecLog.error(locatorName + " not found in repository");
        }
	}

    /* summary:
     * This method sets the text in a text box
     * param: locatorName
     * param: text
     * */
    public static void setText(String locatorName, String text)
    {
        By element = ElementLocator.getLocator(locatorName);
        
        if (element != null)
        {
            try
            {
            	wait.until(ExpectedConditions.visibilityOfElementLocated((element)));
                driver.findElement(element).clear();
                driver.findElement(element).sendKeys(text);
                report.pass("Entered : " + text + " in " + locatorName);
                ExecLog.info("Entered : " + text + " in " + locatorName);
            }
            catch (WebDriverException wd)
            {
                report.fail("Exception on the element " + locatorName + "  " + wd.getMessage());
                ExecLog.error("Element: " + locatorName + " not found");
            }
        }
        else
        {
            report.fail(locatorName + " not found in repository");
            ExecLog.error(locatorName + " not found in repository");
        }
    }
    
    /* summary:
     * This method gets the inner text of an element
     * param: locatorName
    */
    public static String getText(String locatorName)
    {
        String text = "";
        By element = ElementLocator.getLocator(locatorName);
        
        if (element != null)
        {
            try
            {
            	wait.until(ExpectedConditions.visibilityOfElementLocated((element)));
                text = driver.findElement(element).getText();
                report.info("Innertext of " + locatorName + " : " + text);
                ExecLog.info("Innertext of " + locatorName + " : " + text);
            }
            catch (WebDriverException wd)
            {
                report.fail("Exception on the element " + locatorName + "  " + wd.getMessage());
                ExecLog.error("Element: " +  locatorName + " not found");
            }
        }
        else
        {
            report.fail(locatorName + " not found in repository");
            ExecLog.error(locatorName + " not found in repository");
        }

        return text;
    }

    /* summary:
     * This method clears the text of a text field
     * param: locatorName
    */
    public static void clearText(String locatorName)
    {
        By element = ElementLocator.getLocator(locatorName);

        if (element != null)
        {
            try
            {
            	wait.until(ExpectedConditions.visibilityOfElementLocated((element)));
                driver.findElement(element).clear();
                report.pass(locatorName + "Cleared");
                ExecLog.info(locatorName + "Cleared");
            }
            catch (WebDriverException wd)
            {
                report.fail("Exception on the element " + locatorName + "  " + wd.getMessage());
                ExecLog.error("Element: " +  locatorName + " not found");
            }
        }
        else
        {
            report.fail(locatorName + " not found in repository");
            ExecLog.error(locatorName + " not found in repository");
        }
    }

    /* summary:
     * This method check the existence of a particular control
     * param: locatorName
    */
    public static Boolean isElementPresent(String locatorName)
    {
        boolean exists = false;
        By element = ElementLocator.getLocator(locatorName);
       
        if (element != null)
        {
            try
            {
                exists = driver.findElement(element).isDisplayed();
                report.pass("Element: " + locatorName + " Exists on screen: " + exists);
                ExecLog.info("Element: " + locatorName + " Exists on screen: " + exists);
            }
            catch (Exception e)
            {
                report.fail("Exception on Element: " + locatorName + e.getMessage());
                ExecLog.error("Element locator:" + locatorName + " not found");
            }


        }
        else
        {
            report.fail(locatorName + " not found in repository");
            ExecLog.error(locatorName + " not found in repository");
        }
        return exists;
    }

    /* summary:
     * This methd is to check if element is not present
     * param: locatorName
    */
    public static Boolean isElementNotPresent(String locatorName)
    {
        boolean exists = false;
        By element = ElementLocator.getLocator(locatorName);
        if (element != null)
        {
            try
            {
                exists = driver.findElement(element).isDisplayed();
                report.fail("Element: " + locatorName + " Exists on screen: " + exists);
                ExecLog.error("Element: " + locatorName + " Exists on screen: " + exists);
            }
            catch (Exception e)
            {
                report.pass("Element: " + locatorName + " exists on screen: " + exists);
                ExecLog.info("Element locator:" + locatorName + " not found");
            }
        }
        else
        {
            report.fail(locatorName + " not found in repository");
            ExecLog.error(locatorName + " not found in repository");
        }
        return exists;
    }

    /* summary:
     * This method verifies whether an element is not visible
     * param: locatorName
    */
    public static Boolean isElementNotVisible(String locatorName)
    {
        boolean exists = false;
        By element = ElementLocator.getLocator(locatorName);
        
        if (element != null)
        {
            try
            {
                exists = driver.findElement(element).isDisplayed();
                if (exists == true)
                {
                	report.fail("Element: " + locatorName + " Exists on screen: " + exists);
                    ExecLog.error("Element: " + locatorName + " Exists on screen: " + exists);
                }
                else
                {
                	report.pass("Element: " + locatorName + " exists on screen: " + exists);
                	ExecLog.info("Element locator: " + locatorName + " not found");
                }
            }
            catch (Exception e)
            {
                report.fail("Exception on Element: " + locatorName + e.getMessage());
                ExecLog.error("Element locator: " + locatorName + " not found");
            }
        }
        else
        {
            report.fail(locatorName + " not found in repository");
            ExecLog.error(locatorName + " not found in repository");
        }
        return exists;
    }
    
    /* summary:
     * This method accepts on screen popups/alerts
    */
    public static void acceptPopup(){
    	try {
			driver.switchTo().alert().accept();
			report.pass("Alert Accepted");
			ExecLog.info("Alert Accepted");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			report.fail("Alert not accepted");
			ExecLog.error("Alert not accepted, Exception: \n"+e.getMessage());
		}
    }
    
    /* summary:
     * This method dismisses on screen popups/alerts
    */
    public static void declinePopup(){
    	try {
			driver.switchTo().alert().dismiss();
			report.pass("Alert declined");
			ExecLog.info("Alert declined");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			report.fail("Alert not declined");
			ExecLog.error("Alert not declined, Exception: \n"+e.getMessage());
		}
    }
    
    @SuppressWarnings("unchecked")
	public static void closeKeyboard() {
        IOSDriver<IOSElement> iosDriver = (IOSDriver<IOSElement>) driver;
        try {
            iosDriver.hideKeyboard();
            report.pass("Keyboard Closed");
            ExecLog.info("Keyboard Closed");
        } catch (Exception e) {
            e.printStackTrace();
            report.fail("Keyboard not closed");
            ExecLog.error("Keyboard not closed, Exception: \n"+e.getMessage());
        }
    }
    
    /* summary:
     * This method switches focus to iframe
    */
    public static void switchToIFrame(String locatorName){
    	By element = ElementLocator.getLocator(locatorName);
    	try {    		
        	wait.until(ExpectedConditions.visibilityOfElementLocated((element)));
        	WebElement iframe = driver.findElement(element);
        	driver.switchTo().frame(iframe);
        	report.pass("Switched to iFrame: "+locatorName);
            ExecLog.info("Switched to iFrame: "+locatorName);
    	} catch (Exception e) {
            e.printStackTrace();
            report.fail("Unable to switch to iFrame: "+locatorName);
            ExecLog.error("Unable to switch to iFrame: "+locatorName+" , Exception: \n"+e.getMessage());
        }	
    }
   
    /* summary:
     * This method switches focus to default content
    */
    public static void switchToDefaultContent(){    	
    	try{
        	driver.switchTo().defaultContent();
        	report.pass("Switched to default content");
            ExecLog.info("Switched to default content");
    	} catch (Exception e){
    		report.pass("Unable to switch to default content");
            ExecLog.info("Unable to switch to default content , Exception: \n"+e.getMessage());
    	}
    }
}
