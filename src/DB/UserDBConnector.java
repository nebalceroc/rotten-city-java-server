package DB;

import java.sql.*;
/**
 *
 * @author Nicolas
 */
public class UserDBConnector extends DBConnector{
    
    public UserDBConnector(){
    }
    
    public String validarUsuario(String user, String pass) throws SQLException{
        ResultSet rs = getQueryResults("SELECT user, pass, enabled FROM users WHERE user ='"+user+"' and pass ='"+pass+"';");
        while(rs.next()){
            if(rs.getBoolean("enabled")){
                    return "VALID_LOGIN";
                }else{
                    return "ACCOUNT_DISABLED";
                }
        }
        return "INVALID_LOGIN";
    }
    
    public boolean usuarioExistente(String user) throws SQLException{
        ResultSet rs = getQueryResults("SELECT user FROM users WHERE user='"+user+"';");
        while(rs.next()){
                return true;            
        }
        return false;
    }
    
    public void crearUsuario(String user, String pass, String email) throws SQLException{
        executeUpdateQuery("INSERT INTO Users VALUES ('"+user+"', '"+pass+"','" +email+"', false, 0);");
    }

    


}
