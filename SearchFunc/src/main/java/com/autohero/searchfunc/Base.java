package com.autohero.searchfunc;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ExtentManager;

public class Base {

	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;	
	public static WebDriver driver;
	public Wait<WebDriver> wait;

	public void waitForPageLoad() throws InterruptedException {
		   Thread.sleep(8000);
	}
}
