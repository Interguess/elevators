package de.igweb.igelevators.plugin.config;

import lombok.Builder;
import lombok.Data;
import org.bukkit.Sound;

@Data
@Builder
public class PluginConfig {

    private final int minRange;

    private final int maxRange;

    private final String successMessage;

    private final Sound successSound;

    private final String notFoundMessage;

    private final Sound notFoundSound;

    private final String unsafeMessage;

    private final Sound unsafeSound;

}