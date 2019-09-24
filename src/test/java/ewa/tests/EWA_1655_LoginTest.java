package ewa.tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.asserts.SoftAssert;

import com.qa.framework.DataReader;
import com.qa.framework.ExtentTestManager;
import com.qa.framework.StartUp;

import ewa.pages.LoginPage;

public class EWA_1655_LoginTest extends StartUp {

	DataReader loginData;
	LoginPage loginpage = new LoginPage();
	SoftAssert assertion = new SoftAssert();
	String unregemail;
	String incorrectpwd;
	String invalidemail;
	String regemail;
	String pwd;

	@SuppressWarnings("unchecked")
	@BeforeClass(alwaysRun = true)
	public void preTest() {
		loginData = new DataReader("LoginData");
		unregemail = loginData.getValue("unregisteredEmail");
		incorrectpwd = loginData.getValue("invalidPassword");
		invalidemail = loginData.getValue("invalidEmail");
		List<?> data = loginData.getValues("validLogin");
		Map<String, String> validLogin = new HashMap<String, String>();
		for (Object validData : data) {
			System.out.println(validData);
			validLogin = (Map<String, String>) validData;
		}
		regemail = validLogin.get("email");
		pwd = validLogin.get("password");
	}

	@Test(priority = 1)
	public void verifyEmptyLogin() {
		ExtentTestManager.createTest("verifyEmptyLogin");

		loginpage.performLogin("", "");
		boolean status = loginpage.isErrMsgDisplayed("emptyemail_errmsg");
		assertEquals(status, true);
		loginpage.performLogin(regemail, "");
		status = loginpage.isErrMsgDisplayed("emptypwd_errmsg");
		assertEquals(status, true);

		loginpage.performLogin("", pwd);
		status = loginpage.isErrMsgDisplayed("emptyemail_errmsg");
		assertEquals(status, true);

		assertion.assertAll();
	}

	@Test(priority = 2)
	public void verifyInvalidLogin() {
		ExtentTestManager.createTest("verifyInvalidLogin");

		// Validate unregistered user login
		loginpage.performLogin(invalidemail, incorrectpwd);
		boolean status = loginpage.isErrMsgDisplayed("incorrectemail_errmsg");
		assertEquals(status, true);

		// Validate registered email and incorrect password login
		loginpage.performLogin(regemail, incorrectpwd);
		status = loginpage.isErrMsgDisplayed("incorrectpwd_errpopup");
		assertEquals(status, true);
		loginpage.closeErrorPopup();

		// Validate unregistered email and password login
		loginpage.performLogin(unregemail, pwd);
		status = loginpage.isErrMsgDisplayed("unreguser_errmsg");
		assertEquals(status, true);
		loginpage.closeErrorPopup();
		
		assertion.assertAll();
	}

	@Test(priority = 3)
	public void verifyLogin() {
		ExtentTestManager.createTest("verifyLogin");
		loginpage.performLogin(regemail, pwd);
		boolean status = loginpage.atHomePage();
		assertEquals(status, true);
	}
}
