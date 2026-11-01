# Xác minh gói nâng cấp

- Mã chính và mã test đã biên dịch với Java 17.
- Maven clean verify -Punit: **36 PASS**, 0 failure, error, skipped.
- Maven Wrapper 3.3.2 đã được chạy kiểm tra với Maven 3.9.9: BUILD SUCCESS.
- JaCoCo CheckoutService: **17/17 dòng, 22/22 nhánh**; đạt ngưỡng 80%/70%.
- 24 UI test: biên dịch thành công; chưa xác nhận thực thi trên máy Windows.
- UI smoke trong môi trường build: Chrome bị chặn socket() trước khi tạo session. Log lỗi giữ riêng và không được tính là PASS.
- Baseline 11 PASS là kết quả trước nâng cấp; chỉ dùng làm bằng chứng lịch sử.

## Hợp đồng module được thay đổi có chủ đích

Tính tiền nội bộ dùng BigDecimal và HALF_UP; mọi đầu vào tiền âm hoặc không hữu hạn bị từ chối, kể cả calculateTotal. Chữ ký API double được giữ để tương thích với test cũ. Thông tin nhận hàng chỉ kiểm tra bắt buộc, không tự giới hạn định dạng mã bưu chính.

## Chạy tiếp

Chạy RUN-UNIT.cmd rồi RUN-ALL.cmd trên Windows. Lưu báo cáo mới từ target trước khi chạy clean lần tiếp theo. Báo cáo Word nằm trong docs/ và phản ánh đúng mức xác minh hiện tại.
