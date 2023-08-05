package de.interguess.igelevators.plugin;

import com.google.inject.AbstractModule;
import de.interguess.igelevators.api.RegionProvider;
import de.interguess.igelevators.plugin.config.PluginConfig;
import de.interguess.igelevators.plugin.provider.ConfigProvider;
import de.interguess.igelevators.plugin.region.PlotSquaredRegionProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IgElevators.class).toInstance(JavaPlugin.getPlugin(IgElevators.class));
        bind(PluginConfig.class).toProvider(ConfigProvider.class);

        if (Bukkit.getPluginManager().getPlugin("PlotSquared") != null) {
            bind(RegionProvider.class).toInstance(new PlotSquaredRegionProvider());
        } else {
            bind(RegionProvider.class).toInstance((player, location) -> true);
        }
    }
}
