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

	// ポイント照会画面の確認
	@Test
	public void testWaR0201010101() throws Exception {
		// 01:Auroraのホーム画面を開く
		driver.get(baseUrl);
		// testUtil.createScreenShot(driver);
		// 02:ホーム画面で「ポイント照会」ボタンをタップする
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("div.btn_categorylist_04"))).click();
		// testUtil.createScreenShot(driver);
		// 03:ログイン画面でIDとパスワードを入力して、「ログイン」ボタンをタップする
		driver.findElement(By.id("txtUserId")).clear();
		// --ID：9900007
		driver.findElement(By.id("txtUserId")).sendKeys("9900007");
		driver.findElement(By.id("txtPassword")).clear();
		// --パスワード：testpass
		driver.findElement(By.id("txtPassword")).sendKeys("testpass");
		// --「ログイン」ボタンをタップする
		driver.findElement(By.cssSelector("#btnLogin > div")).click();
		// testUtil.createScreenShot(driver);
		// 04:ポイント照会画面で「前月」ボタンをタップする
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("#btnLMonth > div"))).click();
		// testUtil.createScreenShot(driver);
		// 05:ポイント照会画面で「ポイント非表示」ボタンをタップする
		driver.findElement(By.cssSelector("#btnPointShow > div")).click();
		// 06:ポイント照会画面で「ポイント表示」ボタンをタップする
		driver.findElement(By.id("btnPointShow")).click();
		// 07:ポイント照会画面でで「ヘルプ」アイコンをタップする
		driver.findElement(By.cssSelector("img[alt=\"ヘルプ\"]")).click();
	}

	// グル－プリーダー一覧画面の確認
	@Test
	public void testWaR0201010102() throws Exception {
		// 01:グローバルナビゲーションで「スポンサーシップマップ」ボタンをタップする
		// --グローバルナビゲーションをタップする。
		driver.findElement(
				By.cssSelector("div.site-header > div.g-header > a.naviOpen.naviOpenBtn > div > span"))
				.click();
		// --「スポンサーシップマップ」ボタンをタップする
		driver.findElement(By.cssSelector("a.gnav-sponsor.mLink > div"))
				.click();

		// 02:スポンサーシップマップ画面の「当月」ボタンをタップし、「グループリーダー一覧」ボタンをタップする
//		(new WebDriverWait(driver, 15)).until(
//				ExpectedConditions.presenceOfElementLocated(By
//						.cssSelector("#btnTMonth > div"))).click();
		
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.stalenessOf(driver.findElement(By
						.id("btnTMonth"))));		
		driver.findElement(By.id("btnTMonth")).click();
	
		// 03:グル－プリーダー一覧画面で「フロント」ボタンをタップする
		driver.findElement(By.id("divGroupBtn")).click();
		// 04:グル－プリーダー一覧画面で「お気に入り」ボタンをタップする
		driver.findElement(By.cssSelector("#btnGroupMid > div")).click();
		driver.findElement(By.cssSelector("#btnGroupRight > div")).click();
		// 05:グル－プリーダー一覧画面で「グループ別」ボタンをタップする
		driver.findElement(By.cssSelector("#btnGroupLeft > div")).click();
		// 06:グル－プリーダー一覧画面で「ヘルプ」アイコンをタップする
		driver.findElement(
				By.cssSelector("#main-contents > #site-header > #title > a.help-icon > div > img[alt=\"ヘルプ\"]"))
				.click();
		// 07:ヘルプ画面で「戻る」ボタンをタップする
		driver.findElement(By.cssSelector("#btnBack > div > img[alt=\"戻る\"]"))
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
		driver.findElement(By.cssSelector("img[alt=\"ヘルプ\"]")).click();

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
				By.cssSelector("#main-contents > #site-header > #title > a.help-icon > div > img[alt=\"ヘルプ\"]"))
				.click();
		driver.findElement(By.cssSelector("#btnBack > div > img[alt=\"戻る\"]"))
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
		// assertEquals("お気に入りに登録しました。\n(JAU8223I)",alert.getText());
		System.out.println(alert.getText());
		alert.accept();
		System.out.println(alert.getText());
		alert.accept();

		// assertEquals("お気に入りに登録します。よろしいですか？\n(JAU8216I)",
		// closeAlertAndGetItsText());
		// assertEquals("お気に入りに登録しました。\n(JAU8223I)", closeAlertAndGetItsText());

		// driver.findElement(By.cssSelector("#btnBack > div > img[alt=\"戻る\"]"))
		// .click();
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("#btnBack > div > img[alt=\"戻る\"]")))
				.click();
		driver.findElement(
				By.xpath("//ul[@id='ulGroupListView']/li[6]/a/div/dl/dd/dl/dd[2]"))
				.click();

		testUtil.createScreenShot(driver);

		driver.findElement(By.id("btnFavDo")).click();
		// assertEquals("お気に入りから削除します。よろしいですか？\n(JAU8217I)",
		// closeAlertAndGetItsText());
		// assertEquals("お気に入りから削除しました。\n(JAU8222I)",
		// closeAlertAndGetItsText());
		alert.accept();
		alert.accept();

		driver.findElement(By.cssSelector("#btnBack > div > img[alt=\"戻る\"]"))
				.click();
		driver.findElement(By.cssSelector("img[alt=\"戻る\"]")).click();
		// driver.findElement(By.cssSelector("#btnIndvidSearch > div")).click();
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("#btnIndvidSearch > div"))).click();

		// assertEquals("ディストリビュータ番号が正しく入力されておりません。\n(JAU8204E)",
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
