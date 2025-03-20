package BLL;

import DAL.RoomDAL;
import DTO.Rooms;

import java.util.ArrayList;

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
}
