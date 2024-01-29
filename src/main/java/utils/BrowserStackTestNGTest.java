package utils;

import com.browserstack.local.Local;

import java.net.URL;
import java.util.Map;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;;

public class BrowserStackTestNGTest {
	public IOSDriver driver;
	JSONObject config;
	DesiredCapabilities capabilities;
	

	@BeforeMethod(alwaysRun=true)
  public void setUp() throws Exception {
		capabilities =getCap();
    
    
//    if(System.getProperty("OS").equalsIgnoreCase("android")){
//    AndroidDriver driver = new AndroidDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities);
//    capabilities.setCapability("autoGrantPermissions",true);
//    }
//    else if(System.getProperty("OS").equalsIgnoreCase("ios")){
//    IOSDriver driver = new IOSDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities);
//  }
//    else {
//    	System.out.println("The OS specified is not correct please check");
//    }

	}
	  @AfterMethod(alwaysRun=true)
	  public void tearDown() throws Exception {
	    
	    driver.quit();
	  }
  
  public DesiredCapabilities getCap() throws FileNotFoundException, IOException, ParseException {
	  JSONParser parser = new JSONParser();
	  if(System.getProperty("OS").equalsIgnoreCase("ios")) {
	    	 config = (JSONObject) parser.parse(new FileReader("src/main/java/resources/first.conf.json"));
	  }
	    else if(System.getProperty("OS").equalsIgnoreCase("Android")) {
	    	config = (JSONObject) parser.parse(new FileReader("src/main/java/resources/android_browserStack.json"));
	    }
	    
	  else {
		   System.out.println("The OS specified is invalid");
	  }
  
	  
	  DesiredCapabilities capabilities = new DesiredCapabilities();
	  JSONArray envs = (JSONArray) config.get("environments");

	    Map<String, String> envCapabilities = (Map<String, String>) envs.get(0);
	    Iterator it = envCapabilities.entrySet().iterator();
	    while (it.hasNext()) {
	      Map.Entry pair = (Map.Entry)it.next();
	      capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
	    }
	    
	    Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
	    it = commonCapabilities.entrySet().iterator();
	    while (it.hasNext()) {
	      Map.Entry pair = (Map.Entry)it.next();
	      if(capabilities.getCapability(pair.getKey().toString()) == null){
	          capabilities.setCapability(pair.getKey().toString(), pair.getValue());
	      }
	    }
	   
//	    capabilities.setCapability("PlatformName","Android");
        String app = System.getenv("BROWSERSTACK_APP_ID");
        if(app != null && !app.isEmpty()) {
          capabilities.setCapability("app", app);
        }
        
        return capabilities;
	    
	  
  }
}