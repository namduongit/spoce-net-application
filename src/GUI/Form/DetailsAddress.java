package GUI.Form;

import java.awt.Font;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import BLL.StaffBLL;
import DTO.Staffs;
import GUI.Components.CustomButton;
import GUI.Components.CustomCombobox;
import GUI.Components.CustomTextField;
import Utils.Helper.AddressComboBox;

public class DetailsAddress extends JFrame {
    private Staffs editStaff;

    private Set<String> provinceSet;
    private Map<String, Set<String>> cityMap;
    private Map<String, Set<String>> wardMap;

    private CustomTextField houseInput;

    private CustomCombobox<String> provinceComboBox;
    private CustomCombobox<String> cityComboBox;
    private CustomCombobox<String> wardComboBox;

    private CustomButton changedButton;
    private CustomButton disposeButton;
    private CustomButton closeButton;

    public DetailsAddress(Staffs editStaff) {
        this.editStaff = editStaff;
        AddressComboBox addressComboBox = new AddressComboBox();
        this.provinceSet = addressComboBox.getProvinceSet();
        this.cityMap = addressComboBox.getCityMap();
        this.wardMap = addressComboBox.getWardMap();

        this.initComponents();
    }

    private void initComponents() {
        this.setSize(400, 500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Thay đổi địa chỉ");
        this.setIconImage(new ImageIcon(System.getProperty("user.dir") +"/src/Assets/Icon/icons8-address-100.png").getImage());
        

        this.provinceComboBox = new CustomCombobox<>();
        this.cityComboBox = new CustomCombobox<>();
        this.wardComboBox = new CustomCombobox<>();
        this.houseInput = new CustomTextField();


        for (String province : this.provinceSet) {
            this.provinceComboBox.addItem(province);
        }

        provinceComboBox.addActionListener(e -> updateCityList());
        cityComboBox.addActionListener(e -> updateWardList());

        updateCityList();
        updateWardList();

        this.loadDataFromCurrentStaff();

        JLabel labelTitle = new JLabel("Thay đổi địa chỉ");
        labelTitle.setFont(new Font("Sans", Font.BOLD, 18));
        labelTitle.setBounds(130, 10, 140, 50);

        JLabel labelProvince = new JLabel("Chọn tỉnh thành:");
        labelProvince.setBounds(25, 50, 300, 35);
        JLabel labelCity = new JLabel("Chọn thành phố:");
        labelCity.setBounds(25, 120, 300, 35);
        JLabel labelWard = new JLabel("Chọn phường xã:");
        labelWard.setBounds(25, 190, 300, 35);
        JLabel labelHouse = new JLabel("Nhập địa chỉ nhà:");
        labelHouse.setBounds(25, 260, 300, 35);

        this.provinceComboBox.setBounds(25, 80, 330, 35);
        this.cityComboBox.setBounds(25, 150, 330, 35);
        this.wardComboBox.setBounds(25, 220, 330, 35);
        this.houseInput.setBounds(25, 290, 330, 35);

        this.changedButton = Utils.Helper.CreateComponent.createGreenButton("Xác nhận");
        this.changedButton.addActionListener(e -> savedDataIntoCurrentStaff());
        this.disposeButton = Utils.Helper.CreateComponent.createBlueButton("Đặt lại");
        this.disposeButton.addActionListener(e -> loadDataFromCurrentStaff());
        this.closeButton = Utils.Helper.CreateComponent.createOrangeButton("Đóng");
        this.closeButton.addActionListener(e -> {
            this.dispose();
        });

        this.changedButton.setBounds(25, 380, 100, 30);
        this.disposeButton.setBounds(140, 380, 100, 30);
        this.closeButton.setBounds(255, 380, 100, 30);

        this.add(labelTitle);
        this.add(labelProvince);
        this.add(labelCity);
        this.add(labelWard);
        this.add(labelHouse);
        this.add(this.provinceComboBox);
        this.add(this.cityComboBox);
        this.add(this.wardComboBox);
        this.add(this.houseInput);
        this.add(this.changedButton);
        this.add(this.disposeButton);
        this.add(this.closeButton);
    }

    private void loadDataFromCurrentStaff() {
        if (this.editStaff != null && (this.editStaff.getAddress() != null || !this.editStaff.getAddress().isEmpty())) {
            String[] regexStrings = this.editStaff.getAddress().split(", ");
            // Tỉnh thành, Thành phố, Phường, Nhà
            this.provinceComboBox.setSelectedItem(regexStrings[0]);
            this.cityComboBox.setSelectedItem(regexStrings[1]);
            this.wardComboBox.setSelectedItem(regexStrings[2]);
            String lastString = "";
            for (int i = 3; i < regexStrings.length; i++) {
                lastString += regexStrings[i] +" ";
            }
            this.houseInput.setText(lastString.trim());
        }
    }

    private void savedDataIntoCurrentStaff() {
        int resultAnswer = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn thay đổi địa chỉ?", 
            "Xác nhận thay đổi", 
            JOptionPane.YES_NO_OPTION);
    
        if (resultAnswer == JOptionPane.YES_OPTION) {
            String newAddressString = this.provinceComboBox.getSelectedItem().toString() + ", " +
                                      this.cityComboBox.getSelectedItem().toString() + ", " +
                                      this.wardComboBox.getSelectedItem().toString() + ", " +
                                      this.houseInput.getText();
    
            boolean resultUpdate = new StaffBLL().updateAddressStaffById(this.editStaff.getStaffId(), newAddressString);
    
            if (resultUpdate) {
                JOptionPane.showMessageDialog(this, 
                    "Cập nhật thành công!", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Cập nhật thất bại!", 
                    "Lỗi", 
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    

    private void updateCityList() {
        this.cityComboBox.removeAllItems();
        this.wardComboBox.removeAllItems();

        String selectedProvince = (String) this.provinceComboBox.getSelectedItem();
        if (selectedProvince != null && this.cityMap.containsKey(selectedProvince)) {
            for (String city : cityMap.get(selectedProvince)) {
                this.cityComboBox.addItem(city);
            }
        }
    }

    private void updateWardList() {
        this.wardComboBox.removeAllItems();

        String selectedCity = (String) this.cityComboBox.getSelectedItem();
        if (selectedCity != null && this.wardMap.containsKey(selectedCity)) {
            for (String ward : this.wardMap.get(selectedCity)) {
                this.wardComboBox.addItem(ward);
            }
        }
    }


    public static void main(String[] args) {
        new DetailsAddress(null).setVisible(true);
    }
}
