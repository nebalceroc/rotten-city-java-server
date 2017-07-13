package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 *
 * @author Nicolas
 */
public class MapDBConnector extends DBConnector {

    public String getMapString(String map_id) throws SQLException {
        ResultSet rs = getQueryResults("SELECT map_id, map_string FROM maps WHERE map_id ='" + map_id + "';");
        while (rs.next()) {
            return rs.getString("map_string");
        }
        return "NULL";
    }

    public int getMapLvl(String map_id) throws SQLException {
        ResultSet rs = getQueryResults("SELECT map_id, map_req_lvl FROM maps WHERE map_id='" + map_id + "';");
        while (rs.next()) {
            return Integer.parseInt(rs.getString("map_req_lvl"));
        }
        return -1;
    }

    public LinkedList getMapIdArray() throws SQLException {
        ResultSet rs = getQueryResults("SELECT map_id FROM maps");
        LinkedList output = new LinkedList();
        while (rs.next()) {
            output.add(rs.getString("map_id"));
        }
        return output;
    }

    public String getMapDataPack(String map_id) throws SQLException {
        ResultSet rs = getQueryResults("SELECT * FROM maps WHERE map_id ='"+map_id+"';");
        while (rs.next()) {
               return map_id + "€" + rs.getString("map_string") + "€" + rs.getString("map_mobs_string") + "€";
        }
        return "";
    }
}