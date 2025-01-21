package net.ramixin.florafarming.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.StateManager;
import net.ramixin.florafarming.ModBlocks;
import net.ramixin.florafarming.SettingsDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Unique
    private boolean floraFarming$fruitfulBlock = false;

    @Shadow @Deprecated public abstract RegistryEntry.Reference<Block> getRegistryEntry();

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;appendProperties(Lnet/minecraft/state/StateManager$Builder;)V"))
    private void insertBlockPropertyIfFlower(Block instance, StateManager.Builder<Block, BlockState> builder, Operation<Void> original, @Local(argsOnly = true) AbstractBlock.Settings settings) {
        if(settings instanceof SettingsDuck duck) {
            RegistryKey<Block> key = duck.floraFarming$getRegistryKey();
            for(RegistryKey<?> fruitfulBlock : ModBlocks.FRUITFUL_BLOCKS)
                if(fruitfulBlock.equals(key)) {
                    builder.add(ModBlocks.FRUITFUL);
                    floraFarming$fruitfulBlock = true;
                }
        }
        original.call(instance, builder);
    }

    @ModifyReturnValue(method = "getDefaultState", at = @At("RETURN"))
    private BlockState changeDefaultStateForFruitfulFlowers(BlockState original) {
        if(!floraFarming$fruitfulBlock) return original;
        return original.with(ModBlocks.FRUITFUL, true);
    }

    @ModifyReturnValue(method = "getPlacementState", at = @At("RETURN"))
    private BlockState changeDefaultPlacementStateForFruitfulFlowers(BlockState original) {
        if(!floraFarming$fruitfulBlock) return original;
        return original.with(ModBlocks.FRUITFUL, false);
    }

}
