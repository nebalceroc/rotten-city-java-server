package FileIO;

import DB.ItemDBConnector;
import DB.PjDBConnector;
import Data.Gun;
import Data.Pj;
import Data.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class BattleFileIO {

    public boolean addExp(int exp, Pj pj) {
        //    synchronized (index) {        
        FileReader reader = null;
        BufferedReader buffer = null;
        FileWriter fichero = null;
        PrintWriter writer = null;
        boolean lvlraise = false;
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            File pj_file = pj.getPj_file();
            reader = new FileReader(pj_file);
            buffer = new BufferedReader(reader);
            // Lectura del fichero
            String line = "";
            String newFile = "";
            while ((line = buffer.readLine()) != null && line.contains("@")) {
                if (line.split("@")[0].equals("exp")) {
                    int oldExp = Integer.parseInt(line.split("@")[1].trim());
                    int newExp = exp + oldExp;
                    newFile = newFile.concat("exp@" + newExp + "\r\n");
                    pj.setExp(newExp);      // este es auxiliar mientras se reorganiza la cuestion de los archivos 

                    if (newExp >= 66.1348 * Math.pow(Math.E, ((pj.getLvl() + 1) / Math.PI))) {
                        String lvlString = buffer.readLine();
                        int currentLvl = Integer.parseInt(lvlString.split("@")[1]) + 1;
                        System.out.println(currentLvl);
                        newFile = newFile.concat("lvl@" + currentLvl + "\r\n");
                        pj.setLvl(currentLvl);
                        lvlraise = true;
                    }
                } else {
                    newFile = newFile.concat(line + "\r\n");
                }

            }
            fichero = new FileWriter(pj_file, false);//true para q no borre el contenido
            writer = new PrintWriter(fichero);
            writer.print(newFile);
            PjDBConnector dbConn = new PjDBConnector();
            dbConn.updatePlayerDesc(pj);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != buffer) {
                    buffer.close();
                }
                if (null != writer) {
                    writer.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return lvlraise;
    }

    public int doDamage(Pj user, String target_name, Gun gun) {
        //    synchronized (index) {        
        FileReader reader = null;
        BufferedReader buffer = null;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            File pj_file = new File("src/ServerIO/Instances/2D/" + user.getMap() + "/" + target_name + ".lsd");
//                        File pj_file = new File("src/ServerIO/Pjs/" + target_name + ".lsd");
            reader = new FileReader(pj_file);
            buffer = new BufferedReader(reader);
            // Lectura del fichero
            String line = "";
            String newFile = "";
            int damage = (int) ((int) gun.getMax_damage() * (1 + (0.01 * user.getIntelect())));
            while ((line = buffer.readLine()) != null && line.contains("@")) {
                if (line.split("@")[0].equals("life")) {
                    int life = Integer.parseInt(line.split("@")[1].trim().split("/")[0]);
                    int newLife = life - damage;
                    if (newLife <= 0) {
                        newFile = newFile.concat("life@" + line.split("@")[1].trim().split("/")[1] + "/" + line.split("@")[1].trim().split("/")[1] + "\r\n");///esta es auxiliar para "revivir"
//                        newFile = newFile.concat("life@0\r\n");///
                        damage = -1;
                    } else {
                        //ACA SE APLICARAN CAMBIOS CUANDO SE QUIERA CAMBIAR LO DEL DAÑO
                        newFile = newFile.concat("life@" + newLife + "/" + line.split("@")[1].trim().split("/")[1] + "\r\n");///
                    }
                } else {
                    newFile = newFile.concat(line + "\r\n");
                }
            }
            fichero = new FileWriter(pj_file, false);//true para q no borre el contenido
            writer = new PrintWriter(fichero);
            writer.print(newFile);
            return damage;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != buffer) {
                    buffer.close();
                }
                if (null != writer) {
                    writer.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }

    public LinkedList<String> getInstanceUsersNames(String map_id, String user_id) {
        FileReader indexReader = null;
        BufferedReader bufferIndex = null;
        LinkedList<String> out = new LinkedList();
        File instance_file = new File("src/ServerIO/Instances/2D/" + map_id + "/index.thc");
        try {
            // synchronized (instance_file) {
            indexReader = new FileReader(instance_file);
            bufferIndex = new BufferedReader(indexReader);
            // Lectura del fichero
            String pjName = "";
            String pjData = "";
            boolean firstRead = true;
            while ((pjName = bufferIndex.readLine()) != null) {
                if (firstRead) {
                    firstRead = false;
                } else {
                    if (!pjName.equals(user_id)) {
                        out.add(pjName);
                    }
                }
            }
            // }
        } catch (IOException ex) {
            Logger.getLogger(LoginFileIO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != indexReader) {
                    indexReader.close();
                }
                if (null != bufferIndex) {
                    bufferIndex.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return out;
    }

    public void removePj(Pj pj, File index) {
        //    synchronized (index) {
        FileReader reader = null;
        BufferedReader buffer = null;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            reader = new FileReader(index);
            buffer = new BufferedReader(reader);
            // Lectura del fichero
            String line = "";
            String newLine = "";
            while ((line = buffer.readLine()) != null) {
                if (!line.equals(pj.getName())) {
                    newLine = newLine.concat(line + "\r\n");
                }
            }
            fichero = new FileWriter(index, false);//true para q no borre el contenido
            writer = new PrintWriter(fichero);
            writer.print(newLine);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
                if (null != reader) {
                    reader.close();
                }
                if (null != buffer) {
                    buffer.close();
                }
                if (null != writer) {
                    writer.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //  }
    }

    public String getPjData(String map_id, String pj_name) {
        File pj_file;
        FileReader pjFileReader = null;
        BufferedReader bufferPj = null;
        String out = "";
        try {
            pj_file = new File("src/ServerIO/Instances/2D/" + map_id + "/" + pj_name + ".lsd");
//            pj_file = pj.getPj_file();
            pjFileReader = new FileReader(pj_file);
            bufferPj = new BufferedReader(pjFileReader);
            String name = bufferPj.readLine().split("@")[1];
            String school = bufferPj.readLine().split("@")[1];
            String exp = bufferPj.readLine().split("@")[1];
            String lvl = bufferPj.readLine().split("@")[1];
            String[] stats = bufferPj.readLine().split("@")[1].split("#");
            String stamina = stats[0];
            String strength = stats[1];
            String speed = stats[2];
            String intelect = stats[3];
            String avatar = bufferPj.readLine().split("@")[1];
            out = out.concat(name + "#" + stamina + "#" + lvl + "#" + strength + "#" + speed + "#" + intelect + "#" + exp + "," + avatar + "%");
        } catch (Exception ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != pjFileReader) {
                    pjFileReader.close();
                }
                if (null != bufferPj) {
                    bufferPj.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return out;
    }

    public String getInstanceUsersData(String map_id, User user) {
        File instance_file = new File("src/ServerIO/Instances/2D/" + map_id + "/index.thc");
        File pj_file;
        FileReader indexReader = null;
        BufferedReader bufferIndex = null;
        String out = "";
        try {
            //synchronized (instance_file) {
            indexReader = new FileReader(instance_file);
            bufferIndex = new BufferedReader(indexReader);

            // Lectura del fichero
            String pjName = "";
            String pjData = "";
            out = "";
            boolean firstRead = true;
            //Primero se verifica que el archivo no este vacio
            while ((pjName = bufferIndex.readLine()) != null) {
                if (firstRead) {
                    //Con este booleano firsRead se salta la primera linea del archivo la cual no es util para este caso
                    firstRead = false;
                } else {
                    if (!pjName.equals(user.getPj().getName())) {
                        out = out.concat(getPjData(map_id, pjName));
                    }
                }
            }

            //    }
        } catch (Exception ex) {
            Logger.getLogger(LoginFileIO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != indexReader) {
                    indexReader.close();
                }
                if (null != bufferIndex) {
                    bufferIndex.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return out;
    }

    public void indexPlayer(String playerID, String map_id) {//metodo para indexar jugador en archivo
        File file = new File("src/ServerIO/Instances/2D/" + map_id + "/index.thc");
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            // synchronized (file) {
            fichero = new FileWriter(file, true);//false para q borre el contenido
            writer = new PrintWriter(fichero);
            writer.print(playerID + "\r\n");
            file.deleteOnExit();
            // }
        } catch (IOException ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
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

    public File createInstanceFile(String map_id, int lvl) {
        File file;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {

            file = new File("src/ServerIO/Instances/2D/" + map_id + "/");
            file.mkdir();
            file = new File("src/ServerIO/Instances/2D/" + map_id + "/index.thc");
            fichero = new FileWriter(file, false);//false para q borre el contenido
            writer = new PrintWriter(fichero);
            writer.print(map_id + "," + lvl + "\r\n");
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

    public void submitPj(String map_id, Pj pj) {
        File pj_file = new File("src/ServerIO/Instances/2D/" + map_id + "/" + pj.getName() + ".lsd");
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

    public int get2DInstanceLvl(String map_id) {
        FileReader reader = null;
        BufferedReader buffer = null;
        File file;
        int output;
        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            file = new File("src/ServerIO/Instances/2D/" + map_id + "/index.thc");
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            // Lectura del fichero
            String line = "";
            String newLine = "";
            line = buffer.readLine();//solo leemos la primera linea
            String[] info = line.split(",");
            output = Integer.parseInt(info[1]);

        } catch (Exception e) {
            e.printStackTrace();
            output = -1;
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
                if (null != buffer) {
                    buffer.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return output;
    }

    public void updatePjPosition(String map_id, Pj pj, String position) {
        File user_file = new File("src/ServerIO/Instances/2D/" + map_id + "/" + pj.getName() + ".lsd");
//        File user_file = pj.getPj_file();
        FileReader pjFileReader = null;
        BufferedReader bufferPj = null;
        ArrayList<String> file_values = new ArrayList<String>();
        //Se empieza a copiar el archivo
        try {
            String line;
            pjFileReader = new FileReader(user_file);
            bufferPj = new BufferedReader(pjFileReader);
            while ((line = bufferPj.readLine()) != null && line.contains("@")) {
                if (line.split("@")[0].equals("position")) {
                    file_values.add("position@" + position);
                } else {
                    file_values.add(line);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != pjFileReader) {
                    pjFileReader.close();
                }
                if (null != bufferPj) {
                    bufferPj.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        //Se reescribe el archivo
        FileWriter pjFileWriter = null;
        PrintWriter writer = null;
        try {
            // synchronized (user_file) {
            pjFileWriter = new FileWriter(user_file, false);//false para q borre el contenido
            writer = new PrintWriter(pjFileWriter);
            while (!file_values.isEmpty()) {
                writer.print(file_values.remove(0) + "\r\n");
            }
            //}
        } catch (Exception ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != writer) {
                    writer.close();
                }
                if (null != pjFileReader) {
                    pjFileReader.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public String getInstanceUpdateData(String map_id, Pj pj) {
        LinkedList<String> users = getInstanceUsersNames(map_id, pj.getName());
        FileReader indexReader = null;
        BufferedReader bufferIndex = null;
        String update_pack = "";
        try {
            File user_file = new File("src/ServerIO/Instances/2D/" + map_id + "/" + pj.getName() + ".lsd");
//            File user_file = pj.getPj_file();
            //synchronized (user_file) {
            indexReader = new FileReader(user_file);
            bufferIndex = new BufferedReader(indexReader);
            String atribute_name;
            String atribute_value;
            String line;
            while ((line = bufferIndex.readLine()) != null && line.contains("@")) {
                String[] values = line.split("@");
                atribute_name = values[0];
                atribute_value = values[1];
                switch (atribute_name) {
                    case "life":
                        update_pack = update_pack.concat(atribute_value + "%");
                        break;
                    default:
                        break;
                }
            }
            //}
            Iterator it = users.iterator();
            File index_file;
            while (it.hasNext()) {
                String pj_name = it.next().toString();
                index_file = new File("src/ServerIO/Instances/2D/" + map_id + "/" + pj_name + ".lsd");
//                index_file = pj.getPj_file();
                //synchronized (index_file) {
                indexReader = new FileReader(index_file);
                bufferIndex = new BufferedReader(indexReader);

                while ((line = bufferIndex.readLine()) != null && line.contains("@")) {
                    atribute_name = line.split("@")[0];
                    atribute_value = line.split("@")[1];
                    switch (atribute_name) {
                        case "position":
                            update_pack = update_pack.concat(atribute_value + ",");
                            break;
                        case "life":
                            update_pack = update_pack.concat(atribute_value + "€");
                            break;
                        case "name":
                            update_pack = update_pack.concat(atribute_value + "#");
                            break;
                        default:
                            break;

                    }
                }
                //}
                update_pack = update_pack.concat("%");
            }
        } catch (Exception ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != indexReader) {
                    indexReader.close();
                }
                if (null != bufferIndex) {
                    bufferIndex.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return update_pack;
    }
}
