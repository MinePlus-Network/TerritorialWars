package io.github.mineplus.territorialwars.configuration;

import io.github.mineplus.territorialwars.TerritorialWars;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.configuration.implementation.api.QuoteStyle;

import java.io.IOException;

public class ConfigLoader {
    private YamlFile config;

    public void load() {
        config = new YamlFile(TerritorialWars.getPluginInstance().getDataFolder() + "/config.yml");
        try {
            config.createOrLoad();
        } catch (IOException err) {
            TerritorialWars.getPluginInstance().getLogger().severe("Failed to load config.yml");
            TerritorialWars.getPluginInstance().getLogger().severe(err.getMessage());
        }
        config.addDefault("locale", "en_US");
        config.setComment("locale", "The locale to use for messages. See the locales folder for available locales.");
        config.addDefault("debug", false);
        config.setComment("debug", "Toggles debug mode. This will print debug messages to the console.");
        config.options().quoteStyleDefaults().setQuoteStyle(String.class, QuoteStyle.DOUBLE);
        try {
            config.save();
        } catch (IOException err) {
            TerritorialWars.getPluginInstance().getLogger().severe("Failed to load config.yml");
            TerritorialWars.getPluginInstance().getLogger().severe(err.getMessage());
        }
    }

    public YamlFile getConfig() {
        return config;
    }
}
