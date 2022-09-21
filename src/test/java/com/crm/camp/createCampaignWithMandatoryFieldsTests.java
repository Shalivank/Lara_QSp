package com.crm.camp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class createCampaignWithMandatoryFieldsTests {

	public static void main(String[] args) throws IOException {
		WebDriver driver = null;
		Random ran = new Random();
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
		String campaignName = book.getSheet("Sheet1").getRow(1).getCell(2).toString();
		String closeDate = book.getSheet("Sheet1").getRow(1).getCell(3).toString();

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

		//step 5 : navigate to campaign module
		driver.findElement(By.xpath("//a[text()='More']")).click();
		driver.findElement(By.xpath("//a[@name='Campaigns']")).click();


		//step 6 : click on Create campaign (+) icon
		driver.findElement(By.xpath("//img[@title='Create Campaign...']")).click();
		//step 7 : Enter the mandatory data and click on Save button
		driver.findElement(By.name("campaignname")).sendKeys(campaignName+random);
		driver.findElement(By.name("closingdate") ).sendKeys(closeDate);
		driver.findElement(By.xpath("//input[@class='crmbutton small save']")).click();
		
		//Validate
		WebElement create = driver.findElement(By.xpath("//span[contains(text(),'Campaign Information') and @class='dvHeaderText']"));
		String textCreate = create.getText();
		if (textCreate.contains("Campaign Information")) 
		{
			System.out.println("Campaign created");
		}
		else 
		{
			System.out.println("Campaign not created");
		}

		//step 8 : Sign out from app
		driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		
		//step 9 : close the browser
		driver.quit();
	}

}
