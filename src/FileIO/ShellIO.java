package FileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class ShellIO {

    private String date;

    public ShellIO(String date) {
        this.date = date;
    }

    public void initShellLogs() {
        File file;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            file = new File("src/Shell/logs/" + date + "/");
            file.mkdir();
            file = new File("src/Shell/logs/" + date + "/users/");
            file.mkdir();
            file = new File("src/Shell/logs/" + date + "/instances/");
            file.mkdir();
        } catch (Exception ex) {
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

    public void initServerLog() {
        File file;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            file = new File("src/Shell/logs/" + date + "/log.meth");
            fichero = new FileWriter(file, true);
            writer = new PrintWriter(fichero);
            writer.print("| ROTTEN CITY SERVER V 0.0  | \r\n"
                    + "4:20 Entertainment \r\n");
        } catch (Exception ex) {
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

    public void writeServerLog(String incoming) {
        File file;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            file = new File("src/Shell/logs/" + date + "/log.meth");
            fichero = new FileWriter(file, true);
            writer = new PrintWriter(fichero);
            writer.print(incoming + " \r\n");
        } catch (Exception ex) {
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

    public String getServerLog() {
        ArrayList<String> buffer = new ArrayList<String>();
        File server_log;
        FileReader logReader = null;
        BufferedReader bufferLog = null;
        String out = "";
        try {
            server_log = new File("src/Shell/logs/" + date + "/log.meth");
            logReader = new FileReader(server_log);
            bufferLog = new BufferedReader(logReader);
            String line;
            while ((line = bufferLog.readLine()) != null) {
                buffer.add(line);
                out = out.concat(line + "\n");
            }
            if (buffer.size() >= 25) {
                out = "";
                int aux_tail = buffer.size() - 1;
                for (int aux_head = aux_tail - 24; aux_head <= aux_tail; aux_head++) {
                    out = out.concat(buffer.get(aux_head) + "\n");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != logReader) {
                    logReader.close();
                }
                if (null != bufferLog) {
                    bufferLog.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return out;
    }
    
    public void initInstanceLog(String map_id){
        File file;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            file = new File("src/Shell/logs/" + date + "/instances/"+map_id+".meth");
            fichero = new FileWriter(file, true);
            writer = new PrintWriter(fichero);
            writer.print("| ROTTEN CITY SERVER V 0.0  | \r\n"
                    + "4:20 Entertainment \r\n"
                    + "Instance: "+map_id+"\r\n");
        } catch (Exception ex) {
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
    
    public void writeInstanceLog(String map_id,String incoming){
        File file;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            file = new File("src/Shell/logs/" + date + "/instances/"+map_id+".meth");
            fichero = new FileWriter(file, true);
            writer = new PrintWriter(fichero);
            writer.print(incoming + " \r\n");
        } catch (Exception ex) {
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
    
    public String getInstanceLog(String map_id){
        ArrayList<String> buffer = new ArrayList<String>();
        File server_log;
        FileReader logReader = null;
        BufferedReader bufferLog = null;
        String out = "";
        try {
            server_log = new File("src/Shell/logs/" + date + "/instances/"+map_id+".meth");
            logReader = new FileReader(server_log);
            bufferLog = new BufferedReader(logReader);
            String line;
            while ((line = bufferLog.readLine()) != null) {
                buffer.add(line);
                out = out.concat(line + "\n");
            }
            if (buffer.size() >= 25) {
                out = "";
                int aux_tail = buffer.size() - 1;
                for (int aux_head = aux_tail - 24; aux_head <= aux_tail; aux_head++) {
                    out = out.concat(buffer.get(aux_head) + "\n");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != logReader) {
                    logReader.close();
                }
                if (null != bufferLog) {
                    bufferLog.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return out;
    }
    
    public void initUserLog(String user_id){
        File file;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            file = new File("src/Shell/logs/" + date + "/users/"+user_id+".meth");
            fichero = new FileWriter(file, true);
            writer = new PrintWriter(fichero);
            writer.print("| ROTTEN CITY SERVER V 0.0  | \r\n"
                    + "4:20 Entertainment \r\n"
                    + "User: "+user_id+"\r\n");
        } catch (Exception ex) {
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
    
    public void writeUserLog(String user_id,String incoming){
        File file;
        FileWriter fichero = null;
        PrintWriter writer = null;
        try {
            file = new File("src/Shell/logs/" + date + "/users/"+user_id+".meth");
            fichero = new FileWriter(file, true);
            writer = new PrintWriter(fichero);
            writer.print(incoming + " \r\n");
        } catch (Exception ex) {
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
    
    public String getUserLog(String user_id){
        ArrayList<String> buffer = new ArrayList<String>();
        File server_log;
        FileReader logReader = null;
        BufferedReader bufferLog = null;
        String out = "";
        try {
            server_log = new File("src/Shell/logs/" + date + "/users/"+user_id+".meth");
            logReader = new FileReader(server_log);
            bufferLog = new BufferedReader(logReader);
            String line;
            while ((line = bufferLog.readLine()) != null) {
                buffer.add(line);
                out = out.concat(line + "\n");
            }
            if (buffer.size() >= 25) {
                out = "";
                int aux_tail = buffer.size() - 1;
                for (int aux_head = aux_tail - 24; aux_head <= aux_tail; aux_head++) {
                    out = out.concat(buffer.get(aux_head) + "\n");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(BattleFileIO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != logReader) {
                    logReader.close();
                }
                if (null != bufferLog) {
                    bufferLog.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return out;
    }
}
