package salted.packedup.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import salted.packedup.common.block.DrumBarrelBlock;
import salted.packedup.common.block.handlers.utils.FluidUtils;
import salted.packedup.common.tag.PUTags;

public class DrumBarrelBlockEntity extends BlockEntity {
    public static final int CAPACITY = FluidType.BUCKET_VOLUME * 9;
    private static int timer(int min, int sec) { return (min * (20 * 60)) + (20 * sec); }
    private static final int FAST_MIN = timer(3, 15);
    private static final int FAST_MAX = timer(5, 25);
    private static final int SLOW_MIN = timer(10, 0);
    private static final int SLOW_MAX = timer(16, 0);
    private static final float RATE_PER_BUCKET = 0.625F;
    public static final float MIN_DRIP_RATE = 1.0F;
    public static final float MAX_DRIP_RATE = MIN_DRIP_RATE + (RATE_PER_BUCKET * 8.0F);
    private float dripProgress;
    private int dripGoal = -1;

    private final FluidTank fluidTank = new FluidTank(CAPACITY, FluidUtils::isBucketable) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level == null || level.isClientSide) return;
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    };

    private final LazyOptional<IFluidHandler> fluidCap = LazyOptional.of(() -> fluidTank);

    public DrumBarrelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public FluidTank getTank() {
        return fluidTank;
    }
    public float getFillPercent() {
        return Mth.clamp(fluidTank.getFluidAmount() / (float) CAPACITY, 0.0F, 1.0F);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) return fluidCap.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fluidCap.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Tank", fluidTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        fluidTank.readFromNBT(tag.getCompound("Tank"));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DrumBarrelBlockEntity drum) {
        // first tick after loading: roll an interval rather than dripping straight away
        if (drum.dripGoal < 0) {
            drum.dripGoal = drum.rollInterval(level.random);
            return;
        }

        drum.dripProgress += drum.dripRate();
        if (drum.dripProgress < drum.dripGoal) return;

        drum.dripProgress = 0.0F;
        drum.dripGoal = drum.rollInterval(level.random);

        if (state.getBlock() instanceof DrumBarrelBlock barrel) {
            barrel.tryDrip(state, (ServerLevel) level, pos);
        }
    }

    private int rollInterval(RandomSource random) {
        boolean slow = FluidUtils.dripsSlowly(fluidTank.getFluid().getFluid());
        return slow ? Mth.nextInt(random, SLOW_MIN, SLOW_MAX) : Mth.nextInt(random, FAST_MIN, FAST_MAX);
    }

    public float dripRate() {
        float buckets = getFillPercent() * (CAPACITY / (float) FluidType.BUCKET_VOLUME);
        return MIN_DRIP_RATE + RATE_PER_BUCKET * Math.max(0.0F, buckets - 1.0F);
    }

}
