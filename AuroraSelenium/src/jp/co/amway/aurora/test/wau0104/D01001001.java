package jp.co.amway.aurora.test.wau0104;

import jp.co.amway.aurora.test.util.AuroraTestCase;
import jp.co.amway.aurora.test.util.AuroraTestCaseHandler;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.lang.reflect.Proxy;

public class D01001001 extends AuroraTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testD01001001() throws Exception {
        driver.get(baseUrl + "/aurora/source/ts/uc_common/view/WA-C-01-F012.html");
//        WebElement el = driver.findElement(By.cssSelector("a.naviOpen.naviOpenBtn > div > span"));
//        //拦截器对象  
//        AuroraTestCaseHandler handler = new AuroraTestCaseHandler(el);   
//           
//        //返回业务对象的代理对象  
//        WebElement proxy = (WebElement)Proxy.newProxyInstance(   
//        		el.getClass().getClassLoader(),    
//        		el.getClass().getInterfaces(),    
//                handler);   
//           
//        //通过代理对象执行业务对象的方法  
//        proxy.click();   
        findElement(By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
//        driver.findElement(By.cssSelector("a.naviOpen.naviOpenBtn > div > span")).click();
        driver.findElement(By.id("aMenuLogin")).click();
        driver.findElement(By.linkText("パスワードを忘れた方")).click();
        driver.findElement(By.id("txtIboNumber")).clear();
        driver.findElement(By.id("txtIboNumber")).sendKeys(getTestValue("By.id(\"txtIboNumber\")", "sendKeys"));
        driver.findElement(By.id("txtPin")).clear();
        driver.findElement(By.id("txtPin")).sendKeys(getTestValue("By.id(\"txtPin\")", "sendKeys"));
        driver.findElement(By.cssSelector("#btnSetPassword > div")).click();
        driver.findElement(By.cssSelector("#btnToHome > div")).click();
    }
}
