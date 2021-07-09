package me.metallicgoat.LuckyBlocks;

import com.google.common.collect.BiMap;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.atomic.AtomicInteger;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Main plugin = Main.getInstance();
        if (args.length == 1 && args[0].equalsIgnoreCase("give")) {
            if (sender instanceof Player) {

                ItemStack LuckyBlock = getSkull();

                ItemMeta meta = LuckyBlock.getItemMeta();

                assert meta != null;
                PersistentDataContainer data = meta.getPersistentDataContainer();

                meta.setDisplayName("LuckyBlock");
                data.set(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING, "orange");

                LuckyBlock.setItemMeta(meta);
                LuckyBlock.setAmount(20);

                ((Player) sender).getInventory().addItem(LuckyBlock);

                return true;
            } else {
                return true;
            }
        }else if (args.length == 1 && args[0].equalsIgnoreCase("map")){
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.GREEN + "LuckyBlocks: " + ChatColor.RED + getNumBlocksInMap());
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

    private ItemStack getSkull(){
        return Heads.getSkull("9fb9a082-e047-11eb-ba80-0242ac130004", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RkMWU2YmQyMTVhZmE1ZTY3MzI4NWFmYWNiODVlYjhkMGY3OWE1YjQ2YzU0MzJkNmZlZWQ2NjA5N2M1MTI0OCJ9fX0=");
    }
}