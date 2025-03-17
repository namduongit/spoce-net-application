package BLL;

import DAL.ComputerDAL;
import DTO.Computers;

import java.util.ArrayList;

public class ComputerBLL {
    private ComputerDAL computerDAL;

    public ComputerBLL() {
        this.computerDAL = new ComputerDAL();
    }

    public ArrayList<Computers> getAllComputers() {
        return this.computerDAL.getComputerList();
    }

    public Computers getComputerById(int id) {
        return this.computerDAL.getComputerById(id);
    }
}
