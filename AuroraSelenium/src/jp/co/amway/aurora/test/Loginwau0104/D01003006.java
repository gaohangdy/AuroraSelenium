package jp.co.amway.aurora.test.Loginwau0104;

import jp.co.amway.aurora.test.util.AuroraTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class D01003006 extends AuroraTestCase {
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testD01003006() throws Exception {
		driver.get(baseUrl
				+ "aurora/source/ts/uc_common/view/WA-C-01-F012.html");
		driver.findElement(
				By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
		driver.findElement(By.id("aMenuLogin")).click();
		driver.findElement(By.linkText("パスワードを忘れた方")).click();
		testUtil.createScreenShot(driver);
		driver.findElement(By.cssSelector("img[alt=\"ヘルプ\"]")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
