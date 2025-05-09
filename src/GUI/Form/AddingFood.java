package GUI.Form;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import BLL.CategoryBLL;
import BLL.FoodBLL;
import DTO.Categories;
import DTO.Foods;
import Utils.Helper.CreateComponent;
import GUI.Components.*;

@SuppressWarnings("rawtypes")
public class AddingFood extends JFrame {
    private CustomTextField nameField;
    private CustomTextField priceField;
    private CustomTextField quantityField;
    private CustomCombobox categoryCombo;
    private JLabel imageLabel;
    private JButton chooseImageBtn;
    private JButton saveBtn, resetBtn;

    private String selectedImagePath = null;

    public AddingFood() {
        setTitle("Thêm sản phẩm mới");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initForm();
    }

    private void initForm() {
        imageLabel = new JLabel();
        imageLabel.setBorder(new LineBorder(Color.BLACK, 1));
        imageLabel.setBounds(25, 25, 190, 190);
        add(imageLabel);

        chooseImageBtn = CreateComponent.createBlueButton("Chọn hình ảnh");
        chooseImageBtn.setBounds(240, 185, 220, 30);
        chooseImageBtn.addActionListener(e -> chooseImage());
        add(chooseImageBtn);

        JLabel nameLabel = new JLabel("Tên món ăn:");
        nameLabel.setBounds(25, 225, 425, 25);
        add(nameLabel);
        nameField = new CustomTextField();
        nameField.setBounds(25, 250, 425, 30);
        add(nameField);

        JLabel priceLabel = new JLabel("Giá:");
        priceLabel.setBounds(25, 280, 425, 25);
        add(priceLabel);
        priceField = new CustomTextField();
        priceField.setBounds(25, 305, 425, 30);
        add(priceField);

        JLabel quantityLabel = new JLabel("Số lượng:");
        quantityLabel.setBounds(25, 335, 425, 25);
        add(quantityLabel);
        quantityField = new CustomTextField("0");
        quantityField.setBounds(25, 360, 425, 30);
        quantityField.setEditable(false);
        add(quantityField);

        JLabel categoryLabel = new JLabel("Loại:");
        categoryLabel.setBounds(25, 390, 425, 25);
        add(categoryLabel);
        ArrayList<Categories> categoryList = new CategoryBLL().getAllCategories();
        ArrayList<String> categoryNames = new ArrayList<>();
        for (Categories cate : categoryList) {
            categoryNames.add(cate.getName());
        }
        categoryCombo = new CustomCombobox<>(categoryNames);
        categoryCombo.setBounds(25, 415, 425, 30);
        add(categoryCombo);

        saveBtn = CreateComponent.createBlueButton("Lưu");
        saveBtn.setBounds(25, 520, 100, 30);
        saveBtn.addActionListener(e -> saveFood());
        add(saveBtn);

        resetBtn = CreateComponent.createOrangeButton("Đặt lại");
        resetBtn.setBounds(135, 520, 100, 30);
        resetBtn.addActionListener(e -> resetForm());
        add(resetBtn);
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();

            ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
        }
    }



    private void resetForm() {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("0");
        categoryCombo.setSelectedIndex(0);
        imageLabel.setIcon(null);
        selectedImagePath = null;
    }

    private void saveFood() {
        // Lấy dữ liệu từ các trường
        String name = nameField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim();
        int categoryIndex = categoryCombo.getSelectedIndex();
    
        // Kiểm tra xem người dùng có nhập đủ thông tin không
        if (name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || selectedImagePath == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin và chọn ảnh!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        // Chuyển đổi giá và số lượng sang kiểu số
        int price, quantity;
        try {
            price = Integer.parseInt(priceText);
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá và số lượng phải là số!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Kiểm tra số âm
        if (quantity < 0) {
            JOptionPane.showMessageDialog(this, "Giá phải lớn hơn hoặc bằng 0!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Lấy danh sách các loại món ăn để lấy categoryId
        ArrayList<Categories> categoryList = new CategoryBLL().getAllCategories();
        int categoryId = categoryList.get(categoryIndex).getCategoryId();
    
        // Tạo tên file ảnh với timestamp để lưu tạm (sẽ rename sau)
        String tempImageName = "temp_" + System.currentTimeMillis() + selectedImagePath.substring(selectedImagePath.lastIndexOf('.'));
    
        // Tạo đối tượng FoodBLL để thêm món ăn mới
        FoodBLL foodBLL = new FoodBLL();
        boolean isCreated = foodBLL.createNewFood(name, price, quantity, categoryId, tempImageName);
    
        // Nếu món ăn được tạo thành công
        if (isCreated) {
            // Lấy món ăn vừa tạo từ danh sách
            ArrayList<Foods> foodList = foodBLL.getAllFoods();
            Foods lastFood = foodList.get(foodList.size() - 1);
    
            try {
                // Lưu ảnh vào thư mục Assets/Food với tên file mới
                Path sourcePath = Paths.get(selectedImagePath);
                Path targetPath = Paths.get(System.getProperty("user.dir") + "/src/Assets/Food/" +
                        lastFood.getFoodId() + tempImageName.substring(tempImageName.lastIndexOf(".")));
    
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    
                // Cập nhật lại đường dẫn ảnh trong DB
                HashMap<String, Object> update = new HashMap<>();
                update.put("image", lastFood.getFoodId() + tempImageName.substring(tempImageName.lastIndexOf(".")));
                foodBLL.updateFoodDetailsById(lastFood.getFoodId(), update);
    
                // Thông báo thành công và reset form
                JOptionPane.showMessageDialog(this, "Thêm món ăn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } catch (IOException e) {
                // Xử lý lỗi khi lưu ảnh
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu ảnh: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Nếu không thể tạo món ăn
            JOptionPane.showMessageDialog(this, "Không thể tạo món ăn mới!", "Thất bại", JOptionPane.ERROR_MESSAGE);
        }
    }
    

}
