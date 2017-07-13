package Modules.Items;

import ConnectionManager.Connection;
import Data.OutputMessage;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class M_Items {

    public OutputMessage doTask(String incoming, Connection conn) {
        String[] buffer = incoming.split("/");
        OutputMessage message = new OutputMessage();
        switch (buffer[1]) {
            case "DATA":
                switch (buffer[2]) {
                    case "ITEMS_DATA_REQUEST":
                        try {
                            message.setAnswer("ITEMS_DATA_PACKED");
                            message.setMessage(conn.getUser().getPj().getPjItemsData());
                        } catch (SQLException ex) {
                            Logger.getLogger(M_Items.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "EQUIP_ITEM":
                        break;
                }
                break;
        }
        return message;
    }
}
