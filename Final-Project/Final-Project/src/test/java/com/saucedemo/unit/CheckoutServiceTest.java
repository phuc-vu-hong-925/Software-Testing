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

    @Test
    @DisplayName("UT07 - Mảng null trả về 0")
    public void testCalculateSubtotalNull() {
        assertEquals(0.0, checkoutService.calculateSubtotal(null), 0.001);
    }

    @Test
    @DisplayName("UT08 - Một sản phẩm")
    public void testCalculateSubtotalSingleItem() {
        assertEquals(29.99, checkoutService.calculateSubtotal(new double[]{29.99}), 0.001);
    }

    @Test
    @DisplayName("UT09 - Giá bằng 0")
    public void testCalculateSubtotalZeroPrice() {
        assertEquals(9.99, checkoutService.calculateSubtotal(new double[]{0, 9.99}), 0.001);
    }

    @Test
    @DisplayName("UT10 - Làm tròn subtotal lên tại nửa cent")
    public void testCalculateSubtotalRoundUp() {
        assertEquals(1.01, checkoutService.calculateSubtotal(new double[]{1.005}), 0.001);
    }

    @Test
    @DisplayName("UT11 - Làm tròn subtotal xuống")
    public void testCalculateSubtotalRoundDown() {
        assertEquals(1.00, checkoutService.calculateSubtotal(new double[]{1.004}), 0.001);
    }

    @Test
    @DisplayName("UT12 - Từ chối giá NaN")
    public void testCalculateSubtotalNaN() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateSubtotal(new double[]{Double.NaN}));
    }

    @Test
    @DisplayName("UT13 - Từ chối giá vô hạn")
    public void testCalculateSubtotalInfinity() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateSubtotal(new double[]{Double.POSITIVE_INFINITY}));
    }

    @Test
    @DisplayName("UT14 - Thuế với subtotal 0")
    public void testCalculateTaxZero() {
        assertEquals(0, checkoutService.calculateTax(0), 0.001);
    }

    @Test
    @DisplayName("UT15 - Từ chối subtotal âm khi tính thuế")
    public void testCalculateTaxNegative() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateTax(-0.01));
    }

    @Test
    @DisplayName("UT16 - Thuế làm tròn lên")
    public void testCalculateTaxRoundUp() {
        assertEquals(0.01, checkoutService.calculateTax(0.07), 0.001);
    }

    @Test
    @DisplayName("UT17 - Thuế làm tròn xuống")
    public void testCalculateTaxRoundDown() {
        assertEquals(0.00, checkoutService.calculateTax(0.05), 0.001);
    }

    @Test
    @DisplayName("UT18 - Từ chối NaN khi tính thuế")
    public void testCalculateTaxNaN() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateTax(Double.NaN));
    }

    @Test
    @DisplayName("UT19 - Từ chối vô hạn khi tính thuế")
    public void testCalculateTaxInfinity() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateTax(Double.POSITIVE_INFINITY));
    }

    @Test
    @DisplayName("UT20 - Tổng thanh toán hợp lệ")
    public void testCalculateTotalValid() {
        assertEquals(60.45, checkoutService.calculateTotal(55.97, 4.48), 0.001);
    }

    @Test
    @DisplayName("UT21 - Tổng thanh toán 0")
    public void testCalculateTotalZero() {
        assertEquals(0, checkoutService.calculateTotal(0, 0), 0.001);
    }

    @Test
    @DisplayName("UT22 - Tổng thanh toán làm tròn HALF_UP")
    public void testCalculateTotalRoundUp() {
        assertEquals(1.01, checkoutService.calculateTotal(1.005, 0), 0.001);
    }

    @Test
    @DisplayName("UT23 - Từ chối subtotal âm khi tính tổng")
    public void testCalculateTotalNegativeSubtotal() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateTotal(-0.01, 0));
    }

    @Test
    @DisplayName("UT24 - Từ chối thuế âm")
    public void testCalculateTotalNegativeTax() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateTotal(1, -0.01));
    }

    @Test
    @DisplayName("UT25 - Từ chối subtotal NaN")
    public void testCalculateTotalNaNSubtotal() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateTotal(Double.NaN, 0));
    }

    @Test
    @DisplayName("UT26 - Từ chối thuế NaN")
    public void testCalculateTotalNaNTax() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateTotal(1, Double.NaN));
    }

    @Test
    @DisplayName("UT27 - Từ chối subtotal vô hạn")
    public void testCalculateTotalInfiniteSubtotal() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateTotal(Double.POSITIVE_INFINITY, 0));
    }

    @Test
    @DisplayName("UT28 - Từ chối thuế vô hạn")
    public void testCalculateTotalInfiniteTax() {
        assertThrows(IllegalArgumentException.class, () -> checkoutService.calculateTotal(1, Double.POSITIVE_INFINITY));
    }

    @Test
    @DisplayName("UT29 - First Name null")
    public void testShippingFirstNameNull() {
        assertFalse(checkoutService.validateShippingInfo(null, "Vu", "100000"));
    }

    @Test
    @DisplayName("UT30 - First Name chỉ khoảng trắng")
    public void testShippingFirstNameWhitespace() {
        assertFalse(checkoutService.validateShippingInfo("   ", "Vu", "100000"));
    }

    @Test
    @DisplayName("UT31 - Last Name null")
    public void testShippingLastNameNull() {
        assertFalse(checkoutService.validateShippingInfo("Phuc", null, "100000"));
    }

    @Test
    @DisplayName("UT32 - Last Name rỗng")
    public void testShippingLastNameEmpty() {
        assertFalse(checkoutService.validateShippingInfo("Phuc", "", "100000"));
    }

    @Test
    @DisplayName("UT33 - Last Name chỉ khoảng trắng")
    public void testShippingLastNameWhitespace() {
        assertFalse(checkoutService.validateShippingInfo("Phuc", "   ", "100000"));
    }

    @Test
    @DisplayName("UT34 - Postal Code rỗng")
    public void testShippingPostalCodeEmpty() {
        assertFalse(checkoutService.validateShippingInfo("Phuc", "Vu", ""));
    }

    @Test
    @DisplayName("UT35 - Postal Code chỉ khoảng trắng")
    public void testShippingPostalCodeWhitespace() {
        assertFalse(checkoutService.validateShippingInfo("Phuc", "Vu", "   "));
    }

    @Test
    @DisplayName("UT36 - Dữ liệu không rỗng có khoảng trắng hai đầu")
    public void testShippingPaddedValid() {
        assertTrue(checkoutService.validateShippingInfo(" Phuc ", " Vu ", " 100000 "));
    }
}
