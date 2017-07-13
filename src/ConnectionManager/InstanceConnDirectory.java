/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionManager;

import Data.User;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolás Balcero
 */
public class InstanceConnDirectory {

    private String map_id;
    private HashMap<String, Connection> connections;
    private boolean hash_enabled;

    public InstanceConnDirectory(String map_id) {
        this.map_id = map_id;
        connections = new HashMap<String, Connection>();
        hash_enabled = true;
    }

    public Connection getConnection(User user) {
        return connections.get(user.getUser_name());
    }

    public void addConnection(Connection conn) {
        synchronized (connections) {
            if (!hash_enabled) {
                try {
                    connections.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(InstanceConnDirectory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            hash_enabled = false;
            connections.put(conn.getUser().getUser_name(), conn);
            hash_enabled = true;
            connections.notifyAll();
        }

    }

    public void removeConnection(Connection conn) {
        synchronized (connections) {
            if (!hash_enabled) {
                try {
                    connections.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(InstanceConnDirectory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            hash_enabled = false;
            connections.remove(conn);
            hash_enabled = true;
            connections.notifyAll();
        }
    }         
       
    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public HashMap<String, Connection> getConnections() {
        return connections;
    }

    public void setConnections(HashMap<String, Connection> connections) {
        this.connections = connections;
    }
}
