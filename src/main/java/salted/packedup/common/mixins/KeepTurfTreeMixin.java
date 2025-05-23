package salted.packedup.common.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import salted.packedup.common.tag.PUTags;

import java.util.function.BiConsumer;

@Mixin(TrunkPlacer.class)
public class KeepTurfTreeMixin {
    /**
     * Due to how Trees generate, this mixin is needed to prevent Turf blocks from becoming dirt under a Giant Spruce Tree growth.
     */
    @Inject(at = @At(value = "HEAD"), method = "setDirtAt", cancellable = true)
    private static void cancelSetDirtIfTurf(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config, CallbackInfo ci) {
        if (level.isStateAtPosition(pos, state -> state.is(PUTags.TURF_BLOCKS))) {
            ci.cancel();
        }
    }
}
