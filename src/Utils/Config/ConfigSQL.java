package Utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ConfigSQL {
    // Kết nối trên Server Vps
    public static String USER_NAMEServer = "net_admin";
    public static String PASSWORDServer = "StrongPassword123";
    public static String URLServer = "jdbc:mysql://159.89.207.183:3306/net_gaming_management";
    // Kết nối Local
    public static String USER_NAME = "root";
    public static String PASSWORD = "";
    public static String URL = "jdbc:mysql://localhost:3306/net_gaming_management";



}
