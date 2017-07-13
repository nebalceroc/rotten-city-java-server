/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileIO;

import Data.Pj;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolás Balcero
 */
public class PjFileIO {

    public void createPjFile(Pj pj) {
        File pj_file = new File("src/ServerIO/Pjs/" + pj.getName() + ".lsd");
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            fichero = new FileWriter(pj_file, false);//false para q borre el contenido
            writer = new PrintWriter(fichero);
            writer.print("name@" + pj.getName() + "\r\n"
                    + "school@" + pj.getSchool() + "\r\n"
                    + "exp@" + pj.getExp() + "\r\n"
                    + "lvl@" + pj.getLvl() + "\r\n"
                    + "stats@" + pj.getStamina() + "#" + pj.getStrength() + "#" + pj.getIntelect() + "#" + pj.getSpeed() + "\r\n"
                    + "avatar@" + pj.getAvatar_string() + "\r\n"
                    + "position@" + pj.getPosition() + "\r\n"
                    + "life@" + pj.getLife() + "/" + pj.getLife() + "\r\n"
                    + "gear@description \r\n"
                    + "HEAD@" + pj.getPj_gear_items().get("HEAD") + "\r\n"
                    + "CHEST@" + pj.getPj_gear_items().get("CHEST") + "\r\n"
                    + "LEGS@" + pj.getPj_gear_items().get("LEGS") + "\r\n"
                    + "FOOT@" + pj.getPj_gear_items().get("FOOT") + "\r\n"
                    + "GUN1@" + pj.getPj_gear_items().get("GUN1") + "\r\n"
                    + "GUN2@" + pj.getPj_gear_items().get("GUN2") + "\r\n"
                    + "AUXGUN@" + pj.getPj_gear_items().get("AUXGUN") + "\r\n");
            pj.setPj_file(pj_file);
        } catch (IOException ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
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
