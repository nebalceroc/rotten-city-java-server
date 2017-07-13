package Modules.Battle.Permanent;

import ConnectionManager.Connection;
import DB.MapDBConnector;
import Data.Pj;
import FileIO.BattleFileIO;
import Modules.ModuleTool;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import java.io.File;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class PermanentBattleInstancesManager {

    private BattleFileIO fileSystem;
    private Shell shell;
    private ModuleTool tools;
    private ServerProfile shell_profile;
    private LinkedList init_maps_ids; //mapas del mundo 2D. 
    private HashMap<String,PermanentBattleWorldInstance> instances = new HashMap<>();
    
  
    public PermanentBattleInstancesManager(Shell shell, ServerProfile shell_profile, BattleFileIO fileSystem, ModuleTool tools) {
        this.shell = shell;
        this.tools=tools;
        this.shell_profile=shell_profile;
        this.fileSystem = fileSystem;
        this.init();
    }


    private void init() {
        MapDBConnector dBConnector = new MapDBConnector();
        PermanentBattleWorldInstance instance;
        try {

            setInit_maps_ids(dBConnector.getMapIdArray());
            for (int x = 0; x < getInit_maps_ids().size(); x++) {
                instance = new PermanentBattleWorldInstance(getFileSystem().createInstanceFile(getInit_maps_ids().get(x).toString(), dBConnector.getMapLvl(getInit_maps_ids().get(x).toString())), getInit_maps_ids().get(x).toString(), getFileSystem(), shell, tools);
                Thread instanceT = new Thread(instance);
                instanceT.start();
                getInstances().put(instance.getMap_id(), instance);
                writeInShell("Instancia: "+getInit_maps_ids().get(x).toString() +" running...");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PermanentBattleInstancesManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }

    }
    
    public void submitPJ(String map_id, Pj pj, Connection conn){
        getFileSystem().submitPj(map_id, pj);
        PermanentBattleWorldInstance instance = getInstances().get(map_id);
        instance.includeNewPj(pj,conn);
    }
    
    public void removePj(Pj pj, Connection conn){
         getInstances().get(pj.getMap()).removeOnlinePj(pj, conn);
    }
    
    public boolean correctLvl(String map_id, Pj pj){
        return(getFileSystem().get2DInstanceLvl(map_id)<=pj.getLvl());        
    }

    public void writeInShell(String output) {
        getShell().write(output, shell_profile);
    }

    /**
     * @return the fileSystem
     */
    public BattleFileIO getFileSystem() {
        return fileSystem;
    }

    /**
     * @param fileSystem the fileSystem to set
     */
    public void setFileSystem(BattleFileIO fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * @return the maps_id
     */
    public LinkedList getInit_maps_ids() {
        return init_maps_ids;
    }

    /**
     * @param maps_id the maps_id to set
     */
    public void setInit_maps_ids(LinkedList init_maps_ids) {
        this.init_maps_ids = init_maps_ids;
    }

    /**
     * @return the instances
     */
    public HashMap<String,PermanentBattleWorldInstance> getInstances() {
        return instances;
    }

    /**
     * @param instances the instances to set
     */
    public void setInstances(HashMap<String,PermanentBattleWorldInstance> instances) {
        this.setInstances(instances);
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


}
