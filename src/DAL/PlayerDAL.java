package DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DTO.Players;
import DAL.SQLHelper.MySQLHelper;

import javax.swing.JOptionPane;

public class PlayerDAL {
     public Players getPlayerByAccountId(int accountId) {
        Players player = null;
        MySQLHelper helper = new MySQLHelper();

        HashMap<String, String> params = new HashMap<>();
        params.put("TABLE", "players");
        params.put("WHERE", "account_id = ?");

        ArrayList<Object> values = new ArrayList<>();
        values.add(accountId);
        helper.buildingQueryParam(params);

        ResultSet resultSet = helper.queryWithParam(values);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    player = new Players(
                        resultSet.getInt("player_id"),
                        resultSet.getInt("account_id"),
                        resultSet.getInt("balance")
                    );
                    resultSet.close();
                    helper.closeConnect();
                    return player;
                }
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        return player;
    }
}
