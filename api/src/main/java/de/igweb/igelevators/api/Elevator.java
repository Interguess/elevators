package de.igweb.igelevators.api;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

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

}