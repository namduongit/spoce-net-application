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
    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // //
    public ArrayList<Accounts> getAccountList() {
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
                        resultSet.getTimestamp(6)));
            }
            resultSet.close();
            helper.closeConnect();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public Accounts getAccountById(int accountId) {
        Accounts accounts = null;
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("WHERE", "account_id = ?");

        ArrayList<Object> values = new ArrayList<>();
        values.add(accountId);
        helper.buildingQueryParam(params);

        ResultSet resultSet = helper.queryWithParam(values);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    accounts = new Accounts(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getTimestamp(6));
                    resultSet.close();
                    helper.closeConnect();
                    return accounts;
                }
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        return accounts;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // //

    public boolean updateAccountDetailsById(int accountId, HashMap<String, Object> updateValues) {
        if (updateValues == null || updateValues.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("WHERE", "account_id = ?");

        helper.buildingQueryParam(params);

        ArrayList<Object> conditionValues = new ArrayList<>();
        conditionValues.add(accountId);

        return helper.updateData(updateValues, conditionValues);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public Accounts staffLoginAccount(String username, String password) {
        MySQLHelper helper = new MySQLHelper();
        Map<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("WHERE", "accounts.username = ? AND accounts.password = ? AND (accounts.role = ? OR accounts.role = ?)");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(username);
        values.add(password);
        values.add("Quản trị viên");
        values.add("Nhân viên");

        ResultSet resultSet = helper.queryWithParam(values);
        Accounts account = null;

        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    account = new Accounts(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getTimestamp(6));
                }
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                helper.closeConnect();
            }
        }

        return account;
    }

}
