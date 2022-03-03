/*     */ package com.budderman18.IngotWarn;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class main
/*     */   extends JavaPlugin
/*     */   implements Listener
/*     */ {
/*  28 */   final String ROOT = "";
/*     */   
/*  30 */   InstanceData getdata = new InstanceData();
/*     */ 
/*     */   
/*     */   private static main plugin;
/*     */ 
/*     */ 
/*     */   
/*     */   private void createFiles() {
/*  38 */     File configf = new File(getDataFolder(), "config.yml");
/*  39 */     if (!configf.exists()) {
/*  40 */       configf.getParentFile().mkdirs();
/*  41 */       saveResource("config.yml", false);
/*     */     } 
/*     */     
/*  44 */     YamlConfiguration yamlConfiguration1 = new YamlConfiguration();
/*     */     try {
/*  46 */       yamlConfiguration1.load(configf);
/*  47 */     } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
/*  48 */       e.printStackTrace();
/*     */     } 
/*  50 */     File playerdataf = new File(getDataFolder(), "playerdata.yml");
/*  51 */     if (!playerdataf.exists()) {
/*  52 */       playerdataf.getParentFile().mkdirs();
/*  53 */       saveResource("playerdata.yml", false);
/*     */     } 
/*     */     
/*  56 */     YamlConfiguration yamlConfiguration2 = new YamlConfiguration();
/*     */     try {
/*  58 */       yamlConfiguration2.load(playerdataf);
/*  59 */     } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
/*  60 */       e.printStackTrace();
/*     */     } 
/*  62 */     File languagef = new File(getDataFolder(), "language.yml");
/*  63 */     if (!languagef.exists()) {
/*  64 */       languagef.getParentFile().mkdirs();
/*  65 */       saveResource("language.yml", false);
/*     */     } 
/*     */     
/*  68 */     YamlConfiguration yamlConfiguration3 = new YamlConfiguration();
/*     */     try {
/*  70 */       yamlConfiguration3.load(languagef);
/*  71 */     } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
/*  72 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static main getInstance() {
/*  79 */     return plugin;
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  83 */     plugin = this;
/*  84 */     createFiles();
/*     */     
/*  86 */     YamlConfiguration yamlConfiguration1 = this.getdata.getCustomData((Plugin)plugin, "config", "");
/*  87 */     YamlConfiguration yamlConfiguration2 = this.getdata.getCustomData((Plugin)plugin, "language", "");
/*  88 */     YamlConfiguration yamlConfiguration3 = this.getdata.getCustomData((Plugin)plugin, "playerdata", "");
/*     */     
/*  90 */     String prefixMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Prefix-Message"));
/*  91 */     String unsupportedVersionAMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Unsupported-VersionA-Message"));
/*  92 */     String unsupportedVersionBMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Unsupported-VersionB-Message"));
/*  93 */     String unsupportedVersionCMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Unsupported-VersionC-Message"));
/*  94 */     String unsecureServerAMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Unsecure-ServerA-Message"));
/*  95 */     String unsecureServerBMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Unsecure-ServerB-Message"));
/*  96 */     String unsecureServerCMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Unsecure-ServerC-Message"));
/*  97 */     String pluginEnabledMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Plugin-Enabled-Message"));
/*     */     
/*  99 */     if (!Bukkit.getVersion().contains("1.18.2")) {
/* 100 */       getServer().getConsoleSender().sendMessage(prefixMessage + prefixMessage);
/* 101 */       getServer().getConsoleSender().sendMessage(prefixMessage + prefixMessage);
/* 102 */       getServer().getConsoleSender().sendMessage(prefixMessage + prefixMessage);
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     if (!getServer().getOnlineMode()) {
/* 107 */       getServer().getConsoleSender().sendMessage(prefixMessage + prefixMessage);
/* 108 */       getServer().getConsoleSender().sendMessage(prefixMessage + prefixMessage);
/* 109 */       getServer().getConsoleSender().sendMessage(prefixMessage + prefixMessage);
/* 110 */       getServer().getPluginManager().disablePlugin((Plugin)this);
/*     */     } 
/*     */     
/* 113 */     getCommand("warn").setExecutor((CommandExecutor)new Warn());
/* 114 */     getCommand("checkwarns").setExecutor((CommandExecutor)new CheckWarn());
/*     */     
/* 116 */     getServer().getPluginManager().registerEvents(this, (Plugin)this);
/* 117 */     getServer().getPluginManager().enablePlugin((Plugin)this);
/* 118 */     getServer().getConsoleSender().sendMessage(prefixMessage + prefixMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
/* 126 */     Plugin plugin = getServer().getPluginManager().getPlugin("IngotWarn");
/* 127 */     YamlConfiguration yamlConfiguration1 = this.getdata.getCustomData(plugin, "language", "");
/* 128 */     YamlConfiguration yamlConfiguration2 = this.getdata.getCustomData(plugin, "playerdata", "");
/* 129 */     File playerdataf = new File("plugins/IngotWarn", "playerdata.yml");
/*     */     
/* 131 */     String prefixMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration1.getString("Prefix-Message"));
/* 132 */     String newPlayerMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration1.getString("New-Player-Message"));
/*     */     
/* 134 */     Player username = event.getPlayer();
/* 135 */     String usernameString = this.getdata.convertUsername(username);
/* 136 */     if (yamlConfiguration2.getString(usernameString) == null) {
/*     */       
/* 138 */       yamlConfiguration2.createSection(usernameString);
/*     */       
/* 140 */       getServer().getConsoleSender().sendMessage(prefixMessage + prefixMessage);
/* 141 */       getServer().getConsoleSender().sendMessage("pdf = " + playerdataf.toString());
/* 142 */       getServer().getConsoleSender().sendMessage("pd = " + yamlConfiguration2.toString());
/*     */       
/* 144 */       UUID uuid = username.getUniqueId();
/* 145 */       yamlConfiguration2.set(usernameString + ".UUID", uuid.toString());
/*     */       
/* 147 */       yamlConfiguration2.save(playerdataf);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 156 */     Plugin plugin = getServer().getPluginManager().getPlugin("IngotWarn");
/* 157 */     File configf = new File("plugins/IngotWarn", "config.yml");
/* 158 */     File languagef = new File("plugins/IngotWarn", "language.yml");
/* 159 */     File playerdataf = new File("plugins/IngotWarn", "playerdata.yml");
/* 160 */     YamlConfiguration yamlConfiguration1 = this.getdata.getCustomData(plugin, "config", "");
/* 161 */     YamlConfiguration yamlConfiguration2 = this.getdata.getCustomData(plugin, "language", "");
/* 162 */     YamlConfiguration yamlConfiguration3 = this.getdata.getCustomData(plugin, "playerdata", "");
/*     */     
/* 164 */     String prefixMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Prefix-Message"));
/* 165 */     String pluginDisabledMessage = ChatColor.translateAlternateColorCodes('&', yamlConfiguration2.getString("Plugin-Disabled-Message"));
/*     */     
/*     */     try {
/* 168 */       yamlConfiguration1.save(configf);
/*     */     }
/* 170 */     catch (IOException ex) {
/* 171 */       Logger.getLogger(main.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */     try {
/* 174 */       yamlConfiguration2.save(languagef);
/*     */     }
/* 176 */     catch (IOException ex) {
/* 177 */       Logger.getLogger(main.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/*     */     try {
/* 180 */       yamlConfiguration3.save(playerdataf);
/*     */     }
/* 182 */     catch (IOException ex) {
/* 183 */       Logger.getLogger(main.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } 
/* 185 */     getServer().getPluginManager().disablePlugin((Plugin)this);
/* 186 */     getServer().getConsoleSender().sendMessage(prefixMessage + prefixMessage);
/*     */   }
/*     */ }


/* Location:              C:\Users\Kyle Collins\Downloads\IngotWarn-1.0-SNAPSHOT.jar!\com\budderman18\IngotWarn\main.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */