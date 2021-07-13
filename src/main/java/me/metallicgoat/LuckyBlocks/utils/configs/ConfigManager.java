package me.metallicgoat.LuckyBlocks.utils.configs;

import me.metallicgoat.LuckyBlocks.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager implements Config {

    @Override
    public FileConfiguration getDefaultConfig(){
        return Main.getInstance().getConfig();
    }

    @Override
    public FileConfiguration getLuckyBlocksConfig(){
        return configLoader().getLuckyBlocksConfig();
    }

    @Override
    public FileConfiguration getLuckyDropsConfig(){
        return configLoader().getLuckyDropsConfig();
    }

    @Override
    public String getLuckyBlockStringAttribute(String type, String path){
        return configLoader().getLuckyBlocksConfig().getString("LuckyBlocks." + type + "." + path);
    }

    @Override
    public int getLuckyBlockIntAttribute(String type, String path){
        return configLoader().getLuckyBlocksConfig().getInt("LuckyBlocks." + type + "." + path);
    }

    @Override
    public long getLuckyBlockLongAttribute(String type, String path){
        return configLoader().getLuckyBlocksConfig().getLong("LuckyBlocks." + type + "." + path);
    }

    private static ConfigLoader configLoader(){
        return new ConfigLoader();
    }
}
