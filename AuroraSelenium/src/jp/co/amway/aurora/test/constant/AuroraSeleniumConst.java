package jp.co.amway.aurora.test.constant;

import java.util.ResourceBundle;

public class AuroraSeleniumConst {
	public static final String BASE_URL = getResourceKeyValue("base_url") != null ? getResourceKeyValue("base_url") : "https://ipdev.amwaylive.com/";
	public static final String DRIVER_TYPE = getResourceKeyValue("driver") != null ? getResourceKeyValue("driver") : "1";
	public static final boolean CONVERT_TO_SOURCE_DIR = "1"
			.equals(getResourceKeyValue("createtosource")!=null ? getResourceKeyValue("createtosource") : "0") ? true
			: false;
	public static final int WAIT_PERIOD = Integer.parseInt(getResourceKeyValue("wait_period") != null ? getResourceKeyValue("wait_period") : "	0");
	public final static String TEMPLATE_FILE_NAME = "TestCase_Template.xls";
	public static final String[] WEBELEMENT_ACTION = { "click", "submit",
			"sendKeys", "clear" };
	public static final String[] SELECT_ACTION = { "selectByVisibleText",
			"selectByIndex", "selectByValue", "deselectAll", "deselectByValue",
			"deselectByIndex", "deselectByVisibleText", "setSelected" };

	public static ResourceBundle getResourceBundle() {
		String path = "AuroraAutomatic";
		try {
			ResourceBundle resource = ResourceBundle.getBundle(path);

			return resource;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static String getResourceKeyValue(String key) {
		String path = "AuroraAutomatic";
		try {
			ResourceBundle resource = ResourceBundle.getBundle(path);

			return resource.getString(key);
		} catch (Exception ex) {
			return null;
		}
	}
}
