package BLL;

import java.util.ArrayList;
import java.util.HashMap;

import DAL.AccountDAL;
import DTO.Accounts;

public class AccountBLL {
    private AccountDAL accountDAL;
    public AccountBLL() {
        this.accountDAL = new AccountDAL();
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public ArrayList<Accounts> getAllAccounts() {
        return this.accountDAL.getAccountList();
    }

    public Accounts getAccountById(int accountId) {
        return this.accountDAL.getAccountById(accountId);
    }

    public Accounts getAccountByUsername(String username) {
        return this.accountDAL.getAccountByUsername(username);
    }

    public ArrayList<Object[]> getInfoPLayerAccountList() {
        return this.accountDAL.getInfoPlayerAccountList();
    }

    public ArrayList<Object[]> filterPlayerAccountList(String searchText, String status) {
        return this.accountDAL.filterPlayerAccountList(searchText, status);
    }

    public ArrayList<Object[]> getInfoStaffAccountList() {
        return this.accountDAL.getInfoStaffAccountList();
    } 

    public ArrayList<Object[]> filterStaffAccountList(String searchText, String status, String role, String orderName) {
        return this.accountDAL.filterStaffAccountList(searchText, status, role, orderName);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public boolean updateAccountDetailsById(int accountId, HashMap<String, Object> updateValues) {
        return this.accountDAL.updateAccountDetailsById(accountId, updateValues);
    }

    public boolean updatePasswordAccountById(int accountId, String currentPassword, String newPassword) {
        return this.accountDAL.updatePasswordAccountById(accountId, currentPassword, newPassword);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public boolean createPlayerAccount(String username, String password) {
        return this.accountDAL.createPlayerAccount(username, password);
    }

    public boolean updateBalancePlayerAccount(String username, int newBalance) {
        return this.accountDAL.updateBalancePlayerAccount(username, newBalance);
    }

    public boolean updateAccountStatus(int accountId, String status) {
        return this.accountDAL.updateAccountStatus(accountId, status);
    }

    public boolean createStaffAccount(String username, String password, String role) {
        return this.accountDAL.createStaffAccount(username, password, role);
    }


    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public boolean deleteAccountByUsername(String username) {
        return this.accountDAL.deleteAccountByUsername(username);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //
    
    public Accounts staffLoginAccount(String username, String password) {
        return this.accountDAL.staffLoginAccount(username, password);
    }
    public Accounts playerLoginAccount(String username, String password) {
        return this.accountDAL.playerLoginAccount(username, password);
    }


}
