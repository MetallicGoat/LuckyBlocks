package me.metallicgoat.LuckyBlocks.listeners;

import com.google.common.collect.BiMap;
import me.metallicgoat.LuckyBlocks.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

public class Explosions implements Listener {

    @EventHandler
    public void onExplosion(EntityExplodeEvent e){

        //TODO: Get from a config
        final boolean explode = false;

        List<Block> blockListCopy = new ArrayList<>(e.blockList());

        if(explode) {
            for (Block block : blockListCopy) {
                if(biMap().containsKey(block)){
                    biMap().get(block).remove();
                    biMap().remove(block);
                }
            }
        }else{
            for (Block block : blockListCopy) {
                if(biMap().containsKey(block)){
                    e.blockList().remove(block);
                }
            }
        }
    }

    private BiMap<Block, ArmorStand> biMap(){
        return plugin().getBiMap();
    }

    private Main plugin(){
        return Main.getInstance();
    }
}
