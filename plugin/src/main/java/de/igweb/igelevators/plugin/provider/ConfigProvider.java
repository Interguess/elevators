package de.igweb.igelevators.plugin.provider;

import com.google.inject.Provider;
import de.igweb.igelevators.plugin.config.PluginConfig;
import org.bukkit.Sound;

public class ConfigProvider implements Provider<PluginConfig> {

    @Override
    public PluginConfig get() {
        return new PluginConfig(
                3,
                50,
                "elevator used",
                Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                "elevator not found",
                Sound.BLOCK_ANVIL_FALL,
                "elevator is unsafe",
                Sound.BLOCK_ANVIL_BREAK
        );
    }
}
