package BLL;

import DAL.MonitorDAL;
import DTO.Monitors;


import java.util.ArrayList;

public class MonitorBLL {
    private MonitorDAL monitorDAL;

    public MonitorBLL() {
        this.monitorDAL = new MonitorDAL();
    }

    public ArrayList<Monitors> getAllMonitors() {
        return this.monitorDAL.getMonitorList();
    }

    public Monitors getMonitorById(int id) {
        return this.monitorDAL.getMonitorById(id);
    }
}
