package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import org.jetbrains.annotations.Nullable;
import salted.packedup.common.block.entity.FluidGaugeBlockEntity;
import salted.packedup.common.block.handlers.ShapeHandler;
import salted.packedup.common.registry.PURegistry;

public class FluidGaugeBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    /**
     * Dial position. 0 is the "no reading" fallback, shown only when the gauge can't
     * see a fluid handler. A working gauge always sits between 1 and 23.
     */
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 23);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    /** Lowest dial position for a gauge that can actually read its container. */
    public static final int MIN_READING = 1;
    public static final int MAX_READING = 23;
    /** Shown when there is nothing to read. */
    public static final int NO_READING = 0;

    // authored facing north; rotated for the other three
    private static final VoxelShape WALL_SHAPE = Block.box(4.5, 7.5, 15.0, 11.5, 14.5, 16.0);
    private static final VoxelShape FLOOR_SHAPE = Block.box(4.5, 0.0, 2.0, 11.5, 6.5, 5.5);
    private static final VoxelShape CEILING_SHAPE = Block.box(4.5, 9.5, 2.0, 11.5, 16.5, 5.5);

    public FluidGaugeBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACE, AttachFace.WALL)
                .setValue(FACING, Direction.NORTH)
                .setValue(FILL, MIN_READING)
                .setValue(WATERLOGGED, false));
    }

    /** Direction the gauge faces, away from the block it's mounted on. */
    public static Direction connectedDirection(BlockState state) {
        return getConnectedDirection(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, FILL, WATERLOGGED);
    }

    // ============================================================
    // Placement
    // ============================================================
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

    /** The block this gauge is mounted on, which is the one it reads. */
    public static BlockPos containerPos(BlockState state, BlockPos pos) {
        return pos.relative(connectedDirection(state).getOpposite());
    }

    // ============================================================
    // Shape
    // ============================================================
    private final ShapeHandler handler = new ShapeHandler();

    private final VoxelShape[] WALL_SHAPES = handler.getRotations(4.5D, 7.5D, 15.0D, 11.5D, 14.5D, 16.0D, false, false);
    private final VoxelShape[] FLOOR_SHAPES = handler.getRotations(4.5D, 0.0D, 2.0D, 11.5D, 6.5D, 5.5D, false, false);
    private final VoxelShape[] CEILING_SHAPES = handler.getRotations(4.5D, 9.5D, 2.0D, 11.5D, 16.5D, 5.5D, false, false);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape[] shapes = switch (state.getValue(FACE)) {
            case FLOOR -> FLOOR_SHAPES;
            case CEILING -> CEILING_SHAPES;
            case WALL -> WALL_SHAPES;
        };
        return shapes[state.getValue(FACING).get2DDataValue()];
    }

    // ============================================================
    // Waterlogging
    // ============================================================
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  net.minecraft.world.level.LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    // ============================================================
    // Block entity
    // ============================================================
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return PURegistry.FLUID_GAUGE_BE.create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return createTickerHelper(type, PURegistry.FLUID_GAUGE_BE.get(), FluidGaugeBlockEntity::serverTick);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> expected, BlockEntityTicker<? super E> ticker) {
        return expected == type ? (BlockEntityTicker<A>) ticker : null;
    }
}
