package DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                // list.add(new Accounts(resultSet.getInt("account_id"),
                //         resultSet.getString("username"),
                //         resultSet.getString("password"),
                //         resultSet.getString("role"),
                //         resultSet.getString("status"),
                //         resultSet.getTimestamp("created_at"))); // Lấy đầy đủ giờ phút giây còn getDate chỉ lấy ngày không lấy thời gian giờ phút giây
            }
            resultSet.close();
            helper.closeConnect();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public static Accounts loginAccount(String username, String password) {
        MySQLHelper helper = new MySQLHelper();
            Map<String, String> params = new HashMap<>();
            params.put("TABLE", "accounts");
            params.put("WHERE", "accounts.username = ? AND accounts.password = ?");
            helper.buidlingQueryParam(params);
            ArrayList<Object> values = new ArrayList<>();
            values.add(username);
            values.add(password);
            ResultSet resultSet = helper.queryWithParam(values);

            // if (resultSet != null) {
            //     try {
            //         return new Accounts(resultSet.getInt("account_id"),
            //                             resultSet.getString("username"),
            //                             resultSet.getString("password"),
            //                             resultSet.getString("role"),
            //                             resultSet.getString("status"),
            //                             resultSet.getTimestamp("created_at"));
            //     } catch(SQLException exception) {
            //         JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            //     }
            // }

            return null;
    }

}
