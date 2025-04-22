package GUI.panels;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import DTO.Accounts;
import DTO.Staffs;
import GUI.Components.CustomPanel;

public class BillPanel extends JPanel{
    private Staffs loginStaff;
    private Accounts loginAccount;

    private CustomPanel headerPanel;
    private CustomPanel filterPanel;
    private CustomPanel dataPanel;

    public BillPanel() {

        this.initComponents();
    }

    private CustomPanel createHeaderPanel() {
        CustomPanel panel = new CustomPanel();

        return panel;
    }

    private void initComponents() {
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        
    }
}
