
package com.demoblaze;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Fileutility.Fileutility;

public class BaseClass {

    public static WebDriver driver;

    @BeforeMethod
    public void setUp() throws IOException, InterruptedException {

        Fileutility fUtil = new Fileutility();

        String browser = fUtil.getProperty("browser");
      
        String url = fUtil.getProperty("url");

        if (browser == null || browser.trim().isEmpty()) {
            browser = "chrome";
        }

        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "URL is missing in config.properties");
        }

        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            driver = new ChromeDriver(options);

        } else {
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser);
        }

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(30));

        driver.get(url);

        System.out.println("Browser launched successfully");
        System.out.println("Application URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + driver.getTitle());
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
