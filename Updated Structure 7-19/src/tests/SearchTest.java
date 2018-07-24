package tests;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchTest {
	
	WebDriver driver;
	
	@Before
	public void loginAndOpenSearch() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/Updated_Structure_7-19/index.jsp");
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		//Thread.sleep(500);
		
	}
	
	@Test
	public void testSearchPatronExists() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("customer1");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("1234");
		//passwordText.submit();
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());	
		WebElement searchBtn = driver.findElement(By.name("submit"));
		searchBtn.click();
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		WebElement searchBtn2 = driver.findElement(By.name("submit"));
		isbnText.sendKeys("000-000-00-0");
		searchBtn2.click();
		
		WebElement searchRes = driver.findElement(By.tagName("table"));
		String tableText = searchRes.getText();
		Assert.assertNotEquals("NULL", tableText);
	}
	
	
	@Test
	public void testSearchPatronNotExists() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("customer1");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("1234");
		//passwordText.submit();
		
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Patron Welcome Page", driver.getTitle());	
		WebElement searchBtn = driver.findElement(By.name("submit"));
		searchBtn.click();
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		WebElement searchBtn2 = driver.findElement(By.name("submit"));
		isbnText.sendKeys("000-000-00-1");
		searchBtn2.click();
		
		WebElement searchRes = driver.findElement(By.tagName("table"));
		String tableText = searchRes.getText();
		Assert.assertNotEquals("NULL", tableText);
	}
	
	
	@Test
	public void testSearchStaffExists() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("admin");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("admin");
		//passwordText.submit();
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Staff Welcome Page", driver.getTitle());	
		
		WebElement searchBtn = driver.findElement(By.id("search"));
		searchBtn.click();
		
		WebElement invID = driver.findElement(By.name("InventoryID"));
		WebElement searchBtn2 = driver.findElement(By.id("search"));
		invID.sendKeys("0");
		searchBtn2.click();
		
		WebElement searchRes = driver.findElement(By.tagName("table"));
		String tableText = searchRes.getText();
		Assert.assertNotEquals("NULL", tableText);
		//Thread.sleep(1000);
	}
	
	
	@Test
	public void testSearchStaffNotExists() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("admin");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("admin");
		//passwordText.submit();
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Staff Welcome Page", driver.getTitle());	
		
		WebElement searchBtn = driver.findElement(By.id("search"));
		searchBtn.click();
		
		WebElement invID = driver.findElement(By.name("InventoryID"));
		WebElement searchBtn2 = driver.findElement(By.id("search"));
		invID.sendKeys("999");
		searchBtn2.click();
		
		WebElement searchRes = driver.findElement(By.tagName("table"));
		String tableText = searchRes.getText();
		Assert.assertNotEquals("NULL", tableText);
		//Thread.sleep(1000);
	}
	
	
	@After
	public void closePage(){
		driver.quit();
	}
}
