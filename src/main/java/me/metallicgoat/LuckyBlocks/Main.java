package me.metallicgoat.LuckyBlocks;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.metallicgoat.LuckyBlocks.utils.ServerManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    public BiMap<Block, ArmorStand> biMap = HashBiMap.create();
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private final Server server = getServer();

    public void onEnable() {
        int pluginId = 11753;
        //Metrics metrics = new Metrics(this, pluginId);

        //loadConfig();

        ServerManager.registerFiles();
        ServerManager.registerCommands();
        ServerManager.registerEvents();

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

    public static Main getInstance() {
        return instance;
    }

    public BiMap<Block, ArmorStand> getBiMap() {
        return biMap;
    }

    public ConsoleCommandSender getConsole() {
        return console;
    }


    private void log(String ...args) {
        for(String s : args)
            getLogger().info(s);
    }
}