package jp.co.amway.aurora.test.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jp.co.amway.aurora.test.bean.TestActionInfo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuroraSelectHandler implements InvocationHandler {
	private Object targetObject;
	private Object parentObject;
//	private Object childObject;
	private List<TestActionInfo> testActionList;
	private TestUtil testUtil;

	public AuroraSelectHandler(Object targetObject, Object parentObject,
			List<TestActionInfo> testActionList, TestUtil testUtil) {
		this.targetObject = targetObject;
		this.parentObject = parentObject;
//		this.childObject = childObject;
		this.testActionList = testActionList;
		this.testUtil = testUtil;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		TestActionInfo testAction = fetchActionInfo(method);
		System.out.println("Excute Action Start : " + testAction.getComment());
		// (new
		// WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(""))));

//		new WebDriverWait((WebDriver) parentObject, 10)
//				.until(ExpectedConditions
//						.presenceOfElementLocated((By) childObject));

		Object result = method.invoke(this.targetObject, args);

		if (testAction.isScreenShot()) {
			this.testUtil.createScreenShot((WebDriver)parentObject);
		}
		System.out.println("Excute Action Success : " + testAction.getComment());

		return result;
	}

	private TestActionInfo fetchActionInfo(Method method) {
		for (TestActionInfo testAction : testActionList) {
//			System.out.println(("By" + testAction.getBy()).toLowerCase());
//			System.out.println(childObject.getClass()
//					.getName().split("\\$")[1].toLowerCase());
//			System.out.println(method.getName());
//			System.out.println(testAction.getAction());
//			System.out.println(childObject.toString().split(":")[1].trim());
//			System.out.println(testAction.getElement().replace("\"", ""));
//			System.out.println(("By" + testAction.getBy()).toLowerCase().equals(childObject.getClass().getName().split("\\$")[1].toLowerCase())); 
//			System.out.println(method.getName().equals(testAction.getAction()));
//			System.out.println(childObject.toString().split(":")[1].trim().equals(testAction.getElement().replace("\"", "")));
//			
//			if (("By" + testAction.getBy()).toLowerCase().equals(childObject.getClass()
//					.getName().split("\\$")[1].toLowerCase())
//					&& method.getName().equals(testAction.getAction())
//					&& childObject.toString().split(":")[1].trim().equals(
//							testAction.getElement().replace("\"", ""))) {
//				return testAction;
//			}
		}
		return null;
	}

}
