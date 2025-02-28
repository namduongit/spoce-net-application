package Pojo;

import java.util.Date;

public class PasswordResetOtps {
    private int otpId;
    private int staffId;
    private String otpCode;
    private Date expiresAt;
    private boolean used;
    private Date createdAt;

    // Constructor không tham số
    public PasswordResetOtps() {
    }

    // Constructor có tham số
    public PasswordResetOtps(int otpId, int staffId, String otpCode, Date expiresAt, boolean used, Date createdAt) {
        this.otpId = otpId;
        this.staffId = staffId;
        this.otpCode = otpCode;
        this.expiresAt = expiresAt;
        this.used = used;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getOtpId() {
        return otpId;
    }

    public void setOtpId(int otpId) {
        this.otpId = otpId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Phương thức toString để trả về thông tin đối tượng
    @Override
    public String toString() {
        return "PasswordResetOtps{" +
                "otpId=" + otpId +
                ", staffId=" + staffId +
                ", otpCode='" + otpCode + '\'' +
                ", expiresAt=" + expiresAt +
                ", used=" + used +
                ", createdAt=" + createdAt +
                '}';
    }
}
