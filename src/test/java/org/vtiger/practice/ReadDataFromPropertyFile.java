package org.vtiger.practice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadDataFromPropertyFile {

	public static void main(String[] args) throws IOException {

		//step 1: Use File Input Stream to load the property file (FileInputStream)(copy path of file--right click/properties/copy Location after project name/put . and paste) or just navigate
		FileInputStream fis = new FileInputStream("./Data/commonData.properties");

		//step 2: Create object of properties and load the file (util package)
		Properties prop = new Properties(); 
		prop.load(fis);

		//step 3: Provide the key to read the value
		String URL = prop.getProperty("url");
		System.out.println(URL);

		String UN = prop.getProperty("username");
		System.out.println(UN);

		String PWD = prop.getProperty("password");
		System.out.println(PWD);

		String BROWSER = prop.getProperty("browser");
		System.out.println(BROWSER);
	}

}
