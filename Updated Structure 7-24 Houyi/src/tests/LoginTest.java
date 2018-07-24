package tests;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

	WebDriver driver;
	
	@Before
	public  void openLibraryIndexPage() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/Updated_Structure_7-19/index.jsp");
		Assert.assertEquals("Online Library Management System", driver.getTitle());
	}
	
	
	@Test
	public void testLoginPageOpen() throws InterruptedException{
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		////Thread.sleep(500);
		Assert.assertEquals("Login page", driver.getTitle());
	}
	
	
	@Test
	public void testDbConn() throws InterruptedException{
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		//////Thread.sleep(500);
		WebElement pageBody = driver.findElement(By.tagName("p"));
		String text = pageBody.getText();
		Assert.assertEquals("Database connection succeeded", text);
	}
	
	
	@Test
	public void testStaffLoginSuccess() throws InterruptedException{
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		//Thread.sleep(500);
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("admin");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("admin");
		//passwordText.submit();

		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Staff Welcome Page", driver.getTitle());
	}
	
	
	@Test
	public void testPatronLoginSuccess() throws InterruptedException{
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		//Thread.sleep(500);
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("customer1");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("1234");
		//passwordText.submit();

		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());
	}
	
	
	
	@After
	public void closePage(){
		driver.quit();
	}
}