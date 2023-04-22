package io.github.mineplus.territorialwars.shared;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SharedStuff {
    public static List<String> tabComplete(CommandSender sender, String command, int spaceCount, String[] args) {
        List<String> completions = new ArrayList<>();

        if (spaceCount == 1) {
            if (sender.hasPermission("territorialwars.admin")) {
                completions.add("admin");
            }
        } else if (spaceCount == 2) {
            if (args[0].equals("admin")) {
                if (sender.hasPermission("territorialwars.admin")) {
                    completions.add("reload");
                    completions.add("debug");
                }
            }
        } else {
            completions.add("");
        }

        return completions;
    }
}
