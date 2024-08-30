package salted.packedup.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import salted.packedup.common.block.BookBundleBlock;
import salted.packedup.common.block.BookBundleSlabBlock;
import salted.packedup.common.block.BookPileBlock;
import salted.packedup.common.block.state.PUProperties;
import salted.packedup.common.registry.PUBlocks;

public class BundleUtils {

    // isn't strictly needed, but makes me feel better
    private static boolean isBundle(Block block, BookBundleBlock bundleBlock, BookBundleSlabBlock bundleSlab) {
        return block == bundleBlock || block == bundleSlab;
    }

    private static InteractionResult convert(BlockState state, int layers, Level world, BlockPos pos, Player player) {
        if (world.isClientSide) {
            return InteractionResult.PASS;
        }
        ItemStack item = player.getMainHandItem();

        if (!player.isCreative()) {
            item.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        world.setBlock(pos, state.setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING)).setValue(PUProperties.QUARTER_LAYERS, layers).setValue(BlockStateProperties.WATERLOGGED, false), 0);
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.9F);
        return InteractionResult.SUCCESS;
    }

    public static InteractionResult shearBundle(Block block, BlockState state, Level world, BlockPos pos, Player player) {
        int size;

        if (block instanceof BookBundleBlock) {
            size = BookPileBlock.MAX_HEIGHT;
        } else if (block instanceof BookBundleSlabBlock) {
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);

            if (type.equals(SlabType.BOTTOM)) {
                size = BookPileBlock.MAX_HEIGHT / 2;
            } else if (type.equals(SlabType.DOUBLE)) {
                size = BookPileBlock.MAX_HEIGHT;
            } else return InteractionResult.PASS;
        } else return InteractionResult.PASS;

        Bundle bundle = getBundle(block);

        // while it may look big, this work super well
        switch (bundle) {
            case WHITE -> {
                return convert(PUBlocks.WHITE_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case LIGHT_GRAY -> {
                return convert(PUBlocks.LIGHT_GRAY_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case GRAY -> {
                return convert(PUBlocks.GRAY_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case BLACK -> {
                return convert(PUBlocks.BLACK_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case BROWN -> {
                return convert(PUBlocks.BROWN_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case RED -> {
                return convert(PUBlocks.RED_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case ORANGE -> {
                return convert(PUBlocks.ORANGE_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case YELLOW -> {
                return convert(PUBlocks.YELLOW_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case LIME -> {
                return convert(PUBlocks.LIME_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case GREEN -> {
                return convert(PUBlocks.GREEN_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case CYAN -> {
                return convert(PUBlocks.CYAN_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case LIGHT_BLUE -> {
                return convert(PUBlocks.LIGHT_BLUE_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case BLUE -> {
                return convert(PUBlocks.BLUE_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case PURPLE -> {
                return convert(PUBlocks.PURPLE_BOOK_BUNDLE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case MAGENTA -> {
                return convert(PUBlocks.MAGENTA_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            case PINK -> {
                return convert(PUBlocks.PINK_BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
            default -> {
                return convert(PUBlocks.BOOK_PILE.get().withPropertiesOf(state), size, world, pos, player);
            }
        }
    }

    // all this just to have a usable enum
    private static Bundle getBundle(Block block) {
        if (isBundle(block, PUBlocks.WHITE_BOOK_BUNDLE.get(), PUBlocks.WHITE_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.WHITE;
        } else if (isBundle(block, PUBlocks.LIGHT_GRAY_BOOK_BUNDLE.get(), PUBlocks.LIGHT_GRAY_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.LIGHT_GRAY;
        } else if (isBundle(block, PUBlocks.GRAY_BOOK_BUNDLE.get(), PUBlocks.GRAY_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.GRAY;
        } else if (isBundle(block, PUBlocks.BLACK_BOOK_BUNDLE.get(), PUBlocks.BLACK_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.BLACK;
        } else if (isBundle(block, PUBlocks.BROWN_BOOK_BUNDLE.get(), PUBlocks.BROWN_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.BROWN;
        } else if (isBundle(block, PUBlocks.RED_BOOK_BUNDLE.get(), PUBlocks.RED_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.RED;
        } else if (isBundle(block, PUBlocks.ORANGE_BOOK_BUNDLE.get(), PUBlocks.ORANGE_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.ORANGE;
        } else if (isBundle(block, PUBlocks.YELLOW_BOOK_BUNDLE.get(), PUBlocks.YELLOW_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.YELLOW;
        } else if (isBundle(block, PUBlocks.LIME_BOOK_BUNDLE.get(), PUBlocks.LIME_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.LIME;
        } else if (isBundle(block, PUBlocks.GREEN_BOOK_BUNDLE.get(), PUBlocks.GREEN_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.GREEN;
        } else if (isBundle(block, PUBlocks.CYAN_BOOK_BUNDLE.get(), PUBlocks.CYAN_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.CYAN;
        } else if (isBundle(block, PUBlocks.LIGHT_BLUE_BOOK_BUNDLE.get(), PUBlocks.LIGHT_BLUE_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.LIGHT_BLUE;
        } else if (isBundle(block, PUBlocks.BLUE_BOOK_BUNDLE.get(), PUBlocks.BLUE_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.BLUE;
        } else if (isBundle(block, PUBlocks.PURPLE_BOOK_BUNDLE.get(), PUBlocks.PURPLE_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.PURPLE;
        } else if (isBundle(block, PUBlocks.MAGENTA_BOOK_BUNDLE.get(), PUBlocks.MAGENTA_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.MAGENTA;
        } else if (isBundle(block, PUBlocks.PINK_BOOK_BUNDLE.get(), PUBlocks.PINK_BOOK_BUNDLE_SLAB.get())) {
            return Bundle.PINK;
        } else return Bundle.BASE;
    }

    private enum Bundle {
        BASE,
        WHITE,
        LIGHT_GRAY,
        GRAY,
        BLACK,
        BROWN,
        RED,
        ORANGE,
        YELLOW,
        LIME,
        GREEN,
        CYAN,
        LIGHT_BLUE,
        BLUE,
        PURPLE,
        MAGENTA,
        PINK
    }
}