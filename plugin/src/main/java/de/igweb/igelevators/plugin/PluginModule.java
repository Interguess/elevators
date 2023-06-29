package de.igweb.igelevators.plugin;

import com.google.inject.AbstractModule;
import de.igweb.igelevators.api.RegionProvider;
import de.igweb.igelevators.plugin.config.PluginConfig;
import de.igweb.igelevators.plugin.provider.ConfigProvider;
import de.igweb.igelevators.plugin.region.PlotSquaredRegionProvider;
import org.bukkit.Bukkit;

public class PluginModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PluginConfig.class).toProvider(ConfigProvider.class);

        if (Bukkit.getPluginManager().getPlugin("PlotSquared") != null) {
            bind(RegionProvider.class).toInstance(new PlotSquaredRegionProvider());
        } else {
            bind(RegionProvider.class).toInstance((player, location) -> true);
        }
    }
}
