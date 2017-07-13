package Modules.Chat;

import ConnectionManager.Connection;
import java.util.LinkedList;

/**
 *
 * @author Nicolás Balcero
 */
public class ChatRoom {
    
    private LinkedList<Connection> conexiones;
    
    public ChatRoom(LinkedList<Connection> conns){
        conexiones= conns;
    }
    
    public void addConn(Connection con){
        getConexiones().add(con);
    }

    public LinkedList<Connection> getConexiones() {
        return conexiones;
    }

    public void setConexiones(LinkedList<Connection> conexiones) {
        this.conexiones = conexiones;
    }
    
}
