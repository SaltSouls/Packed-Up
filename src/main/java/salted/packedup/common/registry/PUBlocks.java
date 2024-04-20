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
    //TODO: Implement feature where it explodes when lit on fire
    public static final RegistryObject<Block> GUNPOWDER_CRATE = BLOCKS.register("gunpowder_crate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(1.0F)
                    .sound(PUSoundsTypes.GUNPOWDER_CRATE)));
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
}
