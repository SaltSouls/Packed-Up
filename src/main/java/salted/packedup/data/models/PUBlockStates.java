package salted.packedup.data.models;

import com.google.common.collect.Sets;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.data.ExistingFileHelper;
import salted.packedup.common.block.CrateLidBlock;
import salted.packedup.common.block.HorizontalBlock;
import salted.packedup.common.block.HorizontalSlabBlock;
import salted.packedup.common.block.QuarterSlabBlock;
import salted.packedup.common.registry.PUBlocks;
import salted.packedup.data.models.builders.PUBlockBuilder;

import java.util.Set;

import static salted.packedup.data.utils.NameUtils.*;


public class PUBlockStates extends PUBlockBuilder {

    public PUBlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    // model/block state registration
    @Override
    protected void registerStatesAndModels() {
        // crate lids
        Set<CrateLidBlock> lids = Sets.newHashSet(
                PUBlocks.CRATE_LID.get(),
                PUBlocks.REINFORCED_CRATE_LID.get()
        );
        for (CrateLidBlock lid : lids) {
            this.simpleCrateLid(lid, BlockStateProperties.WATERLOGGED);
        }

        // resource crates
        Set<Block> resourceCrates = Sets.newHashSet(
                PUBlocks.COBBLESTONE_CRATE.get(),
                PUBlocks.COBBLED_DEEPSLATE_CRATE.get(),
                PUBlocks.ANDESITE_CRATE.get(),
                PUBlocks.DIORITE_CRATE.get(),
                PUBlocks.GRANITE_CRATE.get(),
                PUBlocks.TUFF_CRATE.get(),
                PUBlocks.BLACKSTONE_CRATE.get(),
                PUBlocks.BASALT_CRATE.get(),
                PUBlocks.NETHERRACK_CRATE.get(),
                PUBlocks.RAW_COPPER_CRATE.get(),
                PUBlocks.RAW_IRON_CRATE.get(),
                PUBlocks.RAW_GOLD_CRATE.get()
        );
        for (Block crate : resourceCrates) {
            this.simpleBlock(crate, withRandomRotation(resourceCrate(crate, false)));
        }
        // reinforced
        Set<Block> reinforcedCrates = Sets.newHashSet(
                PUBlocks.REINFORCED_COBBLESTONE_CRATE.get(),
                PUBlocks.REINFORCED_COBBLED_DEEPSLATE_CRATE.get(),
                PUBlocks.REINFORCED_ANDESITE_CRATE.get(),
                PUBlocks.REINFORCED_DIORITE_CRATE.get(),
                PUBlocks.REINFORCED_GRANITE_CRATE.get(),
                PUBlocks.REINFORCED_TUFF_CRATE.get(),
                PUBlocks.REINFORCED_BLACKSTONE_CRATE.get(),
                PUBlocks.REINFORCED_BASALT_CRATE.get(),
                PUBlocks.REINFORCED_NETHERRACK_CRATE.get()
        );
        for (Block crate : reinforcedCrates) {
            this.simpleBlock(crate, withRandomRotation(reinforcedCrate(crate, false)));
        }

        // misc crates
        Set<Block> basicCrates = Sets.newHashSet(
                PUBlocks.GUNPOWDER_CRATE.get(),
                PUBlocks.QUARTZ_CRATE.get(),
                PUBlocks.AMETHYST_CRATE.get(),
                PUBlocks.ECHO_SHARD_CRATE.get()
        );
        for (Block crate : basicCrates) {
            this.simpleBlock(crate, withRandomRotation(resourceCrate(crate, true)));
        }

        // produce crates
        Set<Block> produceCrates = Sets.newHashSet(
                PUBlocks.CARROT_CRATE.get(),
                PUBlocks.GOLDEN_CARROT_CRATE.get(),
                PUBlocks.POTATO_CRATE.get(),
                PUBlocks.BEETROOT_CRATE.get(),
                PUBlocks.EGG_CRATE.get()
        );
        for (Block crate : produceCrates) {
            this.simpleCrate(crate);
        }

        // mushroom crates
        Set<Block> mushroomCrates = Sets.newHashSet(
                PUBlocks.RED_MUSHROOM_CRATE.get(),
                PUBlocks.BROWN_MUSHROOM_CRATE.get(),
                PUBlocks.CRIMSON_FUNGUS_CRATE.get(),
                PUBlocks.WARPED_FUNGUS_CRATE.get()
        );
        for (Block crate : mushroomCrates) {
            this.mushroomCrate(crate);
        }

        // resource bags
        Set<Block> resourceBags = Sets.newHashSet(
                PUBlocks.DIRT_BAG.get(),
                PUBlocks.ROOTED_DIRT_BAG.get(),
                PUBlocks.COARSE_DIRT_BAG.get(),
                PUBlocks.GRAVEL_BAG.get()
        );
        for (Block bag : resourceBags) {
            this.simpleBlock(bag, resourceBag(bag, false));
        }

        // produce baskets
        this.simpleBasket(PUBlocks.APPLE_BASKET.get());
        this.simpleBasket(PUBlocks.GOLDEN_APPLE_BASKET.get());
        this.simpleBasket(PUBlocks.SWEET_BERRY_BASKET.get());
        this.simpleBlock(PUBlocks.GLOW_BERRY_BASKET.get(), existingModel(PUBlocks.GLOW_BERRY_BASKET.get()));

        // material bags
        this.simpleBag(PUBlocks.COCOA_BEAN_BAG.get());
        this.simpleBag(PUBlocks.SUGAR_BAG.get());
        this.simpleBag(PUBlocks.NETHER_WART_BAG.get());
        this.simpleBlock(PUBlocks.GLOWSTONE_DUST_BAG.get(), existingModel(PUBlocks.GLOWSTONE_DUST_BAG.get()));
        this.simpleBag(PUBlocks.ENDER_PEARL_BAG.get());

        // fish barrels
        this.simpleBarrel(PUBlocks.COD_BARREL.get());
        this.simpleBarrel(PUBlocks.SALMON_BARREL.get());

        // brick piles
        Set<Block> resourcePiles = Sets.newHashSet(
                PUBlocks.BRICK_PILE.get(),
                PUBlocks.NETHER_BRICK_PILE.get(),
                PUBlocks.STONE_PILE.get(),
                PUBlocks.DEEPSLATE_PILE.get(),
                PUBlocks.CALCITE_PILE.get()
        );
        for (Block piles : resourcePiles) {
            this.horizontalBlock(piles, resourcePile(piles));
        }

        // pallet
        this.horizontalQuarterSlabBlock(PUBlocks.PALLET.get(), BlockStateProperties.WATERLOGGED);

        // resource pallets
        Set<Block> resourcePallets = Sets.newHashSet(
                PUBlocks.BRICK_PALLET.get(),
                PUBlocks.NETHER_BRICK_PALLET.get(),
                PUBlocks.STONE_PALLET.get(),
                PUBlocks.DEEPSLATE_PALLET.get(),
                PUBlocks.CALCITE_PALLET.get(),
                PUBlocks.COPPER_PALLET.get(),
                PUBlocks.IRON_PALLET.get(),
                PUBlocks.GOLD_PALLET.get(),
                PUBlocks.DIAMOND_PALLET.get(),
                PUBlocks.EMERALD_PALLET.get(),
                PUBlocks.NETHERITE_PALLET.get()
        );
        for (Block pallet : resourcePallets) {
            this.horizontalBlock(pallet, resourcePallet(pallet));
        }

        // book piles
        this.simpleBookPile(PUBlocks.BOOK_PILE.get(), BlockStateProperties.WATERLOGGED);
        Set<QuarterSlabBlock> bookPiles = Sets.newHashSet(
                PUBlocks.WHITE_BOOK_PILE.get(),
                PUBlocks.LIGHT_GRAY_BOOK_PILE.get(),
                PUBlocks.GRAY_BOOK_PILE.get(),
                PUBlocks.BLACK_BOOK_PILE.get(),
                PUBlocks.BROWN_BOOK_PILE.get(),
                PUBlocks.RED_BOOK_PILE.get(),
                PUBlocks.ORANGE_BOOK_PILE.get(),
                PUBlocks.YELLOW_BOOK_PILE.get(),
                PUBlocks.LIME_BOOK_PILE.get(),
                PUBlocks.GREEN_BOOK_PILE.get(),
                PUBlocks.CYAN_BOOK_PILE.get(),
                PUBlocks.LIGHT_BLUE_BOOK_PILE.get(),
                PUBlocks.BLUE_BOOK_PILE.get(),
                PUBlocks.PURPLE_BOOK_PILE.get(),
                PUBlocks.MAGENTA_BOOK_PILE.get(),
                PUBlocks.PINK_BOOK_PILE.get()
        );
        for (QuarterSlabBlock pile : bookPiles) {
            this.simpleColoredBookPile(pile, BlockStateProperties.WATERLOGGED);
        }

        // book bundles
        this.simpleBookBundle(PUBlocks.BOOK_BUNDLE.get());
        Set<HorizontalBlock> bookBundles = Sets.newHashSet(
                PUBlocks.WHITE_BOOK_BUNDLE.get(),
                PUBlocks.LIGHT_GRAY_BOOK_BUNDLE.get(),
                PUBlocks.GRAY_BOOK_BUNDLE.get(),
                PUBlocks.BLACK_BOOK_BUNDLE.get(),
                PUBlocks.BROWN_BOOK_BUNDLE.get(),
                PUBlocks.RED_BOOK_BUNDLE.get(),
                PUBlocks.ORANGE_BOOK_BUNDLE.get(),
                PUBlocks.YELLOW_BOOK_BUNDLE.get(),
                PUBlocks.LIME_BOOK_BUNDLE.get(),
                PUBlocks.GREEN_BOOK_BUNDLE.get(),
                PUBlocks.CYAN_BOOK_BUNDLE.get(),
                PUBlocks.LIGHT_BLUE_BOOK_BUNDLE.get(),
                PUBlocks.BLUE_BOOK_BUNDLE.get(),
                PUBlocks.PURPLE_BOOK_BUNDLE.get(),
                PUBlocks.MAGENTA_BOOK_BUNDLE.get(),
                PUBlocks.PINK_BOOK_BUNDLE.get()
        );
        for (HorizontalBlock bundle : bookBundles) {
            this.simpleColoredBookBundle(bundle);
        }

        // book bundle slabs
        this.simpleBookBundleSlab(PUBlocks.BOOK_BUNDLE_SLAB.get(), BlockStateProperties.WATERLOGGED);
        Set<HorizontalSlabBlock> slabBundles = Sets.newHashSet(
                PUBlocks.WHITE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.LIGHT_GRAY_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.GRAY_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BLACK_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BROWN_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.RED_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.ORANGE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.YELLOW_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.LIME_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.GREEN_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.CYAN_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.LIGHT_BLUE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BLUE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.PURPLE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.MAGENTA_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.PINK_BOOK_BUNDLE_SLAB.get()
        );
        for (HorizontalSlabBlock slab : slabBundles) {
            this.simpleColoredBookBundleSlab(slab, BlockStateProperties.WATERLOGGED);
        }

        // turf
        this.simpleBlock(PUBlocks.GRASS_TURF.get(), withRandomRotation(simpleOverlayBlock(PUBlocks.GRASS_TURF.get(), true)));
        this.simpleTurfBlock(PUBlocks.GRASS_TURF_LAYER.get(), true, BlockStateProperties.WATERLOGGED);

        this.simpleBlock(PUBlocks.MYCELIUM_TURF.get(), withRandomRotation(simpleOverlayBlock(PUBlocks.MYCELIUM_TURF.get(), false)));
        this.simpleTurfBlock(PUBlocks.MYCELIUM_TURF_LAYER.get(), false, BlockStateProperties.WATERLOGGED);

        String podzolTurf = blockName(PUBlocks.PODZOL_TURF.get());
        this.simpleBlock(PUBlocks.PODZOL_TURF.get(), withRandomRotation(models().cubeBottomTop(podzolTurf, blockLocation(podzolTurf + "_side"), blockLocation(podzolTurf + "_bottom"), mcBlockLocation("podzol_top"))));
        this.simpleQuarterSlabBlock(PUBlocks.PODZOL_TURF_LAYER.get(), true, true, BlockStateProperties.WATERLOGGED);

        // grass bundle/thatch
        this.grassBaleBlock(PUBlocks.GRASS_BALE.get());
        this.simpleBlock(PUBlocks.GRASS_THATCH.get(), simpleOverlayBlock(PUBlocks.GRASS_THATCH.get(), false));
        this.simpleOverlayStairsBlock(PUBlocks.GRASS_THATCH_STAIRS.get());
        this.simpleOverlaySlabBlock(PUBlocks.GRASS_THATCH_SLAB.get());
    }

}