/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modules.Battle.Temporal;

import ConnectionManager.Connection;
import Data.Pj;
import FileIO.BattleFileIO;
import Modules.Battle.Advices.DeadPjAdvisor;
import Modules.Battle.Permanent.PermanentBattleWorldInstance;
import Modules.Battle.Advices.DcPjAdvisor;
import Modules.Chat.InstanceChatRoom;
import Modules.ModuleTool;
import Server.Shell.InstanceProfile;
import Server.Shell.Shell;
import java.io.File;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolás Balcero
 */
public class TemporalBattleInstance implements Runnable{
    private String map_id;
    private File index_file;
    private BattleFileIO fileSystem;
    private LinkedList<Connection> conexiones;
    private boolean list_enabled;
    private Shell shell;
    private ModuleTool tools;
    private InstanceProfile shell_profile;
    private InstanceChatRoom chat_room;
    private BattleRulesPack brp;

    public TemporalBattleInstance(File file, String map_id, BattleFileIO fileSystem, Shell shell, ModuleTool tools, BattleRulesPack brp) {
        this.index_file = file;
        this.map_id = map_id;
        this.fileSystem = fileSystem;
        conexiones = new LinkedList<Connection>();
        list_enabled = true;
        this.tools = tools;
        chat_room = tools.getModulo_chat().getChatRoomManager().initInstanceChatRoom(conexiones, map_id);
        this.shell = shell;
        shell_profile = new InstanceProfile(map_id);
        shell.addInstance(map_id);
        shell.getShell_file_sys().initInstanceLog(map_id);
        writeInShell("Instancia inicializada correctamente...");
        this.brp = brp;
    }
    
    public void deadPj(String pj_name){
        DeadPjAdvisor dead_adv = new DeadPjAdvisor(conexiones, pj_name, list_enabled);
        Thread adv = new Thread(dead_adv);
        adv.start();
    }

    public void includeNewPj(Pj pj, Connection conn) {
        writeInShell("Nuevo jugador: " + pj.getName());
        this.getFileSystem().indexPlayer(pj.getName(), getMap_id());
//        NewPjAdvisor ad = new NewPjAdvisor(getConexiones(), pj, list_enabled);
//        Thread advisor = new Thread(ad);
//        advisor.start();
//        writeInShell("Advisor para(new): " + pj.getName() + " corriendo...");
        synchronized (conexiones) {
            if (!list_enabled) {
                try {
                    conexiones.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PermanentBattleWorldInstance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            list_enabled = false;
            conexiones.add(conn);
            list_enabled = true;
            conexiones.notifyAll();
        }
    }

    public void removeOnlinePj(Pj pj, Connection conn) {
        writeInShell("Jugador sale de la instancia: " + pj.getName());
        synchronized (conexiones) {
            if (!list_enabled) {
                try {
                    conexiones.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PermanentBattleWorldInstance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            list_enabled = false;
            conexiones.remove(conn);
            list_enabled = true;
            conexiones.notifyAll();
        }
        this.getFileSystem().removePj(pj, getIndex_file());
        DcPjAdvisor ad = new DcPjAdvisor(getConexiones(), pj, list_enabled);
        Thread advisor = new Thread(ad);
        advisor.start();
        writeInShell("Advisor para(dc): " + pj.getName() + " corriendo...");
    }

    public void writeInShell(String output) {
        getShell().write(output, shell_profile);
    }

    @Override
    public void run() {
        /*aca se va el codigo correspondiente al proceso que se encarga de manejar 
        todo lo que tiene que ver con la instancia como tal, por ejemplo:
        ciclos que actualicen la posicion de los "mobs", IA de los mismos, administrar 
        los objetos del mapa etc
        */
    }

    public String getMap_id() {
        return map_id;
    }

    /**
     * @param map_id the map_id to set
     */
    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    /**
     * @return the index_file
     */
    public File getIndex_file() {
        return index_file;
    }

    /**
     * @param index_file the index_file to set
     */
    public void setIndex_file(File index_file) {
        this.index_file = index_file;
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
     * @return the conexiones
     */
    public LinkedList<Connection> getConexiones() {
        return conexiones;
    }

    /**
     * @param conexiones the conexiones to set
     */
    public void setConexiones(LinkedList<Connection> conexiones) {
        this.conexiones = conexiones;
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
    public InstanceProfile getShell_profile() {
        return shell_profile;
    }

    /**
     * @param shell_profile the shell_profile to set
     */
    public void setShell_profile(InstanceProfile shell_profile) {
        this.shell_profile = shell_profile;
    }
}
