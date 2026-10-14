package com.saucedemo.service;

public class CheckoutService {

    // Tính tổng tiền sản phẩm (chưa bao gồm thuế)
    public double calculateSubtotal(double[] prices) {
        if (prices == null || prices.length == 0) {
            return 0.0;
        }
        double total = 0.0;
        for (double price : prices) {
            if (price < 0) {
                throw new IllegalArgumentException("Giá sản phẩm không được âm");
            }
            total += price;
        }
        return Math.round(total * 100.0) / 100.0;
    }

    // Tính thuế (Tax 8% theo SauceDemo)
    public double calculateTax(double subtotal) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal không hợp lệ");
        }
        return Math.round(subtotal * 0.08 * 100.0) / 100.0;
    }

    // Tính tổng thanh toán cuối cùng (Total = Subtotal + Tax)
    public double calculateTotal(double subtotal, double tax) {
        return Math.round((subtotal + tax) * 100.0) / 100.0;
    }

    // Kiểm tra tính hợp lệ của thông tin nhận hàng
    public boolean validateShippingInfo(String firstName, String lastName, String postalCode) {
        if (firstName == null || firstName.trim().isEmpty()) return false;
        if (lastName == null || lastName.trim().isEmpty()) return false;
        if (postalCode == null || postalCode.trim().isEmpty()) return false;
        return true;
    }
}