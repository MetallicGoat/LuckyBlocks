package me.metallicgoat.LuckyBlocks.commands;

import com.google.common.collect.BiMap;
import me.metallicgoat.LuckyBlocks.utils.GetConfig;
import me.metallicgoat.LuckyBlocks.utils.SkullBuilder;
import me.metallicgoat.LuckyBlocks.Main;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

public class Cmd implements CommandExecutor {

    //commands are for testing purposes

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            if (sender instanceof Player) {

                final String type = args[1];

                final String name = GetConfig.getLuckyString(type, "Name");
                final String texture = GetConfig.getLuckyString(type, "Texture");
                final String textureUUID = GetConfig.getLuckyString(type, "UUID");

                ItemStack LuckyBlock = getSkull(textureUUID, texture, 20, name, type);

                ((Player) sender).getInventory().addItem(LuckyBlock);

                return true;
            } else {
                return true;
            }
        }else if (args.length == 1 && args[0].equalsIgnoreCase("map")){
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.GREEN + "LuckyBlocks: " + ChatColor.RED + getNumBlocksInMap());
                return true;
            }
        }
        return false;
    }


    private BiMap<Block, ArmorStand> biMap(){
        return Main.getInstance().getBiMap();
    }

    private int getNumBlocksInMap(){
        AtomicInteger i = new AtomicInteger();
        biMap().forEach((block, armorStand) -> i.getAndIncrement());
        return i.get();
    }

    private ItemStack getSkull(String textureUUID, String texture, int amount, String name, String type){
        return SkullBuilder.getSkull(textureUUID, texture, amount, name, type);
    }
}