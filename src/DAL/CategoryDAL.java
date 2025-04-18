package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import DAL.SQLHelper.MySQLHelper;
import DTO.Categories;


public class CategoryDAL {
    public ArrayList<Categories> getAllCategoryList() {
        ArrayList<Categories> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        ResultSet resultSet = helper.selectAllFromTable("categories");

        try {
            while (resultSet.next()) {
                list.add(new Categories(
                    resultSet.getInt(1),
                    resultSet.getString(2)
                ));
            }
            resultSet.close();
            helper.closeConnect();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public String getNameCategoryById(String id) {
        String result = "";

        MySQLHelper helper = new MySQLHelper();
        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "categories");
        params.put("WHERE", "category_id = ?");

        helper.buildingQueryParam(params);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        ResultSet resultSet = helper.queryWithParam(values);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    result = resultSet.getString("name");
                    break;
                }
                helper.closeConnect();
                resultSet.close();
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

        return result;
    }

    public boolean createNewCategory(String name) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "categories");
        params.put("FIELD", "name");
        
        helper.buildingQueryParam(params);
            
        ArrayList<Object> values = new ArrayList<>();
        values.add(name);

        return helper.insertData(values);
    }

}
