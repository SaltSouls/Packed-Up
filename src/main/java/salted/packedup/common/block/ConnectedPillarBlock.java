package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import salted.packedup.common.block.handlers.ConnectionHandler;
import salted.packedup.common.block.state.PUProperties;
import salted.packedup.common.block.state.properties.PillarShape;

public class ConnectedPillarBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<PillarShape> SHAPE = PUProperties.PILLAR_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ConnectedPillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SHAPE, PillarShape.SINGLE).setValue(WATERLOGGED, false));
    }

    @NotNull
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Direction.Axis axis = context.getClickedFace().getAxis();
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        FluidState fluid = world.getFluidState(pos);
        boolean waterlogged = fluid.is(FluidTags.WATER);
        BlockState state = this.defaultBlockState().setValue(AXIS, axis).setValue(WATERLOGGED, waterlogged);

        return getPillarShape(world, pos, state);
    }

    @NotNull
    @Override
    public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return getPillarShape(world, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SHAPE, WATERLOGGED);
    }

    protected static @NotNull BlockState getPillarShape(LevelAccessor world, BlockPos pos, BlockState state) {
        ConnectionHandler handler = new ConnectionHandler();
        ConnectionHandler.Part part = handler.getAlongAxis(world, pos, state);

        return switch (part) {
            case MIDDLE -> state.setValue(SHAPE, PillarShape.MIDDLE);
            case LEFT -> state.setValue(SHAPE, PillarShape.LEFT);
            case RIGHT -> state.setValue(SHAPE, PillarShape.RIGHT);
            case BOTTOM -> state.setValue(SHAPE, PillarShape.BOTTOM);
            case TOP -> state.setValue(SHAPE, PillarShape.TOP);
            default -> state.setValue(SHAPE, PillarShape.SINGLE);
        };
    }

    @Override
    public boolean canPlaceLiquid(@NotNull BlockGetter level, @NotNull BlockPos pos, BlockState state, @NotNull Fluid fluid) {
        return true;
    }

    @NotNull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

}
