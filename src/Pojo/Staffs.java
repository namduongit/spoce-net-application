package Pojo;

import java.sql.Date;

public class Staffs {
    private int staffId;
    private int accountId;
    private String fullName;
    private Date birthDate;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String cccd;
    private String avatar;

    // Constructor không tham số
    public Staffs() {
    }

    // Constructor có tham số
    public Staffs(int staffId, int accountId, String fullName, Date birthDate, String gender, String phone, String email, String address, String cccd, String avatar) {
        this.staffId = staffId;
        this.accountId = accountId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.cccd = cccd;
        this.avatar = avatar;
    }

    // Getter và Setter
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "Staffs{" +
                "staffId=" + staffId +
                ", accountId=" + accountId +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", birthDate=" + gender +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", cccd=" + cccd +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
