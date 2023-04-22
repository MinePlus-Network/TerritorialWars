package io.github.mineplus.territorialwars.events;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import io.github.mineplus.territorialwars.shared.SharedStuff;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PaperEvents implements Listener {
    @EventHandler
    public void onAsyncTabCompleteEvent(AsyncTabCompleteEvent event) {
        if (!event.isCommand() && !event.getBuffer().startsWith("/") || event.getBuffer().indexOf(' ') == -1)
            return;

        String command = event.getBuffer().substring(1, event.getBuffer().indexOf(' '));

        if (!command.equals("territorialwars") && !command.equals("twars") && !command.equals("tw"))
            return;

        int spaceCount = event.getBuffer().length() - event.getBuffer().replaceAll(" ", "").length();
        String[] args = event.getBuffer().substring(event.getBuffer().indexOf(' ') + 1).split(" ");

        event.setCompletions(SharedStuff.tabComplete(event.getSender(), command, spaceCount, args));
    }
}
