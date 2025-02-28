package Pojo;

import java.util.Date;

public class Staffs {
    private int staffId;
    private int accountId;
    private String fullName;
    private Date birthDate;
    private String phone;
    private String email;
    private String avatar;

    // Constructor không tham số
    public Staffs() {
    }

    // Constructor có tham số
    public Staffs(int staffId, int accountId, String fullName, Date birthDate, String phone, String email, String avatar) {
        this.staffId = staffId;
        this.accountId = accountId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
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
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
