package me.metallicgoat.LuckyBlocks.utils.configs;

public class GetConfig {
    public static String getLuckyBlockStringAttribute(String type, String path){
        return configManager().getLuckyBlocksConfig().getString("LuckyBlocks." + type + "." + path);
    }

    public static int getLuckyBlockIntAttribute(String type, String path){
        return configManager().getLuckyBlocksConfig().getInt("LuckyBlocks." + type + "." + path);
    }

    public static long getLuckyBlockLongAttribute(String type, String path){
        return configManager().getLuckyBlocksConfig().getLong("LuckyBlocks." + type + "." + path);
    }

    private static ConfigManager configManager(){
        return new ConfigManager();
    }
}
