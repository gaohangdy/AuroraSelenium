package jp.co.amway.aurora.test.constant;

import java.util.ResourceBundle;

public class AuroraSeleniumConst {
	public static final String DRIVER_TYPE = getResourceBundle().getString("driver");
	public static final boolean CONVERT_TO_SOURCE_DIR = "1".equals(getResourceBundle().getString("createtosource")) ? true: false;

	public static ResourceBundle getResourceBundle() {
		String path = "AuroraAutomatic";
		ResourceBundle resource = ResourceBundle.getBundle(path);
		
		return resource;
	}
}
