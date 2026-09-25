package salted.packedup.common.registry.helpers;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import salted.packedup.common.block.*;
import salted.packedup.common.registry.TabCategory;

import java.util.*;

public class Organizer {

    // ============================================================
    // Color Ordering/Grouping
    // ============================================================
    public static final DyeColor[] COLOR_ORDER = {
            DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK,
            DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW,
            DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE,
            DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK
    };

    public static final Map<DyeColor, ColoredBookSet> COLORED_BOOKS = new LinkedHashMap<>();
    public static final Map<DyeColor, RegistryEntry<IndustrialSpoolBlock>> COLORED_SPOOLS = new LinkedHashMap<>();
    public static final Map<DyeColor, RegistryEntry<DrumBarrelBlock>> COLORED_DRUMS = new LinkedHashMap<>();

    /** Color mapped wool blocks based on dye color */
    protected static Block getWoolBlock(DyeColor color) {
        return switch (color) {
            case WHITE      -> Blocks.WHITE_WOOL;
            case ORANGE     -> Blocks.ORANGE_WOOL;
            case MAGENTA    -> Blocks.MAGENTA_WOOL;
            case LIGHT_BLUE -> Blocks.LIGHT_BLUE_WOOL;
            case YELLOW     -> Blocks.YELLOW_WOOL;
            case LIME       -> Blocks.LIME_WOOL;
            case PINK       -> Blocks.PINK_WOOL;
            case GRAY       -> Blocks.GRAY_WOOL;
            case LIGHT_GRAY -> Blocks.LIGHT_GRAY_WOOL;
            case CYAN       -> Blocks.CYAN_WOOL;
            case PURPLE     -> Blocks.PURPLE_WOOL;
            case BLUE       -> Blocks.BLUE_WOOL;
            case BROWN      -> Blocks.BROWN_WOOL;
            case GREEN      -> Blocks.GREEN_WOOL;
            case RED        -> Blocks.RED_WOOL;
            case BLACK      -> Blocks.BLACK_WOOL;
        };
    }

    // ============================================================
    // Block Grouping
    // ============================================================
    public static final List<RegistryEntry<? extends Block>> RESOURCE_CRATE_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> REINFORCED_CRATE_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> MISC_CRATE_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> PRODUCE_CRATE_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> MUSHROOM_CRATE_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> BASKET_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> BARREL_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> PRODUCE_BAG_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> MISC_BAG_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> RESOURCE_BAG_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> PILE_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> PALLET_ENTRIES = new ArrayList<>();
    public static final List<RegistryEntry<? extends Block>> DRUM_BARREL_ENTRIES = new ArrayList<>();

    // ============================================================
    // Item Grouping
    // ============================================================



    public record ColoredBookSet(
            DyeColor color,
            RegistryEntry<BookBundleBlock> bundle,
            RegistryEntry<BookBundleSlabBlock> slab,
            RegistryEntry<BookPileBlock> pile
    ) {
        public DyeColor getColor() { return this.color; }
        public BookBundleBlock getBundle() { return this.bundle.get(); }
        public BookBundleSlabBlock getSlab() { return this.slab.get(); }
        public BookPileBlock getPile() {
            return this.pile.get();
        }
    }

    // ============================================================
    // Tab Ordering
    // ============================================================
    public static final EnumMap<TabCategory, List<RegistryEntry<? extends Block>>> TAB_ORDER = new EnumMap<>(TabCategory.class);
    /** Every registered block — used by loot/datagen auto-discovery. */
    public static final List<RegistryEntry<? extends Block>> ALL_ENTRIES = new ArrayList<>();

    protected static <T extends Block> RegistryEntry<T> categorized(TabCategory cat, RegistryEntry<T> entry) {
        TAB_ORDER.computeIfAbsent(cat, k -> new ArrayList<>()).add(entry);
        ALL_ENTRIES.add(entry);
        return entry;
    }

}
