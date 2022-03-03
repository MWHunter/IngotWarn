/*    */ package com.budderman18.IngotWarn;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.TabExecutor;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckWarn
/*    */   implements TabExecutor
/*    */ {
/* 21 */   Plugin plugin = (Plugin)main.getInstance();
/*    */   
/* 23 */   final String ROOT = "";
/*    */   
/* 25 */   InstanceData getdata = new InstanceData();
/* 26 */   FileConfiguration config = (FileConfiguration)this.getdata.getCustomData(this.plugin, "config", "");
/* 27 */   FileConfiguration language = (FileConfiguration)this.getdata.getCustomData(this.plugin, "language", "");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 41 */     YamlConfiguration yamlConfiguration = this.getdata.getCustomData(this.plugin, "playerdata", "");
/*    */     
/* 43 */     String prefixMessage = ChatColor.translateAlternateColorCodes('&', this.language.getString("Prefix-Message"));
/* 44 */     String incorrectCommandMessage = ChatColor.translateAlternateColorCodes('&', this.language.getString("Incorrect-Command-Message"));
/* 45 */     String noPermissionMessage = ChatColor.translateAlternateColorCodes('&', this.language.getString("No-Permission-Message"));
/* 46 */     String noPlayerMessage = ChatColor.translateAlternateColorCodes('&', this.language.getString("No-Player-Message"));
/*    */     
/* 48 */     String player = Bukkit.getServer().getPlayer(sender.getName()).getName();
/* 49 */     Player username = Bukkit.getPlayer(player);
/* 50 */     String usernameString = this.getdata.convertUsername(username);
/* 51 */     if (cmd.getName().equalsIgnoreCase("checkwarns") && 
/* 52 */       args.length == 0) {
/* 53 */       if (sender.hasPermission("ingotwarn.checkwarns")) {
/*    */         
/* 55 */         sender.sendMessage("Player: " + usernameString);
/* 56 */         byte maxWarns = (byte)Integer.parseInt(this.config.getString("Max-Warns"));
/* 57 */         sender.sendMessage("UUID: " + yamlConfiguration.getString(usernameString + ".UUID"));
/* 58 */         sender.sendMessage("Max Warns: " + maxWarns); byte i;
/* 59 */         for (i = 1; i < maxWarns + 1; i = (byte)(i + 1)) {
/* 60 */           if (yamlConfiguration.get(usernameString + ".Warn" + usernameString) != null) {
/* 61 */             sender.sendMessage("Warn" + i + ": " + yamlConfiguration.get(usernameString + ".Warn" + usernameString));
/*    */           } else {
/*    */             
/* 64 */             i = maxWarns;
/*    */           } 
/*    */         } 
/* 67 */         sender.sendMessage("Done!");
/* 68 */         return true;
/*    */       } 
/*    */       
/* 71 */       sender.sendMessage(prefixMessage + prefixMessage);
/* 72 */       return false;
/*    */     } 
/*    */ 
/*    */     
/* 76 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
/* 83 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Kyle Collins\Downloads\IngotWarn-1.0-SNAPSHOT.jar!\com\budderman18\IngotWarn\CheckWarn.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */