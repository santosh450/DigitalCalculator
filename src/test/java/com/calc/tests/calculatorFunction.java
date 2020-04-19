package com.calc.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.calc.utilities.ReadingWriteXML;

import io.github.bonigarcia.wdm.WebDriverManager;


public class calculatorFunction {

	public static WebDriver driver;

	calculatorFunction clcfunc;
	int count=0;

	@BeforeClass
	public void setUp(){

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.calculator.net/");
	}

	@Test(dataProvider="abc")
	public void validate(String val1, String val2, String oper, String Out) {
		clcfunc = new calculatorFunction();
		System.out.println(val1+" "+val2+" "+oper+" "+Out);
		clcfunc.clickOnButton(val1);
		clcfunc.clickOnButton(clcfunc.convertoperator(oper));
		clcfunc.clickOnButton(val2);
		clcfunc.getwebElelentref("=").click();
		System.out.println("OutPut: "+driver.findElement(By.id("sciOutPut")).getText());
		try {
			ReadingWriteXML.writeDataToExcel(++count, driver.findElement(By.id("sciOutPut")).getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(count+" Count value");
	}

	@AfterClass
	public void tearDown(){

		driver.close();
	}

	public void clickOnButton(String s){
		char c;
		String btn;
		for(int i=0;i<s.length();i++){
			c = s.charAt(i);
			btn = "(//table/tbody//child::div)[10]/div/span[text()='"+c+"']";
			driver.findElement(By.xpath(btn)).click();
		}
	}
	public WebElement getwebElelentref(String s){
		String btn = "(//table/tbody//child::div)[10]/div/span[text()='"+s+"']";
		return driver.findElement(By.xpath(btn));
	}

	public String convertoperator(String s){
		String newS = null;
		if(s.equals("ADD"))
			newS =  "+";
		else if(s.equals("SUB"))
			newS = "–";
		else if(s.equals("MUL"))
			newS = "×";
		else if(s.equals("DIV"))
			newS = "/";
		return newS;
	}

	@DataProvider(name = "abc")
	public Iterator<Object[]> getExcelData() {
		ArrayList<Object[]> abc = ReadingWriteXML.getDataFromExcel();
		return abc.iterator();
	}

}
