package Modules.Chat;

import ConnectionManager.Connection;
import Data.Pj;
import FileIO.BattleFileIO;
import FileIO.ChatFileIO;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Nicolás Balcero
 */
public class ChatRoomManager {
    
    private ChatFileIO fileSystem;
    private Shell shell;
    private ServerProfile shell_profile;
    private HashMap<String,ChatRoom> chats = new HashMap<>();

    public ChatRoomManager(Shell shell, ServerProfile shell_profile, ChatFileIO fileSystem) {
        this.shell = shell;
        this.shell_profile=shell_profile;
        this.fileSystem = fileSystem;
    }
    
    public InstanceChatRoom initInstanceChatRoom(LinkedList<Connection> conn,String instance_id){
        InstanceChatRoom room = new InstanceChatRoom(conn, instance_id);
        fileSystem.initInstanceChatFile(instance_id);
        addInstance(room, instance_id);
        return room;
    }
    
    public void proccessInstanceChat(String instance_id, String message, Pj pj){
        InstanceChatRoom room = (InstanceChatRoom) chats.get(instance_id);
        room.proccessMessage(message, pj);
    }
    
    private void addInstance(ChatRoom room, String instance_id){
        chats.put(instance_id, room);
    }
    
}
