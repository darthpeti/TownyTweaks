package me.darthpeti.townytweaks.Towny.listeners.discord;

import com.palmergames.bukkit.towny.event.town.TownRuinedEvent;
import me.darthpeti.townytweaks.Main;
import me.darthpeti.townytweaks.Towny.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.logging.Logger;

public class RuinedTown implements Listener {

    private Logger logger;

    public RuinedTown(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTown(TownRuinedEvent event) {
        if (Main.instance.getCustomConfig().getString("notification-town-ruins").equalsIgnoreCase("true")) {
            String townName = event.getTown().getName();
            DiscordWebhook webhook = new DiscordWebhook(Main.instance.getCustomConfig().getString("webhook-url"));

            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(255, 0, 0))
                    .setDescription("The town of " + townName + " has fallen into ruins!")
            );
            try {
                webhook.execute();
            } catch (java.io.IOException e) {
                logger.severe(e.getStackTrace().toString());
            }
        }
    }
}

