package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class HorizontalQuarterSlabBlock extends QuarterSlabBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public HorizontalQuarterSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, FACING, WATERLOGGED);
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
            Direction direction = blockstate.getValue(FACING);
            if (layer == 3) {
                return blockstate.setValue(LAYERS, 4)
                        .setValue(FACING, direction)
                        .setValue(WATERLOGGED, false);
            } else {
                return blockstate.setValue(LAYERS, layer + 1)
                        .setValue(FACING, direction)
                        .setValue(WATERLOGGED, flag);
            }
        } else {
            return this.defaultBlockState().setValue(LAYERS, 1)
                    .setValue(FACING, ctx.getHorizontalDirection().getOpposite())
                    .setValue(WATERLOGGED, flag);
        }
    }

}
