package com.budderman18.IngotWarn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * This class handles the adminwarns command
 */
public class AdminWarn implements TabExecutor {
    /**
     * This method handles everything related to the adminwarns command
     * TabCompletion has its own method
     * "Clear" - clears a user's file
     * "Delete" - delete a certain warn
     * "Edit" - edit a certain warn
     * "Version" - display plugin info
     * "Reload" - reloads files
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return 
     */
    //used if the given file isnt in another folder
    final String ROOT = "";
    //retrive plugin instance
    Plugin plugin = main.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //import files
        File configf = new File(plugin.getDataFolder(),"config.yml");
        File languagef = new File(plugin.getDataFolder(),"language.yml");
        File playerdataf = new File(plugin.getDataFolder(),"playerdata.yml");
        InstanceData getdata = new InstanceData();
        FileConfiguration config = getdata.getCustomData(plugin, "config", ROOT);
        FileConfiguration language = getdata.getCustomData(plugin, "language", ROOT);
        FileConfiguration pd = getdata.getCustomData(plugin, "playerdata", ROOT);
        //import version
        String version = config.getString("version");
        //import maxwarns
        byte maxWarns = (byte) Integer.parseInt(config.getString("Max-Warns"));
        //language
        String prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message"));
        String versionMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Version-Message"));
        String reloadMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Reload-Message"));
        String noPermissionMessage = ChatColor.translateAlternateColorCodes('&', language.getString("No-Permission-Message"));
        String incorrectCommandMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Incorrect-Command-Message"));
        String noPlayerMessage = ChatColor.translateAlternateColorCodes('&', language.getString("No-Player-Message"));
        String fileClearedMessage = ChatColor.translateAlternateColorCodes('&', language.getString("File-Cleared-Message"));
        String editedPlayerMessage1 = ChatColor.translateAlternateColorCodes('&', language.getString("Edited-Player-Message-1"));
        String editedPlayerMessage2 = ChatColor.translateAlternateColorCodes('&', language.getString("Edited-Player-Message-2"));
        String deletedPlayerMessage1 = ChatColor.translateAlternateColorCodes('&', language.getString("Deleted-Player-Message-1"));
        String deletedPlayerMessage2 = ChatColor.translateAlternateColorCodes('&', language.getString("Deleted-Player-Message-2"));
        String deletedPlayerMessage3 = ChatColor.translateAlternateColorCodes('&', language.getString("Deleted-Player-Message-3"));
        //file strings
        String usernameString;
        String warnNumber;
        String warnNumberString;
        String editString;
        if (cmd.getName().equalsIgnoreCase("adminwarn")) {
            //check if command is empty to prevent errors
            if (args.length > 0) {
                //check if player has permission
                if (sender.hasPermission("ingotwarn.adminwarns")) {
                    //clear option
                    if (args[0].equalsIgnoreCase("clear")) {
                        //check if args are valid
                        if (args.length >= 2) {
                            usernameString = args[1];
                            //clear file
                            if (pd.getString(usernameString) != null) {
                                for (byte i = 1; i <= maxWarns; i++) {
                                    warnNumberString = "Warn" + i;
                                    if (pd.getString(usernameString + ".Warn" + i) != null) {
                                        pd.set(usernameString + '.' + warnNumberString, "");
                                    } 
                                    else {
                                        i = maxWarns;
                                    }
                                }
                                //update file
                                try {
                                    pd.save(playerdataf);
                                } 
                                catch (IOException ex) {
                                    Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                sender.sendMessage(prefixMessage + fileClearedMessage + usernameString);
                                return true;
                            } 
                            //check if player doesnt exist
                            else {
                                sender.sendMessage(prefixMessage + noPlayerMessage);
                                return true;
                            }
                        } 
                        //send if command is invalid
                        else {
                            sender.sendMessage(prefixMessage + incorrectCommandMessage);
                            return false;
                        }
                    }
                    //delete command
                    if (args[0].equalsIgnoreCase("delete")) {
                        //check if command is valid
                        if (args.length >= 2) {
                            usernameString = args[1];
                            warnNumber = args[2];
                            byte warnNumberByte = (byte) Integer.parseInt(warnNumber);
                            //check if player has data
                            if (pd.getString(usernameString) != null) {
                                //delete
                                pd.set(usernameString + ".Warn" + warnNumber, "");
                                //move any warns ahead of deleted variable in its place
                                for (byte i = warnNumberByte; i <= (maxWarns); i++) {
                                    if (pd.get(usernameString + ".Warn" + (i+1)) != null) {
                                        pd.set(usernameString + ".Warn" + i, pd.getString(usernameString + ".Warn" + (i+1)));
                                    }
                                }
                                //set end of warns to be blank since it will copy the previous without it
                                pd.set(usernameString + ".Warn" + maxWarns, "");
                                //saves file
                                try {
                                    pd.save(playerdataf);
                                } 
                                catch (IOException ex) {
                                    Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                sender.sendMessage(prefixMessage + deletedPlayerMessage1 + warnNumber + deletedPlayerMessage2 + usernameString + deletedPlayerMessage3);
                                return true;
                            } 
                            //send if player doesnt have data
                            else {
                                sender.sendMessage(prefixMessage + noPlayerMessage);
                                return true;
                            }
                        } 
                        //send if player is invalid
                        else {
                            sender.sendMessage(prefixMessage + incorrectCommandMessage);
                            return false;
                        }
                    }
                    //edit command
                    if (args[0].equalsIgnoreCase("edit")) {
                        //check if command is valid
                        if (args.length >= 2) {
                            usernameString = args[1];
                            warnNumber = args[2];
                            //combine args into string
                            String[] split = Arrays.copyOfRange(args, 3, args.length);
                            editString = String.join(" ", split);
                            //check if user has data
                            if (pd.getString(usernameString) != null) {
                                //edit
                                pd.set(usernameString + ".Warn" + warnNumber, editString);
                                //save file
                                try {
                                    pd.save(playerdataf);
                                } 
                                catch (IOException ex) {
                                    Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                sender.sendMessage(prefixMessage + editedPlayerMessage1 + usernameString + editedPlayerMessage2 + warnNumber);
                                return true;
                            }
                            //send if player doesnt have data
                            else {
                                sender.sendMessage(prefixMessage + noPlayerMessage);
                                return true;
                            }
                        } 
                        //send if command is invalid
                        else {
                            sender.sendMessage(prefixMessage + incorrectCommandMessage);
                            return false;
                        }
                    }
                    //version command
                    if (args[0].equalsIgnoreCase("version")) {
                        sender.sendMessage(prefixMessage + versionMessage + version);
                        return true;
                    }
                    //reload command
                    if (args[0].equalsIgnoreCase("reload")) {
                        //load config
                        try {
                            config.load(configf);
                        } 
                        catch (IOException ex) {
                            Logger.getLogger(AdminWarn.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        catch (InvalidConfigurationException ex) {
                            Logger.getLogger(AdminWarn.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //load language
                        try {
                            language.load(languagef);
                        } 
                        catch (IOException ex) {
                            Logger.getLogger(AdminWarn.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        catch (InvalidConfigurationException ex) {
                            Logger.getLogger(AdminWarn.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //load playerdata
                        try {
                            pd.load(playerdataf);
                        } 
                        catch (IOException ex) {
                            Logger.getLogger(AdminWarn.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                        catch (InvalidConfigurationException ex) {
                            Logger.getLogger(AdminWarn.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        sender.sendMessage(prefixMessage + reloadMessage);
                        return true;
                    }
                }
                //send if player doesnt have permission
                else {
                    sender.sendMessage(prefixMessage + noPermissionMessage);
                    return true;
                }
            }
            //send if command is invalid
            else {
                sender.sendMessage(prefixMessage + incorrectCommandMessage);
                return false;
            }
        }        
        return false;  
    }
   /*
    * This method handles tabcompletion
    */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        //main command args
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("clear");
            arguments.add("delete");
            arguments.add("edit");
            arguments.add("version");
            arguments.add("reload");
            return arguments;
        }
        //used to retrive indexes for edit and delete commands
        if (args.length == 3 && (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("delete"))) {
            InstanceData getdata = new InstanceData();
            FileConfiguration config = getdata.getCustomData(plugin, "config", ROOT);
            FileConfiguration pd = getdata.getCustomData(plugin, "playerdata", ROOT);
            List<String> arguments = new ArrayList<>();
            String usernameString;
            String warnNumberString;
            String warnNumber;
            //import maxwarns
            byte maxWarns = (byte) Integer.parseInt(config.getString("Max-Warns"));
            //retrive indexes
            for (byte i = 1; i <= maxWarns; i++) {
                warnNumberString = "Warn" + i;
                warnNumber = Byte.toString(i);
                if (pd.getString(args[1] + ".Warn" + i) != null) {
                    arguments.add(warnNumber);
                } 
                else {
                    i = maxWarns;
                }
            }
            return arguments;
        }
        return null;
    } 
}

