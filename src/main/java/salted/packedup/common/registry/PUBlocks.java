package salted.packedup.common.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import salted.packedup.PackedUp;
import salted.packedup.common.block.*;

public class PUBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PackedUp.MODID);
    // resource crates
    public static final RegistryObject<Block> COBBLESTONE_CRATE = BLOCKS.register("cobblestone_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> COBBLED_DEEPSLATE_CRATE = BLOCKS.register("cobbled_deepslate_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLED_DEEPSLATE)
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> ANDESITE_CRATE = BLOCKS.register("andesite_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> DIORITE_CRATE = BLOCKS.register("diorite_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> GRANITE_CRATE = BLOCKS.register("granite_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> TUFF_CRATE = BLOCKS.register("tuff_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> BLACKSTONE_CRATE = BLOCKS.register("blackstone_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> BASALT_CRATE = BLOCKS.register("basalt_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> NETHERRACK_CRATE = BLOCKS.register("netherrack_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NETHER)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> QUARTZ_CRATE = BLOCKS.register("quartz_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.SHARD_CRATE)));
    public static final RegistryObject<Block> AMETHYST_CRATE = BLOCKS.register("amethyst_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.CRYSTAL_CRATE)));
    public static final RegistryObject<Block> ECHO_SHARD_CRATE = BLOCKS.register("echo_shard_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.SHARD_CRATE)));
    public static final RegistryObject<Block> RAW_COPPER_CRATE = BLOCKS.register("raw_copper_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> RAW_IRON_CRATE = BLOCKS.register("raw_iron_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.RAW_IRON)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> RAW_GOLD_CRATE = BLOCKS.register("raw_gold_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(4.0F, 8.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
//    // unused crates
//    public static final RegistryObject<Block> DIAMOND_CRATE = BLOCKS.register("diamond_crate",
//            () -> new Block(BlockBehaviour.Properties.of()
//                    .mapColor(MapColor.DIAMOND)
//                    .strength(4.0F, 8.0F)
//                    .sound(PUSoundsTypes.CRYSTAL_CRATE)));
//    public static final RegistryObject<Block> EMERALD_CRATE = BLOCKS.register("emerald_crate",
//            () -> new Block(BlockBehaviour.Properties.of()
//                    .mapColor(MapColor.EMERALD)
//                    .strength(4.0F, 8.0F)
//                    .sound(PUSoundsTypes.CRYSTAL_CRATE)));
    // reinforced crates
    public static final RegistryObject<Block> REINFORCED_COBBLESTONE_CRATE = BLOCKS.register("reinforced_cobblestone_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4.0F, 16.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> REINFORCED_COBBLED_DEEPSLATE_CRATE = BLOCKS.register("reinforced_cobbled_deepslate_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLED_DEEPSLATE)
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(4.0F, 16.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> REINFORCED_ANDESITE_CRATE = BLOCKS.register("reinforced_andesite_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4.0F, 16.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> REINFORCED_DIORITE_CRATE = BLOCKS.register("reinforced_diorite_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(4.0F, 16.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> REINFORCED_GRANITE_CRATE = BLOCKS.register("reinforced_granite_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT)
                    .strength(4.0F, 16.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> REINFORCED_TUFF_CRATE = BLOCKS.register("reinforced_tuff_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .strength(4.0F, 16.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> REINFORCED_BLACKSTONE_CRATE = BLOCKS.register("reinforced_blackstone_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(4.0F, 16.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> REINFORCED_BASALT_CRATE = BLOCKS.register("reinforced_basalt_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(4.0F, 16.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    public static final RegistryObject<Block> REINFORCED_NETHERRACK_CRATE = BLOCKS.register("reinforced_netherrack_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NETHER)
                    .strength(4.0F, 16.0F)
                    .sound(PUSoundsTypes.RESOURCE_CRATE)));
    // misc crates
    //TODO: Implement feature where it explodes when lit on fire
    public static final RegistryObject<Block> GUNPOWDER_CRATE = BLOCKS.register("gunpowder_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.GUNPOWDER_CRATE)));
    // crate lids
    public static RegistryObject<CrateLidBlock> CRATE_LID = BLOCKS.register("crate_lid",
            () -> new CrateLidBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1.0F, 4.0F)));
    public static RegistryObject<CrateLidBlock> REINFORCED_CRATE_LID = BLOCKS.register("reinforced_crate_lid",
            () -> new CrateLidBlock(BlockBehaviour.Properties.copy(CRATE_LID.get())
                    .strength(2.0F, 8.0F)));
    // produce crates
    // TODO: Give these unique map colors/sounds
    public static RegistryObject<Block> GOLDEN_CARROT_CRATE = BLOCKS.register("golden_carrot_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    public static RegistryObject<Block> EGG_CRATE = BLOCKS.register("egg_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    public static RegistryObject<Block> RED_MUSHROOM_CRATE = BLOCKS.register("red_mushroom_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    public static RegistryObject<Block> BROWN_MUSHROOM_CRATE = BLOCKS.register("brown_mushroom_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)));
    // produce baskets
    public static RegistryObject<Block> SWEET_BERRY_BASKET = BLOCKS.register("sweet_berry_basket",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_MAGENTA)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.BERRY_BASKET)));
    public static RegistryObject<Block> GLOW_BERRY_BASKET = BLOCKS.register("glow_berry_basket",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.BERRY_BASKET)
                    .lightLevel((blockState) -> 6)));
    public static RegistryObject<Block> APPLE_BASKET = BLOCKS.register("apple_basket",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.APPLE_BASKET)));
    public static RegistryObject<Block> GOLDEN_APPLE_BASKET = BLOCKS.register("golden_apple_basket",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.APPLE_BASKET)));
    // fish barrels
    // TODO: Implement feature where they slowly rot and spawn flies around them
    public static RegistryObject<Block> COD_BARREL = BLOCKS.register("cod_barrel",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(2.0F, 3.0F)
                    .sound(PUSoundsTypes.FISH_BARREL)));
    public static RegistryObject<Block> SALMON_BARREL = BLOCKS.register("salmon_barrel",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BARREL)
                    .strength(2.0F, 3.0F)
                    .sound(PUSoundsTypes.FISH_BARREL)));
    // material bags
    public static RegistryObject<Block> COCOA_BEAN_BAG = BLOCKS.register("cocoa_bean_bag",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.PRODUCE_BAG)));
    public static RegistryObject<Block> SUGAR_BAG = BLOCKS.register("sugar_bag",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.POWDER_BAG)));
    public static RegistryObject<Block> NETHER_WART_BAG = BLOCKS.register("nether_wart_bag",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.PRODUCE_BAG)));
    public static RegistryObject<Block> GLOWSTONE_DUST_BAG = BLOCKS.register("glowstone_dust_bag",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.POWDER_BAG)
                    .lightLevel((blockState) -> 8)));
    public static RegistryObject<Block> ENDER_PEARL_BAG = BLOCKS.register("ender_pearl_bag",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.GLASS_BAG)));
    // resource bags
    public static RegistryObject<Block> DIRT_BAG = BLOCKS.register("dirt_bag",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.RESOURCE_BAG_DIRT)));
    public static RegistryObject<Block> ROOTED_DIRT_BAG = BLOCKS.register("rooted_dirt_bag",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.RESOURCE_BAG_DIRT)));
    public static RegistryObject<Block> COARSE_DIRT_BAG = BLOCKS.register("coarse_dirt_bag",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.RESOURCE_BAG_GRAVEL)));
    public static RegistryObject<Block> GRAVEL_BAG = BLOCKS.register("gravel_bag",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRAVEL)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.RESOURCE_BAG_GRAVEL)));
    // brick piles
    public static RegistryObject<HorizontalBlock> BRICK_PILE = BLOCKS.register("brick_pile",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)
                    .strength(2.0F, 6.0F)));
    public static RegistryObject<HorizontalBlock> NETHER_BRICK_PILE = BLOCKS.register("nether_brick_pile",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)
                    .strength(2.0F, 6.0F)));
    public static RegistryObject<HorizontalBlock> STONE_PILE = BLOCKS.register("stone_pile",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2.0F, 6.0F)));
    public static RegistryObject<HorizontalBlock> DEEPSLATE_PILE = BLOCKS.register("deepslate_pile",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> CALCITE_PILE = BLOCKS.register("calcite_pile",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.CALCITE)
                    .strength(2.0F, 6.0F)));
    // pallets
    public static RegistryObject<PalletBlock> PALLET = BLOCKS.register("pallet",
            () -> new PalletBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static RegistryObject<HorizontalBlock> BRICK_PALLET = BLOCKS.register("brick_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> NETHER_BRICK_PALLET = BLOCKS.register("nether_brick_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> STONE_PALLET = BLOCKS.register("stone_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> DEEPSLATE_PALLET = BLOCKS.register("deepslate_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4.0F, 24.0F)));
    public static RegistryObject<HorizontalBlock> CALCITE_PALLET = BLOCKS.register("calcite_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.CALCITE)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> COPPER_PALLET = BLOCKS.register("copper_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> IRON_PALLET = BLOCKS.register("iron_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> GOLD_PALLET = BLOCKS.register("gold_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> DIAMOND_PALLET = BLOCKS.register("diamond_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> EMERALD_PALLET = BLOCKS.register("emerald_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<HorizontalBlock> NETHERITE_PALLET = BLOCKS.register("netherite_pallet",
            () -> new HorizontalBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)
                    .strength(4.0F, 1200.0F)));
    // book bundles
    public static RegistryObject<BookBundleBlock> BOOK_BUNDLE = BLOCKS.register("book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> WHITE_BOOK_BUNDLE = BLOCKS.register("white_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> LIGHT_GRAY_BOOK_BUNDLE = BLOCKS.register("light_gray_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> GRAY_BOOK_BUNDLE = BLOCKS.register("gray_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> BLACK_BOOK_BUNDLE = BLOCKS.register("black_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> BROWN_BOOK_BUNDLE = BLOCKS.register("brown_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> RED_BOOK_BUNDLE = BLOCKS.register("red_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.RED_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> ORANGE_BOOK_BUNDLE = BLOCKS.register("orange_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.ORANGE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> YELLOW_BOOK_BUNDLE = BLOCKS.register("yellow_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.YELLOW_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> LIME_BOOK_BUNDLE = BLOCKS.register("lime_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.LIME_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> GREEN_BOOK_BUNDLE = BLOCKS.register("green_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.GREEN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> CYAN_BOOK_BUNDLE = BLOCKS.register("cyan_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> LIGHT_BLUE_BOOK_BUNDLE = BLOCKS.register("light_blue_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> BLUE_BOOK_BUNDLE = BLOCKS.register("blue_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> PURPLE_BOOK_BUNDLE = BLOCKS.register("purple_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.PURPLE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> MAGENTA_BOOK_BUNDLE = BLOCKS.register("magenta_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.MAGENTA_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleBlock> PINK_BOOK_BUNDLE = BLOCKS.register("pink_book_bundle",
            () -> new BookBundleBlock(BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).sound(PUSoundsTypes.BOOKS)));
    // book bundle slabs
    public static RegistryObject<BookBundleSlabBlock> BOOK_BUNDLE_SLAB = BLOCKS.register("book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> WHITE_BOOK_BUNDLE_SLAB = BLOCKS.register("white_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> LIGHT_GRAY_BOOK_BUNDLE_SLAB = BLOCKS.register("light_gray_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> GRAY_BOOK_BUNDLE_SLAB = BLOCKS.register("gray_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> BLACK_BOOK_BUNDLE_SLAB = BLOCKS.register("black_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> BROWN_BOOK_BUNDLE_SLAB = BLOCKS.register("brown_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> RED_BOOK_BUNDLE_SLAB = BLOCKS.register("red_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.RED_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> ORANGE_BOOK_BUNDLE_SLAB = BLOCKS.register("orange_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.ORANGE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> YELLOW_BOOK_BUNDLE_SLAB = BLOCKS.register("yellow_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.YELLOW_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> LIME_BOOK_BUNDLE_SLAB = BLOCKS.register("lime_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.LIME_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> GREEN_BOOK_BUNDLE_SLAB = BLOCKS.register("green_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.GREEN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> CYAN_BOOK_BUNDLE_SLAB = BLOCKS.register("cyan_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> LIGHT_BLUE_BOOK_BUNDLE_SLAB = BLOCKS.register("light_blue_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> BLUE_BOOK_BUNDLE_SLAB = BLOCKS.register("blue_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> PURPLE_BOOK_BUNDLE_SLAB = BLOCKS.register("purple_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.PURPLE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> MAGENTA_BOOK_BUNDLE_SLAB = BLOCKS.register("magenta_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.MAGENTA_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookBundleSlabBlock> PINK_BOOK_BUNDLE_SLAB = BLOCKS.register("pink_book_bundle_slab",
            () -> new BookBundleSlabBlock(BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).sound(PUSoundsTypes.BOOKS)));
    // book piles
    public static RegistryObject<BookPileBlock> BOOK_PILE = BLOCKS.register("book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> WHITE_BOOK_PILE = BLOCKS.register("white_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> LIGHT_GRAY_BOOK_PILE = BLOCKS.register("light_gray_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> GRAY_BOOK_PILE = BLOCKS.register("gray_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> BLACK_BOOK_PILE = BLOCKS.register("black_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> BROWN_BOOK_PILE = BLOCKS.register("brown_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> RED_BOOK_PILE = BLOCKS.register("red_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.RED_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> ORANGE_BOOK_PILE = BLOCKS.register("orange_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.ORANGE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> YELLOW_BOOK_PILE = BLOCKS.register("yellow_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.YELLOW_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> LIME_BOOK_PILE = BLOCKS.register("lime_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.LIME_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> GREEN_BOOK_PILE = BLOCKS.register("green_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.GREEN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> CYAN_BOOK_PILE = BLOCKS.register("cyan_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> LIGHT_BLUE_BOOK_PILE = BLOCKS.register("light_blue_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> BLUE_BOOK_PILE = BLOCKS.register("blue_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> PURPLE_BOOK_PILE = BLOCKS.register("purple_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.PURPLE_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> MAGENTA_BOOK_PILE = BLOCKS.register("magenta_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.MAGENTA_WOOL).sound(PUSoundsTypes.BOOKS)));
    public static RegistryObject<BookPileBlock> PINK_BOOK_PILE = BLOCKS.register("pink_book_pile",
            () -> new BookPileBlock(BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).sound(PUSoundsTypes.BOOKS)));

}
