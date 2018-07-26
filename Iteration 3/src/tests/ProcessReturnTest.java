package tests;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class ProcessReturnTest {
	WebDriver driver;
	
	@Before
	public void loginAndOpenSearch() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver","chromedriver");
		driver = new ChromeDriver();
		driver.get(TestVar.getIndexPage());
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		Thread.sleep(500);
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("admin");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("admin");

		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		Thread.sleep(1000);	
	}
	
	@Test
	public void testProcessReturnPage() throws InterruptedException{
		WebElement processBtn = driver.findElement(By.id("process"));
		processBtn.click();
		Assert.assertEquals("Process Returns", driver.getTitle());	
	}
	
	@Test
	public void testReturnedBookExists_Presentable() throws InterruptedException{
		WebElement processBtn = driver.findElement(By.id("process"));
		processBtn.click();
		WebElement InventoryID = driver.findElement(By.id("InventoryID"));
		InventoryID.sendKeys("3");
		WebElement presentableBtn = driver.findElement(By.id("presentable"));
		presentableBtn.click();
		Thread.sleep(500);
		WebElement pageBody = driver.findElement(By.tagName("h2"));
		String text = pageBody.getText();
		Assert.assertEquals("Book has been made available for Library patrons", text);		
	}
	
	@Test
	public void testReturnedBookExists_Repair() throws InterruptedException{
		WebElement processBtn = driver.findElement(By.id("process"));
		processBtn.click();
		WebElement InventoryID = driver.findElement(By.id("InventoryID"));
		InventoryID.sendKeys("6");
		WebElement repairBtn = driver.findElement(By.id("repair needed"));
		repairBtn.click();
		Thread.sleep(500);
		WebElement pageBody = driver.findElement(By.tagName("h2"));
		String text = pageBody.getText();
		Assert.assertEquals("Book has been sent for repair", text);		
	}
	
	@Test
	public void testReturnedBookExists_Ruined() throws InterruptedException{
		WebElement processBtn = driver.findElement(By.id("process"));
		processBtn.click();
		WebElement InventoryID = driver.findElement(By.id("InventoryID"));
		InventoryID.sendKeys("9");
		WebElement ruinedBtn = driver.findElement(By.id("ruined"));
		ruinedBtn.click();
		Thread.sleep(500);
		WebElement pageBody = driver.findElement(By.tagName("h2"));
		String text = pageBody.getText();
		Assert.assertEquals("Book has been ruined and cannot be made available to the public", text);		
	}
	
	@Test
	public void testReturnedBookNotExists_Presentable() throws InterruptedException{
		WebElement processBtn = driver.findElement(By.id("process"));
		processBtn.click();
		WebElement InventoryID = driver.findElement(By.id("InventoryID"));
		InventoryID.sendKeys("1");
		WebElement presentableBtn = driver.findElement(By.id("presentable"));
		presentableBtn.click();
		Thread.sleep(500);
		WebElement pageBody = driver.findElement(By.tagName("h2"));
		String text = pageBody.getText();
		Assert.assertEquals("Book has not been returned", text);		
	}
	
	@Test
	public void testReturnedBookNotExists_Repair() throws InterruptedException{
		WebElement processBtn = driver.findElement(By.id("process"));
		processBtn.click();
		WebElement InventoryID = driver.findElement(By.id("InventoryID"));
		InventoryID.sendKeys("1");
		WebElement repairBtn = driver.findElement(By.id("repair needed"));
		repairBtn.click();
		Thread.sleep(500);
		WebElement pageBody = driver.findElement(By.tagName("h2"));
		String text = pageBody.getText();
		Assert.assertEquals("Book has not been returned", text);		
	}
	
	@Test
	public void testReturnedBookNotExists_Ruined() throws InterruptedException{
		WebElement processBtn = driver.findElement(By.id("process"));
		processBtn.click();
		WebElement InventoryID = driver.findElement(By.id("InventoryID"));
		InventoryID.sendKeys("1");
		WebElement ruinedBtn = driver.findElement(By.id("ruined"));
		ruinedBtn.click();
		Thread.sleep(500);
		WebElement pageBody = driver.findElement(By.tagName("h2"));
		String text = pageBody.getText();
		Assert.assertEquals("Book has not been returned", text);		
	}
	
	
	@After
	public void closePage(){
		driver.quit();
	}
}
