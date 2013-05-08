package jp.co.amway.aurora.test.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jp.co.amway.aurora.test.bean.TestActionInfo;
import jp.co.amway.aurora.test.constant.AuroraSeleniumConst;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuroraTestCaseHandler implements InvocationHandler {
	// private Object targetObject;
	private Object parentObject;
	private Object childObject;
	private List<TestActionInfo> testActionList;
	private TestUtil testUtil;
	private int fetchIndex;

	public AuroraTestCaseHandler(Object parentObject, Object childObject,
			List<TestActionInfo> testActionList, TestUtil testUtil) {
		// this.targetObject = targetObject;
		this.parentObject = parentObject;
		this.childObject = childObject;
		this.testActionList = testActionList;
		this.testUtil = testUtil;
		this.fetchIndex = -1;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		TestActionInfo testAction = fetchActionInfo(method);
		if (testAction != null && testAction.getComment() != null
				&& !"".equals(testAction.getComment())) {
			System.out.println("Excute Action Start : "
					+ testAction.getComment());
		}

		WebDriverWait wait = new WebDriverWait((WebDriver) parentObject,
				AuroraSeleniumConst.WAIT_PERIOD);
		try {
			((WebDriver) parentObject).manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			if (By.id("_BACKGROUND_ID_") != null) {
//				new WebDriverWait((WebDriver) parentObject,
//						AuroraSeleniumConst.WAIT_PERIOD).until(ExpectedConditions.invisibilityOfElementLocated(By
//						.id("_BACKGROUND_ID_")));
//			}
			switch (method.getName()) {
			case "click":
			case "submit":
				wait.until(ExpectedConditions
						.elementToBeClickable((By) childObject));
				break;
			case "sendKeys":
			case "clear":
				wait.until(ExpectedConditions
						.elementToBeClickable((By) childObject));
				break;
			default:
				wait.until(ExpectedConditions
						.presenceOfElementLocated((By) childObject));
			}
		} catch (Exception ex) {
			testActionList.get(fetchIndex).setStatus(false);
			testActionList.get(fetchIndex).setErrMsg(ex.getMessage());
			throw ex;
		}

		WebElement el = ((WebDriver) parentObject)
				.findElement((By) this.childObject);
		System.out.println("Execute method : " + method);
		System.out.println("WebElement : " + el);
		Object result = null;
		
		int count = 0; 
		while (count < 4){
			try {
				result = method.invoke(el, args);
				break;
			} catch (Exception ex) {
				if (count >= 4) {
					throw ex;
				}
				Thread.sleep(AuroraSeleniumConst.WAIT_PERIOD * 1000);
				count++;
			}
		}
		
		if (testAction != null) {
			if (testAction.isScreenShot()) {
				this.testUtil.createScreenShot((WebDriver) parentObject);
			}
			if (testAction.getComment() != null
					&& !"".equals(testAction.getComment())) {
				System.out.println("Excute Action Success : "
						+ testAction.getComment());
			}
		}
		return result;
	}

	private TestActionInfo fetchActionInfo(Method method) {
		int intStep = 0;
		for (TestActionInfo testAction : testActionList) {
//			System.out.println(("By" + testAction.getBy()).toLowerCase());
//			System.out.println(childObject.getClass().getName().split("\\$")[1]
//					.toLowerCase());
//			System.out.println(method.getName());
//			System.out.println(testAction.getAction());
//			System.out.println(childObject.toString().split(":")[1].trim());
//			System.out.println(testAction.getElement().replace("\"", ""));
//			System.out.println(("By" + testAction.getBy()).toLowerCase()
//					.equals(childObject.getClass().getName().split("\\$")[1]
//							.toLowerCase()));
//			System.out.println(method.getName().equals(testAction.getAction()));
//			System.out.println(childObject.toString().split(":")[1].trim()
//					.equals(testAction.getElement().replace("\"", "")));

			if (("By" + testAction.getBy()).toLowerCase().equals(
					childObject.getClass().getName().split("\\$")[1]
							.toLowerCase())
					&& method.getName().equals(testAction.getAction())
					&& childObject.toString().split(":")[1].trim().equals(
							testAction.getElement().replace("\"", ""))) {
				this.fetchIndex = intStep;
				return testAction;
			}
			intStep++;
		}
		return null;
	}

}
