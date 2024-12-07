package me.gaf1.keftemekeys.items;

import me.gaf1.keftemekeys.Plugin;
import me.gaf1.keftemekeys.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuItems {

    public static final MenuItems instance = new MenuItems();

    public Inventory inventory = Bukkit.createInventory(null, 54, ChatUtil.translate("&0Предметы"));

    public File file = new File(Plugin.getInstance().getDataFolder(), "items.yml");

    public YamlConfiguration config;

    public void saveItems(ItemStack[] itemStacks) {
        List<ItemStack> items = new ArrayList<>();

        for (ItemStack itemStack: itemStacks){
            items.add(itemStack);
        }

        for (int i = 0; i < items.size(); i++) {
            config.createSection(String.valueOf(i));
            ItemStack itemStack = items.get(i);
            config.set(String.valueOf(i), itemStack);
        }
        try {
            config.save(file);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadItems() {

        config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return;
            }
        }

        List<String> keys = new ArrayList<>(config.getKeys(false));

        if (keys.isEmpty()) {
            return;
        }

        ItemStack[] items = new ItemStack[keys.size()];

        for (int i = 0; i < keys.size(); i++) {
            items[i] = config.getItemStack(keys.get(i));
        }

        inventory.setContents(items);
    }

}

