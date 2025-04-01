package BLL;



import DAL.RomDAL;
import DTO.Roms;

import java.util.ArrayList;
import java.util.HashMap;

public class RomBLL {
    private RomDAL romDAL;

    public RomBLL() {
        this.romDAL = new RomDAL();
    }

    public ArrayList<Roms> getAllRoms() {
        return this.romDAL.getRomList();
    }

    public Roms getRomById(int id) {
        return this.romDAL.getRomById(id);
    }

    public ArrayList<Roms> getRomsByStatus(String status) {
        return this.romDAL.getRomsByStatus(status);
    }
    public boolean deleteRomById(int id) {
        return this.romDAL.deleteRomById(id);
    }
    public boolean updateRomById(int id, HashMap<String, Object> newvalues) {
        return this.romDAL.updateRomById(id, newvalues);
    }
}
