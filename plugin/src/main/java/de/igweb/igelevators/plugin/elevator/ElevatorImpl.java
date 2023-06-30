package de.igweb.igelevators.plugin.elevator;

import de.igweb.igelevators.api.elevator.AccessType;
import de.igweb.igelevators.api.elevator.Elevator;
import de.igweb.igelevators.plugin.IgElevators;
import de.igweb.igelevators.plugin.config.PluginConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.DaylightDetector;

import java.util.ArrayList;
import java.util.List;

public class ElevatorImpl implements Elevator {

    private static final PluginConfig config = IgElevators.getInstance().getInjector().getInstance(PluginConfig.class);

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
     * @param direction     the direction to search
     * @param minAccessType the minimal access type to search
     * @return the next {@link Elevator} up or null if there is no Elevator up
     */
    @Override
    public Elevator getNextElevator(BlockFace direction, AccessType minAccessType) {
        Location location = this.location.clone();
        for (int i = config.getMinRange() + 1; i <= config.getMaxRange(); i++) {
            Location next = location.add(direction.getModX(), direction.getModY(), direction.getModZ());

            if (!next.getBlock().getType().equals(Material.DAYLIGHT_DETECTOR)) {
                continue;
            }

            Elevator elevator = new ElevatorImpl(next);

            if (minAccessType == AccessType.PRIVATE || elevator.getAccessType() == AccessType.PUBLIC) {
                return elevator;
            }
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
        return AccessType.fromDaylightDetector(daylightDetector);
    }

    /**
     * @return whether the elevator is safe
     */
    @Override
    public boolean isSafe() {
        return location.clone().add(0, 1, 0).getBlock().isEmpty()
                && location.clone().add(0, 2, 0).getBlock().isEmpty();
    }

    /**
     * @param minAccessType the minimal access type to search
     * @return The floor count of the elevator
     */
    @Override
    public List<Elevator> getFloors(BlockFace direction, AccessType minAccessType) {
        List<Elevator> floors = new ArrayList<>();

        Elevator elevator = new ElevatorImpl(location);
        do {
            if (minAccessType == AccessType.PRIVATE || elevator.getAccessType() == AccessType.PUBLIC) {
                floors.add(elevator);
            }

            elevator = elevator.getNextElevator(direction, minAccessType);
        } while (elevator != null);
        return floors;
    }

}