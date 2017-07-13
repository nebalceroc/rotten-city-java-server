package Modules.Chat;

import ConnectionManager.Connection;
import DB.MapDBConnector;
import DB.PjDBConnector;
import Data.OutputMessage;
import FileIO.BattleFileIO;
import FileIO.ChatFileIO;
import Modules.Battle.Permanent.PermanentBattleInstancesManager;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import Structs.SkipList;
import java.io.PrintStream;

/**
 *
 * @author casa
 */
public class M_Chat {
    
    private Shell shell;
    private ServerProfile shell_profile;
    private ChatFileIO fileSystem;
    private ChatRoomManager chatRoomManager; 

    public M_Chat(Shell shell,ServerProfile shell_profile_s) {
        this.shell = shell;
        this.shell_profile=shell_profile_s;
        fileSystem = new ChatFileIO();
        chatRoomManager = new ChatRoomManager(shell,shell_profile_s,getFileSystem());
    }
    
    public OutputMessage doTask(String incoming, Connection conn) {
        String[] buffer = incoming.split("/");
        OutputMessage message = new OutputMessage();
        switch (buffer[1]) {
            case "INSTANCE_CHAT":
                chatRoomManager.proccessInstanceChat(conn.getUser().getPj().getMap(), buffer[2],conn.getUser().getPj() );
                break;

        }
        return null;

    }

    /**
     * @return the shell
     */
    public Shell getShell() {
        return shell;
    }

    /**
     * @param shell the shell to set
     */
    public void setShell(Shell shell) {
        this.shell = shell;
    }

    /**
     * @return the shell_profile
     */
    public ServerProfile getShell_profile() {
        return shell_profile;
    }

    /**
     * @param shell_profile the shell_profile to set
     */
    public void setShell_profile(ServerProfile shell_profile) {
        this.shell_profile = shell_profile;
    }

    /**
     * @return the fileSystem
     */
    public ChatFileIO getFileSystem() {
        return fileSystem;
    }

    /**
     * @param fileSystem the fileSystem to set
     */
    public void setFileSystem(ChatFileIO fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * @return the chatRoomManager
     */
    public ChatRoomManager getChatRoomManager() {
        return chatRoomManager;
    }

    /**
     * @param chatRoomManager the chatRoomManager to set
     */
    public void setChatRoomManager(ChatRoomManager chatRoomManager) {
        this.chatRoomManager = chatRoomManager;
    }
}
