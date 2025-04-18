package BLL;

import DAL.CpuDAL;
import DTO.Cpus;

import java.util.ArrayList;
import java.util.HashMap;

public class CpuBLL {
    private CpuDAL cpuDAL;

    public CpuBLL() {
        this.cpuDAL = new CpuDAL();
    }

    public ArrayList<Cpus> getAllCpus() {
        return this.cpuDAL.getCpuList();
    }
    public boolean updateCpuById(int id, HashMap<String, Object> newvalues) {
        return this.cpuDAL.updateCpuById(id, newvalues);
    }
    public Cpus getCpuById(int id) {
        return this.cpuDAL.getCpuById(id);
    }
    public boolean deleteCpuById(int id) {
        return this.cpuDAL.deleteCpuById(id);
    }
    public boolean addCpu(Cpus cpu) {
        return this.cpuDAL.addCpu(cpu);
    }
}
