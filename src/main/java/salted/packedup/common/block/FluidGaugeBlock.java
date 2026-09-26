package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import salted.packedup.common.block.entity.FluidGaugeBlockEntity;
import salted.packedup.common.block.handlers.ShapeHandler;
import salted.packedup.common.registry.PURegistry;

public class FluidGaugeBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    /**
     * Dial position. {@link #NO_READING} is the fallback shown when the gauge can't see a fluid
     * handler; a gauge that can read its container always sits between {@link #MIN_READING} and
     * {@link #MAX_READING}.
     */
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 23);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final int NO_READING = 0;
    public static final int MIN_READING = 1;
    public static final int MAX_READING = 23;

    private final ShapeHandler handler = new ShapeHandler();

    // authored facing north, then rotated for the other three
    private final VoxelShape[] WALL_SHAPES = handler.getRotations(4.5D, 7.5D, 15.0D, 11.5D, 14.5D, 16.0D, false, false);
    private final VoxelShape[] FLOOR_SHAPES = handler.getRotations(4.5D, 0.0D, 2.0D, 11.5D, 6.5D, 5.5D, false, false);
    private final VoxelShape[] CEILING_SHAPES = handler.getRotations(4.5D, 9.5D, 2.0D, 11.5D, 16.5D, 5.5D, false, false);

    public FluidGaugeBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACE, AttachFace.WALL)
                .setValue(FACING, Direction.NORTH)
                .setValue(FILL, MIN_READING)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, FILL, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clicked = context.getClickedFace();
        AttachFace face = switch (clicked) {
            case UP -> AttachFace.FLOOR;
            case DOWN -> AttachFace.CEILING;
            default -> AttachFace.WALL;
        };

        Direction facing = clicked.getAxis().isHorizontal()
                ? clicked
                : context.getHorizontalDirection().getOpposite();

        boolean water = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;

        return this.defaultBlockState()
                .setValue(FACE, face)
                .setValue(FACING, facing)
                .setValue(FILL, MIN_READING)
                .setValue(WATERLOGGED, water);
    }

    public static Direction connectedDirection(BlockState state) {
        return getConnectedDirection(state);
    }

    public static BlockPos containerPos(BlockState state, BlockPos pos) {
        return pos.relative(connectedDirection(state).getOpposite());
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape[] shapes = switch (state.getValue(FACE)) {
            case FLOOR -> FLOOR_SHAPES;
            case CEILING -> CEILING_SHAPES;
            case WALL -> WALL_SHAPES;
        };
        return shapes[state.getValue(FACING).get2DDataValue()];
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return PURegistry.FLUID_GAUGE_ENTITY.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide) return null;

        return createTickerHelper(type, PURegistry.FLUID_GAUGE_ENTITY.get(), FluidGaugeBlockEntity::serverTick);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> type, BlockEntityType<E> expected, BlockEntityTicker<? super E> ticker) {
        return expected == type ? (BlockEntityTicker<A>) ticker : null;
    }
}
