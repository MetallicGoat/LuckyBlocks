package me.metallicgoat.LuckyBlocks.utils;

import com.google.common.collect.BiMap;
import de.marcely.bedwars.api.arena.Arena;
import me.metallicgoat.LuckyBlocks.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;

public class CreateBlock {
    public void setBlock(ItemStack hand, Block block, Arena arena, Location loc, Block against){


        final String type = hand.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin(), "LuckyBlock"), PersistentDataType.STRING);

        final String texture = plugin().getConfig().getString("LuckyBlocks." + type + ".Texture");
        final String textureUUID = plugin().getConfig().getString("LuckyBlocks." + type + ".UUID");
        final String color = plugin().getConfig().getString("LuckyBlocks." + type + ".GlassBlock");

        BukkitScheduler scheduler = plugin().getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(plugin(), () -> {
            block.setType(Material.valueOf(color.toUpperCase()));

            arena.setBlockPlayerPlaced(block, true);

            ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0.5, -1.25, 0.5), EntityType.ARMOR_STAND);

            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setMarker(true);
            armorStand.getEquipment().setHelmet(getSkull(textureUUID, texture, type));

            takeBlock(against.getFace(block), hand);

            biMap().put(block, armorStand);
        }, 1L);
    }

    private void takeBlock(BlockFace face, ItemStack hand){
        if(face != BlockFace.UP){
            hand.setAmount(hand.getAmount() - 1);
        }
    }

    private static BiMap<Block, ArmorStand> biMap(){
        return Main.getInstance().getBiMap();
    }

    private static ItemStack getSkull(String textureUUID, String texture, String type){
        return SkullBuilder.getSkull(textureUUID, texture, 1, "LuckyBlock", type);
    }

    private static Main plugin(){
        return Main.getInstance();
    }
}
