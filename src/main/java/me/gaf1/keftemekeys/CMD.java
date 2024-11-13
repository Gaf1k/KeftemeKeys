package me.gaf1.keftemekeys;

import me.gaf1.keftemekeys.items.MenuItems;
import me.gaf1.keftemekeys.key.Key;
import me.gaf1.keftemekeys.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CMD implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args[0].equalsIgnoreCase("reload")){
            Plugin.getInstance().reloadConfig();
            sender.sendMessage(ChatUtil.translate(Plugin.getInstance().getConfig().getString("Messages.reload_config")));
            return true;
        }

        if (sender instanceof Player){
            Player player = (Player) sender;
            if (!player.hasPermission("keftemekeys.admin")){
                ChatUtil.sendConfigMessage(player,"Messages.not_enough_permission");
                return true;
            }

            if (args.length == 0){
                ChatUtil.sendConfigMessage(player,"Messages.error_command");
                return true;
            }

            if (args[0].equalsIgnoreCase("items")){
                player.openInventory(MenuItems.instance.inventory);
                return true;
            }
            else if (args[0].equalsIgnoreCase("giveKey")){
                ItemStack itemStack = Key.instance.getKey();
                player.getInventory().addItem(itemStack);
                return true;
            }
            return true;
        }
        else {
            System.out.println("Тебе нельзя!");
            return true;
        }
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
