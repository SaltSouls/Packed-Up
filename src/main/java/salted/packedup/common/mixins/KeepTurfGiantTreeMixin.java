package salted.packedup.common.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import salted.packedup.common.tag.PUTags;

@Mixin(Feature.class)
public class KeepTurfGiantTreeMixin {
    /**
     * Due to how Trees generate, this mixin is needed to prevent Turf blocks from becoming Podzol under a Giant Spruce Tree growth.
     */
    @Inject(at = @At(value = "HEAD"), method = "isGrassOrDirt", cancellable = true)
    private static void keepTurf(LevelSimulatedReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (world.isStateAtPosition(pos, state -> state.is(PUTags.TURF_BLOCKS))) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
