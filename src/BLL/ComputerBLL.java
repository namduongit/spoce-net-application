package BLL;

import DAL.ComputerDAL;
import DTO.Computers;

import java.util.ArrayList;
import java.util.HashMap;

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

    public boolean updateComputerById(int id, HashMap<String, Object> newValues) {
        return this.computerDAL.updateComputerById(id,newValues);
    }

    public boolean deleteComputerById(int id) {
        return this.computerDAL.deleteComputerById(id);
    }

    public boolean insertComputer(ArrayList<Object> values) {
        return this.computerDAL.insertComputer(values);
    }
}
