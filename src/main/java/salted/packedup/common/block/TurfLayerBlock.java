package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;
import salted.packedup.common.block.handlers.TurfHandler;

public class TurfLayerBlock extends QuarterSlabBlock implements BonemealableTurfBlock {

    public TurfLayerBlock(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (player.isCrouching()) { return InteractionResult.PASS; }

        TurfHandler manager = new TurfHandler();
        return manager.shovelTurf(state, world, pos, player, hit);
    }

    @Override
    public boolean canSustainPlant(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull Direction dir, @NotNull IPlantable plantable) {
        if (state.is(this) && state.getValue(LAYERS) < MAX_HEIGHT) return false;
        return super.canSustainPlant(state, world, pos, dir, plantable);
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
