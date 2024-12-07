package me.gaf1.keftemekeys;

import me.gaf1.keftemekeys.items.MenuEvents;
import me.gaf1.keftemekeys.items.MenuItems;
import me.gaf1.keftemekeys.key.KeyEvent;
import me.gaf1.keftemekeys.swap.SwapCMD;
import me.gaf1.keftemekeys.swap.SwapEvents;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.nodes.MappingNode;

public final class Plugin extends JavaPlugin {

    public static Plugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        Metrics metrics = new Metrics(this, 24090);

        getCommand("keftemekeys").setExecutor(new CMD());
        getCommand("keftemekeys").setTabCompleter(new CMD());
        getCommand("keyswap").setExecutor(new SwapCMD());

        getServer().getPluginManager().registerEvents(new KeyEvent(),this);
        getServer().getPluginManager().registerEvents(new MenuEvents(),this);
        getServer().getPluginManager().registerEvents(new SwapEvents(),this);

        MenuItems.instance.loadItems();
    }

    @Override
    public void onDisable() {
    }
}
