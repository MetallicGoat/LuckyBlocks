package me.metallicgoat.LuckyBlocks.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.metallicgoat.LuckyBlocks.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullBuilder {

    //NOTE: A random UUID will prevent blocks from stacking in player inventory

    public static ItemStack getSkull(String textureUUID, String texture, int amount, String name, String type) {

        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        GameProfile profile = new GameProfile(UUID.fromString(textureUUID), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        PersistentDataContainer data = meta.getPersistentDataContainer();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        data.set(new NamespacedKey(plugin(), "LuckyBlock"), PersistentDataType.STRING, type);

        skull.setItemMeta(meta);
        skull.setAmount(amount);
        return skull;
    }
    private static Main plugin(){
        return Main.getInstance();
    }
}
