package com.sap.hcp.commonfunctions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentTest;
import com.sap.hcp.mainclass.HCPScenarios;

public class MDCOperations {
	WebDriver driver;
	BrowserInitialistaion initialised;
	ReadProperties rp = new ReadProperties();
	Utility util = new Utility();
	HCPScenarios hcp;

	public void createDatabase(WebDriver driver, String ShineUsername, String ShinePassword, String SystemUsername,
			String SystemPassword, ExtentTest logger) throws IOException {
		initialised = new BrowserInitialistaion(driver);

		hcp = new HCPScenarios();

		Helper.getElementByXpath(driver, rp.getpropvalue("NewButton"), 150).click();
		Utility.logInfo(logger, "Clicked on New button to create database");

		Helper.getElementByXpath(driver, rp.getpropvalue("DatabaseID"), 40).sendKeys(ShineUsername);
		Utility.logInfo(logger, "Entered SHINE username");

		Helper.getElementByXpath(driver, rp.getpropvalue("DatabaseSystemDropdown"), 40).click();
		Helper.getElementByXpath(driver, rp.getpropvalue("HanaMdcTrialOption"), 40).click();
		Utility.logInfo(logger, "HANA MDC Trial option is selected from the dropdown");

		Helper.getElementByXpath(driver, rp.getpropvalue("SystemUserPassword"), 40).sendKeys(ShinePassword);
		Utility.logInfo(logger, "Entered SHINE password");

		Helper.getElementByXpath(driver, rp.getpropvalue("CofirmSystemUserPassword"), 40).sendKeys(ShinePassword);
		Utility.logInfo(logger, "Confirm SHINE password");

		Helper.getElementByXpath(driver, rp.getpropvalue("ShineSwitch"), 40).click();
		Utility.logInfo(logger, "Configure user for SHINE - ON ");

		Helper.getElementByXpath(driver, rp.getpropvalue("ShineUsername"), 40).sendKeys(SystemUsername);
		Utility.logInfo(logger, "Entered SYSTEM username");

		Helper.getElementByXpath(driver, rp.getpropvalue("ShinePassword"), 40).sendKeys(SystemPassword);
		Utility.logInfo(logger, "Entered SYSTEM password");

		Helper.getElementByXpath(driver, rp.getpropvalue("ShineCofirmPassword"), 40).sendKeys(SystemPassword);
		Utility.logInfo(logger, "Confirm SYSTEM password");

		Helper.wait(driver, 4000);

		Helper.getElementByXpath(driver, rp.getpropvalue("SaveDB"), 40).click();
		Utility.logInfo(logger, "Clicked on save button");

		Helper.wait(driver, 5000);
	}

	public void navigateToOverviewAndCheckDBStatus(WebDriver driver, ExtentTest logger) throws IOException {

		Helper.getElementByXpath(driver, rp.getpropvalue("Overview"), 40).click();
		Utility.logInfo(logger, "Navigate to Overview");

		Helper.wait(driver, 10000);

		for (int i = 0; i <= 20; i++) {

			refreshBrowserWindow(driver);

			Helper.wait(driver, 10000);

			int count = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("DBStarted"), 50);

			if (count > 0) {
				Utility.logPass(logger, "DB started");
				
				//once db is started cockpit and shine links are enabled.. so refreshing again to enable that links
				refreshBrowserWindow(driver);

				Helper.wait(driver, 10000);
				
				break;
			}
		}
	}

	public void stopDatabase(WebDriver driver, ExtentTest logger) throws IOException {
		refreshBrowserWindow(driver);
		Helper.wait(driver, 5000);
		int count = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("DBStarted"), 50);

		if (count > 0) {
			Helper.getElementByXpath(driver, rp.getpropvalue("DBStopButton"), 40).click();
			Helper.wait(driver, 4000);
			Helper.getElementByXpath(driver, rp.getpropvalue("DeleteDBOKButton"), 40).click();
		}
		refreshBrowserWindow(driver);
		Helper.wait(driver, 15000);

		for (int i = 1; i <= 4; i++) {
			if (Helper.getElementsSizeByXpath(driver, rp.getpropvalue("DBStopped"), 50) > 0) {
				Utility.logPass(logger, "DB stopped successfully");
				break;
			} else {
				refreshBrowserWindow(driver);
				Helper.wait(driver, 20000);
			}
		}

	}

	public void startDatabase(WebDriver driver, ExtentTest logger) throws IOException {
		refreshBrowserWindow(driver);
		Helper.wait(driver, 20000);
		int count = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("DBStopped"), 50);

		if (count > 0) {
			Helper.getElementByXpath(driver, rp.getpropvalue("DBStartButton"), 40).click();
		}

		refreshBrowserWindow(driver);

		Helper.wait(driver, 30000);

		for (int i = 0; i <= 8; i++) {

			int count1 = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("DBStarted"), 50);

			if (count1 > 0) {
				refreshBrowserWindow(driver);
				Helper.wait(driver, 20000);
				Utility.logPass(logger, "DB started successfully");
				break;
			} else {
				refreshBrowserWindow(driver);
				Helper.wait(driver, 20000);
			}
		}
	}

	public void deleteDatabase(WebDriver driver, String ShineUsername, ExtentTest logger) throws IOException {
		refreshBrowserWindow(driver);
		Helper.wait(driver, 20000);

		Helper.getElementByXpath(driver, rp.getpropvalue("DBDeleteButton"), 40).click();
		Helper.wait(driver, 5000);
		Helper.getElementByXpath(driver, rp.getpropvalue("DeleteDBOKButton"), 40).click();
		Helper.wait(driver, 10000);
		refreshBrowserWindow(driver);
		Helper.wait(driver, 10000);
		refreshBrowserWindow(driver);
		Helper.wait(driver, 20000);
		
		int dbAndSchemasElement = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("DatabasesSchemas"), 40);
		
		if (dbAndSchemasElement == 0) {
			try {
				Helper.getElementByXpath(driver, rp.getpropvalue("PersistenceOrSapASE"), 40).click();
			} catch (Exception e) {

				e.printStackTrace();

				Helper.wait(driver, 5000);

				Helper.getElementByXpath(driver, rp.getpropvalue("PersistenceOrSapASE"), 40).click();
				Utility.logInfo(logger, "Clicked on Persistence");
			}
		}
	

		Helper.getElementByXpath(driver, rp.getpropvalue("DatabasesSchemas"), 25).click();
		Utility.logInfo(logger, "Clicked on Databases &  Schemas");
		Helper.wait(driver, 4000);
		refreshBrowserWindow(driver);
		Helper.wait(driver, 20000);
		refreshBrowserWindow(driver);
		Helper.wait(driver, 20000);
		Helper.getElementByXpath(driver, rp.getpropvalue("SearchField"), 40).sendKeys(ShineUsername);
		util.clickEnterKey();

		int count = Helper.getElementsSizeByXpath(driver, rp.getpropvalue("NoDatabasesAndSchemas"), 50);
		if (count > 0) {
			Utility.logPass(logger, "DB deleted successfully");
		} else {
			Utility.logFail(logger, "DB with name " + ShineUsername + " is not deleted");
		}
	}

	public void sapHanaLogon(WebDriver driver, String SystemUsername, String SystemPassword, ExtentTest logger)
			throws IOException {
		Helper.getElementByXpath(driver, rp.getpropvalue("SAPHanaCockpitUsername"), 40).sendKeys(SystemUsername);
		Helper.getElementByXpath(driver, rp.getpropvalue("SAPHanaCockpitPassword"), 40).sendKeys(SystemPassword);
		Helper.getElementByXpath(driver, rp.getpropvalue("SAPHanaCockpitLogon"), 40).click();
		Helper.wait(driver, 10000);
		Utility.logInfo(logger, "Successfully logged in");
	}

	public void sapCloudPlatformLoginEnterOnlyPassword(WebDriver driver, String password) throws Exception {
		int i = Helper.getElementsByXpath(driver, rp.getpropvalue("LogOn"), 100).size();
		if (i > 0) {

			Helper.getElementByXpath(driver, rp.getpropvalue("Password"), 20).sendKeys(password);
			System.out.println("Password entered");

			Helper.getElementByXpath(driver, rp.getpropvalue("LogOnButton"), 20).click();
			System.out.println("Clicked on logon button");
			Thread.sleep(6000);

		} else {
			System.out.println("Logon page is not available");
		}
	}

	public void refreshBrowserWindow(WebDriver driver) {
		driver.navigate().refresh();

		Helper.wait(driver, 15000);
	}
	
}
