package FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Nicolas
 */
public class ChatFileIO{
    
    public File initInstanceChatFile(String instance_id){
        File file;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {

            file = new File("src/ServerIO/Chat/Instances/" + instance_id + ".dmt");
            fichero = new FileWriter(file, false);//false para q borre el contenido
            writer = new PrintWriter(fichero);
            writer.print("ChatLog-Instance: "+instance_id+"\r\n");
            file.deleteOnExit();
            return file;
        } catch (IOException ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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
