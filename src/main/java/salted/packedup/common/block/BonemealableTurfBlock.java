package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import salted.packedup.common.block.handlers.TurfHandler;

public interface BonemealableTurfBlock extends BonemealableBlock {

    @Override
    default boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state, boolean isClient) {
        TurfHandler manager = new TurfHandler();
        return manager.isBonemealable(state, world, pos);
    }

    @Override
    default boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    default void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        TurfHandler manager = new TurfHandler();
        manager.bonemealTurf(world, random, pos, state);
    }

}
