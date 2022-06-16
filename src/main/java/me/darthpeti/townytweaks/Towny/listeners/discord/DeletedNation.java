package me.darthpeti.townytweaks.Towny.listeners.discord;

import com.palmergames.bukkit.towny.event.DeleteNationEvent;
import me.darthpeti.townytweaks.Main;
import me.darthpeti.townytweaks.Towny.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.logging.Logger;

public class DeletedNation implements Listener {

    private Logger logger;

    public DeletedNation(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTown(DeleteNationEvent event) {
        if (Main.instance.getCustomConfig().getString("notification-nation-disband").equalsIgnoreCase("true")) {
            String nationName = event.getNationName();
            DiscordWebhook webhook = new DiscordWebhook(Main.instance.getCustomConfig().getString("webhook-url"));

            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(255, 0, 0))
                    .setDescription("The nation of " + nationName + " has disbanded!")
            );
            try {
                webhook.execute();
            } catch (java.io.IOException e) {
                logger.severe(e.getStackTrace().toString());
            }
        }
    }
}

