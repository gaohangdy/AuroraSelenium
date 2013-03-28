package jp.co.amway.aurora.test.util;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.amway.aurora.test.bean.TestActionInfo;
import jp.co.amway.aurora.test.constant.AuroraSeleniumConst;
import jp.co.amway.aurora.test.tools.convert.parser.ReadTestCaseXls;
import junit.framework.TestCase;

public class AuroraTestCase extends TestCase {
	protected TestUtil testUtil = new TestUtil();
	protected WebDriver driver;
	protected String baseUrl;
	protected boolean acceptNextAlert = true;
	protected StringBuffer verificationErrors = new StringBuffer();
	private List<TestActionInfo> testActionList = new ArrayList<TestActionInfo>();

	@Before
	public void setUp() throws Exception {
		String path = getTestDoc();
		testActionList = new ReadTestCaseXls(path).getTestActionList();
		// Create folder for save screenshot
		testUtil.createScreenShotFolder(getTestSuiteFolder());
		// --Chrome
		if ("2".equals(AuroraSeleniumConst.DRIVER_TYPE)) {
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

			driver = new ChromeDriver(caps);
			driver.manage().deleteAllCookies();
			baseUrl = "https://ipdev.amwaylive.com/";
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} else if ("0".equals(AuroraSeleniumConst.DRIVER_TYPE)) {
			// --Android
			DesiredCapabilities caps = DesiredCapabilities.android();
			caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

			driver = new AndroidDriver(caps);
			driver.manage().deleteAllCookies();
			baseUrl = "https://ipdev.amwaylive.com/";
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} else if ("1".equals(AuroraSeleniumConst.DRIVER_TYPE)) {
			// --Firefox
			DesiredCapabilities caps = DesiredCapabilities.firefox();
			caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

			driver = new FirefoxDriver(caps);
			driver.manage().deleteAllCookies();
			baseUrl = "https://ipdev.amwaylive.com/";
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
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

	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private String getTestDoc() {
		String className = new Throwable().getStackTrace()[2].getClassName();
		String packageName = "";
		try {
			packageName = Class.forName(className).getPackage().getName();

			packageName = packageName.split("\\.")[packageName.split("\\.").length - 1];
			className = className.split("\\.")[className.split("\\.").length - 1];
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String path = FileOperateUtil.fetchTestSuiteFolder(packageName);
		if ("".equals(path)) {
			System.out.println("Can't find testcase document in folder[" + path
					+ "]");
		} else {
			path = path + "/DOC/" + className + ".xls";
		}
		return path;
	}

	private String getTestSuiteFolder() {
		String className = new Throwable().getStackTrace()[2].getClassName();
		String packageName = "";
		try {
			packageName = Class.forName(className).getPackage().getName();

			packageName = packageName.split("\\.")[packageName.split("\\.").length - 1];
			className = className.split("\\.")[className.split("\\.").length - 1];
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String path = FileOperateUtil.fetchTestSuiteFolder(packageName);
		if ("".equals(path)) {
			System.out.println("Can't find testcase document in folder[" + path
					+ "]");
		}
		return path;
	}

	protected String getTestValue(String param, String action) {
		for (TestActionInfo testAction : testActionList) {
			String cmpTxt = "By." + testAction.getBy() + "("
					+ testAction.getElement() + ")";
			if (cmpTxt.equals(param) && testAction.getAction().equals(action)) {
				return testAction.getValue();
			}
		}
		return "";
	}
	
	protected WebElement findElement(By by) {
        WebElement el = driver.findElement(by);
        //拦截器对象  
        AuroraTestCaseHandler handler = new AuroraTestCaseHandler(el, driver, by, testActionList, testUtil);   
           
        //返回业务对象的代理对象  
        WebElement proxy = (WebElement)Proxy.newProxyInstance(   
        		el.getClass().getClassLoader(),    
        		el.getClass().getInterfaces(),    
                handler);   
           return proxy;
//        //通过代理对象执行业务对象的方法  
//        proxy.click(); 
		
		
		
		
//		new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
//			@Override
//			public Boolean apply(WebDriver d) {
//				return true;
//			}
//		});
		
//		(new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("")))).click();
//		return null;
		
	}
}
