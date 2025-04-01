package BLL;

import DAL.MotherboardDAL;
import DTO.Motherboards;

import java.util.ArrayList;
import java.util.HashMap;

public class MotherboardBLL {
    private MotherboardDAL motherboardDAL;

    public MotherboardBLL() {
        this.motherboardDAL = new MotherboardDAL();
    }

    public ArrayList<Motherboards> getAllMotherboards() {
        return this.motherboardDAL.getMotherboardList();
    }

    public Motherboards getMotherboardById(int id) {
        return this.motherboardDAL.getMotherboardById(id);
    }

    public ArrayList<Motherboards> getMotherboardByBrand(String brand) {
        return this.motherboardDAL.getMotherboardByBrand(brand);
    }

    public ArrayList<Motherboards> getMotherboardsByStatus(String status) {
        return this.motherboardDAL.getMotherboardsByStatus(status);
    }

    public boolean updateMotherboardById(int id, HashMap<String, Object> newvalues) {
        return this.motherboardDAL.updateMotherboardById(id, newvalues);
    }
    public boolean deleteMotherboardById(int id) {
        return this.motherboardDAL.deleteMotherboardById(id);
    }
}
