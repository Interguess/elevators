package de.igweb.igelevators.plugin.config;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.Sound;

@Data
public class PluginConfig {

    private final Material elevatorBlockMaterial;

    private final String elevatorText;

    private final Sound elevatorSound;

}
