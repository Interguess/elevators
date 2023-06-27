package de.igweb.igelevators.plugin.elevator;

import de.igweb.igelevators.api.AccessType;
import de.igweb.igelevators.api.Elevator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.DaylightDetector;

public class ElevatorImpl implements Elevator {

    private static final int MIN_RANGE = 1;

    private static final int MAX_RANGE = 50;

    private final Location location;

    private final DaylightDetector daylightDetector;

    /**
     * Elevators are used to move between floors. They can be public or private.
     * Public elevators can be used by anyone. Private elevators can only be used by the plot-owner.
     *
     * @param location the location of the elevator
     */
    public ElevatorImpl(Location location) {
        this.location = location;
        this.daylightDetector = (DaylightDetector) location.getBlock().getBlockData();
    }

    /**
     * @param direction  the direction to search
     * @param accessType the access type to search
     * @return the next {@link Elevator} up or null if there is no Elevator up
     */
    @Override
    public Elevator getNextElevator(BlockFace direction, AccessType accessType) {
        for (int i = MIN_RANGE; i < MAX_RANGE; i++) {
            Location next = location.clone().add(direction.getModX(), direction.getModY(), direction.getModZ());
            if (!next.getBlock().getType().equals(Material.DAYLIGHT_DETECTOR)) continue;
            Elevator elevator = new ElevatorImpl(next);
            if (elevator.getAccessType().equals(accessType)) return elevator;
        }
        return null;
    }

    /**
     * @return the location of the elevator
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * @return the access type of the elevator
     */
    @Override
    public AccessType getAccessType() {
        return AccessType.fromDayLightDetector(daylightDetector);
    }

    /**
     * @return whether the elevator is safe
     */
    @Override
    public boolean isSafe() {
        return location.clone().add(0, 1, 0).getBlock().isEmpty()
                && location.clone().add(0, 2, 0).getBlock().isEmpty();
    }
}