package com.saucedemo.unit;

import com.saucedemo.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutServiceTest {

    private CheckoutService checkoutService;

    @BeforeEach
    public void setUp() {
        checkoutService = new CheckoutService();
    }

    @Test
    @DisplayName("UT01 - Tính đúng tổng tiền của nhiều sản phẩm")
    public void testCalculateSubtotalValid() {
        double[] prices = {29.99, 9.99, 15.99};
        double result = checkoutService.calculateSubtotal(prices);
        assertEquals(55.97, result, 0.001);
    }

    @Test
    @DisplayName("UT02 - Giỏ hàng rỗng thì tổng tiền bằng 0")
    public void testCalculateSubtotalEmpty() {
        double[] prices = {};
        assertEquals(0.0, checkoutService.calculateSubtotal(prices));
    }

    @Test
    @DisplayName("UT03 - Ném ra ngoại lệ khi giá sản phẩm bị âm")
    public void testCalculateSubtotalNegativePrice() {
        double[] prices = {29.99, -5.0};
        assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.calculateSubtotal(prices);
        });
    }

    @Test
    @DisplayName("UT04 - Tính thuế 8% chính xác")
    public void testCalculateTax() {
        double subtotal = 55.97;
        double tax = checkoutService.calculateTax(subtotal);
        assertEquals(4.48, tax, 0.001);
    }

    @Test
    @DisplayName("UT05 - Kiểm tra hợp lệ thông tin giao hàng khi điền đầy đủ")
    public void testValidateShippingInfoValid() {
        assertTrue(checkoutService.validateShippingInfo("Phuc", "Vu", "100000"));
    }

    @Test
    @DisplayName("UT06 - Báo lỗi khi thiếu First Name hoặc Postal Code")
    public void testValidateShippingInfoInvalid() {
        assertFalse(checkoutService.validateShippingInfo("", "Vu", "100000"));
        assertFalse(checkoutService.validateShippingInfo("Phuc", "Vu", null));
    }
}