package de.igweb.igelevators.api.event;


import de.igweb.igelevators.api.elevator.Elevator;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * The ElevatorUseEvent is called when a player uses an Elevator (Also when the Elevator is not safe or no Elevator was found)
 */
public class PlayerElevatorUseEvent extends Event {

    private final Player player;

    private final Elevator from;

    private final Elevator to;

    /**
     * The ElevatorUseEvent is called when a player uses an Elevator (Also when the Elevator is not safe or no Elevator was found)
     *
     * @param player         the player that used the Elevator
     * @param elevator       the Elevator that was used
     * @param targetElevator the Elevator that was targeted, or null if no Elevator was found or the Elevator is not safe
     */
    public PlayerElevatorUseEvent(@NotNull Player player, @NotNull Elevator from, @NotNull Elevator to) {
        super(true);
        this.player = player;
        this.from = from;
        this.to = to;
    }

    /**
     * @return A list of event handlers, stored per-event. Based on lahwran's fevents.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return new HandlerList();
    }

    /**
     * Gets the player that used the Elevator
     *
     * @return the player that used the Elevator
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the Elevator that was used
     *
     * @return the Elevator that was used
     */
    public Elevator getFrom() {
        return from;
    }

    /**
     * Gets the Elevator that was targeted, or null if no Elevator was found or the Elevator is not safe
     *
     * @return the targeted Elevator or null
     */
    public Elevator getTo() {
        return to;
    }
}
