package com.yixu.Util.Structure;

import com.yixu.Model.StructureBlock.StructureDataRecord;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class StructureDataExporter {

    private final File structureFolder;

    public StructureDataExporter(File dataFolder) {
        this.structureFolder = new File(dataFolder, "structures");

        if (!structureFolder.exists()){
            structureFolder.mkdirs();
        }
    }

    public boolean saveStructure(Map<Location, StructureDataRecord> mainBlocks, Map<Location, StructureDataRecord> subBlocks, String structureName) {
        File file = new File(structureFolder, "SavedStructureData.yml");
        YamlConfiguration yaml = new YamlConfiguration();

        Location mainLocation = mainBlocks.keySet().iterator().next();
        StructureDataRecord mainRecord = mainBlocks.get(mainLocation);

        yaml.set("Structures." + structureName + ".main_block.material", mainRecord.getMaterial());

        List<Map<String, Object>> subList = new ArrayList<>();

        for (Map.Entry<Location, StructureDataRecord> entry : subBlocks.entrySet()) {
            Location sub = entry.getKey();
            StructureDataRecord record = entry.getValue();

            int offsetX = sub.getBlockX() - mainLocation.getBlockX();
            int offsetY = sub.getBlockY() - mainLocation.getBlockY();
            int offsetZ = sub.getBlockZ() - mainLocation.getBlockZ();

            Map<String, Object> subData = new LinkedHashMap<>();

            subData.put("offset", offsetX + "," + offsetY + "," + offsetZ);
            subData.put("material", record.getMaterial());

            subList.add(subData);
        }

        yaml.set("Structures." + structureName + ".sub_block", subList);

        try {
            yaml.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
