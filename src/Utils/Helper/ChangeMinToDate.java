package Utils.Helper;

public class ChangeMinToDate {
    public static String changeData(int minutes) {
        int years = minutes / 525600;
        minutes -= years * 525600;

        int months = minutes / 43200;
        minutes -= months * 43200;

        int days = minutes / 1440;
        minutes -= days * 1440;

        int hours = minutes / 60;
        minutes -= hours * 60;

        String result = "";

        if (years > 0) result += years + " năm ";
        if (months > 0) result += months + " tháng ";
        if (days > 0) result += days + " ngày ";
        if (hours > 0) result += hours + " giờ ";
        result += minutes + " phút";

        return result.trim();
    }
    
}
