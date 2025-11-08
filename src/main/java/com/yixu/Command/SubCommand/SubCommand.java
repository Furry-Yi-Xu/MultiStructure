package com.yixu.Command.SubCommand;

import com.yixu.Interface.PlayerState;
import com.yixu.Manager.Config.StructureConfigManager;
import com.yixu.Manager.Player.PlayerStatusManager;
import com.yixu.Manager.Structure.StructureDataManager;
import com.yixu.Model.StructureBlock.StructureDataRecord;
import com.yixu.MultiStructure;
import com.yixu.Util.Block.BlockGlowEffect;
import com.yixu.Util.Message.MessageUtil;
import com.yixu.Util.Structure.StructureDataExporter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class SubCommand {

    public void reloadConfig(Player player) {
        MultiStructure multiStructureAPI = MultiStructure.getInstance();

        multiStructureAPI.getConfigManager().reloadAllConfigs();
        multiStructureAPI.getStructureConfigManager().reloadAllConfigs();

        MessageUtil.sendMessage(player, "Command.Succeed-Reload");
    }

    public void createStructure(Player player) {

        UUID uuid = player.getUniqueId();

        PlayerStatusManager playerStatusManager = MultiStructure.getInstance().getPlayerStatusManager();
        PlayerState playerStatus = playerStatusManager.getPlayerCurrentStatus(uuid);

        if (playerStatus.equals(PlayerState.DISABLED)) {
            playerStatusManager.changePlayerStatus(uuid, PlayerState.ENABLED);
            MultiStructure.getInstance().getStructureDataManager().clearPlayerAllRecordBlockData(player);
            player.sendMessage("&a已开启创建记录模式\n左键 - 记录主方块，右键 - 记录副方块");
            return;
        }

        player.sendMessage("&c你已经开启记录模式了！");

    }

    public void saveStructure(Player player, String[] args) {
        UUID uuid = player.getUniqueId();

        PlayerStatusManager playerStatusManager = MultiStructure.getInstance().getPlayerStatusManager();
        PlayerState playerStatus = playerStatusManager.getPlayerCurrentStatus(uuid);

        if (playerStatus.equals(PlayerState.DISABLED)) {
            player.sendMessage("&c对不起，请先开启记录模式，记录数据！");
            return;
        }
        
        StructureDataManager structureDataManager = MultiStructure.getInstance().getStructureDataManager();
        StructureDataExporter structureDataExporter = structureDataManager.getStructureDataExporter();

        Map<Location, StructureDataRecord> mainBlocks = structureDataManager.getAllMainBlocks(player);
        Map<Location, StructureDataRecord> subBlocks = structureDataManager.getAllSubBlocks(player);

        if (mainBlocks.isEmpty()) {
            player.sendMessage("&c你没有设置主方块！");
            return;
        }

        if (subBlocks.isEmpty()) {
            player.sendMessage("&c你没有设置副方块！");
            return;
        }

        if (args.length < 2) {
            player.sendMessage("&c结构名不可为空！");
            return;
        }

        String structureName = args[1];

        StructureConfigManager structureConfigManager = MultiStructure.getInstance().getStructureConfigManager();
        boolean isExistedStructureName = structureConfigManager.checkStructureNameIskExisted(structureName);

        if (isExistedStructureName) {
            player.sendMessage("&c这个结构名已经存在了，请重新输入！");
            return;
        }

        boolean success = structureDataExporter.saveStructure(mainBlocks, subBlocks, structureName);

        if (success) {
            BlockGlowEffect.hideAllGlowEffect(mainBlocks);
            BlockGlowEffect.hideAllGlowEffect(subBlocks);
            MultiStructure.getInstance().getStructureDataManager().clearPlayerAllRecordBlockData(player);
            player.sendMessage("§a结构已成功保存，已自动清理所有方块数据，自动关闭选择模式！");
        } else {
            player.sendMessage("§c保存结构时发生错误，请重新尝试生成！");
        }

        playerStatusManager.changePlayerStatus(uuid, PlayerState.DISABLED);
    }

    public void cancelCreateStructure(Player player) {
        UUID uuid = player.getUniqueId();

        PlayerStatusManager playerStatusManager = MultiStructure.getInstance().getPlayerStatusManager();
        StructureDataManager structureDataManager = MultiStructure.getInstance().getStructureDataManager();

        playerStatusManager.changePlayerStatus(uuid, PlayerState.DISABLED);
        structureDataManager.clearPlayerAllRecordBlockData(player);

        player.sendMessage("§a结构已取消创建，已自动清理所有方块数据，自动关闭选择模式！");
    }

}