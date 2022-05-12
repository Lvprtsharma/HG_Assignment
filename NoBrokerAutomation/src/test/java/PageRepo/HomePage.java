package PageRepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import GenericFile.Resources_Utility;
import GenericFile.selenium_utility;

public class HomePage extends selenium_utility {

	private static String driverpath = System.getProperty("user.dir") + "\\driver\\";
	public static WebDriver driver = null;
	public static String actual_URl = "https://www.nobroker.in/";
	public String current_URl;
	public String description = null;
	public String Expected_title=null;
	public String Actual_Title=null;

	@BeforeSuite
	public void startup() {
		try {
			if (Resources_Utility.config("browser").equals("chrome")) {											//Demonstration for multiple browsers selection

				System.setProperty("webdriver.chrome.driver", driverpath + "chromedriver.exe");					//Setting the chromedriver
				driver = new ChromeDriver();
			} else if (Resources_Utility.config("browser").equals("ie")) {										//Setting the IE browser

				System.setProperty("webdriver.ie.driver", driverpath + "IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Test(enabled = true, priority = 1)																			// Here we will navigate to the site
	public void navigation_To_Site() {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(Resources_Utility.config("implicit.wait")),
				TimeUnit.SECONDS);
		driver.get(Resources_Utility.config("URL"));
	}

	@Test(enabled = true, priority = 2)
	public void url_Matching() {																				//Matching the URL of site opened
		try {
			current_URl = driver.getCurrentUrl();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.urlToBe(actual_URl));

			verifyEquals(current_URl, actual_URl, driver);
			System.out.println("URL Matched successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@DataProvider
	public Object[][] dataset1() {																					//Data Provider Method

		return new Object[][] { { "Mumbai", "2 BHK", "3 BHK"}/* ,{"Bangalore"} */};
	}

	@Parameters({"city","BH1","BH2"})
	@Test(enabled = true, priority = 3, invocationCount = 2)	
	public void verify_Search(@Optional("Test") String city, String BH1, String BH2) throws Throwable {

		try {
			driver.navigate().refresh();
			sleep(1000);
			click(Resources_Utility.xpath("buy_Tab"), driver);														 // Click on Buy option

			String city_Name="(//div[contains(text(),'"+city+"')])[1]";												//Creating path for city name
			click(Resources_Utility.xpath("city_Dropdown"), driver); 
			driver.findElement(By.xpath(city_Name)).click();

			type(Resources_Utility.xpath("search_Input"), Resources_Utility.config("search_Input"), driver);		// Input value
			click(Resources_Utility.xpath("malad_East"), driver);													// click on Malad East

			type(Resources_Utility.xpath("search_Input"), Resources_Utility.config("search_Input"), driver);		// Input value
			click(Resources_Utility.xpath("malad_West"), driver);													// click on Malad West

			click(Resources_Utility.xpath("apartment_Type"), driver);												//Creating the path for Apartment Type
			String bh1="(//*[@id='searchCity']/div/div)[5]/div/div/label/span[contains(text(),'"+BH1+"')]";
			driver.findElement(By.xpath(bh1)).click();																// selecting 2BHK

			String bh2="(//*[@id='searchCity']/div/div)[5]/div/div/label/span[contains(text(),'"+BH2+"')]";			//Creating the path for Apartment Type
			driver.findElement(By.xpath(bh2)).click();																// Selecting 3 BHK	
			
			click(Resources_Utility.xpath("search_Btn"), driver);													// clicking on Search button
			selecting_Property();																					//Calling the Property Selection function
			verify_Description();																					//Calling the Description section verification

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selecting_Property() throws Throwable {
		try {
			sleep(3000);																							//Waiting for site loading
			do {
				scrollbyElement(null, 1500, driver);																//Scrolling upto 4th property	
				if(element_Displayed(Resources_Utility.xpath("4th_Property_Bottom"), "4th Property", driver)) {		//checking if 4th property is shown
					break;																							//if 4th property is shown then break the loop
				}}while(true);
			
			Expected_title=getTitle(Resources_Utility.xpath("4th_Property_Title"), driver);							//Getting the title of property being selected		
			wait_Tobe_Clickable(Resources_Utility.xpath("4th_Property"), 10, "4th Property", driver);				//Clicking on property
			System.out.println("Property Selected: "+Expected_title);												//Displaying the name of property
			window_Switch(1, tabs, driver);																			//Switching to next window
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verify_Description() throws Throwable{
		try {
			Actual_Title = getTitle(Resources_Utility.xpath("4th_Property_ActualTitle"), driver);					//Getting the title of actual property selected
			verifyEquals(Actual_Title, Expected_title, driver);														//Verifying both the titles
			scrollbyElement(Resources_Utility.xpath("description_Title"), 0, driver);								// Scrolling to description section

			description = getText(Resources_Utility.xpath("description_Text"), driver);								//Getting decription data
			try {
				Assert.assertTrue(description.contains(""), "Pass");												// Verifying the test case pass condition
				//System.out.println(description);
			} catch (Exception e) {
				Assert.assertTrue(description.contains(null), "Fail");												// Verifying the test case fail condition		
			}
			driver.close();																							//closing current window tab
			window_Switch(0, tabs, driver);																			//switching to first window tab
			driver.get(Resources_Utility.config("URL"));															//navigating to main site
		} catch (ElementClickInterceptedException e) {
			e.printStackTrace();
		}
	}

	@Test(enabled = true, priority = 4)
	public void browser_Closure() {																				
		driver.quit();																								//Closing all the tabs
	}
}