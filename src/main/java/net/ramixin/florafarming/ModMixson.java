package net.ramixin.florafarming;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.Identifier;
import net.ramixin.florafarming.blocks.ModBlocks;
import net.ramixin.florafarming.items.ModItems;
import net.ramixin.mixson.atp.GeneratorContext;
import net.ramixin.mixson.atp.annotations.Generator;
import net.ramixin.mixson.atp.annotations.events.GenerativeMixsonEvent;
import net.ramixin.mixson.inline.EventContext;

@SuppressWarnings("unused")
public class ModMixson {

    @GenerativeMixsonEvent("floraFarming:generator")
    private static void floraFarming$editFlowerLootTables(EventContext context) {
        Identifier key = Identifier.of(context.getEventName());
        Identifier cropId = ModItems.CROP_MAP.get(key);
        if(cropId == null) return;
        JsonObject table = context.getFile().getAsJsonObject();
        JsonArray pools = table.getAsJsonArray("pools");
        JsonObject cropItem = JsonParser.parseString(getPoolEntry(key, cropId)).getAsJsonObject();
        pools.add(cropItem);
    }

    @Generator("floraFarming:generator")
    private static void floraFarming$lootTableIdGenerator(GeneratorContext context) {
        for(Identifier block : ModBlocks.FRUITFUL_BLOCKS) {
            context.addFile("loot_table/blocks/"+block.getPath(), block.toString());
        }
    }

    private static String getPoolEntry(Identifier flowerId, Identifier cropId) {
        return String.format("{\"bonus_rolls\":0,\"conditions\":[{\"condition\":\"minecraft:match_tool\",\"predicate\":{\"items\":\"#minecraft:hoes\"}},{\"block\":\"%s\",\"condition\":\"minecraft:block_state_property\",\"properties\":{\"fruitful\":\"true\"}}],\"entries\":[{\"type\":\"minecraft:item\",\"name\":\"%s\"}],\"rolls\":1}", flowerId, cropId);
    }

}
