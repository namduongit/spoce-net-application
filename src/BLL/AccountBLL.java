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

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- //

    public boolean updateAccountDetailsById(int accountId, HashMap<String, Object> updateValues) {
        return this.accountDAL.updateAccountDetailsById(accountId, updateValues);
    }

}
