package jp.co.amway.aurora.test.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestUtil {
	// Hard disk path for save screen shot.
	public final static String SCREENSHOT_PATH = "C:\\Work_dir\\screenshot";
	private String screenShotPath;
	private String testCaseName;
	private int screenShotSeq = 1;

	// All screen shot file will be delete.
	public static void deleteScreenShot(String path) {
		File f = new File(path);
		if (f.isDirectory()) {
			String[] list = f.list();
			for (int i = 0; i < list.length; i++) {
				deleteScreenShot(path + "//" + list[i]);
			}
		}
		f.delete();
	}

	// Create folder for save screen shot file.
	public void createScreenShotFolder() throws ClassNotFoundException {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		// String path = SCREENSHOT_PATH + "\\" + sdf.format(new Date());
		String className = new Throwable().getStackTrace()[2].getClassName();		
		String packageName = Class.forName(className).getPackage().getName();
		packageName = packageName.split("\\.")[packageName.split("\\.").length - 1];
		className = className.split("\\.")[className.split("\\.").length - 1];
		String path = SCREENSHOT_PATH + "\\" + packageName + "\\" + className + "\\";
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		} else {
			deleteScreenShot(path);
			f.mkdir();
		}
		this.screenShotPath = path;
	}

	public void createScreenShot(WebDriver driver) throws IOException,
			InterruptedException {
		Thread.sleep(3000);
		if (!"".equals(testCaseName) && testCaseName != null) {
			if ((new Throwable().getStackTrace()[1].getMethodName())
					.equals(testCaseName)) {
				screenShotSeq++;
			} else {
				testCaseName = new Throwable().getStackTrace()[1]
						.getMethodName();
				screenShotSeq = 1;
			}
		} else {
			testCaseName = new Throwable().getStackTrace()[1].getMethodName();
		}
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(this.screenShotPath + "\\"
				+ testCaseName + "_" + String.format("%03d", screenShotSeq)
				+ ".png"));
	}

	public void createScreenShotDialog(WebDriver driver) throws IOException,
			InterruptedException {
		Thread.sleep(3000);
		
		if (!"".equals(testCaseName) && testCaseName != null) {
			if ((new Throwable().getStackTrace()[1].getMethodName())
					.equals(testCaseName)) {
				screenShotSeq++;
			} else {
				testCaseName = new Throwable().getStackTrace()[1]
						.getMethodName();
				screenShotSeq = 1;
			}
		} else {
			testCaseName = new Throwable().getStackTrace()[1].getMethodName();
		}
		
		Robot objRobot = null;
		try {
			objRobot = new Robot();
		} catch (Exception ex) {

		}

		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

		BufferedImage objBufferedImage = objRobot
				.createScreenCapture(new Rectangle(0, 0, (int) screenDim
						.getWidth(), (int) screenDim.getHeight()));

		int areaToExportWidth = 1024;
		int areaToExportHeight = 768;

		// Create the image
		BufferedImage exportImage = objRobot.createScreenCapture(new Rectangle(
				0, 0, (int) screenDim.getWidth(), (int) screenDim.getHeight()));

		// Get graphics - Get the layer we can actually draw on
		Graphics2D imageGraphics = (Graphics2D) exportImage.getGraphics();

		// Cleanup after ourselves
		imageGraphics.dispose();

		String outputPath = this.screenShotPath + "\\" + testCaseName + "_"
				+ String.format("%03d", screenShotSeq) + ".png";
		// Setup to write the BufferedImage to a file
		File outputFile = new File(outputPath);

		// Write the file
		try { // Attempt the write
			ImageIO.write(exportImage, "png", outputFile);
		} catch (IOException e) { // For some reason it failed so...
			e.printStackTrace();// ... why did it fail?
			driver.switchTo().alert().accept();
		}
	}
}
