package GenericFile;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

//import com.relevantcodes.extentreports.LogStatus;

public class selenium_utility {

	public static String screenshotPath;
	public static String screenshotName;
	public static ArrayList<String> tabs;

	// Sleep comment
	public static void sleep(int nenoSecond) throws Throwable {

		Thread.sleep(nenoSecond);
	}

	// Explicit wait for element visibility
	public static void explicit_Wait(String path, int i, String pathName, WebDriver driver) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(i));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
		} catch (Exception e) {
			System.out.println(
					"----- WAITED FOR " + i + " SECONDS FOR THE VISIBILITY OF '" + pathName + "', BUT NOT FOUND -----");
		}
	}

	// verify assert with logs and screen shot.
	public static void verifyEquals(String actual, String expected, WebDriver driver) throws IOException {

		try {
			Assert.assertEquals(actual, expected);
			System.out.println("('" + actual + "') is Equal to ('" + expected + "')");
			System.out.println("-- Hence Both Verified");
		} catch (Throwable t) {

			if (actual.contains(expected)) {
				System.out.println("-- Results Partially Macthed!!!!");
				System.out.println("('" + actual + "') is Partially Equal to ('" + expected + "')");
			} else if (expected.contains(actual)) {
				System.out.println("-- Results Partially Macthed!!!!");
				System.out.println("('" + expected + "') is Partially Equal to ('" + actual + "')");
			} else {
				System.out.println("('" + actual + "') is Not Equal to ('" + expected + "')");
				captureScreenshot(driver);
				// ReportNG
				Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
				Reporter.log("<a target=\"_blank\" href=" + selenium_utility.screenshotName + "><img src="
						+ selenium_utility.screenshotName + " height=200 width=200></img></a>");
				Reporter.log("<br>");
				Reporter.log("<br>");
			}
		}
	}

	// Take Screen shot.
	public static void captureScreenshot(WebDriver driver) throws IOException {

		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			Date d = new Date();
			screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\test-output\\" + screenshotName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// click method
	public static void click(String path, WebDriver driver) throws Throwable {
		try {
			driver.findElement(By.xpath(path)).click();
			sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void scrollbyElement(String path, int pixel, WebDriver driver) throws Throwable {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			if (path == null) {
				js.executeScript("window.scrollBy(0," + pixel + ")"); // Scrolling the window by pixels vertically
				sleep(3000);
			} else {
				WebElement element = driver.findElement(By.xpath(path));
				js.executeScript("arguments[0].scrollIntoView(true);", element);
				sleep(3000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Window Switching upto two tabs
	public static void window_Switch(int tabNo, ArrayList<String> tabs, WebDriver driver) throws Throwable {

		try {
			sleep(1000);
			Set<String> allWindows = driver.getWindowHandles();
			tabs = new ArrayList<String>(allWindows);
			driver.switchTo().window(tabs.get(tabNo));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Check Element Visibility
	public static boolean element_Displayed(String path, String element_Name, WebDriver driver) throws Throwable {
		boolean value = false;

		for (int i = 0; i < 2; i++) {
			try {
				value = driver.findElement(By.xpath(path)).isDisplayed();
			} catch (Exception e) {
			}
		}
		if (value) {
			System.out.println("-- " + element_Name + " is displayed--");
		} else {
			System.out.println("-- " + element_Name + " is not displayed--");
		}
		return value;
	}

	// Explicit wait for the element to  be clickable
		public static void wait_Tobe_Clickable(String path, int seconds, String pathName, WebDriver driver) {

			try {
				WebElement elementClick =new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(ExpectedConditions.elementToBeClickable(By.xpath(path)));
				Actions action =new Actions(driver);
				action.moveToElement(elementClick).click().build().perform();
			}catch (Exception e) {
				System.out.println("----- WAITED FOR "+seconds+" SECONDS FOR THE VISIBILITY OF '"+pathName+"' TO BECOME CLICKABLE, BUT NOT FOUND -----");
			}
		}


	// type method
	public static void type(String path, String value, WebDriver driver) {

		try {
			driver.findElement(By.xpath(path)).sendKeys(value);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// get text value
	public static String getText(String path, WebDriver driver) {

		String value = null;
		try {
			value = driver.findElement(By.xpath(path)).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	// get title value
	public static String getTitle(String path, WebDriver driver) {

		String value = null;
		try {
			value = driver.findElement(By.xpath(path)).getAttribute("title");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

}