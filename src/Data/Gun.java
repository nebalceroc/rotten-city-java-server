package Data;

/**
 *
 * @author Nicolás Balcero
 */
public class Gun {
    private String id;
    private int max_damage;
    private int min_damage;
    
    public Gun(String gun_string){
        String[] values = gun_string.split("/");
        this.id=values[0];
        this.max_damage=Integer.parseInt(values[1]);
        this.min_damage=Integer.parseInt(values[2]);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the max_damage
     */
    public int getMax_damage() {
        return max_damage;
    }

    /**
     * @param max_damage the max_damage to set
     */
    public void setMax_damage(int max_damage) {
        this.max_damage = max_damage;
    }

    /**
     * @return the min_damage
     */
    public int getMin_damage() {
        return min_damage;
    }

    /**
     * @param min_damage the min_damage to set
     */
    public void setMin_damage(int min_damage) {
        this.min_damage = min_damage;
    }
}
