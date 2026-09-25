package salted.packedup.common.block.handlers.utils;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import salted.packedup.common.block.BookBundleBlock;
import salted.packedup.common.block.BookBundleSlabBlock;
import salted.packedup.common.block.BookPileBlock;
import salted.packedup.common.registry.PURegistry;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class BundleUtils {

    /**
     * Maps blocks ({@link BookBundleBlock} and {@link BookBundleSlabBlock}) to their corresponding {@link Bundle} type.
     * This allows for quick retrieval of the bundle type based on a {@link Block}.
     */
    private static final Map<Block, Bundle> BUNDLE_MAP = Arrays.stream(Bundle.values())
            .flatMap(bundle -> Arrays.stream(new Block[]{bundle.getBundleBlock(), bundle.getBundleSlab()})
                    .map(block -> Map.entry(block, bundle)))              // Create a Map entry for each block and its corresponding bundle
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // Collect into a Map<Block, Bundle>

    /**
     * Retrieves the bundle type associated with a given block.
     *
     * @param block The {@link Block} to look up.
     * @return The {@link Bundle} type associated with the block, or null if the block is not a valid {@link BookBundleBlock} or {@link BookBundleSlabBlock}.
     */
    protected Bundle getBundleType(Block block) {
        return BUNDLE_MAP.get(block);
    }

    // Fetches the correct book block from the set.
    protected static BookBundleBlock getBundle(DyeColor color) {
        return PURegistry.COLORED_BOOKS.get(color).bundle().get();
    }
    protected static BookBundleSlabBlock getSlab(DyeColor color) {
        return PURegistry.COLORED_BOOKS.get(color).slab().get();
    }
    protected static BookPileBlock getPile(DyeColor color) {
        return PURegistry.COLORED_BOOKS.get(color).pile().get();
    }

    /**
     * Enum representing different types of book bundles and their associated blocks.
     * Each type has a corresponding {@link BookBundleBlock}, {@link BookBundleSlabBlock}, and {@link BookPileBlock}.
     */
    protected enum Bundle {
        BASE(PURegistry.BOOK_BUNDLE.get(), PURegistry.BOOK_BUNDLE_SLAB.get(), PURegistry.BOOK_PILE.get()),

        WHITE(getBundle(DyeColor.WHITE), getSlab(DyeColor.WHITE), getPile(DyeColor.WHITE)),
        LIGHT_GRAY(getBundle(DyeColor.LIGHT_GRAY), getSlab(DyeColor.LIGHT_GRAY), getPile(DyeColor.LIGHT_GRAY)),
        GRAY(getBundle(DyeColor.GRAY), getSlab(DyeColor.GRAY), getPile(DyeColor.GRAY)),
        BLACK(getBundle(DyeColor.BLACK), getSlab(DyeColor.BLACK), getPile(DyeColor.BLACK)),
        BROWN(getBundle(DyeColor.BROWN), getSlab(DyeColor.BROWN), getPile(DyeColor.BROWN)),
        RED(getBundle(DyeColor.RED), getSlab(DyeColor.RED), getPile(DyeColor.RED)),
        ORANGE(getBundle(DyeColor.ORANGE), getSlab(DyeColor.ORANGE), getPile(DyeColor.ORANGE)),
        YELLOW(getBundle(DyeColor.YELLOW), getSlab(DyeColor.YELLOW), getPile(DyeColor.YELLOW)),
        LIME(getBundle(DyeColor.LIME), getSlab(DyeColor.LIME), getPile(DyeColor.LIME)),
        GREEN(getBundle(DyeColor.GREEN), getSlab(DyeColor.GREEN), getPile(DyeColor.GREEN)),
        CYAN(getBundle(DyeColor.CYAN), getSlab(DyeColor.CYAN), getPile(DyeColor.CYAN)),
        LIGHT_BLUE(getBundle(DyeColor.LIGHT_BLUE), getSlab(DyeColor.LIGHT_BLUE), getPile(DyeColor.LIGHT_BLUE)),
        BLUE(getBundle(DyeColor.BLUE), getSlab(DyeColor.BLUE), getPile(DyeColor.BLUE)),
        PURPLE(getBundle(DyeColor.PURPLE), getSlab(DyeColor.PURPLE), getPile(DyeColor.PURPLE)),
        MAGENTA(getBundle(DyeColor.MAGENTA), getSlab(DyeColor.MAGENTA), getPile(DyeColor.MAGENTA)),
        PINK(getBundle(DyeColor.PINK), getSlab(DyeColor.PINK), getPile(DyeColor.PINK));

        private final BookBundleBlock bundleBlock;
        private final BookBundleSlabBlock bundleSlab;
        private final BookPileBlock pile;

        /**
         * Constructor for the {@link Bundle} enum.
         *
         * @param bundleBlock The {@link Block} representing the {@link BookBundleBlock}.
         * @param bundleSlab  The {@link Block} representing the {@link BookBundleSlabBlock}.
         * @param pile        The {@link Block} representing the {@link BookPileBlock}.
         */
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
        public BookPileBlock getBookPile() {
            return this.pile;
        }
    }

    protected enum Type {
        BUNDLE,
        SLAB,
        PILE
    }
}
