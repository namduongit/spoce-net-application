package BLL;

import DAL.RoomDAL;
import DTO.Rooms;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomBLL {
    private RoomDAL roomDAL;

    public RoomBLL() {
        this.roomDAL = new RoomDAL();
    }

    public ArrayList<Rooms> getAllRooms() {
        return this.roomDAL.getRoomList();
    }

    public Rooms getRoomById(int id) {
        return this.roomDAL.getRoomById(id);
    }

    public ArrayList<Rooms> getRoomsByStatus(String status) {
        return this.roomDAL.getRoomsByStatus(status);
    }

    public boolean deleteRoomById(int id) {
        return this.roomDAL.deleteRoomById(id);
    }

    public boolean updateRoomById(int id, HashMap<String, Object> values) {
        return this.roomDAL.updateRoomById(id, values);
    }

    public boolean insertRoom(ArrayList<Object> values) {
        return this.roomDAL.insertRoom(values);
    }
}
