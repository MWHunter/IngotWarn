/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.budderman18.IngotWarn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * This class checks for file versions and updates them if needed
 */
public class FileUpdater {
    Plugin plugin = main.getInstance();
    final String ROOT = "";
    AdminWarn getdata = new AdminWarn();
    File configf = new File("plugins/IngotWarn","config.yml");
    File languagef = new File("plugins/IngotWarn","language.yml");
    File playerdataf = new File("plugins/IngotWarn","playerdata.yml");
    FileConfiguration config = getdata.getCustomData(plugin,"config",ROOT);
    FileConfiguration language = getdata.getCustomData(plugin,"language",ROOT);
    FileConfiguration pd = getdata.getCustomData(plugin,"playerdata",ROOT);
    public void updateConfig() {
        //version 1.1
        List<String> commentsVer1_1 = new ArrayList<String>();
        commentsVer1_1.add("Execute commands on a given warn count!");
        commentsVer1_1.add("You can add as may as you'd like! just follow the format");
        commentsVer1_1.add("You can change these in-game with /adminwarn commands");
        commentsVer1_1.add("You must put them in numeric order or some commands may not send!");
        commentsVer1_1.add("use %player% for the player's username. use %target% for the sender's username");
        commentsVer1_1.add("Warnx: <--- X is what warn number you want the command to occur on");
        commentsVer1_1.add("  Commandx <--- X is the number for the command. Keep it in numeric order, as that's the order they will execute in. Max amount of commands is 126");
        config.set("version","1.1");
        config.createSection("Commands");
        config.setComments("Commands", commentsVer1_1);
        //update file
        try {
            config.save(configf);
        } 
        catch (IOException ex) {
            Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateLanguage() {
        //version 1.1
        List<String> commentsVer1_1 = new ArrayList<String>();
        commentsVer1_1.add("don't touch this");
        language.set("version","1.1");
        language.setComments("version", commentsVer1_1);
        language.set("Is-Notified-Message: ","&aisNotified: &b");
        language.set("Added-Command-Succeeded-Message: ","&bCommand added!");
        language.set("Added-Command-Failed-Message: ","&cCommand NOT added! &bAre you trying to edit? use \"edit\" instead!");
        language.set("Delete-Command-Delete-Warn-Message: ","&bDeleted all commands at warn count &6");
        language.set("Delete-Command-Delete-Command-Start-Message: ","&bDeleted command &6");
        language.set("Delete-Command-Delete-Command-End-Message: "," &binside warn count &6");
        language.set("Edit-Command-Edit-Start-Message: ","&bEdited command &6");
        language.set("Edit-Command-Edit-End-Message: "," &bat warn count &6");
        language.set("Edit-Command-Edit-Failed-Message: ","&cCommand NOT edited! Make sure you have a command at the designated values first!");
        language.set("Cleared-Command-Message: ","&bCommands erased.");
        language.set("List-Command-Start-Message: ","&eLoading specified command section(s)...");
        language.set("List-Command-Warn-Start-Message: ","&aCommands at Warn");
        language.set("List-Command-Warn-End-Message: ","&a: &b");
        language.set("List-Command-Command-Start-Message: ","  &aCommand at Command");
        language.set("List-Command-Command-End-Message: ","&a: &b");
        language.set("List-Command-End-Message: ","&eDone!");
        language.set("Player-Exists-Message: ","&4PLAYER ALREADY HAS DATA!!!! &bCopying over old data to new data...");
        language.set("Outdated-Config-Message: ","&4OUTDATED CONFIG!!!! &bConverting to newest format...");
        language.set("Outdated-Config-Message: ","&4OUTDATED LANGUAGE!!!! &bConverting to newest format...");
        language.set("Outdated-Config-Message: ","&4OUTDATED PLAYERDATA!!!! &bConverting to newest format...");
        //update file
        try {
            language.save(languagef);
        } 
        catch (IOException ex) {
            Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updatePlayerData() {
        //version 1.1
        List<String> commentsVer1_1 = new ArrayList<String>();
        commentsVer1_1.add("don't touch this");
        pd.set("version","1.1");
        pd.setComments("version", commentsVer1_1);
        byte maxWarns = Byte.parseByte(config.getString("Max-Warns"));
        for (String key : pd.getKeys(false)) {
            for (byte i = 1; i <= maxWarns; i++) {
                if (pd.getString(key + ".Warn" + i) != null) {
                    pd.set(key + ".Warn" + i + ".Message", pd.getString(key + ".Warn" + i));
                    pd.set(key + ".Warn" + i + ".isNotified", true);
                }
                else {
                    i = maxWarns;
                }
            }
        }
        //update file
        try {
            pd.save(playerdataf);
        } 
        catch (IOException ex) {
            Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
