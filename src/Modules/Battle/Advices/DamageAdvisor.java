
package Modules.Battle.Advices;

import ConnectionManager.Connection;
import Data.OutputMessage;
import Data.Pj;
import FileIO.BattleFileIO;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Nicolás Balcero
 */
public class DamageAdvisor implements Runnable{
    
    private Connection targetConn;    
    private int damage;
    
    public DamageAdvisor(Connection conn, int damage){
        this.damage = damage;
        targetConn = conn;
    }
    
    @Override
    public void run() {
        OutputMessage message = new OutputMessage();
        message.setMessage(generateMessage(damage));
        targetConn.send(message);        
       }

    public String generateMessage(int damage) {
        String message = "BATTLE/DATA/COLLISION/";
        message = message+damage;
        return message;
    }

}
