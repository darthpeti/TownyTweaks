package me.darthpeti.townytweaks.Towny.listeners.discord;

import com.palmergames.bukkit.towny.event.NewTownEvent;
import me.darthpeti.townytweaks.Main;
import me.darthpeti.townytweaks.Towny.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


import java.awt.*;
import java.util.logging.Logger;


public class NewTown implements Listener {

    private Logger logger;

    public NewTown(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTown(NewTownEvent event) {
        if (Main.instance.getCustomConfig().getString("notification-town-creation").equalsIgnoreCase("true")) {
            String townName = event.getTown().getName();
            String mayorName = event.getTown().getMayor().getName();
            DiscordWebhook webhook = new DiscordWebhook(Main.instance.getCustomConfig().getString("webhook-url"));

            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(0, 255, 0))
                    .setDescription(mayorName + " has created a new town called " + townName + "!")
            );
            try {
                webhook.execute();
            } catch (java.io.IOException e) {
                logger.severe(e.getStackTrace().toString());
            }
        }
    }
}
