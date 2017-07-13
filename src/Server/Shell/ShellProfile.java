/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Shell;

/**
 *
 * @author Nicolas
 */
public abstract class ShellProfile {
    private String profile;
    abstract public void write(String input, Shell shell);

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
