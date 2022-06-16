package me.darthpeti.townytweaks.Towny.listeners;

import com.gmail.goosius.siegewar.objects.Siege;
import com.gmail.goosius.siegewar.utils.SiegeWarDistanceUtil;
import com.palmergames.bukkit.towny.event.actions.TownyItemuseEvent;
import me.darthpeti.townytweaks.Towny.util.ConfigUtil;
import me.darthpeti.townytweaks.Towny.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SiegeWarEvents implements Listener {
    @EventHandler
    public void tntCartExplode(EntityExplodeEvent event){
        if(ConfigUtil.disableTntcartBlockDamageBelowBanner()){
            Location location = event.getLocation();
            if(event.getEntityType().equals(EntityType.MINECART_TNT) && SiegeWarDistanceUtil.isLocationInActiveSiegeZone(location)){
                Siege siege = LocationUtil.getActiveSiegeInLocation(location);
                assert siege != null;
                if(location.getY() < siege.getFlagLocation().getY()){
                    event.blockList().clear();
                }
            }
        }
    }
    @EventHandler
    public void townyItemuseEvent (TownyItemuseEvent event) {
        //Setup this way to check Ender_Pearl first so we don't check every PlayerTeleport location and Pearl Status.
        if(event.getMaterial().equals(Material.ENDER_PEARL)){
            if(ConfigUtil.allowPearlsInBesiegedTowns()){
                if(SiegeWarDistanceUtil.isLocationInActiveSiegeZone(event.getLocation())){
                    event.setCancelled(false);
                }
            }
        }
    }
    @EventHandler
    public void playerTeleportEvent(PlayerTeleportEvent event){
        //Setup this way to check Ender_Pearl first so we don't check every PlayerTeleport location and Pearl Status.
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            if(ConfigUtil.allowPearlsInBesiegedTowns()){
                if((SiegeWarDistanceUtil.isLocationInActiveSiegeZone(event.getPlayer().getLocation()))){
                    if(!LocationUtil.isSafe(event.getTo())){
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(ConfigUtil.keepInventoryInSiege()) {
            Location deathloc = event.getPlayer().getLocation();
            Player player = event.getPlayer();
            if(player.hasPermission("siegewar.nation.siege.battle.points") || player.hasPermission("siegewar.town.siege.battle.points")){
                if (SiegeWarDistanceUtil.isLocationInActiveSiegeZone(deathloc)) {
                    event.setKeepInventory(true);
                }
            }
        }
    }
}
