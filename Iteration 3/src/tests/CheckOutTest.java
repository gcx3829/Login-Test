package tests;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class CheckOutTest {
	WebDriver driver;
	
	@Before
	public void GetToLoginPage() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(TestVar.getIndexPage());
		WebElement link = driver.findElement(By.name("loginLink"));
		//Thread.sleep(1000);
		link.click();
		//Thread.sleep(500);
		
	}
	
	@Test
	public void testSuccessfulCheckOut() throws InterruptedException{
		
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
		WebElement searchBtn = driver.findElement(By.id("Search and Check Out"));
		searchBtn.click();
		Thread.sleep(1000);
		WebElement isbnText = driver.findElement(By.id("CheckOutISBN"));
		WebElement CheckoutBtn = driver.findElement(By.id("check out"));
		isbnText.sendKeys("111-111-11-1");
		//Thread.sleep(1000);
		CheckoutBtn.click();
		Thread.sleep(1000);
		WebElement searchRes = driver.findElement(By.id("SecondMsg"));
		String Text = searchRes.getText();
		Assert.assertEquals("Book testTitle was checked out successfully", Text);
	}
	
	
	@Test
	public void testAddedIntoWaitList() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("customer2");
		Thread.sleep(1000);
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("1234");
		Thread.sleep(1000);
		//passwordText.submit();
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		Thread.sleep(1000);
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());	
		WebElement searchBtn = driver.findElement(By.id("Search and Check Out"));
		searchBtn.click();
		Thread.sleep(1000);
		WebElement isbnText = driver.findElement(By.id("CheckOutISBN"));
		WebElement CheckoutBtn = driver.findElement(By.id("check out"));
		isbnText.sendKeys("111-111-11-6");
		Thread.sleep(1000);
		CheckoutBtn.click();
		Thread.sleep(1000);
		WebElement searchRes = driver.findElement(By.id("SecondMsg"));
		String Text = searchRes.getText();
		Assert.assertEquals("You have been successfully added in to wait list of 111-111-11-6 book", Text);
	}
	
	
	@Test
	public void testAlreadyInWaitlist() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("customer3");
		Thread.sleep(1000);
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("1234");
		Thread.sleep(1000);
		//passwordText.submit();
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		Thread.sleep(1000);
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());	
		WebElement searchBtn = driver.findElement(By.id("Search and Check Out"));
		searchBtn.click();
		Thread.sleep(1000);
		WebElement isbnText = driver.findElement(By.id("CheckOutISBN"));
		WebElement CheckoutBtn = driver.findElement(By.id("check out"));
		isbnText.sendKeys("111-111-11-6");
		Thread.sleep(1000);
		CheckoutBtn.click();
		Thread.sleep(1000);
		WebElement searchRes = driver.findElement(By.id("SecondMsg"));
		String Text = searchRes.getText();
		Assert.assertEquals("You are already in the wait list of 111-111-11-6 book", Text);
	}
	
	
	@Test
	public void testBookNotInDatabase() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("customer3");
		Thread.sleep(1000);
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("1234");
		Thread.sleep(1000);
		//passwordText.submit();
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		Thread.sleep(1000);
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());	
		WebElement searchBtn = driver.findElement(By.id("Search and Check Out"));
		searchBtn.click();
		Thread.sleep(1000);
		WebElement isbnText = driver.findElement(By.id("CheckOutISBN"));
		WebElement CheckoutBtn = driver.findElement(By.id("check out"));
		isbnText.sendKeys("111-111-11-7");
		Thread.sleep(1000);
		CheckoutBtn.click();
		Thread.sleep(1000);
		WebElement searchRes = driver.findElement(By.id("SecondMsg"));
		String Text = searchRes.getText();
		Assert.assertEquals("book with ISBN 111-111-11-7 not found in database", Text);
	}
	
	
	@Test
	public void testUserAlreadyRentedThatBook() throws InterruptedException{
		
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
		WebElement searchBtn = driver.findElement(By.id("Search and Check Out"));
		searchBtn.click();
		Thread.sleep(1000);
		WebElement isbnText = driver.findElement(By.id("CheckOutISBN"));
		WebElement CheckoutBtn = driver.findElement(By.id("check out"));
		isbnText.sendKeys("111-111-11-2");
		Thread.sleep(1000);
		CheckoutBtn.click();
		Thread.sleep(1000);
		WebElement searchRes = driver.findElement(By.id("SecondMsg"));
		String Text = searchRes.getText();
		Assert.assertEquals("You already rented book Harry Potter !!! Check out failed", Text);
	}
	
	@After
	public void closePage(){
		driver.quit();
	}
}