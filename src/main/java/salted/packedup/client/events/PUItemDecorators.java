package salted.packedup.client.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import salted.packedup.PackedUp;
import salted.packedup.client.events.decorators.DrumFillDecorator;
import salted.packedup.common.registry.PURegistry;

@Mod.EventBusSubscriber(modid = PackedUp.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PUItemDecorators {

    @SubscribeEvent
    public static void registerDecorations(RegisterItemDecorationsEvent event) {
        for (var entry : PURegistry.DRUM_BARREL_ENTRIES) {
            event.register(entry.get().asItem(), DrumFillDecorator.INSTANCE);
        }
    }
}
