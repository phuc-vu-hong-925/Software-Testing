package com.saucedemo.automation;

import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.util.*;
import static com.saucedemo.pages.InventoryPage.*;
import static org.junit.jupiter.api.Assertions.*;

public class SauceDemoTest extends BaseUiTest {
    private static final String BACKPACK_NAME = "Sauce Labs Backpack";
    private static final String BIKE_NAME = "Sauce Labs Bike Light";
    private static BigDecimal amount(String value) { return new BigDecimal(value); }

    @Test @DisplayName("TC01 - Sai mật khẩu bị từ chối")
    void testLoginFailedInvalidPassword() {
        loginPage.login("standard_user", "wrong_password");
        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"));
        assertTrue(loginPage.isDisplayed());
    }
    @Test @DisplayName("TC02 - Tài khoản khóa bị từ chối")
    void testLoginLockedOutUser() {
        loginPage.login("locked_out_user", "secret_sauce");
        assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out."));
        assertTrue(loginPage.isDisplayed());
    }
    @Test @DisplayName("TC03 - Đăng nhập hợp lệ")
    void testLoginSuccess() {
        login(); assertEquals(6, inventoryPage.getProductNames().size());
    }
    @Test @DisplayName("TC04 - Thêm Backpack và kiểm tra đúng hàng, giá")
    void testAddToCart() {
        login(); inventoryPage.addBackpackToCart(); inventoryPage.waitForCartCount(1);
        assertEquals("1", inventoryPage.getCartItemCount());
        inventoryPage.goToCart();
        assertEquals(List.of(BACKPACK_NAME), cartPage.getItemNames());
        assertEquals(amount("29.99"), cartPage.getItemPrice(BACKPACK_NAME));
        assertEquals(List.of("1"), cartPage.getQuantities());
    }
    @Test @DisplayName("TC05 - E2E kiểm tra hàng, tiền và hoàn tất đơn")
    void testEndToEndCheckout() {
        openCheckoutWithBackpack();
        checkoutPage.enterShippingInfo("Phuc", "Vu", "100000");
        assertEquals("Checkout: Overview", checkoutPage.getPageTitle());
        assertEquals(List.of(BACKPACK_NAME), checkoutPage.getItemNames());
        assertEquals(amount("29.99"), checkoutPage.getSubtotal());
        assertEquals(amount("2.40"), checkoutPage.getTax());
        assertEquals(amount("32.39"), checkoutPage.getTotal());
        checkpoint("single-item-overview");
        checkoutPage.clickFinish();
        assertEquals("Thank you for your order!", checkoutPage.getOrderCompleteMessage());
        checkoutPage.waitForCartCount(0);
    }
    @Test @DisplayName("TC06 - Thiếu username")
    void testLoginEmptyUsername() {
        loginPage.login("", "secret_sauce");
        assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }
    @Test @DisplayName("TC07 - Thiếu password")
    void testLoginEmptyPassword() {
        loginPage.login("standard_user", "");
        assertTrue(loginPage.getErrorMessage().contains("Password is required"));
    }
    @Test @DisplayName("TC08 - Thêm hai sản phẩm")
    void testAddTwoProducts() {
        login(); inventoryPage.addProduct(BACKPACK); inventoryPage.addProduct(BIKE_LIGHT);
        inventoryPage.waitForCartCount(2); assertEquals("2", inventoryPage.getCartItemCount());
        inventoryPage.goToCart();
        assertEquals(Set.of(BACKPACK_NAME, BIKE_NAME), new HashSet<>(cartPage.getItemNames()));
        assertEquals(2, cartPage.getItemNames().size());
        assertEquals(amount("29.99"), cartPage.getItemPrice(BACKPACK_NAME));
        assertEquals(amount("9.99"), cartPage.getItemPrice(BIKE_NAME));
    }
    @Test @DisplayName("TC09 - Xóa một trong hai sản phẩm")
    void testRemoveOneOfTwoProducts() {
        login(); inventoryPage.addProduct(BACKPACK); inventoryPage.addProduct(BIKE_LIGHT); inventoryPage.goToCart();
        cartPage.removeProduct(BACKPACK); cartPage.waitForCartCount(1);
        assertEquals(List.of(BIKE_NAME), cartPage.getItemNames()); assertEquals("1", cartPage.getCartItemCount());
    }
    @Test @DisplayName("TC10 - Xóa sản phẩm cuối, giỏ rỗng")
    void testRemoveLastProduct() {
        login(); inventoryPage.addBackpackToCart(); inventoryPage.goToCart();
        cartPage.removeProduct(BACKPACK); cartPage.waitForCartCount(0);
        assertTrue(cartPage.getItemNames().isEmpty()); assertEquals("0", cartPage.getCartItemCount());
    }
    @Test @DisplayName("TC11 - Xóa hàng tại trang sản phẩm")
    void testRemoveFromInventory() {
        login(); inventoryPage.addBackpackToCart(); inventoryPage.removeProduct(BACKPACK); inventoryPage.waitForCartCount(0);
        inventoryPage.goToCart(); assertTrue(cartPage.getItemNames().isEmpty());
    }
    @Test @DisplayName("TC12 - Tiếp tục mua sắm giữ nguyên giỏ")
    void testContinueShopping() {
        login(); inventoryPage.addBackpackToCart(); inventoryPage.goToCart(); cartPage.continueShopping();
        assertEquals("Products", inventoryPage.getPageTitle()); assertEquals("1", inventoryPage.getCartItemCount());
        inventoryPage.goToCart(); assertEquals(List.of(BACKPACK_NAME), cartPage.getItemNames());
    }
    @Test @DisplayName("TC13 - Checkout thiếu First Name")
    void testCheckoutMissingFirstName() {
        openCheckoutWithBackpack(); checkoutPage.enterShippingInfo("", "Vu", "100000");
        assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"));
        assertEquals("Checkout: Your Information", checkoutPage.getPageTitle());
    }
    @Test @DisplayName("TC14 - Checkout thiếu Last Name")
    void testCheckoutMissingLastName() {
        openCheckoutWithBackpack(); checkoutPage.enterShippingInfo("Phuc", "", "100000");
        assertTrue(checkoutPage.getErrorMessage().contains("Last Name is required"));
        assertEquals("Checkout: Your Information", checkoutPage.getPageTitle());
    }
    @Test @DisplayName("TC15 - Checkout thiếu Postal Code")
    void testCheckoutMissingPostalCode() {
        openCheckoutWithBackpack(); checkoutPage.enterShippingInfo("Phuc", "Vu", "");
        assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"));
        assertEquals("Checkout: Your Information", checkoutPage.getPageTitle());
    }
    @Test @DisplayName("TC16 - Hủy nhập thông tin quay lại giỏ")
    void testCancelShipping() {
        openCheckoutWithBackpack(); checkoutPage.cancelToCart();
        assertEquals("Your Cart", cartPage.getPageTitle()); assertEquals(List.of(BACKPACK_NAME), cartPage.getItemNames());
    }
    @Test @DisplayName("TC17 - Hủy overview giữ hàng trong giỏ")
    void testCancelOverview() {
        openCheckoutWithBackpack(); checkoutPage.enterShippingInfo("Phuc", "Vu", "100000");
        assertEquals("Checkout: Overview", checkoutPage.getPageTitle()); checkoutPage.cancelToInventory();
        assertEquals("Products", inventoryPage.getPageTitle()); inventoryPage.goToCart();
        assertEquals(List.of(BACKPACK_NAME), cartPage.getItemNames());
    }
    @Test @DisplayName("TC18 - Sắp xếp giá tăng dần")
    void testSortPriceAscending() { checkPriceSort("lohi", Comparator.naturalOrder()); }
    @Test @DisplayName("TC19 - Sắp xếp tên giảm dần")
    void testSortNameDescending() { checkNameSort("za", Comparator.reverseOrder()); }
    @Test @DisplayName("TC20 - Đăng xuất")
    void testLogout() { login(); inventoryPage.logout(); assertTrue(loginPage.isDisplayed()); }
    @Test @DisplayName("TC21 - Chi tiết sản phẩm và quay lại")
    void testProductDetails() {
        login(); inventoryPage.openProduct(BACKPACK_NAME);
        assertEquals(BACKPACK_NAME, inventoryPage.getDetailName()); assertEquals(amount("29.99"), inventoryPage.getDetailPrice());
        inventoryPage.backToProducts(); assertEquals("Products", inventoryPage.getPageTitle());
    }
    @Test @DisplayName("TC22 - Sắp xếp giá giảm dần")
    void testSortPriceDescending() { checkPriceSort("hilo", Comparator.reverseOrder()); }
    @Test @DisplayName("TC23 - Sắp xếp tên tăng dần")
    void testSortNameAscending() { checkNameSort("az", Comparator.naturalOrder()); }
    @Test @DisplayName("TC24 - Đối chiếu tổng hai sản phẩm và quay về trang chủ sau đặt hàng")
    void testTwoProductCheckout() {
        login(); inventoryPage.addProduct(BACKPACK); inventoryPage.addProduct(BIKE_LIGHT); inventoryPage.goToCart(); cartPage.checkout();
        checkoutPage.enterShippingInfo("Phuc", "Vu", "100000");
        assertEquals(2, checkoutPage.getItemNames().size());
        assertEquals(Set.of(BACKPACK_NAME, BIKE_NAME), new HashSet<>(checkoutPage.getItemNames()));
        assertEquals(amount("39.98"), checkoutPage.getSubtotal()); assertEquals(amount("3.20"), checkoutPage.getTax());
        assertEquals(amount("43.18"), checkoutPage.getTotal()); checkpoint("two-item-overview"); checkoutPage.clickFinish();
        assertEquals("Thank you for your order!", checkoutPage.getOrderCompleteMessage()); checkoutPage.backHome();
        assertEquals("Products", inventoryPage.getPageTitle()); assertEquals("0", inventoryPage.getCartItemCount());
    }
    private void checkPriceSort(String option, Comparator<BigDecimal> comparator) {
        login(); List<BigDecimal> expected = new ArrayList<>(inventoryPage.getProductPrices());
        assertEquals(6, expected.size()); expected.sort(comparator); inventoryPage.sortBy(option);
        assertEquals(expected, inventoryPage.getProductPrices());
    }
    private void checkNameSort(String option, Comparator<String> comparator) {
        login(); List<String> expected = new ArrayList<>(inventoryPage.getProductNames());
        assertEquals(6, expected.size()); expected.sort(comparator); inventoryPage.sortBy(option);
        assertEquals(expected, inventoryPage.getProductNames());
    }
}
