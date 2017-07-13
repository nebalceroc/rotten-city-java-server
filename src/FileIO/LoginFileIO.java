package FileIO;

import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFileIO {

    private Shell shell;
    private File onlineUsers;
    private ServerProfile shell_profile;

    public LoginFileIO(Shell shell, ServerProfile shell_profile) {
        FileWriter fichero = null;
        PrintWriter writer = null;
        setShell_profile(shell_profile);
        try {
            this.shell = shell;
            onlineUsers = new File("src/ServerIO/Login/online_users.thc");
            onlineUsers.createNewFile();
            fichero = new FileWriter(getOnlineUsers(), false);//false para q borre el contenido
            writer = new PrintWriter(fichero);
            writer.print("USERS:,");
            onlineUsers.deleteOnExit();
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
                if (null != writer) {
                    writer.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void addOnlineUser(String userName) {
        synchronized (getOnlineUsers()) {
            FileWriter fichero = null;
            PrintWriter writer = null;
            try {
                fichero = new FileWriter(getOnlineUsers(), true);//true para q no borre el contenido
                writer = new PrintWriter(fichero);
                writer.print(userName + ",");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // Nuevamente aprovechamos el finally para
                    // asegurarnos que se cierra el fichero.
                    if (null != fichero) {
                        fichero.close();
                    }
                    if (null != writer) {
                        writer.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }


    }
    //true si el usuario ya esta online

    public boolean validateOnlineUser(String userName) {
        // synchronized (onlineUsers) {
        FileReader reader = null;
        BufferedReader buffer = null;
        try {
            reader = new FileReader(getOnlineUsers());
            buffer = new BufferedReader(reader);
            // Lectura del fichero
            String line = "";
            String newLine = "";
            line = buffer.readLine();//solo leemos la primera linea
            String[] users = line.split(",");
            for (int x = 0; x < users.length; x++) {
                if (users[x].equals(userName)) {
                    return true;
                }
            }
            return false;
        } catch (IOException ex) {
            Logger.getLogger(LoginFileIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != reader) {
                    reader.close();
                }
                if (null != buffer) {
                    buffer.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //}
    }

    public void removeOnlineUser(String userName) {
        synchronized (getOnlineUsers()) {
            FileReader reader = null;
            BufferedReader buffer = null;
            FileWriter fichero = null;
            PrintWriter writer = null;
            try {
                // Apertura del fichero y creacion de BufferedReader para poder
                // hacer una lectura comoda (disponer del metodo readLine()).
                reader = new FileReader(getOnlineUsers());
                buffer = new BufferedReader(reader);
                // Lectura del fichero
                String line = "";
                String newLine = "";
                line = buffer.readLine();//solo leemos la primera linea
                String[] users = line.split(",");
                for (int x = 0; x < users.length; x++) {
                    if (!users[x].equals(userName)) {
                        newLine = newLine.concat(users[x] + ",");
                    }
                }
                fichero = new FileWriter(getOnlineUsers(), false);//true para q no borre el contenido
                writer = new PrintWriter(fichero);
                writer.print(newLine);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // Nuevamente aprovechamos el finally para
                    // asegurarnos que se cierra el fichero.
                    if (null != fichero) {
                        fichero.close();
                    }
                    if (null != reader) {
                        reader.close();
                    }
                    if (null != buffer) {
                        buffer.close();
                    }
                    if (null != writer) {
                        writer.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }                
            }
        }





    }

    private void writeInShell(String output) {
        getShell().write(output, shell_profile);
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
     * @return the onlineUsers
     */
    public File getOnlineUsers() {
        return onlineUsers;
    }

    /**
     * @param onlineUsers the onlineUsers to set
     */
    public void setOnlineUsers(File onlineUsers) {
        this.onlineUsers = onlineUsers;
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
