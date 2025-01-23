package net.ramixin.florafarming.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.ramixin.florafarming.FloraFarming;
import net.ramixin.florafarming.ModFoodComponents;
import net.ramixin.florafarming.blocks.GrowingPlantBlock;
import net.ramixin.florafarming.blocks.ModBlocks;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModItems {

    public static final HashMap<Identifier, Identifier> CROP_MAP = new HashMap<>();
    public static final HashMap<Block, SeedItem> SEED_MAP = new HashMap<>();

    public static final Item CRUMEL = registerFoodItem("crumel", "allium", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.CRUMEL));
    public static final Item PETALPOD = registerFoodItem("petalpod", "azure_bluet", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.PETALPOD));
    public static final Item BRISPEN = registerFoodItem("brispen", "blue_orchid", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.BRISPEN));
    public static final Item LUFFIN = registerFoodItem("luffin", "cornflower", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.LUFFIN));
    public static final Item SUNDROP = registerFoodItem("sundrop", "dandelion", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.SUNDROP));
    public static final Item BRIMLET = registerFoodItem("brimlet", "closed_eyeblossom", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.BRIMLET));
    public static final Item SWEET_BRIMLET = registerFoodItem("sweet_brimlet", "open_eyeblossom", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.SWEET_BRIMLET));
    public static final Item DRAMBLE = registerFoodItem("dramble", "lily_of_the_valley", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.DRAMBLE));
    public static final Item CLORREN = registerFoodItem("clorren", "oxeye_daisy", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.CLORREN));
    public static final Item ROSETATO = registerFoodItem("rosetato", "poppy", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.ROSETATO));
    public static final Item HONEYPLUM = registerFoodItem("honeyplum", "orange_tulip", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.HONEYPLUM));
    public static final Item FLORAFIG = registerFoodItem("florafig", "pink_tulip", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.FLORAFIG));
    public static final Item VINDLE = registerFoodItem("vindle", "red_tulip", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.VINDLE));
    public static final Item HUVLET = registerFoodItem("huvlet", "white_tulip", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.HUVLET));
    public static final Item DEWROOT = registerFoodItem("dewroot", "wither_rose", Item::new, new Item.Settings().maxCount(64).food(ModFoodComponents.DEWROOT));

    public static final Item ALLIUM_SEEDS = registerSeedItem("allium_seeds", Blocks.ALLIUM, ModBlocks.GROWING_ALLIUM, new Item.Settings().maxCount(64));

    private static void addCropToMap(String flowerName, String cropName) {
        CROP_MAP.put(Identifier.ofVanilla(flowerName), FloraFarming.id(cropName));
    }

    private static SeedItem registerSeedItem(String name, Block flower, GrowingPlantBlock plant, Item.Settings settings) {
        SeedItem item = registerItem(name, (newSettings) -> new SeedItem(plant, newSettings), settings);
        SEED_MAP.put(flower, item);
        return item;
    }

    private static <T extends Item> T registerFoodItem(String name, String flowerName, Function<Item.Settings, T> itemConstructor, Item.Settings settings) {
        addCropToMap(flowerName, name);
        return registerItem(name, itemConstructor, settings);
    }

    private static <T extends Item> T registerItem(String name, Function<Item.Settings, T> itemConstructor, Item.Settings settings) {
        Identifier id = FloraFarming.id(name);
        settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, id));
        T item = itemConstructor.apply(settings);
        Registry.register(Registries.ITEM, id, item);
        return item;
    }

    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, FloraFarming.id(id), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void dropSeeds(ServerWorld serverWorld, BlockState flowerState, BlockPos flowerPos) {
        Item seedItem = ModItems.SEED_MAP.get(flowerState.getBlock());
        if (seedItem == null) return;
        ItemEntity entity = new ItemEntity(serverWorld, flowerPos.getX(), flowerPos.getY()+0.5, flowerPos.getZ(), new ItemStack(seedItem));
        entity.setToDefaultPickupDelay();
        serverWorld.spawnEntity(entity);
    }

    public static void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.GLOW_BERRIES.getDefaultStack(),
                CRUMEL,
                PETALPOD,
                BRISPEN,
                LUFFIN,
                SUNDROP,
                BRIMLET,
                SWEET_BRIMLET,
                DRAMBLE,
                CLORREN,
                ROSETATO,
                HONEYPLUM,
                FLORAFIG,
                VINDLE,
                HUVLET,
                DEWROOT
        ));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.addAfter(Items.BEETROOT_SEEDS.getDefaultStack(),
                ALLIUM_SEEDS
        ));
    }
}
