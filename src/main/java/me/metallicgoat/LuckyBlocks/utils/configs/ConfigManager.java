package me.metallicgoat.LuckyBlocks.utils.configs;

import me.metallicgoat.LuckyBlocks.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ConfigManager {

    private static FileConfiguration luckyBlocks;
    private static FileConfiguration luckyDrops;

    public FileConfiguration getLuckyBlocksConfig() {
        return luckyBlocks;
    }

    public FileConfiguration getLuckyDropsConfig() {
        return luckyDrops;
    }

    public static void loadDefaultConfig(){
        Main.getInstance().saveDefaultConfig();
        File configFile = new File(Main.getInstance().getDataFolder(), "config.yml");

        try {
            ConfigUpdater.update(Main.getInstance(), "config.yml", configFile, Arrays.asList("Nothing", "here"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Main.getInstance().reloadConfig();
    }


    public static void loadCustomConfigs() {
        File luckyDropsFile = new File(Main.getInstance().getDataFolder(), "LuckyDrops.yml");
        File luckyBlocksFile = new File(Main.getInstance().getDataFolder(), "LuckyBlocks.yml");
        if (!luckyDropsFile.exists()) {
            luckyDropsFile.getParentFile().mkdirs();
            Main.getInstance().saveResource("LuckyDrops.yml", false);
        }
        if (!luckyBlocksFile.exists()) {
            luckyBlocksFile.getParentFile().mkdirs();
            Main.getInstance().saveResource("LuckyBlocks.yml", false);
        }

        luckyDrops = new YamlConfiguration();
        luckyBlocks = new YamlConfiguration();
        try {
            luckyDrops.load(luckyDropsFile);
            luckyBlocks.load(luckyBlocksFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
