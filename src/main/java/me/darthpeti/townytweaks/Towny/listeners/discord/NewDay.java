package me.darthpeti.townytweaks.Towny.listeners.discord;

import com.palmergames.bukkit.towny.event.NewDayEvent;
import me.darthpeti.townytweaks.Main;
import me.darthpeti.townytweaks.Towny.util.DiscordWebhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.*;
import java.util.function.Supplier;
import java.util.logging.Level;

public class NewDay implements Listener {
    DiscordWebhook webhook = new DiscordWebhook(Main.instance.getCustomConfig().getString("webhook-url"));
    @EventHandler
    public void onNewDay(NewDayEvent event){
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setColor(new Color(255, 255, 0))
                        .setTitle("A New Day has arrived")
                .setDescription("Town Taxes Collected: " + event.getTownUpkeepCollected() + "\nNation Taxes Collected: " + event.getNationUpkeepCollected())
        );

        if(!event.getBankruptedTowns().isEmpty()){
           String bankruptTowns = event.getBankruptedTowns().toString();
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(255, 0, 0))
                    .setTitle("The following towns have gone bankrupt:")
                    .setDescription(bankruptTowns)
            );
        }

        if(!event.getFallenNations().isEmpty()){
            String fallenNations = event.getFallenNations().toString();
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(255, 0, 0))
                    .setTitle("The following nations have fallen:")
                    .setDescription(fallenNations)
            );
        }

        if(!event.getFallenTowns().isEmpty()){
            String fallenTowns = event.getFallenTowns().toString();
            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(new Color(255, 0, 0))
                    .setTitle("The following towns have fallen into ruin:")
                    .setDescription(fallenTowns)
            );
        }

        try {
            webhook.execute();
        } catch (java.io.IOException e) {
            Main.log.log(Level.SEVERE, (Supplier<String>) e);
        }
    }
}
