package jp.co.amway.aurora.test.bean;

import java.util.ArrayList;
import java.util.List;

public class TestSuiteInfo {
	private String suiteName;
	private String suiteFilePath;
	private List<TestCaseClassInfo> lstTestCase;
	
	public TestSuiteInfo() {
		suiteName = "";
		suiteFilePath = "";
		lstTestCase = new ArrayList<TestCaseClassInfo>();
				
	}

	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	public String getSuiteFilePath() {
		return suiteFilePath;
	}

	public void setSuiteFilePath(String suiteFilePath) {
		this.suiteFilePath = suiteFilePath;
	}

	public List<TestCaseClassInfo> getLstTestCase() {
		return lstTestCase;
	}

	public void setLstTestCase(List<TestCaseClassInfo> lstTestCase) {
		this.lstTestCase = lstTestCase;
	}
}
