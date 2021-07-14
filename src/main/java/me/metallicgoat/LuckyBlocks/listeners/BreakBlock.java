package me.metallicgoat.LuckyBlocks.listeners;

import com.google.common.collect.BiMap;
import me.metallicgoat.LuckyBlocks.Main;
import me.metallicgoat.LuckyBlocks.utils.DropItem;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BreakBlock implements Listener {

    @EventHandler
    //Removes the armorstand, and removes the block from the map
    public void onBreak(BlockBreakEvent e){
        final Block block = e.getBlock();
        if(biMap().containsKey(block)){
            biMap().get(block).remove();
            dropItem(block);
        }
    }

    private void dropItem(Block block){
        //TODO: Random drop system
        Location loc = block.getLocation();

        //world.dropItemNaturally(loc, new ItemStack(Material.valueOf(plugin.getConfig().getString("LuckyBlocks." + dropType(biMap().get(block)) + ".Drop"))));
        //Get list of drops with droptype. List will be in a separate config

        new DropItem(dropType(biMap().get(block)), loc);

        //Remove from map here
        biMap().remove(block);
    }

    private String dropType(ArmorStand armorStand){
        Main plugin = Main.getInstance();

        ItemMeta is = armorStand.getEquipment().getHelmet().getItemMeta();

        String type = is.getPersistentDataContainer().get(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING);

        return type;
    }

    private BiMap<Block, ArmorStand> biMap(){
        return plugin().getBiMap();
    }

    private Main plugin(){
        return Main.getInstance();
    }
}
