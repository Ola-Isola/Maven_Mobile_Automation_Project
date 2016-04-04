package com.pb.android.base;
import java.io.IOException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


// - See more at: http://software-testing-tutorials-automation.blogspot.co.uk/2015/12/start-stop-appium-server.html#sthash.9l7qhwsH.dpuf
// - See more at: http://software-testing-tutorials-automation.blogspot.co.uk/2015/12/start-stop-appium-server.html#sthash.9l7qhwsH.dpuf

// 3 ways of starting appium node server :

                  //Way 1 : 
// -- Set the path variable and specify path to node.js
// -- Open command prompt and give the command node_module


                 //Way 2 : (Using process class).
// -- Specify path to node.exe into a variable
// -- Specify path to appium.js into another variable.
// -- Concatenate both variables together with a space in between them : String cmd =  variable1 + " " + variable2
// -- Use the process class object p
// -- p = Runtime.getRuntime().exec(cmd); = Appium should get started.
//if(p != null){"Appium is started"}



                //Way 3 (Using commandLine class).

// -- Specify path to node.exe into a variable.
// -- Specify path to appium.js into another variable.
// -- create an object of commandLine class. CommandLine command = new CommandLine("cmd");
// -- Use object of commandLine created (command) to add various appium server arguments.
// -- See examples below.



public class Trial {
 		
	public static String appiumNodeExePath = System.getProperty("user.dir") + "\\appium\\Appium\\node.exe";
	public static String appiumServerPath = System.getProperty("user.dir") + "\\appium\\Appium\\node_modules\\appium\\bin\\appium.js";
	public static String appiumServerLogsFilePath = System.getProperty("user.dir") + "\\appiumServerLogs\\appiumserver.txt";
	static String AppiumServerConfigurations = "--no-reset --local-timezone --log"+" "+ appiumServerLogsFilePath;	
	public static Logger logs = null;
	//static Process p;
	//static String cmd = appiumNodeExePath + " " + appiumServerPath;   
	
	/*
	@BeforeTest
	public static void main(String[] args) throws ExecuteException, IOException, InterruptedException {
		// TODO Auto-generated method stub		
		//stopAppium();
		startAppiumMethod1();
		stopAppiumMethod1();
		
		//startAppiumMethod2();
		//stopAppiumMethod2();
		//startAppiumMethod3("\"" + appiumNodeExePath + "\" \"" + appiumServerPath + "\" " + AppiumServerConfigurations);
	}
	*/
	
	@BeforeTest
	public void quickTest() throws ExecuteException, IOException, InterruptedException{
		
		
		
		initLogs(this.getClass());
		startAppiumMethod1();
		
	}
	
	
	
	
	@Test
	public void initializeLogs(){
		
		logs.debug("Hello How Are You?");
		
	}
	
	
	
	@AfterTest
	public void closeTest() throws ExecuteException, InterruptedException, IOException{
		
		stopAppiumMethod1();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
				public static void startAppiumMethod1() throws ExecuteException, IOException, InterruptedException{//String command) throws InterruptedException {
					
					logs.debug("*************STARTING APPIUM SERVER WITH COMMANDLINE CLASS!!*****************");
					
					// Created object of apache CommandLine class. // It will start command prompt In background. 
					CommandLine command = new CommandLine("cmd"); 
					
					// Add different arguments In command line which requires to start appium server. 
					command.addArgument("/c"); 
					command.addArgument(appiumNodeExePath); 
					command.addArgument(appiumServerPath); 
					
					//Set Server address 
					command.addArgument("--address"); 
					command.addArgument("127.0.0.1"); 
					
					//Set Port 
					command.addArgument("--port"); 
					command.addArgument("4723"); 
					
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
					Thread.sleep(25000L);
					logs.debug("**************APPIUM SERVER STARTED SUCCESSFULLY USING COMMANDLINE CLASS!!*******************");								        
					
				}
							
			
				
				
				public static void stopAppiumMethod1() throws InterruptedException, ExecuteException, IOException{
					
					
					logs.debug("***************STOPPING APPIUM SERVER USING COMMANDLINE CLASS***********************");
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
					Thread.sleep(10000L);
					logs.debug("*******************APPIUM SERVER STOPPED SUCCESSFULLY USING COMMANDLINE CLASS!!***************");
										
			    }
				
		
				 /*
				 public static void startAppiumMethod2() throws IOException, InterruptedException { 
					
					System.out.println("STARTING APPIUM SERVER WITH PROCESS CLASS.");
					
					// Execute command string to start appium server. 
					p = Runtime.getRuntime().exec(cmd); 
					
					// Provide wait time of 10 mins to start appium server properly. 
					// If face any error(Could not start a new session...) then Increase 
					// this time to 15 or 20 mins. Thread.sleep(10000); 
					Thread.sleep(10000L);
					if (p != null) { 
						System.out.println("APPIUM SERVER STARTED SUCCESSFULLY WITH PROCESS CLASS!!."); 
						} 
					} 
				    */

                 /*
				 public static void stopAppiumMethod2() throws IOException {
					
					 System.out.println("STOPPING APPIUM SERVER USING PROCESS CLASS!!."); 
					 
					if (p != null) { 
						
						p.destroy();
						
					} 
					System.out.println("APPIUM SERVER STOPPED SUCCESFULLY USING PROCESS CLASS!!."); 
				} 
                 */
				 
				 
				 /*
				 public static void startAppiumMethod3(String command) {
					 
					 System.out.println("**************STARTING APPIUM SERVER WITH PROCESS CLASS USING DIFFERENT LOGIC!!*******************");
				        String s = null;

				        try {
				            Process p = Runtime.getRuntime().exec(command);
				            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				            // read the output from the command
				            System.out.println("Standard output of the command:\n");
				            while ((s = stdInput.readLine()) != null) {
				                System.out.println(s);
				            }

				            // read any errors from the attempted command
				            System.out.println("Standard error of the command (if any):\n");
				            while ((s = stdError.readLine()) != null) {
				                System.out.println(s);
				            }
				        } catch (IOException e) {
				            System.out.println("exception happened: ");
				            e.printStackTrace();
				        }
				        System.out.println("APPIUM SERVER STARTED SUCCESSFULLY WITH PROCESS CLASS USING DIFFERENT LOGIC!!.");
				    }
				 */
				 
				 
				public void initLogs(Class<?> class1){

					  ConsoleAppender console = new ConsoleAppender();
					  console.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
					  //console.setThreshold(Level.ALL);
					  console.activateOptions();
					  Logger.getRootLogger().addAppender(console);
					  FileAppender appender = new FileAppender();
					  appender.setFile(System.getProperty("user.dir") + "\\src\\main\\java\\com\\pb\\android\\testlogs\\"+class1.getSimpleName()+".log");
					  appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
					  appender.setAppend(false);	
					  appender.activateOptions();					  
					  logs = Logger.getLogger(class1);
					  logs.setLevel(Level.DEBUG);
					  logs.addAppender(appender);
					  logs.debug("Log Initialized to be generated for test : " + class1.getSimpleName());
				}
				 
				 
				 
				 
				 
				 
				 
				 
				 
}

