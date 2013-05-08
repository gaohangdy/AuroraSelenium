package jp.co.amway.aurora.test.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.amway.aurora.test.bean.TestActionInfo;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class AuroraExcelUtil {
	public final static String TEMPLATE_FILE_NAME = "TestCase_Template.xls";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			try {
				writeExcelFile("LoginDA001");
				// getExcelFile();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<TestActionInfo> readTestCaseInfoList() {
		List<TestActionInfo> lstTestCaseInfo = new ArrayList<TestActionInfo>();
		
		return lstTestCaseInfo;
	}

	public static void writeExcelFile(String testCaseName) throws IOException,
			WriteException {
		File templateXls = new File(System.getProperty("user.dir") + "\\resources" + TEMPLATE_FILE_NAME);
		File testCaseXls = new File(testCaseName + ".xls");
		try {
			Workbook rwb = Workbook.getWorkbook(templateXls);
			WritableWorkbook wwb = Workbook.createWorkbook(testCaseXls, rwb);// copy
			WritableSheet ws = wwb.getSheet(0);
			jxl.write.Label lbl = new jxl.write.Label(1, 1, "aaaaaa");
			ws.addCell(lbl);
			wwb.write();
			wwb.close();
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
