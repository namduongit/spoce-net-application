package BLL;

import DAL.ComputerSessionDAL;
import DTO.ComputerSessions;
import java.time.LocalDateTime;
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

      // Lấy dữ liệu cho biểu đồ doanh thu máy
    public ArrayList<Object[]> getComputerRevenue(LocalDateTime start, LocalDateTime end) {
        return computerSessionDAL.getComputerRevenue(start, end);
    }

    public boolean insertComputerSession(ArrayList<Object> values) {
        return this.computerSessionDAL.insertComputerSession(values);
    }
    public int insertValueComputerSession(int computerId, int playerId) {
        return this.computerSessionDAL.insertValueComputerSession(computerId, playerId);
    }

    public int getInsertLastestSession(int computerId) {
        return this.computerSessionDAL.getInsertLastestSession(computerId);
    }

    public boolean updateComputerSession(int computerId, HashMap<String, Object> updatingValues) {
        return this.computerSessionDAL.updateComputerSession(computerId, updatingValues);
    }

    public boolean updateSessionCost(int sessionId, int totalMoney) {
        return this.computerSessionDAL.updateSessionCost(sessionId, totalMoney);
    }

    public boolean updateEndTimeOfComputerSession(int computerId) {
        return this.computerSessionDAL.updateEndTimeOfComputerSession(computerId);
    }

    public boolean updateTotalCostOfComputerSession(int computerId, int pricePerHour) {
        return this.computerSessionDAL.updateTotalCostOfComputerSession(computerId, pricePerHour);
    }

    public HashMap<String, Object> getComputerInfoAndRoomInfoOfSession(int sessionId) {
        return this.computerSessionDAL.getComputerInfoAndRoomInfoOfSession(sessionId);
    }

    public ArrayList<String> getComputersInRoom(String roomName) {
        return computerSessionDAL.getComputersInRoom(roomName);
    }

    public ArrayList<Object[]> getComputersWithRevenue(LocalDateTime start, LocalDateTime end) {
        return computerSessionDAL.getComputersWithRevenue(start, end);
    }
    public ArrayList<String> getAllRooms() {
        return computerSessionDAL.getAllRooms();
    }


}
