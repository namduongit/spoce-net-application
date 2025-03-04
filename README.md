# 👥 Các thành viên trong nhóm
**1** `Nguyễn Nam Dương       -     3123560012`

**2** `Chênh Tiên Luân        -     3123560048`

**3** `Nguyễn Công Quân       -     3123560070`

# 📓 Một số lưu ý khi xây dựng dự án
- **Đảm bảo bộ nhớ**    : Tối ưu hóa hệ thống bằng việc tìm hiểu các loại dữ liệu giúp đỡ tốn bộ nhớ
- **Giải phóng bộ nhớ** : Tham khảo ở lớp `AccountDAO.java`
- **Ví dụ**:
    + Dùng `ArrayList<Object>` để chứa danh sách các `Object`. ArrayList hay kiểu dữ liệu nào đều không thay đổi được địa chỉ bên trong phương thức
    + Dùng `HashMap<String, Object>` nếu cần lưu dữ liệu dưới dạng `key-value`
    + Xóa `dữ liệu` khi `không còn dùng` tới để giảm việc sử dụng bộ nhớ
    ``` java
      List<MyObject> list = new ArrayList<>();
      Map<String, Object> map = new HashMap<>();

      list = null;
      map = null;
    ```

## 📌 Giải thích các Kiểu dữ liệu được sử dụng
- **Wrapper class**     : Tuy tốn bộ nhớ nhiều hơn nhưng cho phép có giá trị là `NULL`.
- **BigDecimal**        : Sử dụng để lưu trữ số thực (số thập phân) có độ chính xác cao.

---

## 📂 Thông tin và ý nghĩa của các thư mục
- **DAO**         : Quản lý một nhóm class nào đó, thường dùng để thao tác với CSDL.
- **Pojo**        : Chứa các class định nghĩa cho một **thực thể**.
- **Utils**       : Chứ các file **Config** và các lớp **Helper** để có thể xây dựng hàm nhanh

---

## ⚙️ Các giá trị `setDefaultCloseOperation`
- `EXIT_ON_CLOSE`         : Đóng chương trình.
- `DO_NOTHING_ON_CLOSE`   : Không làm gì khi đóng.
- `HIDE_ON_CLOSE`         : Ẩn cửa sổ.
- `DISPOSE_ON_CLOSE`      : Đóng cửa sổ nhưng không thoát chương trình.

---

## 🛠️ Xây dựng chức năng **MySQLHelper**
- **JDBC for MySQL**: Sử dụng thư viện JDBC dành cho MySQL để kết nối tới cơ sở dữ liệu.
- 📥 **Tải xuống thư viện**: [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

---

## 🔐 Xây dựng chức năng **Quên mật khẩu cho Admin**
- **Java Mail**: Gửi email đặt lại mật khẩu.
  - 📥 [Tải JavaMail](https://javaee.github.io/javamail/)
- **Activation Framework**: Hỗ trợ kích hoạt email.
  - 📥 [Tải Activation Framework](https://mvnrepository.com/artifact/javax.activation/activation/1.1.1)

---

## 🎨 Các Components được thiết kế lại
### ✨ `Nút (CustomButton)`
- Nên thêm một **JPanel** trước khi add vào **JFrame** để tránh ảnh hưởng tới bố cục.
- **Tính năng tùy chỉnh**:
  - Thêm **icon**, **hình nền**, **hiệu ứng hover**.
  - Sử dụng thư viện hỗ trợ UI đẹp như **FlatLaf**, **SwingX**.
  - Cấu hình bo góc, đổ bóng để giao diện chuyên nghiệp hơn.

📌 *Chỉnh sửa và bổ sung thêm theo nhu cầu của bạn!* 🚀

## ⚔️ Implements Serializable
- Tuy không sử dụng **interface** này nhưng vẫn cần giải thích
- Đây là 1 bảng hợp đồng để cho phép có thể lưu trực tiếp đối tượng nhờ `FileOutputStream` và `ObjectOutputStream` thông qua `FileOutputStream`
- Lưu `Object` bằng cách **writeObject**
- Khi implement nó sẽ tự tạo ra **UID** để đánh dấu số thứ tự
- Thứ mà sau khi ràng buộc nó tạo ra:
      ```java
      private static final long serialVersionUID = 1L;
      ```