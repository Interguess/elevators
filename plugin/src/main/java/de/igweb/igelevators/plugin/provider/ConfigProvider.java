package de.igweb.igelevators.plugin.provider;

import com.google.inject.Provider;
import de.igweb.igelevators.plugin.config.PluginConfig;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class ConfigProvider implements Provider<PluginConfig> {

    @Override
    public PluginConfig get() {
        return new PluginConfig(
                0,
                64,
                ChatColor.translateAlternateColorCodes('&', "&aYou have been teleported!"),
                Sound.valueOf("entity_experience_orb_pickup".toUpperCase()),
                ChatColor.translateAlternateColorCodes('&', "&cNo elevator found"),
                Sound.valueOf("block_anvil_fall".toUpperCase()),
                ChatColor.translateAlternateColorCodes('&', "&cUnsafe elevator"),
                Sound.valueOf("block_anvil_break".toUpperCase())
        );
    }
}
