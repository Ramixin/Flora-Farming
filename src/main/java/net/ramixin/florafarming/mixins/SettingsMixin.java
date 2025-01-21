package net.ramixin.florafarming.mixins;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.ramixin.florafarming.SettingsDuck;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlock.Settings.class)
public class SettingsMixin implements SettingsDuck {

    @Shadow @Nullable private RegistryKey<Block> registryKey;

    @Override
    public RegistryKey<Block> floraFarming$getRegistryKey() {
        return registryKey;
    }
}
