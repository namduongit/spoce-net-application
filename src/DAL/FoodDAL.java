package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


    public void updateStatusFood() {
        ArrayList<Foods> list = this.getFoodList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getQuantity() == 0) {
                list.get(i).setStatus("Hết hàng");
            }
        }
        MySQLHelper helper = new MySQLHelper();
        
    }
}
