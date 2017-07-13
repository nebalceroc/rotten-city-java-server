package Modules.Battle.Advices;

import ConnectionManager.Connection;
import Data.OutputMessage;
import Data.Pj;
import FileIO.BattleFileIO;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class NewPjAdvisor implements Runnable {

    private LinkedList<Connection> connections;
    private boolean list_enabled;
    private Pj new_pj;

    public NewPjAdvisor(LinkedList<Connection> connections, Pj new_pj,boolean enabled) {
        this.connections = connections;
        this.new_pj = new_pj;
        list_enabled=enabled;
    }

    @Override
    public void run() {
        synchronized (connections) {
            if(!list_enabled){
                try {
                    connections.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(NewPjAdvisor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            list_enabled=false;
            OutputMessage message = new OutputMessage();
            message.setMessage(generateMessage(new_pj));
            Iterator it = connections.iterator();
            while (it.hasNext()) {
                Connection out = (Connection) it.next();
                if (!out.getUser().getPj().equals(new_pj)) {
                    out.send(message);
                }
            }
            list_enabled=true;
            connections.notifyAll();
        }
    }

    public String generateMessage(Pj new_pj) {
        String message = "BATTLE/DATA/NEW_PLAYER/";
        message = message.concat(new_pj.getPjData());
        return message;
    }
}
