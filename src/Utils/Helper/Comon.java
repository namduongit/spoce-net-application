package Utils.Helper;

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


    public static void main(String[] args) {
        System.out.println(formatMoney("10000"));
    }
}
