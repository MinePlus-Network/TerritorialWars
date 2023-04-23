package io.github.mineplus.territorialwars.commands;

import io.github.mineplus.territorialwars.TerritorialWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.file.YamlFile;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class MainCommand extends BukkitCommand {
    public MainCommand() {
        super("territorialwars", "Main command for the plugin.", "Usage: /territorialwars", Arrays.asList("twars", "tw"));
        this.setName("territorialwars");
        this.setDescription("Main command for the plugin.");
        this.setUsage("Usage: /territorialwars");
        this.setPermission("territorialwars.use");
        this.setAliases(Arrays.asList("twars", "tw"));

        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register("territorialwars", this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args) {
        YamlFile config = TerritorialWars.getConfigLoader().getConfig();

        if (args.length == 0) {
            commandSender.sendMessage(config.getString("locale"));
            return true;
        }
        else if (args.length == 3) {
            if (args[0].equals("admin") && commandSender.hasPermission("territorialwars.admin")) {
                switch (args[1]) {
                    case "reload": {
                        switch (args[2]) {
                            case "all": {
                                TerritorialWars.getConfigLoader().load();
                                commandSender.sendMessage(ChatColor.GREEN + "Reloaded config & message files.");
                                break;
                            }
                            case "config": {
                                TerritorialWars.getConfigLoader().load();
                                commandSender.sendMessage(ChatColor.GREEN + "Reloaded config files.");
                                break;
                            }
                            case "messages": {
                                commandSender.sendMessage(ChatColor.GREEN + "Reloaded message files.");
                                break;
                            }
                        }
                        break;
                    }
                    case "debug": {
                        switch (args[2]) {
                            case "getConfig": {
                                final String path = String.join(".", Arrays.copyOfRange(args, 1, args.length));
                                commandSender.sendMessage(path + ": " + config.get(path).toString());
                                break;
                            }
                            case "getMessage": {
                                break;
                            }
                        }
                    }
                }
            }
            return true;
        } else if (args.length >= 3) {
            if (args[0].equals("admin") && commandSender.hasPermission("territorialwars.admin")) {
                switch (args[1]) {
                    case "debug": {
                        switch (args[2]) {
                            case "getConfig": {
                                final String path = String.join(".", Arrays.copyOfRange(args, 3, args.length));
                                commandSender.sendMessage(path + ": " + config.get(path));
                                break;
                            }
                            case "getMessage": {
                                break;
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) { // first
            return Arrays.asList("admin");
        }
        else if (args.length == 2) { // second
            switch (args[0]) {
                case "admin":
                    return Arrays.asList("reload", "debug");
            }
        }
        else if (args.length == 3) { // third
            switch (args[1]) {
                case "reload":
                    return Arrays.asList("all", "config", "messages");
                case "debug":
                    return Arrays.asList("getConfig", "getMessage");
            }
        }
        else if (args.length >= 4) {
            if (args[2].equals("getConfig") || args[2].equals("getMessage")) {
                return Arrays.asList("<path>");
            }
        }
        return Arrays.asList("");
    }
}
