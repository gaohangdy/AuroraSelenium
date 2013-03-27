package jp.co.amway.aurora.test.loginwau0104test;

import jp.co.amway.aurora.test.util.AuroraTestCase;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class D01001003 extends AuroraTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testD01001003() throws Exception {
        driver.get(baseUrl + "/aurora/source/ts/uc_common/view/WA-C-01-F012.html");
        driver.findElement(By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
        driver.findElement(By.id("aMenuLogin")).click();
        driver.findElement(By.linkText("パスワードを忘れた方")).click();
        driver.findElement(By.id("txtIboNumber")).clear();
        driver.findElement(By.id("txtIboNumber")).sendKeys(getTestValue("By.id(\"txtIboNumber\")", "sendKeys"));
        driver.findElement(By.id("txtPin")).clear();
        driver.findElement(By.id("txtPin")).sendKeys(getTestValue("By.id(\"txtPin\")", "sendKeys"));
        driver.findElement(By.cssSelector("#btnSetPassword > div")).click();
        driver.findElement(By.cssSelector("#btnToHome > div")).click();
    }
}
