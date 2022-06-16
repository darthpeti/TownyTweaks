package me.darthpeti.townytweaks.Towny.listeners.discord;

import com.palmergames.bukkit.towny.event.NewNationEvent;
import me.darthpeti.townytweaks.Main;
import me.darthpeti.townytweaks.Towny.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.logging.Logger;

public class NewNation implements Listener {

    private Logger logger;

    public NewNation(Logger logger) {
        this.logger = logger;
    }

    @EventHandler
    public void onTown(NewNationEvent event) {
        if (Main.instance.getCustomConfig().getString("notification-nation-create").equalsIgnoreCase("true")) {
            String nationName = event.getNation().getName();
            String kingName = event.getNation().getKing().getName();
            DiscordWebhook webhook = new DiscordWebhook(Main.instance.getCustomConfig().getString("webhook-url"));

            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(0, 255, 0))
                    .setDescription(kingName + " has made a new nation called " + nationName + "!")
            );
            try {
                webhook.execute();
            } catch (java.io.IOException e) {
                logger.severe(e.getStackTrace().toString());
            }
        }
    }
}

