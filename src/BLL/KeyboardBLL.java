package BLL;



import DAL.KeyboardDAL;
import DTO.Keyboards;

import java.util.ArrayList;

public class KeyboardBLL {
    private KeyboardDAL keyboardDAL;

    public KeyboardBLL() {
        this.keyboardDAL = new KeyboardDAL();
    }

    public ArrayList<Keyboards> getAllKeyboards() {
        return this.keyboardDAL.getKeyboardList();
    }

    public Keyboards getKeyboardById(int id) {
        return this.keyboardDAL.getKeyboardById(id);
    }

    public ArrayList<Keyboards> getKeyboardsByStatus(String status) {
        return this.keyboardDAL.getKeyboardsByStatus(status);
    }
}
