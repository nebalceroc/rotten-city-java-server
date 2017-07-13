package Modules.Login;

import DB.UserDBConnector;
import Data.OutputMessage;
import FileIO.LoginFileIO;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class M_Login {

    private UserDBConnector DBCon = new UserDBConnector();
    private Shell shell;
    private LoginFileIO fileSystem;
    private ServerProfile shell_profile;

    public M_Login(Shell shell, ServerProfile shell_profile) {
        this.shell = shell;
        this.shell_profile = shell_profile;
        fileSystem = new LoginFileIO(shell, shell_profile);
        writeInShell("M_Login creado correctamente ...");
    }

    public OutputMessage doTask(String incoming) {
        String[] buffer = incoming.split("/");
        OutputMessage message = new OutputMessage();
        synchronized (getDBCon()) {
            switch (buffer[1]) {
                case "USER_LOGIN":
                    writeInShell("Validando usuario ..." + buffer[2] + "/" + buffer[3]);
                    try {
                        switch (getDBCon().validarUsuario(buffer[2], buffer[3])) {
                            case "INVALID_LOGIN":
                                writeInShell("Usuario y contraseña incorrectos ..." + buffer[2] + "/" + buffer[3]);
                                message.setAnswer("NULL");
                                break;
                            case "VALID_LOGIN":
                                writeInShell("Usuario y contraseña validos ..." + buffer[2] + "/" + buffer[3]);
                                if (validarUserOnline(buffer[2])) {
                                    message.setAnswer("USER_ALREADY_CONECTED");
                                } else {
                                    getFileSystem().addOnlineUser(buffer[2]);
                                    writeInShell("usuario: " + buffer[2] + " añadido");
                                    message.setAnswer(buffer[2]);
                                }
                                break;
                            case "ACCOUNT_DISABLED":
                                writeInShell("Usuario y contraseña validos ..." + buffer[2] + "/" + buffer[3] + " falta activar la cuenta");
                                message.setAnswer("ACCOUNT_DISABLED");
                                break;
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(M_Login.class.getName()).log(Level.SEVERE, null, ex);
                        writeInShell(ex.toString());
                        message.setAnswer("NULL");
                    }
                    break;
                case "USER_CREATION":
                    writeInShell("Creando usuario ..." + buffer[2] + "/" + buffer[3]);
                    try {
                        if (getDBCon().usuarioExistente(buffer[2])) {
                            writeInShell("Usuario ya existe ..." + buffer[2] + "/" + buffer[3]);
                            message.setAnswer("CLON");
                        } else {
                            writeInShell("Nombre de usuario valido ..." + buffer[2]);
                            getDBCon().crearUsuario(buffer[2], buffer[3], buffer[4]);
                            EmailCreator creator = new EmailCreator();
                            creator.enviar(buffer[4]);
                            message.setAnswer("CREATED");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(M_Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    message.setAnswer("NULL");
                    break;
            }
        }
        return message;
    }

    public boolean validarUserOnline(String user) {
        return getFileSystem().validateOnlineUser(user);
    }

    public void removeDisconectedPLayer(String user) {
        getFileSystem().removeOnlineUser(user);
    }

    private void writeInShell(String output) {
        getShell().write(output, shell_profile);
    }

    public LoginFileIO getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(LoginFileIO fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * @return the DBCon
     */
    public UserDBConnector getDBCon() {
        return DBCon;
    }

    /**
     * @param DBCon the DBCon to set
     */
    public void setDBCon(UserDBConnector DBCon) {
        this.DBCon = DBCon;
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
