# MySQLHelper - Hướng Dẫn Sử Dụng

## 1️⃣ Giới thiệu
`MySQLHelper` là một lớp Java hỗ trợ thao tác với MySQL, bao gồm:
- Kết nối đến cơ sở dữ liệu.
- Truy vấn dữ liệu (`SELECT`).
- Chèn dữ liệu (`INSERT`).
- Cập nhật dữ liệu (`UPDATE`).
- Xóa dữ liệu (`DELETE`).
- Đóng kết nối an toàn.

## 2️⃣ Cấu hình kết nối cơ sở dữ liệu
Trước khi sử dụng, bạn cần khai báo thông tin kết nối trong `ConfigSQL.java`:
```java
package Utils.Config;

public class ConfigSQL {
    public static final String URL = "jdbc:mysql://localhost:3306/ten_database";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "password";
}
```

## 3️⃣ Khởi tạo MySQLHelper
```java
MySQLHelper dbHelper = new MySQLHelper();
```

---

## 4️⃣ Truy vấn dữ liệu (`SELECT`)
### 🔹 Lấy tất cả dữ liệu từ bảng
```java
ResultSet result = dbHelper.selectAllFromTable("foods");
while (result.next()) {
    System.out.println(result.getString("name") + " - " + result.getInt("price"));
}
```

### 🔹 Truy vấn có điều kiện
```java
Map<String, String> params = new HashMap<>();
params.put("TABLE", "foods");
params.put("WHERE", "price > ?");
dbHelper.buildingQueryParam(params);

ArrayList<Object> values = new ArrayList<>();
values.add(20000);

ResultSet result = dbHelper.queryWithParam(values);
while (result.next()) {
    System.out.println(result.getString("name") + " - " + result.getInt("price"));
}
```

---

## 5️⃣ Chèn dữ liệu (`INSERT`)
```java
Map<String, String> params = new HashMap<>();
params.put("TABLE", "foods");
params.put("FIELD", "name, price, quantity");
dbHelper.buildingQueryParam(params);

ArrayList<Object> values = new ArrayList<>();
values.add("Bánh mì");
values.add(25000);
values.add(10);

boolean success = dbHelper.insertData(values);
System.out.println(success ? "Thêm thành công!" : "Thêm thất bại!");
```

---

## 6️⃣ Cập nhật dữ liệu (`UPDATE`)
```java
Map<String, String> params = new HashMap<>();
params.put("TABLE", "foods");
params.put("WHERE", "food_id = ?");
dbHelper.buildingQueryParam(params);

Map<String, Object> updateValues = new HashMap<>();
updateValues.put("price", 30000);
updateValues.put("quantity", 20);

ArrayList<Object> conditionValues = new ArrayList<>();
conditionValues.add(1); // food_id = 1

boolean success = dbHelper.updateData(updateValues, conditionValues);
System.out.println(success ? "Cập nhật thành công!" : "Cập nhật thất bại!");
```

---

## 7️⃣ Xóa dữ liệu (`DELETE`)
```java
Map<String, String> params = new HashMap<>();
params.put("TABLE", "foods");
params.put("WHERE", "food_id = ?");
dbHelper.buildingQueryParam(params);

ArrayList<Object> values = new ArrayList<>();
values.add(1); // Xóa food_id = 1

boolean success = dbHelper.deleteData(values);
System.out.println(success ? "Xóa thành công!" : "Xóa thất bại!");
```

---

## 8️⃣ Đóng kết nối
```java
dbHelper.closeConnect();
```

