package Utils.Data;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AddressComboBox extends JFrame {
    private final String fileData = System.getProperty("user.dir") + "/src/Utils/Data/AddressInfo.txt";

    private final Set<String> provinceSet = new LinkedHashSet<>();
    private final Map<String, Set<String>> cityMap = new LinkedHashMap<>();
    private final Map<String, Set<String>> wardMap = new LinkedHashMap<>();

    private JComboBox<String> provinceComboBox;
    private JComboBox<String> cityComboBox;
    private JComboBox<String> wardComboBox;

    public AddressComboBox() {
        this.setTitle("Chọn địa chỉ");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(3, 1));

        provinceComboBox = new JComboBox<>();
        cityComboBox = new JComboBox<>();
        wardComboBox = new JComboBox<>();

        this.add(provinceComboBox);
        this.add(cityComboBox);
        this.add(wardComboBox);

        loadData();

        for (String province : provinceSet) {
            provinceComboBox.addItem(province);
        }

        provinceComboBox.addActionListener(e -> updateCityList());
        cityComboBox.addActionListener(e -> updateWardList());

        updateCityList();
        this.setVisible(true);
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

    private void updateCityList() {
        cityComboBox.removeAllItems();
        wardComboBox.removeAllItems();

        String selectedProvince = (String) provinceComboBox.getSelectedItem();
        if (selectedProvince != null && cityMap.containsKey(selectedProvince)) {
            for (String city : cityMap.get(selectedProvince)) {
                cityComboBox.addItem(city);
            }
        }
    }

    private void updateWardList() {
        wardComboBox.removeAllItems();

        String selectedCity = (String) cityComboBox.getSelectedItem();
        if (selectedCity != null && wardMap.containsKey(selectedCity)) {
            for (String ward : wardMap.get(selectedCity)) {
                wardComboBox.addItem(ward);
            }
        }
    }

    public static void main(String[] args) {
        new AddressComboBox();
    }
}
