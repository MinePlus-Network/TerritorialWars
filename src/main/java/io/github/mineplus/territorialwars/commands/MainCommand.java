package io.github.mineplus.territorialwars.commands;

import io.github.mineplus.territorialwars.TerritorialWars;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.file.YamlFile;

import java.lang.reflect.Field;
import java.util.Arrays;

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
        else if (args.length == 2) {
            if (args[0].equals("admin") && args[1].equals("reload")) {
                if (commandSender.hasPermission("territorialwars.admin")) {
                    TerritorialWars.getConfigLoader().load();
                    return true;
                }
            }
        }

        return false;
    }
}
