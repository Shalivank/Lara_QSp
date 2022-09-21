package com.crm.camp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class verifyDataflowBnOpportunitiesQuotesSalesorderAndInvoice {

	public static void main(String[] args) throws IOException {
		WebDriver driver = null;

		//random NO.
		Random ran = new Random(1000);
		int random = ran.nextInt();

		//step 1 : Read common data from Property file
		FileInputStream fis = new FileInputStream(".\\Data\\commonData.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String URL = prop.getProperty("url");
		String USERNAME = prop.getProperty("username");
		String PASSWORD = prop.getProperty("password");
		String BROWSER = prop.getProperty("browser");

		//step 2 : Read test data from Excel
		FileInputStream file = new FileInputStream(".\\Data\\Campaign.xlsx");
		Workbook book = WorkbookFactory.create(file);
		String orgName = book.getSheet("Sheet1").getRow(3).getCell(2).toString()+"_"+random;
		String oppName = book.getSheet("Sheet1").getRow(3).getCell(3).toString()+"_"+random;
		String closeDate = book.getSheet("Sheet1").getRow(3).getCell(4).toString();

		//step 3 : Launch the Browser
		if(BROWSER.equalsIgnoreCase("chrome"))
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
			System.out.println("Invalid browser");
		}

		//step 4 : Login to app
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(URL);
		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitbutton")).click();
		
		//step 5 : navigate to Organizations module and create Organization with mandatory data
		driver.findElement(By.xpath("//a[text()='Organizations']")).click();
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		driver.findElement(By.xpath("//input[@name='accountname']")).sendKeys(orgName);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[text()='Opportunities' and @href='index.php?module=Potentials&action=index']"))));


		//step 6 : navigate to opportunities module and create opportunity with mandatory data
		driver.findElement(By.xpath("//a[text()='Opportunities' and @href='index.php?module=Potentials&action=index']")).click();
		driver.findElement(By.xpath("//img[@title='Create Opportunity...']")).click();
		driver.findElement(By.xpath("//input[@name='potentialname']")).sendKeys(oppName);
		driver.findElement(By.xpath("//input[@id='related_to_display']/following-sibling::img[@alt='Select']")).click();
		
	}

}
