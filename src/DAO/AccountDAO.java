package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import Pojo.Accounts;
import SQLHelper.MySQLHelper;

public class AccountDAO {
    public static ArrayList<Accounts> getAccountList() {
        ArrayList<Accounts> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        ResultSet resultSet = helper.selectAllFromTable("accounts");
        try {
            while (resultSet.next()) {
                list.add(new Accounts(resultSet.getInt("account_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at")));         // Lấy đầy đủ giờ phút giây còn getDate chỉ lấy ngày không lấy thời gian giờ phút giây
            }
            resultSet.close();
            helper.closeConnect();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public static void main(String[] args) {
        Timestamp timestamp = Timestamp.valueOf("2025-02-14 00:00:00");
        Date date = new Date(timestamp.getTime());
        System.out.println(timestamp);
        System.out.println(date);

        ArrayList<Accounts> list = getAccountList();
        for (Accounts accounts : list) System.out.println(accounts);
    }

}
