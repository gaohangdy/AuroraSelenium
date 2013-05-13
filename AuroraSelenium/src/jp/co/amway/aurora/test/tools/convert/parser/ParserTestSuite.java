package jp.co.amway.aurora.test.tools.convert.parser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.QualifiedNameExpr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import jp.co.amway.aurora.test.constant.AuroraSeleniumConst;

import org.apache.commons.io.FileUtils;

public class ParserTestSuite {
	private CompilationUnit cu;
	private String[] packageArray = { "jp", "co", "amway", "aurora", "test" };

	public void convertTestSuite(String filePath, String testSuiteName) {
		try {
			loadJavaFile(filePath);
			addPackageInfo(testSuiteName);
			refactJavaFile(
					filePath, testSuiteName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadJavaFile(String filePath) throws FileNotFoundException,
			ParseException, IOException {
		// creates an input stream for the file to be parsed
		FileInputStream in = new FileInputStream(filePath);
		System.out.println("Load java file : " + filePath);
		try {
			// parse the file
			cu = JavaParser.parse(in);
		} finally {
			in.close();
		}
	}

	private void addPackageInfo(String testSuiteName) {
		QualifiedNameExpr qExpr = new QualifiedNameExpr();
		qExpr.setName(testSuiteName);

		QualifiedNameExpr cExpr = new QualifiedNameExpr();
		NameExpr tExpr = null;

		for (String item : packageArray) {
			if (tExpr == null) {
				tExpr = new NameExpr();
				tExpr.setName(item);
			} else {
				cExpr = new QualifiedNameExpr();
				cExpr.setName(item);
				cExpr.setQualifier(tExpr);
				tExpr = cExpr;
			}
		}
		cExpr = new QualifiedNameExpr();
		cExpr.setName(testSuiteName);
		cExpr.setQualifier(tExpr);
		tExpr = cExpr;

		if (cu.getPackage() == null) {
			PackageDeclaration pkg = new PackageDeclaration();
			pkg.setName(tExpr);
			cu.setPackage(pkg);
		} else {
			cu.getPackage().setName(tExpr);
		}
	}

	private void refactJavaFile(String path, String testSuiteName)
			throws IOException {
		File fBase = new File(path);
		String fileName = fBase.getName();
		fBase = new File(fBase.getParent());
		
		if (!new File(fBase.getParent() + "/JAVA_CONV").exists()) {
			new File(fBase.getParent() + "/JAVA_CONV").mkdir();
		}
		File fConvert = new File(fBase.getParent() + "/JAVA_CONV/" + fileName);
		FileWriter fstream = new FileWriter(fConvert);
		BufferedWriter outobj = new BufferedWriter(fstream);
		outobj.write(cu.toString());
		outobj.close();
		String sourcePath = createConvertSourceFolder();
		if (AuroraSeleniumConst.CONVERT_TO_SOURCE_DIR) {
			FileUtils.copyFile(fConvert, new File(sourcePath + "/"
					+ fileName));
		}
	}

	private String createConvertSourceFolder() {
		String path = "";
		String sourcePath = "";
		if (AuroraSeleniumConst.CONVERT_TO_SOURCE_DIR) {
			path = System.getProperty("user.dir") + "/src/"
					+ cu.getPackage().getName().toString().replace(".", "/");
			if (!new File(path).exists()) {
				new File(path).mkdir();
			}
			sourcePath = path;
		}
		System.out.println(sourcePath);
		return sourcePath;
	}
}
