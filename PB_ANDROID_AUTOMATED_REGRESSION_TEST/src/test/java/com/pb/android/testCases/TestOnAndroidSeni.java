package com.pb.android.testCases;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.pb.android.base.TestBase;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;



public class TestOnAndroidSeni extends TestBase{
	
	//public Logger logs = null;
	public List<MobileElement> contactList;
	public AndroidDriver<MobileElement> dr1;
	public String getTopName = null;
	public String topName = null;
	public String startAppiumPortNumber = "4755";
	public String appiumIpAddress = "127.0.0.1";
	public String udidseni = "cf5cca54";
	
	@BeforeTest
	public void preconditions() throws ExecuteException, IOException, InterruptedException{
		
		initLogs(this.getClass());
		startAppiumServer(startAppiumPortNumber,appiumIpAddress);
		launchAppUnderTest("Gpappy", "5.0.1", "Android", "com.whatsapp", "com.whatsapp.Main",appiumIpAddress, startAppiumPortNumber,udidseni);
		
	}

	
	
	
	@Test
	public void whatsAppTest() throws InterruptedException{
		
        logs.debug("Hello How Are You?");		
	    Thread.sleep(10000L);
		contactList = dr1.findElements(By.id("com.whatsapp:id/conversations_row_contact_name"));
		logs.debug("Total List Size "+contactList.size());
		topName = contactList.get(0).getText();
		String iyawomi = "Aya mi";
		String okomi = "Hubby";
		logs.debug("Top Name on list "+topName);
		for(int i = 0; i <= contactList.size(); i++){
			topName = contactList.get(0).getText();
			logs.debug("Top Name on list "+topName);
			String contactSelected = contactList.get(i).getText();				
			logs.debug("I am clicking on contact name " + contactSelected);
			contactList.get(i).click();
			logs.debug("I have clicked on contact name " +contactSelected+ " assert if the contact opened name matches contact selected.");												
			WebElement contactFriendOpened = dr1.findElement(By.id("com.whatsapp:id/conversation_contact_name"));
			String contactOpened = contactFriendOpened.getText();
			Assert.assertEquals(contactSelected, contactOpened);
			if(iyawomi.equalsIgnoreCase(contactOpened)){
				
				//send message to my wifey here.
				dr1.findElement(By.id("com.whatsapp:id/entry")).sendKeys("I love you tori torun - This is coming from my test program.");
				WebDriverWait wait = new WebDriverWait(dr1,5);
				wait.until(ExpectedConditions.visibilityOf(dr1.findElement(By.id("com.whatsapp:id/send"))));
				WebElement sendButton = dr1.findElement(By.id("com.whatsapp:id/send"));
				TouchAction action = new TouchAction((MobileDriver)dr1);
				action.tap(sendButton).perform();
				//dr.hideKeyboard();				        	
		        logs.debug("I have sent my wife a message via my programm.");
			}else if(okomi.equalsIgnoreCase(contactOpened)){
				
				dr1.findElement(By.id("com.whatsapp:id/entry")).sendKeys("Seni you are my hero - I love you like wetin dey happen gan.");
				WebDriverWait wait = new WebDriverWait(dr1,5);
				wait.until(ExpectedConditions.visibilityOf(dr1.findElement(By.id("com.whatsapp:id/send"))));
				WebElement sendButton = dr1.findElement(By.id("com.whatsapp:id/send"));
				TouchAction action = new TouchAction((MobileDriver)dr1);
				action.tap(sendButton).perform();
				//dr.hideKeyboard();				        	
		        logs.debug("I have sent my hubby a message via my programm.");
				
			}
			logs.debug("I have asserted the text conatct displayed and will be returning to the main contact page.");
			WebElement backFromConatctList  = dr1.findElement(By.id("com.whatsapp:id/back"));
			backFromConatctList.click();
			logs.debug("I have returned to the contact list.");
			
			if(i == contactList.size()-1){					

				@SuppressWarnings("rawtypes")
				AndroidDriver android = (AndroidDriver)dr1;
				android.swipe(600, 1600, 600, 300, 3000);
				getTopName = contactList.get(0).getText();
				logs.debug("*******************if");
				i=i-i;
				if(getTopName.equalsIgnoreCase(topName)){
					
					logs.debug("I have clicked through all the lists of contacts and will be braking out of the loop.");
			        break;
				}
			}					
		}
	}
	
	
	@AfterTest
	public void quitDriverAfterTestComplete() throws ExecuteException, IOException, InterruptedException{
		
		//Thread.sleep(1000L);
		//dr1.quit();
		stopAppiumServer();
	}
	
	
	
	
	public void launchAppUnderTest(String deviceName, String platformVersion, String platformName,
			String appPackageName, String appActivity, String appiumServerIpAddress, String appiumServerPortNumber, String udid) throws MalformedURLException, InterruptedException{
			
		DesiredCapabilities capabilities1 = new DesiredCapabilities();
		capabilities1.setCapability("deviceName", deviceName);
		capabilities1.setCapability("platformVersion",platformVersion);
		capabilities1.setCapability("platformName",platformName);
		capabilities1.setCapability("appPackage", appPackageName);
		capabilities1.setCapability("appActivity",appActivity);
		capabilities1.setCapability("udid", udid);
		capabilities1.setCapability("newCommandTimeout", 600);
		dr1 = new AndroidDriver<MobileElement>(new URL("http://"+appiumServerIpAddress+":"+appiumServerPortNumber+"/wd/hub"),
				capabilities1);
		dr1.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Thread.sleep(5000L);
	}
	
	
	
	
	
	
	
}
