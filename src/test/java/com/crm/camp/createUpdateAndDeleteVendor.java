package com.crm.camp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class createUpdateAndDeleteVendor {

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
		String vendorName = book.getSheet("Sheet1").getRow(8).getCell(2).toString();
		String updatedVendorName = book.getSheet("Sheet1").getRow(8).getCell(3).toString();

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

		//step 5 : Navigate to Vendor module and Create Vendor
		driver.findElement(By.xpath("//a[text()='More']")).click();
		driver.findElement(By.xpath("//a[text()='Vendors']")).click();
		driver.findElement(By.xpath("//img[@title='Create Vendor...']")).click();
		driver.findElement(By.name("vendorname")).sendKeys(vendorName+random);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		//validate
		WebElement create = driver.findElement(By.xpath("//span[contains(text(),'Vendor')]"));
		String textCreate = create.getText();
		if (textCreate.contains("Vendor Information")) 
		{
			System.out.println("Vendor created");
		}
		else 
		{
			System.out.println("Vendor not created");
		}

		//step 6 : Update Vendor
		driver.findElement(By.xpath("//input[@title='Edit [Alt+E]']")).click();
		driver.findElement(By.name("vendorname")).clear();
		driver.findElement(By.name("vendorname")).sendKeys(updatedVendorName+random);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//validate
		WebElement update = driver.findElement(By.xpath("//span[@class='lvtHeaderText']"));
		String textUpdate = update.getText();
		if (textUpdate.contains("Vendor Information")) 
		{
			System.out.println("Vendor Updated");
		}
		else 
		{
			System.out.println("Vendor not Updated");
		}

		//step 7 : Delete Vendor
		driver.findElement(By.xpath("//input[@title='Delete [Alt+D]']")).click();
		Alert a = driver.switchTo().alert();
		a.accept();

		//validate
		WebElement delete = driver.findElement(By.xpath("//a[text()='Vendors' and @class='hdrLink']"));
		String textDelete = delete.getText();
		if (textDelete.contains("Vendors")) 
		{
			System.out.println("Vendor Deleted");
		}
		else 
		{
			System.out.println("Vendor not deleted");
		}

		//step 8 : Sign out
		driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();

		//step 9 : Close the Browser
		driver.quit();
	
	}

}
