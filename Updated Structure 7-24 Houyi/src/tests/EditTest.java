package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class EditTest {

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
	public void testStaffEdit() throws InterruptedException{
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("admin");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("admin");
		//passwordText.submit();
		WebElement loginBtn = driver.findElement(By.name("submit"));
		loginBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Staff Welcome Page", driver.getTitle());
		
		WebElement editBtn = driver.findElement(By.id("Edit"));
		editBtn.click();
		//Thread.sleep(1000);
		Assert.assertEquals("Edit Book", driver.getTitle());
	
		WebElement targetInvIDText = driver.findElement(By.name("TargetInventoryID"));
		targetInvIDText.sendKeys("20");
		WebElement targetISBNText = driver.findElement(By.name("TargetISBN"));
		targetISBNText.sendKeys("000-000-00-2");
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		isbnText.sendKeys("000-000-00-3");
		WebElement titleText = driver.findElement(By.name("title"));
		titleText.sendKeys("editedTestTitle");
		WebElement authorText = driver.findElement(By.name("author"));
		authorText.sendKeys("editedTestAuthor");
		WebElement categoryText = driver.findElement(By.name("category"));
		categoryText.sendKeys("editedTestCategory");
		WebElement editionText = driver.findElement(By.name("edition"));
		editionText.sendKeys("editedTestEdition");
		
		Thread.sleep(1000);
		

		WebElement editBtn2 = driver.findElement(By.id("edit book"));
		editBtn2.click();
		Thread.sleep(1000);
		Assert.assertEquals("Staff Welcome Page", driver.getTitle());
	}
	
	@After
	public void closePage(){
		driver.quit();
	}
	
}