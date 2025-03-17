package BLL;

import DAL.GpuDAL;
import DTO.Gpus;

import java.util.ArrayList;

public class GpuBLL {
    private GpuDAL gpuDAL;

    public GpuBLL() {
        this.gpuDAL = new GpuDAL();
    }

    public ArrayList<Gpus> getAllGpus() {
        return this.gpuDAL.getGpuList();
    }

    public Gpus getGpubyId(int id) {
        return this.gpuDAL.getGpuById(id);
    }
}
