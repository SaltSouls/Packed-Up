package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import salted.packedup.common.block.handlers.ConnectionHandler;
import salted.packedup.common.block.state.PUProperties;
import salted.packedup.common.block.state.properties.PillarShape;

public class ConnectedPillarBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<PillarShape> SHAPE = PUProperties.PILLAR_SHAPE;

    public ConnectedPillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SHAPE, PillarShape.SINGLE));
    }

    @NotNull
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return Shapes.block();
    }

    @NotNull
    @Override
    public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return getShape(state, world, pos, ctx);
    }

    @NotNull
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Direction.Axis axis = context.getClickedFace().getAxis();
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        BlockState state = this.defaultBlockState().setValue(AXIS, axis);

        return getPillarShape(world, pos, state);
    }

    @NotNull
    @Override
    public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        return getPillarShape(world, pos, state);
    }

    @NotNull
    public BlockState getPillarShape(LevelAccessor world, BlockPos pos, BlockState state) {
        boolean connectAll = false;
        ConnectionHandler handler = new ConnectionHandler();
        ConnectionHandler.Part part;
        if (connectAll) { part = handler.getAlongAxis(world, pos, state, this.getClass()); }
        else { part = handler.getAlongAxis(world, pos, state); }

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
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SHAPE);
    }
}
