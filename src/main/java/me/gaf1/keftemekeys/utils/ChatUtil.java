package me.gaf1.keftemekeys.utils;

import me.gaf1.keftemekeys.Plugin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {


    public static List<String> applyArgs(List<String> text, Map<String, String> args) { // (list, Map.of(Pair("key", "value"))
        for (int i = 0; i < text.size(); i++) {
            String line = text.get(i);

            for (String arg: args.keySet()) {
                line = line.replace(arg, args.get(arg));
            }

            text.set(i, translate(line));
        }

        return text;
    }

    public static void sendMessage(Player player,String msg){
        player.sendMessage(translate(msg));
    }
    public static void sendTitle(Player player,String msg,String subMsg,int fadeIn,int stay,int fadeOut){
        player.sendTitle(translate(msg),translate(subMsg), fadeIn,stay,fadeOut);
    }

    public static void sendTitle(Player player,String msg,String subMsg){
        player.sendTitle(translate(msg),translate(subMsg), 10,20,10);
    }


    public static void broadcastTitle(String msg,String subMsg,int fadeIn,int stay,int fadeOut){
        for (Player player: Plugin.getInstance().getServer().getOnlinePlayers()){
            player.sendTitle(translate(msg),translate(subMsg), fadeIn,stay,fadeOut);
        }
    }
    public static void broadcastTitle(String msg,String subMsg){
        for (Player player: Plugin.getInstance().getServer().getOnlinePlayers()){
            player.sendTitle(translate(msg),translate(subMsg), 10,20,10);
        }
    }
    public static void broadcastMessage(String msg){
        for (Player player: Plugin.getInstance().getServer().getOnlinePlayers()){
            sendMessage(player,msg);
        }
    }

    public static void sendConfigMessage(Player recipient, String configPath) {
        sendMessage(recipient, Plugin.getInstance().getConfig().getString(configPath));
    }

    public static void sendConfigMessage(Player recipient, String configPath, Map<String, String> args) {
        String message = ConfigManager.instance.getString(configPath);

        for (String key: args.keySet()) {
            message = message.replace(key, args.get(key));
        }

        sendMessage(recipient, message);
    }

    public static void sendConfigTitle(Player recipient, String messagePath, String subMessagePath) {
        if (ConfigManager.instance.configs.get("messages.yml").getString(subMessagePath) == null) {
            sendTitle(
                    recipient,
                    ConfigManager.instance.configs.get("messages.yml").getString(messagePath),
                    ""
            );
        }
        else {
            sendTitle(
                    recipient,
                    ConfigManager.instance.configs.get("messages.yml").getString(messagePath),
                    ConfigManager.instance.configs.get("messages.yml").getString(subMessagePath)
            );
        }
    }

    public static void sendConfigTitle(Player recipient, String messagePath, String subMessagePath, Map<String, String> args) {
        String message = ConfigManager.instance.configs.get("messages.yml").getString(messagePath);
        String subMessage;

        if (!subMessagePath.equals("")) {
            subMessage = ConfigManager.instance.configs.get("messages.yml").getString(subMessagePath);
        }
        else {
            subMessage = "";
        }

        assert message != null;

        for (String key: args.keySet()) {
            message = message.replace(key, args.get(key));
        }

        if (subMessage != null) {
            for (String key: args.keySet()) {
                subMessage = subMessage.replace(key, args.get(key));
            }
        }

        if (subMessage == null) {
            sendTitle(
                    recipient,
                    translate(message),
                    ""
            );
        }
        else {
            sendTitle(
                    recipient,
                    translate(message),
                    translate(subMessage)
            );
        }
    }




    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])([A-Fa-f0-9])");

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', HEX_PATTERN.matcher(message).replaceAll("&x&$1&$2&$3&$4&$5&$6"));
    }




}