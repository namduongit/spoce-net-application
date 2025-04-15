package BLL;

import DAL.PlayerDAL;
import DTO.Players;

public class PlayerBLL {
    private PlayerDAL playerDAL;

    public PlayerBLL() {
        this.playerDAL = new PlayerDAL();
    }

    public Players getPlayerByAccountId(int accountId) {
        return this.playerDAL.getPlayerByAccountId(accountId);
    }
}
