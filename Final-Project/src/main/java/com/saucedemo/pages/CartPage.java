package com.saucedemo.pages;
import org.openqa.selenium.*;
import java.math.BigDecimal;
import java.util.List;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) { super(driver); }
    public List<String> getItemNames() {
        visible(By.className("cart_list"));
        return driver.findElements(By.className("inventory_item_name")).stream().map(WebElement::getText).toList();
    }
    public BigDecimal getItemPrice(String name) {
        for (WebElement item : driver.findElements(By.className("cart_item"))) {
            if (item.findElement(By.className("inventory_item_name")).getText().equals(name))
                return parseMoney(item.findElement(By.className("inventory_item_price")).getText());
        }
        throw new NoSuchElementException("Không có sản phẩm trong giỏ: " + name);
    }
    public List<String> getQuantities() {
        return driver.findElements(By.className("cart_quantity")).stream().map(WebElement::getText).toList();
    }
    public void removeProduct(String slug) {
        By locator = By.id("remove-" + slug);
        WebElement button = visible(locator); button.click();
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf(button));
    }
    public void continueShopping() { click(By.id("continue-shopping")); visible(By.className("inventory_list")); }
    public void checkout() { click(By.id("checkout")); visible(By.id("first-name")); }
}
