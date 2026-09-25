package salted.packedup.common.block.handlers.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Container lookups and interactions shared by the fluid-holding blocks.
 * <p>
 * Asking a fluid for its container misses mods that register the item without wiring
 * it back to the fluid, so these also keep reverse maps built from the item registry.
 */
public class FluidUtils {
    /** Vanilla's de facto bottle size: a quarter of a bucket. */
    public static final int BOTTLE_VOLUME = FluidType.BUCKET_VOLUME / 4;

    private static final Map<Fluid, Item> BUCKETS = new IdentityHashMap<>();
    private static final Map<Fluid, Item> BOTTLES = new IdentityHashMap<>();

    // ============================================================
    // Registry scan
    // ============================================================
    /** Must run after registries freeze; see {@code CommonSetup}. */
    public static void buildContainerMaps() {
        BUCKETS.clear();
        BOTTLES.clear();

        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof BucketItem bucket && bucket.getFluid() != Fluids.EMPTY) {
                BUCKETS.putIfAbsent(bucket.getFluid(), item);
                continue;
            }

            // bottles are rarely a shared class, so go by what the item's handler reports
            FluidStack held = handlerContents(new ItemStack(item));
            if (held.isEmpty() || held.getAmount() > BOTTLE_VOLUME) continue;

            BOTTLES.putIfAbsent(held.getFluid(), item);
        }

        // vanilla's water bottle is a potion, so it never turns up in that scan
        BOTTLES.putIfAbsent(Fluids.WATER, Items.POTION);
    }

    /** Whatever an item's own fluid handler says it contains, if it has one. */
    private static FluidStack handlerContents(ItemStack stack) {
        IFluidHandlerItem handler = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
        if (handler == null || handler.getTanks() < 1) return FluidStack.EMPTY;

        return handler.getFluidInTank(0);
    }

    // ============================================================
    // Lookups
    // ============================================================
    /** @return the bucket for a fluid, declared or not, else null. */
    @Nullable
    public static Item bucketFor(Fluid fluid) {
        Item declared = fluid.getBucket();
        if (declared != Items.AIR) return declared;

        return BUCKETS.get(fluid);
    }

    /** @return the bottle for a fluid, else null. */
    @Nullable
    public static Item bottleFor(Fluid fluid) {
        return BOTTLES.get(fluid);
    }

    /** Drums hold anything that comes in a bucket, and nothing that doesn't. */
    public static boolean isBucketable(FluidStack stack) {
        return !stack.isEmpty() && bucketFor(stack.getFluid()) != null;
    }

    /** The bottle block's counterpart: anything that comes in a bottle. */
    public static boolean isBottleable(FluidStack stack) {
        return !stack.isEmpty() && bottleFor(stack.getFluid()) != null;
    }

    // ============================================================
    // Item contents
    // ============================================================
    /** The fluid a bucket holds, matching Forge's own FluidBucketWrapper. */
    public static FluidStack bucketContents(ItemStack stack) {
        Item item = stack.getItem();

        if (item instanceof BucketItem bucket && bucket.getFluid() != Fluids.EMPTY) {
            return new FluidStack(bucket.getFluid(), FluidType.BUCKET_VOLUME);
        }
        if (item instanceof MilkBucketItem && ForgeMod.MILK.isPresent()) {
            return new FluidStack(ForgeMod.MILK.get(), FluidType.BUCKET_VOLUME);
        }
        return FluidStack.EMPTY;
    }

    /** The fluid a bottle holds, including vanilla's water bottle. */
    public static FluidStack bottleContents(ItemStack stack) {
        if (stack.is(Items.POTION) && PotionUtils.getPotion(stack) == Potions.WATER) {
            return new FluidStack(Fluids.WATER, BOTTLE_VOLUME);
        }

        FluidStack held = handlerContents(stack);
        if (held.isEmpty() || held.getAmount() > BOTTLE_VOLUME) return FluidStack.EMPTY;

        return new FluidStack(held, BOTTLE_VOLUME);
    }

    // ============================================================
    // Interactions
    // ============================================================
    /**
     * Bucket handling for containers that never expose an {@link IFluidHandlerItem},
     * which {@link net.minecraftforge.fluids.FluidUtil#interactWithFluidHandler} can't see.
     *
     * @return PASS when the held item isn't a bucket, so the caller can keep looking.
     */
    public static InteractionResult useBucket(Level level, BlockPos pos, Player player,
                                              InteractionHand hand, ItemStack held, IFluidHandler tank) {
        return transfer(level, pos, player, hand, held, tank,
                bucketContents(held), Items.BUCKET, FluidType.BUCKET_VOLUME, true);
    }

    /** The bottle counterpart, moving a quarter bucket at a time. */
    public static InteractionResult useBottle(Level level, BlockPos pos, Player player,
                                              InteractionHand hand, ItemStack held, IFluidHandler tank) {
        return transfer(level, pos, player, hand, held, tank,
                bottleContents(held), Items.GLASS_BOTTLE, BOTTLE_VOLUME, false);
    }

    private static InteractionResult transfer(Level level, BlockPos pos, Player player, InteractionHand hand,
                                              ItemStack held, IFluidHandler tank, FluidStack contents,
                                              Item emptyContainer, int volume, boolean bucket) {
        // filling the tank from a full container
        if (!contents.isEmpty()) {
            if (tank.fill(contents, IFluidHandler.FluidAction.SIMULATE) != volume) return InteractionResult.PASS;

            if (!level.isClientSide) {
                tank.fill(contents, IFluidHandler.FluidAction.EXECUTE);
                if (!player.isCreative()) giveResult(player, hand, held, new ItemStack(emptyContainer));
                playSound(level, pos, contents, bucket, false);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // emptying the tank into an empty container
        if (held.is(emptyContainer)) {
            FluidStack drained = tank.drain(volume, IFluidHandler.FluidAction.SIMULATE);
            if (drained.getAmount() != volume) return InteractionResult.PASS;

            Item filled = bucket ? bucketFor(drained.getFluid()) : bottleFor(drained.getFluid());
            if (filled == null) return InteractionResult.PASS;

            ItemStack result = filled == Items.POTION
                    ? PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)
                    : new ItemStack(filled);

            if (!level.isClientSide) {
                tank.drain(volume, IFluidHandler.FluidAction.EXECUTE);
                if (!player.isCreative()) giveResult(player, hand, held, result);
                playSound(level, pos, drained, bucket, true);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    /** Swaps the held container for what it became, without eating the rest of the stack. */
    private static void giveResult(Player player, InteractionHand hand, ItemStack held, ItemStack result) {
        held.shrink(1);

        if (held.isEmpty()) player.setItemInHand(hand, result);
        else if (!player.getInventory().add(result)) player.drop(result, false);
    }

    // ============================================================
    // Sounds
    // ============================================================
    /**
     * Forge only declares bucket sound actions, and many fluids declare none at all,
     * in which case {@link net.minecraftforge.fluids.FluidUtil} stays silent.
     */
    private static void playBucketSound(Level level, BlockPos pos, FluidStack fluid, SoundAction action) {
        SoundEvent sound = fluid.getFluid().getFluidType().getSound(fluid, action);
        if (sound == null) { sound = action == SoundActions.BUCKET_FILL ? SoundEvents.BUCKET_FILL : SoundEvents.BUCKET_EMPTY; }
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    public static void playFluidSound(Level level, BlockPos pos, FluidStack fluid, boolean bucket, boolean filling) {
        if (bucket) {
            playBucketSound(level, pos, fluid, filling ? SoundActions.BUCKET_FILL : SoundActions.BUCKET_EMPTY);
            return;
        }

        SoundEvent sound = filling ? SoundEvents.BOTTLE_FILL : SoundEvents.BOTTLE_EMPTY;
        level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
}