# Kiểm thử tự động website SauceDemo

**Sinh viên:** Vũ Hồng Phúc  
**Mã sinh viên:** 23010639  
**Lớp:** COUR01.LT01  
**Học phần:** Đánh giá và Kiểm định chất lượng phần mềm  
**Giảng viên:** Trịnh Thanh Bình  
**Hình thức thực hiện:** Cá nhân

## 1. Giới thiệu

Dự án xây dựng bộ kiểm thử tự động cho các luồng mua sắm trên [SauceDemo](https://www.saucedemo.com/) và module Java mô phỏng nghiệp vụ thanh toán. Nội dung gồm đặc tả yêu cầu, lập kế hoạch, thiết kế ca kiểm thử, tự động hóa và phân tích kết quả.

Website được kiểm thử theo hướng hộp đen qua trình duyệt. `CheckoutService` là module mô phỏng độc lập dùng cho kiểm thử đơn vị; không phải mã backend của SauceDemo.

## 2. Phạm vi kiểm thử

- Đăng nhập hợp lệ, mật khẩu sai, tài khoản khóa và dữ liệu thiếu.
- Danh sách, chi tiết và sắp xếp sản phẩm.
- Thêm, xóa, đọc nội dung và số lượng giỏ hàng.
- Thông tin giao hàng bắt buộc, các nhánh hủy và tiếp tục mua sắm.
- Kiểm tra tên sản phẩm, subtotal, tax, total và hoàn tất đặt hàng.
- Logic tính tiền, làm tròn và xác thực dữ liệu của CheckoutService.

## 3. Công nghệ

| Thành phần | Công nghệ |
|---|---|
| Ngôn ngữ | Java 17 |
| Tự động hóa trình duyệt | Selenium WebDriver 4.52.0 |
| Kiểm thử | JUnit 5.10.0 |
| Quản lý build | Maven 3.9.9 qua Maven Wrapper |
| Thực thi test | Maven Surefire 3.5.2 |
| Độ bao phủ | JaCoCo 0.8.16 |
| Kiến trúc test | Page Object Model |

## 4. Cấu trúc dự án

```text
Final-Project/
├── src/main/java/com/saucedemo/
│   ├── pages/                 # Page Object và thao tác giao diện
│   └── service/               # Module mô phỏng thanh toán
├── src/test/java/com/saucedemo/
│   ├── unit/                 # 36 unit test
│   └── automation/           # 24 UI test và thiết lập trình duyệt
├── docs/                     # Báo cáo, yêu cầu, test plan và ma trận truy xuất
├── evidence/unit-tests/      # Báo cáo thực thi unit và JaCoCo
├── .mvn/wrapper/             # Maven Wrapper
├── pom.xml
└── README.md
```

## 5. Môi trường và thực thi

Yêu cầu JDK 17 trở lên, Google Chrome và kết nối internet. Chạy lệnh tại thư mục chứa `pom.xml`.

```powershell
# Chạy kiểm thử đơn vị và tạo báo cáo coverage
.\mvnw.cmd -Punit clean verify

# Chạy kiểm thử giao diện với Chrome hiển thị
.\mvnw.cmd -Pui "-Dheadless=false" clean verify

# Chạy toàn bộ bộ kiểm thử
.\mvnw.cmd clean verify
```

Trên Linux/macOS dùng `./mvnw`. Các tệp `RUN-UNIT.cmd`, `RUN-UI.cmd` và `RUN-ALL.cmd` cung cấp cách chạy tương ứng trên Windows. Selenium Manager quản lý driver trình duyệt khi không chỉ định đường dẫn riêng.

## 6. Kết quả thực thi

| Nhóm | Số ca thiết kế | Kết quả có bằng chứng |
|---|---:|---|
| Kiểm thử đơn vị | 36 | 36 PASS, 0 failures, 0 errors, 0 skipped |
| Kiểm thử giao diện | 24 | Chưa có kết quả thực thi đầy đủ |
| Tổng | 60 | 36 ca đạt; 24 ca chưa có kết quả |

Coverage của `CheckoutService`: **17/17 dòng lệnh và 22/22 nhánh, tương ứng 100%**. Ngưỡng kiểm tra: LINE tối thiểu 80%, BRANCH tối thiểu 70%. Số liệu coverage chỉ áp dụng cho module Java này.

Bằng chứng thực thi unit nằm trong `evidence/unit-tests/`. Kết quả mỗi lần chạy được Maven ghi tại `target/surefire-reports/`; báo cáo coverage tại `target/site/jacoco/index.html`; ảnh tại `target/screenshots/`. Lệnh `clean` xóa `target`, vì vậy các kết quả cần lưu trữ được sao chép riêng theo từng lần thực thi.

## 7. Tài liệu

- [Báo cáo bài tập lớn](docs/Bao_cao_kiem_thu_SauceDemo.docx)
- [Đặc tả yêu cầu](docs/requirements.md)
- [Kế hoạch kiểm thử](docs/test-plan.md)
- [Danh sách ca kiểm thử](docs/test-cases.csv)
- [Ma trận truy xuất yêu cầu](docs/traceability-matrix.md)
- [Báo cáo kết quả](docs/verification-summary.md)

## 8. Quy tắc nghiệp vụ

Module sử dụng BigDecimal nội bộ và làm tròn HALF_UP đến hai chữ số thập phân. Tiền âm, NaN hoặc vô hạn bị từ chối. Thông tin nhận hàng được kiểm tra bắt buộc; không tự áp đặt quy tắc định dạng mã bưu chính.

Kỳ vọng dữ liệu demo: Backpack 29.99 USD, Bike Light 9.99 USD; với thuế 8%, hai sản phẩm có subtotal 39.98 USD, tax 3.20 USD và total 43.18 USD. Assertion UI dùng kỳ vọng độc lập với CheckoutService.
