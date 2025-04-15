package GUI.Form;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import BLL.AccountBLL;
import BLL.PlayerBLL;
import DTO.Accounts;
import DTO.Players;
import GUI.Components.CustomButton;
import GUI.Components.CustomTextField;
import GUI.Components.CustomCombobox;

public class EditingPlayerForm extends JFrame {
    private AccountBLL accountBLL;
    private PlayerBLL playerBLL;

    private JLabel titleForm;
    private JLabel usernameLabel;
    private JLabel statusLabel;
    private JLabel balanceLabel;

    private CustomTextField usernameField;
    private CustomCombobox<String> statusComboBox;
    private CustomTextField balanceField;

    private CustomButton confirmButton;
    private CustomButton cancelButton;

    private Accounts account;
    private Players player;

    public EditingPlayerForm(String selectString) {
        this.accountBLL = new AccountBLL();
        this.playerBLL = new PlayerBLL();
    
        String[] parts = selectString.split("\\|");
        if (parts.length < 2) {
            JOptionPane.showMessageDialog(null, "Lỗi định dạng chuỗi chọn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String leftPart = parts[0].trim();
        String[] leftTokens = leftPart.split(":");
        int accountId = Integer.parseInt(leftTokens[1].trim());
    
        String username = parts[1].trim();
    
        this.account = this.accountBLL.getAccountByUsername(username);
        this.player = this.playerBLL.getPlayerByAccountId(accountId);
    
        this.initComponents();
    }
    

    private void initComponents() {
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Chỉnh sửa người chơi");

        this.titleForm = new JLabel("Chỉnh sửa người chơi");
        this.titleForm.setFont(new Font("Sans", Font.BOLD, 18));
        this.titleForm.setBounds(100, 10, 250, 30);

        this.usernameLabel = new JLabel("Tên đăng nhập:");
        this.usernameLabel.setBounds(25, 60, 335, 30);

        this.usernameField = new CustomTextField();
        this.usernameField.setBounds(25, 90, 335, 40);
        this.usernameField.setText(account.getUsername());
        this.usernameField.setEditable(false);

        this.statusLabel = new JLabel("Trạng thái:");
        this.statusLabel.setBounds(25, 140, 335, 30);

        this.statusComboBox = new CustomCombobox<>(new String[]{"Online", "Offline", "Locked"});
        this.statusComboBox.setBounds(25, 170, 335, 40);
        this.statusComboBox.setSelectedItem(account.getStatus());

        this.balanceLabel = new JLabel("Số dư:");
        this.balanceLabel.setBounds(25, 220, 335, 30);

        this.balanceField = new CustomTextField(this.player.getBalance() +"");
        this.balanceField.setBounds(25, 250, 335, 40);
        this.balanceField.setText(String.valueOf(player.getBalance()));

        this.confirmButton = Utils.Helper.CreateComponent.createGreenButton("Xác nhận");
        this.confirmButton.setBounds(25, 310, 100, 30);
        this.confirmButton.addActionListener(e -> confirmEdit());

        this.cancelButton = Utils.Helper.CreateComponent.createOrangeButton("Hủy");
        this.cancelButton.setBounds(140, 310, 100, 30);
        this.cancelButton.addActionListener(e -> this.dispose());

        this.add(this.titleForm);
        this.add(this.usernameLabel);
        this.add(this.usernameField);
        this.add(this.statusLabel);
        this.add(this.statusComboBox);
        this.add(this.balanceLabel);
        this.add(this.balanceField);
        this.add(this.confirmButton);
        this.add(this.cancelButton);
    }

    private void confirmEdit() {
        String newStatus = (String) statusComboBox.getSelectedItem();
        String balanceText = balanceField.getText().trim();

        if (!Utils.Helper.Comon.isTrueNumber(balanceText)) {
            JOptionPane.showMessageDialog(this, "Số dư không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int newBalance = Integer.parseInt(balanceText);
        boolean isStatusChanged = !account.getStatus().equals(newStatus);
        boolean isBalanceChanged = player.getBalance() != newBalance;

        if (account.getStatus().equals("Online") && (isStatusChanged || isBalanceChanged)) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Tài khoản đang hoạt động. Việc chỉnh sửa có thể ảnh hưởng đến người chơi.\nBạn có chắc chắn muốn tiếp tục?",
                    "Cảnh báo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice != JOptionPane.YES_OPTION) return;
        }

        boolean statusUpdated = accountBLL.updateAccountStatus(account.getAccountId(), newStatus);
        boolean balanceUpdated = accountBLL.updateBalancePlayerAccount(account.getUsername(), newBalance);

        if (statusUpdated && balanceUpdated) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
