/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionManager;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolás Balcero
 */
public class ConnectionDirectory {

    private HashMap<String, Connection> connections = new HashMap<String, Connection>();
    private HashMap<String, InstanceConnDirectory> instance_connections = new HashMap<String, InstanceConnDirectory>();
    private boolean instance_hash_enabled;
    private boolean conn_hash_enabled;

    public ConnectionDirectory() {
        instance_hash_enabled = true;
        conn_hash_enabled = true;
    }

    public void addConnectionToInstanceDirectory(Connection conn) {
        getInstance_connections().get(conn.getUser().getPj().getMap()).addConnection(conn);
    }

    public void removeConnectionInInstanceDirectory(Connection conn) {
        getInstance_connections().get(conn.getUser().getPj().getMap()).removeConnection(conn);
    }

    public void createInstanceConnDirectory(String map_id) {
        synchronized (instance_connections) {
            if (!instance_hash_enabled) {
                try {
                    instance_connections.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectionDirectory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            instance_hash_enabled = false;
            getInstance_connections().put(map_id, new InstanceConnDirectory(map_id));
            instance_hash_enabled = true;
            instance_connections.notifyAll();
        }
    }

    public void addConnection(String user_name, Connection conn) {
        synchronized (connections) {
            if (!conn_hash_enabled) {
                try {
                    connections.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectionDirectory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            conn_hash_enabled = false;
            getConnections().put(user_name, conn);
            conn_hash_enabled = true;
            connections.notifyAll();
        }
    }

    public void removeConnection(String user_name) {
        synchronized (connections) {
            if (!conn_hash_enabled) {
                try {
                    connections.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectionDirectory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            conn_hash_enabled = false;
            getConnections().remove(user_name);
            conn_hash_enabled = true;
            connections.notifyAll();
        }

    }

    public HashMap<String, Connection> getConnections() {
        return connections;
    }

    public void setConnections(HashMap<String, Connection> connections) {
        this.setConnections(connections);
    }

    public HashMap<String, InstanceConnDirectory> getInstance_connections() {
        return instance_connections;
    }

    public void setInstance_connections(HashMap<String, InstanceConnDirectory> instance_connections) {
        this.instance_connections = instance_connections;
    }
}
