package Utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ConfigSQL {
    // Kết nối trên Server Vps
    public static String USER_NAME = "net_admin";
    public static String PASSWORD = "StrongPassword123";
    public static String URL = "jdbc:mysql://159.89.207.183:3306/net_gaming_management";
    // Kết nối Local
<<<<<<< HEAD
    public static String USER_NAMEServer = "root";
    public static String PASSWORDServer = "123456";
    public static String URLServer = "jdbc:mysql://localhost:3306/net_gaming_management";
=======
    public static String USER_NAME = "root";
    public static String PASSWORD = "NDuong205";
    public static String URL = "jdbc:mysql://localhost:3306/net_gaming_management";
>>>>>>> da1ae80e792a70be0047a789b677431eda9a34c8



}
