package BLL;

import DAL.RamDAL;
import DTO.Rams;

import java.util.ArrayList;
import java.util.HashMap;

public class RamBLL {
    private RamDAL ramDAL;

    public RamBLL() {
        this.ramDAL = new RamDAL();
    }

    public ArrayList<Rams> getAllRams() {
        return this.ramDAL.getRamList();
    }

    public Rams getRamById(int id) {
        return this.ramDAL.getRamById(id);
    }

    public ArrayList<Rams> getRamsByStatus(String status) {
        return this.ramDAL.getRamsByStatus(status);
    }

    public boolean deleteRamById(int id) {
        return this.ramDAL.deleteRamById(id);
    }

    public boolean updateRamById(int id, HashMap<String, Object> newvalues) {
        return this.ramDAL.updateRamById(id, newvalues);
    }

    public boolean addRam(Rams ram) {
        return this.ramDAL.addRam(ram);
    }
}