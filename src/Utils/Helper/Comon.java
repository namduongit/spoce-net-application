package Utils.Helper;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;

public class Comon {
    public static String formatMoney(String value) {
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (int i = value.length() - 1; i >= 0; i--) {
            result.insert(0, value.charAt(i));
            count++;

            if (count % 3 == 0 && i != 0) {
                result.insert(0, ".");
            }
        }

        return result.toString() + " VND";
    }

    public static boolean isTrueDate(String dateValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Không cho phép ngày sai
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateValue);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isTrueCCCD(String CCCDValue) {
        return CCCDValue.length() == 12;
    }

    public static boolean isTruePhone(String phoneValue) {
        return phoneValue.length() == 10;
    }

    public static boolean isTrueEmail(String emailValue) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailValue);
        return matcher.matches();
    }

    public static String formatFullName(String nameValue) {
        String result = "";
        String[] namePath = nameValue.trim().split(" ");

        for (String str : namePath) {
            result += str.toUpperCase().charAt(0);
            for (int i = 1; i < str.length(); i++) result += str.toLowerCase().charAt(i);
            result += " ";
        }
        return result.trim();
    }

    public static void main(String[] args) {
        System.out.println(formatMoney("10000"));
    }
}
