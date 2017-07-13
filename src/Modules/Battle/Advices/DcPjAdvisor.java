package Modules.Battle.Advices;

import ConnectionManager.Connection;
import Data.OutputMessage;
import Data.Pj;
import FileIO.BattleFileIO;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class DcPjAdvisor implements Runnable{
    
    private LinkedList<Connection> conexiones;
        private boolean list_enabled;
    private Pj new_pj;

    
    public DcPjAdvisor(LinkedList<Connection> conexiones, Pj new_pj, boolean enabled) {
        this.conexiones = conexiones;
        this.new_pj = new_pj;
        list_enabled=enabled;
    }

    @Override
    public void run() {
        synchronized(conexiones){
        if(!list_enabled){
                try {
                    conexiones.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DcPjAdvisor.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        list_enabled=false;
        OutputMessage message = new OutputMessage();
        message.setMessage(generateMessage(new_pj));
        Iterator it = conexiones.iterator();
        while (it.hasNext()) {
            Connection out = (Connection) it.next();
            if (!out.getUser().getPj().equals(new_pj)) {
                out.send(message);
            }
        }
        list_enabled=true;
        conexiones.notifyAll();
    }
    }
    
    public String generateMessage(Pj new_pj) {
        String message = "BATTLE/DATA/DC_PLAYER/";
        message = message.concat(new_pj.getName());
        return message;
    }

    public LinkedList<Connection> getConexiones() {
        return conexiones;
    }

    public void setConexiones(LinkedList<Connection> conexiones) {
        this.conexiones = conexiones;
    }
}
