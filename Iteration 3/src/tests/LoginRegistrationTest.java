package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class LoginRegistrationTest {
	WebDriver driver;
	
	@Before
	public  void openLibraryIndexPage() throws InterruptedException{
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(TestVar.getIndexPage());
		Assert.assertEquals("Online Library Management System", driver.getTitle());
	}
	
	@Test
	public void testRegistrationSuccess() throws InterruptedException{
		WebElement link = driver.findElement(By.name("RegistrationLink"));
		link.click();
		//Thread.sleep(500);
		
		WebElement usernameText = driver.findElement(By.name("username"));
		usernameText.sendKeys("test1");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("test1");
		
		WebElement nameText = driver.findElement(By.name("name"));
		nameText.sendKeys("test1");
		
		WebElement rePaswordText = driver.findElement(By.name("retry-password"));
		rePaswordText.sendKeys("test1");
		
		Select dropdown = new Select(driver.findElement(By.name("userType")));
		dropdown.selectByIndex(0);		

		
		WebElement RegisterBtn = driver.findElement(By.name("submit"));
		RegisterBtn.click();
		//Thread.sleep(1000);
		WebElement addRes = driver.findElement(By.id("successMessage"));
		Assert.assertEquals("Registration done, please login!",  addRes.getText());
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
		usernameText.sendKeys("patron");
		
		WebElement passwordText = driver.findElement(By.name("password"));
		passwordText.sendKeys("patron");

		
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
