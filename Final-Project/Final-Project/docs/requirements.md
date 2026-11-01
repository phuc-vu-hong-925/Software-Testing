# Đặc tả phục vụ kiểm thử

Đây là cơ sở thiết kế test của bài tập, không phải SRS chính thức của Sauce Labs.

| Mã | Yêu cầu |
|---|---|
| WEB01 | Đăng nhập hợp lệ; từ chối mật khẩu sai, tài khoản khóa và dữ liệu bắt buộc thiếu. |
| WEB02 | Thêm một hoặc hai sản phẩm, hiển thị đúng tên, giá, số lượng và badge. |
| WEB03 | Xóa hàng tại inventory hoặc cart; giỏ rỗng đọc số lượng 0. |
| WEB04 | Continue Shopping và Cancel giữ trạng thái giỏ phù hợp. |
| WEB05 | Bỏ trống từng trường checkout phải có thông báo tương ứng. |
| WEB06 | Overview thể hiện đúng danh sách hàng, subtotal, tax và total. |
| WEB07 | Finish hiển thị thông báo hoàn tất; Back Home về Products và giỏ trống. |
| WEB08 | Bốn lựa chọn sắp xếp tên/giá cho thứ tự đúng. |
| WEB09 | Trang chi tiết hiển thị tên và giá đúng; có thể quay lại. |
| WEB10 | Logout về trang đăng nhập. |
| MOD01 | Subtotal null/rỗng bằng 0; cộng theo thập phân, làm tròn cuối HALF_UP 2 chữ số. |
| MOD02 | Thuế cố định 8%, HALF_UP 2 chữ số. |
| MOD03 | Total = subtotal + tax, HALF_UP 2 chữ số. |
| MOD04 | Mọi đầu vào tiền âm/NaN/vô hạn phải bị từ chối bằng IllegalArgumentException. |
| MOD05 | Từng trường nhận hàng null/rỗng/chỉ khoảng trắng trả false; đầy đủ trả true. |

Các quy tắc MOD áp dụng cho module Java mô phỏng. Website được kiểm tra riêng qua giao diện. Không giả định website có các quy tắc dữ liệu giống toàn bộ module.
