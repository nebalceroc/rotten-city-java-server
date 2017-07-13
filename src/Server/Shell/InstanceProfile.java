package Server.Shell;

/**
 *
 * @author Nicolas
 */
public class InstanceProfile extends ShellProfile{

    private String map_id;
    
    public InstanceProfile(String instance_name){
        super();
        setProfile("instance");
        setMap_id(instance_name);
    }
    
    @Override
    public void write(String input, Shell shell) {
        shell.getShell_file_sys().writeInstanceLog(map_id, input);
    }

    /**
     * @return the map_id
     */
    public String getMap_id() {
        return map_id;
    }

    /**
     * @param map_id the map_id to set
     */
    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }
    
}
