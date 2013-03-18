package jp.co.amway.aurora.test.Loginwau0104;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class D01004011 extends TestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://ipdev.amwaylive.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testD01004011() throws Exception {
    driver.get(baseUrl + "/aurora/source/ts/uc_common/view/WA-C-01-F012.html");
    driver.findElement(By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
    driver.findElement(By.id("aMenuLogin")).click();
    driver.findElement(By.linkText("繝代せ繝ｯ繝ｼ繝峨ｒ蠢倥ｌ縺滓婿")).click();
    driver.findElement(By.id("txtIboNumber")).clear();
    driver.findElement(By.id("txtIboNumber")).sendKeys("5243017");
    driver.findElement(By.id("chkHaveNoPinFlg")).click();
    driver.findElement(By.id("txtZipCodeThree")).clear();
    driver.findElement(By.id("txtZipCodeThree")).sendKeys("011");
    driver.findElement(By.id("txtZipCodeFour")).clear();
    driver.findElement(By.id("txtZipCodeFour")).sendKeys("0042");
    driver.findElement(By.id("txtPhoneOutTown")).clear();
    driver.findElement(By.id("txtPhoneOutTown")).sendKeys("03");
    driver.findElement(By.id("txtPhoneTown")).clear();
    driver.findElement(By.id("txtPhoneTown")).sendKeys("5428");
    driver.findElement(By.id("txtPhoneLocal")).clear();
    driver.findElement(By.id("txtPhoneLocal")).sendKeys("7000");
    new Select(driver.findElement(By.id("selBirthYear"))).selectByVisibleText("1973");
    new Select(driver.findElement(By.id("selBirthMonth"))).selectByVisibleText("6");
    new Select(driver.findElement(By.id("selBirthDay"))).selectByVisibleText("28");
    driver.findElement(By.cssSelector("#btnSetPassword > div")).click();
    assertEquals("繝�ぅ繧ｹ繝医Μ繝薙Η繝ｼ繧ｿ繝ｼ逡ｪ蜿ｷ�医♀螳｢讒倡分蜿ｷ�峨≠繧九＞縺ｯ蛟倶ｺｺ諠��縺ｮ縺�★繧後°縺後�豁｣縺励￥蜈･蜉帙＆繧後※縺翫ｊ縺ｾ縺帙ｓ縲�(JWS5337E)", closeAlertAndGetItsText());
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

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alert.getText();
    } finally {
      acceptNextAlert = true;
    }
  }
}
