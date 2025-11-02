package com.yixu.Manager.Structure;

import com.yixu.Model.StructureBlock.StructureDataRecord;
import com.yixu.Util.Location.LocationUtil;
import com.yixu.Util.Structure.StructureDataExporter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class StructureDataManager {

    private final JavaPlugin plugin;

    private final StructureDataExporter structureDataExporter;

    private final Map<UUID, Map<Location, StructureDataRecord>> playerMainBlocks = new HashMap<>();
    private final Map<UUID, Map<Location, StructureDataRecord>> playerSubBlocks = new HashMap<>();

    public StructureDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        structureDataExporter = new StructureDataExporter(plugin.getDataFolder());
    }

    public boolean addMainBlock(Player player, Location location, StructureDataRecord record) {
        location = LocationUtil.normalizeLocation(location);
        UUID uuid = player.getUniqueId();

        playerMainBlocks.putIfAbsent(uuid, new HashMap<>());
        Map<Location, StructureDataRecord> blocks = playerMainBlocks.get(uuid);

        if (blocks.containsKey(location)) {
            blocks.remove(location);
            return false;
        }

        blocks.put(location, record);
        return true;
    }

    public Map<Location, StructureDataRecord> getAllMainBlocks(Player player) {
        return playerMainBlocks.getOrDefault(player.getUniqueId(), Collections.emptyMap());
    }

    public boolean addSubBlock(Player player, Location location, StructureDataRecord record) {
        location = LocationUtil.normalizeLocation(location);
        UUID uuid = player.getUniqueId();

        playerSubBlocks.putIfAbsent(uuid, new HashMap<>());
        Map<Location, StructureDataRecord> blocks = playerSubBlocks.get(uuid);

        if (blocks.containsKey(location)) {
            blocks.remove(location);
            return false;
        }

        blocks.put(location, record);
        return true;
    }

    public Map<Location, StructureDataRecord> getAllSubBlocks(Player player) {
        return playerSubBlocks.getOrDefault(player.getUniqueId(), Collections.emptyMap());
    }

    public void clearAllPlayerBlockData(Player player) {
        UUID uuid = player.getUniqueId();
        playerMainBlocks.remove(uuid);
        playerSubBlocks.remove(uuid);
    }

    public StructureDataExporter getStructureDataExporter() {
        return structureDataExporter;
    }


}
