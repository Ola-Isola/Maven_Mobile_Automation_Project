package com.pb.android.base;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;


public class TestBase {
	//C:\Maven_Mobile_Automation_Project\PB_ANDROID_AUTOMATED_REGRESSION_TEST\appium\Appium\node.exe
	//Launch appium server once during the test in the @BeforeSuite and stop it in @AfterSuite.
	//Launch app under test in each test unless test are related i.e in the @BeforeTest (Launch App) and @AfterTest (Quit driver object)
	
	
	public static String appiumNodeExePath = System.getProperty("user.dir") + "\\appium\\Appium\\node.exe";
	public static String appiumServerPath = System.getProperty("user.dir") + "\\appium\\Appium\\node_modules\\appium\\bin\\appium.js";
	public static String appiumServerLogsFilePath = System.getProperty("user.dir") + "\\appmServerLogs\\appiumserver.txt";
	static String AppiumServerConfigurations = "--no-reset --local-timezone --log"+" "+ appiumServerLogsFilePath;
	public Logger logs = null;
	public AndroidDriver<MobileElement> dr;
	
	
	
	//start appium programmatically
	public void startAppiumServer(String appiumServerPortNumber, String serverIpAddress) throws ExecuteException, IOException, InterruptedException{
		
		logs.debug("*************STARTING APPIUM SERVER WITH COMMANDLINE CLASS!!*****************");
		
		// Created object of apache CommandLine class. // It will start command prompt In background. 
		CommandLine command = new CommandLine("cmd"); 
		
		// Add different arguments In command line which requires to start appium server. 
		command.addArgument("/c"); 
		command.addArgument(appiumNodeExePath); 
		command.addArgument(appiumServerPath); 
		
		//Set Server address 
		command.addArgument("--address"); 
		command.addArgument(serverIpAddress); 
		
		//Set Port 
		command.addArgument("--port"); 
		command.addArgument(appiumServerPortNumber); 
		
		//Launch appium to no reset
		command.addArgument("--no-reset"); 
		
		//Set Generate Logs To True
		command.addArgument("--log"); 
		
		
		//Set path to store appium server log file. 
		command.addArgument(appiumServerLogsFilePath); 
		
		
		// Execute command line arguments to start appium server. 
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1); 
		executor.execute(command, resultHandler); 
		Thread.sleep(20000L);
		logs.debug("**************APPIUM SERVER STARTED SUCCESSFULLY USING COMMANDLINE CLASS!!*******************");	
		
	}
	
	
	//stop appium programmatically
	public void stopAppiumServer() throws ExecuteException, IOException, InterruptedException{
		
		System.out.println("***************STOPPING APPIUM SERVER USING COMMANDLINE CLASS***********************");
		// Add different arguments In command line which requires to stop appium server.
		CommandLine command = new CommandLine("cmd"); 
		command.addArgument("/c"); 
		command.addArgument("taskkill"); 
		command.addArgument("/F"); 
		command.addArgument("/IM"); 
		command.addArgument("node.exe");
		
		// Execute command line arguments to stop appium server. 
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler(); 
		DefaultExecutor executor = new DefaultExecutor(); 
		executor.setExitValue(1); 
		executor.execute(command, resultHandler); 
		Thread.sleep(5000L);
		System.out.println("*******************APPIUM SERVER STOPPED SUCCESSFULLY USING COMMANDLINE CLASS!!***************");
		
		
	}
	
	
	//launch app
	public void launchAppUnderTest(String deviceName, String platformVersion, String platformName,
			String appPackageName, String appActivity, String appiumServerIpAddress, String appiumServerPortNumber) throws MalformedURLException{
			
		DesiredCapabilities capabilities = new DesiredCapabilities();
		// DesiredCapabilities capabilities = DesiredCapabilities.android();
		// capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("platformVersion",platformVersion);
		capabilities.setCapability("platformName",platformName);
		// capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("appPackage", appPackageName);
		capabilities.setCapability("appActivity",appActivity);

		dr = new AndroidDriver<MobileElement>(new URL("http://"+appiumServerIpAddress+":"+appiumServerPortNumber+"/wd/hub"),
				capabilities);

		dr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
	}
	
	
	    //logs
		public void initLogs(Class<?> class1){

			ConsoleAppender console = new ConsoleAppender();
			  console.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
			  //console.setThreshold(Level.ALL);
			  console.activateOptions();
			  Logger.getRootLogger().addAppender(console);
			  FileAppender appender = new FileAppender();
			  appender.setFile(System.getProperty("user.dir") + "\\src\\main\\java\\com\\pb\\android\\testlogs\\"+class1.getSimpleName()+".txt");
			  appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
			  appender.setAppend(false);	
			  appender.activateOptions();					  
			  logs = Logger.getLogger(class1);
			  logs.setLevel(Level.DEBUG);
			  logs.addAppender(appender);
			  logs.debug("Log Initialized to be generated for test : " + class1.getSimpleName());
		  
		  //In every test class, we will create this : public static Logger logs = null; and in the beforeTest we call the method as : initLogs(this.getClass());
		}

		


		public void quitDriverObject(AndroidDriver<MobileElement> dr){
			
			dr.quit();
			logs.debug("Driver Quitted.");
		}
		
		
		
		

	
	
	//screenshots
	public void takeScreenshot(){
		
		
	}
	
	
	//reports

	public void generateReports(){
		
		
	}
	
	
}
