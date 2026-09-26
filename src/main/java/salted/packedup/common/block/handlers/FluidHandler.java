package salted.packedup.common.block.handlers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import salted.packedup.common.block.handlers.utils.FluidUtils;
import salted.packedup.common.block.handlers.utils.FluidUtils.Container;

public class FluidHandler {

    /**
     * The whole right-click path for a fluid block: Forge's own handling first, then a manual
     * transfer for containers it can't see, and a sound for fluids that declare none. Blocks holding
     * fluid only need this one call.
     *
     * @param side      The face clicked, from {@link BlockHitResult#getDirection()}.
     * @param tank      The block's {@link IFluidHandler}, any implementation.
     * @param container The kind of container this block accepts.
     * @return          SUCCESS when fluid moved, otherwise PASS, leaving the caller to handle the click.
     */
    public static InteractionResult interact(Level level, BlockPos pos, Player player, InteractionHand hand, @Nullable Direction side, IFluidHandler tank, Container container) {
        ItemStack held = player.getItemInHand(hand);
        FluidStack before = FluidUtil.getFluidContained(held).orElse(FluidStack.EMPTY);

        if (FluidUtil.interactWithFluidHandler(player, hand, level, pos, side)) {
            if (!level.isClientSide) coverSilentTransfer(level, pos, tank, container, before);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return transfer(level, pos, player, hand, held, tank, container);
    }

    /**
     * Manual transfer for containers that {@link FluidUtil#interactWithFluidHandler} can't see,
     * because they expose no {@link net.minecraftforge.fluids.capability.IFluidHandlerItem}. Called
     * by {@link #interact}, or on its own by a block that wants to handle Forge's path itself.
     * <p>
     * Transfers are all or nothing; a partial fill would leave a half empty container.
     *
     * @param held The held {@link ItemStack}, swapped for the emptied or filled result.
     * @return     SUCCESS when a container's worth moved, otherwise PASS.
     */
    public static InteractionResult transfer(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack held, IFluidHandler tank, Container container) {
        FluidStack contents = container.contentsOf(held);

        if (!contents.isEmpty()) return fillTank(level, pos, player, hand, held, tank, container, contents);
        if (held.is(container.getEmptyItem())) return drainTank(level, pos, player, hand, held, tank, container);

        return InteractionResult.PASS;
    }

    // pouring a full container into the block
    private static InteractionResult fillTank(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack held, IFluidHandler tank, Container container, FluidStack contents) {
        int volume = container.getVolume();
        if (tank.fill(contents, IFluidHandler.FluidAction.SIMULATE) != volume) return InteractionResult.PASS;

        if (!level.isClientSide) {
            tank.fill(contents, IFluidHandler.FluidAction.EXECUTE);
            giveResult(player, hand, held, new ItemStack(container.getEmptyItem()));
            playFluidSound(level, pos, contents, container, false);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    // drawing fluid out into an empty container
    private static InteractionResult drainTank(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack held, IFluidHandler tank, Container container) {
        int volume = container.getVolume();
        FluidStack drained = tank.drain(volume, IFluidHandler.FluidAction.SIMULATE);
        if (drained.getAmount() != volume) return InteractionResult.PASS;

        ItemStack result = container.filledFor(drained.getFluid());
        if (result.isEmpty()) return InteractionResult.PASS;

        if (!level.isClientSide) {
            tank.drain(volume, IFluidHandler.FluidAction.EXECUTE);
            giveResult(player, hand, held, result);
            playFluidSound(level, pos, drained, container, true);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    // Swaps one container for what it became, leaving the rest of the stack alone.
    private static void giveResult(Player player, InteractionHand hand, ItemStack held, ItemStack result) {
        if (player.isCreative()) return;
        held.shrink(1);

        if (held.isEmpty()) player.setItemInHand(hand, result);
        else if (!player.getInventory().add(result)) player.drop(result, false);
    }

    /**
     * Plays a transfer sound from the block, falling back to vanilla's when the fluid declares none.
     * Bottles always use vanilla's, since Forge has no bottle {@link SoundAction}.
     *
     * @param fluid     The {@link FluidStack} that moved.
     * @param container The kind of container it moved in.
     * @param filling   True when fluid went into the container, false when it went into the block.
     */
    public static void playFluidSound(Level level, BlockPos pos, FluidStack fluid, Container container, boolean filling) {
        if (container == Container.BOTTLE) {
            playSound(level, pos, filling ? SoundEvents.BOTTLE_FILL : SoundEvents.BOTTLE_EMPTY);
            return;
        }

        SoundAction action = filling ? SoundActions.BUCKET_FILL : SoundActions.BUCKET_EMPTY;
        SoundEvent sound = fluid.getFluid().getFluidType().getSound(fluid, action);
        if (sound == null) { sound = filling ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_EMPTY; }

        playSound(level, pos, sound);
    }

    // Forge plays nothing when a fluid declares no sound, so fill in for it after the fact.
    private static void coverSilentTransfer(Level level, BlockPos pos, IFluidHandler tank, Container container, FluidStack before) {
        boolean filling = before.isEmpty();
        FluidStack moved = filling ? FluidUtils.tankContents(tank) : before;

        if (moved.isEmpty() || declaresSound(moved, filling)) return;
        playFluidSound(level, pos, moved, container, filling);
    }

    private static boolean declaresSound(FluidStack fluid, boolean filling) {
        SoundAction action = filling ? SoundActions.BUCKET_FILL : SoundActions.BUCKET_EMPTY;
        return fluid.getFluid().getFluidType().getSound(fluid, action) != null;
    }

    private static void playSound(Level level, BlockPos pos, SoundEvent sound) {
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

}
