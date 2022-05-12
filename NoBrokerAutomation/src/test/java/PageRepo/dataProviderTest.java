package PageRepo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GenericFile.selenium_utility;

public class dataProviderTest extends selenium_utility{

	@DataProvider
	public Object[] dataset() {

		Object[] dataset = new Object[1];
		dataset[0] = "Mumbai";
		dataset[1] = "Bangalore";
		return dataset;
	}
	
	@Test(dataProvider = "dataset")
	public String cityName(String name) {
		
		String city_Name="(//div[contains(text(),'"+name+"')])[2]";
		return city_Name;
	}
}
