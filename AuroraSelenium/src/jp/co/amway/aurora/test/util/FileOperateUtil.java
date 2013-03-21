package jp.co.amway.aurora.test.util;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import bsh.classpath.BshClassPath.DirClassSource;

public class FileOperateUtil {

	public static List<String> getFileByTestSuite(String path) {
		List<String> lstTestSuite = new ArrayList<String>();
		File file = new File(path);
		String[] fns = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				File subFile = new File(dir + "\\" + name);
				if (subFile.isDirectory()) {
					if (new File(subFile.getPath() + "\\" + "JAVA_CONV")
							.exists()) {
						System.out.println("Converted TestSuite:"
								+ subFile.getPath());
						return false;
					} else if (!new File(subFile.getPath() + "\\" + "JAVA_EXP")
							.exists()) {
						System.out.println("No Export Java TestSuite:"
								+ subFile.getPath());
						return false;
					} else if (!new File(subFile.getPath() + "\\" + "JAVA_CONV")
							.exists()) {
						return true;
					}
				}

				return false;
			}
		});
		for (String fnsItem : fns) {
			lstTestSuite.add(path + "\\" + fnsItem);
		}
		return lstTestSuite;
	}

	public static void getFileList(String path, final String prefix,
			final List<String> lstFile) {
		File file = new File(path);
		final String[] aryPrefix = prefix.split(",");
		String[] fns = file.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				File subFile = new File(dir + "\\" + name);
				if (subFile.isFile()) {
					boolean blnMatch = false;
					for (String item : aryPrefix) {
						if (name.toUpperCase().endsWith(item)) {
							blnMatch = true;
						}
					}
					return blnMatch;
				} else if (subFile.isDirectory()) {
					getFileList(dir + "\\" + name, prefix, lstFile);
					return false;
				} else {
					return false;
				}
			}
		});
		for (String fnsItem : fns) {
			lstFile.add(path + "\\" + fnsItem);
		}
	}

	/**
	 * Try to get file character ending. </p> <strong>Warning: </strong>use
	 * cpDetector to detect file's encoding.
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileCharacterEnding(File file) {

		String fileCharacterEnding = "UTF-8";

		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(JChardetFacade.getInstance());

		Charset charset = null;

		// File f = new File(filePath);

		try {
			charset = detector.detectCodepage(file.toURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (charset != null) {
			fileCharacterEnding = charset.name();
		}

		return fileCharacterEnding;
	}

	public static byte[] getSourceText(StringBuilder sbSource) {
		byte[] temp = sbSource.toString().getBytes();
		return temp;
	}

	public void readSourceFromFile(String path, int fileId) {

		try {
			String encoding = "";
			File f = new File(path);
			encoding = FileOperateUtil.getFileCharacterEnding(f);
			InputStreamReader read = null;

			read = new InputStreamReader(new FileInputStream(f), encoding);

			BufferedReader br = new BufferedReader(read);
			String row;

			while ((row = br.readLine()) != null) {
				// if (row.trim().toUpperCase().startsWith(INCLUDE_MATCHED)) {
				// intIncStep++;
				// registIncludeInfo(row, fileId, intIncStep);
				// }
			}

			br.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
