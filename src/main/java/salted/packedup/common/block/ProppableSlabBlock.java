package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import salted.packedup.common.block.state.PUProperties;

public class ProppableSlabBlock extends HorizontalSlabBlock {
    public static BooleanProperty PROPPED = PUProperties.PROPPED;

    public ProppableSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, SlabType.BOTTOM).setValue(FACING, Direction.NORTH).setValue(PROPPED, false).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        BlockState state = ctx.getLevel().getBlockState(pos);
        Direction facing = ctx.getHorizontalDirection().getOpposite();

        if (state.is(this)) {
            Direction currentFacing = state.getValue(FACING);
            // prevent double slab creating if propped up
            if (!state.getValue(PROPPED)) return state.setValue(TYPE, SlabType.DOUBLE).setValue(FACING, currentFacing).setValue(WATERLOGGED, false);
        } else {
            FluidState fluid = ctx.getLevel().getFluidState(pos);
            Boolean flag = fluid.getType() == Fluids.WATER;
            BlockState state1 = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(FACING, facing).setValue(WATERLOGGED, flag);
            Direction dir = ctx.getClickedFace();

            return dir != Direction.DOWN && (dir == Direction.UP || !(ctx.getClickLocation().y - pos.getY() > 0.5D)) ? state1 : state1.setValue(TYPE, SlabType.TOP);
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FACING, PROPPED, WATERLOGGED);
    }

}
