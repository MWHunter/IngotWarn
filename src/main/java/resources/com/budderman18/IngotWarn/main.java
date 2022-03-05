
package com.budderman18.IngotWarn;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class enables and disables the plugin
 * It also imports commands and handles events
 */
public class main extends JavaPlugin implements Listener { 
    //used if the given file isnt in another folder
    final String ROOT = "";
    ConsoleCommandSender sender = getServer().getConsoleSender();
    /*
    * This method creates files if needed
    */
    private void createFiles() {
        File configf = new File(getDataFolder(), "config.yml");
        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
         }

        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(configf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        File playerdataf = new File(getDataFolder(), "playerdata.yml");
        if (!playerdataf.exists()) {
            playerdataf.getParentFile().mkdirs();
            saveResource("playerdata.yml", false);
         }

        FileConfiguration pd = new YamlConfiguration();
        try {
            pd.load(playerdataf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        File languagef = new File(getDataFolder(), "language.yml");
        if (!languagef.exists()) {
            languagef.getParentFile().mkdirs();
            saveResource("language.yml", false);
         }

        FileConfiguration language = new YamlConfiguration();
        try {
            language.load(languagef);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    } 
    //retrive plugin instance
    private static main plugin;
    
    public static main getInstance() {
        return plugin;
    }
    
    AdminWarn getdata = new AdminWarn();
    /*
    * Enables the plugin.
    * Checks if MC version isn't the latest.
    * If its not, warn the player about lacking support
    * Checks if server is running offline mode
    * If it is, disable the plugin
    * Also checks for dependencies and loads commands
    */
    @Override
    public void onEnable() {
        //creates files if needed
        plugin = this;
        createFiles();
        //imports files
        FileConfiguration language = getdata.getCustomData(plugin,"language",ROOT);
        //language variables)
        String prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message")); 
        String unsupportedVersionAMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsupported-VersionA-Message")); 
        String unsupportedVersionBMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsupported-VersionB-Message")); 
        String unsupportedVersionCMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsupported-VersionC-Message")); 
        String unsecureServerAMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsecure-ServerA-Message")); 
        String unsecureServerBMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsecure-ServerB-Message")); 
        String unsecureServerCMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Unsecure-ServerC-Message")); 
        String pluginEnabledMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Plugin-Enabled-Message")); 
        //check for correct version
        if (!(Bukkit.getVersion().contains("1.18.2"))) {
            sender.sendMessage(prefixMessage + unsupportedVersionAMessage);
            sender.sendMessage(prefixMessage + unsupportedVersionBMessage);
            sender.sendMessage(prefixMessage + unsupportedVersionCMessage);
            return;   
        }
        //check for online mode
        //if (!(getServer().getOnlineMode())) {
        //    sender.sendMessage(prefixMessage + unsecureServerAMessage);
        //    sender.sendMessage(prefixMessage + unsecureServerBMessage);
        //    sender.sendMessage(prefixMessage + unsecureServerCMessage);
        //    getServer().getPluginManager().disablePlugin(this);
        //}
        //commands
        this.getCommand("warn").setExecutor(new Warn());
        this.getCommand("checkwarns").setExecutor(new CheckWarn());
        this.getCommand("adminwarn").setExecutor(new AdminWarn());
        //events
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getPluginManager().enablePlugin(this);
        sender.sendMessage(prefixMessage + pluginEnabledMessage);
    }
    /*
    * This method creates playerdata when someone new joins
    * In the future, it'll alert players where werent online when warned
    */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void createUserData(PlayerJoinEvent event) throws IOException {
        //imports files
        Plugin plugin = getServer().getPluginManager().getPlugin("IngotWarn");
        FileConfiguration config = getdata.getCustomData(plugin,"config",ROOT);
        FileConfiguration language = getdata.getCustomData(plugin,"language",ROOT);
        FileConfiguration pd = getdata.getCustomData(plugin,"playerdata",ROOT);
        File playerdataf = new File("plugins/IngotWarn","playerdata.yml");
        //language
        String prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message")); 
        String newPlayerMessage = ChatColor.translateAlternateColorCodes('&', language.getString("New-Player-Message")); 
        String playerExistsMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Player-Exists-Message")); 
        //converts username back into an actual string, since "toString()" leaves useless junk that messes things up
        Player username = event.getPlayer();
        String usernameString = username.getName();
        boolean exists = false;
        String tempusername = null;
        //check if joined user has data
        if (pd.getString(usernameString) == null) {
            //tell console
            sender.sendMessage(prefixMessage + newPlayerMessage);
            //check if joined player's UUID already is in the data
            for (String key : pd.getKeys(false)) {
                if (pd.getString(key + ".UUID").equals(username.getUniqueId().toString())) {
                    tempusername = key;
                    //skip fresh generation
                    exists = true;
                }
            }
            //create section
            pd.createSection(usernameString);
            if (exists == true) {
                //tell console
                sender.sendMessage(prefixMessage + playerExistsMessage);
                //copy over old data to new data
                byte maxWarns = (byte) Integer.parseInt(config.getString("Max-Warns"));
                pd.set(usernameString + ".UUID", pd.getString(tempusername + ".UUID"));
                for (byte i = 1; i < maxWarns + 1; i++) {
                    if (pd.get(tempusername + ".Warn" + i) != null) {
                        pd.set(usernameString + ".Warn" + i, pd.getString(tempusername + ".Warn" + i));
                    } else {
                        i = maxWarns;
                    }
                }
                sender.sendMessage("Done!");
            }
            //check if no data exists
            if (exists == false) {
                //generate UUID section
                UUID uuid = username.getUniqueId();
                pd.set(usernameString + ".UUID", uuid.toString());
            }
            //saves file 
            pd.save(playerdataf);
        }
    }
    /*
    * Disables the plugin.
    */
    @Override
    public void onDisable() {
        //imports files
        Plugin plugin = getServer().getPluginManager().getPlugin("IngotWarn");
        File configf = new File("plugins/IngotWarn","config.yml");
        File languagef = new File("plugins/IngotWarn","language.yml");
        File playerdataf = new File("plugins/IngotWarn","playerdata.yml");
        FileConfiguration config = getdata.getCustomData(plugin,"config",ROOT);
        FileConfiguration language = getdata.getCustomData(plugin,"language",ROOT);
        FileConfiguration pd = getdata.getCustomData(plugin,"playerdata",ROOT);
        //language
        String prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message")); 
        String pluginDisabledMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Plugin-Disabled-Message")); 
        //saves files
        try {
            config.save(configf);
        } 
        catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            language.save(languagef);
        } 
        catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pd.save(playerdataf);
        } 
        catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //disables plugin
        getServer().getPluginManager().disablePlugin(this);
        sender.sendMessage(prefixMessage + pluginDisabledMessage);
    }
}
