package com.sap.hcp.commonfunctions;



import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class Helper
{
	public static void checkElement(WebDriver driver, String xpath, int time) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(time, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(Throwable.class);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		
	}
	

	public static WebElement WaitAndfindElement(WebDriver driver, String xpath_wait, int time) {
		checkElement(driver, xpath_wait, time);

		return driver.findElement(By.xpath(xpath_wait));
	}
	public static List<WebElement> WaitAndfindElements(WebDriver driver, String xpath_wait, int time) {
		checkElement(driver, xpath_wait, time);

		return driver.findElements(By.xpath(xpath_wait));
	}

	public static WebElement getElementByXpath(WebDriver driver, String xpath_wait, int time) {
		WebElement element = WaitAndfindElement(driver, xpath_wait, time);

		elementHighlight(driver, element);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
		}
		
		//WebElement element1 = WaitAndfindElement(driver, xpath_wait, time);

		return element;

	}
	
	public static List<WebElement> getElementsByXpath(WebDriver driver, String xpath_wait, int time) {
		List<WebElement> element = WaitAndfindElements(driver, xpath_wait, time);

		
		
		//WebElement element1 = WaitAndfindElement(driver, xpath_wait, time);

		return element;

	}

	public static void elementHighlight(WebDriver driver, WebElement Webelement) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		String originalStyle = Webelement.getAttribute("style");
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
				Webelement);
		try {
			Thread.sleep(100);
		} catch (Exception e) {

		}
		js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", Webelement);

	}
	
	public static String getXPathByContainsID(String id)
	{
		String xpath="//*[contains(@id,'"+id+"')]";
		
		return xpath;
	}
	
	
	
	public static String getXPathByTagContainsID(String tag,String id)
	{
		String xpath="//"+tag+"[contains(@id,'"+id+"')]";
		
		return xpath;
	}
	
	public static String getXPathByContainsClass(String id)
	{
		String xpath="//*[contains(@class,'"+id+"')]";
		
		return xpath;
	}

	
	public static String getXPathByContainsText(String text)
	{
		String xpath="//*[contains(@class,'"+text+"')]";
		
		return xpath;
	}
	
	public static void wait(WebDriver driver,int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static int getElementsSizeByXpath(WebDriver driver, String xpath, int time) {
		
		Helper.wait(driver, time);
		
		int elementSize = driver.findElements(By.xpath(xpath)).size();

		return elementSize;
	}
}
