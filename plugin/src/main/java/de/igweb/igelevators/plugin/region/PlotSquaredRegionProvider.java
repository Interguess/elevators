package de.igweb.igelevators.plugin.region;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.plot.Plot;
import de.igweb.igelevators.api.RegionProvider;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlotSquaredRegionProvider implements RegionProvider {

    /**
     * Checks if a player is trusted in a region
     *
     * @param player   the player to check
     * @param location the location of the region
     * @return whether the player is trusted in the region
     */
    @Override
    public boolean isTrusted(@NotNull Player player, @NotNull Location location) {
        Plot plot = BukkitUtil.adapt(location).getPlot();
        return plot == null || plot.isAdded(player.getUniqueId());
    }
}
