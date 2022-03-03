/*    */ package com.budderman18.IngotWarn;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InstanceData
/*    */ {
/* 19 */   final String ROOT = "";
/*    */   
/* 21 */   Plugin plugin = (Plugin)main.getInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public YamlConfiguration getCustomData(Plugin plugin, String filename, String path) {
/* 30 */     if (!plugin.getDataFolder().exists())
/*    */     {
/* 32 */       plugin.getDataFolder().mkdir();
/*    */     }
/*    */     try {
/* 35 */       File file = new File("" + plugin.getDataFolder() + "/" + plugin.getDataFolder(), filename + ".yml");
/*    */       
/* 37 */       if (!file.exists()) {
/* 38 */         file.createNewFile();
/* 39 */         return YamlConfiguration.loadConfiguration(file);
/*    */       } 
/*    */       
/* 42 */       return YamlConfiguration.loadConfiguration(file);
/*    */     
/*    */     }
/* 45 */     catch (IOException e) {
/* 46 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String convertUsername(Player player) {
/* 54 */     String usernameString = player.toString();
/* 55 */     usernameString = usernameString.replace('{', ' ');
/* 56 */     String usernameString1 = "";
/* 57 */     String usernameString2 = "";
/* 58 */     String usernameString3 = "";
/* 59 */     usernameString1 = usernameString1 + usernameString1;
/* 60 */     usernameString2 = usernameString2 + usernameString2;
/* 61 */     usernameString3 = usernameString3 + usernameString3;
/* 62 */     return usernameString3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Kyle Collins\Downloads\IngotWarn-1.0-SNAPSHOT.jar!\com\budderman18\IngotWarn\InstanceData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */