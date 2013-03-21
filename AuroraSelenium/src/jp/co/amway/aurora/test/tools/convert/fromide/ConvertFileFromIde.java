package jp.co.amway.aurora.test.tools.convert.fromide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import jp.co.amway.aurora.test.bean.TestCaseClassInfo;
import jp.co.amway.aurora.test.bean.TestSuiteInfo;
import jp.co.amway.aurora.test.util.FileOperateUtil;

public class ConvertFileFromIde {
	public static final boolean CONVERT_TO_SOURCE_DIR = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		convertExportSource();
	}

	public static void convertExportSource() {
		String path = System.getProperty("user.dir")
				+ "/resources/TestCase_Convert";
		List<String> lstTestSuite = FileOperateUtil.getFileByTestSuite(path);
		for (String suitePath : lstTestSuite) {
			List<String> lstTestCase = new ArrayList<String>();
			FileOperateUtil.getFileList(suitePath + "/" + "JAVA_EXP", "JAVA",
					lstTestCase);
			TestSuiteInfo testSuiteInfo = fetchTestSuite(lstTestCase, suitePath
					+ "/" + "JAVA_EXP");

			for (TestCaseClassInfo testCaseClass : testSuiteInfo
					.getLstTestCase()) {
				readExportJavaSource(testCaseClass);
				writeConvertJavaFile(testCaseClass);
			}
		}
	}

	public static TestSuiteInfo fetchTestSuite(List<String> lstTestSuite,
			String folder) {
		TestSuiteInfo testSuiteInfo = new TestSuiteInfo();
		boolean matchedTestSuite = false;
		for (String path : lstTestSuite) {
			try {
				if (matchedTestSuite) {
					break;
				}
				String encoding = "";
				File f = new File(path);
				encoding = FileOperateUtil.getFileCharacterEnding(f);
				InputStreamReader read = null;

				read = new InputStreamReader(new FileInputStream(f), encoding);

				BufferedReader br = new BufferedReader(read);
				String row;
				while ((row = br.readLine()) != null) {
					if ("import junit.framework.TestSuite;".equals(row.trim())) {
						System.out.println("Testsuite:["
								+ f.getName().toLowerCase().split("\\.")[0]
								+ "] has been fund!");
						testSuiteInfo.setSuiteName(f.getName().toLowerCase()
								.split("\\.")[0]);
						matchedTestSuite = true;
					} else if (row.contains("@")) {
						break;
					} else if (row.trim().startsWith("suite.addTestSuite")) {
						TestCaseClassInfo testCaseClassInfo = new TestCaseClassInfo();
						Pattern pat = Pattern
								.compile("(?<=\\u0028).*(?=\\u0029)");
						Matcher mat = pat.matcher(row);
						if (mat.find()) {
							testCaseClassInfo.setClassName(mat.group(0).split(
									"\\.")[0]);
							testCaseClassInfo
									.setPackageName("jp.co.amway.aurora.test."
											+ testSuiteInfo.getSuiteName()
													.toLowerCase());
							testCaseClassInfo.setFilePath(folder + "/"
									+ testCaseClassInfo.getClassName()
									+ ".java");
							testSuiteInfo.getLstTestCase().add(
									testCaseClassInfo);
							System.out.println("TestCase matched:"
									+ mat.group(0));
						}
					}
				}
				br.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		if (!matchedTestSuite)
			System.out.println("No testsuite file:" + lstTestSuite.get(0));
		return testSuiteInfo;
	}

	public static void writeConvertJavaFile(TestCaseClassInfo testCaseClassInfo) {
		String path = System.getProperty("user.dir")
				+ "/resources/TestCaseTemplate";
		StringBuilder sbTestCase = new StringBuilder();
		try {
			String encoding = "";
			File f = new File(path);
			encoding = FileOperateUtil.getFileCharacterEnding(f);
			InputStreamReader read = null;

			read = new InputStreamReader(new FileInputStream(f), encoding);

			BufferedReader br = new BufferedReader(read);
			String row;
			while ((row = br.readLine()) != null) {
				if (row.contains(":PACKAGE")) {
					sbTestCase.append(
							row.replace(":PACKAGE",
									testCaseClassInfo.getPackageName()))
							.append("\n");
				} else if (row.contains(":CLASS")) {
					sbTestCase.append(
							row.replace(":CLASS",
									testCaseClassInfo.getClassName())).append(
							"\n");
				} else if (row.contains(":TESTCASE")) {
					sbTestCase.append(
							row.replace(":TESTCASE",
									testCaseClassInfo.getTestCaseSource()))
							.append("\n");
				} else {
					sbTestCase.append(row).append("\n");
				}
			}
			br.close();

			// if (!new
			// File(testCaseClassInfo.getFilePath().replace(testCaseClassInfo.getClassName()
			// + ".java", "").toUpperCase().replace("JAVA_EXP",
			// "JAVA_CONV")).exists()) {
			// new
			// File(testCaseClassInfo.getFilePath().replace(testCaseClassInfo.getClassName()
			// + ".java", "").toUpperCase().replace("JAVA_EXP",
			// "JAVA_CONV")).mkdir();
			// }
			String sourcePath = createConvertSourceFolder(testCaseClassInfo);
			File fConvert = new File(testCaseClassInfo.getFilePath().replace(
					"JAVA_EXP", "JAVA_CONV"));
			FileWriter fstream = new FileWriter(fConvert);
			BufferedWriter outobj = new BufferedWriter(fstream);
			outobj.write(sbTestCase.toString());
			outobj.close();
			if (CONVERT_TO_SOURCE_DIR) {
				FileUtils.copyFile(fConvert, new File(sourcePath + "/"
						+ testCaseClassInfo.getClassName() + ".java"));
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static String createConvertSourceFolder(
			TestCaseClassInfo testCaseClassInfo) {
		String path = "";
		String sourcePath = "";
		if (CONVERT_TO_SOURCE_DIR) {
			path = System.getProperty("user.dir") + "/src/"
					+ testCaseClassInfo.getPackageName().replace(".", "/");
			if (!new File(path).exists()) {
				new File(path).mkdir();
			}
			sourcePath = path;
		}

		path = testCaseClassInfo.getFilePath()
				.replace(testCaseClassInfo.getClassName() + ".java", "")
				.toUpperCase().replace("JAVA_EXP", "JAVA_CONV");
		if (!new File(path).exists()) {
			new File(path).mkdir();
		}
		System.out.println(sourcePath);
		return sourcePath;
	}

	public static void readExportJavaSource(TestCaseClassInfo testCaseClassInfo) {
		StringBuilder sbTestCase = new StringBuilder();
		try {
			String encoding = "";
			File f = new File(testCaseClassInfo.getFilePath());
			encoding = FileOperateUtil.getFileCharacterEnding(f);
			InputStreamReader read = null;

			read = new InputStreamReader(new FileInputStream(f), encoding);

			BufferedReader br = new BufferedReader(read);
			String row;
			boolean matchedTestCase = false;
			boolean matchedFirstBrackets = false;
			int pairBrackets = 0;
			while ((row = br.readLine()) != null) {
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
