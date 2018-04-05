package com.sap.hcp.commonfunctions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.sap.hcp.mainclass.HCPScenarios;

public class AuthenticationMethods 
{
	
	//static WebDriver driver;
	static ReadProperties rp=new ReadProperties();
	Utility util=new Utility();
	HCPScenarios hcp;
	
	public  void basicAuthentication(WebDriver driver,ExtentTest logger) throws Exception
	{
		
			////Basic should be Checked as Authentication Method[uncheck SAML,SAPlogon and Check form based] --outside
		hcp = new HCPScenarios();
	
		Helper.getElementByXpath(driver, rp.getpropvalue("EditSpan"), 30).click();
		Utility.logInfo(logger,"Click on Edit");
		
		String basicCheck1 =Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 30).getAttribute("aria-checked");
		
		if(basicCheck1.equalsIgnoreCase("false"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 30).click();;
			Utility.logInfo(logger,"Basic is checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault Basic is checked");
		}
		
		String formbasedCheck1 =Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30).getAttribute("aria-checked");
		if(formbasedCheck1.equalsIgnoreCase("false"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30).click();;
			Utility.logInfo(logger,"FormBased  is checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault FormBased is checked");
		}
		
		
		String samlUncheck1 =Helper.getElementByXpath(driver, rp.getpropvalue("SAMLAuthentication"), 30).getAttribute("aria-checked");
		if(samlUncheck1.equalsIgnoreCase("true"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("SAMLAuthentication"), 30).click();;
			Utility.logInfo(logger,"SAML is un-checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault SAML is unchecked");
		}
		
		String saplogonUncheck1 =Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30).getAttribute("aria-checked");
		if(saplogonUncheck1.equalsIgnoreCase("true"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30).click();;
			Utility.logInfo(logger,"SAPLogonOrAssertionTicket  is un-checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault SAPLogonOrAssertionTicket  is unchecked");
		}
		
		Helper.getElementByXpath(driver, rp.getpropvalue("SaveButton"), 30).click();;
		Utility.logInfo(logger,"Click On Save Button ");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("SpanOK"), 30).click();;
		Utility.logInfo(logger,"Click On OK ");
		

		
	//// Basic should be Checked as Authentication Method[uncheck SAML,SAPlogon and Check form based] --inside
		
		Thread.sleep(5000);
		Helper.getElementByXpath(driver, rp.getpropvalue("UiExpand"), 60).click();;
		Utility.logInfo(logger,"Expand UI");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("PoWorkListSpan"), 60).click();;
		Utility.logInfo(logger,"Click on  poworklist ");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("EditSpan"), 30).click();
		Utility.logInfo(logger,"Click on Edit");
		
		String basicCheck2 =Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 30).getAttribute("aria-checked");
		if(basicCheck2.equalsIgnoreCase("false"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 30).click();;
			Utility.logInfo(logger,"Basic is checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault Basic is checked");
		}
		
		String formbasedCheck2 =Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30).getAttribute("aria-checked");
		if(formbasedCheck2.equalsIgnoreCase("false"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30).click();;
			Utility.logInfo(logger,"FormBased  is checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault FormBased is checked");
		}
		
		
		String samlUncheck2 =Helper.getElementByXpath(driver, rp.getpropvalue("SAMLAuthentication"), 30).getAttribute("aria-checked");
		if(samlUncheck2.equalsIgnoreCase("true"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("SAMLAuthentication"), 30).click();;
			Utility.logInfo(logger,"SAML is un-checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault SAML is unchecked");
		}
		
		String saplogonUncheck2 =Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30).getAttribute("aria-checked");
		if(saplogonUncheck2.equalsIgnoreCase("true"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30).click();;
			Utility.logInfo(logger,"SAPLogonOrAssertionTicket  is un-checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault SAPLogonOrAssertionTicket  is unchecked");
		}
		
		Helper.getElementByXpath(driver, rp.getpropvalue("SaveButton"), 30).click();;
		Utility.logInfo(logger,"Click On Save Button ");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("SpanOK"), 30).click();;
		Utility.logInfo(logger,"Click On OK ");
		Thread.sleep(4000);

		
	}
	
	public void samlAuthentication(WebDriver driver,ExtentTest logger) throws Exception
	{
		
		
		
		////SAML should be checked as Authentication Method[uncheck Basic,SAPlogon and Check form based] --outside
		
		
		
		Helper.getElementByXpath(driver, rp.getpropvalue("EditSpan"), 30).click();
		Utility.logInfo(logger,"Click on Edit");
		
		String basicCheck3 =Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 30).getAttribute("aria-checked");
		if(basicCheck3.equalsIgnoreCase("true"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 30).click();;
			Utility.logInfo(logger,"Basic is unchecked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault Basic is unchecked");
		}
		
		String formbasedCheck3 =Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30).getAttribute("aria-checked");
		if(formbasedCheck3.equalsIgnoreCase("false"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30).click();;
			Utility.logInfo(logger,"FormBased  is checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault FormBased is checked");
		}
		
		
		String samlUncheck3 =Helper.getElementByXpath(driver, rp.getpropvalue("SAMLAuthentication"), 60).getAttribute("aria-checked");
		if(samlUncheck3.equalsIgnoreCase("false"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("SAMLAuthentication"), 40).click();
			Utility.logInfo(logger,"SAML is checked ");
			
			Helper.getElementByXpath(driver, rp.getpropvalue("SAMLDropDown"), 40).click();
			Utility.logInfo(logger,"Click on SAML DropDown ");
			
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPIDList"), 40).click();
			Utility.logInfo(logger,"From dropdwon of SAML select SAPID  ");
			
		}
		else
		{
			Utility.logInfo(logger,"ByDefault SAML is checked");
		}
		
		String saplogonUncheck3 =Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30).getAttribute("aria-checked");
		if(saplogonUncheck3.equalsIgnoreCase("true"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30).click();;
			Utility.logInfo(logger,"SAPLogonOrAssertionTicket  is un-checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault SAPLogonOrAssertionTicket  is unchecked");
		}
		
		Helper.getElementByXpath(driver, rp.getpropvalue("SaveButton"), 30).click();;
		Utility.logInfo(logger,"Click On Save Button ");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("SpanOK"), 30).click();;
		Utility.logInfo(logger,"Click On OK ");
		
		Thread.sleep(3000);
		
		
		
		////SAML should be checked as Authentication Method[uncheck Basic,SAPlogon and Check form based] --inside
		
		Helper.getElementByXpath(driver, rp.getpropvalue("UiExpand"), 60).click();;
		Utility.logInfo(logger,"Expand UI");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("PoWorkListSpan"), 60).click();;
		Utility.logInfo(logger,"Click On poworklist ");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("EditSpan"), 40).click();
		Utility.logInfo(logger,"Click on Edit");
		
		String basicCheck4 =Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 40).getAttribute("aria-checked");
		if(basicCheck4.equalsIgnoreCase("true"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 40).click();;
			Utility.logInfo(logger,"Basic is unchecked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault Basic is unchecked");
		}
		
		String formbasedCheck4 =Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30).getAttribute("aria-checked");
		if(formbasedCheck4.equalsIgnoreCase("false"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30).click();;
			Utility.logInfo(logger,"FormBased  is checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault FormBased is checked");
		}
		
		
		String samlUncheck4 =Helper.getElementByXpath(driver, rp.getpropvalue("SAMLAuthentication"), 30).getAttribute("aria-checked");
		if(samlUncheck4.equalsIgnoreCase("false"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("SAMLAuthentication"), 30).click();;
			Utility.logInfo(logger,"SAML is checked ");
			
			Helper.getElementByXpath(driver, rp.getpropvalue("SAMLDropDown"), 40).click();
			Utility.logInfo(logger,"Click on SAML DropDown ");
			
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPIDList"), 40).click();
			Utility.logInfo(logger,"From dropdwon of SAML select SAPID  ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault SAML is checked");
		}
		
		String saplogonUncheck4 =Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30).getAttribute("aria-checked");
		if(saplogonUncheck4.equalsIgnoreCase("true"))
		{
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30).click();
			Utility.logInfo(logger,"SAPLogonOrAssertionTicket  is un-checked ");
		}
		else
		{
			Utility.logInfo(logger,"ByDefault SAPLogonOrAssertionTicket  is unchecked");
		}
		
		Helper.getElementByXpath(driver, rp.getpropvalue("SaveButton"), 30).click();;
		Utility.logInfo(logger,"Click On Save Button ");
		
		Helper.getElementByXpath(driver, rp.getpropvalue("SpanOK"), 30).click();;
		Utility.logInfo(logger,"Click On OK ");
		
		Thread.sleep(3000);
		
		
	}
	
	public void SAPLogonAuthentication(WebDriver driver, ExtentTest logger) throws Exception {

		//// Basic should be Checked as Authentication Method[uncheck
		//// SAML,SAPlogon and Check form based] --outside
		hcp = new HCPScenarios();

		Helper.getElementByXpath(driver, rp.getpropvalue("EditSpan"), 30).click();
		Utility.logInfo(logger, "Click on Edit");
		Thread.sleep(4000);

		String SAPLogonCheck1 = Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30)
				.getAttribute("aria-checked");

		if (SAPLogonCheck1.equalsIgnoreCase("false")) {
			Helper.getElementByXpath(driver, rp.getpropvalue("SAPLogonOrAssertionTicketAuthentication"), 30).click();
			;
			Utility.logInfo(logger, "SAPLogonOrAssertion is checked ");
		} else {
			Utility.logInfo(logger, "ByDefault SAPLogonOrAssertion is checked");
		}
		
		String basicCheck1 = Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 30)
				.getAttribute("aria-checked");
		if (basicCheck1.equalsIgnoreCase("false")) {
			Helper.getElementByXpath(driver, rp.getpropvalue("BasicAuthentication"), 30).click();
			;
			Utility.logInfo(logger, "Basic is checked ");
		} else {
			Utility.logInfo(logger, "ByDefault Basic is checked");
		}

		String formbasedCheck1 = Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30)
				.getAttribute("aria-checked");
		if (formbasedCheck1.equalsIgnoreCase("false")) {
			Helper.getElementByXpath(driver, rp.getpropvalue("FormBasedAuthentication"), 30).click();
			;
			Utility.logInfo(logger, "FormBased  is checked ");
		} else {
			Utility.logInfo(logger, "ByDefault FormBased is checked");
		}
		
		Helper.getElementByXpath(driver, rp.getpropvalue("SaveButton"), 30).click();
		;
		Utility.logInfo(logger, "Click On Save Button ");

		Helper.getElementByXpath(driver, rp.getpropvalue("SpanOK"), 30).click();
		;
		Utility.logInfo(logger, "Click On OK ");
		Thread.sleep(4000);
	}
}
