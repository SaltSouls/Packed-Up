package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class HorizontalSlabBlock extends SlabBlock {
    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public HorizontalSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, SlabType.BOTTOM).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        BlockState state = ctx.getLevel().getBlockState(pos);
        Direction facing = ctx.getHorizontalDirection().getOpposite();

        if (state.is(this)) {
            return state.setValue(TYPE, SlabType.DOUBLE).setValue(FACING, facing).setValue(WATERLOGGED, false);
        } else {
            FluidState fluid = ctx.getLevel().getFluidState(pos);
            Boolean flag = fluid.getType() == Fluids.WATER;
            BlockState state1 = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(FACING, facing).setValue(WATERLOGGED, flag);
            Direction dir = ctx.getClickedFace();

            return dir != Direction.DOWN && (dir == Direction.UP || !(ctx.getClickLocation().y - pos.getY() > 0.5D)) ? state1 : state1.setValue(TYPE, SlabType.TOP);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FACING, WATERLOGGED);
    }
}
