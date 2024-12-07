package me.gaf1.keftemekeys.key;

import me.gaf1.keftemekeys.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KeyEvent implements Listener {

    @EventHandler
    public void spawnKey(ChunkLoadEvent event){
        if (!event.isNewChunk()){
            return;
        }

        Chunk chunk = event.getChunk();
        if (!loadWorldsFromConfig().contains(chunk.getWorld())){
            return;
        }

        int chance = Plugin.getInstance().getConfig().getInt("Key.Chance_spawn");
        Random random = new Random();

        for (BlockState blockState: event.getChunk().getTileEntities()){
            if (blockState instanceof Chest){
                Chest chest = (Chest) blockState;
                int randomInt = random.nextInt(100)+1;
                if (randomInt<=chance){
                    chest.getBlockInventory().setItem(random.nextInt(chest.getBlockInventory().getSize()),Key.instance.getKey());
                }
            }
        }
        for (Entity entity: event.getChunk().getEntities()){
            if (entity instanceof StorageMinecart){
                StorageMinecart minecart = (StorageMinecart) entity;
                int randomInt = random.nextInt(100)+1;
                int randomSlot = random.nextInt(minecart.getInventory().getSize());
                if (randomInt<=chance){
                    minecart.getInventory().setItem(randomSlot, Key.instance.getKey());
                }
            }
        }

    }

    public List<World> loadWorldsFromConfig() {
        List<World> worlds = new ArrayList<>();

        List<String> worldNames = Plugin.getInstance().getConfig().getStringList("Key.Enabled_worlds");

        for (String worldName : worldNames) {
            worlds.add(Bukkit.getWorld(worldName));
        }

        return worlds;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if (event.getItemInHand().hasItemMeta() && event.getItemInHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("key"), PersistentDataType.INTEGER)){
            event.setCancelled(true);
        }
    }

}
