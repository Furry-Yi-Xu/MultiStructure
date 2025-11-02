package com.yixu.Executor;

import com.yixu.Action.CommandAction;
import com.yixu.MultiStructure;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureActionExecutor {

    public void executeStructureActions(Player player, Block block, String structureName) {
        MultiStructure multiStructureAPI = MultiStructure.getInstance();

        FileConfiguration configConfig = multiStructureAPI.getConfigManager().getConfig("config.yml");

        List<Map<?, ?>> rawList = configConfig.getMapList("Actions." + structureName + ".actions");
        List<Map<String, Object>> actionsConfig = new ArrayList<>();

        for (Map<?, ?> map : rawList) {
            Map<String, Object> castedMap = new HashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                castedMap.put(String.valueOf(entry.getKey()), entry.getValue());
            }
            actionsConfig.add(castedMap);
        }

        if (actionsConfig == null || actionsConfig.isEmpty()) {
            return;
        }

        for (Map<String, Object> actionConf : actionsConfig) {
            String type = (String) actionConf.get("type");

            switch (type.toLowerCase()) {
                case "command":
                    List<String> commandList = (List<String>) actionConf.get("commands");
                    if (commandList != null) {
                        new CommandAction(commandList).executeAction(player, block);
                    }
                    break;
                case "test":
                    List<String> testCommands = (List<String>) actionConf.get("commands");
                    if (testCommands != null) {
                        new CommandAction(testCommands).executeAction(player, block);
                    }
                    break;
                // 可继续扩展其他类型
            }
        }
    }
}
