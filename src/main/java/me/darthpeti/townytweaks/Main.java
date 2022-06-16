package me.darthpeti.townytweaks;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.palmergames.bukkit.towny.object.WorldCoord;
import me.darthpeti.townytweaks.Towny.listeners.*;
import me.darthpeti.townytweaks.Towny.listeners.discord.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static Logger log = Bukkit.getLogger();
    public static String prefix = "§e[§bTownyTweaks§e]:§f ";
    public static final Cache<WorldCoord, Boolean> siegeZoneCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();
    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        instance = this;
        createCustomConfig();
        loadConfig();
        registerListeners();
    }

    public void createCustomConfig(){
        customConfigFile = new File(getDataFolder(), "webhook.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("webhook.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    public void registerListeners(){
        instance.getServer().getPluginManager().registerEvents(new ArmorStandRestriction(), instance);
        instance.getServer().getPluginManager().registerEvents(new ShulkerRestriction(), instance);
        instance.getServer().getPluginManager().registerEvents(new ShulkerRestrictionInteract(), instance);
        instance.getServer().getPluginManager().registerEvents(new BrewRestrict(), instance);
        instance.getServer().getPluginManager().registerEvents(new SiegeWarEvents(), instance);
        instance.getServer().getPluginManager().registerEvents(new NewTown(getLogger()), instance);
        instance.getServer().getPluginManager().registerEvents(new RuinedTown(getLogger()), instance);
        instance.getServer().getPluginManager().registerEvents(new NewNation(getLogger()),instance);
        instance.getServer().getPluginManager().registerEvents(new DeletedNation(getLogger()),instance);
        instance.getServer().getPluginManager().registerEvents(new DeletedTown(getLogger()),instance);
        instance.getServer().getPluginManager().registerEvents(new NewDay(), instance);
        instance.getServer().getPluginManager().registerEvents(new ArenaListener(), instance);
    }

    @Override
    public void onDisable() {
        instance.saveConfig();
    }

    public void loadConfig(){
        instance.getConfig().options().copyDefaults(false);
        instance.saveDefaultConfig();
    }


}
