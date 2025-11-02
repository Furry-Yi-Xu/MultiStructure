package com.yixu.Manager.Config;

import com.yixu.Model.StructureBlock.StructureSubBlockData;
import com.yixu.MultiStructure;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * 支持一个主方块材质对应多个结构
 */
public class StructureConfigManager {

    private final JavaPlugin plugin;

    // 主方块材质 -> 对应结构名集合
    public final Map<String, Set<String>> mainBlockToStructures = new HashMap<>();

    // 结构名 -> 配置对象（可选）
    public final Map<String, List<StructureSubBlockData>> structureSubBlockDataMap = new HashMap<>();

    //缓存所有结构名
    public final List<String> structureNameList = new ArrayList<>();

    public StructureConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadStructures();
    }

    public void loadStructures() {
        MultiStructure multiStructureAPI = MultiStructure.getInstance();

        FileConfiguration configStructure = multiStructureAPI.getConfigManager().getConfig("structure.yml");

        ConfigurationSection structures = configStructure.getConfigurationSection("Structures");

        if (structures == null) {
            return;
        }

        for (String structureName : structures.getKeys(false)) {
            ConfigurationSection structSection = structures.getConfigurationSection(structureName);

            if (structSection == null) {
                continue;
            }

            String mainMat = structSection.getString("main_block.material");

            if (mainMat == null) {
                continue;
            }

            List<Map<?, ?>> subList = structSection.getMapList("sub_block");
            List<StructureSubBlockData> subBlocks = new ArrayList<>();

            for (Map<?, ?> subEntry : subList) {
                String offsetStr = (String) subEntry.get("offset");
                String materialStr = (String) subEntry.get("material");

                String[] offsets = offsetStr.split(",");
                int x = Integer.parseInt(offsets[0].trim());
                int y = Integer.parseInt(offsets[1].trim());
                int z = Integer.parseInt(offsets[2].trim());

                subBlocks.add(new StructureSubBlockData(x, y, z, materialStr));
            }

            structureNameList.add(structureName);
            mainBlockToStructures.computeIfAbsent(mainMat, k -> new HashSet<>()).add(structureName);
            structureSubBlockDataMap.put(structureName, subBlocks);
        }
    }

    /**
     * 根据主方块材质查找对应的所有结构名
     */
    public Set<String> getStructuresByMaterial(String material) {
        return mainBlockToStructures.getOrDefault(material, Collections.emptySet());
    }

    /**
     * 判断某个方块材质是否是主方块
     */
    public boolean isMainBlock(String material) {
        return mainBlockToStructures.containsKey(material);
    }

    /**
     * 根据结构名获取对应配置
     */
    public List<String> getStructureConfigByStructureName(String structureName) {
        MultiStructure multiStructureAPI = MultiStructure.getInstance();
        FileConfiguration configStructure = multiStructureAPI.getConfigManager().getConfig("structure.yml");

        return configStructure.getStringList(structureName + "." + "sub_block");
    }

    public boolean checkStructureNameIskExist(String structureName) {
        return structureNameList.contains(structureName);
    }

    /**
     * 重载所有配置文件
     */
    public void reloadAllConfigs() {
        mainBlockToStructures.clear();
        structureSubBlockDataMap.clear();
        loadStructures();
    }

}
