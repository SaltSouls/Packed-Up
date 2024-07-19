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
import salted.packedup.common.block.CrateLidBlock;
import salted.packedup.common.block.ResourcePalletBlock;
import salted.packedup.common.block.ResourcePileBlock;

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
    public static RegistryObject<ResourcePileBlock> BRICK_PILE = BLOCKS.register("brick_pile",
            () -> new ResourcePileBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)
                    .strength(2.0F, 6.0F)));
    public static RegistryObject<ResourcePileBlock> NETHER_BRICK_PILE = BLOCKS.register("nether_brick_pile",
            () -> new ResourcePileBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)
                    .strength(2.0F, 6.0F)));
    public static RegistryObject<ResourcePileBlock> STONE_PILE = BLOCKS.register("stone_pile",
            () -> new ResourcePileBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(2.0F, 6.0F)));
    public static RegistryObject<ResourcePileBlock> DEEPSLATE_PILE = BLOCKS.register("deepslate_pile",
            () -> new ResourcePileBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<ResourcePileBlock> CALCITE_PILE = BLOCKS.register("calcite_pile",
            () -> new ResourcePileBlock(BlockBehaviour.Properties.copy(Blocks.CALCITE)
                    .strength(2.0F, 6.0F)));
    // pallets
    public static RegistryObject<ResourcePalletBlock> BRICK_PALLET = BLOCKS.register("brick_pallet",
            () -> new ResourcePalletBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<ResourcePalletBlock> NETHER_BRICK_PALLET = BLOCKS.register("nether_brick_pallet",
            () -> new ResourcePalletBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<ResourcePileBlock> STONE_PALLET = BLOCKS.register("stone_pallet",
            () -> new ResourcePileBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<ResourcePileBlock> DEEPSLATE_PALLET = BLOCKS.register("deepslate_pallet",
            () -> new ResourcePileBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(4.0F, 24.0F)));
    public static RegistryObject<ResourcePileBlock> CALCITE_PALLET = BLOCKS.register("calcite_pallet",
            () -> new ResourcePileBlock(BlockBehaviour.Properties.copy(Blocks.CALCITE)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<ResourcePalletBlock> COPPER_PALLET = BLOCKS.register("copper_pallet",
            () -> new ResourcePalletBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<ResourcePalletBlock> IRON_PALLET = BLOCKS.register("iron_pallet",
            () -> new ResourcePalletBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<ResourcePalletBlock> GOLD_PALLET = BLOCKS.register("gold_pallet",
            () -> new ResourcePalletBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)
                    .strength(4.0F, 12.0F)));
    public static RegistryObject<ResourcePalletBlock> NETHERITE_PALLET = BLOCKS.register("netherite_pallet",
            () -> new ResourcePalletBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK)
                    .strength(4.0F, 1200.0F)));
}
