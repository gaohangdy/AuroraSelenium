/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.amway.aurora.test.tools.convert.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.amway.aurora.test.bean.TestActionInfo;
import jp.co.amway.aurora.test.constant.AuroraSeleniumConst;
import jp.co.amway.aurora.test.util.AuroraExcelUtil;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 
 * @author epe2
 */
public class WriteTestCaseXls {
	private String testSuitePath;
	private String javaFilePath;
	private String testSuiteName;
	private String testCaseName;

	public WriteTestCaseXls(String testSuitePath, String javaFilePath,
			String testSuiteName, String testCaseName) {
		this.testSuitePath = testSuitePath;
		this.javaFilePath = javaFilePath;
		this.testSuiteName = testSuiteName;
		this.testCaseName = testCaseName;
	}

	public void writeExcelFile(String testCaseName) throws IOException,
			WriteException {
		List<TestActionInfo> lstTestCaseAction = new ParserTestCase(
				javaFilePath).getLstTestCaseAction();
		if (lstTestCaseAction.isEmpty()) {
			return;
		}

		String path = this.testSuitePath + "/DOC";
		if (!new File(path).exists()) {
			new File(path).mkdir();
		}		
		
		File templateXls = new File(System.getProperty("user.dir")
				+ "/resources/" + AuroraSeleniumConst.TEMPLATE_FILE_NAME);
		File testCaseXls = new File(path + "/" + testCaseName
				+ ".xls");
		try {
			Workbook rwb = Workbook.getWorkbook(templateXls);
			WritableWorkbook wwb = Workbook.createWorkbook(testCaseXls, rwb);// copy
			WritableSheet ws = wwb.getSheet(0);
			Sheet rs = rwb.getSheet(0);

			ws.addCell(new Label(1, 0, this.testSuiteName));
			ws.addCell(new Label(1, 1, this.testCaseName));
			int startRow = 4;
			int intStep = 0;
			for (TestActionInfo testAction : lstTestCaseAction) {
				ws.addCell(new Label(0, startRow + intStep, String
						.valueOf(intStep + 1)));
				ws.addCell(new Label(1, startRow + intStep, String
						.valueOf(testAction.getBy())));
				ws.addCell(new Label(2, startRow + intStep, String
						.valueOf(testAction.getElement())));
				ws.addCell(new Label(3, startRow + intStep, String
						.valueOf(testAction.getAction())));
				ws.addCell(new Label(4, startRow + intStep, String
						.valueOf(testAction.getValue())));
				intStep++;
			}

			wwb.write();
			wwb.close();
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
