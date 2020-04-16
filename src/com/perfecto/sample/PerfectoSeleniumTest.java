package com.perfecto.sample;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

public class PerfectoSeleniumTest {

	ITestContext testContext = null;

	RemoteWebDriver driver;
	ReportiumClient reportiumClient;

	@BeforeMethod
	public void beforeMethod(ITestContext context) throws Exception {
		// TODO Auto-generated method stub
		testContext = context;
		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
		capabilities.setCapability("platformName", testContext.getCurrentXmlTest().getParameter("platformName"));
		capabilities.setCapability("platformVersion", testContext.getCurrentXmlTest().getParameter("platformVersion"));
		capabilities.setCapability("location", testContext.getCurrentXmlTest().getParameter("location"));
		capabilities.setCapability("resolution", testContext.getCurrentXmlTest().getParameter("resolution"));
		capabilities.setCapability("manufacturer", testContext.getCurrentXmlTest().getParameter("manufacturer"));
		capabilities.setCapability("browserName", testContext.getCurrentXmlTest().getParameter("browserName"));
		capabilities.setCapability("browserVersion", testContext.getCurrentXmlTest().getParameter("browserVersion"));
		capabilities.setCapability("takesScreenshot", Boolean.parseBoolean(testContext.getCurrentXmlTest().getParameter("takesScreenshot")));
		capabilities.setCapability("model", testContext.getCurrentXmlTest().getParameter("model"));
		capabilities.setCapability("useAppiumForWeb", true);
		String cloudName = Utils.fetchCloudName("<<cloud name>>");
		String host = cloudName+".perfectomobile.com";
		capabilities.setCapability("securityToken", Utils.fetchSecurityToken("<<security token>>"));

		driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
		// IOSDriver driver = new IOSDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Reporting client. For more details, see http://developers.perfectomobile.com/display/PD/Reporting
		reportiumClient = Utils.setReportiumClient(driver, reportiumClient);
	}

	@Test
	public void test1() throws MalformedURLException {
		reportiumClient.testStart("Perfecto mobile web test", new TestContext("tag2", "tag3"));
		reportiumClient.stepStart("browser navigate to perfecto");
		driver.get("https://www.perfecto.io");
		reportiumClient.stepStart("Verify title");
		String aTitle = driver.getTitle();
		//compare the actual title with the expected title
		reportiumClient.reportiumAssert("Title Verification.", aTitle.equals("Web & Mobile App Testing | Continuous Testing | Perfecto"));
		reportiumClient.stepEnd();
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if(result.getStatus()==ITestResult.SUCCESS) {
			reportiumClient.testStop(TestResultFactory.createSuccess());
		}else if(result.getStatus()==ITestResult.FAILURE) {
			reportiumClient.testStop(TestResultFactory.createFailure(result.getThrowable()));
		}else if(result.getStatus()==ITestResult.SKIP) {
			reportiumClient.testStop(TestResultFactory.createFailure(result.getThrowable()));
		}
		driver.quit();
		// Retrieve the URL to the DigitalZoom Report (= Reportium Application) for an aggregated view over the execution
		String reportURL = reportiumClient.getReportUrl();
		System.out.println("Report URL: "+reportURL);
	}

	private static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}

	private static String getCurrentContextHandle(RemoteWebDriver driver) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
		return context;
	}

	private static List<String> getContextHandles(RemoteWebDriver driver) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
		return contexts;
	}
}
