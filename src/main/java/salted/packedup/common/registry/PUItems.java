package salted.packedup.common.registry;

import com.google.common.collect.Sets;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import salted.packedup.PackedUp;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class PUItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PackedUp.MODID);
    public static LinkedHashSet<RegistryObject<Item>> TAB_ITEMS = Sets.newLinkedHashSet();
    // books
    public static final RegistryObject<Item> BOOK_BUNDLE = registerWithTab("book_bundle",
            () -> new BlockItem(PUBlocks.BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> BOOK_BUNDLE_SLAB = registerWithTab("book_bundle_slab",
            () -> new BlockItem(PUBlocks.BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> BOOK_PILE = registerWithTab("book_pile",
            () -> new BlockItem(PUBlocks.BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> WHITE_BOOK_BUNDLE = registerWithTab("white_book_bundle",
            () -> new BlockItem(PUBlocks.WHITE_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> WHITE_BOOK_BUNDLE_SLAB = registerWithTab("white_book_bundle_slab",
            () -> new BlockItem(PUBlocks.WHITE_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> WHITE_BOOK_PILE = registerWithTab("white_book_pile",
            () -> new BlockItem(PUBlocks.WHITE_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> LIGHT_GRAY_BOOK_BUNDLE = registerWithTab("light_gray_book_bundle",
            () -> new BlockItem(PUBlocks.LIGHT_GRAY_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> LIGHT_GRAY_BOOK_BUNDLE_SLAB = registerWithTab("light_gray_book_bundle_slab",
            () -> new BlockItem(PUBlocks.LIGHT_GRAY_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> LIGHT_GRAY_BOOK_PILE = registerWithTab("light_gray_book_pile",
            () -> new BlockItem(PUBlocks.LIGHT_GRAY_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> GRAY_BOOK_BUNDLE = registerWithTab("gray_book_bundle",
            () -> new BlockItem(PUBlocks.GRAY_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> GRAY_BOOK_BUNDLE_SLAB = registerWithTab("gray_book_bundle_slab",
            () -> new BlockItem(PUBlocks.GRAY_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> GRAY_BOOK_PILE = registerWithTab("gray_book_pile",
            () -> new BlockItem(PUBlocks.GRAY_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> BLACK_BOOK_BUNDLE = registerWithTab("black_book_bundle",
            () -> new BlockItem(PUBlocks.BLACK_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> BLACK_BOOK_BUNDLE_SLAB = registerWithTab("black_book_bundle_slab",
            () -> new BlockItem(PUBlocks.BLACK_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> BLACK_BOOK_PILE = registerWithTab("black_book_pile",
            () -> new BlockItem(PUBlocks.BLACK_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> BROWN_BOOK_BUNDLE = registerWithTab("brown_book_bundle",
            () -> new BlockItem(PUBlocks.BROWN_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> BROWN_BOOK_BUNDLE_SLAB = registerWithTab("brown_book_bundle_slab",
            () -> new BlockItem(PUBlocks.BROWN_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> BROWN_BOOK_PILE = registerWithTab("brown_book_pile",
            () -> new BlockItem(PUBlocks.BROWN_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> RED_BOOK_BUNDLE = registerWithTab("red_book_bundle",
            () -> new BlockItem(PUBlocks.RED_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> RED_BOOK_BUNDLE_SLAB = registerWithTab("red_book_bundle_slab",
            () -> new BlockItem(PUBlocks.RED_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> RED_BOOK_PILE = registerWithTab("red_book_pile",
            () -> new BlockItem(PUBlocks.RED_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> ORANGE_BOOK_BUNDLE = registerWithTab("orange_book_bundle",
            () -> new BlockItem(PUBlocks.ORANGE_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> ORANGE_BOOK_BUNDLE_SLAB = registerWithTab("orange_book_bundle_slab",
            () -> new BlockItem(PUBlocks.ORANGE_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> ORANGE_BOOK_PILE = registerWithTab("orange_book_pile",
            () -> new BlockItem(PUBlocks.ORANGE_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> YELLOW_BOOK_BUNDLE = registerWithTab("yellow_book_bundle",
            () -> new BlockItem(PUBlocks.YELLOW_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> YELLOW_BOOK_BUNDLE_SLAB = registerWithTab("yellow_book_bundle_slab",
            () -> new BlockItem(PUBlocks.YELLOW_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> YELLOW_BOOK_PILE = registerWithTab("yellow_book_pile",
            () -> new BlockItem(PUBlocks.YELLOW_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> LIME_BOOK_BUNDLE = registerWithTab("lime_book_bundle",
            () -> new BlockItem(PUBlocks.LIME_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> LIME_BOOK_BUNDLE_SLAB = registerWithTab("lime_book_bundle_slab",
            () -> new BlockItem(PUBlocks.LIME_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> LIME_BOOK_PILE = registerWithTab("lime_book_pile",
            () -> new BlockItem(PUBlocks.LIME_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> GREEN_BOOK_BUNDLE = registerWithTab("green_book_bundle",
            () -> new BlockItem(PUBlocks.GREEN_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> GREEN_BOOK_BUNDLE_SLAB = registerWithTab("green_book_bundle_slab",
            () -> new BlockItem(PUBlocks.GREEN_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> GREEN_BOOK_PILE = registerWithTab("green_book_pile",
            () -> new BlockItem(PUBlocks.GREEN_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> CYAN_BOOK_BUNDLE = registerWithTab("cyan_book_bundle",
            () -> new BlockItem(PUBlocks.CYAN_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> CYAN_BOOK_BUNDLE_SLAB = registerWithTab("cyan_book_bundle_slab",
            () -> new BlockItem(PUBlocks.CYAN_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> CYAN_BOOK_PILE = registerWithTab("cyan_book_pile",
            () -> new BlockItem(PUBlocks.CYAN_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> LIGHT_BLUE_BOOK_BUNDLE = registerWithTab("light_blue_book_bundle",
            () -> new BlockItem(PUBlocks.LIGHT_BLUE_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> LIGHT_BLUE_BOOK_BUNDLE_SLAB = registerWithTab("light_blue_book_bundle_slab",
            () -> new BlockItem(PUBlocks.LIGHT_BLUE_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> LIGHT_BLUE_BOOK_PILE = registerWithTab("light_blue_book_pile",
            () -> new BlockItem(PUBlocks.LIGHT_BLUE_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> BLUE_BOOK_BUNDLE = registerWithTab("blue_book_bundle",
            () -> new BlockItem(PUBlocks.BLUE_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> BLUE_BOOK_BUNDLE_SLAB = registerWithTab("blue_book_bundle_slab",
            () -> new BlockItem(PUBlocks.BLUE_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> BLUE_BOOK_PILE = registerWithTab("blue_book_pile",
            () -> new BlockItem(PUBlocks.BLUE_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> PURPLE_BOOK_BUNDLE = registerWithTab("purple_book_bundle",
            () -> new BlockItem(PUBlocks.PURPLE_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> PURPLE_BOOK_BUNDLE_SLAB = registerWithTab("purple_book_bundle_slab",
            () -> new BlockItem(PUBlocks.PURPLE_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> PURPLE_BOOK_PILE = registerWithTab("purple_book_pile",
            () -> new BlockItem(PUBlocks.PURPLE_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> MAGENTA_BOOK_BUNDLE = registerWithTab("magenta_book_bundle",
            () -> new BlockItem(PUBlocks.MAGENTA_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> MAGENTA_BOOK_BUNDLE_SLAB = registerWithTab("magenta_book_bundle_slab",
            () -> new BlockItem(PUBlocks.MAGENTA_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> MAGENTA_BOOK_PILE = registerWithTab("magenta_book_pile",
            () -> new BlockItem(PUBlocks.MAGENTA_BOOK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> PINK_BOOK_BUNDLE = registerWithTab("pink_book_bundle",
            () -> new BlockItem(PUBlocks.PINK_BOOK_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> PINK_BOOK_BUNDLE_SLAB = registerWithTab("pink_book_bundle_slab",
            () -> new BlockItem(PUBlocks.PINK_BOOK_BUNDLE_SLAB.get(), basicItem()));
    public static final RegistryObject<Item> PINK_BOOK_PILE = registerWithTab("pink_book_pile",
            () -> new BlockItem(PUBlocks.PINK_BOOK_PILE.get(), basicItem()));
    // baskets
    public static final RegistryObject<Item> SWEET_BERRY_BASKET = registerWithTab("sweet_berry_basket",
            () -> new BlockItem(PUBlocks.SWEET_BERRY_BASKET.get(), basicItem()));
    public static final RegistryObject<Item> GLOW_BERRY_BASKET = registerWithTab("glow_berry_basket",
            () -> new BlockItem(PUBlocks.GLOW_BERRY_BASKET.get(), basicItem()));
    public static final RegistryObject<Item> APPLE_BASKET = registerWithTab("apple_basket",
            () -> new BlockItem(PUBlocks.APPLE_BASKET.get(), basicItem()));
    public static final RegistryObject<Item> GOLDEN_APPLE_BASKET = registerWithTab("golden_apple_basket",
            () -> new BlockItem(PUBlocks.GOLDEN_APPLE_BASKET.get(), basicItem()));
    // barrels
    public static final RegistryObject<Item> COD_BARREL = registerWithTab("cod_barrel",
            () -> new BlockItem(PUBlocks.COD_BARREL.get(), basicItem()));
    public static final RegistryObject<Item> SALMON_BARREL = registerWithTab("salmon_barrel",
            () -> new BlockItem(PUBlocks.SALMON_BARREL.get(), basicItem()));
    // produce bags
    public static final RegistryObject<Item> COCOA_BEAN_BAG = registerWithTab("cocoa_bean_bag",
            () -> new BlockItem(PUBlocks.COCOA_BEAN_BAG.get(), basicItem()));
    public static final RegistryObject<Item> SUGAR_BAG = registerWithTab("sugar_bag",
            () -> new BlockItem(PUBlocks.SUGAR_BAG.get(), basicItem()));
    public static final RegistryObject<Item> NETHER_WART_BAG = registerWithTab("nether_wart_bag",
            () -> new BlockItem(PUBlocks.NETHER_WART_BAG.get(), basicItem()));
    // misc bags
    public static final RegistryObject<Item> GLOWSTONE_DUST_BAG = registerWithTab("glowstone_dust_bag",
            () -> new BlockItem(PUBlocks.GLOWSTONE_DUST_BAG.get(), basicItem()));
    public static final RegistryObject<Item> ENDER_PEARL_BAG = registerWithTab("ender_pearl_bag",
            () -> new BlockItem(PUBlocks.ENDER_PEARL_BAG.get(), basicItem()));
    // turf
    public static final RegistryObject<Item> GRASS_TURF = registerWithTab("grass_turf",
            () -> new BlockItem(PUBlocks.GRASS_TURF.get(), basicItem()));
    public static final RegistryObject<Item> GRASS_TURF_LAYER = registerWithTab("grass_turf_layer",
            () -> new BlockItem(PUBlocks.GRASS_TURF_LAYER.get(), basicItem()));
    public static final RegistryObject<Item> PODZOL_TURF = registerWithTab("podzol_turf",
            () -> new BlockItem(PUBlocks.PODZOL_TURF.get(), basicItem()));
    public static final RegistryObject<Item> PODZOL_TURF_LAYER = registerWithTab("podzol_turf_layer",
            () -> new BlockItem(PUBlocks.PODZOL_TURF_LAYER.get(), basicItem()));
    public static final RegistryObject<Item> MYCELIUM_TURF = registerWithTab("mycelium_turf",
            () -> new BlockItem(PUBlocks.MYCELIUM_TURF.get(), basicItem()));
    public static final RegistryObject<Item> MYCELIUM_TURF_LAYER = registerWithTab("mycelium_turf_layer",
            () -> new BlockItem(PUBlocks.MYCELIUM_TURF_LAYER.get(), basicItem()));
    // grass bundle/thatch
    public static final RegistryObject<Item> GRASS_BUNDLE = registerWithTab("grass_bundle",
            () -> new BlockItem(PUBlocks.GRASS_BUNDLE.get(), basicItem()));
    public static final RegistryObject<Item> GRASS_THATCH = registerWithTab("grass_thatch",
            () -> new BlockItem(PUBlocks.GRASS_THATCH.get(), basicItem()));
    // resource bags
    public static final RegistryObject<Item> DIRT_BAG = registerWithTab("dirt_bag",
            () -> new BlockItem(PUBlocks.DIRT_BAG.get(), basicItem()));
    public static final RegistryObject<Item> ROOTED_DIRT_BAG = registerWithTab("rooted_dirt_bag",
            () -> new BlockItem(PUBlocks.ROOTED_DIRT_BAG.get(), basicItem()));
    public static final RegistryObject<Item> COARSE_DIRT_BAG = registerWithTab("coarse_dirt_bag",
            () -> new BlockItem(PUBlocks.COARSE_DIRT_BAG.get(), basicItem()));
    public static final RegistryObject<Item> GRAVEL_BAG = registerWithTab("gravel_bag",
            () -> new BlockItem(PUBlocks.GRAVEL_BAG.get(), basicItem()));
    // produce crates
    public static final RegistryObject<Item> GOLDEN_CARROT_CRATE = registerWithTab("golden_carrot_crate",
            () -> new BlockItem(PUBlocks.GOLDEN_CARROT_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> EGG_CRATE = registerWithTab("egg_crate",
            () -> new BlockItem(PUBlocks.EGG_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> RED_MUSHROOM_CRATE = registerWithTab("red_mushroom_crate",
            () -> new BlockItem(PUBlocks.RED_MUSHROOM_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> BROWN_MUSHROOM_CRATE = registerWithTab("brown_mushroom_crate",
            () -> new BlockItem(PUBlocks.BROWN_MUSHROOM_CRATE.get(), basicItem()));
    // crate lids
    public static final RegistryObject<Item> CRATE_LID = registerWithTab("crate_lid",
            () -> new BlockItem(PUBlocks.CRATE_LID.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_CRATE_LID = registerWithTab("reinforced_crate_lid",
            () -> new BlockItem(PUBlocks.REINFORCED_CRATE_LID.get(), basicItem()));
    // misc crates
    public static final RegistryObject<Item> GUNPOWDER_CRATE = registerWithTab("gunpowder_crate",
            () -> new BlockItem(PUBlocks.GUNPOWDER_CRATE.get(), basicItem()));
    // resource crates
    public static final RegistryObject<Item> COBBLESTONE_CRATE = registerWithTab("cobblestone_crate",
            () -> new BlockItem(PUBlocks.COBBLESTONE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_COBBLESTONE_CRATE = registerWithTab("reinforced_cobblestone_crate",
            () -> new BlockItem(PUBlocks.REINFORCED_COBBLESTONE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> COBBLED_DEEPSLATE_CRATE = registerWithTab("cobbled_deepslate_crate",
            () -> new BlockItem(PUBlocks.COBBLED_DEEPSLATE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_COBBLED_DEEPSLATE_CRATE = registerWithTab("reinforced_cobbled_deepslate_crate",
            () -> new BlockItem(PUBlocks.REINFORCED_COBBLED_DEEPSLATE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> ANDESITE_CRATE = registerWithTab("andesite_crate",
            () -> new BlockItem(PUBlocks.ANDESITE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_ANDESITE_CRATE = registerWithTab("reinforced_andesite_crate",
            () -> new BlockItem(PUBlocks.REINFORCED_ANDESITE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> DIORITE_CRATE = registerWithTab("diorite_crate",
            () -> new BlockItem(PUBlocks.DIORITE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_DIORITE_CRATE = registerWithTab("reinforced_diorite_crate",
            () -> new BlockItem(PUBlocks.REINFORCED_DIORITE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> GRANITE_CRATE = registerWithTab("granite_crate",
            () -> new BlockItem(PUBlocks.GRANITE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_GRANITE_CRATE = registerWithTab("reinforced_granite_crate",
            () -> new BlockItem(PUBlocks.REINFORCED_GRANITE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> TUFF_CRATE = registerWithTab("tuff_crate",
            () -> new BlockItem(PUBlocks.TUFF_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_TUFF_CRATE = registerWithTab("reinforced_tuff_crate",
            () -> new BlockItem(PUBlocks.REINFORCED_TUFF_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> BLACKSTONE_CRATE = registerWithTab("blackstone_crate",
            () -> new BlockItem(PUBlocks.BLACKSTONE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_BLACKSTONE_CRATE = registerWithTab("reinforced_blackstone_crate",
            () -> new BlockItem(PUBlocks.REINFORCED_BLACKSTONE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> BASALT_CRATE = registerWithTab("basalt_crate",
            () -> new BlockItem(PUBlocks.BASALT_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_BASALT_CRATE = registerWithTab("reinforced_basalt_crate",
            () -> new BlockItem(PUBlocks.REINFORCED_BASALT_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> NETHERRACK_CRATE = registerWithTab("netherrack_crate",
            () -> new BlockItem(PUBlocks.NETHERRACK_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> REINFORCED_NETHERRACK_CRATE = registerWithTab("reinforced_netherrack_crate",
            () -> new BlockItem(PUBlocks.REINFORCED_NETHERRACK_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> QUARTZ_CRATE = registerWithTab("quartz_crate",
            () -> new BlockItem(PUBlocks.QUARTZ_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> AMETHYST_CRATE = registerWithTab("amethyst_crate",
            () -> new BlockItem(PUBlocks.AMETHYST_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> ECHO_SHARD_CRATE = registerWithTab("echo_shard_crate",
            () -> new BlockItem(PUBlocks.ECHO_SHARD_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> RAW_COPPER_CRATE = registerWithTab("raw_copper_crate",
            () -> new BlockItem(PUBlocks.RAW_COPPER_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> RAW_IRON_CRATE = registerWithTab("raw_iron_crate",
            () -> new BlockItem(PUBlocks.RAW_IRON_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> RAW_GOLD_CRATE = registerWithTab("raw_gold_crate",
            () -> new BlockItem(PUBlocks.RAW_GOLD_CRATE.get(), basicItem()));
//    // unused crates
//    public static final RegistryObject<Item> DIAMOND_CRATE = registerWithTab("diamond_crate",
//            () -> new BlockItem(PUBlocks.DIAMOND_CRATE.get(), basicItem()));
//    public static final RegistryObject<Item> EMERALD_CRATE = registerWithTab("emerald_crate",
//            () -> new BlockItem(PUBlocks.EMERALD_CRATE.get(), basicItem()));
    // piles/pallets
    public static final RegistryObject<Item> PALLET = registerWithTab("pallet",
            () -> new BlockItem(PUBlocks.PALLET.get(), basicItem()));
    public static final RegistryObject<Item> BRICK_PILE = registerWithTab("brick_pile",
            () -> new BlockItem(PUBlocks.BRICK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> BRICK_PALLET = registerWithTab("brick_pallet",
            () -> new BlockItem(PUBlocks.BRICK_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> NETHER_BRICK_PILE = registerWithTab("nether_brick_pile",
            () -> new BlockItem(PUBlocks.NETHER_BRICK_PILE.get(), basicItem()));
    public static final RegistryObject<Item> NETHER_BRICK_PALLET = registerWithTab("nether_brick_pallet",
            () -> new BlockItem(PUBlocks.NETHER_BRICK_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> STONE_PILE = registerWithTab("stone_pile",
            () -> new BlockItem(PUBlocks.STONE_PILE.get(), basicItem()));
    public static final RegistryObject<Item> STONE_PALLET = registerWithTab("stone_pallet",
            () -> new BlockItem(PUBlocks.STONE_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> DEEPSLATE_PILE = registerWithTab("deepslate_pile",
            () -> new BlockItem(PUBlocks.DEEPSLATE_PILE.get(), basicItem()));
    public static final RegistryObject<Item> DEEPSLATE_PALLET = registerWithTab("deepslate_pallet",
            () -> new BlockItem(PUBlocks.DEEPSLATE_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> CALCITE_PILE = registerWithTab("calcite_pile",
            () -> new BlockItem(PUBlocks.CALCITE_PILE.get(), basicItem()));
    public static final RegistryObject<Item> CALCITE_PALLET = registerWithTab("calcite_pallet",
            () -> new BlockItem(PUBlocks.CALCITE_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> COPPER_PALLET = registerWithTab("copper_pallet",
            () -> new BlockItem(PUBlocks.COPPER_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> IRON_PALLET = registerWithTab("iron_pallet",
            () -> new BlockItem(PUBlocks.IRON_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> GOLD_PALLET = registerWithTab("gold_pallet",
            () -> new BlockItem(PUBlocks.GOLD_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> DIAMOND_PALLET = registerWithTab("diamond_pallet",
            () -> new BlockItem(PUBlocks.DIAMOND_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> EMERALD_PALLET = registerWithTab("emerald_pallet",
            () -> new BlockItem(PUBlocks.EMERALD_PALLET.get(), basicItem()));
    public static final RegistryObject<Item> NETHERITE_PALLET = registerWithTab("netherite_pallet",
            () -> new BlockItem(PUBlocks.NETHERITE_PALLET.get(), basicItem().fireResistant()));

    // thanks to vectorwing for this method of item registration
    public static RegistryObject<Item> registerWithTab(final String name, final Supplier<Item> supplier) {
        RegistryObject<Item> block = ITEMS.register(name, supplier);
        TAB_ITEMS.add(block);
        return block;
    }

    private static Item.Properties basicItem() {
        return new Item.Properties();
    }
}
