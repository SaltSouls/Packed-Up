package salted.packedup.common.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import salted.packedup.PackedUp;

import static salted.packedup.common.registry.helpers.RegistryHelper.REGISTRATE;

public class PUSounds {

    public static final RegistryEntry<SoundEvent> DRUM_KNOCK_EMPTY = sound("block.drum_barrel.knock_empty");
    public static final RegistryEntry<SoundEvent> DRUM_KNOCK_FULL = sound("block.drum_barrel.knock_full");
    public static final RegistryEntry<SoundEvent> DRUM_SLOSH = sound("block.drum_barrel.slosh");
    public static final RegistryEntry<SoundEvent> DRUM_SLOSH_ACCENT = sound("block.drum_barrel.slosh_accent");
    public static final RegistryEntry<SoundEvent> DRUM_WARBLE = sound("block.drum_barrel.warble");
    public static final RegistryEntry<SoundEvent> DRUM_WARBLE_ACCENT = sound("block.drum_barrel.warble_accent");

    private static RegistryEntry<SoundEvent> sound(String name) {
        ResourceLocation id = new ResourceLocation(PackedUp.MODID, name);
        return REGISTRATE.simple(name, Registries.SOUND_EVENT, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register() {}
}
