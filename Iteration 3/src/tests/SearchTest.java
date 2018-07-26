package tests;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchTest {
	
	WebDriver driver;
	
	@Before
	public void loginAndOpenSearch() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver","chromedriver");
		driver = new ChromeDriver();
		driver.get(TestVar.getIndexPage()); //get index link
		WebElement link = driver.findElement(By.name("loginLink")); // go to login page
		link.click();
		
	}
	
	@Test
	public void testSearchPatronExists() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("patron"); //put name as abc
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("patron"); //put password as abc
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click(); //sumbit username and password
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());	
		WebElement searchBtn = driver.findElement(By.id("Search and Check Out"));
		searchBtn.click(); //go to search and checkout page
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		WebElement searchBtn2 = driver.findElement(By.name("submit"));
		isbnText.sendKeys("000-000-00-0"); //get set book that exists
		searchBtn2.click();
		
		WebElement searchRes = driver.findElement(By.id("SearchResults"));
		String tableText = searchRes.getText(); //get table results
		Assert.assertNotEquals("NULL", tableText);
	}
	
	@Test
	public void testSearchPatronEmpty() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("patron"); //put name as abc
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("patron"); //put password as abc
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click(); //sumbit username and password
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());	
		WebElement searchBtn = driver.findElement(By.id("Search and Check Out"));
		searchBtn.click(); //go to search and checkout page
		WebElement searchBtn2 = driver.findElement(By.name("submit"));
		searchBtn2.click();
		
		WebElement searchRes = driver.findElement(By.id("SearchResults"));
		String tableText = searchRes.getText(); //get table results
		Assert.assertNotEquals("NULL", tableText);
	}
	
	
	@Test
	public void testSearchPatronNotExists() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("patron"); //put name as abc
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("patron"); //put password as abc
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());	
		WebElement searchBtn = driver.findElement(By.id("Search and Check Out"));
		searchBtn.click(); //go to search and checkout page
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		WebElement searchBtn2 = driver.findElement(By.name("submit"));
		isbnText.sendKeys("000-000-00-1"); // get book that doesn't exist
		searchBtn2.click();
		
		WebElement searchRes = driver.findElement(By.id("SearchResults"));
		String tableText = searchRes.getText(); //get table results
		Assert.assertNotEquals("NULL", tableText);
	}
	
	
	@Test
	public void testSearchStaffExists() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("admin"); //put name as abcd
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("admin"); //put password as abcd
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click(); //get to home page
		Assert.assertEquals("Staff Welcome Page", driver.getTitle());	
		
		WebElement searchBtn = driver.findElement(By.id("search"));
		searchBtn.click(); // go to search page
		
		WebElement invID = driver.findElement(By.name("InventoryID"));
		WebElement searchBtn2 = driver.findElement(By.id("search"));
		invID.sendKeys("0"); // get book that exist
		searchBtn2.click();
		
		WebElement searchRes = driver.findElement(By.id("SearchResults"));
		String tableText = searchRes.getText();
		Assert.assertNotEquals("NULL", tableText);
	}
	
	@Test
	public void testSearchStaffNotExists() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("admin"); //put name as abcd
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("admin"); //put password as abcd
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click(); //get to home page
		Assert.assertEquals("Staff Welcome Page", driver.getTitle());	
		
		WebElement searchBtn = driver.findElement(By.id("search"));
		searchBtn.click(); // go to search page
		
		WebElement invID = driver.findElement(By.name("InventoryID"));
		WebElement searchBtn2 = driver.findElement(By.id("search"));
		invID.sendKeys("999"); //give inventory ID that doesn't exist
		searchBtn2.click();
		
		WebElement searchRes = driver.findElement(By.id("SearchResults"));
		String tableText = searchRes.getText();
		Assert.assertNotEquals("NULL", tableText);
	}
	/*
	*/
	@After
	public void closePage(){
		driver.quit();
	}
}