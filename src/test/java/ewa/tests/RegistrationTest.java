package ewa.tests;

import org.testng.annotations.Test;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.qa.framework.ApiConfig;
import com.qa.framework.DataReader;
import com.qa.framework.ExtentTestManager;
import com.qa.framework.StartUp;
import com.qa.utils.APIUtility;

public class RegistrationTest extends StartUp{
	
	DataReader userBasicInfo;
	
	@SuppressWarnings("unchecked")
	@Test
	public void AddUser() throws ParseException{
		ExtentTestManager.createTest("AddUser");
		String loginEndpnt = ApiConfig.LOGIN;
		String loginBody = "{\"email\":\"ewaemployee@celsysemail.celsyswtc.in\",\"password\":\"Cel@123\"}";
		APIUtility.userLogin(loginEndpnt, loginBody);		
		String hrToken = (String) APIUtility.parseResponse("user", "accessToken");
		String basicInfoEndpnt = ApiConfig.ADDBASICINFO;
		userBasicInfo = new DataReader("UserBasicInfo");
		Map<String, String> basicInfoBody= (Map<String, String>) userBasicInfo.getData();
		APIUtility.post(basicInfoEndpnt, hrToken, basicInfoBody);
		Long userId = (Long) APIUtility.parseResponse("userInfo", "createdUserId");
		System.out.println(userId);
	}
}
