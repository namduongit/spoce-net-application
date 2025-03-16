package GUI.Form;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import BLL.CategoryBLL;

import DTO.Categories;
import DTO.Foods;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomTextField;

public class DetailsFood extends JFrame{
    private Foods currentFood;

    private CustomTextField foodIdTextField;
    private CustomTextField foodNameTextField;
    private CustomTextField fooodPriceTextField;
    private CustomCombobox foodCateCombobox;
    private CustomTextField foodQuantityTextField;
    private CustomCombobox foodStatusCombobox;
    private JLabel foodImage;
    private CustomTextField foodCreateAtTextField;

    private CustomButton changeImageButton;

    private CustomButton saveButton;
    private CustomButton replaceButton;

    public DetailsFood(Foods foods) {
        this.currentFood = foods;
        this.initComponents();
    }

    private void initComponents() {
        this.setTitle("Thông tin chi tiết đồ ăn");
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/Assets/Icon/icons8-change-100.png").getImage());
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setInfoCurrentFood();

    }

    private void setInfoCurrentFood() {
        this.foodImage = new JLabel();
        this.foodImage.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir")
        + "/src/Assets/Food/" + this.currentFood.getImage())
        .getImage()
        .getScaledInstance(190, 190, Image.SCALE_SMOOTH)));
        this.foodImage.setBorder(new LineBorder(Color.BLACK, 1));
        this.foodImage.setBounds(25, 25, 190, 190);

        JLabel foodIdLable = new JLabel("Mã đồ ăn:");
        foodIdLable.setBounds(240, 10, 220, 25);
        this.foodIdTextField = new CustomTextField(this.currentFood.getFoodId() +"");
        this.foodIdTextField.setEditable(false);
        this.foodIdTextField.setBounds(240, 35, 220, 30);

        JLabel foodCateLable = new JLabel("Loại đồ ăn:");
        foodCateLable.setBounds(240, 65, 220, 25);
        ArrayList<String> foodCateList = new ArrayList<>();
        foodCateList.add(new CategoryBLL().getNameCategoryById(this.currentFood.getCategoryId() +""));
        ArrayList<Categories> categoriesTemp = new CategoryBLL().getAllCategories();
        for (Categories cate : categoriesTemp) {
            if (!cate.getName().equalsIgnoreCase(new CategoryBLL().getNameCategoryById(this.currentFood.getCategoryId() +""))) {
                foodCateList.add(cate.getName());
            }
        }
        this.foodCateCombobox = new CustomCombobox<>(foodCateList);
        this.foodCateCombobox.setBounds(240, 90, 220, 30);

        JLabel foodStatusLabel = new JLabel("Trạng thái:");
        foodStatusLabel.setBounds(240, 120, 220, 25);
        ArrayList<String> foodStatusList = new ArrayList<>();
        if (this.currentFood.getStatus().equals("Còn hàng")) {
            foodStatusList.add("Còn hàng");
            foodStatusList.add("Hết hàng");
        } else {
            foodStatusList.add("Hết hàng");
            foodStatusList.add("Còn hàng");
        }
        this.foodStatusCombobox = new CustomCombobox<>(foodStatusList);
        this.foodStatusCombobox.addActionListener(e -> updateStatusFood());;
        this.foodStatusCombobox.setBounds(240, 145, 220, 30);

        this.changeImageButton = Utils.Helper.CreateComponent.createBlueButton("Đổi hình ảnh");
        this.changeImageButton.setBounds(240, 185, 220, 30);
        this.changeImageButton.addActionListener(e -> changeFoodImage());

        JLabel foodNameLabel = new JLabel("Tên đồ ăn:");
        foodNameLabel.setBounds(25, 225, 425, 25);
        this.foodNameTextField = new CustomTextField(this.currentFood.getName());
        this.foodNameTextField.setBounds(25, 250, 425, 30);

        JLabel foodPriceLabel = new JLabel("Giá cả:");
        foodPriceLabel.setBounds(25, 280, 425, 25);
        this.fooodPriceTextField = new CustomTextField(this.currentFood.getPrice().toString());
        this.fooodPriceTextField.setBounds(25, 305, 425, 30);

        JLabel foodQuantityLabel = new JLabel("Số lượng còn lại (Enter để cập nhật):");
        foodQuantityLabel.setBounds(25, 335, 425, 25);
        this.foodQuantityTextField = new CustomTextField(this.currentFood.getQuantity() +"");
        this.foodQuantityTextField.setBounds(25, 360, 425, 30);
        this.foodQuantityTextField.addActionListener(e -> {
            String textQuantity = foodQuantityTextField.getText().replaceAll("[^0-9]", "");

            if (textQuantity.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int quantity = Integer.parseInt(textQuantity);
            foodQuantityTextField.setText(String.valueOf(quantity));

            if (quantity > 0) {
                foodStatusCombobox.setSelectedItem("Còn hàng");
            } else {
                foodStatusCombobox.setSelectedItem("Hết hàng");
            }

            JOptionPane.showMessageDialog(null, "Đã cập nhật số lượng và trạng thái!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        });


        JLabel foodCreateAt = new JLabel("Ngày tạo:");
        foodCreateAt.setBounds(25, 390, 425, 25);
        this.foodCreateAtTextField = new CustomTextField(this.currentFood.getCreatedAt().toString());
        this.foodCreateAtTextField.setBounds(25, 415, 425, 30);
        this.foodCreateAtTextField.setEditable(false);

        this.saveButton = Utils.Helper.CreateComponent.createBlueButton("Lưu lại");
        this.saveButton.setBounds(25, 500, 100, 30);

        this.replaceButton = Utils.Helper.CreateComponent.createOrangeButton("Đặt lại");
        this.replaceButton.setBounds(135, 500, 100, 30);
        this.replaceButton.addActionListener(e -> resetForm());

        this.add(this.foodImage);
        this.add(foodIdLable);
        this.add(this.foodIdTextField);
        this.add(foodCateLable);
        this.add(this.foodCateCombobox);
        this.add(foodStatusLabel);
        this.add(this.foodStatusCombobox);
        this.add(this.changeImageButton);
        this.add(foodNameLabel);
        this.add(this.foodNameTextField);
        this.add(foodPriceLabel);
        this.add(this.fooodPriceTextField);
        this.add(foodQuantityLabel);
        this.add(this.foodQuantityTextField);
        this.add(foodCreateAt);
        this.add(this.foodCreateAtTextField);
        this.add(this.saveButton);
        this.add(this.replaceButton);
    }

     private void changeFoodImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh mới");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JPG, PNG Images", "jpg", "png"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon newImageIcon = new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath())
                    .getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH));
            this.foodImage.setIcon(newImageIcon);
        }
    }


    private void resetForm() {
        this.foodImage.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir")
                + "/src/Assets/Food/" + this.currentFood.getImage())
                .getImage()
                .getScaledInstance(190, 190, Image.SCALE_SMOOTH)));

        this.foodNameTextField.setText(this.currentFood.getName());
        this.fooodPriceTextField.setText(this.currentFood.getPrice().toString());
        this.foodQuantityTextField.setText(String.valueOf(this.currentFood.getQuantity()));
        this.foodCreateAtTextField.setText(this.currentFood.getCreatedAt().toString());
        this.foodCateCombobox.setSelectedItem(new CategoryBLL().getNameCategoryById(this.currentFood.getCategoryId() + ""));
        this.foodStatusCombobox.setSelectedItem(this.currentFood.getStatus());
    }

    private void updateStatusFood() {
        String selectedStatus = (String) foodStatusCombobox.getSelectedItem();

        if (selectedStatus.equals("Hết hàng")) {
            if (Integer.parseInt(foodQuantityTextField.getText()) > 0) {
                int confirm = javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "Sản phẩm vẫn còn hàng, bạn có chắc chắn muốn đặt trạng thái 'Hết hàng'?",
                    "Xác nhận",
                    javax.swing.JOptionPane.YES_NO_OPTION
                );
                if (confirm == javax.swing.JOptionPane.NO_OPTION) {
                    foodStatusCombobox.setSelectedItem("Còn hàng");
                } else {
                    this.foodQuantityTextField.setText("0");
                }
            }
        } else if (selectedStatus.equals("Còn hàng")) {
            if (Integer.parseInt(foodQuantityTextField.getText()) == 0) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Bạn cần cập nhật số lượng sản phẩm trước khi đặt trạng thái 'Còn hàng'!",
                    "Lỗi",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
                foodStatusCombobox.setSelectedItem("Hết hàng");
            }
        }
    }

}
