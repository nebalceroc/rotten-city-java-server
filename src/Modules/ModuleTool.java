package Modules;

import Modules.Battle.M_Battle;

import Modules.Login.M_Login;
import Modules.Menu.M_Menu;
import Modules.Chat.M_Chat;
import Modules.Items.M_Items;
import Server.Shell.Shell;

/**
 *
 * @author Nicolas
 */
public class ModuleTool {
    
    private M_Battle modulo_combate;
    private M_Login modulo_login;
    private M_Menu modulo_menu;
    private M_World modulo_world;
    private M_Chat modulo_chat;
    private M_Items modulo_items;
    
    
    public String identifyModule(String message){
        String[] buffer = message.split("/"); 
        return buffer[0];
    }            

    public M_Battle getModulo_combate() {
        return modulo_combate;
    }

    public void setModulo_combate(M_Battle modulo_combate) {
        this.modulo_combate = modulo_combate;
    }

    public M_Login getModulo_login() {
        return modulo_login;
    }

    public void setModulo_login(M_Login modulo_login) {
        this.modulo_login = modulo_login;
    }

    public M_Menu getModulo_menu() {
        return modulo_menu;
    }

    public void setModulo_menu(M_Menu modulo_menu) {
        this.modulo_menu = modulo_menu;
    }

    public M_World getModulo_world() {
        return modulo_world;
    }

    public void setModulo_world(M_World modulo_world) {
        this.modulo_world = modulo_world;
    }    

    public M_Chat getModulo_chat() {
        return modulo_chat;
    }

    public void setModulo_chat(M_Chat modulo_chat) {
        this.modulo_chat = modulo_chat;
    }

    public M_Items getModulo_items() {
        return modulo_items;
    }

    public void setModulo_items(M_Items modulo_items) {
        this.modulo_items = modulo_items;
    }

    
}
