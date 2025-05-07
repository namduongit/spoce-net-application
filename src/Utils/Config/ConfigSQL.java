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
    public static String USER_NAMEServer = "root";
    public static String PASSWORDServer = "1234567";
    public static String URLServer = "jdbc:mysql://localhost:3307/net_gaming_management";



}
