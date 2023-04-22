package io.github.mineplus.territorialwars;

import io.github.mineplus.territorialwars.commands.MainCommand;
import io.github.mineplus.territorialwars.configuration.ConfigLoader;
import io.github.mineplus.territorialwars.events.PaperEvents;
import io.github.mineplus.territorialwars.events.SpigotEvents;
import io.github.mineplus.territorialwars.localization.MessageLoader;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TerritorialWars extends JavaPlugin {
    private static TerritorialWars plugin = null;
    private static ConfigLoader configLoader = null;
    private static MessageLoader messageLoader = null;
    private static boolean isUsingPaper = false;

    public static TerritorialWars getPluginInstance() {
        return plugin;
    }
    public static ConfigLoader getConfigLoader() {
        return configLoader;
    }
    public static boolean isUsingPaper() {
        return isUsingPaper;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // Load the config & messages
        configLoader = new ConfigLoader();
        configLoader.load();

        // Check if the server is running on Paper
        try {
            Class.forName("com.destroystokyo.paper.utils.PaperPluginLogger");
            isUsingPaper = true;
            getLogger().info("Your server is running on Paper. Async features will be enabled.");
        } catch (ClassNotFoundException err) {
            isUsingPaper = false;
            getLogger().warning("Your server is not running on Paper. Async features will not work.");
        }

        // Register command(s)
        BukkitCommand mainCommand = new MainCommand();

        // Register events
        getServer().getPluginManager().registerEvents(new SpigotEvents(), this);
        if (isUsingPaper) {
            getServer().getPluginManager().registerEvents(new PaperEvents(), this);
        }
    }
}
