package Data;

import DB.ItemDBConnector;
import FileIO.BattleFileIO;
import FileIO.PjFileIO;
import java.io.File;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Nicolas
 */
public class Pj {

    //CADENA PARA PERSONAJES  
    private String name;
    private int lvl;
    private long exp;
    private String school;
    private String avatar_string;
    private int strength;
    private int intelect;
    private int speed;
    private int stamina;
    private int life;
    private String map;
    private String position;
    private PrintStream output;
    private File pj_file;
    private LinkedList<String> pj_bag_items;
    private HashMap<String, String> pj_gear_items;

    public Pj(String pj_string) {
        System.out.println(pj_string);
        String[] filter_1 = pj_string.split(",");
        this.map = filter_1[0];
        this.avatar_string = filter_1[1];
        String[] player = filter_1[2].split("#");
        this.name = player[0];
        this.school = "none";
        this.stamina = Integer.parseInt(player[1]);
        this.lvl = Integer.parseInt(player[2]);
        this.strength = Integer.parseInt(player[3]);
        this.speed = Integer.parseInt(player[4]);
        this.intelect = Integer.parseInt(player[5]);
        this.exp = Integer.parseInt(player[6]);
        this.position ="400,-90,1,right,20,false,100";
        this.life=(int) (stamina*1.25);
        this.pj_gear_items = new HashMap<String, String>();
        String[] gear = filter_1[3].split("#");
        this.pj_gear_items.put("HEAD", gear[0]);
        this.pj_gear_items.put("CHEST", gear[1]);
        this.pj_gear_items.put("LEGS", gear[2]);
        this.pj_gear_items.put("FOOT", gear[3]);
        this.pj_gear_items.put("GUN1", gear[4]);
        this.pj_gear_items.put("GUN2", gear[5]);
        this.pj_gear_items.put("AUXGUN", gear[6]); 
//        PjFileIO pjFileIO = new PjFileIO();
//        pjFileIO.createPjFile(this);
    }
    
    public String getPlayerDesc(){
        return name+"#"+stamina+"#"+lvl+"#"+strength+"#"+speed+"#"+intelect+"#"+exp;
    }
    
    public String getPjItemsData() throws SQLException{
        ItemDBConnector itemDB = new ItemDBConnector();
        return itemDB.getItemsData(name);
    }
    
    public String getPjData(){
        BattleFileIO file_system = new BattleFileIO();
        return file_system.getPjData(map, this.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAvatar_string() {
        return avatar_string;
    }

    public void setAvatar_string(String avatar_string) {
        this.avatar_string = avatar_string;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelect() {
        return intelect;
    }

    public void setIntelect(int intelect) {
        this.intelect = intelect;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PrintStream getOutput() {
        return output;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public File getPj_file() {
        return pj_file;
    }

    public void setPj_file(File pj_file) {
        this.pj_file = pj_file;
    }

    public LinkedList<String> getPj_bag_items() {
        return pj_bag_items;
    }

    public void setPj_bag_items(LinkedList<String> pj_bag_items) {
        this.setPj_bag_items(pj_bag_items);
    }

    public HashMap<String, String> getPj_gear_items() {
        return pj_gear_items;
    }

    public void setPj_gear_items(HashMap<String, String> pj_gear_items) {
        this.pj_gear_items = pj_gear_items;
    }



}
