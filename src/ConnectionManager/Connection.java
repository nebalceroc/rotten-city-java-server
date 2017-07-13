package ConnectionManager;

import Data.OutputMessage;
import Data.Pj;
import Data.User;
import Modules.ModuleTool;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;
import FileIO.ChatFileIO;
import Modules.Chat.M_Chat;
import Server.Shell.Shell;
import Server.Shell.UserProfile;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Nicolas
 */
public class Connection implements Runnable {

    private ConnectionManager boss;
    private String conn_ip;
    private Socket socket;
    private BufferedReader input;
    private PrintStream output;
    private String moduloActual;
    private ModuleTool modulos;
    private User user = null;
    private Shell shell;
    private UserProfile shell_profile;

    public Connection(Socket socket, ModuleTool mod, ConnectionManager boss, Shell shell) throws IOException {
        this.boss = boss;
        this.socket = socket;
        this.conn_ip = socket.getInetAddress().toString();
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintStream(socket.getOutputStream());
        modulos = mod;
        moduloActual = "new";
        this.shell = shell;
        shell_profile = new UserProfile(conn_ip);
        output.println("<cross-domain-policy><allow-access-from domain='*' to-ports='*'/></cross-domain-policy>");
        output.write(0);
    }

    @Override
    public void run() {

        Calendar calendario = Calendar.getInstance();
        boolean read = true;
        String incoming;
        setConn_ip(getSocket().getInetAddress().toString());
        getBoss().getCon_file().addConnection(this);
        OutputMessage message = new OutputMessage();
        while (read) {
            try {
                incoming = getInput().readLine();
                incoming = incoming.trim();
                writeInShell(incoming);
                setModuloActual(getModulos().identifyModule(incoming));
                switch (getModuloActual()) {
                    //CHAT///////////////////////////////////
                    case "CHAT":
                        message = getModulos().getModulo_chat().doTask(incoming, this);
                        break;
                    //LOGIN//////////////////////////////////
                    case "LOGIN":
                        message = getModulos().getModulo_login().doTask(incoming);
                        switch (message.getAnswer()) {
                            case "NULL":
                                message.setMessage("LOGIN/LOGIN_FAIL");
                                break;
                            case "ACCOUNT_DISABLED":
                                message.setMessage("LOGIN/ACCOUNT_DISABLED");
                                break;
                            case "CREATED":
                                message.setMessage("LOGIN/USER_CREATED");
                                break;
                            case "USER_NAME_CLON":
                                message.setMessage("LOGIN/USER_NAME_CLON");
                                break;
                            case "USER_ALREADY_CONECTED":
                                message.setMessage("LOGIN/ALREADY_CONECTED");
                                break;
                            default:
                                message.setMessage("LOGIN/LOGIN_SUCCESS");
                                identifyUser(message.getAnswer());
                                writeInShell("login success...");
                                shell.getShell_file_sys().initUserLog(getUser().getUser_name());
                                shell.addConn(getUser().getUser_name() + "#" + getUser().getIp());
                                break;
                        }
                        send(message);
                        break;
                    //MENU///////////////////////////////    
                    case "MENU":
                        message = getModulos().getModulo_menu().doTask(incoming, getUser());
                        switch (message.getAnswer()) {
                            case "NULL_PJ":
                                message.setMessage("MENU/DATA/NO_PJ");
                                writeInShell("no pj...");
                                break;
                            case "PJ_SELECTED":
                                pjSelection(message.getMessage());
                                OutputMessage internalMessage = getModulos().getModulo_combate().doTask("BATTLE/INIT/" + getUser().getPj().getMap(), this);
                                message.setMessage("BATTLE/INIT/" + internalMessage.getAnswer());
                                writeInShell("pj selected and send to instance...");
                                break;
                            case "PJ_CREATED":
                                message.setMessage("MENU/PJ_CREATION/ACCEPT");
                                writeInShell("new pj created");
                                break;
                            case "NAME_CLONED":
                                message.setMessage("MENU/PJ_CREATON/DECLINE");
                                writeInShell("error creating new pj");
                                break;
                            case "NO_PJ":
                                message.setMessage("MENU/DATA/DECLINE");
                                break;
                            default:
                                message.setMessage(message.getAnswer());
                                writeInShell("all pj data send...");
                                break;
                        }
                        send(message);
                        break;
                    //BATTLE/////////////////////////////
                    case "BATTLE":
                        message = getModulos().getModulo_combate().doTask(incoming, this);
                        switch (message.getAnswer()) {
                            case "ACCEPT":
                                message.setMessage("BATTLE/INIT/ACCEPT");
                                send(message);
                                writeInShell("instance accept user...");
                                break;
                            case "DECLINE":
                                message.setMessage("BATTLE/INIT/DECLINE");
                                send(message);
                                writeInShell("instance decline user...");
                                break;
                            case "MAP_INIT":
                                message.setMessage("BATTLE/DATA/INIT/" + message.getMessage());
                                send(message);
                                writeInShell("instance init data...");
                                break;
                            case "MAP_UPDATE":
                                message.setMessage("BATTLE/DATA/UPDATE/" + message.getMessage());
                                send(message);
                                break;
                        }
                        break;
                    //////////ITEMS/////////////////////////////
                    case "ITEMS":
                        message = getModulos().getModulo_items().doTask(incoming,this);
                        switch (message.getAnswer()) {
                            case "ITEMS_DATA_PACKED":
                                send(message);                                
                                writeInShell(getUser().getPj().getName() +" item data send");
                                break;
                        }
                        break;
                }

            } catch (Exception ex) {
                //1. NullPonterException al intentar idetificar modulo de una cadena null.
                //Logger.getLogger(Conection.class.getName()).log(Level.SEVERE, null, ex);
                if (getUser() != null) {
                    getModulos().getModulo_login().removeDisconectedPLayer(getUser().getUser_name());
                    getBoss().getCon_file().removeConnection(this);
                    if (getUser().getPj() != null) {
                        getModulos().getModulo_combate().removeDisconectedPj(getUser().getPj(), this);
//                        if (getUser().getPj().getPj_file().delete()) {
//                                writeInShell("Pj borrado");
//                        }
                    }
                }
                read = false;
            }
        }
    }

    public void send(OutputMessage message) {
        getOutput().println(message.getMessage());
        getOutput().write(0);
        writeInShell(message.getMessage());
    }

    public void identifyUser(String values) {
        this.setUser(new User(values));
        getUser().setIp(getConn_ip());
        boss.addIdentifiedConnection(this);
    }
    
    public void pjSelection(String values){
        this.getUser().setPj(new Pj(values));
    }

    private void writeInShell(String output) {
        if (!shell_profile.getUser_name().equals("?")) {
            shell.write(output, shell_profile);
        }
    }

    @Override
    public String toString() {
        return getConn_ip();
    }

    public String getConn_ip() {
        return conn_ip;
    }

    public void setConn_ip(String conn_ip) {
        this.conn_ip = conn_ip;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getInput() {
        return input;
    }

    public void setInput(BufferedReader input) {
        this.input = input;
    }

    public PrintStream getOutput() {
        return output;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }

    public String getModuloActual() {
        return moduloActual;
    }

    public void setModulActual(String module) {
        this.setModuloActual(module);
    }

    /**
     * @return the boss
     */
    public ConnectionManager getBoss() {
        return boss;
    }

    /**
     * @param boss the boss to set
     */
    public void setBoss(ConnectionManager boss) {
        this.boss = boss;
    }

    /**
     * @param moduloActual the moduloActual to set
     */
    public void setModuloActual(String moduloActual) {
        this.moduloActual = moduloActual;
    }

    /**
     * @return the modulos
     */
    public ModuleTool getModulos() {
        return modulos;
    }

    /**
     * @param modulos the modulos to set
     */
    public void setModulos(ModuleTool modulos) {
        this.modulos = modulos;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
        shell_profile.setUser_name(user.getUser_name());
    }
}
