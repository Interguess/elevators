package de.interguess.igelevators.api.event;


import de.interguess.igelevators.api.elevator.Elevator;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * The ElevatorUseEvent is called when a player uses an Elevator (Also when the Elevator is not safe or no Elevator was found)
 */
public class PlayerElevatorUseEvent extends Event implements Cancellable {

    private final Player player;

    private final Elevator from;

    private final Elevator to;

    private boolean cancelled;

    /**
     * The ElevatorUseEvent is called when a player uses an Elevator (Also when the Elevator is not safe or no Elevator was found)
     *
     * @param player the player that used the Elevator
     * @param from   the Elevator that was used
     * @param to     the Elevator that was targeted, or null if no Elevator was found or the Elevator is not safe
     */
    public PlayerElevatorUseEvent(@NotNull Player player, @NotNull Elevator from, @NotNull Elevator to) {
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

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
