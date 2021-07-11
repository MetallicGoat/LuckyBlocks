package me.metallicgoat.LuckyBlocks;

import com.google.common.collect.BiMap;
import de.marcely.bedwars.api.event.arena.ArenaRegenerationStartEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class ArenaRegeneration implements Listener {

    ArrayList<Block> blocksToRemove = new ArrayList<>();

    //This clears the bimap after arena regeneration
    //So no memory leaks

    @EventHandler
    public void onRegeneration(ArenaRegenerationStartEvent e){
        biMap().forEach((block, armorStand) -> {
            if(e.getArena().isInside(block.getLocation())) {
                armorStand.remove();
                blocksToRemove.add(block);
            }
        });
        cleanBiMap();
    }

    private void cleanBiMap(){
        for(Block b:blocksToRemove){
            biMap().remove(b);
        }
        blocksToRemove.clear();
    }

    private BiMap<Block, ArmorStand> biMap(){
        return Main.getInstance().getBiMap();
    }
}
