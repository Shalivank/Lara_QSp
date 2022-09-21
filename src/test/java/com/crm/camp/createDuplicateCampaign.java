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

public class createDuplicateCampaign {
	public static void main(String[] args) throws IOException {
		WebDriver driver = null;

		//random NO.
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
		String camName = book.getSheet("Sheet1").getRow(5).getCell(2).toString();

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

		//step 5 : Navigate to Campaign module and Create Campaign 
		driver.findElement(By.xpath("//a[text()='More']")).click();
		driver.findElement(By.xpath("//a[text()='Campaigns' and @id='more']")).click();
		driver.findElement(By.xpath("//img[@title='Create Campaign...']")).click();
		driver.findElement(By.xpath("//input[@class='detailedViewTextBox'and @name='campaignname']")).sendKeys(camName+random);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		//step 6 : click on duplicate button
		driver.findElement(By.xpath("//input[@title='Duplicate [Alt+U]']")).click();

		//step 7 : validation
		WebElement val = driver.findElement(By.xpath("//span[contains(text(),'Duplicating')]"));
		String text = val.getText();

		if(text.contains("Duplicating"))
		{
			System.out.println("Tc is pass");
		}
		else
		{
			System.out.println("Tc is fail");
		}

		//step 8 : save
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		//step 9 : sign out
		driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();

		//step 10 : Close the browser
		driver.quit();

	}
}
