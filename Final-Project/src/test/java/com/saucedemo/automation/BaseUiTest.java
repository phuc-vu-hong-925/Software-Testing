package com.saucedemo.automation;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.saucedemo.pages.*;
import java.nio.file.*;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

@Tag("ui")
public abstract class BaseUiTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;

    // Invoked before @AfterEach: capture while the browser is still open.
    @RegisterExtension
    final AfterTestExecutionCallback screenshotExtension = context -> {
        boolean failed = context.getExecutionException().isPresent();
        if (failed || "all".equals(System.getProperty("screenshots", "all"))) {
            capture(context.getRequiredTestMethod().getName(), failed ? "FAIL" : "PASS");
        }
    };

    @BeforeEach
    void setUp(TestInfo info) {
        // Maven may pass an empty property; Selenium treats empty driver paths as invalid.
        if (System.getProperty("webdriver.chrome.driver", "").isBlank()) System.clearProperty("webdriver.chrome.driver");
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "true"))) options.addArguments("--headless=new");
        options.addArguments("--window-size=1440,1000", "--disable-dev-shm-usage");
        if ("root".equals(System.getProperty("user.name"))) options.addArguments("--no-sandbox");
        options.setExperimentalOption("prefs", java.util.Map.of("credentials_enable_service", false, "profile.password_manager_enabled", false));
        String binary = System.getProperty("chrome.binary", "");
        if (!binary.isBlank()) options.setBinary(binary);
        try {
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
            saveEnvironment();
            driver.get(System.getProperty("baseUrl", "https://www.saucedemo.com/"));
            loginPage = new LoginPage(driver);
            inventoryPage = new InventoryPage(driver);
            cartPage = new CartPage(driver);
            checkoutPage = new CheckoutPage(driver);
            loginPage.isDisplayed();
        } catch (RuntimeException | Error failure) {
            capture(info.getTestMethod().orElseThrow().getName(), "SETUP_FAIL");
            if (driver != null) { try { driver.quit(); } catch (RuntimeException ignored) {} driver = null; }
            throw failure;
        }
    }
    @AfterEach
    void tearDown() { if (driver != null) { driver.quit(); driver = null; } }

    protected void login() {
        loginPage.login("standard_user", "secret_sauce");
        Assertions.assertEquals("Products", inventoryPage.getPageTitle());
    }
    protected void openCheckoutWithBackpack() {
        login(); inventoryPage.addBackpackToCart(); inventoryPage.goToCart(); cartPage.checkout();
    }
    protected void checkpoint(String name) { capture(name, "EVIDENCE"); }
    private void capture(String method, String state) {
        if (!(driver instanceof TakesScreenshot camera)) return;
        try {
            Path folder = Path.of("target", "screenshots"); Files.createDirectories(folder);
            String name = getClass().getSimpleName() + "_" + method + "_" + state + ".png";
            Files.write(folder.resolve(name), camera.getScreenshotAs(OutputType.BYTES));
        } catch (Exception captureError) {
            System.err.println("Không chụp được ảnh: " + captureError.getMessage());
        }
    }
    private void saveEnvironment() {
        try {
            Properties props = new Properties();
            props.setProperty("capturedAtUtc", Instant.now().toString());
            props.setProperty("os", System.getProperty("os.name"));
            props.setProperty("java", System.getProperty("java.version"));
            props.setProperty("browser", ((RemoteWebDriver) driver).getCapabilities().getBrowserVersion());
            props.setProperty("baseUrl", System.getProperty("baseUrl", "https://www.saucedemo.com/"));
            props.setProperty("headless", System.getProperty("headless", "true"));
            Files.createDirectories(Path.of("target"));
            try (OutputStream out = Files.newOutputStream(Path.of("target", "test-environment.properties"))) { props.store(out, "UI test environment"); }
        } catch (Exception error) { System.err.println("Không ghi được môi trường: " + error.getMessage()); }
    }
}
