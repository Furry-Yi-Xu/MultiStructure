package com.yixu.Action;

import com.yixu.Interface.StructureBlockAction;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandAction implements StructureBlockAction {

    private final List<String> commandList;

    public CommandAction(List<String> commandList) {
        this.commandList = commandList;
    }

    @Override
    public void executeAction(Player player, Block block) {

        for (String command : commandList) {

            String[] commandParts = command.split(":", 2);

            String commandType = commandParts[0].trim();
            String executeCommand = PlaceholderAPI.setPlaceholders(player, commandParts[1].trim());

            switch (commandType.toLowerCase()) {
                case "player":
                    player.performCommand(executeCommand);
                    break;

                case "console":
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), executeCommand);
                    break;

                default:
                    player.sendMessage("&c这个命令是一个非法的命令执行者 " + command);
            }

        }
    }
}
