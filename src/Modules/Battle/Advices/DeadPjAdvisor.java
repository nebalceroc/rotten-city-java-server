/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modules.Battle.Advices;

import Modules.Battle.Advices.NewPjAdvisor;
import ConnectionManager.Connection;
import Data.OutputMessage;
import Data.Pj;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolás Balcero
 */
public class DeadPjAdvisor implements Runnable {

    private LinkedList<Connection> connections;
    private boolean list_enabled;
    private String pj_name;

    public DeadPjAdvisor(LinkedList<Connection> connections, String dead_pj, boolean enabled) {
        this.connections = connections;
        this.pj_name = dead_pj;
        list_enabled = enabled;
    }

    @Override
    public void run() {
        synchronized (connections) {
            if (!list_enabled) {
                try {
                    connections.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(NewPjAdvisor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            list_enabled = false;
            OutputMessage message = new OutputMessage();
            message.setMessage(generateMessage(pj_name));
            Iterator it = connections.iterator();
            while (it.hasNext()) {
                Connection out = (Connection) it.next();
                out.send(message);
            }
            list_enabled = true;
            connections.notifyAll();
        }
    }

    public String generateMessage(String pj_name) {
        String message = "BATTLE/DATA/DEAD_PLAYER/";
        message = message.concat(pj_name);
        return message;
    }
}
