package com.sap.hcp.commonfunctions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.sap.hcp.mainclass.HCPScenarios;
import org.openqa.selenium.By;

public class BrowserInitialistaion 
{
	public String chromeDriverPath = "./browser_drivers/chromedriver.exe";
	public String chromeDriverProperty = "webdriver.chrome.driver";
	HCPScenarios hcp = new HCPScenarios();
	
	ReadProperties rp;
	public WebDriver driver;
	
	
	
	public BrowserInitialistaion(WebDriver driver)
	{
		rp = new ReadProperties();
		this.driver=driver;
	}
	
	
	public WebDriver launchBrowser(String url,String browserType) throws Exception
	
	{
		
		
		if(browserType.equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
			System.out.println("web driver launched");
			driver.manage().window().maximize();
			System.out.println("Browser maximised");
			//driver.navigate().to(url);
			driver.get(url);
			System.out.println("Url is launched");
			Thread.sleep(3000);
		}
		else if(browserType.equalsIgnoreCase("chrome"))
		{
			System.setProperty(chromeDriverProperty,chromeDriverPath );
			driver = new ChromeDriver();
			System.out.println("web driver launched");
			driver.manage().window().maximize();
			System.out.println("Browser maximised");
			driver.get(url);
			System.out.println("Url is launched");
			Thread.sleep(3000);
			
			
			
		}
		return driver;
		
	}
	
	
	public WebDriver launchOnlyBrowserInIncognito(String browserType) throws Exception
	
	{
		
		
		if(browserType.equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
			System.out.println("web driver launched");
			driver.manage().window().maximize();
			System.out.println("Browser maximised");

			Thread.sleep(3000);
		}
		else if(browserType.equalsIgnoreCase("chrome"))
		{
			System.setProperty(chromeDriverProperty,chromeDriverPath );
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			options.addArguments("start-maximized");
		
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();

			
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver=new ChromeDriver(capabilities);
			//driver.switchTo().defaultContent();
			//driver.manage().window().maximize();
			System.out.println("Browser maximised");
			
		
			Thread.sleep(3000);
			
			
		}
		return driver;
		
	}
	
	
	
	public  void sapCloudPlatformLogin(WebDriver driver,String username,String password) throws Exception
	{
		

		Thread.sleep(5000);
		int i = driver.findElements(By.xpath(rp.getpropvalue("LogOn"))).size();
			
			
		if(i>0)
		{	
		Helper.getElementByXpath(driver, rp.getpropvalue("LogOn"),60).click();
		System.out.println("Clicked on logon"); 
		}
		Thread.sleep(5000);
		
		Helper.getElementByXpath(driver, rp.getpropvalue("UserName"), 20).sendKeys(username);
		System.out.println("Username entered");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("Password"), 20).sendKeys(password);
		System.out.println("Password entered");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("LogOnButton"), 20).click();
		System.out.println("Clicked on logon button");
		Thread.sleep(6000);
		
		
		
	}
	
	public  void sapHanaLogin(WebDriver driver,String username,String hanapassword) throws Exception
	{

		
		Utility.enterText(driver, Helper.getElementByXpath(driver, rp.getpropvalue("HanaUserName"), 40), username);
		System.out.println("Username entered");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("HanaPassword"), 40).sendKeys(hanapassword);
		System.out.println("Password entered");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("HanaLogOnButton"), 40).click();
		System.out.println("Clicked on logon button");
		Thread.sleep(6000);
		
		
		
	}

}
