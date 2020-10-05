package com.techelevator.selenium;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class AutomatedFunctionalTests {

	private static WebDriver webDriver;
	
	/* Creating an instance of the WebDriver is time consuming
	 * since it opens up a browser window. Therefore, we do this
	 * only once at the start of the class and reuse the same
	 * browser window for all tests. */
	@BeforeClass
	public static void openWebBrowserForTesting() {
		
		String homeDir = System.getProperty("user.home"); // ~ or $HOME
		/* The ChromeDriver requires a system property with the name "webdriver.chrome.driver" that
		 * contains the directory path to the ChromeDriver executable. The following line assumes
		 * we have installed the ChromeDriver in a known location under our home directory */
		System.setProperty("webdriver.chrome.driver", homeDir+"/dev-tools/chromedriver/chromedriver");
		webDriver = new ChromeDriver();
	}

	@Before
	public void openHomePage() {
		webDriver.get("http://spacejam.com/");
	}
	
	@AfterClass
	public static void closeWebBrowser() {
		webDriver.quit();
	}
	
	/* In order for Selenium to interact with a web page, it needs to be
	 * able to locate elements within the page. It does this using the 
	 * WebDriver.findElementBy(...) and WebDriver.findElementsBy(...)
	 * methods. Both of these elements take a org.openqa.selenium.By
	 * object as an argument. The following static methods return
	 * By objects for different kinds of element queries:
	 * 
	 *  - By.className(String className) .
	 *  - By.cssSelector(String selector) 
	 *  - By.id(String id)  #
	 *  - By.linkText(String linkText)  
	 *  - By.name(String name)      name='whatever'
	 *  - By.tagName(String name)  <tag>
	 */
	
	@Test
	public void cool_links_and_hijinx() {
		// Link elements (i.e. <a> tags) can be found based on their text
		WebElement jumpStation = webDriver.findElement(By.cssSelector("body > center > table:nth-child(6) > tbody > tr:nth-child(3) > td:nth-child(3) > a"));
		jumpStation.click();

		// The getTitle() method returns the value of the page title
		assertTrue(webDriver.getTitle().equalsIgnoreCase("Jump Station"));

		// How to test Frames!
		webDriver.switchTo().frame(webDriver.findElement(By.name("main")));
		WebElement coolLinksAndHijinx = webDriver.findElement(By.xpath("/html/body/blockquote/font"));
		assertTrue(coolLinksAndHijinx.getText().startsWith("Cool links and hijinx!"));
	}
}
