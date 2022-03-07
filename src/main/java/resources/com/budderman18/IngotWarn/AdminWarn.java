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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * This class handles the adminwarns command
 */
public class AdminWarn implements TabExecutor {
    /*
    * THis method is used to read and write to a given file
    * Also handles YML loading
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
        FileConfiguration config = this.getCustomData(plugin, "config", ROOT);
        FileConfiguration language = this.getCustomData(plugin, "language", ROOT);
        FileConfiguration pd = this.getCustomData(plugin, "playerdata", ROOT);
        //import version
        String version = config.getString("version");
        //import maxwarns
        byte maxWarns = (byte) Integer.parseInt(config.getString("Max-Warns"));
        //language
        String prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message"));
        String versionMessage1 = ChatColor.translateAlternateColorCodes('&', language.getString("Version-Message-1"));
        String versionMessage2 = ChatColor.translateAlternateColorCodes('&', language.getString("Version-Message-2"));
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
        String addedCommandSucceededMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Added-Command-Succeeded-Message"));
        String addedCommandFailedMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Added-Command-Failed-Message"));
        String deleteCommandDeleteWarnMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Delete-Command-Delete-Warn-Message"));
        String deleteCommandDeleteCommandStartMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Delete-Command-Delete-Command-Start-Message"));
        String deleteCommandDeleteCommandEndMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Delete-Command-Delete-Command-End-Message"));
        String editCommandEditStartMessage= ChatColor.translateAlternateColorCodes('&', language.getString("Edit-Command-Edit-Start-Message"));
        String editCommandEditEndMessage= ChatColor.translateAlternateColorCodes('&', language.getString("Edit-Command-Edit-End-Message"));
        String editCommandEditFailedMessage= ChatColor.translateAlternateColorCodes('&', language.getString("Edit-Command-Edit-Failed-Message"));
        String clearedCommandMessage= ChatColor.translateAlternateColorCodes('&', language.getString("Cleared-Command-Message"));
        String listCommandStartMessage = ChatColor.translateAlternateColorCodes('&', language.getString("List-Command-Start-Message"));
        String listCommandWarnStartMessage= ChatColor.translateAlternateColorCodes('&', language.getString("List-Command-Warn-Start-Message"));
        String listCommandWarnEndMessage= ChatColor.translateAlternateColorCodes('&', language.getString("List-Command-Warn-End-Message"));
        String listCommandCommandStartMessage = ChatColor.translateAlternateColorCodes('&', language.getString("List-Command-Command-Start-Message"));
        String listCommandCommandEndMessage = ChatColor.translateAlternateColorCodes('&', language.getString("List-Command-Command-End-Message"));
        String listCommandEndMessage = ChatColor.translateAlternateColorCodes('&', language.getString("List-Command-End-Message"));
        //command tempstrings
        String usernameString;
        String warnNumber;
        String warnNumberString;
        String editString;
        String commandAddString;
        String commandEditString;
        String warnIndex;
        byte tempWarnIndex;
        String commandIndex = null;
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
                                    if (pd.getString(usernameString + ".Warn" + i + ".Message") != null) {
                                        pd.set(usernameString + '.' + warnNumberString, null);
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
                                pd.set(usernameString + ".Warn" + warnNumber + ".Message", "");
                                pd.set(usernameString + ".Warn" + warnNumber + ".isNotified", "false");
                                //move any warns ahead of deleted variable in its place
                                for (byte i = warnNumberByte; i <= (maxWarns); i++) {
                                    if (pd.get(usernameString + ".Warn" + (i+1) + ".Message") != null) {
                                        pd.set(usernameString + ".Warn" + i + ".Message", pd.getString(usernameString + ".Warn" + (i+1) + ".Message"));
                                        pd.set(usernameString + ".Warn" + i + ".isNotified", pd.getString(usernameString + ".Warn" + (i+1) + ".isNotified"));
                                    }
                                    else {
                                        pd.set(usernameString + ".Warn" + i, null);
                                        i = maxWarns;
                                    }
                                }
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
                                pd.set(usernameString + ".Warn" + warnNumber + ".Message", editString);
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
                        sender.sendMessage(prefixMessage + versionMessage1 + version);
                        sender.sendMessage(prefixMessage + versionMessage2);
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
                    //command manager
                    if (args[0].equalsIgnoreCase("commands") && args.length > 1) {
                        if (args[1].equalsIgnoreCase("add") && args.length > 2) {
                            //combine args into string
                            String[] split = Arrays.copyOfRange(args, 3, args.length);
                            commandAddString = String.join(" ", split);
                            //warn index
                            warnIndex = args[2];
                            //check if Warn exceeded maxwarns
                            //checks if warnIndex is valid
                            try {
                                tempWarnIndex = Byte.parseByte(warnIndex);
                            }
                            catch (NumberFormatException ex) {
                                tempWarnIndex = 0;
                            }
                            //checks if command is empty
                            try {
                                args[3].chars();
                            }
                            catch (ArrayIndexOutOfBoundsException ex) {
                                tempWarnIndex = 0;
                            }
                            if ((tempWarnIndex > 0 && tempWarnIndex <= maxWarns)) {
                                //set commandIndex
                                for (byte i = 1; i < 127; i++) {
                                    if (config.getString("Commands.Warn" + warnIndex + ".Command" + i) == null) {
                                        commandIndex = Byte.toString(i);
                                        i = 126;
                                    }     
                                }
                                if (config.getString("Commands.Warn" + warnIndex + ".Command" + commandIndex) == null) {
                                    config.set("Commands.Warn" + warnIndex + ".Command" + commandIndex, commandAddString);
                                    sender.sendMessage(prefixMessage + addedCommandSucceededMessage);
                                    //save file
                                    try {
                                        config.save(configf);
                                    } 
                                    catch (IOException ex) {
                                        Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    return true;
                                }
                            }
                            else {
                                sender.sendMessage(prefixMessage + addedCommandFailedMessage);
                                return true;
                            }
                        }
                        if (args[1].equalsIgnoreCase("delete")) {
                            if (args.length == 3) {
                                warnIndex = args[2];
                                try {
                                    tempWarnIndex = Byte.parseByte(warnIndex);
                                } 
                                catch (NumberFormatException ex) {
                                    tempWarnIndex = 0;
                                }
                                if (tempWarnIndex > 0 && tempWarnIndex <= maxWarns) {
                                    config.set("Commands.Warn" + tempWarnIndex, null);
                                    sender.sendMessage(prefixMessage + deleteCommandDeleteWarnMessage + tempWarnIndex);
                                    //save file
                                    try {
                                        config.save(configf);
                                    } 
                                    catch (IOException ex) {
                                        Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    return true;
                                }
                                else {
                                    sender.sendMessage(prefixMessage + incorrectCommandMessage);
                                    return false;
                                }
                            }
                            if (args.length == 4) {
                                warnIndex = args[2];
                                commandIndex = args[3];
                                byte tempCommandIndex;
                                try {
                                    tempWarnIndex = Byte.parseByte(warnIndex);
                                } 
                                catch (NumberFormatException ex) {
                                    tempWarnIndex = 0;
                                }
                                try {
                                    tempCommandIndex = Byte.parseByte(commandIndex);
                                } 
                                catch (NumberFormatException ex) {
                                    tempCommandIndex = 0;
                                }
                                if ((tempWarnIndex > 0 && tempWarnIndex <= maxWarns) && (tempCommandIndex > 0 && tempCommandIndex < 127)) {
                                    config.set("Commands.Warn" + tempWarnIndex + ".Command" + tempCommandIndex, null);
                                    //move any warns ahead of deleted variable in its place
                                    for (byte i = tempCommandIndex; i < 127; i++) {
                                        if (config.get("Commands.Warn" + tempWarnIndex + ".Command" + (i + 1)) != null) {
                                            config.set("Commands.Warn" + tempWarnIndex + ".Command" + i, config.getString("Commands.Warn" + tempWarnIndex + ".Command" + (i + 1)));
                                        } 
                                        else {
                                            config.set("Commands.Warn" + tempWarnIndex + ".Command" + i, null);
                                            i = 126;
                                        }
                                    }
                                    sender.sendMessage(prefixMessage + deleteCommandDeleteCommandStartMessage + tempCommandIndex + deleteCommandDeleteCommandEndMessage + tempWarnIndex);
                                    //save file
                                    try {
                                        config.save(configf);
                                    } 
                                    catch (IOException ex) {
                                        Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    return true;
                                }
                                else {
                                    sender.sendMessage(prefixMessage + incorrectCommandMessage);
                                    return false;
                                }
                            }
                            else {
                                sender.sendMessage(prefixMessage + incorrectCommandMessage);
                                return false;
                            }
                        }
                        if (args[1].equalsIgnoreCase("edit")) {
                            if (args.length >= 5 && (args[4] != null || !"".equals(args[4]))) {
                                warnIndex = args[2];
                                commandIndex = args[3];
                                byte tempCommandIndex;
                                try {
                                    tempWarnIndex = Byte.parseByte(warnIndex);
                                } 
                                catch (NumberFormatException ex) {
                                    tempWarnIndex = 0;
                                }
                                try {
                                    tempCommandIndex = Byte.parseByte(commandIndex);
                                } 
                                catch (NumberFormatException ex) {
                                    tempCommandIndex = 0;
                                }
                                //combine args into string
                                String[] split = Arrays.copyOfRange(args, 4, args.length);
                                commandEditString = String.join(" ", split);
                                if ((tempWarnIndex > 0 && tempWarnIndex <= maxWarns) && (tempCommandIndex > 0 && tempCommandIndex < 127)) {
                                    if (config.getString("Commands.Warn" + tempWarnIndex + ".Command" + tempCommandIndex) != null) {    
                                        config.set("Commands.Warn" + tempWarnIndex + ".Command" + tempCommandIndex, commandEditString);
                                        sender.sendMessage(editCommandEditStartMessage  + tempCommandIndex + editCommandEditEndMessage + tempWarnIndex);
                                        //save file
                                        try {
                                            config.save(configf);
                                        } 
                                        catch (IOException ex) {
                                            Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        return true;
                                    }
                                    else {
                                        sender.sendMessage(prefixMessage + editCommandEditFailedMessage);
                                        return false;
                                    }
                                }
                                else {
                                    sender.sendMessage(prefixMessage + incorrectCommandMessage);
                                    return false;
                                }
                            }
                            else {
                                sender.sendMessage(prefixMessage + incorrectCommandMessage);
                                return false;
                            }
                        }
                        if (args[1].equalsIgnoreCase("clear")) {
                            if (args.length == 2) {
                                config.set("Commands", "");
                                sender.sendMessage(prefixMessage + clearedCommandMessage);
                                //save file
                                try {
                                    config.save(configf);
                                } 
                                catch (IOException ex) {
                                    Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                return true;
                            }
                            else {
                                sender.sendMessage(prefixMessage + incorrectCommandMessage);
                                return false;
                            }
                        }
                        if (args[1].equalsIgnoreCase("list")) {
                            if (args.length == 2) {
                                sender.sendMessage(prefixMessage + listCommandStartMessage);
                                for (byte i = 1; i <= maxWarns; i++) {
                                    if (config.getString("Commands.Warn" + i) != null) {
                                        sender.sendMessage(listCommandWarnStartMessage + i + listCommandWarnEndMessage);
                                    }
                                    for (byte j = 1; j < 127; j++) {
                                        if (config.getString("Commands.Warn" + i + ".Command" + j) != null) {
                                            sender.sendMessage(listCommandCommandStartMessage + j + listCommandCommandEndMessage +config.getString("Commands.Warn" + i + ".Command" + j));
                                        }
                                        else {
                                            j = 126;
                                        }
                                    }
                                }
                                sender.sendMessage(prefixMessage + listCommandEndMessage);
                                return true;
                            }
                            if (args.length == 3) {
                                warnIndex = args[2];
                                try {
                                    tempWarnIndex = Byte.parseByte(warnIndex);
                                } 
                                catch (NumberFormatException ex) {
                                    tempWarnIndex = 0;
                                }
                                if (tempWarnIndex > 0 && tempWarnIndex < 127) {
                                    sender.sendMessage(prefixMessage + listCommandStartMessage);
                                    sender.sendMessage(listCommandWarnStartMessage + tempWarnIndex + listCommandWarnEndMessage);
                                    for (byte i=1; i < 127; i++) {
                                        if (config.getString("Commands.Warn" + tempWarnIndex + ".Command" + i) != null) {
                                            sender.sendMessage(listCommandCommandStartMessage + i + listCommandCommandEndMessage + config.getString("Commands.Warn" + tempWarnIndex + ".Command" + i));
                                        }
                                        else {
                                            i = 126;
                                        }
                                    }
                                    sender.sendMessage(prefixMessage + listCommandEndMessage); 
                                    return true;
                                }
                                else {
                                    sender.sendMessage(prefixMessage + incorrectCommandMessage);
                                    return false;
                                }
                            }
                            else {
                                sender.sendMessage(prefixMessage + incorrectCommandMessage);
                                return false;
                            }
                        }
                        else {
                            sender.sendMessage(prefixMessage + incorrectCommandMessage);
                            return false;
                        }
                    } 
                    else {
                        sender.sendMessage(prefixMessage + incorrectCommandMessage);
                        return false;
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
            arguments.add("commands");
            arguments.add("version");
            arguments.add("reload");
            return arguments;
        }
        //adminwarn commands args
        if (args.length == 2 && args[0].equalsIgnoreCase("commands")) {
            List<String> arguments = new ArrayList<>();
            arguments.add("add");
            arguments.add("delete");
            arguments.add("edit");
            arguments.add("clear");
            arguments.add("list");
            return arguments;
        }
        //commands add args
        if (args.length == 3 && args[1].equalsIgnoreCase("add")) {
            List<String> arguments = new ArrayList<>();
            FileConfiguration config = this.getCustomData(plugin, "config", ROOT);
            byte maxWarns = Byte.parseByte(config.getString("Max-Warns"));
            for (byte i=1; i <= maxWarns; i++) {
                arguments.add(Byte.toString(i));
            }
            return arguments;
        }
        //command clear and list args
        if (args.length == 3 && (args[1].equalsIgnoreCase("clear") || args[1].equalsIgnoreCase("list"))) {
            List<String> arguments = new ArrayList<>();
            FileConfiguration config = this.getCustomData(plugin, "config", ROOT);
            byte maxWarns = Byte.parseByte(config.getString("Max-Warns"));
            for (byte i=1; i <= maxWarns; i++) {
                if (config.getString("Commands.Warn" + i) != null) {
                    arguments.add(Byte.toString(i));
                }
            }
            return arguments;
        }
        //command delete and edit args
        if (args.length == 3 && (args[1].equalsIgnoreCase("edit") || args[1].equalsIgnoreCase("delete"))) {
            List<String> arguments = new ArrayList<>();
            FileConfiguration config = this.getCustomData(plugin, "config", ROOT);
            byte maxWarns = Byte.parseByte(config.getString("Max-Warns"));
            for (byte i=1; i <= maxWarns; i++) {
                if (config.getString("Commands.Warn" + i) != null) {
                    arguments.add(Byte.toString(i));
                }
            }
            return arguments;
        }
        if (args.length == 4 && (args[1].equalsIgnoreCase("edit") || args[1].equalsIgnoreCase("delete"))) {
            List<String> arguments = new ArrayList<>();
            FileConfiguration config = this.getCustomData(plugin, "config", ROOT);
            String warnIndex = args[2];
            byte tempWarnIndex;
            try {
                tempWarnIndex = Byte.parseByte(warnIndex);
            } 
            catch (NumberFormatException ex) {
                tempWarnIndex = 0;
            }       
            for (byte i=1; i < 127; i++) {
                if (config.getString("Commands.Warn" + tempWarnIndex + ".Command" + i) != null) {
                    arguments.add(Byte.toString(i));
                }
                else {
                    i = 126;
                }
            }
            return arguments;
        }
        //used to retrive indexes for edit and delete commands
        if (args.length == 3 && (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("delete"))) {
            FileConfiguration config = this.getCustomData(plugin, "config", ROOT);
            FileConfiguration pd = this.getCustomData(plugin, "playerdata", ROOT);
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

