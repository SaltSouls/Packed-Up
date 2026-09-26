package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import salted.packedup.common.block.entity.DrumBarrelBlockEntity;
import salted.packedup.common.block.handlers.FluidHandler;
import salted.packedup.common.block.handlers.utils.FluidUtils;
import salted.packedup.common.block.handlers.utils.FluidUtils.Container;
import salted.packedup.common.mixins.CauldronInvoker;
import salted.packedup.common.registry.PURegistry;
import salted.packedup.common.registry.PUSounds;
import salted.packedup.common.tag.PUTags;

import static salted.packedup.common.block.entity.DrumBarrelBlockEntity.MAX_DRIP_RATE;
import static salted.packedup.common.block.entity.DrumBarrelBlockEntity.MIN_DRIP_RATE;

public class DrumBarrelBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    private static final float MIN_PARTICLE_CHANCE = 0.06F;
    private static final float MAX_PARTICLE_CHANCE = 1.0F;
    private static final float MIN_FALLING_SHARE = 0.1F;
    private static final float MAX_FALLING_SHARE = 0.7F;
    private static final int MAX_DRIP_DISTANCE = 11;

    public DrumBarrelBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return PURegistry.DRUM_BARREL_ENTITY.create(pos, state);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof DrumBarrelBlockEntity drum)) return InteractionResult.PASS;

        InteractionResult fluid = FluidHandler.interact(level, pos, player, hand, hit.getDirection(), drum.getTank(), Container.BUCKET);
        if (fluid != InteractionResult.PASS) return fluid;

        return knock(level, pos, player, hand, drum);
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState state, Level level, @NotNull BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof DrumBarrelBlockEntity drum)) return 0;
        FluidTank tank = drum.getTank();
        if (tank.isEmpty()) return 0;

        return Mth.floor(tank.getFluidAmount() / (float) DrumBarrelBlockEntity.CAPACITY * 14.0F) + 1;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return createTickerHelper(type, PURegistry.DRUM_BARREL_ENTITY.get(), DrumBarrelBlockEntity::serverTick);
    }

    public void tryDrip(BlockState state, ServerLevel level, BlockPos pos) {
        FluidStack fluid = drippingFluid(state, level, pos);
        if (fluid.isEmpty()) return;

        BlockPos cauldronPos = findCauldron(level, pos);
        if (cauldronPos == null) return;

        BlockState cauldron = level.getBlockState(cauldronPos);
        if (!(cauldron.getBlock() instanceof AbstractCauldronBlock block)) return;

        CauldronInvoker invoker = (CauldronInvoker) block;
        if (!invoker.packedup$canReceiveStalactiteDrip(fluid.getFluid())) return;

        invoker.packedup$receiveStalactiteDrip(cauldron, level, cauldronPos, fluid.getFluid());
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        FluidStack fluid = drippingFluid(state, level, pos);
        if (fluid.isEmpty()) return;
        if (!(level.getBlockEntity(pos) instanceof DrumBarrelBlockEntity drum)) return;

        // one drop a tick at most, so a busy drum spreads its stream over ticks instead of clumping
        float fill = fillScale(drum.dripRate());
        if (random.nextFloat() > Mth.lerp(fill, MIN_PARTICLE_CHANCE, MAX_PARTICLE_CHANCE)) return;

        // just under the hopper's spout
        double x = pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
        double y = pos.getY() - 1.085D;
        double z = pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;

        level.addParticle(dripParticle(fluid, random, fill), x, y, z, 0.0D, 0.0D, 0.0D);
    }

    private float fillScale(float rate) {
        return Mth.clamp((rate - MIN_DRIP_RATE) / (MAX_DRIP_RATE - MIN_DRIP_RATE), 0.0F, 1.0F);
    }

    private FluidStack drippingFluid(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.getValue(FACING) != Direction.DOWN) return FluidStack.EMPTY;

        BlockState hopper = level.getBlockState(pos.below());
        if (!(hopper.getBlock() instanceof HopperBlock)) return FluidStack.EMPTY;
        if (hopper.getValue(HopperBlock.FACING) != Direction.DOWN) return FluidStack.EMPTY;

        if (!(level.getBlockEntity(pos) instanceof DrumBarrelBlockEntity drum)) return FluidStack.EMPTY;

        FluidStack fluid = drum.getTank().getFluid();
        return FluidUtils.canDrip(fluid.getFluid()) ? fluid : FluidStack.EMPTY;
    }

    @Nullable
    private BlockPos findCauldron(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos check = pos.below().mutable();

        // trys to find unobstructed cauldron beneath the hopper
        for (int i = 0; i < MAX_DRIP_DISTANCE; i++) {
            check.move(Direction.DOWN);
            BlockState state = level.getBlockState(check);

            if (state.getBlock() instanceof AbstractCauldronBlock) return check.immutable();
            if (!state.isAir()) return null;
        }
        return null;
    }

    private ParticleOptions dripParticle(FluidStack fluid, RandomSource random, float fill) {
        float falling_share = Mth.lerp(fill, MIN_FALLING_SHARE, MAX_FALLING_SHARE);
        boolean falling = random.nextFloat() < falling_share;

        if (fluid.getFluid().is(FluidTags.WATER)) { return falling ? ParticleTypes.FALLING_WATER : ParticleTypes.DRIPPING_WATER; }
        if (fluid.getFluid().is(FluidTags.LAVA)) { return falling ? ParticleTypes.FALLING_LAVA : ParticleTypes.DRIPPING_LAVA; }

        // use generic particle for other fluids
        int tint = IClientFluidTypeExtensions.of(fluid.getFluid()).getTintColor(fluid);
        return new DustParticleOptions(Vec3.fromRGB24(tint).toVector3f(), 1.0F);
    }

    private InteractionResult knock(Level level, BlockPos pos, Player player, InteractionHand hand, DrumBarrelBlockEntity drum) {
        ItemStack held = player.getItemInHand(hand);
        ItemStack other = player.getItemInHand(hand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND);

        boolean striker = held.is(PUTags.DRUM_STRIKERS);
        if (!striker && !(held.isEmpty() && other.isEmpty())) return InteractionResult.PASS;

        if (!level.isClientSide) playKnock(level, pos, drum.getTank().getFluid(), drum.getFillPercent(), !striker);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void playKnock(Level level, BlockPos pos, FluidStack fluid, float fill, boolean bareHand) {
        float pitch = 1.25F - (fill * 0.5F) - (bareHand ? 0.1F : 0.0F);
        float master = bareHand ? 0.4F : 0.8F;
        float dampingCurve = 0.35F;

        float damped = (float) Math.pow(fill, dampingCurve);
        float angle = damped * Mth.HALF_PI;

        float emptyGain = master * Mth.cos(angle);
        float fullGain = master * Mth.sin(angle);

        if (emptyGain > 0.01F) { level.playSound(null, pos, PUSounds.DRUM_KNOCK_EMPTY.get(), SoundSource.BLOCKS, emptyGain, pitch); }
        if (fullGain > 0.01F) { level.playSound(null, pos, PUSounds.DRUM_KNOCK_FULL.get(), SoundSource.BLOCKS, fullGain, pitch); }

        playResonance(level, pos, fluid, fill, master, pitch);
    }

    private void playResonance(Level level, BlockPos pos, FluidStack fluid, float fill, float master, float pitch) {
        if (fluid.isEmpty()) return;
        float sloshCurve = 0.6F;
        float resonanceShare = 0.5F;
        float accentShare = 0.3F;

        float volume = master * resonanceShare * (float) Math.pow(fill, sloshCurve);
        if (volume < 0.01F) return;

        boolean molten = FluidUtils.isMolten(fluid);
        SoundEvent body = molten ? PUSounds.DRUM_WARBLE.get() : PUSounds.DRUM_SLOSH.get();
        SoundEvent accent = molten ? PUSounds.DRUM_WARBLE_ACCENT.get() : PUSounds.DRUM_SLOSH_ACCENT.get();

        level.playSound(null, pos, body, SoundSource.BLOCKS, volume, pitch);
        level.playSound(null, pos, accent, SoundSource.BLOCKS, volume * accentShare, pitch * 0.9F);
    }
}