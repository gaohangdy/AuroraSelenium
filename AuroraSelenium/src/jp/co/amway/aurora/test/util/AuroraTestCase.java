package jp.co.amway.aurora.test.util;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.TestCase;

public class AuroraTestCase extends TestCase {
	protected TestUtil testUtil = new TestUtil();
	protected WebDriver driver;
	protected String baseUrl;
	protected boolean acceptNextAlert = true;

	@Before
	public void setUp() throws Exception {
		// Create folder for save screenshot
		testUtil.createScreenShotFolder();
		// //--Chrome
		// DesiredCapabilities caps = DesiredCapabilities.chrome();
		// caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		//
		// driver = new ChromeDriver(caps);
		// driver.manage().deleteAllCookies();
		// baseUrl = "https://ipdev.amwaylive.com/";
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// --Android
		DesiredCapabilities caps = DesiredCapabilities.android();
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		driver = new AndroidDriver(caps);
		driver.manage().deleteAllCookies();
		baseUrl = "https://ipdev.amwaylive.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	protected String closeAlertAndGetItsText() {	
		try {
			(new WebDriverWait(driver, 10)).until(ExpectedConditions
					.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			String alertMessage = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertMessage;
		} finally {
			acceptNextAlert = true;
		}
	}
}
