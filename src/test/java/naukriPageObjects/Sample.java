package naukriPageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Sample {

	
	
	public static void main(String[] args) {
		WebDriver driver; 
		driver  = new ChromeDriver();
		driver.get("https://www.ilovepdf.com/pdf_to_word");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//			WebElement elem = driver.findElement(By.xpath("//a[@class='uploader__btn tooltip--left active']"));
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			WebElement uploadBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type= 'file']")));

//			WebElement uploadbtn= driver.findElement(By.xpath("//a[@class='uploader__btn tooltip--left active']"));
			uploadBtn.sendKeys("E:\\NewProjects\\UpdateNaukri.com\\resource\\Madhav_Pujari_Resume_Updated.pdf");
		
			System.out.println("successfully uploaded");
	}
}
