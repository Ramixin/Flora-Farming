package net.ramixin.florafarming.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.ramixin.florafarming.blocks.GrowingPlantBlock;

public class SeedItem extends Item {

    private final GrowingPlantBlock plant;

    public SeedItem(GrowingPlantBlock plant, Settings settings) {
        super(settings);
        this.plant = plant;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getSide() != Direction.UP) return ActionResult.PASS;
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        if(!state.isIn(BlockTags.DIRT)) return ActionResult.PASS;
        BlockPos pos = context.getBlockPos().up();
        BlockState toReplace = context.getWorld().getBlockState(pos);
        if(!toReplace.isReplaceable()) return ActionResult.PASS;
        context.getWorld().setBlockState(pos, plant.getDefaultState());
        return ActionResult.SUCCESS;
    }
}
