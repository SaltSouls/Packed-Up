package salted.packedup.common.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import salted.packedup.common.block.*;
import salted.packedup.common.block.entity.DrumBarrelBlockEntity;
import salted.packedup.common.block.entity.FluidGaugeBlockEntity;
import salted.packedup.common.registry.helpers.RegistryHelper;

public class PURegistry extends RegistryHelper {


    // ============================================================
    // Books
    // ============================================================
    public static final RegistryEntry<BookBundleBlock> BOOK_BUNDLE = bookBundle("book_bundle", () -> Blocks.BROWN_WOOL);
    public static final RegistryEntry<BookBundleSlabBlock> BOOK_BUNDLE_SLAB = bookBundleSlab("book_bundle_slab", () -> Blocks.BROWN_WOOL);
    public static final RegistryEntry<BookPileBlock> BOOK_PILE = bookPile("book_pile", () -> Blocks.BROWN_WOOL);
    // Colored books
    static { for (DyeColor color : COLOR_ORDER) coloredBookSet(color); }

    // ============================================================
    // Baskets
    // ============================================================
    public static final RegistryEntry<Block> SWEET_BERRY_BASKET = basket("sweet_berry_basket", MapColor.COLOR_MAGENTA, PUSoundsTypes.BERRY_BASKET);
    public static final RegistryEntry<Block> GLOW_BERRY_BASKET = basket("glow_berry_basket", MapColor.GOLD, PUSoundsTypes.BERRY_BASKET, 6);
    public static final RegistryEntry<Block> APPLE_BASKET = basket("apple_basket", MapColor.COLOR_RED, PUSoundsTypes.APPLE_BASKET);
    public static final RegistryEntry<Block> GOLDEN_APPLE_BASKET = basket("golden_apple_basket", MapColor.GOLD, PUSoundsTypes.APPLE_BASKET);

    // ============================================================
    // Fish Barrels
    // ============================================================
    public static final RegistryEntry<Block> COD_BARREL = fishBarrel("cod_barrel", () -> Blocks.OAK_PLANKS);
    public static final RegistryEntry<Block> SALMON_BARREL = fishBarrel("salmon_barrel", () -> Blocks.BARREL);

    // ============================================================
    // Produce Bags
    // ============================================================
    public static final RegistryEntry<Block> COCOA_BEAN_BAG = produceBag("cocoa_bean_bag", MapColor.COLOR_BROWN, PUSoundsTypes.PRODUCE_BAG);
    public static final RegistryEntry<Block> SUGAR_BAG = produceBag("sugar_bag", MapColor.COLOR_LIGHT_GRAY, PUSoundsTypes.POWDER_BAG);
    public static final RegistryEntry<Block> NETHER_WART_BAG = produceBag("nether_wart_bag", MapColor.COLOR_BROWN, PUSoundsTypes.PRODUCE_BAG);

    // ============================================================
    // Misc Bags
    // ============================================================
    public static final RegistryEntry<Block> GLOWSTONE_DUST_BAG = miscBag("glowstone_dust_bag", MapColor.COLOR_BROWN, PUSoundsTypes.POWDER_BAG, 8);
    public static final RegistryEntry<Block> ENDER_PEARL_BAG = miscBag("ender_pearl_bag", MapColor.COLOR_PURPLE, PUSoundsTypes.GLASS_BAG);

    // ============================================================
    // Grass / Thatch
    // ============================================================
    public static final RegistryEntry<HayBlock> GRASS_BALE = categorized(TabCategory.GRASS,
            noDatagenItem(REGISTRATE.block("grass_bale", HayBlock::new).initialProperties(() -> Blocks.GRASS_BLOCK)).register());
    public static final RegistryEntry<Block> GRASS_THATCH = categorized(TabCategory.GRASS,
            noDatagenItem(REGISTRATE.block("grass_thatch", Block::new).initialProperties(() -> Blocks.HAY_BLOCK)).register());
    public static final RegistryEntry<StairBlock> GRASS_THATCH_STAIRS = categorized(TabCategory.GRASS,
            noDatagenItem(REGISTRATE.block("grass_thatch_stairs", p -> new StairBlock(Blocks.HAY_BLOCK::defaultBlockState, p)).initialProperties(() -> Blocks.HAY_BLOCK)).register());
    public static final RegistryEntry<SlabBlock> GRASS_THATCH_SLAB = categorized(TabCategory.GRASS,
            noDatagenItem(REGISTRATE.block("grass_thatch_slab", SlabBlock::new).initialProperties(() -> Blocks.HAY_BLOCK)).register());

    // ============================================================
    // Turf
    // ============================================================
    public static final RegistryEntry<TurfBlock> GRASS_TURF = categorized(TabCategory.TURF,
            noDatagenItem(REGISTRATE.block("grass_turf", TurfBlock::new).initialProperties(() -> Blocks.GRASS_BLOCK)).register());
    public static final RegistryEntry<TurfLayerBlock> GRASS_TURF_LAYER = categorized(TabCategory.TURF,
            noDatagenItem(REGISTRATE.block("grass_turf_layer", TurfLayerBlock::new).initialProperties(() -> Blocks.GRASS_BLOCK)).register());
    public static final RegistryEntry<TurfBlock> PODZOL_TURF = categorized(TabCategory.TURF,
            noDatagenItem(REGISTRATE.block("podzol_turf", TurfBlock::new).initialProperties(() -> Blocks.PODZOL)).register());
    public static final RegistryEntry<TurfLayerBlock> PODZOL_TURF_LAYER = categorized(TabCategory.TURF,
            noDatagenItem(REGISTRATE.block("podzol_turf_layer", TurfLayerBlock::new).initialProperties(() -> Blocks.PODZOL)).register());
    public static final RegistryEntry<TurfBlock> MYCELIUM_TURF = categorized(TabCategory.TURF,
            noDatagenItem(REGISTRATE.block("mycelium_turf", TurfBlock::new).initialProperties(() -> Blocks.MYCELIUM)).register());
    public static final RegistryEntry<TurfLayerBlock> MYCELIUM_TURF_LAYER = categorized(TabCategory.TURF,
            noDatagenItem(REGISTRATE.block("mycelium_turf_layer", TurfLayerBlock::new).initialProperties(() -> Blocks.MYCELIUM)).register());

    // ============================================================
    // Resource Bags
    // ============================================================
    public static final RegistryEntry<Block> DIRT_BAG = resourceBag("dirt_bag", () -> Blocks.DIRT, PUSoundsTypes.RESOURCE_BAG_DIRT);
    public static final RegistryEntry<Block> ROOTED_DIRT_BAG = resourceBag("rooted_dirt_bag", () -> Blocks.ROOTED_DIRT, PUSoundsTypes.RESOURCE_BAG_DIRT);
    public static final RegistryEntry<Block> COARSE_DIRT_BAG = resourceBag("coarse_dirt_bag", () -> Blocks.COARSE_DIRT, PUSoundsTypes.RESOURCE_BAG_GRAVEL);
    public static final RegistryEntry<Block> GRAVEL_BAG = resourceBag("gravel_bag", () -> Blocks.GRAVEL, PUSoundsTypes.RESOURCE_BAG_GRAVEL);

    // ============================================================
    // Produce Crates
    // ============================================================
    public static final RegistryEntry<Block> CARROT_CRATE = produceCrate("carrot_crate");
    public static final RegistryEntry<Block> GOLDEN_CARROT_CRATE = produceCrate("golden_carrot_crate");
    public static final RegistryEntry<Block> POTATO_CRATE = produceCrate("potato_crate");
    public static final RegistryEntry<Block> BEETROOT_CRATE = produceCrate("beetroot_crate");
    public static final RegistryEntry<Block> EGG_CRATE = produceCrate("egg_crate");
    public static final RegistryEntry<Block> RED_MUSHROOM_CRATE = mushroomCrate("red_mushroom_crate");
    public static final RegistryEntry<Block> BROWN_MUSHROOM_CRATE = mushroomCrate("brown_mushroom_crate");
    public static final RegistryEntry<Block> CRIMSON_FUNGUS_CRATE = mushroomCrate("crimson_fungus_crate");
    public static final RegistryEntry<Block> WARPED_FUNGUS_CRATE = mushroomCrate("warped_fungus_crate");

    // ============================================================
    // Crate Lids
    // ============================================================
    public static final RegistryEntry<CrateLidBlock> CRATE_LID = categorized(TabCategory.CRATE_LIDS,
            noDatagenItem(REGISTRATE.block("crate_lid", CrateLidBlock::new)
                    .initialProperties(() -> Blocks.OAK_PLANKS)
                    .properties(p -> p.strength(1.0F, 4.0F)))
                    .register());
    public static final RegistryEntry<CrateLidBlock> REINFORCED_CRATE_LID = categorized(TabCategory.CRATE_LIDS,
            noDatagenItem(REGISTRATE.block("reinforced_crate_lid", CrateLidBlock::new)
                    .initialProperties(() -> Blocks.OAK_PLANKS)
                    .properties(p -> p.strength(2.0F, 8.0F)))
                    .register());

    // ============================================================
    // Misc Crates
    // ============================================================
    public static final RegistryEntry<Block> GUNPOWDER_CRATE = miscCrate("gunpowder_crate", MapColor.COLOR_GRAY, 1.0F, 1.0F, PUSoundsTypes.GUNPOWDER_CRATE);
    public static final RegistryEntry<Block> QUARTZ_CRATE = miscResourceCrate("quartz_crate", MapColor.QUARTZ, PUSoundsTypes.SHARD_CRATE);
    public static final RegistryEntry<Block> AMETHYST_CRATE = miscResourceCrate("amethyst_crate", MapColor.COLOR_PURPLE, PUSoundsTypes.CRYSTAL_CRATE);
    public static final RegistryEntry<Block> ECHO_SHARD_CRATE = miscResourceCrate("echo_shard_crate", MapColor.COLOR_CYAN, PUSoundsTypes.SHARD_CRATE);

    // ============================================================
    // Resource Crates
    // ============================================================
    public static final RegistryEntry<Block> COBBLESTONE_CRATE = resourceCrate("cobblestone_crate", MapColor.STONE);
    public static final RegistryEntry<Block> COBBLED_DEEPSLATE_CRATE = resourceCrate("cobbled_deepslate_crate", MapColor.DEEPSLATE);
    public static final RegistryEntry<Block> ANDESITE_CRATE = resourceCrate("andesite_crate", MapColor.STONE);
    public static final RegistryEntry<Block> DIORITE_CRATE = resourceCrate("diorite_crate", MapColor.QUARTZ);
    public static final RegistryEntry<Block> GRANITE_CRATE = resourceCrate("granite_crate", MapColor.DIRT);
    public static final RegistryEntry<Block> TUFF_CRATE = resourceCrate("tuff_crate", MapColor.TERRACOTTA_GRAY);
    public static final RegistryEntry<Block> BLACKSTONE_CRATE = resourceCrate("blackstone_crate", MapColor.COLOR_BLACK);
    public static final RegistryEntry<Block> BASALT_CRATE = resourceCrate("basalt_crate", MapColor.COLOR_BLACK);
    public static final RegistryEntry<Block> NETHERRACK_CRATE = resourceCrate("netherrack_crate", MapColor.NETHER);
    public static final RegistryEntry<Block> RAW_COPPER_CRATE = resourceCrate("raw_copper_crate", MapColor.COLOR_ORANGE);
    public static final RegistryEntry<Block> RAW_IRON_CRATE = resourceCrate("raw_iron_crate", MapColor.RAW_IRON);
    public static final RegistryEntry<Block> RAW_GOLD_CRATE = resourceCrate("raw_gold_crate", MapColor.GOLD);

    // ============================================================
    // Reinforced Crates
    // ============================================================
    public static final RegistryEntry<Block> REINFORCED_COBBLESTONE_CRATE = reinforcedCrate("reinforced_cobblestone_crate", () -> Blocks.COBBLESTONE, MapColor.STONE);
    public static final RegistryEntry<Block> REINFORCED_COBBLED_DEEPSLATE_CRATE = reinforcedCrate("reinforced_cobbled_deepslate_crate", () -> Blocks.COBBLED_DEEPSLATE, MapColor.DEEPSLATE);
    public static final RegistryEntry<Block> REINFORCED_ANDESITE_CRATE = reinforcedCrate("reinforced_andesite_crate", () -> Blocks.STONE, MapColor.STONE);
    public static final RegistryEntry<Block> REINFORCED_DIORITE_CRATE = reinforcedCrate("reinforced_diorite_crate", () -> Blocks.STONE, MapColor.QUARTZ);
    public static final RegistryEntry<Block> REINFORCED_GRANITE_CRATE = reinforcedCrate("reinforced_granite_crate", () -> Blocks.STONE, MapColor.DIRT);
    public static final RegistryEntry<Block> REINFORCED_TUFF_CRATE = reinforcedCrate("reinforced_tuff_crate", () -> Blocks.STONE, MapColor.TERRACOTTA_GRAY);
    public static final RegistryEntry<Block> REINFORCED_BLACKSTONE_CRATE = reinforcedCrate("reinforced_blackstone_crate", () -> Blocks.STONE, MapColor.COLOR_BLACK);
    public static final RegistryEntry<Block> REINFORCED_BASALT_CRATE = reinforcedCrate("reinforced_basalt_crate", () -> Blocks.STONE, MapColor.COLOR_BLACK);
    public static final RegistryEntry<Block> REINFORCED_NETHERRACK_CRATE = reinforcedCrate("reinforced_netherrack_crate", () -> Blocks.STONE, MapColor.NETHER);

    // ============================================================
    // Piles
    // ============================================================
    public static final RegistryEntry<HorizontalBlock> BRICK_PILE = pile("brick_pile", () -> Blocks.BRICKS, 2.0F, 6.0F);
    public static final RegistryEntry<HorizontalBlock> NETHER_BRICK_PILE = pile("nether_brick_pile", () -> Blocks.NETHER_BRICKS, 2.0F, 6.0F);
    public static final RegistryEntry<HorizontalBlock> STONE_PILE = pile("stone_pile", () -> Blocks.STONE, 2.0F, 6.0F);
    public static final RegistryEntry<HorizontalBlock> DEEPSLATE_PILE = pile("deepslate_pile", () -> Blocks.DEEPSLATE, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> CALCITE_PILE = pile("calcite_pile", () -> Blocks.CALCITE, 2.0F, 6.0F);

    // ============================================================
    // Pallets
    // ============================================================
    public static final RegistryEntry<HorizontalQuarterSlabBlock> PALLET = categorized(TabCategory.PALLETS,
            noDatagenItem(REGISTRATE.block("pallet", HorizontalQuarterSlabBlock::new)
                    .initialProperties(() -> Blocks.OAK_PLANKS)
                    .loot((lt, block) -> lt.add(block, quarterSlabLoot(block))))
                    .register());
    public static final RegistryEntry<HorizontalBlock> BRICK_PALLET = pallet("brick_pallet", () -> Blocks.BRICKS, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> NETHER_BRICK_PALLET = pallet("nether_brick_pallet", () -> Blocks.NETHER_BRICKS, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> STONE_PALLET = pallet("stone_pallet", () -> Blocks.STONE, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> DEEPSLATE_PALLET = pallet("deepslate_pallet", () -> Blocks.DEEPSLATE, 4.0F, 24.0F);
    public static final RegistryEntry<HorizontalBlock> CALCITE_PALLET = pallet("calcite_pallet", () -> Blocks.CALCITE, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> COPPER_PALLET = pallet("copper_pallet", () -> Blocks.COPPER_BLOCK, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> IRON_PALLET = pallet("iron_pallet", () -> Blocks.IRON_BLOCK, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> GOLD_PALLET = pallet("gold_pallet", () -> Blocks.GOLD_BLOCK, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> DIAMOND_PALLET = pallet("diamond_pallet", () -> Blocks.DIAMOND_BLOCK, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> EMERALD_PALLET = pallet("emerald_pallet", () -> Blocks.EMERALD_BLOCK, 4.0F, 12.0F);
    public static final RegistryEntry<HorizontalBlock> NETHERITE_PALLET = fireResistantPallet("netherite_pallet", () -> Blocks.NETHERITE_BLOCK, 4.0F, 1200.0F);

    // ============================================================
    // Industrial Spools
    // ============================================================
    static { for (DyeColor color : COLOR_ORDER) industrialSpool(color); }

    // ============================================================
    // Drums
    // ============================================================
    public static final RegistryEntry<DrumBarrelBlock> DRUM_BARREL = drumBarrel("drum_barrel", MapColor.METAL);
    static { for (DyeColor color : COLOR_ORDER) coloredDrumBarrel(color); }
    @SuppressWarnings("unchecked")
    public static final BlockEntityEntry<DrumBarrelBlockEntity> DRUM_BARREL_ENTITY = REGISTRATE
            .blockEntity("drum_barrel", DrumBarrelBlockEntity::new)
            .validBlocks(DRUM_BARREL_ENTRIES.toArray(NonNullSupplier[]::new))
            .register();

    // ============================================================
    // Items
    // ============================================================
    public static final RegistryEntry<FluidGaugeBlock> FLUID_GAUGE = fluidGauge("fluid_gauge");

    public static final BlockEntityEntry<FluidGaugeBlockEntity> FLUID_GAUGE_ENTITY = REGISTRATE
            .blockEntity("fluid_gauge", FluidGaugeBlockEntity::new)
            .validBlocks(FLUID_GAUGE)
            .register();

    // ============================================================
    // Initialization
    // ============================================================
    public static void register() {}
}