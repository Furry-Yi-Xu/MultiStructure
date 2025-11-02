package com.yixu.Util.Location;

import org.bukkit.Location;

public class LocationUtil {

    public static Location normalizeLocation(Location location) {
        Location cloneLocation = location.clone();
        cloneLocation.setX(cloneLocation.getBlockX());
        cloneLocation.setY(cloneLocation.getBlockY());
        cloneLocation.setZ(cloneLocation.getBlockZ());
        cloneLocation.setPitch(0);
        cloneLocation.setYaw(0);
        return cloneLocation;
    }

}
