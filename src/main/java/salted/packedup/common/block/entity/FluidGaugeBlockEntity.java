package salted.packedup.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import salted.packedup.common.block.FluidGaugeBlock;
import salted.packedup.common.block.handlers.utils.FluidUtils;

public class FluidGaugeBlockEntity extends BlockEntity {
    private static final int POLL_INTERVAL = 20;
    private static final int TICKS_PER_STEP = 2;

    private int target = FluidGaugeBlock.MIN_READING;
    private int ticks;

    private FluidStack contents = FluidStack.EMPTY;
    private int capacity;

    public FluidGaugeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public FluidStack getContents() {
        return contents;
    }

    public int getCapacity() {
        return capacity;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FluidGaugeBlockEntity gauge) {
        gauge.ticks++;

        if (gauge.ticks % POLL_INTERVAL == 0) gauge.poll(level, state, pos);
        if (gauge.ticks % TICKS_PER_STEP != 0) return;

        int current = state.getValue(FluidGaugeBlock.FILL);
        if (current == gauge.target) return;

        // a lost reading snaps to the fallback; everything else sweeps one step
        int next = gauge.target == FluidGaugeBlock.NO_READING
                ? FluidGaugeBlock.NO_READING
                : current + Integer.signum(gauge.target - current);

        level.setBlock(pos, state.setValue(FluidGaugeBlock.FILL, next), Block.UPDATE_CLIENTS);
    }

    private void poll(Level level, BlockState state, BlockPos pos) {
        BlockPos containerPos = FluidGaugeBlock.containerPos(state, pos);
        Direction side = FluidGaugeBlock.connectedDirection(state);

        IFluidHandler container = FluidUtils.handlerAt(level, containerPos, side);
        if (container == null) {
            snapshot(FluidStack.EMPTY, 0);
            target = FluidGaugeBlock.NO_READING;
            return;
        }

        float fill = FluidUtils.fillPercent(container);
        if (fill == FluidUtils.UNREADABLE) {
            snapshot(FluidStack.EMPTY, 0);
            target = FluidGaugeBlock.NO_READING;
            return;
        }

        snapshot(FluidUtils.tankContents(container), FluidUtils.totalCapacity(container));

        int span = FluidGaugeBlock.MAX_READING - FluidGaugeBlock.MIN_READING;
        target = FluidGaugeBlock.MIN_READING + Math.round(fill * span);
    }

    private void snapshot(FluidStack fluid, int tankCapacity) {
        boolean same = capacity == tankCapacity
                && contents.getAmount() == fluid.getAmount()
                && contents.isFluidEqual(fluid);
        if (same) return;

        contents = fluid.copy();
        capacity = tankCapacity;

        setChanged();
        if (level != null) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("Target", target);
        tag.putInt("Capacity", capacity);
        tag.put("Contents", contents.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        target = tag.getInt("Target");
        capacity = tag.getInt("Capacity");
        contents = FluidStack.loadFluidStackFromNBT(tag.getCompound("Contents"));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}