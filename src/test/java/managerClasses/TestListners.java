package managerClasses;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.configuration.Theme;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import naukriPageObjects.LoginPage;

public class TestListners implements ITestListener {

	public static WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

	public void onStart(ITestContext context) {
		// Initialize ExtentReports with a reporter
		 ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/extent-report.html");
	        sparkReporter.config().setDocumentTitle("Test Automation Report");
	        sparkReporter.config().setReportName("Functional Testing");
	        sparkReporter.config().setTheme(Theme.STANDARD);

	        extent = new ExtentReports();
	        extent.attachReporter(sparkReporter);
	        extent.setSystemInfo("Host Name", "Localhost");
	        extent.setSystemInfo("Environment", "QA");
	        extent.setSystemInfo("User Name", "Madhava raju pujari");
	}

	public void onTestStart(ITestResult result) {
		/*
		 * ChromeOptions options = new ChromeOptions();
		 * options.addArguments("--headless"); options.addArguments("--disable-gpu"); //
		 * Applicable to Windows OS options.addArguments("--window-size=1920,1080"); //
		 * Optional, sets the size of the browser window
		 * options.addArguments("--no-sandbox"); // Bypass OS security model
		 * options.addArguments("--disable-dev-shm-usage");
		 */
		 test = extent.createTest(result.getMethod().getMethodName());
	        test.log(Status.INFO, "Starting test: " + result.getMethod().getMethodName());
		driver = new ChromeDriver();
//	        driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		Object testClass = result.getInstance();
		if (testClass instanceof LoginPage) {
			((LoginPage) testClass).setDriver(driver); // Use setter to inject driver into LoginPage
		}
	}

	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
		if (driver != null) {
			driver.quit();
			System.out.println("Test passed, driver closed.");
		}
	}
	public void onTestFailure(ITestResult result) {
	    test.log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
	    test.log(Status.FAIL, result.getThrowable());

	    if (driver != null) {
	        // Capture the screenshot and get the file path
	        String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
	        
	        if (screenshotPath != null) {
	            try {
	                // Attach the screenshot to the Extent report
	                test.addScreenCaptureFromPath(screenshotPath);
	                test.log(Status.INFO, "Screenshot attached for test failure.");
	            } catch (Exception e) {
	                // Log the error in the Extent report if the screenshot cannot be attached
	                test.log(Status.FAIL, "Error while attaching screenshot: " + e.getMessage());
	                e.printStackTrace(); // Optionally, print the stack trace for debugging
	            }
	        } else {
	            test.log(Status.FAIL, "Screenshot capture failed. Unable to attach screenshot.");
	        }
	        
	        // Close the driver after test failure
	        driver.quit();
	    }
	}
	
	public void onFinish(ITestContext context) {
		 extent.flush();
		if (driver != null) {
			
			
			
			driver.quit();
			System.out.println("Driver closed after all tests.");
		}
	}
	public String captureScreenshot(String methodName) {
	    try {
	        TakesScreenshot screenshot = (TakesScreenshot) driver;
	        File source = screenshot.getScreenshotAs(OutputType.FILE);
	        String destinationPath = "screenshots/" + methodName + ".png";
	        File destination = new File(destinationPath);
	        FileUtils.copyFile(source, destination); // This might throw IOException
	        return destinationPath;
	    } catch (IOException e) {
	        System.out.println("Exception while taking screenshot: " + e.getMessage());
	        return null;  // Return null if there's an issue capturing the screenshot
	    }
	}
}