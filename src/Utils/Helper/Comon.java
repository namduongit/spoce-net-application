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

    public static boolean isTrueNumber(String numberValue) {
        if (numberValue == null || numberValue.trim().isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(numberValue.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isTrueDateString(String dateValue) {
        String[] regexStrings = dateValue.split("-");
        if (regexStrings.length != 3) return false;

        String year = regexStrings[0];
        String month = regexStrings[1];
        String day = regexStrings[2];

        if (!isTrueNumber(year) || !isTrueNumber(month) || !isTrueNumber(day)) return false;

        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        int dayInt = Integer.parseInt(day);

        if (yearInt > 2025) return false;
        if (monthInt < 1 || monthInt > 12) return false;
        if (dayInt < 1 || dayInt > 31) return false;

        // Xử lý tháng có 30 ngày
        if ((monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt == 11) && dayInt > 30) return false;

        // Xử lý tháng 2
        if (monthInt == 2) {
            boolean isLeap = (yearInt % 4 == 0 && (yearInt % 100 != 0 || yearInt % 400 == 0));
            if ((isLeap && dayInt > 29) || (!isLeap && dayInt > 28)) return false;
        }

        return true;
    }

    
}
