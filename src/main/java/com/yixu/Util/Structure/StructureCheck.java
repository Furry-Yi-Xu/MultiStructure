package com.yixu.Util.Structure;

import com.yixu.Manager.Config.StructureConfigManager;
import com.yixu.Model.StructureBlock.StructureSubBlockData;
import com.yixu.MultiStructure;
import com.yixu.Util.Block.ResolveBlockId;
import org.bukkit.block.Block;

import java.util.*;

public class StructureCheck {

    public static String checkStructure(Set<String> findStructures, Block mainBlock) {
        List<String> sortedStructures = sortStructureMap(findStructures);

        for (String structureName : sortedStructures) {
            List<StructureSubBlockData> subBlocks = MultiStructure.getInstance().getStructureConfigManager().structureSubBlockDataMap.get(structureName);

            if (subBlocks == null || subBlocks.isEmpty()) {
                continue;
            }

            boolean isMatched = true;

            for (StructureSubBlockData sub : subBlocks) {
                Block relative = mainBlock.getRelative(sub.offsetX, sub.offsetY, sub.offsetZ);
                String relativeId = ResolveBlockId.getBlockIdWithNameSpace(relative);

                if (!relativeId.equalsIgnoreCase(sub.material)) {
                    isMatched = false;
                    break;
                }
            }

            if (isMatched) {
                return structureName;
            }
        }

        return "null";
    }

    public static List<String> sortStructureMap(Set<String> findStructures) {
        final StructureConfigManager structureConfigManager = MultiStructure.getInstance().getStructureConfigManager();

        List<String> sortedStructures = new ArrayList<>(findStructures);

        Collections.sort(sortedStructures, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                int sizeA = structureConfigManager.structureSubBlockDataMap.get(a).size();
                int sizeB = structureConfigManager.structureSubBlockDataMap.get(b).size();
                return sizeB - sizeA;
            }
        });

        return sortedStructures;
    }

}
