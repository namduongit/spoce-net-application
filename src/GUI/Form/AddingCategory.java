package GUI.Form;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import BLL.CategoryBLL;
import GUI.Components.CustomButton;
import GUI.Components.CustomTextField;
import Utils.Helper.CreateComponent;

@SuppressWarnings({"FieldMayBeFinal"})
public class AddingCategory extends JFrame {
    private JPanel content;
    private CustomTextField categoryTextField;

    private CategoryBLL categoryBLL;

    public AddingCategory() {
        this.categoryBLL = new CategoryBLL();

        this.setTitle("Thêm loại thức ăn");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(330,200);
        this.setResizable(false);
        this.setLayout(null);

        this.content = this.createContent();
        this.content.setBounds(0, 0, 330, 200);

        this.add(this.content);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createContent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel categoryLabel = new JLabel("Tên loại thức ăn:");
        categoryLabel.setBounds(20, 10, 100, 20);

        categoryTextField = new CustomTextField("Nhập tên loại thức ăn");
        categoryTextField.setBounds(20, 35, 270, 30);
        categoryTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (categoryTextField.getText().equals("Nhập tên loại thức ăn")) {
                    categoryTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (categoryTextField.getText().isEmpty()) {
                    categoryTextField.setText("Nhập tên loại thức ăn");
                }
            }
        });

        CustomButton submitButton = CreateComponent.createBlueButton("Lưu lại");
        submitButton.setBorderSize(2);
        submitButton.setBounds(20, 110, 100, 30);
        submitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddingCategory.this.submitCategory();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                submitButton.setBackground(Color.WHITE);
                submitButton.setForeground(Color.decode("#1E88E5"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                submitButton.setBackground(Color.decode("#1E88E5"));
                submitButton.setForeground(Color.WHITE);
            }
        });

        CustomButton resetButton = CreateComponent.createOrangeButton("Đặt lại");
        resetButton.setBounds(130, 110, 100, 30);
        resetButton.setBorderSize(2);
        resetButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddingCategory.this.categoryTextField.setText("Nhập tên loại thức ăn");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                resetButton.setBackground(Color.WHITE);
                resetButton.setForeground(Color.decode("#DD2C00"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetButton.setBackground(Color.decode("#DD2C00"));
                resetButton.setForeground(Color.WHITE);
            }
        });

        panel.add(categoryLabel);
        panel.add(categoryTextField);
        panel.add(submitButton);
        panel.add(resetButton);

        return panel;
    }

    private void submitCategory() {
        String category = this.categoryTextField.getText().trim();

        if (category.isEmpty() || category.equals("Nhập tên loại thức ăn")) {
            JOptionPane.showMessageDialog(
                null,
                "Vui lòng nhập tên loại thức ăn!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        boolean result = this.categoryBLL.createNewCategory(category);
        if (result) {
            JOptionPane.showMessageDialog(
                null,
                "Thêm loại thức ăn thành công!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                null,
                "Thêm loại thức ăn thất bại!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
