package me.gaf1.keftemekeys.swap;

import me.gaf1.keftemekeys.items.MenuItems;
import me.gaf1.keftemekeys.key.Key;
import me.gaf1.keftemekeys.utils.ChatUtil;
import me.gaf1.keftemekeys.utils.ConfigManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class SwapEvents implements Listener{



    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (!(event.getInventory() == SwapMenu.instance.swap)){
            return;
        }
        if (event.getCurrentItem() == null){
            return;
        }
        event.setCancelled(true);
        if (!event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("button"), PersistentDataType.INTEGER)){
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (player.getInventory().firstEmpty() == -1) {
            ChatUtil.sendConfigMessage(player,"Messages.full_inventory");
            return;
        }

        ItemStack itemStack = Key.instance.getKey().clone();
        itemStack.setAmount(1);

        if (player.getInventory().containsAtLeast(itemStack,1)) {
            player.getInventory().removeItem(itemStack);
            giveRandomItem(player);
        } else {
            ChatUtil.sendConfigMessage(player,"Messages.no_key");
        }
    }

    public void giveRandomItem(Player player){
        YamlConfiguration config = YamlConfiguration.loadConfiguration(MenuItems.instance.file) ;
        Random random = new Random();
        List<String> keys = new ArrayList<>(config.getKeys(false));

        if (keys.isEmpty()) {
            return;
        }

        ItemStack[] items = new ItemStack[keys.size()];

        for (int i = 0; i < keys.size(); i++) {
            items[i] = config.getItemStack(keys.get(i));
        }
        ItemStack itemStack = items[random.nextInt(keys.size())];
        ChatUtil.sendConfigMessage(player,"Messages.swap_key");
        player.getInventory().addItem(itemStack);
    }
}
