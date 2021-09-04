package me.metallicgoat.LuckyBlocks.listeners;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.arena.Arena;
import me.metallicgoat.LuckyBlocks.utils.CreateBlock;
import me.metallicgoat.LuckyBlocks.Main;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class PlaceBlock implements Listener {

    @EventHandler
    public void onPlace(final BlockPlaceEvent e){
        final Main plugin = Main.getInstance();
        final Location loc = e.getBlock().getLocation();
        final Block block = e.getBlock();
        final Block against = e.getBlockAgainst();
        final Player player = e.getPlayer();
        final Arena arena = BedwarsAPI.getGameAPI().getArenaByPlayer(player);
        final ItemStack hand = e.getItemInHand();
        final Location centerBlock = e.getBlock().getLocation().add(0.5, 0, 0.5);

        if (hand.getItemMeta() != null &&
                hand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING)) {
            //lucky blocks are heads, this makes it so plays can't place blocks inside themselves (Needs to be made better)

            System.out.println(Math.abs(player.getLocation().getX() - centerBlock.getX()) + " " + Math.abs(player.getLocation().getZ() - centerBlock.getZ()) + " " +  Math.abs(player.getLocation().getY() - centerBlock.getY()));
            System.out.println(player.getVelocity().getY());

            //TODO Entities

            //THANKS MARCEL!
            if(Math.abs(player.getLocation().getX() - centerBlock.getX()) > 0.8
                    || Math.abs(player.getLocation().getZ() - centerBlock.getZ()) > 0.8
                    || Math.abs(player.getLocation().getY() - centerBlock.getY()) >= 1.0) {

                //Player falling
                if(Math.abs(player.getLocation().getY() - centerBlock.getY()) < 1.15
                        && player.getVelocity().getY() < -0.08){
                    e.setBuild(false);
                    return;
                }


                if (arena != null && arena.isInside(loc)) {

                    CreateBlock createBlock = new CreateBlock();
                    createBlock.setBlock(hand, block, arena, loc, against);
                } else {
                    //Lucky blocks cant be placed outside an arena
                    e.setBuild(false);
                }

            }else{
                //Lucky blocks can be placed inside a player
                e.setBuild(false);
            }
        }
    }
}
