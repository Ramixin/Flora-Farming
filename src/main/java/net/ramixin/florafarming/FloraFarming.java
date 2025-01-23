package net.ramixin.florafarming;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.ramixin.florafarming.blocks.ModBlocks;
import net.ramixin.florafarming.items.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloraFarming implements ModInitializer {

    public static final String MOD_ID = "flora_farming";
    private static final Logger LOGGER = LoggerFactory.getLogger("FloraFarming");


    @Override
    public void onInitialize() {
        ModItems.onInitialize();
        ModBlocks.onInitialize();

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
