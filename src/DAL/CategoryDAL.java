package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DAL.SQLHelper.MySQLHelper;
import DTO.Categories;


public class CategoryDAL {
    public ArrayList<Categories> getAllCategoryList() {
        ArrayList<Categories> list = new ArrayList<>();

        MySQLHelper helper = new MySQLHelper();
        ResultSet resultSet = helper.selectAllFromTable("Categories");

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
}
