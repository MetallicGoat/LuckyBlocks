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
        final double distance = player.getLocation().distance(e.getBlock().getLocation().add(0.5, 0, 0.5));

        if (hand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING)) {
            //lucky blocks are heads, this makes it so plays cant place blocks inside of themselves (Needs to be made better)
            if(arena != null && arena.isInside(loc) && distance >= 1.0D) {
                CreateBlock createBlock = new CreateBlock();
                createBlock.setBlock(hand, block, arena, loc, against);

            }else{
                //Lucky blocks cant be placed outside an arena
                e.setBuild(false);
            }
        }
    }
}
