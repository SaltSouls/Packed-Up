package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import salted.packedup.common.block.entity.DrumBarrelBlockEntity;
import salted.packedup.common.registry.PURegistry;
import salted.packedup.common.registry.PUSounds;
import salted.packedup.common.tag.PUTags;

import static salted.packedup.common.block.handlers.utils.FluidUtils.playFluidSound;
import static salted.packedup.common.block.handlers.utils.FluidUtils.useBucket;

public class DrumBarrelBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public DrumBarrelBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    /** Lies down along the direction the player is looking, like a vanilla barrel. */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return PURegistry.DRUM_BARREL_ENTITY.create(pos, state);
    }

    private FluidStack peekTank(Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof DrumBarrelBlockEntity drum
                ? drum.getTank().getFluid()
                : FluidStack.EMPTY;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);
        FluidStack before = FluidUtil.getFluidContained(held).orElse(FluidStack.EMPTY);

        if (FluidUtil.interactWithFluidHandler(player, hand, level, pos, hit.getDirection())) {
            if (!level.isClientSide) {
                boolean emptying = !before.isEmpty();
                FluidStack moved = emptying ? before : peekTank(level, pos);
                SoundAction action = emptying ? SoundActions.BUCKET_EMPTY : SoundActions.BUCKET_FILL;
                boolean isFilling = action == SoundActions.BUCKET_FILL;

                if (!moved.isEmpty() && moved.getFluid().getFluidType().getSound(moved, action) == null) {
                    playFluidSound(level, pos, moved, true, isFilling);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (level.getBlockEntity(pos) instanceof DrumBarrelBlockEntity drum) {
            InteractionResult bucket = useBucket(level, pos, player, hand, held, drum.getTank());
            if (bucket != InteractionResult.PASS) return bucket;
        }

        ItemStack other = player.getItemInHand(hand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND);

        if (held.is(PUTags.DRUM_STRIKERS)) {
            knock(level, pos, false);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (held.isEmpty() && other.isEmpty()) {
            knock(level, pos, true);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }


    private void knock(Level level, BlockPos pos, boolean bareHand) {
        if (level.isClientSide) return;

        float fill = 0.0F;
        if (level.getBlockEntity(pos) instanceof DrumBarrelBlockEntity drum) {
            fill = Mth.clamp(drum.getTank().getFluidAmount() / (float) DrumBarrelBlockEntity.CAPACITY, 0.0F, 1.0F);
        }

        float pitch = 1.25F - (fill * 0.5F);
        float master = bareHand ? 0.4F : 0.8F;
        if (bareHand) pitch -= 0.1F;

        float angle = fill * (float) (Math.PI / 2.0);
        float emptyGain = Mth.cos(angle);
        float fullGain = Mth.sin(angle);

        if (emptyGain > 0.01F) {
            level.playSound(null, pos, PUSounds.DRUM_KNOCK_EMPTY.get(), SoundSource.BLOCKS, master * emptyGain, pitch);
        }
        if (fullGain > 0.01F) {
            level.playSound(null, pos, PUSounds.DRUM_KNOCK_FULL.get(), SoundSource.BLOCKS, master * fullGain, pitch);
        }
    }
}
