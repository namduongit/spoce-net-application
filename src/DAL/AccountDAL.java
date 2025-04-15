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

    public Accounts getAccountByUsername(String username) {
        Accounts accounts = null;
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("WHERE", "username = ?");

        ArrayList<Object> values = new ArrayList<>();
        values.add(username);
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

    public ArrayList<Accounts> getPlayerAccountList() {
        ArrayList<Accounts> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("WHERE", "role = ?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add("Người chơi");

        ResultSet resultSet = helper.queryWithParam(values);
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


    public ArrayList<Object[]> getInfoPlayerAccountList() {
        ArrayList<Object[]> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("JOIN", "players player on accounts.account_id = player.account_id");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();


        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                Object[] objects = new Object[]{
                    resultSet.getInt("account_id"),
                    resultSet.getString("username"),
                    resultSet.getInt("balance"),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("created_at")
                };
                list.add(objects);
            }
            resultSet.close();
            helper.closeConnect();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public ArrayList<Accounts> getStaffAccountList() {
        ArrayList<Accounts> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("WHERE", "role = ? or role =?");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add("Nhân viên");
        values.add("Quản trị viên");

        ResultSet resultSet = helper.queryWithParam(values);
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

    public ArrayList<Object[]> getInfoStaffAccountList() {
        ArrayList<Object[]> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("JOIN", "staffs staff on accounts.account_id = staff.account_id");
        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();


        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                Object[] objects = new Object[]{
                    resultSet.getInt("account_id"),
                    resultSet.getString("username"),
                    resultSet.getString("full_name"),
                    resultSet.getString("role"),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("created_at")
                };
                list.add(objects);
            }
            resultSet.close();
            helper.closeConnect();

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public ArrayList<Object[]> filterPlayerAccountList(String searchText, String status) {
        ArrayList<Object[]> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();
    
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("JOIN", "players player ON accounts.account_id = player.account_id");
    
        ArrayList<String> whereConditions = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
    
        if (searchText != null && !searchText.isEmpty()) {
            whereConditions.add("username LIKE ?");
            values.add("%" + searchText + "%");
        }
    
        if (status != null && !status.equalsIgnoreCase("Tất cả")) {
            whereConditions.add("status = ?");
            values.add(status);
        }
    
        whereConditions.add("role = ?");
        values.add("Người chơi");
    
        if (!whereConditions.isEmpty()) {
            String whereClause = String.join(" AND ", whereConditions);
            params.put("WHERE", whereClause);
        }
    
        helper.buildingQueryParam(params);
    
        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                Object[] objects = new Object[]{
                    resultSet.getInt("account_id"),
                    resultSet.getString("username"),
                    resultSet.getInt("balance"),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("created_at")
                };
                list.add(objects);
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    
        return list;
    }
    


    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

    public boolean updatePasswordAccountById(int accountId, String currentPassword, String newPassword) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("WHERE", "account_id = ? AND password = ?");
        helper.buildingQueryParam(params);

        HashMap<String, Object> updateValues = new HashMap<>();
        updateValues.put("password", Utils.Helper.EncriptString.MD5String(newPassword));

        ArrayList<Object> conditionValues = new ArrayList<>();
        conditionValues.add(accountId);
        conditionValues.add(Utils.Helper.EncriptString.MD5String(currentPassword));

        return helper.updateData(updateValues, conditionValues);
    }

    public boolean updateAccountStatus(int accountId, String status) {
        MySQLHelper helper = new MySQLHelper();
    
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("WHERE", "account_id = ?");
        helper.buildingQueryParam(params);
    
        HashMap<String, Object> updateValues = new HashMap<>();
        updateValues.put("status", status);
    
        ArrayList<Object> conditionValues = new ArrayList<>();
        conditionValues.add(accountId);
    
        return helper.updateData(updateValues, conditionValues);
    }
    


    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public boolean createPlayerAccount(String username, String password) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("FIELD", "username, password, role");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(username);
        values.add(Utils.Helper.EncriptString.MD5String(password));
        values.add("Người chơi");

        return helper.insertData(values);
    }

    public boolean plusBalancePlayerAccount(String username, int amountToAdd) {
        MySQLHelper helper = new MySQLHelper();
    
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("JOIN", "players player on accounts.account_id = player.account_id");
        params.put("SET", "balance = balance + ?");
        params.put("WHERE", "username = ?");
        helper.buildingQueryParam(params);
    
        ArrayList<Object> values = new ArrayList<>();
        values.add(amountToAdd); 
        values.add(username);
    
        return helper.updateData(values);
    }

    public boolean updateBalancePlayerAccount(String username, int newBalance) {
        MySQLHelper helper = new MySQLHelper();
    
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("JOIN", "players player ON accounts.account_id = player.account_id");
        params.put("SET", "balance = ?");
        params.put("WHERE", "username = ?");
        helper.buildingQueryParam(params);
    
        ArrayList<Object> values = new ArrayList<>();
        values.add(newBalance); 
        values.add(username);
    
        return helper.updateData(values);
    }
    

    public boolean minBalancePlayerAccount(String username, int amountToAdd) {
        MySQLHelper helper = new MySQLHelper();
    
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("JOIN", "players player on accounts.account_id = player.account_id");
        params.put("SET", "balance = balance - ?");
        params.put("WHERE", "username = ?");
        helper.buildingQueryParam(params);
    
        ArrayList<Object> values = new ArrayList<>();
        values.add(amountToAdd); 
        values.add(username);
    
        return helper.updateData(values);
    }

    public boolean createStaffAccount(String username, String password, String typeStaff) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("FIELD", "username, password, role");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(username);
        values.add(Utils.Helper.EncriptString.MD5String(password));
        values.add(typeStaff);

        return helper.insertData(values);
    }
    
    // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public boolean deleteAccountByUsername(String username) {
        MySQLHelper helper = new MySQLHelper();
    
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "accounts");
        params.put("WHERE", "username = ?");
        helper.buildingQueryParam(params);
    
        ArrayList<Object> values = new ArrayList<>();
        values.add(username);
    
        return helper.deleteData(values);
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
