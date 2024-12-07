package me.gaf1.keftemekeys.swap;

import me.gaf1.keftemekeys.Plugin;
import me.gaf1.keftemekeys.utils.ChatUtil;
import me.gaf1.keftemekeys.utils.ConfigManager;
import me.gaf1.keftemekeys.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SwapMenu {

    public static final SwapMenu instance = new SwapMenu();

    Inventory swap = Bukkit.createInventory(null,54, ChatUtil.translate("&0Обмен ключей"));

    SwapMenu(){
        ItemStack swapButton = new ItemBuilder(Material.valueOf(Plugin.getInstance().getConfig().getString("SwapMenu.swapButton.Material").toUpperCase()))
                .setName(ConfigManager.instance.getString("SwapMenu.swapButton.Name"))
                .setLore(ConfigManager.instance.getListString("SwapMenu.swapButton.Lore"))
                .addPersistent("button", PersistentDataType.INTEGER,1)
                .addOrRemoveAllFlags(true)
                .build();
        swap.setItem(Plugin.getInstance().getConfig().getInt("SwapMenu.swapButton.Slot"),swapButton);

        ItemStack informationSlot = new ItemBuilder(Material.valueOf(Plugin.getInstance().getConfig().getString("SwapMenu.informationSlot.Material").toUpperCase()))
                .setName(ConfigManager.instance.getString("SwapMenu.informationSlot.Name"))
                .setLore(ConfigManager.instance.getListString("SwapMenu.informationSlot.Lore"))
                .addPersistent("information",PersistentDataType.INTEGER,1)
                .addOrRemoveAllFlags(true)
                .build();
        List<Integer> list = Plugin.getInstance().getConfig().getIntegerList("SwapMenu.informationSlot.Slot");
        for (int i: list){
            swap.setItem(i,informationSlot);
        }
    }


}
