package GenericFile;

import java.io.FileInputStream;
import java.util.Properties;

public class Resources_Utility {

	
	private static String configpath = System.getProperty("user.dir")+"\\src\\test\\java\\Resources\\Config.properties";
	private static String xpathpath = System.getProperty("user.dir")+"\\src\\test\\java\\PathRepo\\xpath.properties";
	private static Properties config = new Properties();
	private static Properties xpath = new Properties();
	private static FileInputStream Xfis;
	private static FileInputStream Cfis;

	public static String xpath(String path) {

		try {
			Xfis = new FileInputStream(xpathpath);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			xpath.load(Xfis);
		} catch (Exception e) {
			// TODO: handle exception
		}
		path = xpath.getProperty(path);
		return path;

	}

	public static String config (String data) {

		try {
			Cfis = new FileInputStream(configpath);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			config.load(Cfis);
		} catch (Exception e) {
			// TODO: handle exception
		}
		data = config.getProperty(data);
		return data;
	}
	
}