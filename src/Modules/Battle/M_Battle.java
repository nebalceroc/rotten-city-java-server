package Modules.Battle;

import Modules.Battle.Permanent.PermanentBattleInstancesManager;
import ConnectionManager.Connection;
import DB.ItemDBConnector;
import DB.MapDBConnector;
import DB.PjDBConnector;
import DB.UserDBConnector;
import Data.Gun;
import Data.OutputMessage;
import Data.Pj;
import FileIO.BattleFileIO;
import FileIO.LoginFileIO;
import Modules.Battle.Advices.LvlUpAdvisor;
import Modules.ModuleTool;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class M_Battle {

    private PjDBConnector DBConPj = new PjDBConnector();
    private MapDBConnector DBConMap = new MapDBConnector();
    private Shell shell;
    private ServerProfile shell_profile_s;
    private BattleFileIO fileSystem;
    private PermanentBattleInstancesManager wiManager;
    private ModuleTool tools;

    public M_Battle(Shell shell, ServerProfile shell_profile_s, ModuleTool tools) {
        this.shell = shell;
        this.shell_profile_s = shell_profile_s;
        this.tools = tools;
        fileSystem = new BattleFileIO();
        DBConPj = new PjDBConnector();
        DBConMap = new MapDBConnector();
        wiManager = new PermanentBattleInstancesManager(shell, shell_profile_s, getFileSystem(), tools);
    }

    public void removeDisconectedPj(Pj pj, Connection conn) {
        getWiManager().removePj(pj, conn);
    }

    public OutputMessage doTask(String incoming, Connection conn) {
        String[] buffer = incoming.split("/");
        OutputMessage message = new OutputMessage();
        switch (buffer[1]) {
            case "INIT":
                String map_id = buffer[2];
                switch (map_id.charAt(0)) {
                    case 'W':
                        if (getWiManager().correctLvl(map_id, conn.getUser().getPj())) {
                            getWiManager().submitPJ(map_id, conn.getUser().getPj(), conn);
                            message.setAnswer("ACCEPT");
                        } else {
                            message.setAnswer("DECLINE");
                        }
                        break;
                    default:
                        break;
                }
                break;
            case "WAITING":
                try {
                    String map_data = "";
                    message.setAnswer("MAP_INIT");
                    message.setMessage(getDBConMap().getMapDataPack(conn.getUser().getPj().getMap()) + getFileSystem().getInstanceUsersData(conn.getUser().getPj().getMap(), conn.getUser()));
                } catch (SQLException ex) {
                    Logger.getLogger(M_Battle.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "DATA":
                switch (buffer[2]) {
                    case "UPDATE":
                        //synchronized (fileSystem) {
                        getFileSystem().updatePjPosition(conn.getUser().getPj().getMap(), conn.getUser().getPj(), buffer[3]);
                        message.setAnswer("MAP_UPDATE");
                        message.setMessage(getFileSystem().getInstanceUpdateData(conn.getUser().getPj().getMap(), conn.getUser().getPj()));
                        //}
                        break;
                    case "COLLISION":
                            int damage = processCollision(conn.getUser().getPj(), buffer[3], buffer[4]);
                            if ( damage>= 0) {
                                message.setAnswer("HIT");
                                message.setMessage("BATTLE/DATA/COLLISION/"+damage);
                                writeInInstanceShell(conn,conn.getUser().getPj().getName() +" hits to "+buffer[3]+" : weapon= "+buffer[4]+" : damage= "+damage);
                            }else{
                                message.setAnswer("HIT");
                                if(getFileSystem().addExp(8, conn.getUser().getPj())){
                                    System.out.println(conn.getUser().getPj()+"subio de lvl");
                                    LvlUpAdvisor adv = new LvlUpAdvisor(wiManager.getInstances().get(conn.getUser().getPj().getMap()).getConexiones(), conn.getUser().getPj(),wiManager.getInstances().get(conn.getUser().getPj().getMap()).isList_enabled());
                                    Thread advT = new Thread(adv);
                                    advT.start();
                                }   
                                message.setMessage("BATTLE/DATA/COLLISION/kill");
                                wiManager.getInstances().get(conn.getUser().getPj().getMap()).deadPj(buffer[3]);
                                writeInInstanceShell(conn, conn.getUser().getPj().getName() +" kills "+buffer[3]);
                            }

                        break;
                }
                break;
            case "CHANGE_INSTANCE":
                String new_map_id = buffer[2];
                switch (new_map_id.charAt(0)) {
                    case 'W':
                        if (getWiManager().correctLvl(new_map_id, conn.getUser().getPj())) {
                            getWiManager().removePj(conn.getUser().getPj(), conn);
                            getWiManager().submitPJ(new_map_id, conn.getUser().getPj(), conn);
                            message.setAnswer("ACCEPT");
                        } else {
                            message.setAnswer("DECLINE");
                        }
                        break;
                    default:
                        break;
                }                
                break;
            default:
                break;
        }
        return message;
    }

    public void manageDeadPj(String pj_name) {
    }

    public void writeInInstanceShell(Connection conn, String message){
        wiManager.getInstances().get(conn.getUser().getPj().getMap()).writeInShell(message);
    }
    
    public int processCollision(Pj user, String target_name, String gun_id){
        try {
            ItemDBConnector itemDB = new ItemDBConnector();
            Gun gun = itemDB.getGunById(gun_id);
            int damage = fileSystem.doDamage(user, target_name, gun);
            //         DamageAdvisor adv = 
            return damage;
        } catch (SQLException ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public void writeInServerShell(String output) {
        shell.write(output, shell_profile_s);
    }

    public PjDBConnector getDBConPj() {
        return DBConPj;
    }

    public void setDBConPj(PjDBConnector DBConPj) {
        this.DBConPj = DBConPj;
    }

    public MapDBConnector getDBConMap() {
        return DBConMap;
    }

    public void setDBConMap(MapDBConnector DBConMap) {
        this.DBConMap = DBConMap;
    }

    public BattleFileIO getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(BattleFileIO fileSystem) {
        this.fileSystem = fileSystem;
    }

    public PermanentBattleInstancesManager getWiManager() {
        return wiManager;
    }

    public void setWiManager(PermanentBattleInstancesManager wiManager) {
        this.wiManager = wiManager;
    }
}
