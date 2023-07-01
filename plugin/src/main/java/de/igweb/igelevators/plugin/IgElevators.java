package de.igweb.igelevators.plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.igweb.igelevators.plugin.config.PluginConfig;
import de.igweb.igelevators.plugin.listeners.ElevatorListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class IgElevators extends JavaPlugin {

    private static Injector injector;

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();

        injector = Guice.createInjector(new PluginModule());

        Bukkit.getPluginManager().registerEvents(injector.getInstance(ElevatorListener.class), this);
        getLogger().info("IgElevators enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("IgElevators disabled!");
    }

    public static PluginConfig getPluginConfig() {
        return injector.getInstance(PluginConfig.class);
    }
}
