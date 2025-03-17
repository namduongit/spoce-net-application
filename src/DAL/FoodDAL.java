package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import DAL.SQLHelper.MySQLHelper;
import DTO.Foods;

public class FoodDAL {
    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //
    public ArrayList<Foods> getFoodList() {
        ArrayList<Foods> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        ResultSet resultSet = helper.selectAllFromTable("foods");

        try {
            while (resultSet.next()) {
                list.add(new Foods(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getTimestamp(8)
                ));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public ArrayList<Foods> getFoodByCategory(String typeCategory) {
        ArrayList<Foods> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "foods");
        params.put("JOIN", "categories cate on foods.category_id = cate.category_id");
        params.put("WHERE", "cate.name = ?");

        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(typeCategory);

        ResultSet resultSet = helper.queryWithParam(values);

        try {
            while (resultSet.next()) {
                list.add(new Foods(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getTimestamp(8)
                ));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public ArrayList<Foods> getFoodByStatus(String status) {
        ArrayList<Foods> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "foods");
        params.put("WHERE", "foods.status = ?");

        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(status);

        ResultSet resultSet = helper.queryWithParam(values);

        try {
            while (resultSet.next()) {
                list.add(new Foods(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getTimestamp(8)
                ));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    public Foods getFoodByID(int id) {
        ArrayList<Foods> list = this.getFoodList();
        for (Foods fd : list) {
            if (fd.getFoodId() == id) {
                return fd;
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public ArrayList<Foods> searchFoodByName(String keyword) {
        ArrayList<Foods> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "foods");
        params.put("WHERE", "name LIKE ?");

        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add("%" + keyword + "%");

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                list.add(new Foods(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getTimestamp(8)
                ));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public ArrayList<Foods> advancedSearch(String category, String status, String keyword) {
        ArrayList<Foods> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "foods");
        params.put("JOIN", "categories cate ON foods.category_id = cate.category_id");
        params.put("WHERE", "cate.name = ? AND foods.status = ? AND foods.name LIKE ?");

        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(category);
        values.add(status);
        values.add("%" + keyword + "%");

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                list.add(new Foods(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getTimestamp(8)
                ));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public boolean updateFoodDetailsById(int foodId, HashMap<String, Object> updateValues) {
        if (updateValues == null || updateValues.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "foods");
        params.put("WHERE", "food_id = ?");

        helper.buildingQueryParam(params);

        ArrayList<Object> conditionValues = new ArrayList<>();
        conditionValues.add(foodId);

        return helper.updateData(updateValues, conditionValues);
    }
}

