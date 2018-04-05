package com.sap.hcp.mainclass;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.xml.serializer.utils.Utils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import com.sap.hcp.commonfunctions.Navigate;
import com.sap.hcp.commonfunctions.ReadProperties;
import com.sap.hcp.commonfunctions.Utility;

public class HCPScenarios {

	public BrowserInitialistaion initialise1;
	public BrowserInitialistaion initialise2;
	public BrowserInitialistaion initialise3;

	WebDriver driver = null;
	WebDriver driver1 = null;
	WebDriver driver2 = null;
	WebDriver driver3 = null;

	public String currentUrl_poworklist_basic;
	public String currentUrl_poworklist_saml;
	ReadProperties rp = new ReadProperties();
	Navigate nav;
	Actions act;
	Utility util;

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

	@Parameters({ "USERNAME", "HANAPASSWORD", "PASSWORD" })
	@Test(priority = 1)
	public void editorOperation(String username, String hanapassword, String password) throws Exception {

		logger = report.startTest("Editor Operation");

		nav.navigateToEditor(driver, username, hanapassword, password, logger);

		Utility.logInfo(logger, "Login done");
	}

	@Parameters({ "XSADMINURL", "appurl", "USERNAME", "PASSWORD", "BROWSER", "Scenario_Name", "HANAPASSWORD" })
	@Test(priority = 2)
	public void executeMethod(String XsAdminUrl, String Appurl, String Username, String Password, String Browser,
			String methodName, String HanaPassword) throws Exception {

		logger = report.startTest("Execute Operation");

		Utility.logInfo(logger, "scenario name coming from POM.xml is : " + methodName);

			if (methodName.equalsIgnoreCase("PAL")) {
				Utility.logInfo(logger, "Executing PAL scenario");
				palScenario();
			} else if (methodName.equalsIgnoreCase("SERVICE")) {
				Utility.logInfo(logger, "Executing Service to Service scenario");
				serviceToSeriveScenario();
			} else if (methodName.equalsIgnoreCase("HTTP")) {
				Utility.logInfo(logger, "Executing HTTP scenario");
				httpScenario(Username, Password, HanaPassword);
			} else if (methodName.equalsIgnoreCase("HTTPS")) {
				Utility.logInfo(logger, "Executing HTTPs scenario");
				httpsScenario();
			} else if (methodName.equalsIgnoreCase("APPLICATIONFROMSCRATCH_BASIC")) {
				Utility.logInfo(logger, "Execute Basic Authentication of Application From Scratch Scenario");
				applicationFromScratch_Basic(XsAdminUrl, Appurl, Username, Password, Browser, HanaPassword);
			} else if (methodName.equalsIgnoreCase("applicationFromScratch_SAML")) {
				Utility.logInfo(logger, "Execute SAMLAuthentication of Application From Scratch Scenario");
				applicationFromScratch_SAML(XsAdminUrl, Appurl, Username, Password, Browser, HanaPassword);
			}
	}

	public void serviceToSeriveScenario() throws Exception
	{
		boolean b1 = Helper.getElementByXpath(driver, rp.getpropvalue("WorkbenchEditor"), 1800).isDisplayed();

		if (b1) {
			Utility.logInfo(logger, "SAP HANA Web-based Development Workbench: Editor opened");
		} else {
			Utility.logInfo(logger, "unable to open developement workbench / Editor ");
		}

		nav.navigateToEpm(driver, logger);

		Date date = new Date();
		DateFormat df = new SimpleDateFormat("HH_mm_ss");
		String s = "name" + df.format(date);

		StringSelection stringSelection = new StringSelection(s);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		Helper.getElementByXpath(driver, rp.getpropvalue("Services"), 30).click();
		Utility.logInfo(logger, "Click on Services");
		Thread.sleep(3000);

		WebElement elem = Helper.getElementByXpath(driver, rp.getpropvalue("EcmCreateRepositoryXsjs"), 30);
		new Actions(driver).doubleClick(elem).perform();
		Utility.logInfo(logger, "Double click on ecm_create_repository.xsjs file");
		Thread.sleep(3000);
		Utility.logInfo(logger, "repository name: " + s);

		WebElement e2 = Helper.getElementByXpath(driver, rp.getpropvalue("RepositoryName"), 30);
		new Actions(driver).doubleClick(e2).perform();
		
		e2 = Helper.getElementByXpath(driver, rp.getpropvalue("RepositoryName"), 30);
		new Actions(driver).doubleClick(e2).perform();

		//e2.clear();
		
		Thread.sleep(3000);
		Robot r = new Robot();
		Thread.sleep(10000);
		//r.setAutoDelay(5000);

		r.keyPress(KeyEvent.VK_DELETE);
		Thread.sleep(3000);
		r.keyRelease(KeyEvent.VK_DELETE);
		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_V);
		Thread.sleep(3000);
		r.keyRelease(KeyEvent.VK_V);
		Thread.sleep(3000);
		r.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(3000);

		Utility.logInfo(logger, "Repository name passed is:" + s);

		Thread.sleep(6000);
		Helper.getElementByXpath(driver, rp.getpropvalue("SaveButton"), 30).click();
		Utility.logInfo(logger, "clicked on Save button");

		Thread.sleep(4000);

		Helper.getElementByXpath(driver, rp.getpropvalue("Run"), 30).click();
		Utility.logInfo(logger, "Click on run button");

		Thread.sleep(10000);

		util.windowHandles(driver, 3);

		Thread.sleep(20000);
		int errmsg = driver.findElements(By.xpath(rp.getpropvalue("ErrorMessage"))).size();
		//Helper.getElementsByXpath(driver, rp.getpropvalue("ErrorMessage"), 90).size();
		if (errmsg > 0) {
			Utility.logInfo(logger,
					"CHECK :--Error code Message:' There exists already a configuration entry for the unique repository name'");
		}

		Thread.sleep(3000);
		int sucessmsg = Helper.getElementsByXpath(driver, rp.getpropvalue("RepositoryId"), 90).size();
		if (sucessmsg > 0) {
			boolean bool = Helper.getElementByXpath(driver, rp.getpropvalue("RepositoryId"), 90).isDisplayed();
			if(bool == true)
				Utility.logPass(logger, "CHECK :-- repositoryId is generated.Hence,scenario is successful");
			else
				Utility.logFail(logger, "CHECK :-- repositoryId is not generated.Hence,scenario is un-successful");
		}

		Thread.sleep(6000);
		Utility.logInfo(logger, "Service to service scenario executed successfully");
	}

	public void httpScenario(String username, String password, String HanaPassword) throws Exception {
		String dir = System.getProperty("user.dir");

		nav.navigateToEpm(driver, logger);

		Helper.getElementByXpath(driver, rp.getpropvalue("Ui"), 30).click();
		Utility.logInfo(logger, "Click on UI");

		Helper.getElementByXpath(driver, rp.getpropvalue("PoWorklist"), 30).click();
		Utility.logInfo(logger, "Click on poWorkList");

		WebElement element = Helper.getElementByXpath(driver, rp.getpropvalue("PoWorklistHtml"), 30);
		// Actions act = new Actions(driver);
		act.doubleClick(element).perform();
		Utility.logInfo(logger, "Double click on poWorklist.html file ");
		Thread.sleep(3000);

		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

		Helper.getElementByXpath(driver, rp.getpropvalue("Run"), 30).click();
		Utility.logInfo(logger, "Click on run button");

		WebDriverWait wait4 = new WebDriverWait(driver, 60);
		wait4.until(ExpectedConditions.numberOfWindowsToBe(4));
		Thread.sleep(5000);

		util.windowHandles(driver, 3);

		Thread.sleep(10000);

		driver.close();

		driver1 = new BrowserInitialistaion(driver).launchBrowser(
				"https://itzhanasrv.staging.hanavlab.ondemand.com/sap/hana/democontent/epm/ui/poworklist/poWorklist.html",
				"chrome");

		driver = null;

		Utility.logInfo(logger, "entered url in new window");

		BrowserInitialistaion browserInitialise = new BrowserInitialistaion(driver1);

		WebDriverWait wait = new WebDriverWait(driver1, 60);
		int countOfLogins = driver1.findElements(By.xpath(rp.getpropvalue("LogOnButton"))).size();
		Utility.logInfo(logger, "count :" + countOfLogins);

		if (countOfLogins > 0) {
			Utility.logInfo(logger, "cloud platform login");
			Helper.wait(driver1, 6000);
			browserInitialise.sapCloudPlatformLogin(driver1, username, password);
			Helper.wait(driver1, 6000);
		} else {
			Utility.logInfo(logger, "hana login");
			Helper.wait(driver1, 6000);
			browserInitialise.sapHanaLogin(driver1, username, HanaPassword);
			Helper.wait(driver1, 6000);
		}
		
		//browserInitialise.sapCloudPlatformLogin(driver1, username, password);
		Thread.sleep(10000);

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		try {
			alert.accept();
		} catch (Exception e) {

			System.out.println("No Alert found");
		}

		Robot robo = new Robot();

		robo.keyPress(KeyEvent.VK_ENTER);
		System.out.println("Enter key pressed");
		Helper.wait(driver1, 2000);
		robo.keyRelease(KeyEvent.VK_ENTER);
		System.out.println("Enter key released");

		Thread.sleep(6000);

		Helper.getElementByXpath(driver1, rp.getpropvalue("Localization"), 30).click();
		Utility.logInfo(logger, "Click on Localization button");

		Helper.getElementByXpath(driver1, rp.getpropvalue("SelectCurrencyComboBox"), 30).click();
		Utility.logInfo(logger, "Click on ComboBox");

		Helper.getElementByXpath(driver1, rp.getpropvalue("SelectINR"), 30).click();
		Utility.logInfo(logger, "Select INR from the dropdown");

		Helper.getElementByXpath(driver1, rp.getpropvalue("Ok"), 30).click();
		Utility.logInfo(logger, "Click on Ok button");
		Thread.sleep(10000);

		Helper.getElementByXpath(driver1, rp.getpropvalue("OK"), 30).click();
		Utility.logInfo(logger, "Click on Ok button");
		Thread.sleep(6000);

		Thread.sleep(4000);
		util.pressEnterKey(driver1);
		Thread.sleep(3000);

		int elem = Helper.getElementsByXpath(driver1, rp.getpropvalue("CountOfINR"), 30).size();
		Utility.logInfo(logger, "Count of elemets  :" + elem);

		if (elem > 5) {
			Utility.logPass(logger, "CHECK : Currency changed from EUR to INR");
			Thread.sleep(3000);
		} else {
			Utility.logFail(logger, "CHECK: Currency is not changed from EUR to INR");
		}

		driver1.close();

		Utility.logInfo(logger, "http scenario completed successfully");

	}

	public void httpsScenario() throws Exception {

		nav.navigateToEpm(driver, logger);

		Helper.getElementByXpath(driver, rp.getpropvalue("Ui"), 30).click();
		Utility.logInfo(logger, "Click on UI");

		Helper.getElementByXpath(driver, rp.getpropvalue("SalesDashboard"), 40).click();
		Utility.logInfo(logger, "Click on salesDashboard");

		WebElement element = Helper.getElementByXpath(driver, rp.getpropvalue("SoWorklistHtml"), 30);
		// Actions act = new Actions(driver);
		act.doubleClick(element).perform();
		Utility.logInfo(logger, "Double click on soWorklist.html file ");

		Helper.getElementByXpath(driver, rp.getpropvalue("Run"), 30).click();
		Utility.logInfo(logger, "Click on run button");

		Thread.sleep(3000);
		util.windowHandles(driver, 3);

		Thread.sleep(4000);
		Helper.getElementByXpath(driver, rp.getpropvalue("New"), 30).click();
		Utility.logInfo(logger, "Click on New button");

		Helper.getElementByXpath(driver, rp.getpropvalue("MailNotificationCheckbox"), 60).click();
		Utility.logInfo(logger, "Check the checkbox NotifyBussinessPartnetsByEmail");

		Helper.getElementByXpath(driver, rp.getpropvalue("SelectBusinessPartnerComboBox"), 30).click();
		Utility.logInfo(logger, "click on BUsiness Partner comboBox");

		Helper.getElementByXpath(driver, rp.getpropvalue("BusinessPartnerBaleda"), 30).click();
		Utility.logInfo(logger, "selected Baleda from the dropdown box");

		Helper.getElementByXpath(driver, rp.getpropvalue("SelectProductComboBox"), 30).click();
		Utility.logInfo(logger, "click on Select a Product ComboBox ");

		Helper.getElementByXpath(driver, rp.getpropvalue("ProductBasicBoosterA"), 30).click();
		Utility.logInfo(logger, "selected BasicBoosterA from the dropdown box");

		Helper.getElementByXpath(driver, rp.getpropvalue("CreateButton"), 30).click();
		Utility.logInfo(logger, "clicked on created button");

		int msg = Helper.getElementsByXpath(driver, rp.getpropvalue("SalesOrderSuccessCreation"), 30).size();
		Utility.logInfo(logger, "msg count :" + msg);

		if (msg > 0) {
			Utility.logInfo(logger, "CHECK:Data is created/inserted in the table");
			Thread.sleep(3000);
		} else {
			Utility.logInfo(logger, "CHECK:Data is not inserted sucessfully in to the table");
		}

		String str = Helper.getElementByXpath(driver, rp.getpropvalue("SalesOrderSuccessCreation"), 30).getText();
		Utility.logInfo(logger, str);
		String ordernumber = StringUtils.substringBetween(str, "Order", "Created");
		// int orderid = Integer.parseInt(ordernumber);
		Utility.logInfo(logger, "Order created successfully and the order id is" + ordernumber);

		Helper.getElementByXpath(driver, rp.getpropvalue("OK"), 30).click();
		Utility.logInfo(logger, "clicked on OK button");

		int ordercount = Helper.getElementsByXpath(driver, "//*[contains(text(),'" + ordernumber + "')]", 50).size();

		// driver.findElements(By.linkText(ordernumber)).size();
		if (ordercount > 0) {
			Utility.logInfo(logger, "CHECK : Data inserted & visible in table");
		} else {
			Utility.logInfo(logger, "CHECK : Data is not inserted & not visible in table");
		}

	}

	public void palScenario() throws Exception {
		JavascriptExecutor je = null;
		nav.navigateToEpm(driver, logger);

		WebElement element = Helper.getElementByXpath(driver, rp.getpropvalue("IndexHtml"), 30);

		act.doubleClick(element).perform();

		Helper.getElementByXpath(driver, rp.getpropvalue("Run"), 30).click();
		Utility.logInfo(logger, "Click on run button");

		util.windowHandles(driver, 3);

		Helper.getElementByXpath(driver, rp.getpropvalue("OK"), 30).click();
		Utility.logInfo(logger, "Click on OK button");

		Helper.getElementByXpath(driver, rp.getpropvalue("RightScroller"), 30).click();
		Utility.logInfo(logger, "Click on rightScroller");

		// Helper.getElementByXpath(driver, rp.getpropvalue("RightScroller"),
		// 30).click();
		// Utility.logInfo(logger,"Click on rightScroller again");

		Helper.getElementByXpath(driver, rp.getpropvalue("PalCustomerSegmentation"), 30).click();
		Utility.logInfo(logger, "Click on PAL - CustomerSegmentation tile ");

		Helper.getElementByXpath(driver, rp.getpropvalue("Continue"), 30).click();
		Utility.logInfo(logger, "Click on Continue button ");
		Thread.sleep(4000);

		Helper.getElementByXpath(driver, rp.getpropvalue("HeaderPalCustomerSegmentation"), 80).click();
		Utility.logInfo(logger, "Click on PAL - CustomerSegmentation tile ");

		Helper.getElementByXpath(driver, rp.getpropvalue("CustomerClustering"), 50).click();
		Utility.logInfo(logger, "CHECK : Runtime opened .Please refer screenshots for runtime checks ");

		//// Run time checks
		Thread.sleep(15000);
		
		Utility.CaptureScreenshot(driver);
		Thread.sleep(5000);
		logger.log(LogStatus.PASS, logger.addScreenCapture(jenkinsScreemshot + Utility.time + ".png"));
		Thread.sleep(10000);
		 
		WebElement webElement = driver.findElement(By.xpath(rp.getpropvalue("CustomerSalesDetailsTitle")));
		int size = driver.findElements(By.xpath(rp.getpropvalue("CustomerSalesDetailsTitle"))).size();
		System.out.println("size: " + size);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",webElement);
		Thread.sleep(5000);
		Utility.CaptureScreenshot(driver);
		Thread.sleep(5000);
		logger.log(LogStatus.PASS, logger.addScreenCapture(jenkinsScreemshot + Utility.time + ".png"));
		Thread.sleep(15000);
		
		webElement = driver.findElement(By.xpath(rp.getpropvalue("QuarterlySalesDetailsTitle")));
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",webElement);
		Thread.sleep(5000);
		Utility.CaptureScreenshot(driver);
		Thread.sleep(5000);
		logger.log(LogStatus.PASS, logger.addScreenCapture(jenkinsScreemshot + Utility.time + ".png"));
	}

	public void applicationFromScratch_Basic(String xsAdminUrl, String Appurl, String Username, String Password,
			String Browser, String HanaPassword) throws Exception {

		nav.navigateToEpm(driver, logger);

		Helper.getElementByXpath(driver, rp.getpropvalue("Ui"), 30).click();
		Utility.logInfo(logger, "Click on UI");

		Helper.getElementByXpath(driver, rp.getpropvalue("PoWorklist"), 30).click();
		Utility.logInfo(logger, "Click on poWorkList");

		WebElement element = Helper.getElementByXpath(driver, rp.getpropvalue("PoWorklistHtml"), 30);
		act.doubleClick(element).perform();
		Utility.logInfo(logger, "Double click on poWorklist.html file ");
		Helper.wait(driver, 3000);

		Helper.getElementByXpath(driver, rp.getpropvalue("Run"), 30).click();
		Utility.logInfo(logger, "Click on run button");
		Helper.wait(driver, 6000);

		util.windowHandles(driver, 3);
		Helper.wait(driver, 15000);

		driver1 = new BrowserInitialistaion(driver).launchBrowser(
				"https://itzhanasrv.staging.hanavlab.ondemand.com/sap/hana/democontent/epm/ui/poworklist/poWorklist.html",
				"chrome");

		driver = null;

		Utility.logInfo(logger, "entered url in new window");

		BrowserInitialistaion browserInitialise = new BrowserInitialistaion(driver1);

		WebDriverWait wait = new WebDriverWait(driver1, 60);
		Helper.wait(driver1, 15000);
		int countOfLogins = driver1.findElements(By.xpath(rp.getpropvalue("LogOnButton"))).size();
		Utility.logInfo(logger, "count :" + countOfLogins);

		if (countOfLogins > 0) {
			Utility.logInfo(logger, "cloud platform login");
			Helper.wait(driver1, 6000);
			browserInitialise.sapCloudPlatformLogin(driver1, Username, Password);
			Helper.wait(driver1, 6000);
		} else {
			Utility.logInfo(logger, "hana login");
			Helper.wait(driver1, 6000);
			browserInitialise.sapHanaLogin(driver1, Username, HanaPassword);
			Helper.wait(driver1, 6000);
		}

		Helper.wait(driver1, 10000);

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		try {
			alert.accept();
		} catch (Exception e) {

			System.out.println("No Alert found");
		}

		Robot robo = new Robot();

		robo.keyPress(KeyEvent.VK_ENTER);
		System.out.println("Enter key pressed");
		Helper.wait(driver1, 2000);
		robo.keyRelease(KeyEvent.VK_ENTER);
		System.out.println("Enter key released");

		Helper.wait(driver1, 6000);

		currentUrl_poworklist_basic = driver1.getCurrentUrl();

		driver1 = null;

		initialise2 = new BrowserInitialistaion(driver2);

		driver2 = initialise2.launchBrowser(xsAdminUrl, Browser);
		Helper.wait(driver2, 10000);
		countOfLogins =driver2.findElements(By.xpath(rp.getpropvalue("LogOnButton"))).size(); 
			
		Utility.logInfo(logger, "count :" + countOfLogins);
		if (countOfLogins > 0) {

			Helper.wait(driver2, 6000);
			initialise2.sapCloudPlatformLogin(driver2, Username, Password);
			Helper.wait(driver2, 6000);
		} else {

			Helper.wait(driver2, 6000);
			initialise2.sapHanaLogin(driver2, Username, HanaPassword);
			Helper.wait(driver2, 6000);
		}

		nav.navigateToEpmInXsAdmin(driver2, logger);

		new AuthenticationMethods().basicAuthentication(driver2, logger);

		driver2 = null;

		initialise3 = new BrowserInitialistaion(driver3);
		driver3 = initialise3.launchOnlyBrowserInIncognito(Browser);
		driver3.get(currentUrl_poworklist_basic);

		Helper.wait(driver3, 8000);
		int countOfHanaLogin2 = Helper.getElementsByXpath(driver3, rp.getpropvalue("HanaLogOnButton"), 200).size();
		if (countOfHanaLogin2 > 0) {
			Utility.logPass(logger, "Check : Hana Login window appeared for BASIC Authentication.Hence,passed");
		} else {
			Utility.logFail(logger, "Check :Hana Login window didnot appeared for BASIC Authentication .Hence,failed");
		}

	}

	public void applicationFromScratch_SAML(String xsAdminUrl, String Appurl, String Username, String Password,
			String Browser, String HanaPassword) throws Exception {

		nav.navigateToEpm(driver, logger);

		Helper.getElementByXpath(driver, rp.getpropvalue("Ui"), 30).click();
		Utility.logInfo(logger, "Click " + "on UI");

		Helper.getElementByXpath(driver, rp.getpropvalue("PoWorklist"), 30).click();
		Utility.logInfo(logger, "Click on poWorkList");

		WebElement element = Helper.getElementByXpath(driver, rp.getpropvalue("PoWorklistHtml"), 30);
		act.doubleClick(element).perform();
		Utility.logInfo(logger, "Double click on poWorklist.html file ");
		Helper.wait(driver, 5000);

		Helper.getElementByXpath(driver, rp.getpropvalue("Run"), 30).click();
		Utility.logInfo(logger, "Click on run button");
		Helper.wait(driver, 15000);

		WebDriver driver1 = new BrowserInitialistaion(driver).launchBrowser(
				"https://itzhanasrv.staging.hanavlab.ondemand.com/sap/hana/democontent/epm/ui/poworklist/poWorklist.html",
				"chrome");
		driver = null;

		Utility.logInfo(logger, "entered url in new window");

		BrowserInitialistaion browserInitialise = new BrowserInitialistaion(driver1);

		WebDriverWait wait = new WebDriverWait(driver1, 60);
		Helper.wait(driver1, 15000);
		int countOfLogins = driver1.findElements(By.xpath(rp.getpropvalue("LogOnButton"))).size();
		Utility.logInfo(logger, "count :" + countOfLogins);

		if (countOfLogins > 0) {
			Utility.logInfo(logger, "cloud platform login");
			Helper.wait(driver1, 6000);
			browserInitialise.sapCloudPlatformLogin(driver1, Username, Password);
			Helper.wait(driver1, 6000);
		} else {
			Utility.logInfo(logger, "hana login");
			Helper.wait(driver1, 6000);
			browserInitialise.sapHanaLogin(driver1, Username, HanaPassword);
			Helper.wait(driver1, 6000);
		}

		Helper.wait(driver1, 10000);

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		try {
			alert.accept();
		} catch (Exception e) {

			System.out.println("No Alert found");
		}

		Robot robo = new Robot();

		robo.keyPress(KeyEvent.VK_ENTER);
		System.out.println("Enter key pressed");
		Helper.wait(driver1, 2000);
		robo.keyRelease(KeyEvent.VK_ENTER);
		System.out.println("Enter key released");

		Helper.wait(driver1, 6000);

		currentUrl_poworklist_saml = driver1.getCurrentUrl();

		driver1 = null;

		initialise2 = new BrowserInitialistaion(driver2);

		driver2 = initialise2.launchBrowser(xsAdminUrl, Browser);
		Helper.wait(driver2, 2000);
		
		Thread.sleep(3000);
		countOfLogins = driver2.findElements(By.xpath(rp.getpropvalue("LogOnButton"))).size();
			
		Utility.logInfo(logger, "count :" + countOfLogins);
		if (countOfLogins > 0) {

			Helper.wait(driver2, 6000);
			initialise2.sapCloudPlatformLogin(driver2, Username, Password);
			Helper.wait(driver2, 6000);
		} else {

			Helper.wait(driver2, 6000);
			initialise2.sapHanaLogin(driver2, Username, HanaPassword);
			Helper.wait(driver2, 6000);
		}

		nav.navigateToEpmInXsAdmin(driver2, logger);

		new AuthenticationMethods().samlAuthentication(driver2, logger);

		driver2 = null;

		initialise3 = new BrowserInitialistaion(driver3);
		driver3 = initialise3.launchOnlyBrowserInIncognito(Browser);
		// initialise3.launchOnlyBrowser(Browser);
		driver3.get(currentUrl_poworklist_saml);

		Helper.wait(driver3, 10000);

		int noHanaLogin = Helper.getElementsByXpath(driver3, rp.getpropvalue("LogOnButton"), 200).size();
		if (noHanaLogin > 0) {
			Utility.logPass(logger,
					"Check : Sap Cloud platform Login window appeared for SAML Authentication.Hence,passed");
		} else {
			Utility.logFail(logger,
					"Check :Sap Cloud platform Login window did not  appeared for SAML Authentication .Hence,failed");
		}
	}
	/*
	 * @AfterTest public void tearDown() { driver.quit();
	 * Utility.logInfo(logger,"Execution finished"); }
	 */

	@AfterMethod
	public void tearDownReport(ITestResult result) {

		Utility.logInfo(logger, "Test in After Method");

		System.out.println("test result: " + result.getStatus());

		WebDriver driverEnabled = Utility.getEnabledDriver(driver, driver1, driver2, driver3);

		if (result.getStatus() == ITestResult.FAILURE) {

			Utility.CaptureScreenshot(driverEnabled);

			logger.log(LogStatus.FAIL, logger.addScreenCapture(jenkinsScreemshot + Utility.time + ".png"));

			Utility.logFail(logger, result.getThrowable().getMessage());
		}

		if (result.getStatus() == ITestResult.SUCCESS) {

			Utility.CaptureScreenshot(driverEnabled);

			logger.log(LogStatus.PASS, logger.addScreenCapture(jenkinsScreemshot + Utility.time + ".png"));

			Utility.logPass(logger, "Test Completed Successfully");
		}

		report.endTest(logger);

	}

	@AfterSuite
	public void tearDown() {
		report.flush();

		try {
			WindowsUtils.killByName("chromedriver.exe");

			Utility.logInfo(logger, "Some Chrome Driver Instance were running and Killed - Test Can be Started");
		} catch (Exception e3) {

			Utility.logInfo(logger, "No Chrome Driver Instance Is running- Test Can be Started");
		}

	}
}
