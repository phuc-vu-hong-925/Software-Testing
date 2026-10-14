package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
    private WebDriver driver;

    // Locators
    private By pageTitle = By.className("title");
    private By backpackAddToCartBtn = By.id("add-to-cart-sauce-labs-backpack");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public void addBackpackToCart() {
        driver.findElement(backpackAddToCartBtn).click();
    }

    public String getCartCount() {
        return driver.findElement(cartBadge).getText();
    }

    public void goToCart() {
        driver.findElement(cartLink).click();
    }
}