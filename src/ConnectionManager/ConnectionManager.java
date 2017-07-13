
package ConnectionManager;

import FileIO.ConnectionFileIO;
import Modules.ModuleTool;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas Balcero
 */
public class ConnectionManager implements Runnable {

    private ConnectionDirectory directory = new ConnectionDirectory();
    private ConnectionFileIO con_file;
    private long conections_cont;
    private ServerSocket s_server;
    private boolean manager_enabled;
    private int port;
    private ServerProfile shell_profile;
    private Shell shell;
    private ModuleTool modulosRef;    

    public ConnectionManager(int port, Shell shell,ServerProfile shell_profile, ModuleTool mod) {
        conections_cont = 0;
        manager_enabled = false;
        this.port = port;
        this.shell = shell;
        modulosRef = mod;
        con_file = new ConnectionFileIO(shell,shell_profile);
        this.shell_profile = shell_profile;
        writeInShell("Manager creado correctamente");
    }

    public boolean iniciar_servicio() {
        try {
            this.setS_server(new ServerSocket(getPort()));
            this.setManager_enabled(true);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            writeInShell("Fallo al iniciar Servicio \n"+ex);
            return false;
        }
        writeInShell("Inició Servicio");
        return true;
    }

    @Override
    public void run() {
        Socket indexSocket;
        while (isManager_enabled()) {
            try {
                indexSocket = getS_server().accept();
                writeInShell("Conexion entrante de : "+indexSocket.getInetAddress().toString());
                addUnknownConnection(indexSocket);                
            } catch (IOException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }
        }
    }
    
    public void addIdentifiedConnection(Connection conn){
        getDirectory().addConnection(conn.getUser().getUser_name(), conn);
    }
    
    public void addConnectionToInstanceDirectory(Connection conn){
        getDirectory().addConnectionToInstanceDirectory(conn);
    }
    
    public void removeIdentifiedConnection(Connection conn){
        getDirectory().removeConnection(conn.getUser().getUser_name());
    }
    
    public void removeConnectionInInstanceDirectory(Connection conn){
        getDirectory().removeConnectionInInstanceDirectory(conn);
    }
    
    private void addUnknownConnection(Socket socket){
        try {
            Socket con_socket = new Socket();
            con_socket = socket;
            Connection con = new Connection(socket , getModulosRef(), this, getShell());
            Thread connectionT = new Thread(con);
            connectionT.start();
            writeInShell("Conection creada y añadida al sistema");
            setConections_cont(getConections_cont() + 1);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            writeInShell("Error al crear conexion \n"
                    + ex);
        }
    }
        
    private void writeInShell(String output){
        getShell().write(output, getShell_profile());
    }            

    public long getConections_cont() {
        return conections_cont;
    }

    public void setConections_cont(long conections_cont) {
        this.conections_cont = conections_cont;
    }

    public ServerSocket getS_server() {
        return s_server;
    }

    public void setS_server(ServerSocket s_server) {
        this.s_server = s_server;
    }

    public boolean isManager_enabled() {
        return manager_enabled;
    }

    public void setManager_enabled(boolean manager_enabled) {
        this.manager_enabled = manager_enabled;
    }

    public ConnectionFileIO getCon_file() {
        return con_file;
    }

    public void setCon_file(ConnectionFileIO con_file) {
        this.con_file = con_file;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ModuleTool getModulosRef() {
        return modulosRef;
    }

    public void setModulosRef(ModuleTool modulosRef) {
        this.modulosRef = modulosRef;
    }

    /**
     * @return the directory
     */
    public ConnectionDirectory getDirectory() {
        return directory;
    }

    /**
     * @param directory the directory to set
     */
    public void setDirectory(ConnectionDirectory directory) {
        this.directory = directory;
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
    
}
