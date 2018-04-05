package com.sap.hcp.commonfunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.sap.hcp.mainclass.HCPScenarios;

public class Navigate {
	WebDriver driver;
	BrowserInitialistaion initialised;
	ReadProperties rp = new ReadProperties();
	Utility util = new Utility();
	HCPScenarios hcp;

	public void navigateToEditor(WebDriver driver, String username, String hanapassword, String password,
			ExtentTest logger) throws Exception {
		initialised = new BrowserInitialistaion(driver);

		hcp = new HCPScenarios();

		Thread.sleep(6000);

		int popup = driver.findElements(By.xpath(rp.getpropvalue("CloseSpan"))).size();
		if (popup > 0) {
			Helper.getElementByXpath(driver, rp.getpropvalue("NewFeaturesWindowPops"), 40).click();
			Utility.logInfo(logger, "Mark on the chekbox");
			Thread.sleep(2000);
			Helper.getElementByXpath(driver, rp.getpropvalue("CloseSpan"), 40).click();
			Utility.logInfo(logger, "Click on Close");

		} else {
			System.out.println("Popup did not appeared.");
		}
		
		
		int europeRotStagingLandscape = driver.findElements(By.xpath(rp.getpropvalue("EuropeRotStaging"))).size();
		if (europeRotStagingLandscape > 0)
		{
		      Helper.getElementByXpath(driver, rp.getpropvalue("EuropeRotStaging"), 40).click();	
		}
		else
		{ 
			System.out.println("Europe (Rot)- Staging is not appeared");
			
		}

		Helper.getElementByXpath(driver, rp.getpropvalue("StandaloneSubaccounts"), 50).click();
		Utility.logInfo(logger, "Clicked on standalone subaccounts");
		
		Thread.sleep(8000);

		Helper.getElementByXpath(driver, rp.getpropvalue("Hanasrv"), 40).click();
		Utility.logInfo(logger, "Clicked on hanasrv");

		try {
			Helper.getElementByXpath(driver, rp.getpropvalue("PersistenceOrSapASE"), 40).click();
		} catch (Exception e) {

			e.printStackTrace();

			Thread.sleep(5000);

			Helper.getElementByXpath(driver, rp.getpropvalue("PersistenceOrSapASE"), 40).click();
			Utility.logInfo(logger, "Clicked on Persistence");
		}

		Helper.getElementByXpath(driver, rp.getpropvalue("DatabasesSchemas"), 25).click();
		Utility.logInfo(logger, "Clicked on Databases &  Schemas");

		Helper.getElementByXpath(driver, rp.getpropvalue("SearchField"), 25).sendKeys("itz");
		Utility.logInfo(logger, "Entered itz in search field");

		Helper.getElementByXpath(driver, rp.getpropvalue("Itz"), 30).click();
		Utility.logInfo(logger, "clicked on itz ");

		Thread.sleep(10000);
		Boolean b = driver.findElement(By.xpath("//*[contains(text(),'Links')]")).isDisplayed();
		// Helper.getElementByXpath(driver, rp.getpropvalue("Links"),
		// 40).isDisplayed();
		if (b) {
			Utility.logInfo(logger, "check passed");
		}

		Thread.sleep(12000);
		driver.findElement(By.xpath(rp.getpropvalue("SapHanaWebBasedDevelopmentWorkbench"))).click();
		// Helper.getElementByXpath(driver,
		// rp.getpropvalue("SapHanaWebBasedDevelopmentWorkbench"), 60).click();
		Utility.logInfo(logger, "clicked on Sap Hana Web-based Development Workbench ");
		// Thread.sleep(8000);

		util.windowHandles(driver, 1);
		Thread.sleep(4000);

		int sapLoginCount = driver.findElements(By.xpath(rp.getpropvalue("LogOnButton"))).size();
		Utility.logInfo(logger, "count :" + sapLoginCount);
		if (sapLoginCount > 0) {

			Thread.sleep(6000);
			initialised.sapCloudPlatformLogin(driver, username, password);
			Thread.sleep(6000);
		} else {

			Thread.sleep(6000);
			initialised.sapHanaLogin(driver, username, hanapassword);
			Thread.sleep(6000);

		}

		Helper.getElementByXpath(driver, rp.getpropvalue("Editor"), 40).click();
		Utility.logInfo(logger, "clicked on Editor ");

		util.windowHandles(driver, 2);

		boolean b1 = Helper.getElementByXpath(driver, rp.getpropvalue("WorkbenchEditor"), 1800).isDisplayed();

		if (b1) {
			Utility.logInfo(logger, "SAP HANA Web-based Development Workbench: Editor opened");
		} else {
			Utility.logInfo(logger, "unable to open developement workbench / Editor ");
		}

	}

	public void navigateToEpm(WebDriver driver, ExtentTest logger) throws Exception {
		Thread.sleep(4000);

		Helper.getElementByXpath(driver, rp.getpropvalue("Sap"), 100).click();
		Utility.logInfo(logger, "Expanded sap folder");

		Helper.getElementByXpath(driver, rp.getpropvalue("Hana"), 40).click();
		Utility.logInfo(logger, "Expanded hana folder");

		Helper.getElementByXpath(driver, rp.getpropvalue("DemoContent"), 40).click();
		Utility.logInfo(logger, "Expanded democontent folder");

		Helper.getElementByXpath(driver, rp.getpropvalue("Epm"), 40).click();
		Utility.logInfo(logger, "Expanded epm folder");
	}

	public void navigateToEpmInXsAdmin(WebDriver driver, ExtentTest logger) throws Exception {

		//// Navigate to EPM in "
		//// https://itzhanasrv.staging.hanavlab.ondemand.com/sap/hana/xs/admin/
		//// "

		Thread.sleep(3000);
		Helper.getElementByXpath(driver, rp.getpropvalue("SAPExpand"), 100).click();
		Utility.logInfo(logger, "Expand sap");

		Helper.getElementByXpath(driver, rp.getpropvalue("HANAExpand"), 60).click();
		Utility.logInfo(logger, "Expand hana");

		Helper.getElementByXpath(driver, rp.getpropvalue("DemoContentExpand"), 60).click();
		Utility.logInfo(logger, "Expand demo content");

		Helper.getElementByXpath(driver, rp.getpropvalue("EpmSpan"), 60).click();
		Utility.logInfo(logger, "Click on EPM ");

		Helper.getElementByXpath(driver, rp.getpropvalue("EpmExpand"), 60).click();
		Utility.logInfo(logger, "Expand EPM");

	}

	public void navigateToDatabaseAndSchemas(WebDriver driver, String username, String hanapassword, String password,
			ExtentTest logger) throws Exception {
		initialised = new BrowserInitialistaion(driver);

		hcp = new HCPScenarios();

		Helper.getElementByXpath(driver, rp.getpropvalue("StandaloneSubaccounts"), 60).click();
		Utility.logInfo(logger, "Clicked on standalone subaccounts");
		Thread.sleep(8000);
		
		Helper.getElementByXpath(driver, rp.getpropvalue("Hanasrv"), 40).click();
		Utility.logInfo(logger, "Clicked on hanasrv");

		try {
			Helper.getElementByXpath(driver, rp.getpropvalue("PersistenceOrSapASE"), 40).click();
		} catch (Exception e) {

			e.printStackTrace();

			Thread.sleep(5000);

			Helper.getElementByXpath(driver, rp.getpropvalue("PersistenceOrSapASE"), 40).click();
			Utility.logInfo(logger, "Clicked on Persistence");
		}

		Helper.getElementByXpath(driver, rp.getpropvalue("DatabasesSchemas"), 25).click();
		Utility.logInfo(logger, "Clicked on Databases &  Schemas");

		Thread.sleep(5000);
	}

	public void navigateToLibInXsAdmin(WebDriver driver, ExtentTest logger) throws Exception {

		//// Navigate to EPM in "
		//// https://itzhanasrv.staging.hanavlab.ondemand.com/sap/hana/xs/admin/
		//// "
		Thread.sleep(3000);
		Helper.getElementByXpath(driver, rp.getpropvalue("SAPExpand"), 100).click();
		Utility.logInfo(logger, "Expand sap");

		Helper.getElementByXpath(driver, rp.getpropvalue("HANAExpand"), 60).click();
		Utility.logInfo(logger, "Expand hana");

		Helper.getElementByXpath(driver, rp.getpropvalue("TestToolsExpand"), 60).click();
		Utility.logInfo(logger, "Expand TestTools content");

		Helper.getElementByXpath(driver, rp.getpropvalue("UnitExpand"), 60).click();
		Utility.logInfo(logger, "Expand Unit");

		Helper.getElementByXpath(driver, rp.getpropvalue("JasmineXSExpand"), 60).click();
		Utility.logInfo(logger, "Expand JasmineXS");

		Helper.getElementByXpath(driver, rp.getpropvalue("LibExpand"), 60).click();
		Utility.logInfo(logger, "Expand Lib");

		Helper.getElementByXpath(driver, rp.getpropvalue("LocalHostXshttpdest"), 60).click();
		Utility.logInfo(logger, "Click localhost:xshttpdest");
	}

}
