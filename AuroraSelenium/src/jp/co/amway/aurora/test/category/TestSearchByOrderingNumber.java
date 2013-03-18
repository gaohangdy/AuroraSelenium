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

	//�J�^���O�{�^���̃^�C�g�������؂���B
	@Test
	public void testCheckCategoryBtn() throws Exception {
		driver.findElement(By.cssSelector("div.btn_categorylist_01")).getText();
		
		WebElement elementCategoryBtn = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector("div.btn_categorylist_01")));
		//�J�^���O�{�^���̃^�C�g�������؂���B
		assertEquals("�J�^���O", elementCategoryBtn.getAttribute("title"));
	}
	
	//�J�^���O�{�^�����N���b�N����ƁA���i�J�^���O��ʂɑJ�ڂ��邩�Ƃ��B
	@Test
	public void testClickCategoryBtn() throws Exception {
		//�J�^���O�{�^�����N���b�N����B
		driver.findElement(By.cssSelector("div.btn_categorylist_01")).click();
		//�u���i�J�^���O�v��ʂ̃^�C�g�������؂���B
		(new WebDriverWait(driver, 10))
		.until(ExpectedConditions.titleIs("���i�J�^���O | amwaylive"));		
	}

	//�u���i���E�����ԍ����猟������v�����N���N���b�N����ƁA�u���i���E�����ԍ����́v��ʂɑJ�ڂ��邩�Ƃ�
	@Test
	public void testClickOrderingNumberLink() throws Exception {
		//�u���i���E�����ԍ����猟������v�����N���N���b�N����
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
