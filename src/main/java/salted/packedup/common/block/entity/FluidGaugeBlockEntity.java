package salted.packedup.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import salted.packedup.common.block.FluidGaugeBlock;
import salted.packedup.common.registry.PURegistry;

public class FluidGaugeBlockEntity extends BlockEntity {
    private static final int POLL_INTERVAL = 20;
    private static final int TICKS_PER_STEP = 2;

    private int target = FluidGaugeBlock.MIN_READING;
    private int ticks;

    public FluidGaugeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FluidGaugeBlockEntity gauge) {
        gauge.ticks++;

        if (gauge.ticks % POLL_INTERVAL == 0) {
            gauge.target = gauge.readContainer(level, state, pos);
        }

        int current = state.getValue(FluidGaugeBlock.FILL);
        if (current == gauge.target) return;
        if (gauge.ticks % TICKS_PER_STEP != 0) return;

        // a lost reading snaps to the fallback; everything else sweeps one step
        int next = gauge.target == FluidGaugeBlock.NO_READING
                ? FluidGaugeBlock.NO_READING
                : current + Integer.signum(gauge.target - current);

        level.setBlock(pos, state.setValue(FluidGaugeBlock.FILL, next), Block.UPDATE_CLIENTS);
    }

    private int readContainer(Level level, BlockState state, BlockPos pos) {
        BlockPos containerPos = FluidGaugeBlock.containerPos(state, pos);
        BlockEntity container = level.getBlockEntity(containerPos);
        if (container == null) return FluidGaugeBlock.NO_READING;

        IFluidHandler handler = container
                .getCapability(ForgeCapabilities.FLUID_HANDLER, FluidGaugeBlock.connectedDirection(state))
                .orElse(null);
        if (handler == null) return FluidGaugeBlock.NO_READING;

        // sum across tanks, so multi-tank blocks read as one container
        long amount = 0;
        long capacity = 0;
        for (int tank = 0; tank < handler.getTanks(); tank++) {
            amount += handler.getFluidInTank(tank).getAmount();
            capacity += handler.getTankCapacity(tank);
        }
        if (capacity <= 0) return FluidGaugeBlock.NO_READING;

        float fill = Mth.clamp(amount / (float) capacity, 0.0F, 1.0F);
        int span = FluidGaugeBlock.MAX_READING - FluidGaugeBlock.MIN_READING;

        return FluidGaugeBlock.MIN_READING + Math.round(fill * span);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Target", target);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        target = tag.getInt("Target");
    }
}
