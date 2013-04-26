package jp.co.amway.aurora.test.constant;

import java.util.ResourceBundle;

public class AuroraSeleniumConst {
	public static final String DRIVER_TYPE = getResourceBundle().getString(
			"driver");
	public static final boolean CONVERT_TO_SOURCE_DIR = "1"
			.equals(getResourceBundle().getString("createtosource")) ? true
			: false;
	public static final int WAIT_PERIOD = Integer.parseInt(getResourceBundle().getString(
			"wait_period"));
	public final static String TEMPLATE_FILE_NAME = "TestCase_Template.xls";
	public static final String[] WEBELEMENT_ACTION = { "click", "submit",
			"sendKeys", "clear" };
	public static final String[] SELECT_ACTION = { "selectByVisibleText",
			"selectByIndex", "selectByValue", "deselectAll", "deselectByValue",
			"deselectByIndex", "deselectByVisibleText", "setSelected" };

	public static ResourceBundle getResourceBundle() {
		String path = "AuroraAutomatic";
		ResourceBundle resource = ResourceBundle.getBundle(path);

		return resource;
	}
}
