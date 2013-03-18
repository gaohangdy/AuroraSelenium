package jp.co.amway.aurora.test.category;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSearchByOrderingNumber {
	private static WebDriver driver;
	private static String baseUrl;
	private static boolean acceptNextAlert = true;
	private static StringBuffer verificationErrors = new StringBuffer();
	
    @BeforeClass
    public static void setUpClass() {
		System.setProperty("webdriver.chrome.driver",
				"/Work_dir/driver/chromedriver_win_26.0.1383.0/chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "https://ipdev.amwaylive.com/sp/uc_common/view/WA-C-01-F012.html";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl);    	
    }
    
    @AfterClass
    public static void tearDownClass() {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}    	
    }
    
	@Before
	public void setUp() throws Exception {
//		System.setProperty("webdriver.chrome.driver",
//				"/Work_dir/driver/chromedriver_win_26.0.1383.0/chromedriver.exe");
//		driver = new ChromeDriver();
//		baseUrl = "https://ipdev.amwaylive.com/sp/uc_common/view/WA-C-01-F012.html";
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		driver.get(baseUrl);
	}

	//カタログボタンのタイトルを検証する。
	@Test
	public void testCheckCategoryBtn() throws Exception {
		driver.findElement(By.cssSelector("div.btn_categorylist_01")).getText();
		
		WebElement elementCategoryBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("div.btn_categorylist_01")));
		//カタログボタンのタイトルを検証する。
		assertEquals("カタログ", elementCategoryBtn.getAttribute("title"));
	}
	
	//カタログボタンをクリックすると、製品カタログ画面に遷移するかとか。
	@Test
	public void testClickCategoryBtn() throws Exception {
		//カタログボタンをクリックする。
		driver.findElement(By.cssSelector("div.btn_categorylist_01")).click();
		//「製品カタログ」画面のタイトルを検証する。
		(new WebDriverWait(driver, 10))
		.until(ExpectedConditions.titleIs("製品カタログ | amwaylive"));		
	}

	//「製品名・発注番号から検索する」リンクをクリックすると、「製品名・発注番号入力」画面に遷移するかとか
	@Test
	public void testClickOrderingNumberLink() throws Exception {
		//「製品名・発注番号から検索する」リンクをクリックする
		driver.findElement(By.xpath("//a[@id='aSearch']/div[2]")).click();
	}
	
	@Test
	@Ignore
	public void testSearchByOrderingNumber() throws Exception {
		driver.get(baseUrl + "/sp/uc_common/view/WA-C-01-F012.html");
		driver.findElement(By.cssSelector("div.btn_categorylist_01")).click();
		driver.findElement(By.xpath("//a[@id='aSearch']/div[2]")).click();
		driver.findElement(By.id("txtSearchData")).clear();
		driver.findElement(By.id("txtSearchData")).sendKeys("12");
		driver.findElement(By.cssSelector("div.btn_categorylist_01")).click();
		driver.findElement(By.xpath("//a[@id='aPurpose']/div[2]")).click();
		driver.findElement(
				By.cssSelector("#aCategoryListLine_2 > div.omission-div"))
				.click();
		driver.findElement(By.cssSelector("p.p-name")).click();
		driver.findElement(By.cssSelector("dd.p-official-name")).click();
	}

	@After
	public void tearDown() throws Exception {
//		driver.quit();
//		String verificationErrorString = verificationErrors.toString();
//		if (!"".equals(verificationErrorString)) {
//			fail(verificationErrorString);
//		}
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
