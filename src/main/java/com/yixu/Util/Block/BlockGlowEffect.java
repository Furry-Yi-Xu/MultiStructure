package com.yixu.Util.Block;

import com.yixu.Model.StructureBlock.StructureDataRecord;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.Map;

public class BlockGlowEffect {

    public static void showGlowEffect(Location location, Color color) {
        BlockDisplay display = location.getWorld().spawn(location, BlockDisplay.class);

        display.setBlock(location.getBlock().getBlockData());
        display.setGlowing(true);
        display.setGlowColorOverride(color);
        display.setVisibleByDefault(true);
        display.setPersistent(false);
    }

    public static void hideGlowEffect(Location location) {
        Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, 1, 1, 1);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof BlockDisplay display) {
                display.remove();
            }
        }
    }


    public static void hideAllGlowEffect(Map<Location, StructureDataRecord> recordBlocks) {
        for (Location location : recordBlocks.keySet()) {
            BlockGlowEffect.hideGlowEffect(location);
        }
    }

}
