package jp.co.amway.aurora.test.Loginwau0104;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import jp.co.amway.aurora.test.util.AuroraTestCase;
import junit.framework.TestCase;

import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

public class D01001002 extends AuroraTestCase {	
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testD01001002() throws Exception {
		driver.get(baseUrl
				+ "/aurora/source/ts/uc_common/view/WA-C-01-F012.html");

		testUtil.createScreenShot(driver);
		driver.findElement(
				By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
		driver.findElement(By.id("aMenuLogin")).click();
		testUtil.createScreenShot(driver);
		driver.findElement(By.linkText("パスワードを忘れた方")).click();
		testUtil.createScreenShot(driver);
		driver.findElement(By.id("chkHaveNoPinFlg")).click();
		testUtil.createScreenShot(driver);
		driver.findElement(By.id("txtIboNumber")).clear();
		driver.findElement(By.id("txtIboNumber")).sendKeys("5243017");
		driver.findElement(By.id("txtZipCodeThree")).clear();
		driver.findElement(By.id("txtZipCodeThree")).sendKeys("010");
		driver.findElement(By.id("txtZipCodeFour")).clear();
		driver.findElement(By.id("txtZipCodeFour")).sendKeys("0042");
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.visibilityOfElementLocated(By.id("selBirthYear")));
		new Select(driver.findElement(By.id("selBirthYear")))
				.selectByValue("1973");
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.visibilityOfElementLocated(By.id("selBirthMonth")));
		new Select(driver.findElement(By.id("selBirthMonth")))
				.selectByValue("6");
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.visibilityOfElementLocated(By.id("selBirthDay")));
		new Select(driver.findElement(By.id("selBirthDay")))
				.selectByVisibleText("28");
		driver.findElement(By.cssSelector("#btnSetPassword > div")).click();
		//Confirm alert message.
		assertEquals("電話番号が正しく入力されておりません。\n(JAU4164E)", closeAlertAndGetItsText());
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
