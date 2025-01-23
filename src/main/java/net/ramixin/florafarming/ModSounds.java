package net.ramixin.florafarming;

import net.minecraft.sound.SoundEvent;

public interface ModSounds {

    SoundEvent CUT_SEEDS = SoundEvent.of(FloraFarming.id("item.shears.cut_seeds"));

    SoundEvent POP_SEEDS = SoundEvent.of(FloraFarming.id("entity.bee.pop_seeds"));

}
