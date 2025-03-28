package BLL;

import DAL.ComputerSessionDAL;
import DTO.ComputerSessions;

import java.util.ArrayList;

public class ComputerSessionBLL {
    private ComputerSessionDAL computerSessionDAL;

    public ComputerSessionBLL() {
        this.computerSessionDAL = new ComputerSessionDAL();
    }

    public ArrayList<ComputerSessions> getComputerSessionList() {
        return this.computerSessionDAL.getComputerSessionList();
    }
}
