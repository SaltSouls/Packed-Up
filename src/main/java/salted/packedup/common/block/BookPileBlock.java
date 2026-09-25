package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BookPileBlock extends HorizontalQuarterSlabBlock {

    public BookPileBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState blockstate = world.getBlockState(pos.below());
        return Block.isFaceFull(blockstate.getCollisionShape(world, pos.below()), Direction.UP) || blockstate.is(this) && blockstate.getValue(LAYERS) == 4;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos blockPos, BlockPos facingPos) {
        return !state.canSurvive(world, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, blockPos, facingPos);
    }

}
