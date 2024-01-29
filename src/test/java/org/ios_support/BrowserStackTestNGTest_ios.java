package org.ios_support;

import io.appium.java_client.ios.IOSDriver;
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

public class BrowserStackTestNGTest_ios {

	
	public IOSDriver driver;

	  @BeforeMethod(alwaysRun=true)
	  public void setUp() throws Exception {
	    JSONParser parser = new JSONParser();
	    JSONObject config = (JSONObject) parser.parse(new FileReader("src/main/java/resources/first.conf.json"));
	    JSONArray envs = (JSONArray) config.get("environments");

	    DesiredCapabilities capabilities = new DesiredCapabilities();

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

	    String username = System.getenv("BROWSERSTACK_USERNAME");
	    if(username == null) {
	      username = (String) config.get("username");
	    }

	    String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
	    if(accessKey == null) {
	      accessKey = (String) config.get("access_key");
	    }
	    
	    String app = System.getenv("BROWSERSTACK_APP_ID");
	    if(app != null && !app.isEmpty()) {
	      capabilities.setCapability("app", app);
	    }
	    String con =(String) config.get("server");
	    capabilities.setCapability("deviceName", "iPhone 11 Pro");
	    driver = new IOSDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities);
	  }

	  @AfterMethod(alwaysRun=true)
	  public void tearDown() throws Exception {
	    
	    driver.quit();
	  }
}