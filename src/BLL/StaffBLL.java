package BLL;

import java.util.ArrayList;
import java.util.HashMap;

import DAL.StaffDAL;
import DTO.Staffs;

public class StaffBLL {
    private StaffDAL staffDAL;
    public StaffBLL() {
        this.staffDAL = new StaffDAL();
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //
    public ArrayList<Staffs> getAllStaffs() {
        return this.staffDAL.getStaffList();
    }

    public Staffs getStaffById(int staffId) {
        return this.staffDAL.getStaffById(staffId);
    }

    public Staffs getStaffByEmail(String staffEmail) {
        return this.staffDAL.getStaffByEmail(staffEmail);
    }

    public Staffs getStaffByAccountId(int accountId) {
        return this.staffDAL.getStaffByAccountId(accountId);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //
    public boolean updateAddressStaffById(int staffId, String addressValue) {
        return this.staffDAL.updateAddressStaffById(staffId, addressValue);
    }

    public boolean updateDetailsInfoStaffById(int staffId, HashMap<String, Object> updateValues) {
        return this.staffDAL.updateDetailsInfoStaffById(staffId, updateValues);
    }
    
    public boolean updateAvatarStaffById(int staffId, String avatarName) {
        return this.staffDAL.updateAvatarStaffById(staffId, avatarName);
    }
}
