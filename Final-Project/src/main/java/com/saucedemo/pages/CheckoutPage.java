package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;

    private By checkoutBtn = By.id("checkout");
    private By firstNameInput = By.id("first-name");
    private By lastNameInput = By.id("last-name");
    private By postalCodeInput = By.id("postal-code");
    private By continueBtn = By.id("continue");
    private By finishBtn = By.id("finish");
    private By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void proceedToCheckout() {
        driver.findElement(checkoutBtn).click();
    }

    public void fillInformation(String first, String last, String zip) {
        driver.findElement(firstNameInput).sendKeys(first);
        driver.findElement(lastNameInput).sendKeys(last);
        driver.findElement(postalCodeInput).sendKeys(zip);
        driver.findElement(continueBtn).click();
    }

    public void finishOrder() {
        driver.findElement(finishBtn).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(completeHeader).getText();
    }
}