package de.igweb.igelevators.plugin.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.igweb.igelevators.api.RegionProvider;
import de.igweb.igelevators.api.elevator.AccessType;
import de.igweb.igelevators.api.elevator.Elevator;
import de.igweb.igelevators.api.event.PlayerElevatorUseEvent;
import de.igweb.igelevators.plugin.config.PluginConfig;
import de.igweb.igelevators.plugin.elevator.ElevatorImpl;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

@Singleton
public class ElevatorListener implements Listener {

    private final PluginConfig config;

    private final RegionProvider regionProvider;

    @Inject
    public ElevatorListener(PluginConfig config, RegionProvider regionProvider) {
        this.config = config;
        this.regionProvider = regionProvider;
    }

    @EventHandler
    public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent event) {
        if (event.getStatistic().equals(Statistic.JUMP)) {
            handle(event.getPlayer(), BlockFace.UP);
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            handle(event.getPlayer(), BlockFace.DOWN);
        }
    }

    private void handle(Player player, BlockFace direction) {
        Location location = player.getLocation();

        if (!location.getBlock().getType().equals(Material.DAYLIGHT_DETECTOR)) {
            return;
        }

        AccessType minAccessType = regionProvider.isTrusted(player, location) ? AccessType.PRIVATE : AccessType.PUBLIC;
        Elevator from = new ElevatorImpl(location);
        Elevator to = from.getNextElevator(direction, minAccessType);

        if (to == null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(config.getNotFoundMessage()));
            player.playSound(player.getLocation(), config.getNotFoundSound(), 1, 1);
            return;
        }

        PlayerElevatorUseEvent event = new PlayerElevatorUseEvent(player, from, to);

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        if (!to.isSafe()) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(config.getUnsafeMessage()));
            player.playSound(player.getLocation(), config.getUnsafeSound(), 1, 1);
            return;
        }

        int floor = to.getFloors(BlockFace.DOWN, minAccessType).size();
        int floors = floor + to.getFloors(BlockFace.UP, minAccessType).size() - 1;

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                config.getSuccessMessage()
                        .replace("%floor%", String.valueOf(floor))
                        .replace("%floors%", String.valueOf(floors))

        ));

        player.playSound(player.getLocation(), config.getSuccessSound(), 1, 1);
        player.teleport(to.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }
}
