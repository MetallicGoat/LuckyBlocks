package me.metallicgoat.LuckyBlocks;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.ArenaStatus;
import de.marcely.bedwars.api.event.arena.RoundStartEvent;
import de.marcely.bedwars.api.game.spawner.Spawner;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;

public class ArenaStart implements Listener {
    @EventHandler
    public void onRoundStart(RoundStartEvent e){

        //Gets all the lucky blocks from the config

        Main plugin = Main.getInstance();

        final ConfigurationSection sect = plugin.getConfig().getConfigurationSection("LuckyBlocks");

        final Arena arena = e.getArena();

        sect.getKeys(false).forEach(key ->  {
            final long time = plugin.getConfig().getLong("LuckyBlocks." + key + ".DropRate");
            final String name = plugin.getConfig().getString("LuckyBlocks." + key + ".Name");
            final String texture = plugin.getConfig().getString("LuckyBlocks." + key + ".Texture");
            final String textureUUID = plugin.getConfig().getString("LuckyBlocks." + key + ".UUID");
            final String spawner = plugin.getConfig().getString("LuckyBlocks." + key + ".Spawner");
            final int itemCap = plugin.getConfig().getInt("LuckyBlocks." + key + ".ItemCap");
            final int amount = plugin.getConfig().getInt("LuckyBlocks." + key + ".DropAmount");

            SpawnLucky(arena, time, name, key, texture, textureUUID, spawner, itemCap, amount);

        });
    }
    private void SpawnLucky(Arena arena, Long time, String name, String type, String texture, String textureUUID, String spawner, int itemCap, int amount){

        //Spawns lucky blocks and schedules next luck block drop if necessary

        Main plugin = Main.getInstance();

        final World w = arena.getGameWorld();

        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(plugin, () -> {
            if(arena.getStatus() == ArenaStatus.RUNNING) {
                for(Spawner s : arena.getSpawners()){
                    if(spawner.equals(getItemType(s))){
                        assert w != null;
                        if(itemCap > getAmount(s.getLocation().toLocation(w), w, type)) {
                            w.dropItemNaturally(s.getLocation().toLocation(w), getSkull(textureUUID, texture, amount, name, type));
                        }
                    }
                }
                SpawnLucky(arena, time, name, type, texture, textureUUID, spawner, itemCap, amount);
            }
        }, time);
    }

    private ItemStack getSkull(String textureUUID, String texture, int amount, String name, String type){
        return LuckyBlockSkulls.getSkull(textureUUID, texture, amount, name, type);
    }

    //gets amount of a type of lucky blocks are inside a generator
    private int getAmount(Location loc, World w, String type){
        Main plugin = Main.getInstance();

        int i = 0;

        for(Entity e:w.getNearbyEntities(loc, 2.5, 2, 2.5)){
            if(e.getType() == EntityType.DROPPED_ITEM){

                ItemStack itemStack = ((Item) e).getItemStack();

                if(itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING)){
                    if(itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "LuckyBlock"), PersistentDataType.STRING).equals(type))
                    i = i + itemStack.getAmount();
                }
            }
        }

        return i;
    }

    //Gets spawner type
    private String getItemType(Spawner s){
        for(ItemStack i : s.getDropType().getDroppingMaterials()){
            return i.getType().name();
        }
        return "";
    }
}
