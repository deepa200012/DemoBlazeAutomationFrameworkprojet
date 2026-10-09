
package com.demoblaze.smokeTest;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.demoblaze.BaseClass;
import Fileutility.Fileutility;

public class SmokeTest extends BaseClass {

    WebDriverWait wait;

    // SMK_001: Verify DemoBlaze application launches successfully
    @Test
    public void verifyApplicationLaunch() {

        wait = new WebDriverWait(driver, Duration.ofSeconds(2000));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("nava")));

        String currentUrl = driver.getCurrentUrl();
  
        Assert.assertTrue(
                currentUrl.contains("demoblaze.com"),
                "DemoBlaze home page did not open");
      

        System.out.println("SMK_001 PASSED: DemoBlaze launched successfully");
    }


    // SMK_002: Verify user can log in successfully
    @Test
    public void verifyLoginSuccessfully() throws IOException {

        wait = new WebDriverWait(driver, Duration.ofSeconds(2000));

        Fileutility fUtil = new Fileutility();

        String username = fUtil.getProperty("username");
        String password = fUtil.getProperty("password");

        Assert.assertNotNull(username, "Username is missing in config.properties");
        Assert.assertNotNull(password, "Password is missing in config.properties");

        // Click Login link
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("login2"))).click();

        // Enter username
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("loginusername")));

        usernameField.sendKeys(username);

        // Enter password
        driver.findElement(By.id("loginpassword"))
                .sendKeys(password);

        // Click Login button
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Log in']"))).click();

        // Verify successful login
        WebElement welcomeMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("nameofuser")));

        Assert.assertTrue(
                welcomeMessage.getText().contains(username),
                "Logged-in username was not displayed");

        System.out.println("SMK_002 PASSED: Login successful");
    }


    // SMK_003: Verify user can add a product to cart
    @Test
    public void verifyProductAddedToCart() {

        wait = new WebDriverWait(driver, Duration.ofSeconds(2000));

        // Select the first product on the home page
        WebElement product = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".card-title a")));

        String productName = product.getText();

        product.click();

        // Wait for product details page to load
        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Add to cart")));

        // Click Add to cart
        driver.findElement(By.linkText("Add to cart")).click();

        // Handle product-added alert
        Alert alert = wait.until(
                ExpectedConditions.alertIsPresent());

        System.out.println("Cart alert: " + alert.getText());
        alert.accept();

        // Open Cart
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("cartur"))).click();

        // Verify product is displayed in the cart
        WebElement cartRow = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#tbodyid tr")));

        Assert.assertTrue(
                cartRow.getText().contains(productName),
                "Selected product was not found in the cart");

        System.out.println(
                "SMK_003 PASSED: " + productName
                + " added to cart successfully");
    }
}
