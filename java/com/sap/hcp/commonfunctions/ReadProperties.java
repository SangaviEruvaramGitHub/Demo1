package com.sap.hcp.commonfunctions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebElement;

public class ReadProperties {
	WebElement element;
	public String getpropvalue(String key) throws IOException{

		String element = null;
		try {
			FileInputStream fis = new FileInputStream("./objRepository/objRepository.properties");
			Properties pro = new Properties();
			pro.load(fis);
			
			element=pro.getProperty(key);	
			

		} catch (Exception e) {
			System.out.println("Unable to find property value");
		}

		return element;
	}
}
