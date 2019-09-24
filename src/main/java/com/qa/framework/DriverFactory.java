package com.qa.framework;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverFactory {
	public WebDriver driver = null;
	private static DriverFactory instance = new DriverFactory();
	public static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	public static DriverFactory getInstance() {
		return instance;
	}

	/*
	 * This method creates driver based on Platform, OS and Browser specified
	 * */
	public void createDriver(String platform, String os, Browsers browser) {
		ExecLog.info("Creating Driver");
		Browsers browsers = browser;
		if (platform.equalsIgnoreCase("web")) {
			// String browser = Config.BROWSER;
			switch (browsers) {
			case firefox:
				System.setProperty("webdriver.gecko.driver", Config.DRIVER_LOC + "geckodriver.exe");
				ExecLog.info("Opening " + browsers + " browser");
				driver = new FirefoxDriver();
				break;

			case chrome:
				System.setProperty("webdriver.chrome.driver", Config.DRIVER_LOC + "chromedriver.exe");
				ExecLog.info("Opening " + browsers + " browser");
				driver = new ChromeDriver();
				break;

			case ie:
				System.setProperty("webdriver.ie.driver", Config.DRIVER_LOC + "MicrosoftWebDriver.exe");
				ExecLog.info("Opening " + browsers + " browser");
				driver = new InternetExplorerDriver();
				break;

			default:
				ExecLog.error("Browser is : '" + browsers + "' which is other than firefox, chrome and ie");
			}

			if (driver != null) {
				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				driver.manage().window().maximize();
			} else {
				ExecLog.error("Driver not initilialized");
			}
		}

		else if (platform.equalsIgnoreCase("mobile")) {
			// String os = Config.OS;
			try {
				ExecLog.info("Setting desired capabilities");
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "15");

				if (os.equalsIgnoreCase("ios")) {
					cap.setCapability("platformName", os);
					cap.setCapability("platformVersion", "11.3");
					cap.setCapability("deviceName", "iPhone Simulator");
					cap.setCapability("bundleId", Config.BUNDLEID);					
					driver = new IOSDriver<IOSElement>(
							new URL("http://" + Config.REMADDR + ":" + Config.REMPORT + "/wd/hub"), cap);
				}

				else if (os.equalsIgnoreCase("android")) {
					cap.setCapability(MobileCapabilityType.PLATFORM_NAME, os);
					cap.setCapability(MobileCapabilityType.DEVICE_NAME, "192.168.205.101:5555");
					cap.setCapability("version", "7.1.1");
					cap.setCapability("appPackage", Config.APPPACKAGE);

					// This is Launcher activity of your app (you can get it
					// from apk info app)
					cap.setCapability("appActivity", Config.APPACTIVITY);

					// Create RemoteWebDriver instance and connect to the Appium
					// server
					// It will launch the Calculator App in Android Device using
					// the
					// configurations specified in Desired Capabilities
					driver = new AndroidDriver<AndroidElement>(
							new URL("http://" + Config.REMADDR + ":" + Config.REMPORT + "/wd/hub"), cap);
				}

				else {
					System.out.println("Incorrect OS");
					ExecLog.error("Incorrect OS");
				}
				ExecLog.info("Driver Created");
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				ExecLog.error(e.getMessage());
			}
		}
		setDriver();
	}

	public void setDriver() {
		webDriver.set(driver);
	}

	public WebDriver getDriver() {
		return webDriver.get();
	}

	public void quitDriver() {
		webDriver.get().quit();
	}
}