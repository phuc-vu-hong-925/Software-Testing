package com.saucedemo.pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.math.BigDecimal;
import java.util.List;

public class InventoryPage extends BasePage {
    public static final String BACKPACK = "sauce-labs-backpack";
    public static final String BIKE_LIGHT = "sauce-labs-bike-light";
    public InventoryPage(WebDriver driver) { super(driver); }
    public void addBackpackToCart() { addProduct(BACKPACK); }
    public void addProduct(String slug) {
        click(By.id("add-to-cart-" + slug));
        visible(By.id("remove-" + slug));
    }
    public void removeProduct(String slug) {
        click(By.id("remove-" + slug));
        visible(By.id("add-to-cart-" + slug));
    }
    public void goToCart() { click(By.className("shopping_cart_link")); visible(By.id("checkout")); }
    public List<String> getProductNames() {
        visible(By.className("inventory_list"));
        return driver.findElements(By.className("inventory_item_name")).stream().map(WebElement::getText).toList();
    }
    public List<BigDecimal> getProductPrices() {
        visible(By.className("inventory_list"));
        return driver.findElements(By.className("inventory_item_price")).stream().map(e -> parseMoney(e.getText())).toList();
    }
    public void sortBy(String value) {
        Select select = new Select(visible(By.className("product_sort_container")));
        select.selectByValue(value);
        wait.until(d -> new Select(d.findElement(By.className("product_sort_container"))).getFirstSelectedOption().getAttribute("value").equals(value));
    }
    public void openProduct(String name) {
        for (WebElement element : driver.findElements(By.className("inventory_item_name"))) {
            if (element.getText().equals(name)) { element.click(); visible(By.className("inventory_details_name")); return; }
        }
        throw new NoSuchElementException("Không tìm thấy sản phẩm: " + name);
    }
    public String getDetailName() { return visible(By.className("inventory_details_name")).getText(); }
    public BigDecimal getDetailPrice() { return parseMoney(visible(By.className("inventory_details_price")).getText()); }
    public void backToProducts() { click(By.id("back-to-products")); visible(By.className("inventory_list")); }
}
