package me.metallicgoat.LuckyBlocks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabComp implements TabCompleter {
    List<String> arguments = new ArrayList<>();

    public List<String> onTabComplete(CommandSender sender, Command c, String s, String[] args) {
        if (this.arguments.isEmpty() &&
                sender.hasPermission("LuckyBlocks.admin")) {
            this.arguments.add("map");
            this.arguments.add("give");
        }
        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            for (String a : this.arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
            }
            return result;
        }
        return null;
    }
}
