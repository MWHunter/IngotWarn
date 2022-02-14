/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Kyle Collins
 */
public class Warn implements TabExecutor {
    Plugin plugin = Bukkit.getPluginManager().getPlugin("IngotWarn");
    //files
    File playerdataf = new File(plugin.getDataFolder(), "playerdata.yml");
    FileConfiguration pd = YamlConfiguration.loadConfiguration(playerdataf);
    File languagef = new File(plugin.getDataFolder(), "language.yml");
    FileConfiguration language = YamlConfiguration.loadConfiguration(languagef);
    //language variables
    String prefixMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Prefix-Message"));
    String incorrectCommandMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Incorrect-Command-Message"));
    String noPermissionMessage = ChatColor.translateAlternateColorCodes('&', language.getString("No-Permission-Message"));
    String warnedMessage = ChatColor.translateAlternateColorCodes('&', language.getString("Warned-Player-Message"));
    String youWarnedMessage = ChatColor.translateAlternateColorCodes('&', language.getString("You-Warned-Player-Message"));
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
       String player = Bukkit.getServer().getPlayer(args[0]).getName();
       Player target = Bukkit.getPlayer(player);
       Player warnTarget;
       if (cmd.getName().equalsIgnoreCase("warn")) {
           if (sender.hasPermission("ingotwarn.warn")) {
                if (args.length == 0) {
                sender.sendMessage(prefixMessage + incorrectCommandMessage + "at command");
                return false;
            }
                if (args[0].equalsIgnoreCase(player)) {
                    if (args.length >= 2) {
                        String warnReason = "";
                        String[] split = Arrays.copyOfRange(args, 1, args.length);
                        warnReason = String.join(" ", split);
                        sender.sendMessage(youWarnedMessage + player);
                        target.sendMessage(warnedMessage + warnReason);
                        return true;
                    }
                }  
                else {
                    sender.sendMessage(prefixMessage + incorrectCommandMessage + "at reason");
                    return false;
                }
           }
           else {
               sender.sendMessage(prefixMessage + noPermissionMessage + "at player");
               return false;
           }
       }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        
        return null;
    }
    
}
