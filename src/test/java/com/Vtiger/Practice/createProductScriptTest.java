package com.Vtiger.Practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class createProductScriptTest {

public static void main(String[] args) throws IOException {
	WebDriver driver = null;
	//step 1: Fetch common data from Property file
	FileInputStream fis = new FileInputStream(".\\Data\\commonData.properties");
	Properties prop = new Properties();
	prop.load(fis);
	String URL = prop.getProperty("url");
	String USERNAME = prop.getProperty("username");
	String PASSWORD = prop.getProperty("password");
	String BROWSER = prop.getProperty("browser");

	//step 2: Fetch test data from Excel
	FileInputStream file = new FileInputStream(".\\Data\\ddt.xlsx");
	Workbook book = WorkbookFactory.create(file);
	String value = book.getSheet("Sheet1").getRow(5).getCell(0).getStringCellValue();
	
	//step 3: Launch the browser
	if(BROWSER.equalsIgnoreCase("Chrome"))
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}
	else if(BROWSER.equalsIgnoreCase("firefox"))
	{
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
	}
	else
	{
		System.out.println("incorrect browser");
	}
	
	//step 4: Login to application 
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.get(URL);
	driver.findElement(By.name("user_name")).sendKeys(USERNAME);
	driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
	driver.findElement(By.id("submitButton")).click();
	
	//step 5: Click on Products major tab
	driver.findElement(By.linkText("Products")).click();

	//step 6: Click on Create Products 
	driver.findElement(By.xpath("//img[@title='Create Product...']")).click();

	//step 7: Enter mandatory fields
	driver.findElement(By.xpath("//input[@name='productname']")).sendKeys(value);

	//step 8 : Click on Save
	driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		}
}

