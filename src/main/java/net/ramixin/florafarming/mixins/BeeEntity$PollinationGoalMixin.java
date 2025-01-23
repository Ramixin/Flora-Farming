package net.ramixin.florafarming.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.ramixin.florafarming.ducks.BlockDuck;
import net.ramixin.florafarming.blocks.ModBlocks;
import net.ramixin.florafarming.items.ModItems;
import net.ramixin.florafarming.ModSounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(BeeEntity.PollinateGoal.class)
public abstract class BeeEntity$PollinationGoalMixin {

    @Shadow protected abstract Optional<BlockPos> getFlower();

    @WrapOperation(method = "stop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/BeeEntity;setHasNectar(Z)V"))
    private void dropSeedWhenFinishedPollination(BeeEntity instance, boolean hasNectar, Operation<Void> original) {
        operation: {
            Optional<BlockPos> flower = this.getFlower();
            if(flower.isEmpty()) break operation;
            if(!(instance.getWorld() instanceof ServerWorld serverWorld)) break operation;
            BlockPos flowerPos = flower.get();
            BlockState flowerState = serverWorld.getBlockState(flowerPos);
            if(!(flowerState.getBlock() instanceof BlockDuck duck)) break operation;
            if(!duck.floraFarming$isFruitfulBlock()) break operation;
            if(!flowerState.get(ModBlocks.HAS_SEEDS)) break operation;
            ModItems.dropSeeds(serverWorld, flowerState, flowerPos);
            serverWorld.playSound(null, flowerPos.getX(), flowerPos.getY(), flowerPos.getZ(), ModSounds.POP_SEEDS, SoundCategory.BLOCKS);
        }
        original.call(instance, hasNectar);
    }

}
