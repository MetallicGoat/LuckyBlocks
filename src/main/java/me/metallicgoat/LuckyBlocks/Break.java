package me.metallicgoat.LuckyBlocks;

import com.google.common.collect.BiMap;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Break implements Listener {

    @EventHandler
    //Removes the armorstand, and removes the block from the map
    public void onBreak(BlockBreakEvent e){
        if(biMap().containsKey(e.getBlock())){
            biMap().get(e.getBlock()).remove();
            biMap().remove(e.getBlock());
            dropItem();
        }
    }

    private void dropItem(){
        //TODO: Random drop system
    }

    private BiMap<Block, ArmorStand> biMap(){
        return Main.getInstance().getBiMap();
    }
}
