package Utils.Helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

public class EncriptString {
    public static String MD5String(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Chuyển chuỗi thành byte để băm luôn luôn có length bằng 16
            byte[] bytes = md.digest(value.getBytes());

            // 1 biểu thị cho số dương, không cần bù 2
            BigInteger big = new BigInteger(1, bytes);

            // Chuyển sang hệ 16
            String hashtext = big.toString(16);
            // Đảm bảo đủ 32 kí tự
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }
}
