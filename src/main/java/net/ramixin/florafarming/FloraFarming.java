package net.ramixin.florafarming;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.ramixin.mixson.inline.Mixson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloraFarming implements ModInitializer {

    public static final String MOD_ID = "flora_farming";
    private static final Logger LOGGER = LoggerFactory.getLogger("FloraFarming");


    @Override
    public void onInitialize() {
        ModItems.onInitialize();

        for(RegistryKey<?> key : ModBlocks.FRUITFUL_BLOCKS)
            Mixson.registerEvent(
                    Mixson.DEFAULT_PRIORITY,
                    "loot_table/blocks/"+key.getValue().getPath(),
                    "Add FloraFarming Loot tables",
                    (context) -> {
                        Identifier cropId = ModItems.CROP_MAP.get(key.getValue());
                        if(cropId == null) return;
                        JsonObject table = context.getFile().getAsJsonObject();
                        JsonArray pools = table.getAsJsonArray("pools");
                        JsonObject cropItem = JsonParser.parseString(getPoolEntry(cropId)).getAsJsonObject();
                        pools.add(cropItem);
                    },
                    false
            );

    }

    private static String getPoolEntry(Identifier cropId) {
        return String.format("{\"bonus_rolls\": 0.0, \"conditions\": [{\"condition\": \"minecraft:match_tool\", \"predicate\": {\"items\": \"#minecraft:hoes\"}}], \"entries\": [{\"type\": \"minecraft:item\", \"name\": \"%s\"}], \"rolls\": 1.0}", cropId);
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static void logInfo(String msg, Object... args) {
        LOGGER.info(msg, args);
    }

    public static void logError(String msg, Object... args) {
        LOGGER.error(msg, args);
    }
}
