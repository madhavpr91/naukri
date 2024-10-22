package naukriPageObjects;

import java.io.File;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(managerClasses.TestListners.class) // Attach listener to test class
public class LoginPage {

	 private WebDriver driver;

	    // Setter method to set WebDriver instance
	    public void setDriver(WebDriver driver) {
	        this.driver = driver;
	    }
	
	/*
	 * @BeforeTest public void initBrowser() {
	 * 
	 * ChromeOptions options = new ChromeOptions();
	 * options.addArguments("--headless"); options.addArguments("--disable-gpu"); //
	 * Applicable to Windows OS options.addArguments("--window-size=1920,1080"); //
	 * Optional, sets the size of the browser window
	 * options.addArguments("--no-sandbox"); // Bypass OS security model
	 * options.addArguments("--disable-dev-shm-usage");
	 * 
	 * driver = new ChromeDriver(options);
	 * 
	 * driver = new ChromeDriver();
	 * 
	 * driver.get("https://www.naukri.com/"); driver.manage().window().maximize();
	 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	 * 
	 * }
	 */

	/*
	 * @AfterTest public void closeDriver() { driver.quit(); }
	 */

	
	@Test
	public void mainTest() {
		
		  if (driver == null) {
	            System.out.println("Driver is null in mainTest.");
	            return; // Exit the test if driver is not initialized
	        }
		  
		driver.get("https://www.naukri.com/");
		System.out.println("my name is madhava");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement loginLayer = wait
				.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[@id='login_Layer']"))));
		loginLayer.click();

		WebElement userName = driver
				.findElement(By.xpath("//label[text()='Email ID / Username']/following-sibling::input"));
		wait.until(ExpectedConditions.visibilityOf(userName));
		userName.sendKeys("madhavaraju86@gmail.com");

		WebElement password = driver.findElement(
				By.xpath("//label[text()='Password']/following-sibling::input[@placeholder='Enter your password']"));
		wait.until(ExpectedConditions.visibilityOf(password));
		password.sendKeys("9676081532");
		wait.until(ExpectedConditions.visibilityOf(password));

		WebElement loginBtn = driver.findElement(By.xpath("//button[@class='btn-primary loginButton']"));
		if (loginBtn.isEnabled()) {
			loginBtn.click();
		} else {
			System.out.println("login button element is not enabled");
		}

		WebElement headerName = driver.findElement(By.xpath("//div[@class='info__heading']"));
		wait.until(ExpectedConditions.visibilityOf(headerName));
		Assert.assertEquals(headerName.getText(), "Madhava raju Pujari", "This is not matching your profile");

		WebElement profile = driver.findElement(By.xpath("//a[text()='Complete']"));

		wait.until(ExpectedConditions.visibilityOf(profile));
		wait.until(ExpectedConditions.elementToBeClickable(profile));
		profile.click();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WebElement uploadElement = driver.findElement(By.xpath("//input[@type='file' and @id = 'attachCV']")); // Replace
																												// with
																												// actual
																												// ID

		// Make sure the element is not hidden
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].style.display='block';", uploadElement);

		// Upload the file using sendKeys()
		File file = new File("resource/Madhav_Pujari_Resume_Updated.pdf");
		uploadElement.sendKeys(file.getAbsolutePath());
//	        uploadElement.sendKeys("E:/NewProjects/UpdateNaukri.com/resource/Madhav_Pujari_Resume_Updated.pdf");

		WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[@class='cnt']/descendant::i[@class='icon' and text()='GreenTick']/following-sibling::p[text()='Success']/following-sibling::p")));
		Assert.assertEquals(successMessage.getText(), "Resume has been successfully uploaded.",
				"Successfully update message not displayed");

	}

}
