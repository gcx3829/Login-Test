package tests;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ReturnTest {

	WebDriver driver;
	
	@Before
	public void loginAndOpenSearch() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver","chromedriver");
		driver = new ChromeDriver();
		driver.get(TestVar.getIndexPage());
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		//Thread.sleep(500);
		
	}
	
	@Test
	public void testSuccessfulReturn() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("customer1");
		Thread.sleep(1000);
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("1234");
		Thread.sleep(1000);
		//passwordText.submit();
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		Thread.sleep(1000);
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());
		WebElement isbnText = driver.findElement(By.id("ISBN"));	
		WebElement searchBtn = driver.findElement(By.id("Return"));
		isbnText.sendKeys("111-111-11-2");
		searchBtn.click();
		Thread.sleep(1000);
		WebElement searchRes = driver.findElement(By.id("SecondMsg"));
		String Text = searchRes.getText();
		Assert.assertEquals("Book Harry Potter was returned successfully.", Text);
	}
	
	@Test
	public void testFailedReturn() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("customer1");
		Thread.sleep(1000);
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("1234");
		Thread.sleep(1000);
		//passwordText.submit();
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		Thread.sleep(1000);
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());
		WebElement isbnText = driver.findElement(By.id("ISBN"));	
		WebElement searchBtn = driver.findElement(By.id("Return"));
		isbnText.sendKeys("111-111-11-3");
		searchBtn.click();
		Thread.sleep(1000);
		WebElement searchRes = driver.findElement(By.id("SecondMsg"));
		String Text = searchRes.getText();
		Assert.assertEquals("You haven't rented a book with ISBN: 111-111-11-3 !!! Return failed", Text);
	}
	
	@After
	public void closePage(){
		driver.quit();
	}
	
	
}
