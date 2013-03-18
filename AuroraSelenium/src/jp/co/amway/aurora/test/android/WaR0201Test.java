package jp.co.amway.aurora.test.android;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import jp.co.amway.aurora.test.util.TestUtil;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaR0201Test {
	private static WebDriver driver;
	private static String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private TestUtil testUtil = new TestUtil();

	@BeforeClass
	public static void setUpClass() {
		DesiredCapabilities caps = DesiredCapabilities.android();
		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		driver = new AndroidDriver(caps);
		driver.manage().deleteAllCookies();
		baseUrl = "https://ipdev.amwaylive.com/sp/uc_common/view/WA-C-01-F012.html";// ?interstitial=false
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		TestUtil.deleteScreenShot(TestUtil.SCREENSHOT_PATH + "\\"
				+ new Throwable().getStackTrace()[0].getClassName());
	}

	@Before
	public void setUp() throws Exception {
		testUtil.createScreenShotFolder();
		// DesiredCapabilities caps = DesiredCapabilities.android();
		// caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		//
		// driver = new AndroidDriver(caps);
		// driver.manage().deleteAllCookies();
		// baseUrl =
		// "https://ipdev.amwaylive.com/sp/uc_common/view/WA-C-01-F012.html?interstitial=false";
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
		// driver.quit();
		// String verificationErrorString = verificationErrors.toString();
		// if (!"".equals(verificationErrorString)) {
		// fail(verificationErrorString);
		// }
	}

	@AfterClass
	public static void tearDownClass() {
		driver.quit();
	}

	// �|�C���g�Ɖ��ʂ̊m�F
	@Test
	public void testWaR0201010101() throws Exception {
		// 01:Aurora�̃z�[����ʂ��J��
		driver.get(baseUrl);
		// testUtil.createScreenShot(driver);
		// 02:�z�[����ʂŁu�|�C���g�Ɖ�v�{�^�����^�b�v����
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("div.btn_categorylist_04"))).click();
		// testUtil.createScreenShot(driver);
		// 03:���O�C����ʂ�ID�ƃp�X���[�h����͂��āA�u���O�C���v�{�^�����^�b�v����
		driver.findElement(By.id("txtUserId")).clear();
		// --ID�F9900007
		driver.findElement(By.id("txtUserId")).sendKeys("9900007");
		driver.findElement(By.id("txtPassword")).clear();
		// --�p�X���[�h�Ftestpass
		driver.findElement(By.id("txtPassword")).sendKeys("testpass");
		// --�u���O�C���v�{�^�����^�b�v����
		driver.findElement(By.cssSelector("#btnLogin > div")).click();
		// testUtil.createScreenShot(driver);
		// 04:�|�C���g�Ɖ��ʂŁu�O���v�{�^�����^�b�v����
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("#btnLMonth > div"))).click();
		// testUtil.createScreenShot(driver);
		// 05:�|�C���g�Ɖ��ʂŁu�|�C���g��\���v�{�^�����^�b�v����
		driver.findElement(By.cssSelector("#btnPointShow > div")).click();
		// 06:�|�C���g�Ɖ��ʂŁu�|�C���g�\���v�{�^�����^�b�v����
		driver.findElement(By.id("btnPointShow")).click();
		// 07:�|�C���g�Ɖ��ʂłŁu�w���v�v�A�C�R�����^�b�v����
		driver.findElement(By.cssSelector("img[alt=\"�w���v\"]")).click();
	}

	// �O���|�v���[�_�[�ꗗ��ʂ̊m�F
	@Test
	public void testWaR0201010102() throws Exception {
		// 01:�O���[�o���i�r�Q�[�V�����Łu�X�|���T�[�V�b�v�}�b�v�v�{�^�����^�b�v����
		// --�O���[�o���i�r�Q�[�V�������^�b�v����B
		driver.findElement(
				By.cssSelector("div.site-header > div.g-header > a.naviOpen.naviOpenBtn > div > span"))
				.click();
		// --�u�X�|���T�[�V�b�v�}�b�v�v�{�^�����^�b�v����
		driver.findElement(By.cssSelector("a.gnav-sponsor.mLink > div"))
				.click();

		// 02:�X�|���T�[�V�b�v�}�b�v��ʂ́u�����v�{�^�����^�b�v���A�u�O���[�v���[�_�[�ꗗ�v�{�^�����^�b�v����
//		(new WebDriverWait(driver, 15)).until(
//				ExpectedConditions.presenceOfElementLocated(By
//						.cssSelector("#btnTMonth > div"))).click();
		
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.stalenessOf(driver.findElement(By
						.id("btnTMonth"))));		
		driver.findElement(By.id("btnTMonth")).click();
	
		// 03:�O���|�v���[�_�[�ꗗ��ʂŁu�t�����g�v�{�^�����^�b�v����
		driver.findElement(By.id("divGroupBtn")).click();
		// 04:�O���|�v���[�_�[�ꗗ��ʂŁu���C�ɓ���v�{�^�����^�b�v����
		driver.findElement(By.cssSelector("#btnGroupMid > div")).click();
		driver.findElement(By.cssSelector("#btnGroupRight > div")).click();
		// 05:�O���|�v���[�_�[�ꗗ��ʂŁu�O���[�v�ʁv�{�^�����^�b�v����
		driver.findElement(By.cssSelector("#btnGroupLeft > div")).click();
		// 06:�O���|�v���[�_�[�ꗗ��ʂŁu�w���v�v�A�C�R�����^�b�v����
		driver.findElement(
				By.cssSelector("#main-contents > #site-header > #title > a.help-icon > div > img[alt=\"�w���v\"]"))
				.click();
		// 07:�w���v��ʂŁu�߂�v�{�^�����^�b�v����
		driver.findElement(By.cssSelector("#btnBack > div > img[alt=\"�߂�\"]"))
				.click();
	}

	@Test
	@Ignore
	public void testWrR0201() throws Exception {
		// 01
		driver.get(baseUrl);
		testUtil.createScreenShot(driver);
		driver.findElement(By.cssSelector("div.btn_categorylist_04")).click();
		testUtil.createScreenShot(driver);
		driver.findElement(By.id("txtUserId")).clear();
		driver.findElement(By.id("txtUserId")).sendKeys("9900007");
		driver.findElement(By.id("txtPassword")).clear();
		driver.findElement(By.id("txtPassword")).sendKeys("testpass");
		driver.findElement(By.cssSelector("#btnLogin > div")).click();
		testUtil.createScreenShot(driver);
		// driver.findElement(By.cssSelector("#btnLMonth > div")).click();
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("#btnLMonth > div"))).click();
		testUtil.createScreenShot(driver);
		driver.findElement(By.cssSelector("#btnPointShow > div")).click();
		driver.findElement(By.id("btnPointShow")).click();
		driver.findElement(By.cssSelector("img[alt=\"�w���v\"]")).click();

		// 02
		driver.findElement(
				By.cssSelector("div.site-header > div.g-header > a.naviOpen.naviOpenBtn > div > span"))
				.click();
		driver.findElement(By.cssSelector("a.gnav-sponsor.mLink > div"))
				.click();

		// driver.findElement(By.cssSelector("#btnTMonth > div")).click();
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("#btnTMonth > div"))).click();
		driver.findElement(By.id("divGroupBtn")).click();
		driver.findElement(By.cssSelector("#btnGroupMid > div")).click();
		driver.findElement(By.cssSelector("#btnGroupRight > div")).click();
		driver.findElement(By.cssSelector("#btnGroupLeft > div")).click();
		driver.findElement(
				By.cssSelector("#main-contents > #site-header > #title > a.help-icon > div > img[alt=\"�w���v\"]"))
				.click();
		driver.findElement(By.cssSelector("#btnBack > div > img[alt=\"�߂�\"]"))
				.click();
		// 03
		driver.findElement(
				By.cssSelector("a[name=\"aGroupListLine_352e18f7099a7b6c4630e2fbb3d2cf34d4f8f16b\"] > div.pv-list > dl.pv-cnt > dt.pv-rank > div.omission-div.pv-rank-name"))
				.click();
		driver.findElement(By.id("btnFavDo")).click();
		testUtil.createScreenShot(driver);

		// // assuming your driver can handle JS ;)
		// JavascriptExecutor js = (JavascriptExecutor)driver;
		//
		// // stores the original confirm() function and replaces it
		// js.executeScript("window.originalConfirm = window.confirm;"
		// + "window.confirm = function(m) { return true; };");
		// "window.confirm = function(msg) { return true; }"
		// // get the confirm back
		// js.executeScript("window.confirm = window.originalConfirm;");
		//

		// ((JavascriptExecutor)driver).executeScript("window.confirm = function(msg){return true;};");
		Alert alert = driver.switchTo().alert();
		// assertEquals("���C�ɓ���ɓo�^���܂����B\n(JAU8223I)",alert.getText());
		System.out.println(alert.getText());
		alert.accept();
		System.out.println(alert.getText());
		alert.accept();

		// assertEquals("���C�ɓ���ɓo�^���܂��B��낵���ł����H\n(JAU8216I)",
		// closeAlertAndGetItsText());
		// assertEquals("���C�ɓ���ɓo�^���܂����B\n(JAU8223I)", closeAlertAndGetItsText());

		// driver.findElement(By.cssSelector("#btnBack > div > img[alt=\"�߂�\"]"))
		// .click();
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("#btnBack > div > img[alt=\"�߂�\"]")))
				.click();
		driver.findElement(
				By.xpath("//ul[@id='ulGroupListView']/li[6]/a/div/dl/dd/dl/dd[2]"))
				.click();

		testUtil.createScreenShot(driver);

		driver.findElement(By.id("btnFavDo")).click();
		// assertEquals("���C�ɓ��肩��폜���܂��B��낵���ł����H\n(JAU8217I)",
		// closeAlertAndGetItsText());
		// assertEquals("���C�ɓ��肩��폜���܂����B\n(JAU8222I)",
		// closeAlertAndGetItsText());
		alert.accept();
		alert.accept();

		driver.findElement(By.cssSelector("#btnBack > div > img[alt=\"�߂�\"]"))
				.click();
		driver.findElement(By.cssSelector("img[alt=\"�߂�\"]")).click();
		// driver.findElement(By.cssSelector("#btnIndvidSearch > div")).click();
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("#btnIndvidSearch > div"))).click();

		// assertEquals("�f�B�X�g���r���[�^�ԍ������������͂���Ă���܂���B\n(JAU8204E)",
		// closeAlertAndGetItsText());
		alert.accept();
		driver.findElement(By.id("txtInputID")).clear();
		driver.findElement(By.id("txtInputID")).sendKeys("9900006");
		driver.findElement(By.cssSelector("#btnIndvidSearch > div")).click();
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
		String alertMessage = "";
		try {
			Alert alert = driver.switchTo().alert();
			alertMessage = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			// return alert.getText();
			return alertMessage;
		} catch (Exception ex) {
			System.out.print(ex);
			return ex.getMessage();
		} finally {
			acceptNextAlert = true;
		}
	}
}
