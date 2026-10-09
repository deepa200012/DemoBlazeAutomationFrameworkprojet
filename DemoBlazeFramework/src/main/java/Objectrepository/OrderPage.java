package Objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderPage {

    WebDriver driver;

    // Name
    @FindBy(id = "name")
    private WebElement nameTextField;

    // Country
    @FindBy(id = "country")
    private WebElement countryTextField;

    // City
    @FindBy(id = "city")
    private WebElement cityTextField;

    // Credit Card
    @FindBy(id = "card")
    private WebElement creditCardTextField;

    // Month
    @FindBy(id = "month")
    private WebElement monthTextField;

    // Year
    @FindBy(id = "year")
    private WebElement yearTextField;

    // Purchase button
    @FindBy(xpath = "//button[text()='Purchase']")
    private WebElement purchaseButton;


    // Constructor
    public OrderPage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);
    }


    // Enter name
    public void enterName(String name) {

        nameTextField.sendKeys(name);
    }


    // Enter country
    public void enterCountry(String country) {

        countryTextField.sendKeys(country);
    }


    // Enter city
    public void enterCity(String city) {

        cityTextField.sendKeys(city);
    }


    // Enter credit card
    public void enterCreditCard(String card) {

        creditCardTextField.sendKeys(card);
    }


    // Enter month
    public void enterMonth(String month) {

        monthTextField.sendKeys(month);
    }


    // Enter year
    public void enterYear(String year) {

        yearTextField.sendKeys(year);
    }


    // Click Purchase
    public void clickPurchase() {

        purchaseButton.click();
    }


    // Complete order
    public void completeOrder(
            String name,
            String country,
            String city,
            String card,
            String month,
            String year) {

        enterName(name);
        enterCountry(country);
        enterCity(city);
        enterCreditCard(card);
        enterMonth(month);
        enterYear(year);

        clickPurchase();
    }
}