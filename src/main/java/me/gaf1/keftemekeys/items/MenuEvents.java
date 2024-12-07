package me.gaf1.keftemekeys.items;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class MenuEvents implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory() == MenuItems.instance.inventory) {
            ItemStack[] itemStacks = MenuItems.instance.inventory.getContents();
            MenuItems.instance.saveItems(itemStacks);
        }
    }
}
