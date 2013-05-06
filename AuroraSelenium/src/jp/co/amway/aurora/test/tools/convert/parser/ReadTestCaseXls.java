package jp.co.amway.aurora.test.tools.convert.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.amway.aurora.test.bean.TestActionInfo;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadTestCaseXls {
	// public static void main(String[] args) {
	// String path = System.getProperty("user.dir")
	// + "/resources/TestCase_Convert/WA-U-01-04/D01001001.xls";
	// try {
	// readDataFromXls(path);
	// } catch (BiffException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	private List<TestActionInfo> testActionList = new ArrayList<TestActionInfo>();

	public List<TestActionInfo> getTestActionList() {
		return testActionList;
	}

	public ReadTestCaseXls(String path) {
		try {
			readDataFromXls(path);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readDataFromXls(String path) throws BiffException, IOException {
		File testCaseXls = new File(path);
		Workbook rwb = Workbook.getWorkbook(testCaseXls);
		Sheet rs = rwb.getSheet(0);
		int intStep = 4;
		while (!"".equals(rs.getCell(0, intStep).getContents())) {
			TestActionInfo testActionInfo = new TestActionInfo();
			testActionInfo.setBy(rs.getCell(1, intStep).getContents());
			testActionInfo.setElement(rs.getCell(2, intStep).getContents());
			testActionInfo.setAction(rs.getCell(3, intStep).getContents());
			if (!"null".equals(rs.getCell(4, intStep).getContents())
					&& !"".equals(rs.getCell(4, intStep).getContents())) {
				testActionInfo.setValue(rs.getCell(4, intStep).getContents().substring(1, rs.getCell(4, intStep).getContents().length() - 1));
			}
			if ("YES"
					.equals(rs.getCell(5, intStep).getContents().toUpperCase())) {
				testActionInfo.setScreenShot(true);
			} else {
				testActionInfo.setScreenShot(false);
			}
			
			testActionInfo.setComment(rs.getCell(6, intStep).getContents());
			testActionList.add(testActionInfo);
			intStep++;
		}
	}
}
