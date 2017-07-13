package DB;

import Data.Gun;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemDBConnector extends DBConnector{
    
    public ItemDBConnector(){
        
    }
        
    public Gun getGunById(String id) throws SQLException{
        ResultSet rs = getQueryResults("SELECT * FROM gun_items WHERE item_id ='"+id+"';");
        while(rs.next()){
            String gun_string = rs.getString("item_id")+"/"+rs.getString("max_damage")+"/"+rs.getString("min_damage");
               return new Gun(gun_string);
        }
        return null;        
    }
    
    public String getItemsData(String pj_name) throws SQLException{
        ResultSet rs = getQueryResults("SELECT * FROM pj_items WHERE pj_name ='"+pj_name+"';");
        String data_pack = "";
        while(rs.next()){
               String item_location = rs.getString("item_location");
               if(item_location.equals("BAG")){
                   data_pack = data_pack.concat(rs.getString("item_id")+"@");
               } 
        }
        return data_pack.substring(0, data_pack.length()-1);    
    }
    
}
