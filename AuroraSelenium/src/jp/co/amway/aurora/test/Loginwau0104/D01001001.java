package jp.co.amway.aurora.test.Loginwau0104;

import jp.co.amway.aurora.test.util.AuroraTestCase;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class D01001001 extends AuroraTestCase {
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testD01001001() throws Exception {
		driver.get(baseUrl
				+ "/aurora/source/ts/uc_common/view/WA-C-01-F012.html");
		testUtil.createScreenShot(driver);
		driver.findElement(
				By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
		driver.findElement(By.id("aMenuLogin")).click();
		testUtil.createScreenShot(driver);
		(new WebDriverWait(driver, 20)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.linkText("パスワードを忘れた方"))).click();
		testUtil.createScreenShot(driver);
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.id("txtIboNumber"))).clear();
		driver.findElement(By.id("txtIboNumber")).sendKeys("5243017");
		driver.findElement(By.id("txtPin")).clear();
		driver.findElement(By.id("txtPin")).sendKeys("8300");
		testUtil.createScreenShot(driver);
		driver.findElement(By.cssSelector("#btnSetPassword > div")).click();
		driver.findElement(By.cssSelector("#btnToHome > div")).click();
		testUtil.createScreenShot(driver);
	}

}
