package me.darthpeti.townytweaks.Towny.listeners.discord;

import com.palmergames.bukkit.towny.event.DeleteTownEvent;
import me.darthpeti.townytweaks.Main;
import me.darthpeti.townytweaks.Towny.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.logging.Logger;

public class DeletedTown implements Listener {

    private Logger logger;

    public DeletedTown(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTown(DeleteTownEvent event) {
        if (Main.instance.getCustomConfig().getString("notification-town-delete").equalsIgnoreCase("true")) {
            String townName = event.getTownName();
            DiscordWebhook webhook = new DiscordWebhook(Main.instance.getCustomConfig().getString("webhook-url"));

            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(255, 0, 0))
                    .setDescription("The town of " + townName + " has been deleted!")
            );
            try {
                webhook.execute();
            } catch (java.io.IOException e) {
                logger.severe(e.getStackTrace().toString());
            }
        }
    }
}

