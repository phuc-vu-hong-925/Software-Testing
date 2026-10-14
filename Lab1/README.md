# Bài tập: Kiểm thử JUnit & Độ bao phủ mã (Code Coverage)

## 1. Giới thiệu
Chương trình chứa hàm `countEvenPositives(int[] arr)` trong lớp `NumberUtils` dùng để đếm số lượng số nguyên dương chẵn trong mảng:
- Có ít nhất 1 vòng lặp (`for-each`).
- Có câu lệnh rẽ nhánh điều kiện phức (`if (x > 0 && x % 2 == 0)`).

## 2. Chiến lược kiểm thử

### Issue #1: Statement Coverage (Bao phủ câu lệnh)
- File test: `StatementTest.java`
- Mục tiêu: Thực thi tất cả các dòng lệnh ít nhất một lần.
- Các ca kiểm thử:
  - `arr == null`: Bao phủ dòng lệnh rẽ nhánh thoát sớm `return -1;`.
  - `arr = {2}`: Bao phủ việc khởi tạo, duyệt vòng lặp, thực thi lệnh `count++` và `return count;`.

### Issue #2: Path Coverage (Bao phủ đường đi)
- File test: `PathTest.java`
- Mục tiêu: Kiểm tra mọi nhánh rẽ và tổ hợp điều kiện logic (`&&`):
  - Đường đi 1: `arr == null` -> Kết thúc ngay.
  - Đường đi 2: Mảng rỗng `{}` -> Bỏ qua thân vòng lặp.
  - Đường đi 3: `x <= 0` (ví dụ: `-3`) -> Điều kiện đầu sai, ngắn mạch nhánh `&&`.
  - Đường đi 4: `x > 0` và `x % 2 != 0` (ví dụ: `5`) -> Điều kiện đầu đúng, điều kiện sau sai.
  - Đường đi 5: `x > 0` và `x % 2 == 0` (ví dụ: `4`) -> Cả 2 điều kiện đúng, tăng biến đếm.