package Server.Shell;

/**
 *
 * @author Nicolas
 */
public class ServerProfile extends ShellProfile{

    public ServerProfile(){
        super();
        setProfile("server");
    }

    @Override
    public void write(String input, Shell shell) {
        shell.getShell_file_sys().writeServerLog(">>"+input);
    }
}
