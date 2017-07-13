package FileIO;

import ConnectionManager.Connection;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class ConnectionFileIO {

    private Shell shell;
    private ServerProfile shell_profile;
    private File connections;

    public ConnectionFileIO(Shell shell, ServerProfile shell_profile) {
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            this.shell = shell;
            this.shell_profile=shell_profile;
            connections = new File("src/ServerIO/Connections/connections.thc");
            fichero = new FileWriter(getConnections(), false);//false para q borre el contenido
            writer = new PrintWriter(fichero);
            writer.print("CON: \r\n");
            connections.deleteOnExit();
            writeInShell("LoginFileSystemCreated...");
        } catch (IOException ex) {
            Logger.getLogger(LoginFileIO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void addConnection(Connection connection) {
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            fichero = new FileWriter(getConnections(), true);//true para q no borre el contenido
            writer = new PrintWriter(fichero);
            writer.print(connection.toString() + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void removeConnection(Connection con) {
        File file;
        FileReader reader = null;
        BufferedReader buffer;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            file = getConnections();
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            // Lectura del fichero
            String line = "";
            String newLine = "";
            line = buffer.readLine();//solo leemos la primera linea
            while (line != null) {                
                if (!line.equals(con.getConn_ip())) {
                    newLine = newLine + line + "\r\n";
                }
                line = buffer.readLine();//solo leemos la primera linea
            }
            fichero = new FileWriter(getConnections(), false);//false para q borre el contenido
            writer = new PrintWriter(fichero);
            writer.print(newLine);

        } catch (IOException ex) {
            Logger.getLogger(LoginFileIO.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != reader) {
                    reader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void writeInShell(String output) {
        getShell().write(output, getShell_profile());
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
     * @return the connections
     */
    public File getConnections() {
        return connections;
    }

    /**
     * @param connections the connections to set
     */
    public void setConnections(File connections) {
        this.connections = connections;
    }
}
