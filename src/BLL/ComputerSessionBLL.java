package BLL;

import DAL.ComputerSessionDAL;
import DTO.ComputerSessions;

import java.util.ArrayList;
import java.util.HashMap;

public class ComputerSessionBLL {
    private ComputerSessionDAL computerSessionDAL;

    public ComputerSessionBLL() {
        this.computerSessionDAL = new ComputerSessionDAL();
    }

    public ArrayList<ComputerSessions> getComputerSessionList() {
        return this.computerSessionDAL.getComputerSessionList();
    }

    public boolean insertComputerSession(ArrayList<Object> values) {
        return this.computerSessionDAL.insertComputerSession(values);
    }

    public boolean updateComputerSession(int computerId, HashMap<String, Object> updatingValues) {
        return this.computerSessionDAL.updateComputerSession(computerId, updatingValues);
    }

    public boolean updateEndTimeOfComputerSession(int computerId) {
        return this.computerSessionDAL.updateEndTimeOfComputerSession(computerId);
    }

    public boolean updateTotalCostOfComputerSession(int computerId, int pricePerHour) {
        return this.computerSessionDAL.updateTotalCostOfComputerSession(computerId, pricePerHour);
    }
}
