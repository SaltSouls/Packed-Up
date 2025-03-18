package salted.packedup.common.block.handlers.utils;

import net.minecraft.world.level.block.Block;
import salted.packedup.common.block.BookBundleBlock;
import salted.packedup.common.block.BookBundleSlabBlock;
import salted.packedup.common.block.BookPileBlock;
import salted.packedup.common.registry.PUBlocks;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class BundleUtils {

    // Map for fast lookups
    private static final Map<Block, Bundle> BUNDLE_MAP = Arrays.stream(Bundle.values())
            .flatMap(bundle -> Arrays.stream(new Block[]{bundle.getBundleBlock(), bundle.getBundleSlab()})
                    .map(block -> Map.entry(block, bundle)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    // Lookup method
    protected Bundle getBundleType(Block block) {
        return BUNDLE_MAP.get(block);
    }

    // Enum definition
    protected enum Bundle {
        BASE(PUBlocks.BOOK_BUNDLE.get(), PUBlocks.BOOK_BUNDLE_SLAB.get(), PUBlocks.BOOK_PILE.get()),
        WHITE(PUBlocks.WHITE_BOOK_BUNDLE.get(), PUBlocks.WHITE_BOOK_BUNDLE_SLAB.get(), PUBlocks.WHITE_BOOK_PILE.get()),
        LIGHT_GRAY(PUBlocks.LIGHT_GRAY_BOOK_BUNDLE.get(), PUBlocks.LIGHT_GRAY_BOOK_BUNDLE_SLAB.get(), PUBlocks.LIGHT_GRAY_BOOK_PILE.get()),
        GRAY(PUBlocks.GRAY_BOOK_BUNDLE.get(), PUBlocks.GRAY_BOOK_BUNDLE_SLAB.get(), PUBlocks.GRAY_BOOK_PILE.get()),
        BLACK(PUBlocks.BLACK_BOOK_BUNDLE.get(), PUBlocks.BLACK_BOOK_BUNDLE_SLAB.get(), PUBlocks.BLACK_BOOK_PILE.get()),
        BROWN(PUBlocks.BROWN_BOOK_BUNDLE.get(), PUBlocks.BROWN_BOOK_BUNDLE_SLAB.get(), PUBlocks.BROWN_BOOK_PILE.get()),
        RED(PUBlocks.RED_BOOK_BUNDLE.get(), PUBlocks.RED_BOOK_BUNDLE_SLAB.get(), PUBlocks.RED_BOOK_PILE.get()),
        ORANGE(PUBlocks.ORANGE_BOOK_BUNDLE.get(), PUBlocks.ORANGE_BOOK_BUNDLE_SLAB.get(), PUBlocks.ORANGE_BOOK_PILE.get()),
        YELLOW(PUBlocks.YELLOW_BOOK_BUNDLE.get(), PUBlocks.YELLOW_BOOK_BUNDLE_SLAB.get(), PUBlocks.YELLOW_BOOK_PILE.get()),
        LIME(PUBlocks.LIME_BOOK_BUNDLE.get(), PUBlocks.LIME_BOOK_BUNDLE_SLAB.get(), PUBlocks.LIME_BOOK_PILE.get()),
        GREEN(PUBlocks.GREEN_BOOK_BUNDLE.get(), PUBlocks.GREEN_BOOK_BUNDLE_SLAB.get(), PUBlocks.GREEN_BOOK_PILE.get()),
        CYAN(PUBlocks.CYAN_BOOK_BUNDLE.get(), PUBlocks.CYAN_BOOK_BUNDLE_SLAB.get(), PUBlocks.CYAN_BOOK_PILE.get()),
        LIGHT_BLUE(PUBlocks.LIGHT_BLUE_BOOK_BUNDLE.get(), PUBlocks.LIGHT_BLUE_BOOK_BUNDLE_SLAB.get(), PUBlocks.LIGHT_BLUE_BOOK_PILE.get()),
        BLUE(PUBlocks.BLUE_BOOK_BUNDLE.get(), PUBlocks.BLUE_BOOK_BUNDLE_SLAB.get(), PUBlocks.BLUE_BOOK_PILE.get()),
        PURPLE(PUBlocks.PURPLE_BOOK_BUNDLE.get(), PUBlocks.PURPLE_BOOK_BUNDLE_SLAB.get(), PUBlocks.PURPLE_BOOK_PILE.get()),
        MAGENTA(PUBlocks.MAGENTA_BOOK_BUNDLE.get(), PUBlocks.MAGENTA_BOOK_BUNDLE_SLAB.get(), PUBlocks.MAGENTA_BOOK_PILE.get()),
        PINK(PUBlocks.PINK_BOOK_BUNDLE.get(), PUBlocks.PINK_BOOK_BUNDLE_SLAB.get(), PUBlocks.PINK_BOOK_PILE.get());

        private final BookBundleBlock bundleBlock;
        private final BookBundleSlabBlock bundleSlab;
        private final BookPileBlock pile;

        Bundle(BookBundleBlock bundleBlock, BookBundleSlabBlock bundleSlab, BookPileBlock pile) {
            this.bundleBlock = bundleBlock;
            this.bundleSlab = bundleSlab;
            this.pile = pile;
        }

        public BookBundleBlock getBundleBlock() {
            return this.bundleBlock;
        }

        public BookBundleSlabBlock getBundleSlab() {
            return this.bundleSlab;
        }

        public BookPileBlock getPile() {
            return this.pile;
        }
    }
}
