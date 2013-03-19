package jp.co.amway.aurora.test.Loginwau0104;

import jp.co.amway.aurora.test.util.AuroraTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class D01004010 extends AuroraTestCase {
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testD01004010() throws Exception {
		driver.get(baseUrl
				+ "aurora/source/ts/uc_common/view/WA-C-01-F012.html");
		driver.findElement(
				By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
		driver.findElement(By.id("aMenuLogin")).click();
		driver.findElement(By.linkText("パスワードを忘れた方")).click();
		driver.findElement(By.id("txtIboNumber")).clear();
		driver.findElement(By.id("txtIboNumber")).sendKeys("5243017");
		driver.findElement(By.id("txtPin")).clear();
		driver.findElement(By.id("txtPin")).sendKeys("1061");
		driver.findElement(By.cssSelector("#btnSetPassword > div")).click();
		assertEquals("ディストリビューター番号（お客様番号）または暗証番号が、正しく入力されておりません。\n(JWS5336E)",
				closeAlertAndGetItsText());
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
