package salted.packedup.client.events;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import salted.packedup.common.registry.PUBlocks;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PUColorHandlers {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) ->
                level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) :
                        GrassColor.getDefaultColor(), PUBlocks.GRASS_TURF.get(), PUBlocks.GRASS_TURF_LAYER.get(), PUBlocks.GRASS_BALE.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((itemStack, tintIndex) -> {
            BlockColors blockColors = event.getBlockColors();
            BlockState blockstate = ((BlockItem)itemStack.getItem()).getBlock().defaultBlockState();

            return blockColors.getColor(blockstate, null, null, tintIndex);
        }, PUBlocks.GRASS_TURF.get(), PUBlocks.GRASS_TURF_LAYER.get(), PUBlocks.GRASS_BALE.get());
    }
}
