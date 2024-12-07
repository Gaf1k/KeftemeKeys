package me.gaf1.keftemekeys.utils;

import me.gaf1.keftemekeys.Plugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfigManager {

    public final static ConfigManager instance = new ConfigManager();

    public Map<String, YamlConfiguration> configs = new HashMap<>();

    public void init(String fileName) {
        fileName = fileName + ".yml";

        File file = new File(Plugin.getInstance().getDataFolder().getAbsolutePath() + "/" + fileName);

        if (!file.exists()) {
            Plugin.getInstance().saveResource(fileName, false);
        }

        configs.put(fileName, YamlConfiguration.loadConfiguration(file));
    }

    public String getString(String path){
        String text = ChatUtil.translate(Plugin.getInstance().getConfig().getString(path));
        return text;
    }
    public List<String> getListString(String path){
        List<String> list = (List<String>) Plugin.getInstance().getConfig().getList(path);
        for (int i = 0; i<list.size();i++){
            String text = list.get(i);
            list.set(i,ChatUtil.translate(text));
        }

        return list;
    }
}
