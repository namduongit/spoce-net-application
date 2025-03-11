package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import DAL.SQLHelper.MySQLHelper;
import DTO.Foods;

public class FoodDAL {
    public ArrayList<Foods> getFoodList() {
        ArrayList<Foods> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        ResultSet resultSet = helper.selectAllFromTable("Foods");

        try {
            while (resultSet.next()) {
                list.add(new Foods(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
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

        helper.buidlingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(typeCategory);

        ResultSet resultSet = helper.queryWithParam(values);

        try {
            while (resultSet.next()) {
                list.add(new Foods(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
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

        helper.buidlingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(status);

        ResultSet resultSet = helper.queryWithParam(values);

        try {
            while (resultSet.next()) {
                list.add(new Foods(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
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

    public ArrayList<Foods> searchFoodByName(String keyword) {
        ArrayList<Foods> list = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "foods");
        params.put("WHERE", "name LIKE ?");

        helper.buidlingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add("%" + keyword + "%");

        ResultSet resultSet = helper.queryWithParam(values);
        try {
            while (resultSet.next()) {
                list.add(new Foods(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
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
        params.put("TABLE", "foods JOIN categories cate ON foods.category_id = cate.category_id");
        params.put("WHERE", "cate.name = ? AND foods.status = ? AND foods.name LIKE ?");

        helper.buidlingQueryParam(params);
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
                    resultSet.getBigDecimal(3),
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

    public static void main(String[] args) {
        ArrayList<Foods> list = new FoodDAL().getFoodByCategory("Đồ uống");
        for (Foods fd : list) System.out.println(fd);
    }
}
