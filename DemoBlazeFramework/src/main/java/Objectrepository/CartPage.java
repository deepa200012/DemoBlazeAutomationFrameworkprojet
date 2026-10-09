package Objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

    WebDriver driver;


    @FindBy(xpath = "//button[text()='Place Order']")
    private WebElement placeOrderButton;


    @FindBy(id = "name")
    private WebElement nameTextField;


    @FindBy(id = "country")
    private WebElement countryTextField;


    @FindBy(id = "city")
    private WebElement cityTextField;


    @FindBy(id = "card")
    private WebElement cardTextField;


    @FindBy(id = "month")
    private WebElement monthTextField;


    @FindBy(id = "year")
    private WebElement yearTextField;


    @FindBy(xpath = "//button[text()='Purchase']")
    private WebElement purchaseButton;


    public CartPage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);
    }


    public void clickPlaceOrder() {

        placeOrderButton.click();
    }


    public void enterName(String name) {

        nameTextField.sendKeys(name);
    }


    public void enterCountry(String country) {

        countryTextField.sendKeys(country);
    }


    public void enterCity(String city) {

        cityTextField.sendKeys(city);
    }


    public void enterCard(String card) {

        cardTextField.sendKeys(card);
    }


    public void enterMonth(String month) {

        monthTextField.sendKeys(month);
    }


    public void enterYear(String year) {

        yearTextField.sendKeys(year);
    }


    public void clickPurchase() {

        purchaseButton.click();
    }


    public void placeOrder(
            String name,
            String country,
            String city,
            String card,
            String month,
            String year) {

        enterName(name);

        enterCountry(country);

        enterCity(city);

        enterCard(card);

        enterMonth(month);

        enterYear(year);

        clickPurchase();
    }
}