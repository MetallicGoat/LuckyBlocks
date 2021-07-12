package me.metallicgoat.LuckyBlocks;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.metallicgoat.LuckyBlocks.commands.Cmd;
import me.metallicgoat.LuckyBlocks.listeners.ArenaRegeneration;
import me.metallicgoat.LuckyBlocks.listeners.ArenaStart;
import me.metallicgoat.LuckyBlocks.listeners.BreakBlock;
import me.metallicgoat.LuckyBlocks.listeners.PlaceBlock;
import me.metallicgoat.LuckyBlocks.utils.ConfigUpdater;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main extends JavaPlugin {

    private static Main instance;
    public BiMap<Block, ArmorStand> biMap = HashBiMap.create();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private final Server server = getServer();

    public void onEnable() {
        int pluginId = 11753;
        //Metrics metrics = new Metrics(this, pluginId);

        loadConfig();
        registerEvents();
        registerCommands();

        instance = this;
        PluginDescriptionFile pdf = this.getDescription();

        log(
                "------------------------------",
                pdf.getName() + " For MBedwars",
                "By: " + pdf.getAuthors(),
                "Version: " + pdf.getVersion(),
                "------------------------------"
        );

    }

    private void registerEvents() {
        PluginManager manager = this.server.getPluginManager();
        manager.registerEvents(new PlaceBlock(), this);
        manager.registerEvents(new BreakBlock(), this);
        manager.registerEvents(new ArenaRegeneration(), this);
        manager.registerEvents(new ArenaStart(), this);
    }

    private void registerCommands() {
        getCommand("LuckyBlock").setExecutor(new Cmd());
        //getCommand("LuckyBlock").setTabCompleter(new tabCompleter());
    }

    public static Main getInstance() {
        return instance;
    }

    public BiMap<Block, ArmorStand> getBiMap() {
        return biMap;
    }

    public ConsoleCommandSender getConsole() {
        return console;
    }

    private void loadConfig(){
        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");

        try {
            ConfigUpdater.update(this, "config.yml", configFile, Arrays.asList("Nothing", "here"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadConfig();
    }

    private void log(String ...args) {
        for(String s : args)
            getLogger().info(s);
    }
}