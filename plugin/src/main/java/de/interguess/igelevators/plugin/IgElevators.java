package de.interguess.igelevators.plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.interguess.igelevators.plugin.config.PluginConfig;
import de.interguess.igelevators.plugin.listeners.ElevatorListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class IgElevators extends JavaPlugin {

    private static Injector injector;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        injector = Guice.createInjector(new PluginModule());

        Bukkit.getPluginManager().registerEvents(injector.getInstance(ElevatorListener.class), this);
        this.getLogger().info("IgElevators enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("IgElevators disabled!");
    }

    public static PluginConfig getPluginConfig() {
        return injector.getInstance(PluginConfig.class);
    }
}
