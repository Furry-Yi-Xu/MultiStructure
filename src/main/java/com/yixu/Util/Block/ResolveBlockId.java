package com.yixu.Util.Block;

import net.momirealms.craftengine.bukkit.api.CraftEngineBlocks;
import net.momirealms.craftengine.core.block.ImmutableBlockState;
import org.bukkit.block.Block;

public class ResolveBlockId {

    public static String getBlockIdWithNameSpace(Block block) {

        boolean isCustomBlock = CraftEngineBlocks.isCustomBlock(block);

        if (isCustomBlock) {
            return "craftengine:" + getBlockId(block);
        }

        return "minecraft:" + getBlockId(block);
    }

    public static String getBlockId(Block block) {

        boolean isCustomBlock = CraftEngineBlocks.isCustomBlock(block);

        if (isCustomBlock) {
            ImmutableBlockState blockState = CraftEngineBlocks.getCustomBlockState(block);
            return blockState.customBlockState().ownerId().asString();
        }

        return block.getBlockData().getMaterial().toString().toLowerCase();
    }

}
