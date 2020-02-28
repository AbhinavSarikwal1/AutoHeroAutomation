package com.autohero.searchfunc;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Unit test for search App.
 */
public class SearchTest{
	
	Search s = new Search();
	WebDriver driver;
	
	@BeforeMethod
	public void launch() throws IOException,InterruptedException{	
		driver=s.browserMethod();
	}
	
		
	@Test(priority=1)
	public void testFirstReg() throws Exception{
		
		s.registrationFrom();
		s.registrationYear();
		s.sortingddl();
		s.sortingHP();	
		Assert.assertTrue(s.regVerify());
	
	}
	
	@Test(priority=2)
	public void testPriceSort() throws IOException, InterruptedException{
		
		s.registrationFrom();
		s.registrationYear();
		s.sortingddl();
		s.sortingHP();		
		Assert.assertTrue(s.sortVerify());
		
	}
	
	@AfterMethod
	public void logoutTest(){
		driver.close();
		
	}
}