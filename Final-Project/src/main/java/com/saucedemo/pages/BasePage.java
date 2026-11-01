package com.saucedemo.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private static final By BADGE = By.className("shopping_cart_badge");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.getLong("wait.seconds", 15L)));
    }
    protected WebElement visible(By locator) { return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); }
    protected void click(By locator) { wait.until(ExpectedConditions.elementToBeClickable(locator)).click(); }
    protected void fill(By locator, String text) {
        WebElement element = visible(locator);
        element.clear();
        if (text != null && !text.isEmpty()) element.sendKeys(text);
    }
    public String getPageTitle() { return visible(By.className("title")).getText(); }
    public String getCartItemCount() {
        List<WebElement> badges = driver.findElements(BADGE);
        return badges.isEmpty() ? "0" : badges.get(0).getText();
    }
    public void waitForCartCount(int expected) {
        wait.until(d -> getCartItemCount().equals(Integer.toString(expected)));
    }
    public static BigDecimal parseMoney(String text) {
        return new BigDecimal(text.substring(text.indexOf('$') + 1).trim());
    }
    public void logout() {
        click(By.id("react-burger-menu-btn"));
        click(By.id("logout_sidebar_link"));
        visible(By.id("login-button"));
    }
}
