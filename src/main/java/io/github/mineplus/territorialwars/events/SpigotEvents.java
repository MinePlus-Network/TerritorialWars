package io.github.mineplus.territorialwars.events;

import io.github.mineplus.territorialwars.TerritorialWars;
import io.github.mineplus.territorialwars.shared.SharedStuff;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

public class SpigotEvents implements Listener {
    @EventHandler
    public void onTabCompleteEvent(TabCompleteEvent event) {
        if (TerritorialWars.isUsingPaper())
            return;

        String command = event.getBuffer().substring(1, event.getBuffer().indexOf(' '));

        if (!command.equals("territorialwars") && !command.equals("twars") && !command.equals("tw"))
            return;

        int spaceCount = event.getBuffer().length() - event.getBuffer().replaceAll(" ", "").length();
        String[] args = event.getBuffer().substring(event.getBuffer().indexOf(' ') + 1).split(" ");

        event.setCompletions(SharedStuff.tabComplete(event.getSender(), command, spaceCount, args));
    }
}
