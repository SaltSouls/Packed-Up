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
    // bags
    public static final RegistryObject<Item> COCOA_BEAN_BAG = registerWithTab("cocoa_bean_bag",
            () -> new BlockItem(PUBlocks.COCOA_BEAN_BAG.get(), basicItem()));
    public static final RegistryObject<Item> SUGAR_BAG = registerWithTab("sugar_bag",
            () -> new BlockItem(PUBlocks.SUGAR_BAG.get(), basicItem()));
    public static final RegistryObject<Item> NETHER_WART_BAG = registerWithTab("nether_wart_bag",
            () -> new BlockItem(PUBlocks.NETHER_WART_BAG.get(), basicItem()));
    public static final RegistryObject<Item> GLOWSTONE_DUST_BAG = registerWithTab("glowstone_dust_bag",
            () -> new BlockItem(PUBlocks.GLOWSTONE_DUST_BAG.get(), basicItem()));
    public static final RegistryObject<Item> DIRT_BAG = registerWithTab("dirt_bag",
            () -> new BlockItem(PUBlocks.DIRT_BAG.get(), basicItem()));
    public static final RegistryObject<Item> ROOTED_DIRT_BAG = registerWithTab("rooted_dirt_bag",
            () -> new BlockItem(PUBlocks.ROOTED_DIRT_BAG.get(), basicItem()));
    public static final RegistryObject<Item> COARSE_DIRT_BAG = registerWithTab("coarse_dirt_bag",
            () -> new BlockItem(PUBlocks.COARSE_DIRT_BAG.get(), basicItem()));
    public static final RegistryObject<Item> GRAVEL_BAG = registerWithTab("gravel_bag",
            () -> new BlockItem(PUBlocks.GRAVEL_BAG.get(), basicItem()));
    // crates
    public static final RegistryObject<Item> GOLDEN_CARROT_CRATE = registerWithTab("golden_carrot_crate",
            () -> new BlockItem(PUBlocks.GOLDEN_CARROT_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> EGG_CRATE = registerWithTab("egg_crate",
            () -> new BlockItem(PUBlocks.EGG_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> COBBLESTONE_CRATE = registerWithTab("cobblestone_crate",
            () -> new BlockItem(PUBlocks.COBBLESTONE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> COBBLED_DEEPSLATE_CRATE = registerWithTab("cobbled_deepslate_crate",
            () -> new BlockItem(PUBlocks.COBBLED_DEEPSLATE_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> RAW_COPPER_CRATE = registerWithTab("raw_copper_crate",
            () -> new BlockItem(PUBlocks.RAW_COPPER_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> RAW_IRON_CRATE = registerWithTab("raw_iron_crate",
            () -> new BlockItem(PUBlocks.RAW_IRON_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> RAW_GOLD_CRATE = registerWithTab("raw_gold_crate",
            () -> new BlockItem(PUBlocks.RAW_GOLD_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> GUNPOWDER_CRATE = registerWithTab("gunpowder_crate",
            () -> new BlockItem(PUBlocks.GUNPOWDER_CRATE.get(), basicItem()));

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
