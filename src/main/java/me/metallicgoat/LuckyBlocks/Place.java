package me.metallicgoat.LuckyBlocks;

import com.google.common.collect.BiMap;
import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.arena.Arena;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;

public class Place implements Listener {

    @EventHandler
    public void onPlace(final BlockPlaceEvent e){
        final Main plugin = Main.getInstance();
        final Location loc = e.getBlock().getLocation();
        final Player player = e.getPlayer();
        final Arena arena = BedwarsAPI.getGameAPI().getArenaByPlayer(player);
        final ItemStack hand = e.getItemInHand();
        final double distance = player.getLocation().distance(e.getBlock().getLocation().add(0.5, 0, 0.5));

        if (hand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING)) {
            //lucky blocks are heads, this makes it so plays cant place blocks inside of themselves (Needs to be made better)
            if(arena != null && arena.isInside(loc) && distance >= 1.0D) {
                String type = hand.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING);

                final String texture = plugin.getConfig().getString("LuckyBlocks." + type + ".Texture");
                final String textureUUID = plugin.getConfig().getString("LuckyBlocks." + type + ".UUID");
                final String color = plugin.getConfig().getString("LuckyBlocks." + type + ".GlassBlock");

                BukkitScheduler scheduler = plugin.getServer().getScheduler();
                scheduler.scheduleSyncDelayedTask(plugin, () -> {
                    e.getBlock().setType(Material.valueOf(color.toUpperCase()));
                    arena.setBlockPlayerPlaced(e.getBlock(), true);

                    ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5, -1.25, 0.5), EntityType.ARMOR_STAND);

                    armorStand.setVisible(false);
                    armorStand.setGravity(false);
                    armorStand.setMarker(true);
                    armorStand.getEquipment().setHelmet(getSkull(textureUUID, texture, 1, "LuckyBlock", "Placed"));

                    biMap().put(e.getBlock(), armorStand);
                }, 1L);
            }else{
                //Lucky blocks cant be placed outside an arena
                e.setBuild(false);
            }
        }
    }

    private BiMap<Block, ArmorStand> biMap(){
        return Main.getInstance().getBiMap();
    }

    private ItemStack getSkull(String textureUUID, String texture, int amount, String name, String type){
        return LuckyBlockSkulls.getSkull(textureUUID, texture, amount, name, type);
    }
}
