package com.Vtiger.Practice;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class amazonXpathPractice {

	public static void main(String[] args) {
		Scanner s= new Scanner(System.in);
		String a = s.next();
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver=new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://amazon.in/");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("mobile");
		driver.findElement(By.id("nav-search-submit-button")).click();
		driver.findElement(By.xpath("//ul[@class='a-unordered-list a-nostyle a-vertical a-spacing-medium' and @aria-labelledby='p_89-title']/descendant::a[@class='a-link-normal s-navigation-item']/span[text()='"+a+"']/../div[@class='a-checkbox a-checkbox-fancy s-navigation-checkbox aok-float-left']/label")).click();
		driver.quit();
	}

}
