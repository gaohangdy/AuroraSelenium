package jp.co.amway.aurora.test.bean;

public class TestCaseClassInfo {
	private String className;
	private String packageName;
	private StringBuilder testCaseSource;
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public StringBuilder getTestCaseSource() {
		return testCaseSource;
	}

	public void setTestCaseSource(StringBuilder testCaseSource) {
		this.testCaseSource = testCaseSource;
	}
}
