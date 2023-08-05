package de.interguess.igelevators.plugin.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.interguess.igelevators.plugin.IgElevators;
import de.interguess.igelevators.plugin.config.PluginConfig;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
                    .successMessage(parseMessage(fileConfiguration.getString("messages.success")))
                    .successSound(parseSound(fileConfiguration.getString("sounds.success")))
                    .notFoundMessage(parseMessage(fileConfiguration.getString("messages.not-found")))
                    .notFoundSound(parseSound(fileConfiguration.getString("sounds.not-found")))
                    .unsafeMessage(parseMessage(fileConfiguration.getString("messages.unsafe")))
                    .unsafeSound(parseSound(fileConfiguration.getString("sounds.unsafe")))
                    .build();
        } catch (Exception exception) {
            handleException();
            return get();
        }
    }

    private String parseMessage(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    private Sound parseSound(String input) {
        return Sound.valueOf(input.toUpperCase());
    }

    private void handleException() {
        try {
            plugin.getLogger().warning("There was a error in your config.yml, please fix it!");

            Path dataFolderPath = plugin.getDataFolder().toPath();
            Path configPath = dataFolderPath.resolve("config.yml");
            Path backupPath = dataFolderPath.resolve("config.yml.broken");

            Files.move(configPath, backupPath, StandardCopyOption.REPLACE_EXISTING);

            plugin.saveDefaultConfig();
            plugin.reloadConfig();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
