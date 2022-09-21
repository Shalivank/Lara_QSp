package com.Vtiger.Practice;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class XpathPracticeOlympicScript {

	public static void main(String[] args) throws InterruptedException {
	WebDriverManager.firefoxdriver().setup();
	WebDriver driver=new FirefoxDriver();
	driver.get("https://olympics.com/");
	Thread.sleep(4000);
	Scanner s=new Scanner(System.in);
	String name = s.next();
	WebElement add = driver.findElement(By.xpath("//ul[@class='b2p-list  featured-athletes--4']//span[text()='Eliud KIPCHOGE']/../../../../div[@class='featured-athlete__medals text-body-sm']/span[@class='result-medal result-medal--gold']"));
	System.out.println(add.getText());
	driver.close();
	
	}
}
