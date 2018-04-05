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

public class MDCScenario {

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
	String jenkinsScreemshot = "http://mo-9b4709999.mo.sap.corp:8080/view/HCP_Automation/job/Automated_Job/ws/Screenshot/Screenshot";

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

		jenkinsReportpath = "http://mo-9b4709999.mo.sap.corp:8080/view/HCP_Automation/job/Automated_Job/ws/Reports/"
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
			mdcScenario(Username, HanaPassword, Password, SystemUsername, SystemPassword, ShineUsername, ShinePassword);
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

		try {
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

			Helper.wait(driver, 2000);
			
			int EuropeROTStagingTile = driver.findElements(By.xpath(rp.getpropvalue("EuropeRotStaging"))).size();
			if (EuropeROTStagingTile > 0) {
				Helper.getElementByXpath(driver, rp.getpropvalue("EuropeRotStaging"), 40).click();
				Utility.logInfo(logger, "Click on Europe (Rot) - Staging");
				Helper.wait(driver, 2000);
			}
			
			nav.navigateToDatabaseAndSchemas(driver, username, hanapassword, password, logger);
			Helper.wait(driver, 10000);

			mdcOperations.createDatabase(driver, ShineUsername, ShinePassword, SystemUsername, SystemPassword, logger);
			Helper.wait(driver, 10000);

			mdcOperations.navigateToOverviewAndCheckDBStatus(driver, logger);
			Helper.wait(driver, 20000);

			Helper.getElementByXpath(driver, rp.getpropvalue("SAPHanaCockpit"), 40).click();
			Helper.wait(driver, 14000);
			util.windowHandles(driver, 1);

			mdcOperations.sapHanaLogon(driver, SystemUsername, SystemPassword, logger);
			Helper.wait(driver, 8000);

			int OKButton = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("SpanOK"), 15);
			if (OKButton > 0) {
				Helper.getElementByXpath(driver, rp.getpropvalue("SpanOK"), 40).click();
				Helper.wait(driver, 5000);
			}

			Helper.wait(driver, 2000);
			int ContinueButton = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("ContinueButton"), 15);
			if (ContinueButton > 0) {
				Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButton"), 40).click();
			}

			Helper.wait(driver, 22000);
			String heading = Helper.getElementByXpath(driver, rp.getpropvalue("HanaDBAdministrationHeading"), 40)
					.getAttribute("title");
			if (heading.equals("SAP HANA Database Administration")) {
				Utility.logPass(logger, "Successfully logged in to cockpit");
			} else {
				Utility.logInfo(logger, "Cockpit login failed");
			}

			driver.close();

			util.handleParentWindows(driver);
			Helper.wait(driver, 8000);
			Helper.getElementByXpath(driver, rp.getpropvalue("SapHanaWebBasedDevelopmentWorkbench"), 40).click();
			Helper.wait(driver, 8000);
			util.windowHandles(driver, 1);
			Helper.wait(driver, 4000);
			Helper.getElementByXpath(driver, rp.getpropvalue("Security"), 40).click();
			Helper.wait(driver, 25000);
			util.windowHandles(driver, 2);
			Helper.wait(driver, 10000);
			for (int i = 0; i <= 10; i++) {
				int UsersElementCount = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("Users"), 20);
				if (UsersElementCount > 0) {
					break;
				}
				Helper.wait(driver, 4000);
			}
			Helper.checkElement(driver, rp.getpropvalue("Users"), 80);
			Helper.getElementByXpath(driver, rp.getpropvalue("Users"), 40).click();
			Helper.wait(driver, 6000);
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
			
			/*int SystemElementCount = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("SYSTEM"), 20);
			if (SystemElementCount == 0) {
				Helper.getElementByXpath(driver, rp.getpropvalue("Users"), 40).click();
				Helper.wait(driver, 6000);
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
			}*/

			/*
			 * Helper.getElementByXpath(driver, rp.getpropvalue("Users"), 40).click();
			 * Helper.wait(driver, 2000); robot.keyPress(KeyEvent.VK_RIGHT);
			 * robot.keyRelease(KeyEvent.VK_RIGHT);
			 */

			System.out.println("after robot class");

			Helper.wait(driver, 20000);

			System.out.println("after wait");

			// now execute query which actually will scroll until that element is not
			// appeared on page.

			WebElement systemElement = Helper.getElementByXpath(driver, rp.getpropvalue("SYSTEM"), 40);

			js.executeScript("arguments[0].scrollIntoView(true);", systemElement);

			Helper.wait(driver, 4000);
			


			try {
				System.out.println("try block");
				mdcOperations.refreshBrowserWindow(driver);
                		Helper.wait(driver, 20000);
                		WebElement element = Helper.getElementByXpath(driver, rp.getpropvalue("SYSTEM"), 40);
                		element.click();
                		Helper.wait(driver, 4000);
                		action.moveToElement(element).doubleClick().build().perform();
                		Helper.wait(driver, 4000);
			} catch (org.openqa.selenium.StaleElementReferenceException ex) {
				System.out.println("catch block");
				mdcOperations.refreshBrowserWindow(driver);
                		Helper.wait(driver, 20000);
                		WebElement element = Helper.getElementByXpath(driver, rp.getpropvalue("SYSTEM"), 40);
                		element.click();
                		Helper.wait(driver, 4000);
                		action.moveToElement(element).doubleClick().build().perform();
                		Helper.wait(driver, 4000);
			}

			System.out.println("after try catch block");
			Helper.wait(driver, 8000);
			driver.findElement(By.xpath(rp.getpropvalue("AddButton"))).click();
			Helper.wait(driver, 6000);

			Helper.getElementByXpath(driver, rp.getpropvalue("FirstRole"), 40).click();

			Thread.sleep(5000);

			action.sendKeys(Keys.SHIFT).build().perform();

			driver.findElements(By.xpath("//li[contains(@role,'option')]")).get(1).click();

			WebElement element = driver
					.findElement(By.xpath("//span[contains(text(),'sap.hana.xs.wdisp.admin::WebDispatcherMonitor')]"));

			js.executeScript("arguments[0].scrollIntoView(true);", element);

			element.click();

			Thread.sleep(5000);

			Helper.getElementByXpath(driver, rp.getpropvalue("RolesOkButton"), 40).click();
			Helper.wait(driver, 6000);
			Helper.getElementByXpath(driver, rp.getpropvalue("SaveRolesbutton"), 40).click();
			Helper.wait(driver, 5000);
			action.sendKeys(Keys.SHIFT).build().perform();

			// xs/lm
			util.windowHandles(driver, 1);
			Helper.wait(driver, 3000);
			driver.get("https://" + ShineUsername + "hanasrv.staging.hanavlab.ondemand.com/sap/hana/xs/lm");
			System.out.println("xs lm url: " + "https://" + ShineUsername
					+ "hanasrv.staging.hanavlab.ondemand.com/sap/hana/xs/lm");
			Helper.wait(driver, 15000);
			Helper.getElementByXpath(driver, rp.getpropvalue("DeliveryUnits"), 40).click();
			Helper.wait(driver, 5000);

			Helper.getElementByXpath(driver, rp.getpropvalue("ImportDU"), 40).click();
			Helper.wait(driver, 7000);

			driver.findElement(By.xpath(rp.getpropvalue("InputBox"))).sendKeys(filePath);
			Helper.wait(driver, 6000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ImportButton"), 40).click();
			Helper.wait(driver, 8000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ImportButton"), 40).click();
			Helper.wait(driver, 3000);

			// add one more role
			util.windowHandles(driver, 2);
			Helper.wait(driver, 3000);
			mdcOperations.refreshBrowserWindow(driver);
			Helper.wait(driver, 20000);

			for (int i = 0; i <= 10; i++) {
				int UsersElementCount = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("Users"), 20);
				if (UsersElementCount > 0) {
					break;
				}
				Helper.wait(driver, 4000);
			}

			Helper.checkElement(driver, rp.getpropvalue("Users"), 100);
			Helper.getElementByXpath(driver, rp.getpropvalue("Users"), 40).click();
			
			Helper.wait(driver, 6000);
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
			
			/*SystemElementCount = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("SYSTEM"), 20);
			if (SystemElementCount == 0) {
				Helper.getElementByXpath(driver, rp.getpropvalue("Users"), 40).click();
				Helper.wait(driver, 6000);
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
			}*/

			System.out.println("after robot class");

			Helper.wait(driver, 10000);

			System.out.println("after wait");

			systemElement = Helper.getElementByXpath(driver, rp.getpropvalue("SYSTEM"), 40);

			js.executeScript("arguments[0].scrollIntoView(true);", systemElement);

			Helper.wait(driver, 4000);


			
			try {
				System.out.println("try block");
				mdcOperations.refreshBrowserWindow(driver);
                		Helper.wait(driver, 20000);
                		element = Helper.getElementByXpath(driver, rp.getpropvalue("SYSTEM"), 40);
                		element.click();
                		Helper.wait(driver, 4000);
                		action.moveToElement(element).doubleClick().build().perform();
                		Helper.wait(driver, 4000);
			} catch (org.openqa.selenium.StaleElementReferenceException ex) {
				System.out.println("catch block");
				mdcOperations.refreshBrowserWindow(driver);
               			 Helper.wait(driver, 20000);
                		element = Helper.getElementByXpath(driver, rp.getpropvalue("SYSTEM"), 40);
                		element.click();
                		Helper.wait(driver, 4000);
                		action.moveToElement(element).doubleClick().build().perform();
                		Helper.wait(driver, 4000);
			}

			System.out.println("after try catch block");
			Helper.wait(driver, 10000);
			driver.findElement(By.xpath(rp.getpropvalue("AddButton"))).click();
			Helper.wait(driver, 6000);

			Helper.getElementByXpath(driver, rp.getpropvalue("FirstRole"), 40).click();
			Thread.sleep(5000);

			action.sendKeys(Keys.SHIFT).build().perform();

			driver.findElements(By.xpath("//li[contains(@role,'option')]")).get(1).click();

			element = driver
					.findElement(By.xpath("//span[contains(text(),'sap.hana.xs.wdisp.admin::WebDispatcherMonitor')]"));

			js.executeScript("arguments[0].scrollIntoView(true);", element);

			element.click();

			Thread.sleep(5000);

			Helper.getElementByXpath(driver, rp.getpropvalue("RolesOkButton"), 40).click();
			Helper.wait(driver, 6000);
			Helper.getElementByXpath(driver, rp.getpropvalue("SaveRolesbutton"), 40).click();
			Helper.wait(driver, 5000);
			action.sendKeys(Keys.SHIFT).build().perform();

			util.handleParentWindows(driver);
			Helper.wait(driver, 5000);
			mdcOperations.stopDatabase(driver, logger);
			Thread.sleep(2000);

			mdcOperations.startDatabase(driver, logger);
			Thread.sleep(2000);

			Helper.getElementByXpath(driver, rp.getpropvalue("SAPHanaCockpit"), 40).click();
			Helper.wait(driver, 14000);
			util.windowHandles(driver, 3);

			mdcOperations.sapHanaLogon(driver, SystemUsername, SystemPassword, logger);
			Helper.wait(driver, 8000);

			OKButton = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("SpanOK"), 15);
			if (OKButton > 0) {
				Helper.getElementByXpath(driver, rp.getpropvalue("SpanOK"), 40).click();
				Helper.wait(driver, 5000);
			}

			Helper.wait(driver, 2000);
			ContinueButton = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("ContinueButton"), 15);
			if (ContinueButton > 0) {
				Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButton"), 40).click();
			}

			Helper.wait(driver, 15000);
			// xs/admin
			driver.get("https://" + ShineUsername + "hanasrv.staging.hanavlab.ondemand.com/sap/hana/xs/admin");
			System.out.println("xs admin url: " + "https://" + ShineUsername
					+ "hanasrv.staging.hanavlab.ondemand.com/sap/hana/xs/admin");
			Helper.wait(driver, 10000);
			nav.navigateToEpmInXsAdmin(driver, logger);
			Helper.wait(driver, 2000);
			new AuthenticationMethods().SAPLogonAuthentication(driver, logger);
			Helper.wait(driver, 2000);
			driver.get("https://" + ShineUsername + "hanasrv.staging.hanavlab.ondemand.com/sap/hana/xs/admin");
			Helper.wait(driver, 2000);
			nav.navigateToLibInXsAdmin(driver, logger);
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ExtendButton"), 40).click();
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("NewPackage"), 40).click();
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ExtensionPackageName"), 40).sendKeys("testing");
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ExtensionName"), 40).sendKeys("testrole");
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("CreateExtension"), 40).click();
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("CancelExtension"), 40).click();
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("EditHTTPDestinationDetails"), 40).click();
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("AuthenticationDetails"), 40).click();
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPAssertionTicket"), 40).click();
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPID"), 40).sendKeys(username);
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPPassword"), 40).sendKeys(password);
			Helper.wait(driver, 4000);
			Helper.getElementByXpath(driver, rp.getpropvalue("SaveHTTPDestinationDetails"), 40).click();
			Helper.wait(driver, 4000);
			driver.close();

			util.handleParentWindows(driver);
			Helper.wait(driver, 5000);
			driver.findElement(By.linkText("SAP HANA Interactive Education (SHINE)")).click();
			Helper.wait(driver, 5000);

			util.windowHandles(driver, 3);
			Helper.wait(driver, 5000);

			Helper.getElementByXpath(driver, rp.getpropvalue("OK"), 40).click();
			Helper.wait(driver, 4000);

			// DataGenerator page
			Helper.getElementByXpath(driver, rp.getpropvalue("DataGenerator"), 40).click();
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.WaitAndfindElements(driver, rp.getpropvalue("DataGeneratorCheck"), 50);
			int DataGeneratorCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("DataGeneratorCheck"), 40);
			if (DataGeneratorCheckElementCount > 0) {
				Utility.logPass(logger, "Data Generator page displayed successfully");
			} else {
				Utility.logFail(logger, "Data Generator page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// PurchaseOrderWorklist page
			Helper.getElementByXpath(driver, rp.getpropvalue("PurchaseOrderWorklist"), 40).click();
			Helper.wait(driver, 2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.WaitAndfindElements(driver, rp.getpropvalue("PurchaseOrderWorklistCheck"), 50);
			int PurchaseOrderWorklistCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("PurchaseOrderWorklistCheck"), 40);
			if (PurchaseOrderWorklistCheckElementCount > 0) {
				Utility.logPass(logger, "PurchaseOrderWorklist page displayed successfully");
			} else {
				Utility.logFail(logger, "PurchaseOrderWorklist page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// SalesDashboard page
			Helper.getElementByXpath(driver, rp.getpropvalue("SalesDashboardTile"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.WaitAndfindElements(driver, rp.getpropvalue("SalesDashboardCheck"), 50);
			int SalesDashboardCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("SalesDashboardCheck"), 40);
			if (SalesDashboardCheckElementCount > 0) {
				Utility.logPass(logger, "SalesDashboard page displayed successfully");
			} else {
				Utility.logFail(logger, "SalesDashboard page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// SalesWorklist page
			Helper.getElementByXpath(driver, rp.getpropvalue("SalesWorklist"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.WaitAndfindElements(driver, rp.getpropvalue("SalesWorklistCheck"), 50);
			int SalesWorklistCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("SalesWorklistCheck"), 40);
			if (SalesWorklistCheckElementCount > 0) {
				Utility.logPass(logger, "SalesWorklist page displayed successfully");
			} else {
				Utility.logFail(logger, "SalesWorklist page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// UserCrud page
			Helper.getElementByXpath(driver, rp.getpropvalue("UserCrud"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.wait(driver, 1000);
			Helper.WaitAndfindElements(driver, rp.getpropvalue("UserCrudCheck"), 50);
			int UserCrudCheckElementCount = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("UserCrudCheck"), 40);
			if (UserCrudCheckElementCount > 0) {
				Utility.logPass(logger, "UserCrud page displayed successfully");
			} else {
				Utility.logFail(logger, "UserCrud page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// SpatialDemo page
			Helper.getElementByXpath(driver, rp.getpropvalue("SpatialDemo"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.wait(driver, 2000);
			
			int AppIdElement = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("AppId"), 40);
			if (AppIdElement > 0) {
				Helper.getElementByXpath(driver, rp.getpropvalue("AppId"), 40).sendKeys("Cvc53ZeZWMpHldjTTzSd");
				Helper.getElementByXpath(driver, rp.getpropvalue("AppCode"), 40).sendKeys("DtjnypXOBmcFWTptBUMnTQ");
				Helper.getElementByXpath(driver, rp.getpropvalue("SubmitButton"), 40).click();
				Helper.wait(driver, 2000);
			}
			Helper.WaitAndfindElements(driver, rp.getpropvalue("SpatialDemoCheck"), 50);
			int SpatialDemoCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("SpatialDemoCheck"), 40);
			if (SpatialDemoCheckElementCount > 0) {
				Utility.logPass(logger, "SpatialDemo page displayed successfully");
			} else {
				Utility.logFail(logger, "SpatialDemo page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			driver.close();

			util.handleParentWindows(driver);
			Helper.wait(driver, 5000);
			mdcOperations.stopDatabase(driver, logger);
			Thread.sleep(2000);

			mdcOperations.startDatabase(driver, logger);
			Thread.sleep(2000);
			driver.findElement(By.linkText("SAP HANA Interactive Education (SHINE)")).click();
			Helper.wait(driver, 5000);

			util.windowHandles(driver, 3);
			Helper.wait(driver, 20000);

			mdcOperations.refreshBrowserWindow(driver);
			Helper.wait(driver, 25000);

			int logonElementCount = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("SAPHanaCockpitUsername"),
					60);
			if (logonElementCount > 0) {
				mdcOperations.sapHanaLogon(driver, SystemUsername, SystemPassword, logger);
				Helper.wait(driver, 10000);
			}

			int OkButtonInPopup = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("OK"), 40);
			if (OkButtonInPopup > 0) {
				Helper.getElementByXpath(driver, rp.getpropvalue("OK"), 40).click();
				Helper.wait(driver, 4000);
			}

			// SalesDashboardMobile page
			Helper.getElementByXpath(driver, rp.getpropvalue("SalesDashboardMobile"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.wait(driver, 1000);
			Helper.WaitAndfindElements(driver, rp.getpropvalue("SalesDashboardMobileCheck"), 50);
			int SalesDashboardMobileCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("SalesDashboardMobileCheck"), 40);
			if (SalesDashboardMobileCheckElementCount > 0) {
				Utility.logPass(logger, "SalesDashboardMobile page displayed successfully");
			} else {
				Utility.logFail(logger, "SalesDashboardMobile page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// FioriLaunchpad page
			Helper.getElementByXpath(driver, rp.getpropvalue("FioriLaunchpad"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.wait(driver, 10000);
			Helper.WaitAndfindElements(driver, rp.getpropvalue("FioriLaunchpadCheck"), 50);
			int FioriLaunchpadCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("FioriLaunchpadCheck"), 40);
			if (FioriLaunchpadCheckElementCount > 0) {
				Utility.logPass(logger, "FioriLaunchpad page displayed successfully");
			} else {
				Utility.logFail(logger, "FioriLaunchpad page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// XSDataServices page
			Helper.getElementByXpath(driver, rp.getpropvalue("XSDataServices"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.wait(driver, 1000);
			Helper.WaitAndfindElements(driver, rp.getpropvalue("XSDataServicesCheck"), 50);
			int XSDataServicesCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("XSDataServicesCheck"), 40);
			if (XSDataServicesCheckElementCount > 0) {
				Utility.logPass(logger, "XSDataServices page displayed successfully");
			} else {
				Utility.logFail(logger, "XSDataServices page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// XSUnitTests page
			Helper.getElementByXpath(driver, rp.getpropvalue("XSUnitTests"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.wait(driver, 1000);
			Helper.WaitAndfindElements(driver, rp.getpropvalue("XSUnitTestsCheck"), 50);
			int XSUnitTestsCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("XSUnitTestsCheck"), 40);
			if (XSUnitTestsCheckElementCount > 0) {
				Utility.logPass(logger, "XSUnitTests page displayed successfully");
			} else {
				Utility.logFail(logger, "XSUnitTests page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// FullTextSearchWithSAP page
			Helper.getElementByXpath(driver, rp.getpropvalue("FullTextSearchWithSAP"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.wait(driver, 1000);
			Helper.WaitAndfindElements(driver, rp.getpropvalue("FullTextSearchWithSAPCheck"), 50);
			int FullTextSearchWithSAPCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("FullTextSearchWithSAPCheck"), 40);
			if (FullTextSearchWithSAPCheckElementCount > 0) {
				Utility.logPass(logger, "FullTextSearchWithSAP page displayed successfully");
			} else {
				Utility.logFail(logger, "FullTextSearchWithSAP page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 5000);

			// RuntimeJobScheduling page
			Helper.getElementByXpath(driver, rp.getpropvalue("RuntimeJobScheduling"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.wait(driver, 1000);
			Helper.WaitAndfindElements(driver, rp.getpropvalue("RuntimeJobSchedulingCheck"), 50);
			int RuntimeJobSchedulingCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("RuntimeJobSchedulingCheck"), 40);
			if (RuntimeJobSchedulingCheckElementCount > 0) {
				Utility.logPass(logger, "RuntimeJobScheduling page displayed successfully");
			} else {
				Utility.logFail(logger, "RuntimeJobScheduling page failed to display data");
			}

			driver.navigate().back();
			Helper.wait(driver, 10000);

			for (int i = 0; i <= 5; i++) {
				int Next = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("RightScroller"), 40);
				if (Next > 0) {
					driver.findElement(By.xpath(rp.getpropvalue("RightScroller"))).click();
					break;
				}
				Helper.wait(driver, 4000);
			}

			Helper.wait(driver, 5000);
			Utility.logInfo(logger, "Clicked on rightScroller/Next button");

			// EtagsAndNullValuesInXSOData page
			Helper.getElementByXpath(driver, rp.getpropvalue("EtagsAndNullValuesInXSOData"), 40).click();
			Helper.wait(driver, 5000);
			Helper.getElementByXpath(driver, rp.getpropvalue("ContinueButtonInPopUp"), 40).click();
			Helper.wait(driver, 1000);
			Helper.WaitAndfindElements(driver, rp.getpropvalue("EtagsAndNullValuesInXSODataCheck"), 50);
			int EtagsAndNullValuesInXSODataCheckElementCount = Helper.getElementsSizeByXpath(driver,
					rp.getpropvalue("EtagsAndNullValuesInXSODataCheck"), 40);
			if (EtagsAndNullValuesInXSODataCheckElementCount > 0) {
				Utility.logPass(logger, "EtagsAndNullValuesInXSOData page displayed successfully");
			} else {
				Utility.logFail(logger, "EtagsAndNullValuesInXSOData page failed to display data");
			}

			Utility.logInfo(logger, "MDC scenario executed successfully");
		}

		catch (Exception e) {
			throw e;
		}

		finally {
			util.handleParentWindows(driver);
			Helper.wait(driver, 5000);

			boolean bool = Helper.getElementByXpath(driver, rp.getpropvalue("DBDeleteButton"), 40).isDisplayed();
			if (bool == true) {
				Thread.sleep(5000);
				mdcOperations.stopDatabase(driver, logger);
				Thread.sleep(5000);
				mdcOperations.deleteDatabase(driver, ShineUsername, logger);
				Thread.sleep(2000);
			} else {
				Utility.logInfo(logger, "DB already deleted or not yet created");
			}
		}
	}

	@AfterMethod
	public void tearDownReport(ITestResult result) {

		Utility.logInfo(logger, "Test in After Method");

		if (result.getStatus() == ITestResult.FAILURE) {

			Utility.CaptureScreenshot(driver);

			logger.log(LogStatus.FAIL, logger.addScreenCapture(jenkinsScreemshot + Utility.time + ".png"));

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
