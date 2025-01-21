package net.ramixin.florafarming;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final BooleanProperty FRUITFUL = BooleanProperty.of("fruitful");

    public static final RegistryKey<?>[] FRUITFUL_BLOCKS = new RegistryKey[]{
            getKey("allium"),
            getKey("azure_bluet"),
            getKey("blue_orchid"),
            getKey("cornflower"),
            getKey("dandelion"),
            getKey("closed_eyeblossom"),
            getKey("open_eyeblossom"),
            getKey("lily_of_the_valley"),
            getKey("oxeye_daisy"),
            getKey("poppy"),
            getKey("orange_tulip"),
            getKey("pink_tulip"),
            getKey("red_tulip"),
            getKey("white_tulip"),
            getKey("wither_rose")
    };

    private static RegistryKey<Block> getKey(String blockId) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(blockId));
    }
}
