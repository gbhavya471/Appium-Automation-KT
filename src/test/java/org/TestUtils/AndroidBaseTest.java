package org.TestUtils;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import pageObjects_android.FormPage;
import utils.AppiumUtils;

public class AndroidBaseTest extends AppiumUtils{

	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	public FormPage formPage;
	
	
	@BeforeClass(alwaysRun=true)
	public void ConfigureAppium() throws IOException, NumberFormatException, ParseException
	{
//		Properties prop = new Properties();
//		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//resources//data.properties");
//		String ipAddress = System.getProperty("ipAddress")!=null ? System.getProperty("ipAddress") : prop.getProperty("ipAddress");
//		System.out.println(ipAddress);
//		prop.load(fis);
//		//String ipAddress = prop.getProperty("ipAddress");
//		String port = prop.getProperty("port");
//			
//		service = startAppiumServer("http://127.0.0.1",Integer.parseInt("4723"));
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//resources//data.properties");
		prop.load(fis);
		String ipAddress = System.getProperty("ipAddress")!=null ? System.getProperty("ipAddress") : prop.getProperty("ipAddress");
		System.out.println(ipAddress);
		
		//String ipAddress = prop.getProperty("ipAddress");
		String port = prop.getProperty("port");
			
		service = startAppiumServer(ipAddress,Integer.parseInt(port));
			
		if(System.getProperty("platform").equals("Simulator")){
								
			UiAutomator2Options options = new UiAutomator2Options();
			options.setDeviceName(prop.getProperty("AndroidDeviceNames")); //emulator
	
			options.setApp(System.getProperty("user.dir")+"//src//test//java//org//resources//General-Store.apk");
			
			 driver = new AndroidDriver(service.getUrl(), options);
			 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			 formPage= new FormPage(driver);
		}
		else {
			DesiredCapabilities capabilities =getCap();
			capabilities.setCapability("deviceName", "Galaxy Note 10+");
//			capabilities.setCapability("platformName", "Android");
			String username="bhavyag_s5W0qT";
			 String accessKey="xHcn2eY8LwpTAtu5pLDn";
			 String server="hub-cloud.browserstack.com";
			driver = new AndroidDriver(new URL("http://"+username+":"+accessKey+"@"+server+"/wd/hub"), capabilities);
			
			capabilities.setCapability("autoGrantPermissions",true);
			formPage= new FormPage(driver);
			
		}
		
								
//		UiAutomator2Options options=new UiAutomator2Options();
//		options.setDeviceName("Android Emulator");
//		options.setPlatformName("Android");
//		options.setCapability("platformVersion","9.0");
//		options.setApp("C:\\Users\\shusharm35\\Downloads\\WikipediaSample.apk");
//			
//			 driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
//			 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//			 formPage= new FormPage(driver);
	}
	
	


	
	@AfterClass(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
        service.stop();
		}
	
}