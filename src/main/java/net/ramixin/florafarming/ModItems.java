package net.ramixin.florafarming;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.function.Function;

public class ModItems {

    public static final HashMap<Identifier, Identifier> CROP_MAP = new HashMap<>();

    public static final Item CRUMEL = registerItem("crumel", "allium", Item::new, new Item.Settings().maxCount(64).food(FoodComponents.COOKED_MUTTON));

    public static void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.GLOW_BERRIES.getDefaultStack(),
                CRUMEL
        ));
    }

    private static void addCropToMap(String flowerName, String cropName) {
        CROP_MAP.put(Identifier.ofVanilla(flowerName), FloraFarming.id(cropName));
    }

    private static <T extends Item> T registerItem(String name, String flowerName, Function<Item.Settings, T> itemConstructor, Item.Settings settings) {
        addCropToMap(flowerName, name);
        Identifier id = FloraFarming.id(name);
        settings.registryKey(RegistryKey.of(RegistryKeys.ITEM, id));
        T item = itemConstructor.apply(settings);
        Registry.register(Registries.ITEM, id, item);
        return item;
    }

}
