
package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportUtility {

    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {

        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(
                            "test-output/DemoBlazeExtentReport.html");

            spark.config().setDocumentTitle("DemoBlaze Automation Report");
            spark.config().setReportName("DemoBlaze Test Execution Report");
            spark.config().setTheme(Theme.DARK);
            spark.config().setTimeStampFormat("dd-MMM-yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Application", "DemoBlaze");
            extent.setSystemInfo("Automation Tool", "Selenium WebDriver");
            extent.setSystemInfo("Testing Framework", "TestNG");
            extent.setSystemInfo("Language", "Java");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Environment", "QA");
        }

        return extent;
    }
}
