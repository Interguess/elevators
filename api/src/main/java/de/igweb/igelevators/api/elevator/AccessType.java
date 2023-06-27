package de.igweb.igelevators.api.elevator;

import org.bukkit.block.data.type.DaylightDetector;

public enum AccessType {

    PUBLIC,
    PRIVATE;

    public static AccessType fromDayLightDetector(DaylightDetector daylightDetector) {
        return daylightDetector.isInverted() ? PRIVATE : PUBLIC;
    }
}
