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
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/Updated_Structure_7-19/index.jsp");
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		//Thread.sleep(500);
		
	}
	
	
	
	
	
	@After
	public void closePage(){
		driver.quit();
	}
	
	
}