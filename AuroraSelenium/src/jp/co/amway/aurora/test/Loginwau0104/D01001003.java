package jp.co.amway.aurora.test.Loginwau0104;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import jp.co.amway.aurora.test.util.AuroraTestCase;
import junit.framework.TestCase;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class D01001003 extends AuroraTestCase {
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  super.setUp();
  }

  @Test
  public void testD01001003() throws Exception {
    driver.get(baseUrl + "/aurora/source/ts/uc_common/view/WA-C-01-F012.html");
    driver.findElement(By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
    driver.findElement(By.id("aMenuLogin")).click();
	(new WebDriverWait(driver, 10)).until(
			ExpectedConditions.elementToBeClickable(By.linkText("パスワードを忘れた方"))).click();    
    driver.findElement(By.id("txtIboNumber")).clear();
    driver.findElement(By.id("txtIboNumber")).sendKeys("5243017");
    driver.findElement(By.id("txtPin")).clear();
    driver.findElement(By.id("txtPin")).sendKeys("8300");
    driver.findElement(By.cssSelector("#btnSetPassword > div")).click();
    driver.findElement(By.cssSelector("#btnToHome > div")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }
}
