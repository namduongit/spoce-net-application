package model.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Pojo.Staffs;
import model.SQLHelper.MySQLHelper;

public class StaffDAO {
    public static ArrayList<Staffs> getStaffList() {
        ArrayList<Staffs> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        ResultSet resultSet = helper.selectAllFromTable("staffs");
        try {
            while (resultSet.next()) {
                list.add(new Staffs(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10)
                ));
            }
            resultSet.close();
            helper.closeConnect();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public static Staffs getStaffsByAccountID(int accountID) {
        MySQLHelper helper = new MySQLHelper();
        ResultSet resultSet = helper.selectAllFromTable("staffs");
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    int staffAccountID = resultSet.getInt(2);
                    if (staffAccountID == accountID) {
                        return new Staffs(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getDate(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9),
                            resultSet.getString(10)
                        );
                    }
                }
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

        helper.closeConnect();
        // Có thể nhân viên chưa cập nhật thông tin nên vẫn trả về 1 object mới để tránh bị null exception
        return new Staffs();
    }

    public static void main(String[] args) {
        Staffs staffs = getStaffsByAccountID(1);
        System.out.println(staffs);
    }
}
