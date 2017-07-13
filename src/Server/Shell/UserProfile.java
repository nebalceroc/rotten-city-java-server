/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Shell;

/**
 *
 * @author Nicolas
 */
public class UserProfile extends ShellProfile{
     private String user_name;
     private String ip;
    
    public UserProfile(String ip){
        super();
        setProfile("user");
        setIp(ip);
        setUser_name("?");
    }
    
    @Override
    public void write(String input, Shell shell) {
        shell.getShell_file_sys().writeUserLog(user_name, input);
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
