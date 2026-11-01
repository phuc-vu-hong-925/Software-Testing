package com.saucedemo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Module mô phỏng độc lập, không phải backend của SauceDemo. */
public class CheckoutService {
    private static final BigDecimal TAX_RATE = new BigDecimal("0.08");

    public double calculateSubtotal(double[] prices) {
        if (prices == null || prices.length == 0) return 0.0;
        BigDecimal total = BigDecimal.ZERO;
        for (double price : prices) {
            total = total.add(moneyInput(price, "Giá sản phẩm"));
        }
        return rounded(total);
    }

    public double calculateTax(double subtotal) {
        return rounded(moneyInput(subtotal, "Subtotal").multiply(TAX_RATE));
    }

    public double calculateTotal(double subtotal, double tax) {
        return rounded(moneyInput(subtotal, "Subtotal").add(moneyInput(tax, "Tax")));
    }

    public boolean validateShippingInfo(String firstName, String lastName, String postalCode) {
        if (firstName == null || firstName.trim().isEmpty()) return false;
        if (lastName == null || lastName.trim().isEmpty()) return false;
        if (postalCode == null || postalCode.trim().isEmpty()) return false;
        return true;
    }

    private BigDecimal moneyInput(double value, String field) {
        if (!Double.isFinite(value) || value < 0) {
            throw new IllegalArgumentException(field + " phải hữu hạn và không âm");
        }
        return BigDecimal.valueOf(value);
    }

    private double rounded(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
