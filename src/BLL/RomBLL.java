package BLL;



import DAL.RomDAL;
import DTO.Roms;

import java.util.ArrayList;

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
}
