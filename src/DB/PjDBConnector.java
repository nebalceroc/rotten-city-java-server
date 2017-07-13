package DB;

import Data.Pj;
import Data.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Nicolas
 */
public class PjDBConnector extends DBConnector {

    public String createPj(String pj_name, String pj_owner, String player_desc, String gear_desc, String body_desc) throws SQLException {
        if (!nameExists(pj_name)) {
            executeUpdateQuery("INSERT into pj VALUES ('" + pj_name + "','" + pj_owner + "','" + player_desc + "','" + gear_desc + "','WPED')");
            executeUpdateQuery("INSERT into avatar VALUES ('" + pj_name + "','" + body_desc + "')");
            return "PJ_CREATED";
        }else{
            return "NAME_CLONED";
        }
    }
    
    public void updatePlayerDesc(Pj pj){
        executeUpdateQuery("UPDATE pj SET player_desc= '"+pj.getPlayerDesc()+"' where pj_name= '"+pj.getName()+"'");
    }

    public boolean nameExists(String pj_name) throws SQLException {
        ResultSet rs = getQueryResults("SELECT pj_name FROM pj WHERE pj_name ='" + pj_name+"'");
        while (rs.next()) {
            return true;
        }
        return false;

    }

    public String getPjData(String pj_name) throws SQLException {
        ResultSet rs = getQueryResults("SELECT * FROM pj JOIN avatar USING(pj_name) WHERE pj_name = '" + pj_name + "';");
        while (rs.next()) {
            return rs.getString("ubication_map_id") + "," + rs.getString("body_desc") + "," + rs.getString("player_desc") + "," + rs.getString("gear_desc");
        }
        return "";
    }

    public boolean hasPj(User user) throws SQLException {
        ResultSet rs = getQueryResults("SELECT user, pjCont FROM users WHERE user='" + user.getUser_name() + "';");
        String pjPack = "";
        while (rs.next()) {
            if (!rs.getString("pjCont").equals("0")) {
                return true;
            }
        }
        return false;
    }

    public String getUserPjData(User user) throws SQLException {
        ResultSet rs = getQueryResults("SELECT * FROM pj JOIN avatar USING(pj_name) WHERE pj_owner='" + user.getUser_name() + "';");
        String pjPack = "";
        while (rs.next()) {
            pjPack = pjPack.concat(rs.getString("ubication_map_id") + "," + rs.getString("body_desc") + "," + rs.getString("player_desc") + "," + rs.getString("gear_desc") + "%");
        }
        return pjPack;
    }
//    public String getPjString(String name){
//        
//    }
}
