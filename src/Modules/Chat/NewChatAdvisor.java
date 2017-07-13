package Modules.Chat;

import ConnectionManager.Connection;
import Data.OutputMessage;
import Data.Pj;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Nicolás Balcero
 */
public class NewChatAdvisor implements Runnable{
    
    private LinkedList<Connection> conexiones;
    private Pj emisor;
    private String text;


    public NewChatAdvisor(LinkedList<Connection> conexiones, String message, Pj emisor) {
        this.conexiones = conexiones;
        this.text = message;
        this.emisor = emisor;
    }

    @Override
    public void run() {
        OutputMessage message = new OutputMessage();
        message.setMessage(generateMessage(text));
        LinkedList<Connection> aux_con = new LinkedList<Connection>();
        aux_con=conexiones;
        Iterator it = aux_con.iterator();
        while (it.hasNext()) {
            Connection out = (Connection) it.next();
            if (!out.getUser().getPj().equals(emisor)) {
                out.send(message);
            }
        }
       }

    public String generateMessage(String chat_msg) {
        return "CHAT/INSTANCE_CHAT/"+chat_msg;
    }

    public LinkedList<Connection> getConexiones() {
        return conexiones;
    }

    public void setConexiones(LinkedList<Connection> conexiones) {
        this.conexiones = conexiones;
    }

}
