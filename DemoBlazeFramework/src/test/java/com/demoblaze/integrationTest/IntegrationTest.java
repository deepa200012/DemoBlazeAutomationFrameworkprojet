

package com.demoblaze.integrationTest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.demoblaze.BaseClass;
import Fileutility.Fileutility;

public class IntegrationTest extends BaseClass {

    WebDriverWait wait;

    // INT_001: Add a product to cart and verify it is displayed
    @Test
    public void verifyProductAddedToCart() {

        wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        // Select the first product
        String productName = addProductToCart(0);

        // Open Cart
        openCart();

        // Verify the product is displayed in the cart
        List<WebElement> cartProducts = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("#tbodyid tr")));

        boolean productFound = false;

        for (WebElement product : cartProducts) {
            if (product.getText().contains(productName)) {
                productFound = true;
                break;
            }
        }

        Assert.assertTrue(
                productFound,
                "Product was not displayed in the cart: " + productName);

        System.out.println(
                "INT_001 PASSED: Product added and displayed in cart");
    }


    // INT_002: Logged-in user adds a product to cart
    @Test
    public void verifyLoggedInUserCanAddProductToCart()
            throws IOException {

        wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        // Read credentials from config.properties
        Fileutility fUtil = new Fileutility();

        String username = fUtil.getProperty("username");
        String password = fUtil.getProperty("password");

        Assert.assertNotNull(username,
                "Username is missing in config.properties");
        Assert.assertNotNull(password,
                "Password is missing in config.properties");

        // Click Login
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("login2"))).click();

        // Enter username
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername"))).sendKeys(username);

        // Enter password
        driver.findElement(By.id("loginpassword"))
                .sendKeys(password);

        // Submit login form
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Log in']"))).click();

        // Verify login success
        WebElement welcomeMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("nameofuser")));

        Assert.assertTrue(
                welcomeMessage.getText().contains(username),
                "Login was not successful");

        System.out.println("User logged in successfully");

        // Add product after login
        String productName = addProductToCart(0);

        // Open cart and verify product
        openCart();

        List<WebElement> cartProducts = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("#tbodyid tr")));

        boolean productFound = false;

        for (WebElement product : cartProducts) {
            if (product.getText().contains(productName)) {
                productFound = true;
                break;
            }
        }

        Assert.assertTrue(
                productFound,
                "Product was not found in cart after login: "
                        + productName);

        System.out.println(
                "INT_002 PASSED: Logged-in user added product to cart");
    }


    // INT_004: Verify multiple products can be added to cart
    @Test
    public void verifyMultipleProductsAddedToCart() {

        wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        // Add first product
        String firstProduct = addProductToCart(0);

        // Return to the home page without clearing the cart
        driver.get("https://www.demoblaze.com/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".card-title a")));

        // Add second product
        String secondProduct = addProductToCart(1);

        Assert.assertNotEquals(
                firstProduct,
                secondProduct,
                "The two selected products must be different");

        // Open Cart
        openCart();

        // Verify cart contains both products
        List<WebElement> cartProducts = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("#tbodyid tr")));

        boolean firstFound = false;
        boolean secondFound = false;

        for (WebElement product : cartProducts) {

            String cartProductName = product.getText();

            if (cartProductName.contains(firstProduct)) {
                firstFound = true;
            }

            if (cartProductName.contains(secondProduct)) {
                secondFound = true;
            }
        }

        Assert.assertTrue(
                firstFound,
                "First product missing from cart: " + firstProduct);

        Assert.assertTrue(
                secondFound,
                "Second product missing from cart: " + secondProduct);

        Assert.assertTrue(
                cartProducts.size() >= 2,
                "Cart should contain at least two products");

        System.out.println(
                "INT_004 PASSED: Multiple products displayed in cart");
        System.out.println("Product 1: " + firstProduct);
        System.out.println("Product 2: " + secondProduct);
    }


    // Reusable method to select a product and add it to cart
    private String addProductToCart(int productIndex) {

        // Wait for products on the home page
        List<WebElement> products = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector(".card-title a")));

        Assert.assertTrue(
                products.size() > productIndex,
                "Requested product is not available on the home page");

        // Capture the product name before clicking
        String productName = products.get(productIndex).getText();

        products.get(productIndex).click();

        // Wait for the product details page
        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Add to cart")));

        // Click Add to cart
        driver.findElement(By.linkText("Add to cart")).click();

        // Accept the product-added alert
        Alert alert = wait.until(
                ExpectedConditions.alertIsPresent());

        System.out.println("Cart message: " + alert.getText());
        alert.accept();

        System.out.println("Added product: " + productName);

        return productName;
    }


    // Reusable method to open Cart
    private void openCart() {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("cartur"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("tbodyid")));
    }
}
