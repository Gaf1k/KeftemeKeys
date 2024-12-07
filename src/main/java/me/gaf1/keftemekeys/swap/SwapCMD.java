package me.gaf1.keftemekeys.swap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SwapCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            System.out.println("Тебе нельзя!");
            return true;
        }
        Player player = (Player) sender;
        player.openInventory(SwapMenu.instance.swap);

        return true;
    }
}
