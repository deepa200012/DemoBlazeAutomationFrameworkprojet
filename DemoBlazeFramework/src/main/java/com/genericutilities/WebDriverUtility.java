package com.genericutilities;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtility {
	

public static WebDriver getDriver() {
    return UtilityClassObject.getDriver();
}


    // Maximize browser
    public void maximizeBrowser(WebDriver driver) {

        driver.manage().window().maximize();
    }


    // Implicit wait
    public void waitForPageLoad(WebDriver driver) {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    // Wait for element to be visible
    public void waitForElementToBeVisible(
            WebDriver driver,
            WebElement element) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.visibilityOf(element)
        );
    }


    // Wait for element to be clickable
    public void waitForElementToBeClickable(
            WebDriver driver,
            WebElement element) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.elementToBeClickable(element)
        );
    }


    // Click element
    public void click(WebElement element) {

        element.click();
    }


    // Enter text
    public void enterText(
            WebElement element,
            String text) {

        element.clear();

        element.sendKeys(text);
    }


    // Mouse hover
    public void mouseHover(
            WebDriver driver,
            WebElement element) {

        Actions actions = new Actions(driver);

        actions.moveToElement(element).perform();
    }


    // Navigate back
    public void navigateBack(WebDriver driver) {

        driver.navigate().back();
    }


    // Navigate forward
    public void navigateForward(WebDriver driver) {

        driver.navigate().forward();
    }


    // Refresh page
    public void refreshPage(WebDriver driver) {

        driver.navigate().refresh();
    }
}