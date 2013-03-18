package jp.co.amway.aurora.test;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class chromeTest {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver",
				"/Work_dir/driver/chromedriver_win_26.0.1383.0/chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "https://ipdev.amwaylive.com/sp/uc_common/view/WA-C-01-F012.html";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	@Ignore
	public void testAmway01() throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.cssSelector("div.btn_categorylist_01")).click();
		driver.findElement(By.xpath("//a[@id='aSearch']/div[2]")).click();
		driver.findElement(By.id("txtSearchData")).clear();
		driver.findElement(By.id("txtSearchData")).sendKeys("12");
		driver.findElement(By.cssSelector("dd.p-official-name")).click();

		// check orderingNumber of page
		assertTrue((new WebDriverWait(driver, 5))
				.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver d) {
						return "î≠íçî‘çÜÅF0012".equals(d.findElement(
								By.id("pOrderingNumber")).getText());
					}
				}));

		// Explicit Waits
		WebElement elementOrderingNum = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id("pOrderingNumber")));
		assertEquals("î≠íçî‘çÜÅF0012", elementOrderingNum.getText());
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File screenshot = ((TakesScreenshot) augmentedDriver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File("C:/Work_dir/screenshot/"
				+ screenshot.getName()));

	}

	@Test
//	@Ignore
	public void tesAmway02() throws Exception {
		//To run this testcase,Firstly please startup [Standalone Selenium Server]
		//command:java -jar %path%\selenium-server-standalone-2.30.0.jar
		//add chromedriver.exe's folder to system variable.
		WebDriver driver11 = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
				DesiredCapabilities.chrome());
		driver11.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver11.get(baseUrl);
		
		driver11.findElement(By.cssSelector("div.btn_categorylist_01")).click();
		driver11.findElement(By.xpath("//a[@id='aSearch']/div[2]")).click();
		driver11.findElement(By.id("txtSearchData")).clear();
		driver11.findElement(By.id("txtSearchData")).sendKeys("12");
		driver11.findElement(By.cssSelector("dd.p-official-name")).click();	
		
		driver11.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		
		// RemoteWebDriver does not implement the TakesScreenshot class
		// if the driver does have the Capabilities to take a screenshot
		// then Augmenter will add the TakesScreenshot methods to the instance
		WebDriver augmentedDriver = new Augmenter().augment(driver11);
		File screenshot = ((TakesScreenshot) augmentedDriver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File("C:/Work_dir/screenshot/"
				+ screenshot.getName()));	
		
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
