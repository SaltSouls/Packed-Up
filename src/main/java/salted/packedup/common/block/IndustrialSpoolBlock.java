package salted.packedup.common.block;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import salted.packedup.common.block.handlers.ShapeHandler;
import salted.packedup.common.block.state.PUProperties;
import salted.packedup.common.block.state.properties.PillarShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IndustrialSpoolBlock extends ConnectedPillarBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // block collision
    private final ShapeHandler handler = new ShapeHandler();
    private final ImmutableMap<BlockState, VoxelShape> SHAPES;

    private final VoxelShape[] TOP = handler.getRotations(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D, true, true);
    private final VoxelShape[] BOTTOM = handler.getRotations(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D, true, true);
    private final VoxelShape[] MIDDLE = handler.getRotations(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D, true, true);

    private ImmutableBiMap<BlockState, VoxelShape> shapeConstructor(ImmutableList<BlockState> states) {
        ImmutableBiMap.Builder<BlockState, VoxelShape> shape = new ImmutableBiMap.Builder<>();
        for (BlockState state : states) {
            Direction.Axis axis = state.getValue(AXIS);
            PillarShape pillar = state.getValue(SHAPE);
            List<VoxelShape> shapes = new ArrayList<>();

            switch (pillar) {
                case TOP, RIGHT:
                    shapes.add(TOP[axis.ordinal()]);
                    shapes.add(MIDDLE[axis.ordinal()]);
                    break;
                case BOTTOM, LEFT:
                    shapes.add(MIDDLE[axis.ordinal()]);
                    shapes.add(BOTTOM[axis.ordinal()]);
                    break;
                case MIDDLE:
                    shapes.add(MIDDLE[axis.ordinal()]);
                    break;
                default:
                    shapes.add(TOP[axis.ordinal()]);
                    shapes.add(MIDDLE[axis.ordinal()]);
                    shapes.add(BOTTOM[axis.ordinal()]);
            }

            shape.put(state, handler.combineAll(shapes));
        }
        return shape.build();
    }

    public IndustrialSpoolBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
        SHAPES = this.shapeConstructor(this.getStateDefinition().getPossibleStates());
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return Objects.requireNonNull(SHAPES.get(state));
    }

    @NotNull
    @Override
    public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return Objects.requireNonNull(SHAPES.get(state));
    }

    @NotNull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction.Axis axis = ctx.getClickedFace().getAxis();
        BlockPos pos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        FluidState fluid = ctx.getLevel().getFluidState(pos);
        boolean flag = fluid.getType() == Fluids.WATER;
        BlockState state = this.defaultBlockState().setValue(AXIS, axis).setValue(WATERLOGGED, flag);

        return getPillarShape(world, pos, state);
    }

    @Override
    public boolean canPlaceLiquid(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Fluid fluid) {
        return true;
    }

    @NotNull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    public EnumProperty<PillarShape> getShape() {
        return PUProperties.PILLAR_SHAPE;
    }
}
