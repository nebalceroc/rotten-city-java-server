package Modules.Battle.Permanent;

import ConnectionManager.Connection;
import Data.Pj;
import FileIO.BattleFileIO;
import Modules.Battle.Advices.DeadPjAdvisor;
import Modules.Battle.Advices.NewPjAdvisor;
import Modules.Battle.Advices.DcPjAdvisor;
import Modules.Battle.Advices.RespawnAdvice;
import Modules.Chat.ChatRoom;
import Modules.Chat.InstanceChatRoom;
import Modules.ModuleTool;
import Server.Shell.InstanceProfile;
import Server.Shell.Shell;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class PermanentBattleWorldInstance implements Runnable {

    private String map_id;
    private File index_file;
    private BattleFileIO fileSystem;
    private LinkedList<Connection> conexiones;
    private boolean list_enabled;
    private Shell shell;
    private ModuleTool tools;
    private InstanceProfile shell_profile;
    private InstanceChatRoom chat_room;

    public PermanentBattleWorldInstance(File file, String map_id, BattleFileIO fileSystem, Shell shell, ModuleTool tools) {
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
    }
    
    public void deadPj(String pj_name){
        DeadPjAdvisor dead_adv = new DeadPjAdvisor(getConexiones(), pj_name, isList_enabled());
        Thread adv = new Thread(dead_adv);
        adv.start();
        RespawnAdvice spawn_adv = new RespawnAdvice(getConexiones(), pj_name, isList_enabled());
        Thread advS = new Thread(spawn_adv);
        advS.start();
    }

    public void includeNewPj(Pj pj, Connection conn) {
        writeInShell("Nuevo jugador: " + pj.getName());
        this.getFileSystem().indexPlayer(pj.getName(), getMap_id());
        NewPjAdvisor ad = new NewPjAdvisor(getConexiones(), pj, isList_enabled());
        Thread advisor = new Thread(ad);
        advisor.start();
        writeInShell("Advisor para(new): " + pj.getName() + " corriendo...");
        synchronized (getConexiones()) {
            if (!isList_enabled()) {
                try {
                    getConexiones().wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PermanentBattleWorldInstance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            setList_enabled(false);
            getConexiones().add(conn);
            setList_enabled(true);
            getConexiones().notifyAll();
        }
    }

    public void removeOnlinePj(Pj pj, Connection conn) {
        writeInShell("Jugador sale de la instancia: " + pj.getName());
        synchronized (getConexiones()) {
            if (!isList_enabled()) {
                try {
                    getConexiones().wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PermanentBattleWorldInstance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            setList_enabled(false);
            getConexiones().remove(conn);
            setList_enabled(true);
            getConexiones().notifyAll();
        }
        this.getFileSystem().removePj(pj, getIndex_file());
        DcPjAdvisor ad = new DcPjAdvisor(getConexiones(), pj, isList_enabled());
        Thread advisor = new Thread(ad);
        advisor.start();
        writeInShell("Advisor para(dc): " + pj.getName() + " corriendo...");
    }

    public void writeInShell(String output) {
        getShell().write(output, getShell_profile());
    }

    @Override
    public void run() {
        /*aca se va el codigo correspondiente al proceso que se encarga de manejar 
        todo lo que tiene que ver con la instancia como tal, por ejemplo:
        ciclos que actualicen la posicion de los "mobs", IA de los mismos, administrar 
        los objetos del mapa
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
        this.setConexiones(conexiones);
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

    /**
     * @return the list_enabled
     */
    public boolean isList_enabled() {
        return list_enabled;
    }

    /**
     * @param list_enabled the list_enabled to set
     */
    public void setList_enabled(boolean list_enabled) {
        this.list_enabled = list_enabled;
    }

    /**
     * @return the tools
     */
    public ModuleTool getTools() {
        return tools;
    }

    /**
     * @param tools the tools to set
     */
    public void setTools(ModuleTool tools) {
        this.tools = tools;
    }

    /**
     * @return the chat_room
     */
    public InstanceChatRoom getChat_room() {
        return chat_room;
    }

    /**
     * @param chat_room the chat_room to set
     */
    public void setChat_room(InstanceChatRoom chat_room) {
        this.chat_room = chat_room;
    }
}
