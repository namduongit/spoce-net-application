package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import DTO.Accounts;
import DAL.SQLHelper.MySQLHelper;

public class AccountDAL {
    public static ArrayList<Accounts> getAccountList() {
        ArrayList<Accounts> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        ResultSet resultSet = helper.selectAllFromTable("accounts");
        try {
            while (resultSet.next()) {
                list.add(new Accounts(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getTimestamp(6)
                ));
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
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(username);
        values.add(password);

        ResultSet resultSet = helper.queryWithParam(values);
        Accounts account = null;

        if (resultSet != null) {
            try {
                if (resultSet.next()) {  // Quan trọng: cần gọi .next() trước khi lấy dữ liệu
                    account = new Accounts(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getTimestamp(6)
                    );
                }
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                helper.closeConnect();  // Đảm bảo đóng kết nối khi xong
            }
        }

        return account;
    }




}
