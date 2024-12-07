package me.gaf1.keftemekeys;

import me.gaf1.keftemekeys.items.MenuItems;
import me.gaf1.keftemekeys.key.Key;
import me.gaf1.keftemekeys.swap.SwapMenu;
import me.gaf1.keftemekeys.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CMD implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatUtil.translate(Plugin.getInstance().getConfig().getString("Messages.error_command")));
            return true;
        }

        if (!sender.hasPermission("keftemekeys.admin")) {
            sender.sendMessage(ChatUtil.translate(Plugin.getInstance().getConfig().getString("Messages.not_enough_permission")));
            return true;
        }


        if (args[0].equalsIgnoreCase("reload")) {
            Plugin.getInstance().reloadConfig();
            sender.sendMessage(ChatUtil.translate(Plugin.getInstance().getConfig().getString("Messages.reload_config")));
            return true;
        }

        if (args[0].equalsIgnoreCase("givekey")) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack itemStack = Key.instance.getKey();
                    player.getInventory().addItem(itemStack);
                    return true;
                }
            }
            else if (args.length == 2) {
                ItemStack itemStack = Key.instance.getKey();
                Player player1 = Bukkit.getPlayer(args[1]);
                if (player1 == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cТакого игрока не существует либо он оффлайн"));
                    return true;
                }
                player1.getInventory().addItem(itemStack);
                return true;
            } else {
                sender.sendMessage("Тебе нельзя!");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("items")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.openInventory(MenuItems.instance.inventory);
                return true;
            } else {
                sender.sendMessage("Тебе нельзя!");
                return true;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!sender.hasPermission("keftemekeys.admin")){
            return null;
        }
        if (args.length == 1){
            return List.of("reload","items","giveKey");
        }

        return null;
    }
}
