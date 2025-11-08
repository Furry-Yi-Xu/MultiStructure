package com.yixu.Event.Vanilla;

import com.yixu.Interface.PlayerState;
import com.yixu.Manager.Config.StructureConfigManager;
import com.yixu.Executor.StructureActionExecutor;
import com.yixu.Manager.Structure.StructureDataManager;
import com.yixu.Model.StructureBlock.StructureDataRecord;
import com.yixu.MultiStructure;
import com.yixu.Util.Block.BlockGlowEffect;
import com.yixu.Util.Block.ResolveBlockId;
import com.yixu.Util.Structure.StructureCheck;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Set;
import java.util.UUID;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        EquipmentSlot hand = event.getHand();

        if (hand == EquipmentSlot.OFF_HAND) {
            return;
        }

        if (action != Action.LEFT_CLICK_BLOCK && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        Location location = block.getLocation();

        if (block == null) {
            return;
        }

        UUID uuid = player.getUniqueId();
        PlayerState playerStatus = MultiStructure.getInstance().getPlayerStatusManager().getPlayerCurrentStatus(uuid);

        String blockIdWithNameSpace = ResolveBlockId.getBlockIdWithNameSpace(block);

        if (playerStatus.equals(PlayerState.ENABLED)) {
            addBlockToCreateStructureMode(player, block, blockIdWithNameSpace, location, action);
            event.setCancelled(true);
            return;
        }

        checkBlockIsStructure(player, block, blockIdWithNameSpace);

    }

    public void checkBlockIsStructure(Player player, Block block, String blockIdWithNameSpace) {

        StructureConfigManager structureConfigManager = MultiStructure.getInstance().getStructureConfigManager();

        boolean isMainBlock = structureConfigManager.isMainBlock(blockIdWithNameSpace);

        if (!isMainBlock) {
            return;
        }

        Set<String> findStructures = structureConfigManager.getStructuresByMaterial(blockIdWithNameSpace);
        String getStructureName = StructureCheck.checkStructure(findStructures, block);

        if (getStructureName.equals("null")) {
            return;
        }

        new StructureActionExecutor().executeStructureActions(player, block, getStructureName);

    }

    public void addBlockToCreateStructureMode(Player player, Block block, String blockIdWithNameSpace, Location location, Action action) {
        StructureDataRecord record = new StructureDataRecord(location, blockIdWithNameSpace);
        StructureDataManager structureDataManager = MultiStructure.getInstance().getStructureDataManager();

        boolean isAdded;
        String rawMessage;
        Color glowColor;

        if (action == Action.LEFT_CLICK_BLOCK) {
            isAdded = structureDataManager.addMainBlock(player, location, record);
            rawMessage = "主方块";
            glowColor = Color.GREEN;
        } else {
            isAdded = structureDataManager.addSubBlock(player, location, record);
            rawMessage = "副方块";
            glowColor = Color.ORANGE;
        }

        if (isAdded) {
            BlockGlowEffect.showGlowEffect(location, glowColor);
            player.sendMessage("&a你添加了这个" + rawMessage + "！");
        } else {
            BlockGlowEffect.hideGlowEffect(location);
            player.sendMessage("&c你移除了这个" + rawMessage + "！");
        }
    }

}
