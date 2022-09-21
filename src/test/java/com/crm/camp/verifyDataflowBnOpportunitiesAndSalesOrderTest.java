package com.crm.camp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class verifyDataflowBnOpportunitiesAndSalesOrderTest {

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
		String orgName = book.getSheet("Sheet1").getRow(3).getCell(2).toString();
		String oppName = book.getSheet("Sheet1").getRow(3).getCell(3).toString();
		String closeDate = book.getSheet("Sheet1").getRow(3).getCell(4).toString();
		orgName= orgName+random;

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

		//step 5 : navigate to Organization module and create organization with mandatory fields
		driver.findElement(By.xpath("//a[text()='Organizations' and @href='index.php?module=Accounts&action=index']")).click();
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		driver.findElement(By.xpath("//input[@class='detailedViewTextBox' and @name='accountname']")).sendKeys(orgName);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		driver.navigate().refresh();

		//step 6 : navigate to opportunities module and create opportunity with mandatory data
		driver.findElement(By.xpath("//a[text()='Opportunities' and @href='index.php?module=Potentials&action=index']")).click();
		driver.findElement(By.xpath("//img[@title='Create Opportunity...']")).click();
		driver.findElement(By.xpath("//input[@name='potentialname']")).sendKeys(oppName);
		driver.findElement(By.xpath("//input[@id='related_to_display']/following-sibling::img[@alt='Select']")).click();
            String parent =driver.getWindowHandle();
		Set<String> add = driver.getWindowHandles();
		for(String we:add)
		{
		 if(!parent.equals(we))
		 {
			 driver.switchTo().window(we);
		 }
		}
		driver.findElement(By.xpath("//a[text()='"+orgName+"'"));
		Alert a =driver.switchTo().alert();
		a.accept();
        driver.switchTo().window(parent);
		driver.findElement(By.xpath("//input[@name='closingdate']")).sendKeys(closeDate);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		//step 7 : navigate to Sales order and click on + icon
		driver.findElement(By.xpath("//a[text()='More']")).click();
		driver.findElement(By.xpath("//a[text()='Sales Order']")).click();
		driver.findElement(By.xpath("//img[@title='Create Sales Order...']")).click();


		//step 8 : click on add(+) icon under opportunities name field
		driver.findElement(By.xpath("//td[text()='Opportunity Name 			']/following-sibling::td[@class='dvtCellInfo']/img[@alt='Select']")).click();
		driver.switchTo().window("1");

		//step 9 : validation
	
		//steps 10 : sign out from app
		driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
	}

}
