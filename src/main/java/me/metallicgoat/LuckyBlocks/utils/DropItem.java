package me.metallicgoat.LuckyBlocks.utils;

import de.marcely.bedwars.api.BedwarsAPI;
import me.metallicgoat.LuckyBlocks.utils.configs.ConfigManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class DropItem {

    public DropItem(String type, Location loc){

        String drop = randomDrop(type);

        final String dropType = configManager().getLuckyDropsConfig().getString("LuckyBlocks." + type + "." + drop + ".Type");
        final String name = configManager().getLuckyDropsConfig().getString("LuckyBlocks." + type + "." + drop + ".Name");
        final int amount = configManager().getLuckyDropsConfig().getInt("LuckyBlocks." + type + "." + drop + ".Amount");

        assert dropType != null;
        setDrop(dropType, name, amount, loc);

    }

    private void setDrop(String dropType, String name, int amount, Location loc){

        final World world = loc.getWorld();
        ItemStack drop = null;

        if(dropType.equalsIgnoreCase("Material")){
            drop = new ItemStack(Material.valueOf(name.toUpperCase()));
        } else if(dropType.equalsIgnoreCase("SpecialItem")){
            drop = BedwarsAPI.getGameAPI().getSpecialItem(name).getItemStack();
        }

        assert drop != null;
        assert world != null;
        drop.setAmount(amount);
        world.dropItemNaturally(loc, drop);
    }

    private String randomDrop(String type){

        Set<String> stringSet = Objects.requireNonNull(configManager().getLuckyDropsConfig().getConfigurationSection("LuckyBlocks." + type)).getKeys(false);

        ArrayList<String> stringArray = new ArrayList<>(stringSet);

        Random random = new Random();

        return stringArray.get(random.nextInt(stringArray.size()));
    }

    private ConfigManager configManager(){
        return ServerManager.getConfigManager();
    }
}
