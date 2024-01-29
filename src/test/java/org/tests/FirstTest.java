package org.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.ios_support.BrowserStackTestNGTest_ios;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.*;
import utils.BrowserStackTestNGTest;



public class FirstTest extends BrowserStackTestNGTest_ios {

  @Test
  public void test() throws Exception {
    WebElement textButton = (WebElement) new WebDriverWait(driver, Duration.ofMillis(3000)).until(
        ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Text Button")));
    textButton.click();
    WebElement textInput = (WebElement) new WebDriverWait(driver, Duration.ofMillis(3000)).until(
        ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Text Input")));
    textInput.sendKeys("hello@browserstack.com"+"\n");

    Thread.sleep(5000);

    WebElement textOutput = (WebElement) new WebDriverWait(driver, Duration.ofMillis(3000)).until(
        ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Text Output")));

    Assert.assertEquals(textOutput.getText(),"hello@browserstack.com");
  }
}
