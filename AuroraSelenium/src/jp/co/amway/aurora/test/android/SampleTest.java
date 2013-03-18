package jp.co.amway.aurora.test.android;

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
import org.openqa.selenium.iphone.IPhoneDriver;

public class SampleTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		DesiredCapabilities caps = DesiredCapabilities.iphone();
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		driver = new IPhoneDriver(caps);
		// driver = new FirefoxDriver();
		baseUrl = "https://ipdev.amwaylive.com/sp/uc_common/view/WA-C-01-F012.html";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testForAndroid() throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.cssSelector("div.btn_categorylist_01")).click();
		driver.findElement(By.xpath("//a[@id='aSearch']/div[2]")).click();
		driver.findElement(By.id("txtSearchData")).clear();
		driver.findElement(By.id("txtSearchData")).sendKeys("12");
		driver.findElement(By.cssSelector("dd.p-official-name")).click();
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
