/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules.Battle.Temporal;

import ConnectionManager.Connection;
import DB.MapDBConnector;
import Data.Pj;
import FileIO.BattleFileIO;
import Modules.ModuleTool;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolás Balcero
 */
public class TemporalBattleInstancesManager implements Runnable{
    private BattleFileIO fileSystem;
    private Shell shell;
    private ModuleTool tools;
    private ServerProfile shell_profile;
    private HashMap<String,TemporalBattleInstance> instances = new HashMap<>();
    
  
    public TemporalBattleInstancesManager (Shell shell, ServerProfile shell_profile, BattleFileIO fileSystem, ModuleTool tools) {
        this.shell = shell;
        this.tools=tools;
        this.shell_profile=shell_profile;
        this.fileSystem = fileSystem;
        this.init();
    }


    private void init() {
        MapDBConnector dBConnector = new MapDBConnector();
        TemporalBattleInstance instance;        
    }
    
    public void createInstance(BattleRulesPack brp, String instance_id){
        try {
            MapDBConnector dBConnector = new MapDBConnector();
            TemporalBattleInstance instance = new TemporalBattleInstance(getFileSystem().createInstanceFile(instance_id, dBConnector.getMapLvl(instance_id)), instance_id, getFileSystem(), shell, tools, brp);
            instances.put(instance_id, instance);
            Thread t = new Thread(instance);
            t.start();
        } catch (SQLException ex) {
            Logger.getLogger(TemporalBattleInstancesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void submitPJ(String map_id, Pj pj, Connection conn){
        getFileSystem().submitPj(map_id, pj);
        TemporalBattleInstance instance = getInstances().get(map_id);
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

    public HashMap<String,TemporalBattleInstance> getInstances() {
        return instances;
    }

    /**
     * @param instances the instances to set
     */
    public void setInstances(HashMap<String,TemporalBattleInstance> instances) {
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

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
