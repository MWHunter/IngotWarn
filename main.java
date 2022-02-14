/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.budderman18.IngotWarn;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Kyle Collins
 */
public class main extends JavaPlugin {
    private File configf;
    private File languagef; 
    private FileConfiguration config; 
    private FileConfiguration language; 
    private File playerdataf;
    private FileConfiguration pd;;
    /*
    * Enables the plugin.
    * Check if MC version isn't the latest.
    * If its not, warn the player about lacking support
    * Also checks for dependencies and loads commands
    * Also saves and loads config
    */  
    private void createFiles() {
        configf = new File(getDataFolder(), "config.yml");
        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
         }

        config = new YamlConfiguration();
        try {
            config.load(configf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        languagef = new File(getDataFolder(), "language.yml");
        if (!languagef.exists()) {
            languagef.getParentFile().mkdirs();
            saveResource("language.yml", false);
         }

        language = new YamlConfiguration();
        try {
            language.load(configf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        playerdataf = new File(getDataFolder(), "playerdata.yml");
        if (!playerdataf.exists()) {
            playerdataf.getParentFile().mkdirs();
            saveResource("playerdata.yml", false);
         }

        pd = new YamlConfiguration();
        try {
            pd.load(playerdataf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onEnable() {
        createFiles();
        //language variables defaults (in order)
        String prefixMessage = ChatColor.translateAlternateColorCodes('&', "&2[&9IngotWarn&2] ");
        String unsupportedVersionAMessage = ChatColor.translateAlternateColorCodes('&', "&4IngotWarn does not support your version!");
        String unsupportedVersionBMessage = ChatColor.translateAlternateColorCodes('&', "&aOnly 1.18.1 is supported");
        String unsupportedVersionCMessage = ChatColor.translateAlternateColorCodes('&', "&aThis plugin will likely not work and you will get no help for issues");
        String unsecureServerAMessage = ChatColor.translateAlternateColorCodes('&', "&4NEVER EVER EVER EVER EVER EVER USE OFFLINE MODE!!!!!!!!!!!!!!!");
        String unsecureServerBMessage = ChatColor.translateAlternateColorCodes('&', "&cOffline mode is a serious threat to your server and will never be supported by my plugins!");
        String unsecureServerCMessage = ChatColor.translateAlternateColorCodes('&', "&cTo protect your safety and this plugin's saftey, this plugin will now be disabled");
        String pluginEnabledMessage = ChatColor.translateAlternateColorCodes('&', "&eIngotWarn by Budderman18 has been enabled!");
        //check if it can retrive the message strings
        if (language.getString("Prefix-Message") != null) {
            prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message")); 
        }
        if (language.getString("Unsupported-VersionA-Message") != null) {
            unsupportedVersionAMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsupported-VersionA-Message")); 
        }
        if (language.getString("Unsupported-VersionB-Message") != null) {
            unsupportedVersionBMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsupported-VersionB-Message")); 
        }
        if (language.getString("Unsupported-VersionC-Message") != null) {
            unsupportedVersionCMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsupported-VersionC-Message")); 
        }
        if (language.getString("Unsecure-ServerA-Message") != null) {
            unsecureServerAMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsecure-ServerA-Message")); 
        }
        if (language.getString("Unsecure-ServerB-Message") != null) {
            unsecureServerBMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsecure-ServerB-Message")); 
        }
        if (language.getString("Unsecure-ServerC-Message") != null) {
            unsecureServerCMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsecure-ServerC-Message")); 
        }
        if (language.getString("Plugin-Enabled-Message") != null) {
            pluginEnabledMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Plugin-Enabled-Message")); 
        }
        if (!(Bukkit.getVersion().contains("1.18.1"))) {
            getServer().getConsoleSender().sendMessage(prefixMessage + unsupportedVersionAMessage);
            getServer().getConsoleSender().sendMessage(prefixMessage + unsupportedVersionBMessage);
            getServer().getConsoleSender().sendMessage(prefixMessage + unsupportedVersionCMessage);
            return;   
        }
        if (!(getServer().getOnlineMode())) {
            getServer().getConsoleSender().sendMessage(prefixMessage + unsecureServerAMessage);
            getServer().getConsoleSender().sendMessage(prefixMessage + unsecureServerBMessage);
            getServer().getConsoleSender().sendMessage(prefixMessage + unsecureServerCMessage);
            getServer().getPluginManager().disablePlugin(this);
        }
        //commands
        this.getCommand("warn").setExecutor(new Warn());
        getServer().getPluginManager().enablePlugin(this);
        getServer().getConsoleSender().sendMessage(prefixMessage + pluginEnabledMessage);
    }
    //these 3 methods help retrive files when in other classes
    public FileConfiguration getCustomConfig() {
        return this.config;
    }
    public FileConfiguration getCustomPlayerData() {
        return this.pd;
    }
    public FileConfiguration getCustomLanguage() {
        return this.language;
    }
    /*
    * Disables the plugin.
    */
    @Override
    public void onDisable() {
        String prefixMessage = ChatColor.translateAlternateColorCodes('&', "&2[&9IngotWarn&2] ");
        String pluginDisabledMessage = ChatColor.translateAlternateColorCodes('&', "&eIngotWarn has been disabled!");
        if (language.getString("Prefix-Message") != null) {
            prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message")); 
        }
        if (language.getString("Plugin-Disabled-Message") != null) {
            pluginDisabledMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Plugin-Disabled-Message")); 
        }
        Plugin plugin = Bukkit.getPluginManager().getPlugin("IngotWarn");
        //files
        File configf = new File(plugin.getDataFolder(), "config.yml");
        File playerdataf = new File(plugin.getDataFolder(), "playerdata.yml");
        File languagef = new File(plugin.getDataFolder(), "language.yml");
        FileConfiguration pd = YamlConfiguration.loadConfiguration(playerdataf);
        FileConfiguration language = YamlConfiguration.loadConfiguration(languagef);
        FileConfiguration config = YamlConfiguration.loadConfiguration(configf);
        try {
            config.save(configf);
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            language.save(languagef);
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            language.save(playerdataf);
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        getServer().getPluginManager().disablePlugin(this);
        getServer().getConsoleSender().sendMessage(prefixMessage + pluginDisabledMessage);
    }
}
