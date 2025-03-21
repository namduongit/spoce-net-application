package BLL;

import DAL.MonitorDAL;
import DTO.Monitors;


import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Monitors> getMonitorsByStatus(String status) {
        return this.monitorDAL.getMonitorsByStatus(status);
    }

    public boolean updateMonitorById(int id, HashMap<String, Object> newvalues) {
        return this.monitorDAL.updateMonitorById(id, newvalues);
    }
}
