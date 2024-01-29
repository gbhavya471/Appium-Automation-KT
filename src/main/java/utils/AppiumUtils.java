package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.io.FileReader;
import java.net.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public abstract class AppiumUtils extends BrowserStackTestNGTest {

	public AppiumDriverLocalService service;

	
	public Double getFormattedAmount(String amount)
	{
		Double price = Double.parseDouble(amount.substring(1));
		return price;
		
	}
	
	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {
//System.getProperty("user.dir")+"//src//test//java//org//appiumframework//testData//eCommerce.json"
		// conver json file content to json string
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath),StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data;

	}
	
//	public AppiumDriverLocalService startAppiumServer(String ipAddress,int port)
//	{
//		 service = new AppiumServiceBuilder().
//				 withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/lib/main.js"))
//				 .usingDriverExecutable(new File ("/opt/homebrew/bin/node"))
//					.withIPAddress(ipAddress).usingPort(port)
//					.build();
//				service.start();
//				return service;
//	}
	
	public AppiumDriverLocalService startAppiumServer(String ipAddress,int port)
			throws FileNotFoundException, IOException, ParseException 
	
	{
		String platform = System.getProperty("platform"); 
		
		if(platform.equalsIgnoreCase("Simulator")) {
			 service = new AppiumServiceBuilder().
					 withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/lib/main.js"))
					 .usingDriverExecutable(new File ("/opt/homebrew/bin/node"))
						.withIPAddress(ipAddress).usingPort(port)
						.build();
					service.start();
					return service;
		}
				
		else if(platform.equalsIgnoreCase("BrowserStack")) {
			DesiredCapabilities capabilitis = getCap();
//			capabilitis.setCapability("PlatformName", "Android");
//			capabilitis.setCapability("DeviceName", "Android");
			capabilitis.setCapability("deviceName", "Galaxy Note 10+");
			String username="bhavyag_s5W0qT";
			 String accessKey="xHcn2eY8LwpTAtu5pLDn";
			 String server="hub-cloud.browserstack.com";
			
	       AndroidDriver driver = new AndroidDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilitis);
	        capabilities.setCapability("autoGrantPermissions",true);
		}
		else {
			System.out.println("Incorrect platform");
		}
		JSONParser parser = new JSONParser();
//        JSONObject config = (JSONObject) parser.parse(new FileReader(System.getProperty("user_dir")+"/src/main/java/resources/first.conf.json"));

		 if(System.getProperty("OS").equalsIgnoreCase("Android")) {
	    	 config = (JSONObject) parser.parse(new FileReader("src/main/java/resources/first.conf.json"));
	  }
	    else if(System.getProperty("OS").equalsIgnoreCase("ios")) {
	    	config = (JSONObject) parser.parse(new FileReader("src/main/java/resources/android_browserStack.json"));
	    }
	    
	  else {
		   System.out.println("The OS specified is invalid");
	  }
		
		String username = System.getenv("BROWSERSTACK_USERNAME");
	    if(username == null) {
	        username = (String) config.get("username");
	    }

	    String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
	    if(accessKey == null) {
	        accessKey = (String) config.get("access_key");
	    }
		
		
		
		return service;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void waitForElementToAppear(WebElement ele, AppiumDriver driver)
	{
		WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains((ele),"text" , "Cart"));
	}
	
	
	public String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException
	{
		File source = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir")+"//reports"+testCaseName+".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
		//1. capture and place in folder //2. extent report pick file and attach to report
		
		
		
	}
	
	
}
