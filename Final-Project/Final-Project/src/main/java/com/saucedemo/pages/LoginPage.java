package com.saucedemo.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final By USER = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN = By.id("login-button");
    public LoginPage(WebDriver driver) { super(driver); }
    public void enterUsername(String username) { fill(USER, username); }
    public void enterPassword(String password) { fill(PASSWORD, password); }
    public void clickLogin() { click(LOGIN); }
    public void login(String username, String password) {
        enterUsername(username); enterPassword(password); clickLogin();
    }
    public String getErrorMessage() { return visible(By.cssSelector("[data-test='error']")).getText(); }
    public boolean isDisplayed() { return visible(LOGIN).isDisplayed(); }
}
