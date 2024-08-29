package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class BookPileBlock extends QuarterSlabBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public BookPileBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, FACING, WATERLOGGED);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        int layer = state.getValue(LAYERS);

        if (ctx.getItemInHand().is(this.asItem()) && layer < MAX_HEIGHT) {
            if (ctx.replacingClickedOnBlock()) {
                return ctx.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return false;
        }
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Level level = ctx.getLevel();
        BlockState blockstate = ctx.getLevel().getBlockState(pos);
        FluidState fluid = level.getFluidState(pos);
        boolean flag = fluid.getType() == Fluids.WATER;

        if (blockstate.is(this)) {
            int layer = blockstate.getValue(LAYERS);
            Direction direction = blockstate.getValue(FACING);
            if (layer == 3) {
                return blockstate.setValue(LAYERS, 4)
                        .setValue(FACING, direction)
                        .setValue(WATERLOGGED, false);
            } else {
                return blockstate.setValue(LAYERS, layer + 1)
                        .setValue(FACING, direction)
                        .setValue(WATERLOGGED, flag);
            }
        } else {
            return this.defaultBlockState().setValue(LAYERS, 1)
                    .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                    .setValue(WATERLOGGED, flag);
        }
    }
}
