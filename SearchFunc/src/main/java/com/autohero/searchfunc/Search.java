package com.autohero.searchfunc;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class Search extends Base {

	/*
	 * Following is the Auto Hero Launch URL
	 */

	String baseurl = "https://www.autohero.com/de/search/";

	/*
	 * Following are elements under Auto hero Web Page
	 */

	By yearSelddl = By.cssSelector(
			"div.side___1TuKo.hidden-xs.hidden-sm div:nth-of-type(1) div:nth-of-type(3) div:nth-of-type(1) span.labelText___1_7Q2");

	By yearddl = By.cssSelector(
			"div.side___1TuKo.hidden-xs.hidden-sm div:nth-of-type(1) div:nth-of-type(3) div:nth-of-type(2) div.form-control.root___3GvTO select");

	By sortingddl = By.cssSelector("div.form-control.hidden-xs.hidden-sm.root___3GvTO");

	By highPricetxt = By.cssSelector(
			"div.root___144QZ div.form-control.hidden-xs.hidden-sm.root___3GvTO select.form-control.select___1VVkB option:nth-of-type(3)");

	By outsideClick = By.cssSelector("div.col-md-9 h1");

	By clickNextPage = By.cssSelector("div.root___19NRv ul li:nth-of-type(10) a");

	By tltRecords = By.cssSelector("div.resultsAmount___3OrV7");

	By recPerPge = By.cssSelector("div.react-use-css-8.face___2JbbR div");

	By regYears = By.cssSelector("div.item___T1IPF a ul li:nth-of-type(1)");

	By carPrices = By.cssSelector("div.item___T1IPF a div.totalPrice___3yfNv");
	
	/*
	 * Following method is to launch the browser
	 */
	
	public WebDriver browserMethod() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "/src/main/java/browserDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(baseurl);
		return driver;
	}
	
	/*
	 * Following method is to click the *Registration Date From*  Drop down
	 */

	public void registrationFrom() throws InterruptedException {

		driver.findElement(yearSelddl).click();
		waitForPageLoad();
	}

	/*
	 * Following method is to select  the Year From *Registration Date From* Drop down
	 */ 

	
	public void registrationYear() throws InterruptedException {

		driver.findElement(yearddl).click();
		Select yr = new Select(driver.findElement(yearddl));
		yr.selectByVisibleText("2015");
		waitForPageLoad();
		
	}
	
	/*
	 * Following method is to click the Sorted by Option
	 */

	public void sortingddl() throws InterruptedException {

		driver.findElement(sortingddl).click();
		waitForPageLoad();
	}
        
	/*
	 * Following method is to select the Highest Price Option from the Sorted by Option
	 */
	
	public void sortingHP() throws InterruptedException {
		driver.findElement(highPricetxt).click();
		waitForPageLoad();
	}
	
	/*
	 * Following method to extract the integer values from a given values 
	 */	
	
	static String extractInt(String str) {
		// Replacing every non-digit number
		// with a space(" ")
		str = str.replaceAll("[^\\d]", " ");

		// Remove extra spaces from the beginning
		// and the ending of the string
		str = str.trim();

		// Replace all the consecutive white
		// spaces with a single space
		str = str.replaceAll(" +", "");

		if (str.equals(""))
			return "-1";

		return str;
	}
	
	/*
	 * Following Code is to check the Registration Year of Cars
	 */
	
	
	public boolean regVerify() throws InterruptedException {	
		/*
		 * Following Code is to find the number of pages and number of clicks
		 */
		WebElement nxtPage = driver.findElement(clickNextPage);
		String totalData = driver.findElement(tltRecords).getText();
		int noofRecords = Integer.parseInt(extractInt(totalData));
		String records = driver.findElement(recPerPge).getText();
		int recordsPerPage = Integer.parseInt(records);
		int page = 0;
		int pagination = noofRecords / recordsPerPage;
		int rem = noofRecords % recordsPerPage;
		page = rem == 0 ? pagination : pagination + 1;

		for (int j = 0; j < page; j++) {		
		/*
			 * Following code is to verify registration years of Cars
			 */		
			List<WebElement> listElement = driver.findElements(regYears);
			int yearLength = 4;
			for (int i = 0; i < listElement.size(); i++) {
				String elementText = listElement.get(i).getText();
				String result = elementText.replaceAll("[^A-Za-z0-9]","");
				int output = Integer.parseInt(result.substring(result.length() - yearLength));
				System.out.println(output);
				if (output < 2015)
					return false;		
			}
			
			if (j == page - 1) {
				break;

			} else {
				nxtPage.click();
				waitForPageLoad();
			}
		}
		System.out.println("Registration of all cars are from Year 2015 onwards");
		return true;

	}

	/*
	 * Following Code is to check the Price Sorting
	 */
	
	public boolean sortVerify() throws InterruptedException {	
		/*
		 * Following Code is to find the number of pages and number of clicks
		 */
		WebElement nxtPage = driver.findElement(clickNextPage);
		String totalData = driver.findElement(tltRecords).getText();
		int noofRecords = Integer.parseInt(extractInt(totalData));
		String records = driver.findElement(recPerPge).getText();
		int recordsPerPage = Integer.parseInt(records);
		int page = 0;
		int pagination = noofRecords / recordsPerPage;
		int rem = noofRecords % recordsPerPage;
		page = rem == 0 ? pagination : pagination + 1;

		int prevPrice = Integer.MAX_VALUE;
		for (int j = 0; j < page; j++) {		
			/*
			 * Following code is to verify Sorting
			 */			
			List<WebElement> theList = driver.findElements(carPrices);
			int curPrice = 0;
			for (WebElement el : theList) {
				String data = el.getText();
				System.out.println(data);
				curPrice = Integer.parseInt(data.replaceAll("[^A-Za-z0-9]", ""));
				if (prevPrice < curPrice)
					return false;
				prevPrice = curPrice;
			}
			System.out.println("Prices of Cars are sorted in descending order");
			if (j == page - 1)
				break;
			nxtPage.click();
			waitForPageLoad();
		}
		return true;

	}

}
