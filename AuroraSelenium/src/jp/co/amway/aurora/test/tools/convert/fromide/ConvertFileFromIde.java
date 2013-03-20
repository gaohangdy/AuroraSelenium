package jp.co.amway.aurora.test.tools.convert.fromide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.amway.aurora.test.bean.TestCaseClassInfo;
import jp.co.amway.aurora.test.util.FileOperateUtil;

public class ConvertFileFromIde {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "C:\\Work_dir\\TestCase_Convert";
		List<String> lstTestSuite = FileOperateUtil.getFileByTestSuite(path);
		for (String suitePath : lstTestSuite) {
			List<String> lstTestCase = new ArrayList<String>();
			FileOperateUtil.getFileList(suitePath + "\\" + "JAVA_EXP", "JAVA",
					lstTestCase);
			for (String casePath : lstTestCase) {
				System.out.print(readExportJavaSource(casePath));
			}
		}

	}

	public static void classAnalysis() {

	}

	public static void readJavaSource() {
		// MessageFormat.format(message, values);
	}

	// private static String getMessageInner(String resouceName, String
	// messageCode, Object... values) {
	// FWMessageUtil own = (FWMessageUtil)
	// FWBeanManager.getBean(FWMessageUtil.class);
	// if (resouceName == null) {
	// resouceName = own.getMessageResourceName();
	// }
	// String message = own.getFWResourceBundle().getString(resouceName,
	// messageCode);
	// return MessageFormat.format(message, values);
	// }
	
	public static String fetchTestSuite(List<String> lstTestSuite) {
		for(String path : lstTestSuite) {
			
			//import junit.framework.TestSuite;
			try {				
				String encoding = "";
				File f = new File(path);
				encoding = FileOperateUtil.getFileCharacterEnding(f);
				InputStreamReader read = null;

				read = new InputStreamReader(new FileInputStream(f), encoding);

				BufferedReader br = new BufferedReader(read);
				String row;
				while ((row = br.readLine()) != null) {
					if ("import junit.framework.TestSuite;".equals(row.trim())) {
						return f.getName().toLowerCase().split("\\.")[0];
					}
				}
				br.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}			
		}
		System.out.println("No testsuite file:" + lstTestSuite.get(0));
		return "";
	}

	public static void writeConvertJavaFile(TestCaseClassInfo testCaseClassInfo) {
		String path = System.getProperty("user.dir") + "\\bin\\TestCaseTemplate";
		StringBuilder sbTestCase = new StringBuilder();
		try {
			String encoding = "";
			File f = new File(path);
			encoding = FileOperateUtil.getFileCharacterEnding(f);
			InputStreamReader read = null;

			read = new InputStreamReader(new FileInputStream(f), encoding);

			BufferedReader br = new BufferedReader(read);
			String row;
			boolean matchedTestCase = false;
			boolean matchedFirstBrackets = false;
			int pairBrackets = 0;
			while ((row = br.readLine()) != null) {
				if (row.contains(":PACKAGE")) {
					
				}
//				System.out.println(row);
				if (row.trim().startsWith("public class ")) {
					testCaseClassInfo.setClassName(row.trim().split(" ")[2]);
				}
				if ("@Test".equals(row.trim()) && !matchedTestCase) {
					matchedTestCase = true;
				}
				if (matchedTestCase) {
					if (matchedFirstBrackets && pairBrackets == 0) {
						break;
					}					
					if (row.contains("{")) {
						matchedFirstBrackets = true;
						pairBrackets++;
						sbTestCase.append(row).append("\n");
					} else if (row.contains("}")) {
						pairBrackets--;
						sbTestCase.append(row).append("\n");
					} else {
						sbTestCase.append(row).append("\n");
					}
				}
			}
			testCaseClassInfo.setTestCaseSource(sbTestCase);
			br.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	public static TestCaseClassInfo readExportJavaSource(String path) {
		TestCaseClassInfo testCaseClassInfo = new TestCaseClassInfo();
		StringBuilder sbTestCase = new StringBuilder();
		try {
			String encoding = "";
			File f = new File(path);
			encoding = FileOperateUtil.getFileCharacterEnding(f);
			InputStreamReader read = null;

			read = new InputStreamReader(new FileInputStream(f), encoding);

			BufferedReader br = new BufferedReader(read);
			String row;
			boolean matchedTestCase = false;
			boolean matchedFirstBrackets = false;
			int pairBrackets = 0;
			while ((row = br.readLine()) != null) {
//				System.out.println(row);
				if (row.trim().startsWith("public class ")) {
					testCaseClassInfo.setClassName(row.trim().split(" ")[2]);
				}
				if ("@Test".equals(row.trim()) && !matchedTestCase) {
					matchedTestCase = true;
				}
				if (matchedTestCase) {
					if (matchedFirstBrackets && pairBrackets == 0) {
						break;
					}					
					if (row.contains("{")) {
						matchedFirstBrackets = true;
						pairBrackets++;
						sbTestCase.append(row).append("\n");
					} else if (row.contains("}")) {
						pairBrackets--;
						sbTestCase.append(row).append("\n");
					} else {
						sbTestCase.append(row).append("\n");
					}
				}
			}
			testCaseClassInfo.setTestCaseSource(sbTestCase);
			br.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		return testCaseClassInfo;
	}

	public void rewriteJavaSource(String path) {

		File f = new File(path);

		FileInputStream fs = null;
		InputStreamReader in = null;
		BufferedReader br = null;

		StringBuffer sb = new StringBuffer();

		String textinLine;

		try {
			fs = new FileInputStream(f);
			in = new InputStreamReader(fs);
			br = new BufferedReader(in);
			String row;
			while ((row = br.readLine()) != null) {
				sb.append(formatSourceText(row));
			}
			while (true) {
				textinLine = br.readLine();
				if (textinLine == null)
					break;
				sb.append(textinLine);
			}

			fs.close();
			in.close();
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter fstream = new FileWriter(f);
			BufferedWriter outobj = new BufferedWriter(fstream);
			outobj.write(sb.toString());
			outobj.close();

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	private String formatSourceText(String sourceText) {
		return "";
	}
}
