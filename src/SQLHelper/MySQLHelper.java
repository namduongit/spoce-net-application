package SQLHelper;

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
        } catch (SQLException exception) {
            String messenger = exception.getMessage();
            JOptionPane.showMessageDialog(null, messenger, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */

    public String buildingCondition() {
        if (this.queryParams != null && !this.queryParams.get("WHERE").isEmpty()) {
            return "WHERE " + this.queryParams.get("WHERE");
        }
        return "";
    }

    public String buidlingJoinTable() {
        if (this.queryParams != null && !this.queryParams.get("JOIN").isEmpty()) {
            return "JOIN " + this.queryParams.get("JOIN");
        }
        return "";
    }

    public String buidlingFieldInsert() {
        if (this.queryParams != null && !this.queryParams.get("FIELD").isEmpty()) {
            return "("+ this.queryParams.get("FIELD") +")";
        }
        return "";
    }

    /* --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */

    public MySQLHelper buidlingQueryParam(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }

        params.putIfAbsent("SELECT", "*");          // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("TABLE", "");            // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("JOIN", "");             // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("WHERE", "");            // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("OTHER", "");            // Thêm nếu chưa có dữ liệu Key
        params.putIfAbsent("FIELD", "");            // Thêm nếu chưa có dữ liệu Key

        this.queryParams = params;
        return this;
    }

    /* --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- */

    public ResultSet query(String sql) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
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

            return preparedStatement.executeQuery();
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public boolean insertData(ArrayList<Object> values) {
        try {
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

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                preparedStatement.setObject(i + 1, values.get(i));
            }

            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();
            return rowsInserted > 0;

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
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

    public boolean updateData(Map<String, Object> updateValues, ArrayList<Object> conditionValues) {
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
