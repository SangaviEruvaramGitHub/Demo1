package com.sap.hcp.mainclass;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sap.hcp.commonfunctions.AuthenticationMethods;
import com.sap.hcp.commonfunctions.BrowserInitialistaion;
import com.sap.hcp.commonfunctions.Helper;
import com.sap.hcp.commonfunctions.MDCOperations;
import com.sap.hcp.commonfunctions.Navigate;
import com.sap.hcp.commonfunctions.ReadProperties;
import com.sap.hcp.commonfunctions.Utility;

public class CreateAndDeleteDB {

	public BrowserInitialistaion initialise1;
	public BrowserInitialistaion initialise2;
	public BrowserInitialistaion initialise3;

	WebDriver driver;
	WebDriver driver1;
	WebDriver driver2;
	WebDriver driver3;

	public String currentUrl_poworklist_basic;
	public String currentUrl_poworklist_saml;
	ReadProperties rp = new ReadProperties();
	Navigate nav;
	Actions act;
	Utility util;
	MDCOperations mdcOperations;

	public String reportpath;
	String jenkinsReportpath;
	ExtentReports report;
	public ExtentTest logger;
	String jenkinsScreemshot = "http://mo-7ea2f676a.mo.sap.corp:8080/view/HCP_MDCScenario/job/HCP_MDC/ws/Screenshot/Screenshot";

	
	@Parameters({ "appurl", "USERNAME", "PASSWORD", "BROWSER" })
	@BeforeTest
	public void Login(String url, String username, String password, String browser) throws Exception {
		util = new Utility();
		nav = new Navigate();

		initialise1 = new BrowserInitialistaion(driver);
		driver = initialise1.launchBrowser(url, browser);
		initialise1.sapCloudPlatformLogin(driver, username, password);
		act = new Actions(driver);

		String time = Utility.CurrentDateTime();

		reportpath = System.getProperty("user.dir") + "/Reports/" + "Automated_Job" + time + ".html";

		// Utility.logInfo(logger,"Local report " + reportpath);
		System.out.println("Local report " + reportpath);
		
		jenkinsReportpath = "http://mo-7ea2f676a.mo.sap.corp:8080/view/HCP_MDCScenario/job/HCP_MDC/ws/Reports/"
				+ "Automated_Job" + time + ".html"; 

		// Utility.logInfo(logger,"jenkins path " + jenkinsReportpath);
		System.out.println("jenkins path " + jenkinsReportpath);

		report = new ExtentReports(reportpath);

		// Utility.logInfo(logger,"==========Report check be checked using Below
		// URL=============");
		System.out.println("==========Report check be checked using Below URL=============");

		// Utility.logInfo(logger,jenkinsReportpath);
		System.out.println(jenkinsReportpath);

	}

	@Parameters({ "XSADMINURL", "appurl", "USERNAME", "PASSWORD", "BROWSER", "Scenario_Name", "HANAPASSWORD",
			"SYSTEMUSERNAME", "SYSTEMPASSWORD", "SHINEUSERNAME", "SHINEPASSWORD" })
	@Test(priority = 1)
	public void executeMethod(String XsAdminUrl, String Appurl, String Username, String Password, String Browser,
			String methodName, String HanaPassword, String SystemUsername, String SystemPassword, String ShineUsername,
			String ShinePassword) throws Exception {

		logger = report.startTest("Execute Operation");

		Utility.logInfo(logger, "scenario name coming from POM.xml is : " + methodName);

			if (methodName.equalsIgnoreCase("MDC")) {
				Utility.logInfo(logger, "Executing MDC scenario");
				mdcScenario(Username, HanaPassword, Password, SystemUsername, SystemPassword, ShineUsername,
						ShinePassword);
			}
	}

	private void mdcScenario(String username, String hanapassword, String password, String SystemUsername,
			String SystemPassword, String ShineUsername, String ShinePassword) throws Exception {
		Robot robot = new Robot();
		Actions action = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		mdcOperations = new MDCOperations();
		String dir = System.getProperty("user.dir");
		String filePath = dir + "\\PreRequisiteFiles\\HANATESTTOOLS.tgz";

		System.out.println("MDC scenario starting...");
		int popup = driver.findElements(By.xpath(rp.getpropvalue("CloseSpan"))).size();
		if (popup > 0) {
			Helper.getElementByXpath(driver, rp.getpropvalue("NewFeaturesWindowPops"), 40).click();
			Utility.logInfo(logger, "Mark on the chekbox");
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("CloseSpan"), 40).click();
			Utility.logInfo(logger, "Click on Close");

		} else {
			System.out.println("Popup did not appeared.");
		}

		nav.navigateToDatabaseAndSchemas(driver, username, hanapassword, password, logger);
		Helper.wait(driver, 10000);
		
		for (int loop = 1; loop<=5;loop++){
		mdcOperations.createDatabase(driver, ShineUsername, ShinePassword, SystemUsername, SystemPassword, logger);
		Helper.wait(driver, 10000);

		mdcOperations.navigateToOverviewAndCheckDBStatus(driver, logger);
		Helper.wait(driver, 20000);
		
		mdcOperations.stopDatabase(driver, logger);
		Thread.sleep(2000);
		mdcOperations.deleteDatabase(driver, ShineUsername, logger);
		Thread.sleep(2000);
		Utility.logInfo(logger, "created and deleted db successfully");
		}
	}
	
	@AfterMethod
	public void tearDownReport(ITestResult result) {

		Utility.logInfo(logger, "Test in After Method");

		if (result.getStatus() == ITestResult.FAILURE) {

			Utility.CaptureScreenshot(driver);

			logger.log(LogStatus.FAIL, logger.addScreenCapture(jenkinsScreemshot + Utility.time + ".png"));

			
			/*
			 * logger.log(LogStatus.FAIL,
			 * logger.addScreenCapture(System.getProperty("user.dir") +
			 * "\\Screenshot\\Screenshot") + Utility.time + ".png");
			 */

			Utility.logFail(logger, result.getThrowable().getMessage());
		}

		if (result.getStatus() == ITestResult.SUCCESS) {

			Utility.CaptureScreenshot(driver);

			logger.log(LogStatus.PASS, logger.addScreenCapture(jenkinsScreemshot + Utility.time + ".png"));

			Utility.logPass(logger, "Test Completed Successfully");
		}

		report.endTest(logger);

		report.flush();
		
		driver.quit();
	}
}
