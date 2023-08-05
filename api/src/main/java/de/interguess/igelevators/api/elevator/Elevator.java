package de.interguess.igelevators.api.elevator;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Elevator {

    /**
     * @return the location of the elevator
     */
    @NotNull Location getLocation();

    /**
     * @param direction  the direction to search
     * @param accessType the access type to search
     * @return the next {@link Elevator} in the given direction or null if there is no Elevator in the given direction
     */
    @Nullable Elevator getNextElevator(@NotNull BlockFace direction, @NotNull AccessType accessType);

    /**
     * @return the access type of the elevator
     */
    @NotNull AccessType getAccessType();

    /**
     * @param minAccessType the minimal access type to search
     * @return The floor count of the elevator
     */
    @NotNull List<Elevator> getFloors(@NotNull BlockFace direction, @NotNull AccessType minAccessType);

    /**
     * @return whether the elevator is safe
     */
    boolean isSafe();
}