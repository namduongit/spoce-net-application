package DAL.SQLHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import Utils.Config.ConfigSQL;

public class MySQLHelper {
    private Connection connection;

    private Map<String, String> queryParams;

    public MySQLHelper() {
        try {
            this.connection = DriverManager.getConnection(Utils.Config.ConfigSQL.URL, ConfigSQL.USER_NAME, ConfigSQL.PASSWORD);
            this.queryParams = new HashMap<>(); // Khởi tạo queryParams ngay trong constructor
        } catch (SQLException exception) {
            String messenger = exception.getMessage();
            JOptionPane.showMessageDialog(null,"Không thể kết nối đến cơ sở dữ liệu: "+ messenger , "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* -------------------------Các phương thức hỗ trợ-------------------------------------------------------------------------------------------------------------------------------------------------- */

    private String buildingCondition() {
        if (this.queryParams != null && this.queryParams.get("WHERE") != null && !this.queryParams.get("WHERE").isEmpty()) {
            return "WHERE " + this.queryParams.get("WHERE");
        }
        return "";
    }

    private String buidlingJoinTable() {
        if (this.queryParams != null && this.queryParams.get("JOIN") != null && !this.queryParams.get("JOIN").isEmpty()) {
            return "JOIN " + this.queryParams.get("JOIN");
        }
        return "";
    }

    private String buidlingFieldInsert() {
        if (this.queryParams != null && this.queryParams.get("FIELD") != null && !this.queryParams.get("FIELD").isEmpty()) {
            return "("+ this.queryParams.get("FIELD") +")";
        }
        return "";
    }

    /* ---------------------------Thiết lập queryParams------------------------------------------------------------------------------------------------------------------------------------------------ */

    public MySQLHelper buildingQueryParam(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }

        params.putIfAbsent("SELECT", "*");          // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("TABLE", "");            // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("JOIN", "");             // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("WHERE", "");            // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("OTHER", "");            // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("FIELD", "");            // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("SET", "");

        this.queryParams = params;
        return this;
    }

    /* ------------------------Các phương thức truy vấn--------------------------------------------------------------------------------------------------------------------------------------------------- */

    public ResultSet query(String sql) {
        if (this.connection == null) {
            JOptionPane.showMessageDialog(null, "Chưa kết nối đến database!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            return statement.executeQuery();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }


    public ResultSet selectAllFromTable(String tableName) {
        try {
            String sql = "SELECT * FROM "+ tableName;
            PreparedStatement statement = this.connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public ResultSet queryWithParam(ArrayList<Object> values) {
        try {
            if (this.queryParams == null || this.queryParams.get("TABLE") == null || this.queryParams.get("TABLE").isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tên bảng chưa được thiết lập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            String select = this.queryParams.get("SELECT");
            String table = this.queryParams.get("TABLE");
            String join = this.buidlingJoinTable();
            String where = this.buildingCondition();
            String other = this.queryParams.get("OTHER");

            String sql = "SELECT " + select + " FROM " + table + "\n" + join + "\n" + where + "\n" + other;
            System.out.println("SQL Query: " + sql); // Log để kiểm tra

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            if (values != null && !values.isEmpty()) {
                for (int i = 0; i < values.size(); i++) {
                    preparedStatement.setObject(i + 1, values.get(i));
                }
            }
            return preparedStatement.executeQuery();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public boolean insertData(ArrayList<Object> values) {
        try {
            if (this.queryParams == null || this.queryParams.get("TABLE") == null || this.queryParams.get("TABLE").isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tên bảng chưa được thiết lập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            String table = this.queryParams.get("TABLE");
            String field = this.buidlingFieldInsert();

            StringBuilder insertValue = new StringBuilder();
            for (int i = 0; i < values.size(); i++) {
                insertValue.append("?");
                if (i < values.size() - 1) {
                    insertValue.append(", ");
                }
            }

            String sql = "INSERT INTO " + table + " " + field + " VALUES (" + insertValue + ")";
            System.out.println("SQL Insert: " + sql); // Log để kiểm tra
            System.out.println("Values: " + values); // Log dữ liệu đầu vào

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsInserted > 0;

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Lỗi SQL: " + exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
            return false;
        }
    }

    public boolean deleteData(ArrayList<Object> values) {
        try {
            String table = this.queryParams.get("TABLE");
            String where = this.buildingCondition();

            String sql = "DELETE FROM " + table + " " + where;

            try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
                for (int i = 0; i < values.size(); i++) {
                    preparedStatement.setObject(i + 1, values.get(i));
                }

                int affectedRows = preparedStatement.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean updateData(HashMap<String, Object> updateValues, ArrayList<Object> conditionValues) {
        try {
            String table = this.queryParams.get("TABLE");
            String where = this.buildingCondition();

            StringBuilder setClause = new StringBuilder();
            for (String column : updateValues.keySet()) {
                if (setClause.length() > 0) {
                    setClause.append(", ");
                }
                setClause.append(column).append(" = ?");
            }

            String sql = "UPDATE " + table + " SET " + setClause + " " + where;

            try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
                int index = 1;

                for (Object value : updateValues.values()) {
                    preparedStatement.setObject(index++, value);
                }

                for (Object value : conditionValues) {
                    preparedStatement.setObject(index++, value);
                }

                int affectedRows = preparedStatement.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean updateData(ArrayList<Object> valueCondition) {
        try {
            String join = this.buidlingJoinTable(); 
            String sql = "UPDATE "+ this.queryParams.get("TABLE") +" "
                        + join + " "
                        +"SET "+ this.queryParams.get("SET") +" "
                        +"WHERE "+ this.queryParams.get("WHERE");
            

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            int index = 1;

            for (Object value : valueCondition) {
                preparedStatement.setObject(index++, value);
            }

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    /* --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */

    public boolean existsDataInTable(ArrayList<Object> values) {
        try {
            String select = this.queryParams.get("SELECT");
            String table = this.queryParams.get("TABLE");
            String join = this.buidlingJoinTable();
            String where = this.buildingCondition();
            String other = this.queryParams.get("OTHER");

            String sql = "SELECT " + select + " FROM " + table + " \n"
                    + join + " \n"
                    + where + " \n"
                    + other;

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            if (values.size() > 0) {
                for (int i = 0; i < values.size(); i++) {
                    preparedStatement.setObject(i + 1, values.get(i));
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet != null;

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    /* --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */

    public void closeConnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
