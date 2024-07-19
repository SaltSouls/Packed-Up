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
    public static final RegistryObject<Item> RAW_COPPER_CRATE = registerWithTab("raw_copper_crate",
            () -> new BlockItem(PUBlocks.RAW_COPPER_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> RAW_IRON_CRATE = registerWithTab("raw_iron_crate",
            () -> new BlockItem(PUBlocks.RAW_IRON_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> RAW_GOLD_CRATE = registerWithTab("raw_gold_crate",
            () -> new BlockItem(PUBlocks.RAW_GOLD_CRATE.get(), basicItem()));
    // piles/pallets
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
