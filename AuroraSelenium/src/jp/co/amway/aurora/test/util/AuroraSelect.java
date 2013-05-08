package jp.co.amway.aurora.test.util;

import java.util.List;

import jp.co.amway.aurora.test.bean.TestActionInfo;
import jp.co.amway.aurora.test.constant.AuroraSeleniumConst;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuroraSelect extends Select {
	private List<TestActionInfo> testActionList;
	private String elementText;

	public AuroraSelect(WebElement element,
			List<TestActionInfo> testActionList, String elementText,
			WebDriver driver) {
		super(element);
		this.elementText = elementText;
		this.testActionList = testActionList;

		// List<WebElement> options = element.findElements(By
		// .xpath(".//option[normalize-space(.) = "
		// + escapeQuotes(elementText) + "]"));
		WebDriverWait wait = new WebDriverWait(driver,
				AuroraSeleniumConst.WAIT_PERIOD);
		wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath(".//option[normalize-space(.) = "
						+ escapeQuotes(elementText) + "]")));
	}

	@Override
	public void selectByVisibleText(String text) {
		TestActionInfo testAction = fetchActionInfo(new Throwable()
				.getStackTrace()[0].getMethodName());
		System.out.println("Excute Action Start : " + testAction.getComment());

		super.selectByVisibleText(text.replace("\"", ""));

		// if (testAction.isScreenShot()) {
		// this.testUtil.createScreenShot((WebDriver)parentObject);
		// }
		System.out
				.println("Excute Action Success : " + testAction.getComment());

	}

	private TestActionInfo fetchActionInfo(String action) {
		for (TestActionInfo testAction : testActionList) {
			System.out.println("By." + testAction.getBy() + "("
					+ testAction.getElement() + ")");
			System.out.println(this.elementText);

			if (this.elementText.equals("By." + testAction.getBy() + "("
					+ testAction.getElement() + ")")
					&& action.equals(testAction.getAction())) {
				return testAction;
			}
		}
		return null;
	}

}
