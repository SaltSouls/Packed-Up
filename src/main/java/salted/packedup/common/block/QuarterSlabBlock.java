package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import salted.packedup.common.block.state.PUProperties;

import javax.annotation.Nullable;
import java.util.Objects;

public class QuarterSlabBlock extends Block implements SimpleWaterloggedBlock {
    public static final int MAX_HEIGHT = 4;
    public static final int HEIGHT_PASSABLE = 2;
    public static final IntegerProperty LAYERS = PUProperties.QUARTER_LAYERS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public QuarterSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1).setValue(WATERLOGGED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS) - 1];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return getShape(state, world, pos, ctx);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        if (Objects.requireNonNull(type) == PathComputationType.LAND) {
            return state.getValue(LAYERS) <= HEIGHT_PASSABLE;
        }
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, WATERLOGGED);
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
            if (layer == 3) {
                return blockstate.setValue(LAYERS, 4).setValue(WATERLOGGED, false);
            } else {
                return blockstate.setValue(LAYERS, layer + 1).setValue(WATERLOGGED, flag);
            }
        } else {
            return this.defaultBlockState().setValue(LAYERS, 1).setValue(WATERLOGGED, flag);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        int layer = state.getValue(LAYERS);

        if (ctx.getItemInHand().is(this.asItem()) && layer < MAX_HEIGHT) {
            if (ctx.replacingClickedOnBlock()) { return ctx.getClickedFace() == Direction.UP; }
            else { return true; }
        }
        else { return false; }
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return state.getValue(LAYERS) != 4 ? SimpleWaterloggedBlock.super.canPlaceLiquid(level, pos, state, fluid) : false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public IntegerProperty getQuarterLayers() {
        return PUProperties.QUARTER_LAYERS;
    }
}
