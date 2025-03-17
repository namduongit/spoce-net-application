package BLL;

import DAL.MotherboardDAL;
import DTO.Motherboards;

import java.util.ArrayList;

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
}
