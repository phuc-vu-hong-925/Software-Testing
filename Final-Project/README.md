# Kiểm thử tự động SauceDemo phiên bản nâng cấp

**Sinh viên:** Vũ Hồng Phúc · **MSV:** 23010639 · **Lớp:** COUR01.LT01  
**Học phần:** Đánh giá và Kiểm định chất lượng phần mềm  
**Giảng viên:** Trịnh Thanh Bình · **Thực hiện:** Cá nhân

## Bắt đầu trên Windows

1. Giải nén toàn bộ ZIP vào thư mục mới, ví dụ `D:\software testing\Final-Project-v2`. Không chép đè bản cũ trước khi lưu bản sao.
2. Mở thư mục có `pom.xml` trong VS Code. Máy cần **JDK 17 trở lên**, internet và Google Chrome để chạy UI.
3. Nhấp đúp **RUN-UNIT.cmd**. Maven Wrapper đi kèm sẽ tải Maven 3.9.9 lần đầu; không cần cấu hình lệnh `mvn` trong PATH.
4. Khi unit thành công, nhấp đúp **RUN-ALL.cmd** để chạy cả unit và UI. Chrome sẽ mở trong quá trình chạy. Đừng thao tác trên các cửa sổ Chrome đang được test điều khiển.
5. Chờ `BUILD SUCCESS` hoặc xem lỗi trong `target/surefire-reports`. Sau lần chạy all, gửi thư mục reports mới để cập nhật báo cáo kết quả trên Windows.

Java 17 là phù hợp; không cần đổi Java chỉ vì VS Code gợi ý nâng cấp.

## Lệnh trong terminal PowerShell

Chuyển vào thư mục chứa `pom.xml`, rồi chạy:

```powershell
# 36 unit test, tạo coverage và kiểm tra ngưỡng
.\mvnw.cmd -Punit clean verify

# 24 UI test, mở trình duyệt; không chạy gate coverage của service
.\mvnw.cmd -Pui "-Dheadless=false" clean verify

# Toàn bộ 60 test, mặc định headless
.\mvnw.cmd clean verify

# Toàn bộ test và mở Chrome
.\mvnw.cmd "-Dheadless=false" clean verify
```

Linux/macOS dùng `./mvnw` thay cho `.\mvnw.cmd`. Nếu file mất quyền chạy: `chmod +x mvnw`.
Nếu mạng không cho Maven Wrapper tải Maven, vẫn có thể dùng Maven đã cài: `mvn -Punit clean verify`.

## Kết quả kiểm tra đã thực hiện

| Phạm vi | Trạng thái của bản nâng cấp |
|---|---|
| Biên dịch mã ứng dụng và cả hai lớp test | Thành công với Java 17 |
| 36 unit test | 36 PASS, 0 failures, 0 errors, 0 skipped |
| Coverage CheckoutService | 17/17 dòng và 22/22 nhánh, 100% theo JaCoCo |
| 24 UI test mới | Đã biên dịch; chưa xác nhận chạy hoàn chỉnh trên Windows |
| Thử khởi động UI trong môi trường build | Bị chặn bởi quyền socket khi Chrome khởi tạo; không phải kết quả PASS |

**Kết quả 11/11 PASS của phiên bản cũ nằm riêng tại `evidence/baseline-v1`; không được dùng làm kết quả của 60 test mới.**
Các số coverage chỉ áp dụng cho module Java `CheckoutService`, không phải toàn bộ website SauceDemo.

## Các thay đổi chính

- 36 unit test: giữ 6 test ban đầu và bổ sung 30 test, gồm null, rỗng, biên làm tròn, dữ liệu âm/NaN/vô hạn và các nhánh thông tin giao hàng.
- 24 UI test độc lập: login, giỏ hàng, xóa hàng, dữ liệu checkout thiếu, hủy, sắp xếp, chi tiết sản phẩm, logout và đối chiếu tiền.
- Thêm `CartPage` và `BasePage`; dùng explicit wait, không trộn implicit wait.
- Badge giỏ rỗng trả về `0`; test kiểm tra cả tên hàng và giá.
- Checkpoint chụp overview trước Finish. Chụp kết quả từng test trước khi đóng trình duyệt; luôn chụp khi test lỗi nếu Chrome còn hoạt động.
- Tính tiền bằng BigDecimal nội bộ và HALF_UP; đầu vào tiền phải hữu hạn và không âm.
- Selenium 4.52.0, Surefire 3.5.2, JaCoCo 0.8.16; Java 17 và JUnit 5.10.0.
- Maven Wrapper, nút chạy Windows và cấu hình CI mẫu.

## Đọc kết quả sau khi chạy

- `target/surefire-reports/`: XML/TXT của lần chạy mới.
- `target/site/jacoco/index.html`: mở bằng Chrome để đọc coverage sau `verify` với unit hoặc all.
- `target/screenshots/`: ảnh PASS/FAIL và checkpoint overview.
- `target/test-environment.properties`: Java, hệ điều hành, Chrome và thời điểm của lần chạy UI.
- `evidence/verification-unit/`: bằng chứng unit đã chạy khi chuẩn bị gói này.
- `evidence/ui-startup-blocked/`: chẩn đoán lần thử UI bị giới hạn môi trường.
- `docs/`: test plan, yêu cầu, danh sách ca kiểm thử, ma trận truy xuất và báo cáo.

`clean` xóa thư mục target, vì vậy sao lưu reports và screenshots trước lần chạy mới nếu cần giữ lịch sử. `-Pui` chỉ chạy UI nên không tạo JaCoCo cho service.

## Cấu hình tùy chọn

```powershell
.\mvnw.cmd "-Dwait.seconds=20" clean verify
.\mvnw.cmd "-Dscreenshots=fail" clean verify
.\mvnw.cmd "-Dchrome.binary=C:\path\chrome.exe" "-Dwebdriver.chrome.driver=C:\path\chromedriver.exe" -Pui test
```

Mặc định Selenium Manager tìm/tải driver tương thích nếu chưa cấu hình. Lần đầu cần internet. Không có retry tự động hoặc skip âm thầm khi Chrome không khởi tạo được.

## Giới hạn và hợp đồng nghiệp vụ

CheckoutService là **module mô phỏng do sinh viên sở hữu**, không được coi là backend của website. UI test không dùng chính module này làm oracle để tránh lỗi chung giữa bộ tính kỳ vọng và ứng dụng.

Kỳ vọng demo: Backpack 29.99 USD, Bike Light 9.99 USD, thuế 8%; hai sản phẩm có subtotal 39.98, tax 3.20, total 43.18 USD. Nếu website thay dữ liệu/chính sách, phải xác minh rồi cập nhật test, không sửa kỳ vọng chỉ để test PASS.

Thông tin giao hàng của module chỉ kiểm tra bắt buộc, không tự đặt quy tắc độ dài hay định dạng mã bưu chính. Không thêm test API, database, thanh toán ngân hàng hoặc tải lớn vào website công cộng này.

## CI tùy chọn

Nếu chép dự án vào thư mục con `Final-Project` của repository hiện có, hãy đưa `.github/workflows/tests.yml` lên `.github/workflows/tests.yml` ở **gốc repository**. Chỉ giải nén dự án không tạo hay kích hoạt workflow trên GitHub.

Sau khi RUN-ALL.cmd xong, chạy EXPORT-RESULTS.cmd để tạo `SauceDemo-results.zip` chứa báo cáo và ảnh của lần chạy trên máy bạn.
