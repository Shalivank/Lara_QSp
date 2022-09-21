package com.Vtiger.Practice;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class selectLeadAndDelete {

	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://rmgtestingserver:8888/");
		driver.findElement(By.xpath("//input[@name='user_name']")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@name='user_password']")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();
		driver.findElement(By.xpath("//td[@class='tabUnSelected']/a[text()='Leads']")).click();
		driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr/td/a[text()='Ram']/../../td[1]/input")).click();
		driver.findElement(By.xpath("//table[@class='lvt small']/tbody/tr/td/a[text()='Ram']/../../td[10]/a[text()='del']")).click();
		
	Alert a=driver.switchTo().alert();
	a.accept();
	}

}
