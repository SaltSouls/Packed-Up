package salted.packedup.common.block;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import salted.packedup.common.block.handlers.ShapeHandler;

import java.util.ArrayList;
import java.util.List;

public class CrateLidBlock extends Block implements SimpleWaterloggedBlock {
    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // block collision
    private final ImmutableMap<BlockState, VoxelShape> SHAPES;
    private static final Direction defaultFacing = Direction.NORTH;

    private final VoxelShape BOTTOM = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    private final VoxelShape[] TOP1 = ShapeHandler.getRotated(ShapeHandler.rotate(Block.box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 8.0D), defaultFacing));
    private final VoxelShape[] TOP2 = ShapeHandler.getRotated(ShapeHandler.rotate(Block.box(0.0D, 3.0D, 3.0D, 16.0D, 6.0D, 7.0D), defaultFacing));
    private final VoxelShape[] TOP3 = ShapeHandler.getRotated(ShapeHandler.rotate(Block.box(0.0D, 6.0D, 2.0D, 16.0D, 9.0D, 6.0D), defaultFacing));
    private final VoxelShape[] TOP4 = ShapeHandler.getRotated(ShapeHandler.rotate(Block.box(0.0D, 9.0D, 1.0D, 16.0D, 12.0D, 5.0D), defaultFacing));
    private final VoxelShape[] TOP5 = ShapeHandler.getRotated(ShapeHandler.rotate(Block.box(0.0D, 12.0D, 0.0D, 16.0D, 15.0D, 4.0D), defaultFacing));

    private ImmutableBiMap<BlockState, VoxelShape> shapeConstructor(ImmutableList<BlockState> states) {
        ImmutableBiMap.Builder<BlockState, VoxelShape> shape = new ImmutableBiMap.Builder<>();
        for (BlockState state : states) {
            Direction facing = state.getValue(FACING);
            Half half = state.getValue(HALF);
            List<VoxelShape> shapes = new ArrayList<>();

            if (half == Half.TOP) {
                shapes.add(TOP1[facing.get2DDataValue()]);
                shapes.add(TOP2[facing.get2DDataValue()]);
                shapes.add(TOP3[facing.get2DDataValue()]);
                shapes.add(TOP4[facing.get2DDataValue()]);
                shapes.add(TOP5[facing.get2DDataValue()]);
            }
            else shapes.add(BOTTOM);

            shape.put(state, ShapeHandler.combineAll(shapes));
        }
        return shape.build();
    }

    public CrateLidBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, defaultFacing).setValue(HALF, Half.BOTTOM).setValue(WATERLOGGED, false));
        SHAPES = this.shapeConstructor(this.getStateDefinition().getPossibleStates());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPES.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPES.get(state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Half half = state.getValue(HALF);
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        BlockState blockstate = world.getBlockState(blockpos);
        boolean bottomSupport = canSupportCenter(world, pos.below(), Direction.UP);

        if (half.equals(Half.TOP)) { return bottomSupport && blockstate.isFaceSturdy(world, blockpos, direction); }
        else return bottomSupport;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos blockPos, BlockPos facingPos) {
        return !state.canSurvive(world, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, blockPos, facingPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        Direction direction = ctx.getClickedFace();
        Direction facing = ctx.getHorizontalDirection().getOpposite();
        FluidState fluid = ctx.getLevel().getFluidState(pos);
        boolean flag = fluid.getType() == Fluids.WATER;

        BlockState state = this.defaultBlockState().setValue(FACING, facing).setValue(HALF, Half.BOTTOM).setValue(WATERLOGGED, flag);
        return direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getClickLocation().y - pos.getY() > 0.5D)) ? state : state.setValue(FACING, direction).setValue(HALF, Half.TOP);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
