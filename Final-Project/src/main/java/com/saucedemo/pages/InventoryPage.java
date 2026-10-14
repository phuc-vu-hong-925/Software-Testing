package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
    private WebDriver driver;

    // Locators
    private By titleHeader = By.className("title");
    private By addToCartBackpackButton = By.id("add-to-cart-sauce-labs-backpack");
    private By shoppingCartBadge = By.className("shopping_cart_badge");
    private By cartButton = By.className("shopping_cart_link");

    // Constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public String getPageTitle() {
        return driver.findElement(titleHeader).getText();
    }

    public void addBackpackToCart() {
        driver.findElement(addToCartBackpackButton).click();
    }

    public String getCartItemCount() {
        return driver.findElement(shoppingCartBadge).getText();
    }

    public void goToCart() {
        driver.findElement(cartButton).click();
    }
}