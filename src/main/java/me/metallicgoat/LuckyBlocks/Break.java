package me.metallicgoat.LuckyBlocks;

import com.google.common.collect.BiMap;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Break implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(biMap().containsKey(e.getBlock())){
            biMap().get(e.getBlock()).remove();
            biMap().remove(e.getBlock());
        }
    }

    private BiMap<Block, ArmorStand> biMap(){
        return Main.getInstance().getBiMap();
    }
}
