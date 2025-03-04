package model.Pojo;

public class Categories {
    private int categoryId;
    private String name;

    // Constructor không tham số
    public Categories() {
    }

    // Constructor có tham số
    public Categories(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    // Getter và Setter
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "Categories{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}