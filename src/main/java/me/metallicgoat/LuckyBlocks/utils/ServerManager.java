package me.metallicgoat.LuckyBlocks.utils;

import me.metallicgoat.LuckyBlocks.Main;
import me.metallicgoat.LuckyBlocks.commands.Cmd;
import me.metallicgoat.LuckyBlocks.commands.TabComp;
import me.metallicgoat.LuckyBlocks.listeners.ArenaRegeneration;
import me.metallicgoat.LuckyBlocks.listeners.ArenaStart;
import me.metallicgoat.LuckyBlocks.listeners.BreakBlock;
import me.metallicgoat.LuckyBlocks.listeners.PlaceBlock;
import me.metallicgoat.LuckyBlocks.utils.configs.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ServerManager {

    public static void registerFiles() {
        ConfigManager.loadDefaultConfig();
        ConfigManager.loadCustomConfigs();
    }

    public static void registerEvents() {
        PluginManager manager = Bukkit.getServer().getPluginManager();
        manager.registerEvents(new PlaceBlock(), Main.getInstance());
        manager.registerEvents(new BreakBlock(), Main.getInstance());
        manager.registerEvents(new ArenaRegeneration(), Main.getInstance());
        manager.registerEvents(new ArenaStart(), Main.getInstance());
    }

    public static void registerCommands() {
        Main.getInstance().getCommand("LuckyBlock").setExecutor(new Cmd());
        Main.getInstance().getCommand("LuckyBlock").setTabCompleter(new TabComp());
    }

}
