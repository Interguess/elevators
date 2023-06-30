package de.igweb.igelevators.api.elevator;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.List;

public interface Elevator {

    /**
     * @return the location of the elevator
     */
    Location getLocation();

    /**
     * @param direction  the direction to search
     * @param accessType the access type to search
     * @return the next {@link Elevator} in the given direction or null if there is no Elevator in the given direction
     */
    Elevator getNextElevator(BlockFace direction, AccessType accessType);

    /**
     * @return the access type of the elevator
     */
    AccessType getAccessType();

    /**
     * @return whether the elevator is safe
     */
    boolean isSafe();

    /**
     * @param minAccessType the minimal access type to search
     * @return The floor count of the elevator
     */
    List<Elevator> getFloors(BlockFace direction, AccessType minAccessType);

}