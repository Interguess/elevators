package de.igweb.igelevators.plugin;

import com.google.inject.AbstractModule;
import de.igweb.igelevators.plugin.config.PluginConfig;
import de.igweb.igelevators.plugin.provider.ConfigProvider;

public class PluginModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PluginConfig.class).toProvider(ConfigProvider.class);
    }
}
