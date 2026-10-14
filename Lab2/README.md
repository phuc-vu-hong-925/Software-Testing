# BÁO CÁO BÀI TẬP LAB 2: THIẾT KẾ CA KIỂM THỬ

---

## BÀI 1: KIỂM THỬ CHỨC NĂNG ĐĂNG NHẬP

### 1. Phân tích yêu cầu
- **Tên đăng nhập:** Độ dài hợp lệ từ 6 đến 20 ký tự.
- **Mật khẩu:** Độ dài hợp lệ từ 6 đến 25 ký tự.
- **Quy tắc khóa:** Nhập sai quá 5 lần sẽ khóa tài khoản trong 15 phút.

### 2. Danh sách ca kiểm thử (Test Cases)

| Mã TC | Tên ca kiểm thử | Tên đăng nhập | Mật khẩu | Kết quả mong đợi |
| :--- | :--- | :--- | :--- | :--- |
| **TC01** | Đăng nhập thành công | Đúng (8 ký tự) | Đúng (10 ký tự) | Đăng nhập thành công, chuyển hướng vào hệ thống. |
| **TC02** | Bỏ trống cả hai trường | *[Để trống]* | *[Để trống]* | Báo lỗi yêu cầu nhập đầy đủ thông tin. |
| **TC03** | Tên đăng nhập < 6 ký tự | `user1` (5 ký tự) | Hợp lệ | Báo lỗi: Tên đăng nhập phải từ 6 đến 20 ký tự. |
| **TC04** | Tên đăng nhập biên dưới (6 ký tự) | `user01` (6 ký tự) | Hợp lệ | Đăng nhập thành công. |
| **TC05** | Tên đăng nhập biên trên (20 ký tự) | 20 ký tự hợp lệ | Hợp lệ | Đăng nhập thành công. |
| **TC06** | Tên đăng nhập > 20 ký tự | 21 ký tự | Hợp lệ | Báo lỗi độ dài tên đăng nhập không hợp lệ. |
| **TC07** | Mật khẩu < 6 ký tự | Hợp lệ | `12345` (5 ký tự) | Báo lỗi: Mật khẩu phải từ 6 đến 25 ký tự. |
| **TC08** | Mật khẩu biên dưới (6 ký tự) | Hợp lệ | 6 ký tự đúng | Đăng nhập thành công. |
| **TC09** | Mật khẩu biên trên (25 ký tự) | Hợp lệ | 25 ký tự đúng | Đăng nhập thành công. |
| **TC10** | Mật khẩu > 25 ký tự | Hợp lệ | 26 ký tự | Báo lỗi độ dài mật khẩu không hợp lệ. |
| **TC11** | Nhập sai mật khẩu (1 - 5 lần) | Đúng | Sai | Báo lỗi thông tin đăng nhập, hiển thị số lần còn lại. |
| **TC12** | Nhập sai mật khẩu lần 6 | Đúng | Sai (lần thứ 6) | Khóa tài khoản, báo: *"Tài khoản đã bị khóa do đăng nhập thất bại quá 5 lần. Bạn vui lòng đăng nhập lại sau 15 phút"*. |
| **TC13** | Đăng nhập khi đang bị khóa (< 15p) | Đúng | Đúng | Không cho phép đăng nhập, tiếp tục báo tài khoản đang bị khóa. |
| **TC14** | Đăng nhập lại sau khi hết thời gian khóa (> 15p) | Đúng | Đúng | Tự động mở khóa, đăng nhập thành công. |

---

## BÀI 2: HỆ THỐNG BÁN VÉ XE ĐIỆN

### 1. Bảng phân vùng tương đương và giá trị biên

| Phân vùng | Loại vé | Khoảng thời gian | Giá trị biên kiểm thử (BVA) |
| :--- | :--- | :--- | :--- |
| **Vùng 1** | **Vé thường** (Trước 9:30) | `00:00` - `09:29` | `00:00`, `09:29` |
| **Vùng 2** | **Vé tiết kiệm** (9:30 - 16:00) | `09:30` - `16:00` | `09:30`, `16:00` |
| **Vùng 3** | **Vé thường** (16:01 - 19:30) | `16:01` - `19:30` | `16:01`, `19:30` |
| **Vùng 4** | **Vé tiết kiệm** (19:31 - 24:00) | `19:31` - `24:00` | `19:31`, `24:00` (hoặc `23:59`) |

### 2. Danh sách ca kiểm thử chi tiết

| Mã TC | Mục đích kiểm thử | Giờ chuyến xe (Input) | Kết quả mong đợi (Output) |
| :--- | :--- | :--- | :--- |
| **TC_TIME_01** | Giá trị biên bắt đầu ngày | `00:00` | Mua Vé thường |
| **TC_TIME_02** | Đại diện vùng Vé thường sáng | `06:00` | Mua Vé thường |
| **TC_TIME_03** | Giá trị biên kết thúc Vé thường sáng | `09:29` | Mua Vé thường |
| **TC_TIME_04** | Giá trị biên bắt đầu Vé tiết kiệm | `09:30` | Mua Vé tiết kiệm |
| **TC_TIME_05** | Đại diện vùng Vé tiết kiệm trưa | `12:00` | Mua Vé tiết kiệm |
| **TC_TIME_06** | Giá trị biên kết thúc Vé tiết kiệm chiều | `16:00` | Mua Vé tiết kiệm |
| **TC_TIME_07** | Giá trị biên bắt đầu cao điểm chiều | `16:01` | Mua Vé thường |
| **TC_TIME_08** | Đại diện vùng cao điểm chiều | `18:00` | Mua Vé thường |
| **TC_TIME_09** | Giá trị biên kết thúc cao điểm chiều | `19:30` | Mua Vé thường |
| **TC_TIME_10** | Giá trị biên bắt đầu Vé tiết kiệm tối | `19:31` | Mua Vé tiết kiệm |
| **TC_TIME_11** | Đại diện vùng Vé tiết kiệm tối | `21:00` | Mua Vé tiết kiệm |
| **TC_TIME_12** | Giá trị biên kết thúc ngày | `24:00` | Mua Vé tiết kiệm |
| **TC_TIME_13** | Ngoại lệ: Giờ không hợp lệ | `25:00` | Báo lỗi giờ không hợp lệ |