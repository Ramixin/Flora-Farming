package net.ramixin.florafarming.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.ramixin.florafarming.blocks.ModBlocks;

public class FloraFarmingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        registerNonFull(
                ModBlocks.GROWING_ALLIUM
        );

    }

    static void registerNonFull(Block... blocks) {
        for(Block block : blocks) BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }
}
