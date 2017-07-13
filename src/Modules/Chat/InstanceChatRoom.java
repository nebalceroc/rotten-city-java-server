package Modules.Chat;

import ConnectionManager.Connection;
import Data.Pj;
import java.util.LinkedList;

/**
 *
 * @author Nicolás Balcero
 */
public class InstanceChatRoom extends ChatRoom{
    
    private String instance_id;
    
    public InstanceChatRoom(LinkedList<Connection> conn, String instance_id){
        super(conn);
        this.instance_id=instance_id;
    }
    
    public void proccessMessage(String message, Pj pj){
        NewChatAdvisor advisor = new NewChatAdvisor(super.getConexiones(), message, pj);
        Thread t = new Thread(advisor);
        t.start();
    }
}
