# Báo cáo kết quả kiểm thử

## 1. Phạm vi và môi trường

Bộ kiểm thử gồm 36 phương thức unit test và 24 phương thức UI test. Kiểm thử đơn vị được thực thi bằng Java 17 trên Linux, Maven 3.9.9, JUnit 5.10.0 và Surefire 3.5.2.

## 2. Kết quả

| Nhóm | Số ca thiết kế | Đã có kết quả | PASS | Failures | Errors | Skipped |
|---|---:|---:|---:|---:|---:|---:|
| Unit | 36 | 36 | 36 | 0 | 0 | 0 |
| UI | 24 | Chưa có | - | - | - | - |

Các ca giao diện đã biên dịch; tập bằng chứng chưa ghi nhận kết quả thực thi đầy đủ của nhóm này.

## 3. Độ bao phủ module

CheckoutService đạt 17/17 dòng lệnh và 22/22 nhánh, tương ứng 100%. JaCoCo check đạt ngưỡng LINE 80% và BRANCH 70%. Độ bao phủ không áp dụng cho mã nguồn website SauceDemo.

## 4. Bằng chứng và đánh giá

XML/TXT Surefire, log Maven và báo cáo JaCoCo nằm trong `evidence/unit-tests/`. Kết quả unit không ghi nhận failure hoặc error. Chưa xác lập defect chức năng website từ tập bằng chứng hiện có. Kết luận chất lượng được giới hạn ở phạm vi và dữ liệu đã thực thi.
