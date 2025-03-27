package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import DTO.Staffs;
import DAL.SQLHelper.MySQLHelper;

public class StaffDAL {
    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //
    public ArrayList<Staffs> getStaffList() {
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

    public Staffs getStaffById(int staffId) {
        Staffs staff = null;
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "staffs");
        params.put("WHERE", "staff_id = ?");

        ArrayList<Object> values = new ArrayList<>();
        values.add(staffId);
        helper.buildingQueryParam(params);

        ResultSet resultSet = helper.queryWithParam(values);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    staff = new Staffs(
                        resultSet.getInt("staff_id"),
                        resultSet.getInt("account_id"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("birth_date"),
                        resultSet.getString("gender"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getString("cccd"),
                        resultSet.getString("avatar")
                    );
                    resultSet.close();
                    helper.closeConnect();
                }
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        return staff;
    }

    public Staffs getStaffByEmail(String staffEmail) {
        Staffs staff = null;
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "staffs");
        params.put("WHERE", "email = ?");

        ArrayList<Object> values = new ArrayList<>();
        values.add(staffEmail);
        helper.buildingQueryParam(params);

        ResultSet resultSet = helper.queryWithParam(values);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    staff = new Staffs(
                        resultSet.getInt("staff_id"),
                        resultSet.getInt("account_id"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("birth_date"),
                        resultSet.getString("gender"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getString("cccd"),
                        resultSet.getString("avatar")
                    );
                    resultSet.close();
                    helper.closeConnect();
                }
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        return staff;
    }
    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //
    public boolean updateAddressStaffById(int staffId, String addressValue) {
        if (addressValue == null || addressValue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "staffs");
        params.put("SET", "address = ?");
        params.put("WHERE", "staff_id = ?");

        helper.buildingQueryParam(params);
        ArrayList<Object> valueCondition = new ArrayList<>();
        valueCondition.add(addressValue);
        valueCondition.add(staffId);

        return helper.updateData(valueCondition);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

}
