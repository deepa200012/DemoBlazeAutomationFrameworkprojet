package Objectrepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

    WebDriver driver;


    @FindBy(xpath = "//a[contains(@href,'prod.html?idp_')]")
    private List<WebElement> products;


    @FindBy(xpath = "//a[text()='Samsung galaxy s6']")
    private WebElement samsungGalaxyS6;


    @FindBy(xpath = "//a[text()='Nokia lumia 1520']")
    private WebElement nokiaLumia1520;


    public ProductPage(WebDriver driver) {

        this.driver = driver;

        PageFactory.initElements(driver, this);
    }


    public int getProductCount() {

        return products.size();
    }


    public void clickSamsungGalaxyS6() {

        samsungGalaxyS6.click();
    }


    public void clickNokiaLumia1520() {

        nokiaLumia1520.click();
    }
}