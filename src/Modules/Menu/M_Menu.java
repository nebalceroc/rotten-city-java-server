package Modules.Menu;

import DB.ItemDBConnector;
import DB.PjDBConnector;
import Data.OutputMessage;
import Data.User;
import Server.Shell.ServerProfile;
import Server.Shell.Shell;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class M_Menu {

    private PjDBConnector pjDBCon = new PjDBConnector();
    private Shell shell;
    private ServerProfile shell_profile_s;

    public M_Menu(Shell shell, ServerProfile shell_profile_s) {
        this.shell = shell;
        this.shell_profile_s = shell_profile_s;
        writeInServerShell("M_Menu creado correctamente...");
    }

    public OutputMessage doTask(String incoming, User user) {
        String[] buffer = incoming.split("/");
        OutputMessage message = new OutputMessage();
        String[] out_buffer = new String[5];
        switch (buffer[1]) {
            case "ALL_PJ_DATA_REQUEST":
                try {
                    if (user.hasPj()) {
                        message.setAnswer("MENU/DATA/ALL_PJ_ANSWER/" + user.packAllPjData());
                    }else{
                        System.out.println("sin pjs");
                        message.setAnswer("NO_PJ");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(M_Menu.class.getName()).log(Level.SEVERE, null, ex);
                    message.setAnswer("NULL_PJ");
                }
                break;
            case "PJ_SELECT":
                message.setAnswer("PJ_SELECTED");
                try {
                    message.setMessage(user.getPjData(buffer[2]));
                } catch (SQLException ex) {
                    Logger.getLogger(M_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "PJ_CREATION":
                try {
                    message.setAnswer(pjDBCon.createPj(buffer[2].split("€")[1].split("#")[0], user.getUser_name(), buffer[2].split("€")[1], buffer[2].split("€")[2], buffer[2].split("€")[0]));
                    System.out.println(message.getAnswer());
                } catch (SQLException ex) {
                    Logger.getLogger(M_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                message.setAnswer("NULL");
                break;
        }
        return message;
    }

    private void writeInServerShell(String output) {
        shell.write(output, shell_profile_s);
    }
}
