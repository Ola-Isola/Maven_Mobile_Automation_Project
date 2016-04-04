package com.pb.android.Utilities;

import java.util.Hashtable;

public class TestUtil {

	
	
	
	// finds if the Test suite is runnable 
		public static boolean isSuiteRunnable(Xls_Reader xls , String suiteName, String SheetName, String SuiteColName, String RunModeColName){
			boolean isExecutable=false;
			for(int i=2; i <= xls.getRowCount(SheetName); i++){
				
				String Suite = xls.getCellData(SheetName, SuiteColName, i);
				String RunMode = xls.getCellData(SheetName, RunModeColName, i);
	            if(Suite.equalsIgnoreCase(suiteName)){
	            	    if(RunMode.equalsIgnoreCase("Y")){
	            	    	isExecutable = true;
	            	    }else{
	            	    	isExecutable = false;
	            	    }
	                  }
	               }
			    xls = null;
			    return isExecutable;
	            }
		
		
		
		// Find if individual test is runnable
		 public static boolean isTestCaseRunnable(Xls_Reader xls, String testCaseName){
				boolean isExecutable=false;
				for(int i=2; i<= xls.getRowCount("TestCases") ; i++){	
					if(xls.getCellData("TestCases", "TCID", i).equalsIgnoreCase(testCaseName)){
						if(xls.getCellData("TestCases", "RUNMODE", i).equalsIgnoreCase("Y")){
							isExecutable= true;
						}else{
							isExecutable= false;
						}
					}
				}
				xls = null;
				return isExecutable;
				
			}
	
        
      
               
        //Returns Test data from excel sheet for parameterising our tests.
        public static Object[][] getData(Xls_Reader xls, String testCase)
        {   
        //This get the row number the testcase name supplied starts from.
        int testStartRowNum = 1;
        while(!xls.getCellData("Data", 0, testStartRowNum).equals(testCase))
        {
         testStartRowNum++;
        }
        
        //Total number of  rows of data for testcase: Data will start 2rows down the testcase name starts from hence +2.
        int dataStartRowNum = testStartRowNum+2;
        int rows=0;
        while(!xls.getCellData("Data",0,dataStartRowNum+rows).equals(""))   //This will iterate through the data sheet until a space is found.
        {
           rows++;
        }
       
        
        //Total Column number for testcase: Column will start 1row down the testcase name start from hence +1.
        int TotalColNum = testStartRowNum+1;
        int cols = 0;
        while(!xls.getCellData("Data",cols,TotalColNum).equals(""))
        {
          cols++;
        }
        
        Object testData[][] = new Object[rows][1];
        int index=0;
        Hashtable <String, String> table = null;


        //Extract data for the test case
        for(int rNum = dataStartRowNum; rNum < dataStartRowNum+rows; rNum++)
         {
             table = new Hashtable<String,String>();   //table gets initialised for every row.
             for(int cNum=0; cNum < cols; cNum++){
                 String key = xls.getCellData("Data", cNum,TotalColNum);
                 String value = xls.getCellData("Data" , cNum , rNum);
                
               // This will start to fill the table in a key value pair format
               table.put(key,value);       
              }
              System.out.println("");
              testData[index][0] = table;
              index++;
         }
        return testData;  //This returns data as an object array.
       }
        
        
        
        // This will read from the excel sheet and return the browser to be used in running test for each tests
        public static String WhichBrowserForMyTest(Xls_Reader xls , String TestName){
        	String Browser = null;
        	String TestCase = null;
        	for(int i = 2; i <= xls.getRowCount("TestCases"); i++){    	
        		TestCase = xls.getCellData("TestCases", "TCID", i);
        		if(TestCase.equalsIgnoreCase(TestName)){
        			Browser = xls.getCellData("TestCases", "BROWSER", i);
        		}
        		
        	}
        	return Browser;
        }


        
        
        
        
        
		
}