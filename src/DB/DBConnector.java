package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public abstract class DBConnector {
    
    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/GameDB?" + "user=root&password=";
            Connection con = DriverManager.getConnection(connectionUrl);
            return con;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        }
        return null;
    }
    
    public ResultSet getQueryResults(String query){
        try {
            Connection con = conectar();
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void executeUpdateQuery(String query){
        try {
            Connection con = conectar();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
