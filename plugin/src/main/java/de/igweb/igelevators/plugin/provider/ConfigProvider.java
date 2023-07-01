package de.igweb.igelevators.plugin.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.igweb.igelevators.plugin.IgElevators;
import de.igweb.igelevators.plugin.config.PluginConfig;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigProvider implements Provider<PluginConfig> {

    private final IgElevators plugin;

    @Inject
    public ConfigProvider(IgElevators plugin) {
        this.plugin = plugin;
    }

    @Override
    public PluginConfig get() {
        try {
            FileConfiguration fileConfiguration = plugin.getConfig();
            return PluginConfig.builder()
                    .minRange(fileConfiguration.getInt("min-range"))
                    .maxRange(fileConfiguration.getInt("max-range"))
                    .successMessage(ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString("messages.success")))
                    .successSound(Sound.valueOf(fileConfiguration.getString("sounds.success").toUpperCase()))
                    .notFoundMessage(ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString("messages.not-found")))
                    .notFoundSound(Sound.valueOf(fileConfiguration.getString("sounds.not-found").toUpperCase()))
                    .unsafeMessage(ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString("messages.unsafe")))
                    .unsafeSound(Sound.valueOf(fileConfiguration.getString("sounds.unsafe").toUpperCase()))
                    .build();
        } catch (Exception exception) {
            handleException();
            return get();
        }
    }

    private void handleException() {
        try {
            plugin.getLogger().warning("There was a error in your config.yml, please fix it!");

            Path configPath = plugin.getDataFolder().toPath().resolve("config.yml");
            Path backupPath = plugin.getDataFolder().toPath().resolve("config.yml.broken");

            Files.deleteIfExists(backupPath);
            Files.copy(configPath, backupPath);
            Files.delete(configPath);

            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
