package com.yixu.Model.StructureBlock;

import org.bukkit.Location;

public class StructureDataRecord {
    private final String world;
    private final int x;
    private final int y;
    private final int z;
    private final String material;

    public StructureDataRecord(Location location, String material) {
        this.world = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }
}

