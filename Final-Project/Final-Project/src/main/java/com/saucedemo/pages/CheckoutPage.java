package com.saucedemo.pages;
import org.openqa.selenium.*;
import java.math.BigDecimal;
import java.util.List;

public class CheckoutPage extends BasePage {
    public CheckoutPage(WebDriver driver) { super(driver); }
    public void clickCheckout() { click(By.id("checkout")); visible(By.id("first-name")); }
    public void enterShippingInfo(String firstName, String lastName, String postalCode) {
        fill(By.id("first-name"), firstName); fill(By.id("last-name"), lastName); fill(By.id("postal-code"), postalCode);
        click(By.id("continue"));
    }
    public String getErrorMessage() { return visible(By.cssSelector("[data-test='error']")).getText(); }
    public List<String> getItemNames() {
        visible(By.className("summary_info"));
        return driver.findElements(By.className("inventory_item_name")).stream().map(WebElement::getText).toList();
    }
    public BigDecimal getSubtotal() { return parseMoney(visible(By.className("summary_subtotal_label")).getText()); }
    public BigDecimal getTax() { return parseMoney(visible(By.className("summary_tax_label")).getText()); }
    public BigDecimal getTotal() { return parseMoney(visible(By.className("summary_total_label")).getText()); }
    public void clickFinish() { click(By.id("finish")); visible(By.className("complete-header")); }
    public String getOrderCompleteMessage() { return visible(By.className("complete-header")).getText(); }
    public void cancelToCart() { click(By.id("cancel")); visible(By.id("checkout")); }
    public void cancelToInventory() { click(By.id("cancel")); visible(By.className("inventory_list")); }
    public void backHome() { click(By.id("back-to-products")); visible(By.className("inventory_list")); }
}
