/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.budderman18.IngotWarn;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * This class handles all data that needs to be instated into other classes
 */
public class InstanceData {
    //used if the given file isnt in another folder
    final String ROOT = "";
    //retrive plugin instance
    Plugin plugin = main.getInstance();
    /**
     * this method calls all the files
     * It also can be used to manage them
     * @param plugin
     * @param filename
     * @param path
     * @return 
     */
    public YamlConfiguration getCustomData(Plugin plugin, String filename, String path) {
        //check if folder is a thing
        if (!plugin.getDataFolder().exists())
        {
            plugin.getDataFolder().mkdir();
        }
         //check if file broke somehow
        File file = new File(plugin.getDataFolder() + "/" + path, filename + ".yml");
        //load
        return YamlConfiguration.loadConfiguration(file);
    }
    /*
    * This method converts username to an actual string
    * This is because the .toString() feature leaves useless junk and messes things up
    */
    public String convertUsername(Player player) {
        //converts username back into an actual string, since "toString()" leaves useless junk that messes things up
        String usernameString = player.toString();
        usernameString = usernameString.replace('{',' ');
        String usernameString1 = "";
        String usernameString2 = "";
        String usernameString3 = "";
        usernameString1 = (usernameString1 + usernameString.replaceAll("CraftPlayer ", ""));
        usernameString2 = (usernameString2 + usernameString1.replaceAll("name=", ""));
        usernameString3 = (usernameString3 + usernameString2.replaceAll("}", ""));
        return usernameString3;
    }
}
