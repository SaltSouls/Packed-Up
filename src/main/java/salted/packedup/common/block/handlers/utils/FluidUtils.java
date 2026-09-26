package salted.packedup.common.block.handlers.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import salted.packedup.common.tag.PUTags;

import java.util.IdentityHashMap;
import java.util.Map;

public class FluidUtils {
    public static final float UNREADABLE = -1.0F;
    public static final int BOTTLE_VOLUME = FluidType.BUCKET_VOLUME / 4;

    /**
     * Maps fluids to the item that carries them, one map per {@link Container}.
     * Filled by {@link #buildContainerMaps()}, since a container's fluid often doesn't point back at it.
     */
    private static final Map<Container, Map<Fluid, Item>> CONTAINER_MAP = Map.of(
            Container.BUCKET, new IdentityHashMap<>(),
            Container.BOTTLE, new IdentityHashMap<>());

    /**
     * Builds the fluid to container maps by scanning the item registry. Buckets are matched by class,
     * bottles by what their {@link IFluidHandlerItem} reports; vanilla's water bottle is a potion
     * rather than a fluid container, so it's added by hand.
     * <p>
     * Must run after the registries freeze, or other mods' items are invisible to it.
     */
    public static void buildContainerMaps() {
        CONTAINER_MAP.values().forEach(Map::clear);

        for (Item item : ForgeRegistries.ITEMS) {
            for (Container container : Container.values()) {
                FluidStack held = container.contentsOf(new ItemStack(item));
                if (held.isEmpty()) continue;

                CONTAINER_MAP.get(container).putIfAbsent(held.getFluid(), item);
                break;
            }
        }

        CONTAINER_MAP.get(Container.BOTTLE).putIfAbsent(Fluids.WATER, Items.POTION);
    }

    // Reads an item's own fluid handler, which most items don't have.
    private static FluidStack handlerContents(ItemStack stack) {
        IFluidHandlerItem handler = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (handler == null || handler.getTanks() < 1) return FluidStack.EMPTY;

        return handler.getFluidInTank(0);
    }

    /**
     * @param fluid The {@link Fluid} to look up.
     * @return      The filled bucket for this fluid, whether the fluid declares one or not, or null
     *              if nothing in the game buckets it.
     */
    @Nullable
    public static Item bucketFor(Fluid fluid) {
        Item declared = fluid.getBucket();
        if (declared != Items.AIR) return declared;

        return CONTAINER_MAP.get(Container.BUCKET).get(fluid);
    }

    /**
     * @param fluid The {@link Fluid} to look up.
     * @return      The filled bottle for this fluid, or null if nothing in the game bottles it.
     */
    @Nullable
    public static Item bottleFor(Fluid fluid) {
        return CONTAINER_MAP.get(Container.BOTTLE).get(fluid);
    }

    /**
     * Tank validator for bucket-based blocks. Passed to a {@link net.minecraftforge.fluids.capability.templates.FluidTank},
     * it refuses anything that can't be carried in a bucket, from pipes as well as by hand.
     *
     * @param stack The {@link FluidStack} offered to the tank.
     * @return      True if a bucket exists for this fluid.
     */
    public static boolean isBucketable(FluidStack stack) {
        return !stack.isEmpty() && bucketFor(stack.getFluid()) != null;
    }

    /**
     * Tank validator for bottle-based blocks.
     *
     * @param stack The {@link FluidStack} offered to the tank.
     * @return      True if a bottle exists for this fluid.
     */
    public static boolean isBottleable(FluidStack stack) {
        return !stack.isEmpty() && bottleFor(stack.getFluid()) != null;
    }

    /**
     * @param tank Any {@link IFluidHandler}.
     * @return     What its first tank holds, or {@link FluidStack#EMPTY} if it has none.
     */
    public static FluidStack tankContents(IFluidHandler tank) {
        return tank.getTanks() > 0 ? tank.getFluidInTank(0) : FluidStack.EMPTY;
    }

    /**
     * @param level The {@link net.minecraft.world.level.Level} or other {@link BlockGetter} to look in.
     * @param pos   The {@link BlockPos} of the block to read.
     * @param side  The face being read from, as a sided handler may answer differently per face.
     * @return      That block's {@link IFluidHandler}, or null if it has none.
     */
    @Nullable
    public static IFluidHandler handlerAt(BlockGetter level, BlockPos pos, @Nullable Direction side) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return null;

        return blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER, side).orElse(null);
    }

    /**
     * Sums every tank, so a block with separate input and output buffers reads as one container.
     *
     * @param tank Any {@link IFluidHandler}.
     * @return     How full it is, from 0 to 1, or {@link #UNREADABLE} when it reports no capacity.
     */
    public static float fillPercent(IFluidHandler tank) {
        long amount = 0;
        long capacity = 0;

        for (int i = 0; i < tank.getTanks(); i++) {
            amount += tank.getFluidInTank(i).getAmount();
            capacity += tank.getTankCapacity(i);
        }
        if (capacity <= 0) return UNREADABLE;

        return Mth.clamp(amount / (float) capacity, 0.0F, 1.0F);
    }

    /**
     * @param tank Any {@link IFluidHandler}.
     * @return     Its capacity across every tank, in millibuckets.
     */
    public static int totalCapacity(IFluidHandler tank) {
        long capacity = 0;

        for (int i = 0; i < tank.getTanks(); i++) {
            capacity += tank.getTankCapacity(i);
        }
        return (int) Math.min(capacity, Integer.MAX_VALUE);
    }

    /**
     * @param stack The {@link FluidStack} to check.
     * @return      True if this fluid should make its container look hot. Tag driven, so packs can
     *              add modded molten metals without code.
     */
    public static boolean isMolten(FluidStack stack) {
        return !stack.isEmpty() && stack.getFluid().is(PUTags.MOLTEN);
    }

    /**
     * @param fluid The {@link Fluid} to check.
     * @return      True if the drums can drip this fluid. A fluid drips only if it's tagged
     *              <i>and</i> the cauldron below accepts it, so the tags can narrow what vanilla
     *              allows but can't make a cauldron take something new.
     */
    public static boolean canDrip(Fluid fluid) {
        return fluid.is(PUTags.DRUM_DRIPPING_FAST) || fluid.is(PUTags.DRUM_DRIPPING_SLOW);
    }

    /** @return True if this fluid drips on the slow schedule. Anything untagged is fast. */
    public static boolean dripsSlowly(Fluid fluid) {
        return fluid.is(PUTags.DRUM_DRIPPING_SLOW);
    }

    /**
     * The container kinds the fluid blocks accept, each knowing how much it moves, what it looks like
     * empty, and how to read and produce its items. The interactions themselves live in
     * {@link salted.packedup.common.block.handlers.FluidHandler}.
     */
    public enum Container {
        BUCKET(FluidType.BUCKET_VOLUME, Items.BUCKET),
        BOTTLE(BOTTLE_VOLUME, Items.GLASS_BOTTLE);

        private final int volume;
        private final Item emptyItem;

        /**
         * @param volume    Millibuckets moved per interaction.
         * @param emptyItem What an emptied container becomes, and what's accepted when drawing fluid out.
         */
        Container(int volume, Item emptyItem) {
            this.volume = volume;
            this.emptyItem = emptyItem;
        }

        public int getVolume() {
            return this.volume;
        }
        public Item getEmptyItem() {
            return this.emptyItem;
        }

        /**
         * Mirrors Forge's {@code FluidBucketWrapper}, but works on containers that expose no capability.
         *
         * @param stack The held {@link ItemStack}.
         * @return      A container's worth of its fluid, or {@link FluidStack#EMPTY} if the item isn't
         *              one of this kind. Milk only reports when something has called
         *              {@link ForgeMod#enableMilkFluid()}.
         */
        public FluidStack contentsOf(ItemStack stack) {
            return switch (this) {
                case BUCKET -> bucketFluid(stack);
                case BOTTLE -> bottleFluid(stack);
            };
        }

        /**
         * @param fluid The {@link Fluid} to carry.
         * @return      The filled container as an item the player can be handed, or
         *              {@link ItemStack#EMPTY} if this kind can't carry the fluid.
         */
        public ItemStack filledFor(Fluid fluid) {
            Item filled = this == BUCKET ? bucketFor(fluid) : bottleFor(fluid);
            if (filled == null) return ItemStack.EMPTY;

            // vanilla's water bottle is a potion, so a bare item stack isn't enough
            return filled == Items.POTION
                    ? PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)
                    : new ItemStack(filled);
        }

        private static FluidStack bucketFluid(ItemStack stack) {
            Item item = stack.getItem();

            if (item instanceof BucketItem bucket && bucket.getFluid() != Fluids.EMPTY) {
                return new FluidStack(bucket.getFluid(), FluidType.BUCKET_VOLUME);
            }
            if (item instanceof MilkBucketItem && ForgeMod.MILK.isPresent()) {
                return new FluidStack(ForgeMod.MILK.get(), FluidType.BUCKET_VOLUME);
            }
            return FluidStack.EMPTY;
        }

        private static FluidStack bottleFluid(ItemStack stack) {
            if (stack.is(Items.POTION) && PotionUtils.getPotion(stack) == Potions.WATER) {
                return new FluidStack(Fluids.WATER, BOTTLE_VOLUME);
            }

            FluidStack held = handlerContents(stack);
            if (held.isEmpty() || held.getAmount() > BOTTLE_VOLUME) return FluidStack.EMPTY;

            return new FluidStack(held, BOTTLE_VOLUME);
        }
    }

}
