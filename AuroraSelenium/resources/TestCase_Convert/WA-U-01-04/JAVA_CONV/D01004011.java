package jp.co.amway.aurora.test.loginwau0104test;

import jp.co.amway.aurora.test.util.AuroraTestCase;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class D01004011 extends AuroraTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testD01004011() throws Exception {
        driver.get(baseUrl + "/aurora/source/ts/uc_common/view/WA-C-01-F012.html");
        driver.findElement(By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
        driver.findElement(By.id("aMenuLogin")).click();
        driver.findElement(By.linkText("パスワードを忘れた方")).click();
        driver.findElement(By.id("txtIboNumber")).clear();
        driver.findElement(By.id("txtIboNumber")).sendKeys(getTestValue("By.id(\"txtIboNumber\")", "sendKeys"));
        driver.findElement(By.id("chkHaveNoPinFlg")).click();
        driver.findElement(By.id("txtZipCodeThree")).clear();
        driver.findElement(By.id("txtZipCodeThree")).sendKeys(getTestValue("By.id(\"txtZipCodeThree\")", "sendKeys"));
        driver.findElement(By.id("txtZipCodeFour")).clear();
        driver.findElement(By.id("txtZipCodeFour")).sendKeys(getTestValue("By.id(\"txtZipCodeFour\")", "sendKeys"));
        driver.findElement(By.id("txtPhoneOutTown")).clear();
        driver.findElement(By.id("txtPhoneOutTown")).sendKeys(getTestValue("By.id(\"txtPhoneOutTown\")", "sendKeys"));
        driver.findElement(By.id("txtPhoneTown")).clear();
        driver.findElement(By.id("txtPhoneTown")).sendKeys(getTestValue("By.id(\"txtPhoneTown\")", "sendKeys"));
        driver.findElement(By.id("txtPhoneLocal")).clear();
        driver.findElement(By.id("txtPhoneLocal")).sendKeys(getTestValue("By.id(\"txtPhoneLocal\")", "sendKeys"));
        new Select(driver.findElement(By.id("selBirthYear"))).selectByVisibleText("1973");
        new Select(driver.findElement(By.id("selBirthMonth"))).selectByVisibleText("6");
        new Select(driver.findElement(By.id("selBirthDay"))).selectByVisibleText("28");
        driver.findElement(By.cssSelector("#btnSetPassword > div")).click();
        assertEquals("ディストリビューター番号（お客様番号）あるいは個人情報のいずれかが、正しく入力されておりません。 (JWS5337E)", closeAlertAndGetItsText());
    }
}
