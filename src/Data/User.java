package Data;

import DB.PjDBConnector;
import DB.UserDBConnector;
import java.sql.SQLException;

/**
 *
 * @author Nicolas
 */
public class User {
    
    private String user_name;
    private String ip;
    private Pj pj;
    
    public User(String user){
        this.user_name = user;        
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
        
    }
    
    public Pj getPj() {
        return pj;
    }

    public void setPj(Pj pj) {
        this.pj = pj;
    }
    
    public boolean hasPj() throws SQLException{
        PjDBConnector pjDB = new PjDBConnector();
        return pjDB.hasPj(this);
    }
    
    public String getPjData(String values) throws SQLException{
        PjDBConnector pjDB = new PjDBConnector(); 
        return pjDB.getPjData(values);
    }
    
    public String packAllPjData() throws SQLException{
        PjDBConnector pjDB = new PjDBConnector();
        return pjDB.getUserPjData(this);        
    }
}
