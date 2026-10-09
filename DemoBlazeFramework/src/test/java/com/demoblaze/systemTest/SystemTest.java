
package com.demoblaze.systemTest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.demoblaze.BaseClass;
import Fileutility.Fileutility;

public class SystemTest extends BaseClass {

    private WebDriverWait wait;

    // Initialize explicit wait
    private void initializeWait() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // SYS_011: Verify navigation and shopping flow
    @Test
    public void verifyApplicationNavigation() throws IOException {

        initializeWait();

        Fileutility file = new Fileutility();

        login(
            file.getProperty("username"),
            file.getProperty("password")
        );

        goToHome();

        String[] categories = {"Phones", "Laptops", "Monitors"};

        for (String category : categories) {

            wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.linkText(category)
                )
            ).click();

            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#tbodyid .card")
                )
            );

            System.out.println("Category opened: " + category);

            goToHome();
        }

        String productName = openProduct(0);

        Assert.assertFalse(
            productName.trim().isEmpty(),
            "Product name is empty"
        );

        addCurrentProductToCart();

        openCart();

        Assert.assertTrue(
            isProductInCart(productName),
            "Product not found in cart"
        );

        System.out.println("SYS_011 PASSED");
    }

    // SYS_014: Verify registration, login, purchase and logout
    @Test
    public void verifyMajorUserFlow() {

        initializeWait();

        System.out.println("SYS_014: Starting registration");
        String username = createNewUser();

        System.out.println("SYS_014: Logging in");
        login(username, "Test@12345");

        goToHome();

        String productName = openProduct(0);

        addCurrentProductToCart();

        openCart();

        Assert.assertTrue(
            isProductInCart(productName),
            "Product not found in cart"
        );

        placeOrder();

        verifyOrderConfirmation();

        closeOrderModalIfOpen();

        logout();

        System.out.println("SYS_014 PASSED");
    }

    // SYS_015: Verify complete e-commerce flow
    @Test
    public void verifyCompleteEcommerceSystem() {

        initializeWait();

        System.out.println("SYS_015: Starting registration");
        String username = createNewUser();

        System.out.println("SYS_015: Starting login");
        login(username, "Test@12345");

        goToHome();

        System.out.println("SYS_015: Opening second product");
        String productName = openProduct(1);

        Assert.assertFalse(
            productName.trim().isEmpty(),
            "Product details did not open"
        );

        System.out.println("SYS_015: Adding product to cart");
        addCurrentProductToCart();

        openCart();

        System.out.println("SYS_015: Verifying cart");
        Assert.assertTrue(
            isProductInCart(productName),
            "SYS_015: Product not found in cart"
        );

        System.out.println("SYS_015: Placing order");
        placeOrder();

        System.out.println("SYS_015: Verifying confirmation");
        verifyOrderConfirmation();

        System.out.println("SYS_015: Closing modal");
        closeOrderModalIfOpen();

        System.out.println("SYS_015: Logging out");
        logout();

        System.out.println("SYS_015 PASSED");
    }

    // Register a new user
    private String createNewUser() {

        String username = "dbuser" + System.currentTimeMillis();

        wait.until(
            ExpectedConditions.elementToBeClickable(By.id("signin2"))
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("sign-username")
            )
        ).sendKeys(username);

        driver.findElement(By.id("sign-password"))
              .sendKeys("Test@12345");

        driver.findElement(
            By.xpath("//button[text()='Sign up']")
        ).click();

        Alert alert = wait.until(
            ExpectedConditions.alertIsPresent()
        );

        String message = alert.getText();

        System.out.println("Registration message: " + message);

        alert.accept();

        Assert.assertTrue(
            message.toLowerCase().contains("sign up successful"),
            "Registration failed: " + message
        );

        return username;
    }

    // Login
    private void login(String username, String password) {

        Assert.assertNotNull(username, "Username is null");
        Assert.assertNotNull(password, "Password is null");

        wait.until(
            ExpectedConditions.elementToBeClickable(By.id("login2"))
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("loginusername")
            )
        ).sendKeys(username);

        driver.findElement(By.id("loginpassword"))
              .sendKeys(password);

        driver.findElement(
            By.xpath("//button[text()='Log in']")
        ).click();

        WebElement welcomeMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("nameofuser")
            )
        );

        Assert.assertTrue(
            welcomeMessage.getText().contains(username),
            "Login was not successful"
        );

        System.out.println("Login successful: " + username);
    }

    // Navigate to home page
    private void goToHome() {

        wait.until(
            ExpectedConditions.elementToBeClickable(By.id("nava"))
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#tbodyid .card")
            )
        );
    }

    // Open a product by its index on the displayed home page
    private String openProduct(int index) {

        List<WebElement> products = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tbodyid .card-title a")
            )
        );

        Assert.assertTrue(
            products.size() > index,
            "Product index is unavailable: " + index
        );

        String productName = products.get(index).getText();

        products.get(index).click();

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.linkText("Add to cart")
            )
        );

        System.out.println("Product opened: " + productName);

        return productName;
    }

    // Add the currently opened product to cart
    private void addCurrentProductToCart() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.linkText("Add to cart")
            )
        ).click();

        Alert alert = wait.until(
            ExpectedConditions.alertIsPresent()
        );

        String message = alert.getText();

        System.out.println("Add-to-cart message: " + message);

        alert.accept();

        Assert.assertTrue(
            message.toLowerCase().contains("product added"),
            "Product was not added: " + message
        );
    }

    // Open cart page
    private void openCart() {

        wait.until(
            ExpectedConditions.elementToBeClickable(By.id("cartur"))
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("tbodyid")
            )
        );
    }

    // Verify product is displayed in cart
    private boolean isProductInCart(String productName) {

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("tbodyid")
            )
        );

        List<WebElement> rows = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tbodyid tr")
            )
        );

        for (WebElement row : rows) {

            if (row.getText().contains(productName)) {
                System.out.println(
                    "Product found in cart: " + productName
                );
                return true;
            }
        }

        return false;
    }

    // Place an order
    private void placeOrder() {

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Place Order']")
            )
        ).click();

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("name")
            )
        ).sendKeys("Demo Test User");

        driver.findElement(By.id("country")).sendKeys("India");
        driver.findElement(By.id("city")).sendKeys("Bangalore");
        driver.findElement(By.id("card"))
              .sendKeys("4111111111111111");
        driver.findElement(By.id("month")).sendKeys("10");
        driver.findElement(By.id("year")).sendKeys("2028");

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Purchase']")
            )
        ).click();

        System.out.println("Purchase submitted");
    }

    // Verify purchase confirmation
    private void verifyOrderConfirmation() {

        WebElement confirmation = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".sweet-alert h2")
            )
        );

        Assert.assertTrue(
            confirmation.getText().contains(
                "Thank you for your purchase!"
            ),
            "Purchase confirmation was not displayed"
        );

        System.out.println("Order confirmation verified");

        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(".sweet-alert .confirm")
            )
        ).click();

        wait.until(
            ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".sweet-alert")
            )
        );
    }

    // Close order modal if it is still open
    private void closeOrderModalIfOpen() {

        List<WebElement> modals = driver.findElements(
            By.cssSelector("#orderModal.show")
        );

        if (!modals.isEmpty()) {

            WebElement closeButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.cssSelector("#orderModal button.close")
                )
            );

            try {
                closeButton.click();
            } catch (ElementClickInterceptedException e) {

                ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", closeButton);
            }

            wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("#orderModal.show")
                )
            );

            wait.until(
                ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector(".modal-backdrop")
                )
            );
        }

        System.out.println("Order modal closed");
    }

    // Logout
    private void logout() {

        WebElement logoutButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("logout2")
            )
        );

        try {
            logoutButton.click();
        } catch (ElementClickInterceptedException e) {

            ((JavascriptExecutor) driver)
                .executeScript(
                    "arguments[0].click();",
                    logoutButton
                );
        }

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("login2")
            )
        );

        System.out.println("Logout successful");
    }
}
