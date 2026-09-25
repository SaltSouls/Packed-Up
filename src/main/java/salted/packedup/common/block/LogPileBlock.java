package salted.packedup.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import salted.packedup.common.block.state.PUProperties;

public class LogPileBlock extends Block {
    public static final Property<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final IntegerProperty ROPE_DISTANCE = PUProperties.ROPE_DISTANCE;

    public LogPileBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X).setValue(ROPE_DISTANCE, 0));
    }

//    @Override
//    public RenderShape getRenderShape(BlockState state) {
//        return RenderShape.MODEL;
//    }
//
//    @Override
//    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
//        return Shapes.block();
//    }
//
//    @Override
//    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
//        return getShape(state, world, pos, ctx);
//    }
//
//    @Override
//    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
//
//    }
//
//    @Override
//    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
//
//
//    }
//
//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
//        super.createBlockStateDefinition(builder);
//        builder.add(AXIS, ROPE_DISTANCE);
//    }
}
