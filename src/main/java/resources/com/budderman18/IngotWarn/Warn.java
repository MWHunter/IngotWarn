
package com.budderman18.IngotWarn;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
/**
 * This class handles the warn command
 */
public class Warn implements TabExecutor {
    //retrive plugin instance
    Plugin plugin = main.getInstance();
    //used if the given file isnt in another folder
    final String ROOT = "";
    //imports files
    File playerdataf = new File("plugins/IngotWarn","playerdata.yml");
    AdminWarn getdata = new AdminWarn();
    //warn variables
    byte warnNumber = 0;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ConsoleCommandSender csender = getServer().getConsoleSender();
        //files
        FileConfiguration config = getdata.getCustomData(plugin,"config",ROOT);
        FileConfiguration language = getdata.getCustomData(plugin,"language",ROOT);
        FileConfiguration pd = getdata.getCustomData(plugin,"playerdata",ROOT);
        //max warns
        byte maxWarns = (byte) Integer.parseInt(config.getString("Max-Warns"));
        boolean isNotified = false;
        //language variables
        String prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message")); 
        String incorrectCommandMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Incorrect-Command-Message"));
        String noPermissionMessage = ChatColor.translateAlternateColorCodes('&', language.getString("No-Permission-Message"));
        String warnedMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Warned-Player-Message"));
        String youWarnedPlayerMessage = ChatColor.translateAlternateColorCodes('&', language.getString("You-Warned-Player-Message"));
        String noPlayerMessage = ChatColor.translateAlternateColorCodes('&', language.getString("No-Player-Message"));
        String limitReachedMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Limit-Reached-Message"));
        if (cmd.getName().equalsIgnoreCase("warn")) {
            //check for permission before running
            if (sender.hasPermission("ingotwarn.warn")) {
                //check if blank to prevent errors
                if (args.length == 0) {
                    sender.sendMessage(prefixMessage + incorrectCommandMessage);
                    return false;
                }
                //set username
                String usernameString = args[0];
                //check if entered username has data
                if (pd.getConfigurationSection(usernameString) != null) {
                    //retrives username
                    String player = args[0];
                    Player target;
                    //check if no reason is entered
                    if (args.length == 1) {
                        sender.sendMessage(prefixMessage + incorrectCommandMessage);
                        return false;
                    }
                    if (args.length >= 2) {
                        //convert command args to a string
                        String warnReason = "";
                        String[] split = Arrays.copyOfRange(args, 1, args.length);
                        warnReason = String.join(" ", split);
                        //gets current warn count
                        for (byte i = 1; i <= (maxWarns); i++) {
                            if ((pd.getString(usernameString + ".Warn" + i + ".Message") == null) || (pd.getString(usernameString + ".Warn" + i + ".Message") == "")) {
                                warnNumber = i;
                                i = maxWarns;
                            }
                            warnNumber += 1;
                        }
                        //check if warn limit reached
                        if (warnNumber <= (maxWarns+1) && (warnNumber != maxWarns)) {
                            sender.sendMessage(prefixMessage + youWarnedPlayerMessage + player);
                            if (Bukkit.getServer().getPlayer(args[0]) != null) {
                                target = Bukkit.getServer().getPlayer(args[0]);
                                target.sendMessage(warnedMessage + warnReason);
                                isNotified = true;
                            }
                            //set warn number
                            String warnNumberString = "Warn" + (warnNumber-1);
                            warnNumber+=1;                            
                            if (warnNumber > maxWarns) {
                                warnNumber=maxWarns;
                            }
                            //check for overflow
                            if (warnNumber <= 0) {
                                warnNumber=maxWarns;
                            }
                            //update file
                            if ((warnNumber <= maxWarns) && (warnNumber > 0)) {
                                pd.set(usernameString + '.' + warnNumberString + ".Message", warnReason);
                                pd.set(usernameString + '.' + warnNumberString + '.' + "isNotified", Boolean.toString(isNotified));
                            }
                            //execute commands
                            for (byte i = (byte) (warnNumber-2); i < 127; i++) {
                                if (config.getString("Commands.Warn" + i) != null) {
                                    for (byte j = 1; j < 127; j++) {
                                        if (config.getString("Commands.Warn" + i + ".Command" + j) != null) {
                                            String command = config.getString("Commands.Warn" + i + ".Command" + j);
                                            if (command.contains("%player%")) {
                                                command = command.replaceAll("%player%", usernameString);
                                            }
                                            if (command.contains("%target%")) {
                                                command = command.replaceAll("%target%", sender.getName());
                                            }
                                            getServer().dispatchCommand(csender, command);
                                        }
                                        else {
                                            j = 126;
                                            i = 126;
                                        }
                                    }
                                }
                                else {
                                    i = 126;
                                }
                            }
                            //save file
                            try {
                                pd.save(playerdataf);
                            } 
                            catch (IOException ex) {
                                Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            return true;
                        }
                        //say when limit it reached
                        else {
                            sender.sendMessage(prefixMessage + limitReachedMessage);
                            return true;
                        }
                    }
                } 
                //send if player not found
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
