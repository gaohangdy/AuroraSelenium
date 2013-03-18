package jp.co.amway.aurora.test.android;

import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

public class WaR020101Test {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		DesiredCapabilities caps = DesiredCapabilities.android();
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		driver = new AndroidDriver(caps);
		baseUrl = "https://ipdev.amwaylive.com/sp/uc_common/view/WA-C-01-F012.html";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testWar0201() throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.cssSelector("div.btn_categorylist_04")).click();
		driver.findElement(By.id("txtUserId")).clear();
		driver.findElement(By.id("txtUserId")).sendKeys("9900007");
		driver.findElement(By.id("txtPassword")).clear();
		driver.findElement(By.id("txtPassword")).sendKeys("testpass");
		driver.findElement(By.cssSelector("#btnLogin > div")).click();
		driver.findElement(By.cssSelector("#btnPointShow > div")).click();
		driver.findElement(By.id("btnPointShow")).click();
		driver.findElement(By.cssSelector("img[alt=\"�w���v\"]")).click();
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
