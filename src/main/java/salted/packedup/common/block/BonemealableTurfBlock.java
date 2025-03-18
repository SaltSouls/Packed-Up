package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import salted.packedup.common.block.handlers.TurfHandler;

public interface BonemealableTurfBlock extends BonemealableBlock {

    @Override
    default boolean isValidBonemealTarget(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        TurfHandler manager = new TurfHandler();
        return manager.isBonemealable(state, world, pos);
    }

    @Override
    default boolean isBonemealSuccess(@NotNull Level world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    default void performBonemeal(@NotNull ServerLevel world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        TurfHandler manager = new TurfHandler();
        manager.bonemealTurf(world, random, pos, state);
    }

}
