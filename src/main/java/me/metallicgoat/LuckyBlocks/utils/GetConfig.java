package me.metallicgoat.LuckyBlocks.utils;

import me.metallicgoat.LuckyBlocks.Main;

public class GetConfig {
    public static String getLuckyString(String type, String path){
        return plugin().getConfig().getString("LuckyBlocks." + type + "." + path);
    }

    public static int getLuckyInt(String type, String path){
        return plugin().getConfig().getInt("LuckyBlocks." + type + "." + path);
    }

    private static Main plugin(){
        return Main.getInstance();
    }
}
