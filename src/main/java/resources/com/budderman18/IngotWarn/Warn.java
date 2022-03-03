/*     */ package com.budderman18.IngotWarn;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.TabExecutor;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Warn
/*     */   implements TabExecutor
/*     */ {
/*  24 */   Plugin plugin = (Plugin)main.getInstance();
/*     */   
/*  26 */   final String ROOT = "";
/*     */   
/*  28 */   public File playerdataf = new File("plugins/IngotWarn", "playerdata.yml");
/*  29 */   public InstanceData getdata = new InstanceData();
/*     */   
/*  31 */   byte warnNumber = 0;
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/*     */     String player;
/*  35 */     YamlConfiguration yamlConfiguration1 = this.getdata.getCustomData(this.plugin, "config", "");
/*  36 */     YamlConfiguration yamlConfiguration2 = this.getdata.getCustomData(this.plugin, "language", "");
/*  37 */     YamlConfiguration yamlConfiguration3 = this.getdata.getCustomData(this.plugin, "playerdata", "");
/*     */     
/*  39 */     byte maxWarns = (byte)Integer.parseInt(yamlConfiguration1.getString("Max-Warns"));
/*     */     
/*  41 */     String prefixMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Prefix-Message"));
/*  42 */     String incorrectCommandMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Incorrect-Command-Message"));
/*  43 */     String noPermissionMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("No-Permission-Message"));
/*  44 */     String warnedMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Warned-Player-Message"));
/*  45 */     String youWarnedPlayerMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("You-Warned-Player-Message"));
/*  46 */     String noPlayerMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("No-Player-Message"));
/*  47 */     String limitReachedMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Limit-Reached-Message"));
/*     */ 
/*     */     
/*  50 */     if (null == Bukkit.getServer().getPlayer(args[0]).getName()) {
/*  51 */       player = " ";
/*     */     } else {
/*     */       
/*  54 */       player = Bukkit.getServer().getPlayer(args[0]).getName();
/*     */     } 
/*     */     
/*  57 */     Player target = Bukkit.getPlayer(player);
/*  58 */     Player username = Bukkit.getPlayer(player);
/*  59 */     String usernameString = this.getdata.convertUsername(username);
/*     */     byte i;
/*  61 */     for (i = 1; i <= maxWarns; i = (byte)(i + 1)) {
/*  62 */       if (yamlConfiguration3.getString(usernameString + ".Warn" + usernameString) == null) {
/*  63 */         this.warnNumber = i;
/*  64 */         i = maxWarns;
/*     */       } 
/*  66 */       this.warnNumber = (byte)(this.warnNumber + 1);
/*     */     } 
/*  68 */     if (cmd.getName().equalsIgnoreCase("warn")) {
/*  69 */       if (sender.hasPermission("ingotwarn.warn")) {
/*  70 */         if (args.length == 0) {
/*  71 */           sender.sendMessage(prefixMessage + prefixMessage + "at command");
/*  72 */           return false;
/*     */         } 
/*  74 */         if (yamlConfiguration3.getConfigurationSection(usernameString) != null) {
/*  75 */           if (args.length >= 2) {
/*  76 */             String warnReason = "";
/*  77 */             String[] split = Arrays.<String>copyOfRange(args, 1, args.length);
/*  78 */             warnReason = String.join(" ", (CharSequence[])split);
/*  79 */             if (this.warnNumber <= maxWarns + 1) {
/*  80 */               sender.sendMessage(prefixMessage + prefixMessage + youWarnedPlayerMessage);
/*  81 */               target.sendMessage(warnedMessage + warnedMessage);
/*     */               
/*  83 */               String warnNumberString = "Warn" + this.warnNumber - 1;
/*  84 */               this.warnNumber = (byte)(this.warnNumber + 1);
/*     */               
/*  86 */               yamlConfiguration3.set(usernameString + "." + usernameString, warnReason);
/*     */               try {
/*  88 */                 yamlConfiguration3.save(this.playerdataf);
/*     */               }
/*  90 */               catch (IOException ex) {
/*  91 */                 Logger.getLogger(Warn.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */               } 
/*  93 */               return true;
/*     */             } 
/*     */             
/*  96 */             sender.sendMessage(prefixMessage + prefixMessage);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 101 */           sender.sendMessage(prefixMessage + prefixMessage);
/* 102 */           return false;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 107 */       sender.sendMessage(prefixMessage + prefixMessage);
/* 108 */       return false;
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
/* 117 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Kyle Collins\Downloads\IngotWarn-1.0-SNAPSHOT.jar!\com\budderman18\IngotWarn\Warn.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */