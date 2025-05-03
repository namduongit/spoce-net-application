package Utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ConfigSQL {
    // Kết nối trên Server Vps
    public static String USER_NAMEServer = "spoceNet";
    public static String PASSWORDServer = "SpoceTech";
    public static String URLServer = "jdbc:mysql://128.199.154.64:3306/net_gaming_management";
    // Kết nối Local
    public static String USER_NAME = "root";
    public static String PASSWORD = "NDuong205";
    public static String URL = "jdbc:mysql://localhost:3306/net_gaming_management";


    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        String sql = "SELECT * from foods";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberCols = metaData.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= numberCols; i++) {
                    System.out.print(resultSet.getObject(i));
                }
                System.out.println();
            }
        }
    }

}
