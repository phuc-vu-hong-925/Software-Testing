# BÁO CÁO BÀI TẬP LỚN (FINAL PROJECT): KIỂM THỬ HỆ THỐNG THƯƠNG MẠI ĐIỆN TỬ SAUCEDEMO

- **Học phần:** Kiểm thử phần mềm (Software Testing)
- **Sinh viên thực hiện:** Vũ Hồng Phúc
- **Đối tượng kiểm thử (SUT):** Website thương mại điện tử SauceDemo (`https://www.saucedemo.com/`)
- **Công nghệ & Công cụ:** Java 17, Selenium WebDriver 4, JUnit 5, Maven, mô hình Page Object Model (POM)

---

## 1. Giới thiệu và Mục tiêu Dự án
Dự án xây dựng bộ khung kiểm thử tự động (Automation & Unit Testing Framework) cho hệ thống thương mại điện tử **SauceDemo**. Mục tiêu chính bao gồm:
- **Kiểm thử đơn vị (Unit Testing):** Kiểm tra tính đúng đắn của các hàm xử lý logic tính toán giỏ hàng, thuế (8%) và xác thực thông tin giao hàng (`CheckoutService`).
- **Kiểm thử giao diện tự động (UI Automation Testing):** Tự động hóa các luồng nghiệp vụ cốt lõi từ Đăng nhập (Login), Quản lý giỏ hàng (Inventory/Cart) đến Thanh toán đơn hàng hoàn chỉnh (End-to-End Checkout).
- **Áp dụng kiến trúc Page Object Model (POM):** Tách biệt mã nguồn định vị phần tử giao diện (Locators/Pages) và kịch bản kiểm thử (Test Scripts), giúp mã nguồn dễ đọc, dễ bảo trì và tái sử dụng.

---

## 2. Cấu trúc Thư mục Dự án

```text
Final-Project/
├── src/
│   ├── main/java/com/saucedemo/
│   │   ├── pages/                  # Các lớp Page Object Model (POM)
│   │   │   ├── LoginPage.java      # Định vị & thao tác trang Đăng nhập
│   │   │   ├── InventoryPage.java  # Định vị & thao tác trang Sản phẩm & Giỏ hàng
│   │   │   └── CheckoutPage.java   # Định vị & thao tác trang Thanh toán
│   │   └── service/                # Lớp xử lý logic nghiệp vụ
│   │       └── CheckoutService.java
│   └── test/java/com/saucedemo/
│       ├── automation/             # Kịch bản kiểm thử tự động với Selenium
│       │   └── SauceDemoTest.java
│       └── unit/                   # Kịch bản kiểm thử đơn vị với JUnit 5
│           └── CheckoutServiceTest.java
├── pom.xml                         # Quản lý thư viện Maven (Selenium, JUnit 5)
└── README.md                       # Tài liệu báo cáo dự án
```

---

## 3. Danh sách Ca kiểm thử (Test Cases)

### 3.1. Kiểm thử Đơn vị (Unit Testing - `CheckoutServiceTest.java`)

| Mã TC | Tên ca kiểm thử | Đầu vào (Input) | Kết quả mong đợi (Expected Output) | Trạng thái |
| :---: | :--- | :--- | :--- | :---: |
| **UT01** | Tính đúng tổng tiền nhiều sản phẩm | `{29.99, 9.99, 15.99}` | `55.97` | **PASS** |
| **UT02** | Tính tổng tiền khi giỏ hàng rỗng | `{}` | `0.0` | **PASS** |
| **UT03** | Kiểm tra ngoại lệ khi giá sản phẩm âm | `{29.99, -5.0}` | Ném ngoại lệ `IllegalArgumentException` | **PASS** |
| **UT04** | Tính thuế GTGT (8%) chính xác | `Subtotal = 55.97` | `Tax = 4.48` | **PASS** |
| **UT05** | Xác thực thông tin giao hàng hợp lệ | `("Phuc", "Vu", "100000")` | `true` | **PASS** |
| **UT06** | Báo lỗi khi thiếu thông tin giao hàng | `("", "Vu", "100000")` | `false` | **PASS** |

---

### 3.2. Kiểm thử Tự động Giao diện (Selenium WebDriver - `SauceDemoTest.java`)

| Mã TC | Tên kịch bản kiểm thử | Các bước thực hiện | Kết quả mong đợi | Trạng thái |
| :---: | :--- | :--- | :--- | :---: |
| **TC01** | Đăng nhập thất bại khi sai mật khẩu | Nhập `standard_user` / `wrong_password` $\rightarrow$ Click Login | Hiển thị thông báo lỗi: *"Username and password do not match..."* | **PASS** |
| **TC02** | Đăng nhập thất bại với tài khoản bị khóa | Nhập `locked_out_user` / `secret_sauce` $\rightarrow$ Click Login | Hiển thị thông báo lỗi: *"Sorry, this user has been locked out."* | **PASS** |
| **TC03** | Đăng nhập thành công với tài khoản chuẩn | Nhập `standard_user` / `secret_sauce` $\rightarrow$ Click Login | Chuyển hướng vào trang chủ, tiêu đề hiển thị `"Products"` | **PASS** |
| **TC04** | Thêm sản phẩm vào giỏ hàng thành công | Đăng nhập $\rightarrow$ Click `"Add to cart"` sản phẩm Backpack | Biểu tượng giỏ hàng cập nhật số lượng là `"1"` | **PASS** |
| **TC05** | Hoàn tất luồng đặt hàng End-to-End | Đăng nhập $\rightarrow$ Thêm hàng $\rightarrow$ Vào giỏ $\rightarrow$ Checkout $\rightarrow$ Điền `"Phuc", "Vu", "100000"` $\rightarrow$ Finish | Hiển thị thông báo hoàn tất: `"Thank you for your order!"` | **PASS** |

---

## 4. Hướng dẫn Cài đặt và Thực thi Kiểm thử

1. **Yêu cầu môi trường:**
   - Java Development Kit (JDK) phiên bản 17 trở lên.
   - Apache Maven 3.8+.
   - Trình duyệt Google Chrome.

2. **Lệnh chạy toàn bộ bộ kiểm thử:**
   ```bash
   cd Final-Project
   mvn clean test
   ```

3. **Tổng kết kết quả thực thi:**
   - **Tổng số bài kiểm thử:** 11 tests (6 Unit Tests + 5 Automation UI Tests).
   - **Kết quả:** 11/11 Passed (100% Pass rate, 0 Failures, 0 Errors).