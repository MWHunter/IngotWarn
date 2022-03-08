package com.budderman18.IngotWarn;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
/**
 * This class handles the checkwarns command
 */
public class CheckWarn implements TabExecutor {
    //retrive plugin instance
    Plugin plugin = main.getInstance();
    //used if the given file isnt in another folder
    final String ROOT = "";
    //imports files
    FileUpdater getdata = new FileUpdater();
    FileConfiguration config = getdata.getCustomData(plugin,"config",ROOT);
    FileConfiguration language = getdata.getCustomData(plugin,"language",ROOT);
    /**
     * This method handles everything related to the checkwarns command
     * TabCompletion has its own method
     * 
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return 
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //import userfile
        FileConfiguration pd = getdata.getCustomData(plugin,"playerdata",ROOT);
        //language variables
        String prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message")); 
        String noPermissionMessage = ChatColor.translateAlternateColorCodes('&', language.getString("No-Permission-Message"));
        String noPlayerMessage = ChatColor.translateAlternateColorCodes('&', language.getString("No-Player-Message"));
        String playerMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Player-Message"));
        String uuidMessage = ChatColor.translateAlternateColorCodes('&', language.getString("UUID-Message"));
        String warnNumberMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Warn-Number-Message"));
        String warnStartMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Warn-Start-Message"));
        String warnEndMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Warn-End-Message"));
        String isNotifiedMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Is-Notified-Message"));
        String startOfListMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Start-Of-List-Message"));
        String endOfListMessage = ChatColor.translateAlternateColorCodes('&', language.getString("End-Of-List-Message"));
        if (cmd.getName().equalsIgnoreCase("checkwarns")) {
            //check if the defaukt player is used (sender)
            if (args.length == 0) {
                //check if sender is a player
                if ((sender instanceof Player)) {
                    //gets sender's username
                    String player = Bukkit.getServer().getPlayer(sender.getName()).getName();
                    Player username = Bukkit.getPlayer(player);
                    String usernameString = username.getName();
                    //check for permission
                    if (sender.hasPermission("ingotwarn.checkwarns")) {
                        //outputs userfile
                        sender.sendMessage(prefixMessage + startOfListMessage);
                        sender.sendMessage(playerMessage + usernameString);
                        byte maxWarns = (byte) Integer.parseInt(config.getString("Max-Warns"));
                        sender.sendMessage(uuidMessage + pd.getString(usernameString + ".UUID"));
                        for (byte i=1; i < maxWarns + 1; i++) {
                            if (pd.get(usernameString+".Warn" + i + ".Message") != null) {
                                sender.sendMessage(warnNumberMessage + i + ':');
                                sender.sendMessage("    " + warnStartMessage + i + warnEndMessage + pd.getString(usernameString+".Warn" + i + ".Message"));
                                sender.sendMessage("    " + isNotifiedMessage + pd.getBoolean(usernameString + ".Warn" + i + ".isNotified"));
                            }
                            else {
                                i = maxWarns;
                            }
                        }
                        sender.sendMessage(prefixMessage + endOfListMessage);
                        return true;
                    }
                    //send if player lacks permission
                    else {
                        sender.sendMessage(prefixMessage + noPermissionMessage);
                        return true;
                    }
                }
                //send if sender is not a player
                else {
                    return false;
                }
            }
            //check if a user is specified
            if (args.length >= 1) {
                String usernameString = args[0];
                //check if player has permission
                if (sender.hasPermission("ingotwarn.checkwarns.others")) {
                    if (pd.getConfigurationSection(usernameString) != null) {
                        //outputs userfile
                        sender.sendMessage(prefixMessage + startOfListMessage);
                        sender.sendMessage(playerMessage + usernameString);
                        byte maxWarns = (byte) Integer.parseInt(config.getString("Max-Warns"));
                        sender.sendMessage(uuidMessage + pd.getString(usernameString + ".UUID"));
                        for (byte i=1; i < maxWarns + 1; i++) {
                            if (pd.get(usernameString+".Warn" + i + ".Message") != null) {
                                sender.sendMessage(warnNumberMessage + i + ':');
                                sender.sendMessage("    " + warnStartMessage + i + warnEndMessage + pd.getString(usernameString+".Warn" + i + ".Message"));
                                sender.sendMessage("    " + isNotifiedMessage + pd.getBoolean(usernameString + ".Warn" + i + ".isNotified"));
                            }
                            else {
                                i = maxWarns;
                            }
                        }
                        sender.sendMessage(prefixMessage + endOfListMessage);
                        return true;
                    }
                    //send if player isnt found
                    else {
                        sender.sendMessage(prefixMessage + noPlayerMessage);
                        return true;
                    }
                }
                //send if lacking permission
                else {
                    sender.sendMessage(prefixMessage + noPermissionMessage);
                    return true;
                }
            }
        }
        return false;
    }
    /*
    * This method handles tabcompletion when required
    */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
