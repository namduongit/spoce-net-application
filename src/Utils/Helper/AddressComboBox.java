package Utils.Helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class AddressComboBox  {
    private final String fileData = System.getProperty("user.dir") + "/src/Utils/Data/AddressInfo.txt";

    private final Set<String> provinceSet = new LinkedHashSet<>();
    private final Map<String, Set<String>> cityMap = new LinkedHashMap<>();
    private final Map<String, Set<String>> wardMap = new LinkedHashMap<>();

    public AddressComboBox() {
        loadData();
    }

    private void loadData() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileData))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty() || line.equalsIgnoreCase("--") || line.equalsIgnoreCase("-")) continue;
                if (line.contains("Chọn")) continue;
                String[] pathCountry = line.split("\\|");
                if (pathCountry.length == 3) {
                    String province = pathCountry[0].trim();
                    String city = pathCountry[1].trim();
                    String ward = pathCountry[2].trim();

                    provinceSet.add(province);

                    cityMap.computeIfAbsent(province, k -> new HashSet<>()).add(city);

                    wardMap.computeIfAbsent(city, k -> new HashSet<>()).add(ward);
                    }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Set<String> getProvinceSet() {
        return provinceSet;
    }
    public Map<String, Set<String>> getCityMap() {
        return cityMap;
    }
    public Map<String, Set<String>> getWardMap() {
        return wardMap;
    }
}
