package BLL;



import DAL.HeadphoneDAL;
import DTO.Headphones;

import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Headphones> getHeadphonesByStatus(String status) {
        return this.headphoneDAL.getHeadphonesByStatus(status);
    }

    public boolean updateHeadphoneById(int id, HashMap<String, Object> newvalues) {
        return this.headphoneDAL.updateHeadphoneById(id, newvalues);
    }
    public boolean deleteHeadphoneById(int id) {
        return this.headphoneDAL.deleteHeadphoneById(id);
    }
}
