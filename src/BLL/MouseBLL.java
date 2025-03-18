package BLL;

import DAL.MouseDAL;
import DTO.Mouse;

import java.util.ArrayList;

public class MouseBLL {
    private MouseDAL mouseDAL;

    public MouseBLL() {
        this.mouseDAL = new MouseDAL();
    }

    public ArrayList<Mouse> getAllMouses() {
        return this.mouseDAL.getMouseList();
    }

    public Mouse getMouseById(int id) {
        return this.mouseDAL.getMouseById(id);
    }

    public ArrayList<Mouse> getMousesByStatus(String status) {
        return this.mouseDAL.getMousesByStatus(status);
    }
}
