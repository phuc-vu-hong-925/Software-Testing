package com.saucedemo.automation;

import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SauceDemoTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CheckoutPage checkoutPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("TC01 - Đăng nhập thất bại khi sai mật khẩu")
    public void testLoginFailedInvalidPassword() {
        loginPage.login("standard_user", "wrong_password");
        String error = loginPage.getErrorMessage();
        assertTrue(error.contains("Username and password do not match any user in this service"));
    }

    @Test
    @Order(2)
    @DisplayName("TC02 - Đăng nhập thất bại khi tài khoản bị khóa")
    public void testLoginLockedOutUser() {
        loginPage.login("locked_out_user", "secret_sauce");
        String error = loginPage.getErrorMessage();
        assertTrue(error.contains("Sorry, this user has been locked out."));
    }

    @Test
    @Order(3)
    @DisplayName("TC03 - Đăng nhập thành công với tài khoản chuẩn")
    public void testLoginSuccess() {
        loginPage.login("standard_user", "secret_sauce");
        assertEquals("Products", inventoryPage.getPageTitle());
    }

    @Test
    @Order(4)
    @DisplayName("TC04 - Thêm sản phẩm vào giỏ hàng thành công")
    public void testAddToCart() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();
        assertEquals("1", inventoryPage.getCartItemCount());
    }

    @Test
    @Order(5)
    @DisplayName("TC05 - Hoàn tất quy trình đặt hàng thành công (End-to-End Checkout)")
    public void testEndToEndCheckout() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();

        checkoutPage.clickCheckout();
        checkoutPage.enterShippingInfo("Phuc", "Vu", "100000");
        checkoutPage.clickFinish();

        assertEquals("Thank you for your order!", checkoutPage.getOrderCompleteMessage());
    }
}