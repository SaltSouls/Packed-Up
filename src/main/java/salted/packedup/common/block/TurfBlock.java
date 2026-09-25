package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import salted.packedup.common.block.handlers.TurfHandler;

public class TurfBlock extends Block implements BonemealableBlock {

    public TurfBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        TurfHandler manager = new TurfHandler();
        return manager.isBonemealable(state, world, pos);
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        TurfHandler manager = new TurfHandler();
        manager.bonemealTurf(world, random, pos, state);
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (player.isCrouching()) { return InteractionResult.PASS; }

        TurfHandler manager = new TurfHandler();
        return manager.shovelTurf(state, world, pos, player, hit);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.animateTick(state, world, pos, random);

        TurfHandler manager = new TurfHandler();
        manager.spawnMyceliumParticles(state, world, pos, random);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        TurfHandler manager = new TurfHandler();
        manager.spreadGrass(state, world, pos, random);
    }
}
