package me.darthpeti.townytweaks.Towny.listeners;

import me.darthpeti.townytweaks.Towny.util.ConfigUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.gmail.goosius.siegewar.SiegeController;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ArenaListener implements Listener {

    @EventHandler
    public void onArmorDamage(PlayerItemDamageEvent event) {
        if(ConfigUtil.disableArmorDamageInArenaPlots()){
            Player player = event.getPlayer();
            TownBlock townBlock = TownyAPI.getInstance().getTownBlock(player.getLocation());
            if(townBlock == null || townBlock.getType() != TownBlockType.ARENA)
                return;
            if(!townBlock.hasTown() || SiegeController.hasSiege(Objects.requireNonNull(TownyAPI.getInstance().getTown(player.getLocation()))))
                return;
            ItemStack item = event.getItem();
            if(!item.getType().toString().endsWith("HELMET") && !item.getType().toString().endsWith("CHESTPLATE") && !item.getType().toString().endsWith("LEGGINGS") && !item.getType().toString().endsWith("BOOTS"))
                return;
            event.setCancelled(true);
        }
    }
}
