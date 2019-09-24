package ewa.pages;

import com.qa.keywords.Keywords;

public class LoginPage {

	public void enterUsername(String email) {
		Keywords.setText("username_txtbx", email);
	}

	public void enterPassword(String password) {
		Keywords.setText("password_txtbx", password);
	}

	public void clickSubmit() {
		Keywords.click("submit_btn");
	}

	public void performLogin(String email, String password) {
		enterUsername(email);
		enterPassword(password);
		clickSubmit();
	}

	public boolean isErrMsgDisplayed(String locatorName) {
		boolean status = Keywords.isElementPresent(locatorName);
		return status;
	}

	public boolean atHomePage() {
		boolean status = Keywords.isElementPresent("home_welcomemsg");
		return status;
	}
	
	public void closeErrorPopup(){
		Keywords.click("errpopup_okbtn");
	}
}
