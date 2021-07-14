package me.metallicgoat.LuckyBlocks.utils.configs;

import me.metallicgoat.LuckyBlocks.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public FileConfiguration getDefaultConfig(){
        return Main.getInstance().getConfig();
    }

    public FileConfiguration getLuckyBlocksConfig(){
        return configLoader().getLuckyBlocksConfig();
    }

    public FileConfiguration getLuckyDropsConfig(){
        return configLoader().getLuckyDropsConfig();
    }

    public String getLuckyBlockStringAttribute(String type, String path){
        return configLoader().getLuckyBlocksConfig().getString("LuckyBlocks." + type + "." + path);
    }

    public int getLuckyBlockIntAttribute(String type, String path){
        return configLoader().getLuckyBlocksConfig().getInt("LuckyBlocks." + type + "." + path);
    }

    public long getLuckyBlockLongAttribute(String type, String path){
        return configLoader().getLuckyBlocksConfig().getLong("LuckyBlocks." + type + "." + path);
    }

    private static ConfigLoader configLoader(){
        return new ConfigLoader();
    }
}
