package DAL;

import DAL.SQLHelper.MySQLHelper;
import DTO.Computers;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ComputerDAL {

    public ArrayList<Computers> getComputerList() {
        ArrayList<Computers> arr = new ArrayList<>();
        MySQLHelper helper = new MySQLHelper();

        // lấy tất cả dòng dữ liệu của bảng computers
        ResultSet rs = helper.selectAllFromTable("computers");

        try {
            while (rs.next()) {
                Computers computer = new Computers(
                        rs.getInt("computer_id"),
                        rs.getString("name"),
                        rs.getInt("price_per_hour"),
                        rs.getInt("motherboard_id"),
                        (Integer)rs.getObject("mouse_id"),
                        (Integer)rs.getObject("keyboard_id"),
                        (Integer)rs.getObject("monitor_id"),
                        (Integer)rs.getObject("headphone_id"),
                        (Integer)rs.getObject("rom_id"),
                        (Integer)rs.getObject("room_id"),
                        rs.getString("status")
                );

                arr.add(computer);
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return arr;
    }

    public Computers getComputerById(int id) {
        ArrayList<Computers> arr = this.getComputerList();

        for (Computers x : arr) {
            if (x.getComputerId() == id) {
                return x;
            }
        }

        return null;
    }

    public boolean updateComputerById(int id, HashMap<String, Object> newValues) {
        if (newValues == null || newValues.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computers");
        params.put("WHERE", "computers.computer_id = ?");

        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.updateData(newValues, values);
    }

    public boolean deleteComputerById(int id) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computers");
        params.put("WHERE", "computers.computer_id = ?");
        helper.buildingQueryParam(params);

        ArrayList<Object> values = new ArrayList<>();
        values.add(id);

        return helper.deleteData(values);
    }

    public boolean insertComputer(ArrayList<Object> values) {
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computers");
        params.put("FIELD", "name, price_per_hour, motherboard_id, mouse_id, keyboard_id, monitor_id, headphone_id, rom_id, room_id, status");
        helper.buildingQueryParam(params);

        return helper.insertData(values);
    }

    public ArrayList<Computers> getComputerSByStatus(String status) {
        ArrayList<Computers> list = new ArrayList<>();
        for (Computers x : this.getComputerList()) {
            if (x.getStatus().equals(status)) {
                list.add(x);
            }
        }
        return list;
    }

    public HashMap<String, Object> getComputerInfoAndSpec(int computerId) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<Integer, Object> ramList = new HashMap<>();
        HashMap<Integer, Object> storageList = new HashMap<>();
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "computers c");
        params.put("WHERE", "c.computer_id = ?");
        params.put("JOIN",
                "motherboards m ON c.motherboard_id = m.motherboard_id " +
                "JOIN cpus ON m.cpu_id = cpus.cpu_id " +
                "JOIN gpus ON m.gpu_id = gpus.gpu_id " +
                "JOIN psus ON m.psu_id = psus.psu_id " +
                "LEFT JOIN mouse ON c.mouse_id = mouse.mouse_id " +
                "LEFT JOIN keyboards ON c.keyboard_id = keyboards.keyboard_id " +
                "LEFT JOIN monitors ON c.monitor_id = monitors.monitor_id " +
                "LEFT JOIN headphones ON c.headphone_id = headphones.headphone_id " +
                "LEFT JOIN roms ON c.rom_id = roms.rom_id " +
                "JOIN motherboard_ram mr ON m.motherboard_id = mr.motherboard_id " +
                "JOIN rams ON mr.ram_id = rams.ram_id " +
                "JOIN motherboard_storage ms ON m.motherboard_id = ms.motherboard_id " +
                "JOIN storages ON ms.storage_id = storages.storage_id ");
        params.put("SELECT",
                "c.computer_id, c.name, c.price_per_hour, c.status, " +
                "m.model AS motherboard_name, " +
                "cpus.model AS cpu_name, " +
                "gpus.model AS gpu_name, " +
                "psus.model AS psu_name, " +
                "mouse.model AS mouse_name, " +
                "keyboards.model AS keyboard_name, " +
                "monitors.model AS monitor_name, " +
                "headphones.model AS headphone_name, " +
                "roms.model AS rom_name, " +
                "rams.ram_id, " +
                "rams.model AS ram_name, " +
                "storages.storage_id, " +
                "storages.model AS storage_name");

        helper.buildingQueryParam(params);

        ArrayList<Object> conditionValues = new ArrayList<>();
        conditionValues.add(computerId);

        try {
            ResultSet rs = helper.queryWithParam(conditionValues);
            boolean isFirstRow = true;
            while (rs.next()) {
                if (isFirstRow) {
                    result.put("computer_id", rs.getInt("computer_id"));
                    result.put("name", rs.getString("name"));
                    result.put("price_per_hour", rs.getInt("price_per_hour"));
                    result.put("status", rs.getString("status"));
                    result.put("motherboard_name", rs.getString("motherboard_name"));
                    result.put("cpu_name", rs.getString("cpu_name"));
                    result.put("gpu_name", rs.getString("gpu_name"));
                    result.put("psu_name", rs.getString("psu_name"));
                    result.put("mouse_name", rs.getString("mouse_name"));
                    result.put("keyboard_name", rs.getString("keyboard_name"));
                    result.put("monitor_name", rs.getString("monitor_name"));
                    result.put("headphone_name", rs.getString("headphone_name"));
                    result.put("rom_name", rs.getString("rom_name"));

                    isFirstRow = false;
                }

                ramList.put(rs.getInt("ram_id"), rs.getString("ram_name"));
                storageList.put(rs.getInt("storage_id"), rs.getString("storage_name"));
            }
            rs.close();
            helper.closeConnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Lỗi ở phương thức getComputerInfoAndSpec",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        result.put("ram", ramList);
        result.put("storage", storageList);

        return result;
    }
}
