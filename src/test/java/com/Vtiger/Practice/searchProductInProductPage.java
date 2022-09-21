package com.Vtiger.Practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class searchProductInProductPage {

	public static void main(String[] args) throws IOException {
		WebDriver driver = null;
		//step 1: Read data from Property file
		FileInputStream fis = new FileInputStream(".\\Data\\commonData.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String URL = prop.getProperty("url");
		String UN = prop.getProperty("username");
		String PWD = prop.getProperty("password");
		String BROWSER = prop.getProperty("browser");
		
		//step 2: Read data from Excel
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
			System.out.println("invalid browser");
		}
		//step 4: Login to application
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(URL);
		driver.findElement(By.name("user_name")).sendKeys(UN);
		driver.findElement(By.name("user_password")).sendKeys(PWD);
		driver.findElement(By.id("submitButton")).click();
		
		//step 5: Click on Products link
		driver.findElement(By.linkText("Products")).click();

		//step 6: select Product name from --in dropdown
		WebElement add = driver.findElement(By.id("bas_searchfield"));
		Select s=new Select(add);
		s.selectByVisibleText("Product Name");
		
		//step 7: enter product name in search for textfield
		driver.findElement(By.xpath("//input[@class='txtBox']")).sendKeys(value);
		
		//step 8: click on search now button
		driver.findElement(By.xpath("(//input[@class='crmbutton small create'])[1]")).click();
	}

}
