package BLL;

import java.util.ArrayList;

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
    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //
    public boolean updateAddressStaffById(int staffId, String addressValue) {
        return this.staffDAL.updateAddressStaffById(staffId, addressValue);
    }
}
