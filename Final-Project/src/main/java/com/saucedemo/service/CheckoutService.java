package com.saucedemo.automation;

import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SauceDemoTest {
    private static WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CheckoutPage checkoutPage;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("TC_AUTO_01: Đăng nhập thành công với tài khoản chuẩn")
    void testLoginSuccess() {
        loginPage.login("standard_user", "secret_sauce");
        assertEquals("Products", inventoryPage.getTitle());
    }

    @Test
    @Order(2)
    @DisplayName("TC_AUTO_02: Đăng nhập thất bại khi tài khoản bị khóa")
    void testLoginLockedUser() {
        loginPage.login("locked_out_user", "secret_sauce");
        assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out."));
    }

    @Test
    @Order(3)
    @DisplayName("TC_AUTO_03: Kịch bản End-to-End mua hàng hoàn tất")
    void testEndToEndPurchase() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addBackpackToCart();
        assertEquals("1", inventoryPage.getCartCount());

        inventoryPage.goToCart();
        checkoutPage.proceedToCheckout();
        checkoutPage.fillInformation("Phuc", "Vu", "100000");
        checkoutPage.finishOrder();

        assertEquals("Thank you for your order!", checkoutPage.getSuccessMessage());
    }
}