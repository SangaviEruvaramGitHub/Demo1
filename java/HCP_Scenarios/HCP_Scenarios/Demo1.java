package HCP_Scenarios.HCP_Scenarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.sap.hcp.commonfunctions.BrowserInitialistaion;
import com.sap.hcp.commonfunctions.Utility;

public class Demo1 {
public static WebDriver driver; 
	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		BrowserInitialistaion cap = new BrowserInitialistaion(driver);
	
		//driver=cap.launchBrowser("https://itzhanasrv.staging.hanavlab.ondemand.com/sap/hana/xs/formLogin/login.html","chrome");
		driver=cap.launchOnlyBrowserInIncognito("chrome");
		driver.get("http://www.google.com");
		//Thread.sleep(60000);
		
		//Utility.openIncognitoWindow();
		//cap.sapHanaLogin(driver, "i322729", "Abcd12345");
		
		/*new Actions(driver).moveToElement(driver.findElement(By.xpath("//div[text()='SampleWidgetTemplate276201761853']"))).pause(2000)
		.click()
		.build()
		.perform();*/
		
		
		// driver.findElement(By.xpath("//div[text()='Click this section to use the menu to add widgets and change settings']")).click();
		
	}

}
