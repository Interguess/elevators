package de.igweb.igelevators.plugin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.igweb.igelevators.plugin.listeners.ElevatorListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public class IgElevators extends JavaPlugin {

    @Getter
    @Setter
    private static IgElevators instance;

    private Injector injector;

    @Override
    public void onEnable() {
        setInstance(this);
        setInjector(Guice.createInjector(new PluginModule()));
        
        Bukkit.getPluginManager().registerEvents(injector.getInstance(ElevatorListener.class), this);
        getLogger().info("IgElevators enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("IgElevators disabled!");
    }
}
