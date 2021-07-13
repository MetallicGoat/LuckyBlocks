package me.metallicgoat.LuckyBlocks.utils.configs;

import org.bukkit.configuration.file.FileConfiguration;

public interface Config {

    String getLuckyBlockStringAttribute(String type, String path);

    long getLuckyBlockLongAttribute(String type, String path);

    int getLuckyBlockIntAttribute(String type, String path);

    FileConfiguration getDefaultConfig();

    FileConfiguration getLuckyBlocksConfig();

    FileConfiguration getLuckyDropsConfig();

}
