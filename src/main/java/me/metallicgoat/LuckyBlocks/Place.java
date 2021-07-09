package me.metallicgoat.LuckyBlocks;

import com.google.common.collect.BiMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
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
        final ItemStack hand = e.getItemInHand();
        if(hand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING)){

            String type = hand.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING);

            final String texture = plugin.getConfig().getString("LuckyBlocks." + type + ".Texture");
            final String TextureUUID = plugin.getConfig().getString("LuckyBlocks." + type + ".UUID");
            final String color = plugin.getConfig().getString("LuckyBlocks." + type + ".GlassBlock");

            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(plugin, () -> {
                e.getBlock().setType(Material.valueOf(color.toUpperCase()));

                ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5, -1.25, 0.5), EntityType.ARMOR_STAND);

                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.setMarker(true);
                armorStand.getEquipment().setHelmet(getSkull(TextureUUID, texture));

                biMap().put(e.getBlock(), armorStand);
            }, 1L);
        }
    }

    private BiMap<Block, ArmorStand> biMap(){
        return Main.getInstance().getBiMap();
    }

    private ItemStack getSkull(String TextureUUID, String texture){
        return Heads.getSkull(TextureUUID, texture);
    }


        /*
    @EventHandler
    public void clickStand(PlayerInteractAtEntityEvent e){
        if(e.getRightClicked().getType() == EntityType.ARMOR_STAND && isPlaceable(e.getPlayer(), 3)){
            ArmorStand clicked = (ArmorStand) e.getRightClicked();
            if(biMap.containsValue(clicked)){
                Block block = biMap.inverse().get(clicked).getRelative(0, -1, 0);
                double distance = e.getPlayer().getLocation().distance(block.getLocation().add(0.5, 0, 0.5));

                if(distance > 1.27D) {
                    Location loc = block.getLocation();
                    block.setType(Material.GLASS);

                    ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5, -1.25, 0.5), EntityType.ARMOR_STAND);

                    armorStand.setVisible(false);
                    armorStand.setGravity(false);
                    armorStand.setHelmet(getSkull());

                    biMap.put(block, armorStand);

                }
            }
        }
    }

        private boolean isPlaceable(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock;
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            return true;
        }
        return false;
    }

     */
}
