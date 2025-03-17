package BLL;



import DAL.HeadphoneDAL;
import DTO.Headphones;

import java.util.ArrayList;

public class HeadphoneBLL {
    private HeadphoneDAL headphoneDAL;

    public HeadphoneBLL() {
        this.headphoneDAL = new HeadphoneDAL();
    }

    public ArrayList<Headphones> getAllHeadphones() {
        return this.headphoneDAL.getHeadphoneList();
    }

    public Headphones getHeadphoneById(int id) {
        return this.headphoneDAL.getHeadphoneById(id);
    }
}
