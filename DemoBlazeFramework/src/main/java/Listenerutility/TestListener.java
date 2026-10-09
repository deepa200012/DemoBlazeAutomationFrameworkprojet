
package Listenerutility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.genericutilities.WebDriverUtility;

import utilities.ExtentReportUtility;
import utilities.LogUtility;

public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private static final Logger log = LogUtility.getLogger();

    @Override
    public void onStart(ITestContext context) {

        extent = ExtentReportUtility.getExtentReports();

        File screenshotFolder = new File("test-output/screenshots");

        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();
        }

        log.info("Test execution started");
        System.out.println("Test execution started");
    }

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());

        test.set(extentTest);
        test.get().info("Test started");

        log.info("Test started: "
                + result.getMethod().getMethodName());

        System.out.println("Started: "
                + result.getMethod().getMethodName());
    }


       @Override
       public void onTestSuccess(ITestResult result) {

    long duration = result.getEndMillis() - result.getStartMillis();

    test.get().pass("Test passed");

    test.get().info("Execution Time: " + duration + " milliseconds");

    log.info("Test passed: " + result.getMethod().getMethodName()
            + " | Execution Time: " + duration + " ms");

    System.out.println("Passed: " + result.getMethod().getMethodName());
    System.out.println("Execution Time: " + duration + " ms");
}


    @Override
    public void onTestFailure(ITestResult result) {

        test.get().fail(result.getThrowable());

        log.error("Test failed: "
                + result.getMethod().getMethodName(),
                result.getThrowable());

        String screenshotPath = takeScreenshot(result);

        if (screenshotPath != null) {
            try {
                test.get().fail(
                        "Failure screenshot",
                        MediaEntityBuilder
                                .createScreenCaptureFromPath(screenshotPath)
                                .build());

                log.info("Screenshot saved: " + screenshotPath);

            } catch (Exception e) {
                test.get().warning(
                        "Unable to attach screenshot: " + e.getMessage());

                log.error("Unable to attach screenshot", e);
            }
        }

        System.out.println("Failed: "
                + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.get().skip("Test skipped");

        if (result.getThrowable() != null) {
            test.get().skip(result.getThrowable());
        }

        log.warn("Test skipped: "
                + result.getMethod().getMethodName());

        System.out.println("Skipped: "
                + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {

        if (extent != null) {
            extent.flush();
        }

        log.info("Test execution finished");

        System.out.println("Test execution finished");
        System.out.println(
                "Extent report: test-output/DemoBlazeExtentReport.html");
    }

    private String takeScreenshot(ITestResult result) {

        WebDriver driver = WebDriverUtility.getDriver();

        if (driver == null) {
            log.warn("Screenshot not captured: WebDriver is null");
            System.out.println(
                    "Screenshot not captured: WebDriver is null");
            return null;
        }

        try {
            File source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            String fileName =
                    result.getMethod().getMethodName()
                    + "_" + timestamp + ".png";

            File destination = new File(
                    "test-output/screenshots/" + fileName);

            Files.copy(
                    source.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            log.info("Screenshot saved: "
                    + destination.getAbsolutePath());

            return destination.getAbsolutePath();

        } catch (IOException | RuntimeException e) {

            log.error("Screenshot error", e);

            System.out.println(
                    "Screenshot error: " + e.getMessage());

            return null;
        }
    }
}
