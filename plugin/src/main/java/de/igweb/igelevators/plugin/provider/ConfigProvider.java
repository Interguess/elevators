package de.igweb.igelevators.plugin.provider;

import com.google.inject.Provider;
import de.igweb.igelevators.plugin.IgElevators;
import de.igweb.igelevators.plugin.config.PluginConfig;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigProvider implements Provider<PluginConfig> {

    @Override
    public PluginConfig get() {
        Path configPath = getConfigPath();
        copyDefaultConfig(configPath);

        try {
            YamlConfiguration yamlConfiguration = loadConfiguration(configPath);
            return buildPluginConfig(yamlConfiguration);
        } catch (Exception exception) {
            handleException(configPath);
            throw exception;
        }
    }

    private Path getConfigPath() {
        return IgElevators.getInstance().getDataFolder().toPath().resolve("config.yml");
    }

    private void copyDefaultConfig(Path configPath) {
        if (!Files.exists(configPath)) {
            try {
                Files.copy(IgElevators.getInstance().getResource("config.yml"), configPath);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private YamlConfiguration loadConfiguration(Path configPath) {
        return YamlConfiguration.loadConfiguration(configPath.toFile());
    }

    private PluginConfig buildPluginConfig(YamlConfiguration yamlConfiguration) {
        return PluginConfig.builder()
                .minRange(yamlConfiguration.getInt("min-range"))
                .maxRange(yamlConfiguration.getInt("max-range"))
                .successMessage(ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("messages.success")))
                .successSound(Sound.valueOf(yamlConfiguration.getString("sounds.success").toUpperCase()))
                .notFoundMessage(ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("messages.not-found")))
                .notFoundSound(Sound.valueOf(yamlConfiguration.getString("sounds.not-found").toUpperCase()))
                .unsafeMessage(ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("messages.unsafe")))
                .unsafeSound(Sound.valueOf(yamlConfiguration.getString("sounds.unsafe").toUpperCase()))
                .build();
    }

    private void handleException(Path configPath) {
        try {
            Path backupPath = configPath.resolveSibling("config.yml.broken");
            Files.copy(configPath, backupPath);
            Files.delete(configPath);
            Files.copy(IgElevators.getInstance().getResource("config.yml"), configPath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
