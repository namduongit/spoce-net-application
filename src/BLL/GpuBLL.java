package BLL;

import DAL.GpuDAL;
import DTO.Gpus;

import java.util.ArrayList;
import java.util.HashMap;

public class GpuBLL {
    private GpuDAL gpuDAL;

    public GpuBLL() {
        this.gpuDAL = new GpuDAL();
    }

    public ArrayList<Gpus> getAllGpus() {
        return this.gpuDAL.getGpuList();
    }
    public boolean updateGpuById(int id, HashMap<String, Object> newvalues) {
        return this.gpuDAL.updateGpuById(id, newvalues);
    }
    public Gpus getGpubyId(int id) {
        return this.gpuDAL.getGpuById(id);
    }

    public boolean deleteGpuById(int id) {
        return this.gpuDAL.deleteGpuById(id);
    }

    public boolean addGpu(Gpus gpu) {
        return this.gpuDAL.addGpu(gpu);
    }
}