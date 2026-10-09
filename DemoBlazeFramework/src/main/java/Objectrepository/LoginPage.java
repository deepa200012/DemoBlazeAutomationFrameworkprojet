package Objectrepository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;


    @FindBy(id = "loginusername")
    private WebElement usernameTextField;


    @FindBy(id = "loginpassword")
    private WebElement passwordTextField;


    @FindBy(xpath = "//button[text()='Log in']")
    private WebElement loginButton;


    public LoginPage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);
    }


    public void enterUsername(String username) {
        usernameTextField.sendKeys(username);
    }


    public void enterPassword(String password) {

        passwordTextField.sendKeys(password);
    }


    public void clickLogin() {

        loginButton.click();
    }


    public void login(
            String username,
            String password) {

        enterUsername(username);

        enterPassword(password);

        clickLogin();
    }
}