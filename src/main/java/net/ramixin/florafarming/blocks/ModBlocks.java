package net.ramixin.florafarming.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.ramixin.florafarming.FloraFarming;
import net.ramixin.florafarming.items.ModItems;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    public static final BooleanProperty FRUITFUL = BooleanProperty.of("fruitful");
    public static final BooleanProperty HAS_SEEDS = BooleanProperty.of("has_seeds");

    public static final Identifier[] FRUITFUL_BLOCKS = new Identifier[]{
            Identifier.ofVanilla("allium"),
            Identifier.ofVanilla("azure_bluet"),
            Identifier.ofVanilla("blue_orchid"),
            Identifier.ofVanilla("cornflower"),
            Identifier.ofVanilla("dandelion"),
            Identifier.ofVanilla("closed_eyeblossom"),
            Identifier.ofVanilla("open_eyeblossom"),
            Identifier.ofVanilla("lily_of_the_valley"),
            Identifier.ofVanilla("oxeye_daisy"),
            Identifier.ofVanilla("poppy"),
            Identifier.ofVanilla("orange_tulip"),
            Identifier.ofVanilla("pink_tulip"),
            Identifier.ofVanilla("red_tulip"),
            Identifier.ofVanilla("white_tulip"),
            Identifier.ofVanilla("wither_rose")
    };

    public static final GrowingPlantBlock GROWING_ALLIUM = registerGrowingBlock("growing_allium", () -> Blocks.ALLIUM, () -> ModItems.ALLIUM_SEEDS);

    private static GrowingPlantBlock registerGrowingBlock(String name, Supplier<Block> finalStageSupplier, Supplier<Item> seedItemSupplier) {
        return registerBlock(name, (givenSettings) -> new GrowingPlantBlock(finalStageSupplier, seedItemSupplier, givenSettings), AbstractBlock.Settings.create().breakInstantly().noCollision().ticksRandomly().offset(AbstractBlock.OffsetType.XZ));
    }


    private static <T extends Block> T registerBlock(String name, Function<AbstractBlock.Settings, T> blockConstructor, AbstractBlock.Settings settings) {
        Identifier id = FloraFarming.id(name);
        settings.registryKey(RegistryKey.of(RegistryKeys.BLOCK, id));
        T item = blockConstructor.apply(settings);
        Registry.register(Registries.BLOCK, id, item);
        return item;
    }

    public static void onInitialize() {

    }
}
