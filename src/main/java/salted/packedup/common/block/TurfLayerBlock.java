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
import salted.packedup.common.block.handlers.TurfHandler;

public class TurfLayerBlock extends QuarterSlabBlock implements BonemealableTurfBlock {

    public TurfLayerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isCrouching()) { return InteractionResult.PASS; }

        TurfHandler manager = new TurfHandler();
        return manager.shovelTurf(state, world, pos, player, hit);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction dir, IPlantable plantable) {
        if (state.is(this) && state.getValue(LAYERS) < MAX_HEIGHT) return false;
        return super.canSustainPlant(state, world, pos, dir, plantable);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);

        TurfHandler manager = new TurfHandler();
        manager.spawnMyceliumParticles(state, world, pos, random);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        TurfHandler manager = new TurfHandler();
        manager.spreadGrass(state, world, pos, random);
    }
}
