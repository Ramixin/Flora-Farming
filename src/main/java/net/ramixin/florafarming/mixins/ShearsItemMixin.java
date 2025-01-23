package net.ramixin.florafarming.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShearsItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.ramixin.florafarming.ModSounds;
import net.ramixin.florafarming.blocks.ModBlocks;
import net.ramixin.florafarming.ducks.BlockDuck;
import net.ramixin.florafarming.items.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {

    @ModifyReturnValue(method = "useOnBlock", at = @At("RETURN"))
    private ActionResult maybeExtractSeedsFromFlower(ActionResult original, ItemUsageContext context) {
        if(original != ActionResult.PASS) return original;
        if(!(context.getWorld() instanceof ServerWorld serverWorld)) return original;
        BlockPos pos = context.getBlockPos();
        BlockState state = serverWorld.getBlockState(pos);
        if(!(state.getBlock() instanceof BlockDuck duck)) return original;
        if(!duck.floraFarming$isFruitfulBlock()) return original;
        if(!state.get(ModBlocks.HAS_SEEDS)) return original;
        ModItems.dropSeeds(serverWorld, state, pos);
        BlockState blockState = state.with(ModBlocks.HAS_SEEDS, false);
        serverWorld.setBlockState(pos, blockState);
        ItemStack itemStack = context.getStack();
        itemStack.damage(1, context.getPlayer());
        serverWorld.playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModSounds.CUT_SEEDS, SoundCategory.BLOCKS);
        return ActionResult.SUCCESS;
    }

}
