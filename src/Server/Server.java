package Server;

import ConnectionManager.ConnectionManager;
import FileIO.FileIO;
import Modules.*;
import Modules.Battle.M_Battle;
import Modules.Chat.M_Chat;
import Modules.Login.M_Login;
import Modules.Items.M_Items;
import Modules.Menu.M_Menu;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Nicolas Balcero
 */
public class Server {

    private int port = 8888;    //Puerto donde corre el servidor           
    private ConnectionManager manager;
    private ModuleTool modulos;
    private FileIO fileIO;
    private Shell shell;
    private ServerProfile shell_profile;

    public Server() {
        shell = new Shell();
        shell_profile = new ServerProfile();
        fileIO = new FileIO();
        fileIO.createFolders();
        writeInShell("Ficheros creados");
        M_Battle battle_module;
        M_Login login_module;
        M_World world_module;
        M_Menu menu_module;
        M_Chat chat_module;
        M_Items items_module;
        modulos = new ModuleTool();
        login_module = new M_Login(getShell(), getShell_profile());
        modulos.setModulo_login(login_module);
        writeInShell("Login Module creado");
        menu_module = new M_Menu(getShell(), getShell_profile());
        modulos.setModulo_menu(menu_module);
        writeInShell("Menu Module creado");
        chat_module = new M_Chat(getShell(), getShell_profile());
        modulos.setModulo_chat(chat_module);
        writeInShell("Chat Module creado");
        world_module = new M_World();
        modulos.setModulo_world(world_module);
        writeInShell("World Module creado");
        battle_module = new M_Battle(getShell(), getShell_profile(), modulos);
        modulos.setModulo_combate(battle_module);
        writeInShell("Battle Module creado");
        items_module = new M_Items();
        modulos.setModulo_items(items_module);
        writeInShell("Items Module creado");
        writeInShell("Herramienta de Modulos configurado");
        manager = new ConnectionManager(getPort(), getShell(), getShell_profile(), getModulos());
        writeInShell("Conection Manager creado");
        if (manager.iniciar_servicio()) {
            writeInShell("Manager inicializado");
            Thread managerT = new Thread(getManager());
            managerT.start();
        } else {
            writeInShell("Error[No se pudo iniciar el servicio del Manager] \n"
                    + "el servidor se cerrara en 10 segundos...");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        splash();
        try {
            UIManager.setLookAndFeel(new HiFiLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
        }
        Server server = new Server();
    }

    private void writeInShell(String output) {
        this.getShell().write(output, getShell_profile());
    }
    //Metodo para mostrar el "splash" antes de que se muestre la GUI del server.

    public static void splash() {
        try {
            JFrame splash = new JFrame();
            JLabel image = new JLabel();
            splash.setUndecorated(true);
            image.setIcon(new ImageIcon("src/Images/splash.png"));
            splash.getContentPane().add(image);
            splash.setSize(new Dimension(753, 352));
            splash.setLocationRelativeTo(null);
            splash.setVisible(true);
            Thread.sleep(2500);
            splash.dispose();
        } catch (InterruptedException ex) {
            Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConnectionManager getManager() {
        return manager;
    }

    public void setManager(ConnectionManager manager) {
        this.manager = manager;
    }

    public ModuleTool getModulos() {
        return modulos;
    }

    public void setModulos(ModuleTool modulos) {
        this.modulos = modulos;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public FileIO getFileIO() {
        return fileIO;
    }

    public void setFileIO(FileIO fileIO) {
        this.fileIO = fileIO;
    }

    public Shell getShell() {
        return shell;
    }

    public void setShell(Shell shell) {
        this.shell = shell;
    }

    public ServerProfile getShell_profile() {
        return shell_profile;
    }

    public void setShell_profile(ServerProfile shell_profile) {
        this.shell_profile = shell_profile;
    }
}
