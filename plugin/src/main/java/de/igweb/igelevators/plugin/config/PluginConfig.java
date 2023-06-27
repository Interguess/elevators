package de.igweb.igelevators.plugin.config;

import lombok.Data;
import org.bukkit.Sound;

@Data
public class PluginConfig {

    private final String successMessage;

    private final Sound successSound;

    private final String notFoundMessage;

    private final Sound notFoundSound;

    private final String unsafeMessage;

    private final Sound unsafeSound;

}
