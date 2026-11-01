# Kế hoạch kiểm thử

## Mục tiêu và phạm vi
Kiểm thử chức năng hộp đen website SauceDemo và kiểm thử hộp trắng CheckoutService. Trong phạm vi: yêu cầu WEB01-WEB10 và MOD01-MOD05. Ngoài phạm vi: backend website, database, API chưa được cung cấp, thanh toán thật, bảo mật chuyên sâu và tải lớn.

## Quy trình
1. Chạy unit profile để phát hiện nhanh lỗi module và xác minh gate coverage.
2. Chạy UI profile hoặc all trên máy có Chrome và truy cập được SauceDemo.
3. Kiểm tra XML/TXT, ảnh và môi trường. Phân loại lỗi script, môi trường hoặc website.
4. Chỉ sửa kỳ vọng khi yêu cầu nghiệp vụ thực sự thay đổi; không bật skip/retry để che lỗi.
5. Lưu bằng chứng gắn commit/lần chạy rồi cập nhật test report.

## Kỹ thuật
- Phân lớp tương đương: login hợp lệ/sai/khóa/thiếu; thông tin nhận hàng hợp lệ/thiếu.
- Biên: tiền 0/âm, mảng null/rỗng/một/nhiều phần tử, giá trị hai phía của ngưỡng làm tròn, dữ liệu không hữu hạn.
- Bảng quyết định: từng trường firstName/lastName/postalCode hợp lệ hoặc thiếu.
- Chuyển trạng thái màn hình: Login → Inventory → Cart → Information → Overview → Complete; các nhánh cancel và logout.
- Hộp trắng: coverage của service; không tính coverage website ngoài quyền sở hữu.

## Điều kiện
Bắt đầu: JDK17+, Maven hoặc Wrapper, Chrome cho UI, kết nối internet, website và dữ liệu demo khả dụng.
Kết thúc: các test cam kết đều đã có trạng thái, các lỗi nghiêm trọng được xử lý hoặc có giải trình, gate service LINE >=80% và BRANCH >=70% đạt, bằng chứng mới đã lưu.
Không kết luận kết thúc chỉ từ số lượng test hoặc coverage100%.

## Rủi ro
Website công cộng và Chrome có thể thay đổi; driver lần đầu cần tải; mạng/giới hạn môi trường có thể ngăn khởi tạo browser. Mỗi test tạo profile Chrome mới, dùng explicit wait và quit sau thực thi. Ảnh không thay thế assertion. Không ghi thời gian test thành response time của website.
