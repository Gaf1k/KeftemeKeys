package me.gaf1.keftemekeys.key;

import me.gaf1.keftemekeys.Plugin;
import me.gaf1.keftemekeys.utils.ChatUtil;
import me.gaf1.keftemekeys.utils.ConfigManager;
import me.gaf1.keftemekeys.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Key {

    public static final Key instance = new Key();


    public ItemStack getKey() {

        ItemStack key = new ItemBuilder(Material.TRIPWIRE_HOOK)
                .setName(ConfigManager.instance.getString("Key.Name"))
                .setLore(ConfigManager.instance.getListString("Key.Lore"))
                .addOrRemoveAllFlags(true)
                .addPersistent("key", PersistentDataType.INTEGER,1)
                .build();
        if (Plugin.getInstance().getConfig().getBoolean("Key.Glow")){
            ItemMeta meta = key.getItemMeta();
            meta.addEnchant(Enchantment.PROTECTION_FALL,1,false);
            key.setItemMeta(meta);
        }

        return key;
    }

}
