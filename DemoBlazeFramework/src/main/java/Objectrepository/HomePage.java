package Objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;


    @FindBy(id = "login2")
    private WebElement loginLink;


    @FindBy(id = "signin2")
    private WebElement signupLink;


    @FindBy(id = "cartur")
    private WebElement cartLink;


    @FindBy(xpath = "//a[text()='Home']")
    private WebElement homeLink;


    public HomePage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);
    }


    public void clickLogin() {

        loginLink.click();
    }


    public void clickSignup() {

        signupLink.click();
    }


    public void clickCart() {

        cartLink.click();
    }


    public void clickHome() {

        homeLink.click();
    }
}