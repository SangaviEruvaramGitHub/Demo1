package com.sap.hcp.commonfunctions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Utility {

	public static String time;

	private static String timestamp;

	public void windowHandles(WebDriver driver, int tab)

	{
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tab));

	}

	public static void enterText(WebDriver ldriver, WebElement element, String text) {

		try {
			element.sendKeys(text);
		} catch (Exception e) {

			System.out.println("*********Unable to perform sendkeys " + e.getMessage()
					+ "Trying with Actions Class movement**********");

			new Actions(ldriver).moveToElement(element).pause(2000).click().build().perform();

			System.out.println("*************** Action class Click performed");
		}

	}

	public static void openIncognitoWindow() {
		Robot rr;
		try {
			rr = new Robot();
			rr.keyPress(KeyEvent.VK_CONTROL);
			rr.keyRelease(KeyEvent.VK_CONTROL);

			rr.keyPress(KeyEvent.VK_SHIFT);
			rr.keyRelease(KeyEvent.VK_CONTROL);

			rr.keyPress(KeyEvent.VK_N);
			rr.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
			System.out.println("Error" + e.getMessage());
		}
	}

	public void pressEnterKey(WebDriver driver)
	{
		
		try {
			WebDriverWait wait=new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.alertIsPresent()).accept();
		} catch (Exception e1) {
			System.out.println("No  Alert found");
		}
		
		
		Robot robo;
		try {
			robo = new Robot();
			robo.keyPress(KeyEvent.VK_ENTER);
			System.out.println("Enter key pressed");
			robo.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("Enter key released");
		} catch (AWTException e) {
			System.out.println("Error : " + e.getMessage());
		}

	}
	
	public void clickEnterKey()

	{
		Robot robo;
		try {
			robo = new Robot();
			robo.keyPress(KeyEvent.VK_ENTER);
			System.out.println("Enter key pressed");
			robo.keyRelease(KeyEvent.VK_ENTER);
			System.out.println("Enter key released");
		} catch (AWTException e) {
			System.out.println("Error : " + e.getMessage());
		}

	}
	
	public void pageDown() throws Exception
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		Thread.sleep(5000);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		System.out.println("Page down happened");
	}

	public static void logInfo(ExtentTest logger, String msg) {

		logger.log(LogStatus.INFO, "====" + msg + "====");
		System.out.println("====" + msg + "====");
	}

	public static void logPass(ExtentTest logger, String msg) {

		logger.log(LogStatus.PASS, "====" + msg + "====");
		System.out.println("====" + msg + "====");
	}

	public static void logFail(ExtentTest logger, String msg) {

		logger.log(LogStatus.FAIL, "====" + msg + "====");
		System.out.println("====" + msg + "====");
	}

	public static void attachLogInReport(WebDriver driver, ExtentTest logger, String path) {

		try {
			logger.log(LogStatus.INFO, logger.addScreenCapture(Utility.CaptureScreenshot(driver)));
		} catch (Exception e) {

			System.out.println("Sorry Attachment Couldn't added");

			System.out.println("Exception Logs are " + e.getMessage());
		}

	}

	public static String CaptureScreenshot(WebDriver driver) {

		String pathOfScreenShot = null;
		try {
			// take the screenshot at the end of every test
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// now save the screenshot to a file some place

			// pathOfScreenShot =
			// System.getProperty("user.dir")+"\\Screenshot\\Screenshot"+getCurrentDateTime()+".png";

			time = CurrentDateTime();

			pathOfScreenShot = System.getProperty("user.dir") + "\\Screenshot\\Screenshot" + time + ".png";

			FileUtils.copyFile(scrFile, new File(pathOfScreenShot));

		} catch (Exception e) {

			System.out.println("Screenshot Failed");
		}

		return pathOfScreenShot;
	}

	public static String CurrentDateTime() {
		DateFormat format = new SimpleDateFormat("dd_MM_yyyy_hh_mm_SS");

		Date date = new Date();

		return format.format(date);
	}
	
	public void handleParentWindows(WebDriver driver) {

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		driver.switchTo().window(tabs.get(0));

		driver.manage().window().maximize();

	}
	
		public static WebDriver getEnabledDriver(WebDriver driver, WebDriver driver1, WebDriver driver2, WebDriver driver3) {
		if(driver != null) {
			System.out.println("driver is not null");
			return driver;
		}
		else if(driver1 != null) {
			System.out.println("driver1 is not null");
			return driver1;
		}
		else if(driver2 != null) {
			System.out.println("driver2 is not null");
			return driver2;
		}
		else if(driver3 != null) {
			System.out.println("driver3 is not null");
			return driver3;
		}
		else 
		{
			System.out.println("All drivers are null");
			return null;
		}
	}
}
