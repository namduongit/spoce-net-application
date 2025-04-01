package BLL;

import DAL.CpuDAL;
import DTO.Cpus;

import java.util.ArrayList;

public class CpuBLL {
    private CpuDAL cpuDAL;

    public CpuBLL() {
        this.cpuDAL = new CpuDAL();
    }

    public ArrayList<Cpus> getAllCpus() {
        return this.cpuDAL.getCpuList();
    }

    public Cpus getCpuById(int id) {
        return this.cpuDAL.getCpuById(id);
    }
    public boolean deleteCpuById(int id) {
        return this.cpuDAL.deleteCpuById(id);
    }
}
