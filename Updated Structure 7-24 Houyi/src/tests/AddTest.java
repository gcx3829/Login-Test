package tests;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddTest {

WebDriver driver;
	
	@Before
	public void login() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/Updated_Structure_7-19/index.jsp");
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		//Thread.sleep(500);
		
	}
	
	@Test
	public void testStaffAdd() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("admin");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("admin");
		//passwordText.submit();
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Staff Welcome Page", driver.getTitle());
		
		WebElement addBtn = driver.findElement(By.id("Add"));
		addBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Add Book", driver.getTitle());
		
		
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		isbnText.sendKeys("000-000-00-1");
		WebElement titleText = driver.findElement(By.name("title"));
		titleText.sendKeys("addTestTitle");
		WebElement authorText = driver.findElement(By.name("author"));
		authorText.sendKeys("addTestAuthor");
		WebElement categoryText = driver.findElement(By.name("category"));
		categoryText.sendKeys("addTestCategory");
		WebElement editionText = driver.findElement(By.name("edition"));
		editionText.sendKeys("addTestEdition");
		
		//Thread.sleep(1000);
		
		WebElement addBtn2 = driver.findElement(By.id("add"));
		addBtn2.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Staff Welcome Page", driver.getTitle());		
		
	}
	
	
	@After
	public void closePage(){
		driver.quit();
	}
	
}