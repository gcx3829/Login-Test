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
		System.setProperty("webdriver.chrome.driver","chromedriver");
		driver = new ChromeDriver();
		driver.get(TestVar.getIndexPage());
		WebElement link = driver.findElement(By.name("loginLink"));
		link.click();
		//Thread.sleep(500);
		
	}
	
	@Test
	public void testEditBookNotInDatabase() throws InterruptedException{
		
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
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		isbnText.sendKeys("000-000-00-3");
		WebElement titleText = driver.findElement(By.name("title"));
		titleText.sendKeys("editedTestTitle");
		WebElement authorText = driver.findElement(By.name("author"));
		authorText.sendKeys("editedTestAuthor");
		WebElement genreText = driver.findElement(By.name("genre"));
		genreText.sendKeys("editedTestGenre");
		WebElement editionText = driver.findElement(By.name("edition"));
		editionText.sendKeys("editedTestEdition");
		
		//Thread.sleep(1000);
		

		WebElement editBtn2 = driver.findElement(By.id("edit book"));
		editBtn2.click();
		//Thread.sleep(1000);
		
		WebElement editRes = driver.findElement(By.id("SecondMsg"));
		Assert.assertEquals("Book with Inventory ID 20 is not in the database!!", editRes.getText());
	}
	
	@Test
	public void testEditBookExists() throws InterruptedException{
		
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
		targetInvIDText.sendKeys("10");
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		isbnText.sendKeys("111-111-11-2");
		WebElement titleText = driver.findElement(By.name("title"));
		titleText.sendKeys("editedTestTitle");
		WebElement authorText = driver.findElement(By.name("author"));
		authorText.sendKeys("editedTestAuthor");
		WebElement genreText = driver.findElement(By.name("genre"));
		genreText.sendKeys("editedTestGenre");
		WebElement editionText = driver.findElement(By.name("edition"));
		editionText.sendKeys("editedTestEdition");
		
		//Thread.sleep(1000);
		

		WebElement editBtn2 = driver.findElement(By.id("edit book"));
		editBtn2.click();
		//Thread.sleep(1000);
		
		WebElement searchRes = driver.findElement(By.id("SecondMsg"));
		Assert.assertEquals("Book with inventory ID 10 was edited successfully", searchRes.getText());
	}
	
	@Test
	public void testEditTitleNotInDatabase() throws InterruptedException{
		
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
	
		WebElement targetInvIDText = driver.findElement(By.name("TargetISBN"));
		targetInvIDText.sendKeys("000-000-00-9");
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		isbnText.sendKeys("000-000-00-3");
		WebElement titleText = driver.findElement(By.name("title"));
		titleText.sendKeys("editedTestTitle");
		WebElement authorText = driver.findElement(By.name("author"));
		authorText.sendKeys("editedTestAuthor");
		WebElement genreText = driver.findElement(By.name("genre"));
		genreText.sendKeys("editedTestGenre");
		WebElement editionText = driver.findElement(By.name("edition"));
		editionText.sendKeys("editedTestEdition");
		
		//Thread.sleep(1000);
		

		WebElement editBtn2 = driver.findElement(By.id("edit title"));
		editBtn2.click();
		//Thread.sleep(1000);
		
		WebElement editRes = driver.findElement(By.id("SecondMsg"));
		Assert.assertEquals("Book with ISBN 000-000-00-9 is not in the database!!", editRes.getText());
	}
	
	@Test
	public void testEditTitleExists() throws InterruptedException{
		
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
	
		WebElement targetInvIDText = driver.findElement(By.name("TargetISBN"));
		targetInvIDText.sendKeys("000-000-00-0");
		WebElement isbnText = driver.findElement(By.name("ISBN"));
		isbnText.sendKeys("000-000-00-0");
		WebElement titleText = driver.findElement(By.name("title"));
		titleText.sendKeys("editedTestTitle");
		WebElement authorText = driver.findElement(By.name("author"));
		authorText.sendKeys("editedTestAuthor");
		WebElement genreText = driver.findElement(By.name("genre"));
		genreText.sendKeys("editedTestGenre");
		WebElement editionText = driver.findElement(By.name("edition"));
		editionText.sendKeys("editedTestEdition");
		
		//Thread.sleep(1000);
		

		WebElement editBtn2 = driver.findElement(By.id("edit title"));
		editBtn2.click();
		//Thread.sleep(1000);
		
		WebElement searchRes = driver.findElement(By.id("SecondMsg"));
		Assert.assertEquals("Book with ISBN 000-000-00-0 has been successfully edited!!", searchRes.getText());
	}
	@After
	public void closePage(){
		driver.quit();
	}
	
}
