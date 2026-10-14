# BÁO CÁO BÀI TẬP LAB 4: KIỂM THỬ PERFORMANCE & RESPONSIVE BẰNG GOOGLE LIGHTHOUSE

* **Học phần:** Kiểm thử phần mềm (Software Testing)
* **Sinh viên thực hiện:** Vũ Hồng Phúc
* **Công cụ sử dụng:** Google Lighthouse tích hợp sẵn trên DevTools trình duyệt Google Chrome

---

## 1. Mục tiêu kiểm thử
* **Performance (Hiệu năng):** Đánh giá thời gian tải trang, khả năng phản hồi tương tác và độ tối ưu hóa tài nguyên qua các chỉ số Core Web Vitals (FCP, LCP, TBT, CLS).
* **Responsive (Khả năng tương thích thiết bị):** So sánh hiệu năng, bố cục và tính ổn định thị giác khi trang web hiển thị trên hai môi trường thiết bị: **Desktop (Máy tính để bàn)** và **Mobile (Thiết bị di động)**.

---

## 2. Môi trường và đối tượng kiểm thử
* **Môi trường đo:** Google Chrome ở chế độ ẩn danh (Incognito Mode) để loại bỏ tác động từ tiện ích mở rộng (extensions).
* **Danh sách website thực nghiệm:**
  1. Website tin tức báo chí: `https://vnexpress.net/`
  2. Website sàn thương mại điện tử: `https://shopee.vn/`

---

## 3. Bảng tổng hợp số liệu đo lường thực tế

| Website | Môi trường | Điểm Performance | FCP | LCP | TBT | CLS |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **VnExpress** | **Desktop** | **25 / 100** | 0,8 giây | 5,0 giây | 820 ms | 0,308 |
| | **Mobile** | **40 / 100** | 1,1 giây | 9,8 giây | 1.470 ms | 0,038 |
| **Shopee** | **Desktop** | **39 / 100** | 1,0 giây | 1,7 giây | 10.120 ms | 0,260 |
| | **Mobile** | **30 / 100** | 4,4 giây | 6,3 giây | 16.490 ms | 0,096 |
| **Ngưỡng chuẩn (Google)** | — | $\ge 90$ *(Tốt)* | $\le 1,8$s | $\le 2,5$s | $\le 200$ms | $\le 0,1$ |

---

## 4. Phân tích kết quả chi tiết

### a. Về Performance (Hiệu năng thực thi)
* **VnExpress:**
  * **FCP (0,8s - 1,1s):** Thời gian phản hồi HTML và khung sườn ban đầu từ máy chủ rất nhanh ở cả 2 thiết bị.
  * **LCP (5,0s Desktop; 9,8s Mobile):** Điểm LCP ở mức báo động đỏ do ảnh tiêu điểm bài viết chính và các banner quảng cáo có kích thước lớn, chưa được nén tối ưu cho mạng di động.
  * **TBT (820 ms Desktop; 1.470 ms Mobile):** Bị nghẽn luồng xử lý do lượng lớn mã JavaScript của bên thứ ba (analytics, tracking và thư viện quảng cáo động).

* **Shopee:**
  * **LCP Desktop (1,7s):** Nội dung khung chính trên màn hình rộng tải hoàn tất rất nhanh (đạt chuẩn xanh).
  * **TBT cực kỳ cao (10.120 ms Desktop; 16.490 ms Mobile):** Vì là nền tảng Single Page Application (SPA) thương mại điện tử phức tạp, Shopee phải tải khối lượng JavaScript khổng lồ (quản lý state, gợi ý sản phẩm, popup khuyến mãi). Trên thiết bị di động bị giả lập nghẽn CPU, luồng chính bị đơ tới hơn 16 giây.
  * **FCP Mobile (4,4s):** Quá trình tải gói bundle mã nguồn di động bị chậm.

### b. Về Responsive (Khả năng co giãn và chuyển đổi giao diện)
* **Bố cục giao diện:**
  * Cả hai website đều tự động nhận diện thiết bị linh hoạt: chuyển đổi dạng lưới nhiều cột (Desktop) sang dạng cuộn 1 cột dọc (Mobile).
  * Shopee trên Mobile có thêm modal popup quảng cáo tự co giãn che kín màn hình di động.
* **Độ ổn định thị giác (CLS):**
  * **Trên Desktop:** Cả 2 trang đều có CLS vượt ngưỡng chuẩn (VnExpress: 0,308; Shopee: 0,260). Nguyên nhân do các khối banner quảng cáo tải chậm, đẩy dồn các khối nội dung bên dưới khi nạp xong.
  * **Trên Mobile:** Cả 2 trang lại đạt chuẩn rất tốt (VnExpress: 0,038; Shopee: 0,096) nhờ thiết lập trước chiều dọc cố định cho các ô hiển thị.

---

## 5. Đề xuất giải pháp khắc phục (Recommendations)
1. **Tối ưu hóa tài nguyên JavaScript:** 
   * Áp dụng Code Splitting và Lazy loading cho các component chưa cần thiết.
   * Dùng thuộc tính `defer` hoặc `async` để hoãn thực thi các script tracking/quảng cáo của bên thứ 3 nhằm hạ thấp chỉ số TBT.
2. **Tối ưu hóa hình ảnh (LCP):**
   * Chuyển đổi định dạng ảnh sang WebP hoặc AVIF.
   * Thêm thuộc tính `fetchpriority="high"` cho ảnh tiêu điểm đầu trang để trình duyệt ưu tiên tải trước.
3. **Cố định layout chống giật (CLS):**
   * Đặt thuộc tính `min-height` hoặc `aspect-ratio` cố định cho các khung chứa banner quảng cáo trên bản Desktop để tránh làm xô lệch bài viết khi người dùng đang đọc.