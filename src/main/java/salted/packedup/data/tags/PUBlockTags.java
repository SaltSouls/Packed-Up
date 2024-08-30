package salted.packedup.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUBlocks;
import salted.packedup.common.registry.PUItems;
import salted.packedup.common.tag.PUTags;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class PUBlockTags extends BlockTagsProvider {
    public PUBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PackedUp.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerModTags();
        this.registerEffectiveTools();
    }

    protected void registerModTags() {
        // remove from staw_blocks and add to bag_blocks
        tag(ModTags.STRAW_BLOCKS).remove(ModBlocks.RICE_BAG.get());

        tag(PUTags.BASKET_BLOCKS).add(
                PUBlocks.SWEET_BERRY_BASKET.get(),
                PUBlocks.GLOW_BERRY_BASKET.get(),
                PUBlocks.APPLE_BASKET.get(),
                PUBlocks.GOLDEN_APPLE_BASKET.get()
        );

        tag(PUTags.BARREL_BLOCKS).add(
                PUBlocks.COD_BARREL.get(),
                PUBlocks.SALMON_BARREL.get()
        );

        tag(PUTags.CRATE_BLOCKS).add(
                PUBlocks.CRATE_LID.get(),
                // resources
                PUBlocks.COBBLESTONE_CRATE.get(),
                PUBlocks.COBBLED_DEEPSLATE_CRATE.get(),
                PUBlocks.ANDESITE_CRATE.get(),
                PUBlocks.DIORITE_CRATE.get(),
                PUBlocks.GRANITE_CRATE.get(),
                PUBlocks.TUFF_CRATE.get(),
                PUBlocks.BLACKSTONE_CRATE.get(),
                PUBlocks.BASALT_CRATE.get(),
                PUBlocks.RAW_COPPER_CRATE.get(),
                PUBlocks.RAW_IRON_CRATE.get(),
                PUBlocks.RAW_GOLD_CRATE.get(),
                // produce
                ModBlocks.CARROT_CRATE.get(),
                PUBlocks.GOLDEN_CARROT_CRATE.get(),
                ModBlocks.POTATO_CRATE.get(),
                ModBlocks.BEETROOT_CRATE.get(),
                ModBlocks.CABBAGE_CRATE.get(),
                ModBlocks.TOMATO_CRATE.get(),
                ModBlocks.ONION_CRATE.get(),
                PUBlocks.EGG_CRATE.get(),
                PUBlocks.RED_MUSHROOM_CRATE.get(),
                PUBlocks.BROWN_MUSHROOM_CRATE.get(),
                // misc crates
                PUBlocks.GUNPOWDER_CRATE.get(),
                PUBlocks.QUARTZ_CRATE.get(),
                PUBlocks.AMETHYST_CRATE.get(),
                PUBlocks.ECHO_SHARD_CRATE.get()
        );

        tag(PUTags.REINFORCED_CRATE_BLOCKS).add(
                PUBlocks.REINFORCED_CRATE_LID.get(),
                // resources
                PUBlocks.REINFORCED_COBBLESTONE_CRATE.get(),
                PUBlocks.REINFORCED_COBBLED_DEEPSLATE_CRATE.get(),
                PUBlocks.REINFORCED_ANDESITE_CRATE.get(),
                PUBlocks.REINFORCED_DIORITE_CRATE.get(),
                PUBlocks.REINFORCED_GRANITE_CRATE.get(),
                PUBlocks.REINFORCED_TUFF_CRATE.get(),
                PUBlocks.REINFORCED_BLACKSTONE_CRATE.get(),
                PUBlocks.REINFORCED_BASALT_CRATE.get()
        );

        tag(PUTags.RESOURCE_PILE_BLOCKS).add(
                PUBlocks.BRICK_PILE.get(),
                PUBlocks.NETHER_BRICK_PILE.get(),
                PUBlocks.STONE_PILE.get(),
                PUBlocks.DEEPSLATE_PILE.get(),
                PUBlocks.CALCITE_PILE.get()
        );

        tag(PUTags.RESOURCE_PALLET_BLOCKS).add(
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

        tag(PUTags.BAG_BLOCKS).add(
                // resources
                PUBlocks.DIRT_BAG.get(),
                PUBlocks.ROOTED_DIRT_BAG.get(),
                PUBlocks.COARSE_DIRT_BAG.get(),
                PUBlocks.GRAVEL_BAG.get(),
                // produce
                ModBlocks.RICE_BAG.get(),
                PUBlocks.COCOA_BEAN_BAG.get(),
                PUBlocks.SUGAR_BAG.get(),
                PUBlocks.NETHER_WART_BAG.get(),
                PUBlocks.GLOWSTONE_DUST_BAG.get(),
                PUBlocks.ENDER_PEARL_BAG.get()
        );

        tag(PUTags.BOOK_BLOCKS).add(
                PUBlocks.BOOK_BUNDLE.get(),
                PUBlocks.BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BOOK_PILE.get(),
                PUBlocks.WHITE_BOOK_BUNDLE.get(),
                PUBlocks.WHITE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.WHITE_BOOK_PILE.get(),
                PUBlocks.LIGHT_GRAY_BOOK_BUNDLE.get(),
                PUBlocks.LIGHT_GRAY_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.LIGHT_GRAY_BOOK_PILE.get(),
                PUBlocks.GRAY_BOOK_BUNDLE.get(),
                PUBlocks.GRAY_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.GRAY_BOOK_PILE.get(),
                PUBlocks.BLACK_BOOK_BUNDLE.get(),
                PUBlocks.BLACK_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BLACK_BOOK_PILE.get(),
                PUBlocks.BROWN_BOOK_BUNDLE.get(),
                PUBlocks.BROWN_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BROWN_BOOK_PILE.get(),
                PUBlocks.RED_BOOK_BUNDLE.get(),
                PUBlocks.RED_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.RED_BOOK_PILE.get(),
                PUBlocks.ORANGE_BOOK_BUNDLE.get(),
                PUBlocks.ORANGE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.ORANGE_BOOK_PILE.get(),
                PUBlocks.YELLOW_BOOK_BUNDLE.get(),
                PUBlocks.YELLOW_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.YELLOW_BOOK_PILE.get(),
                PUBlocks.LIME_BOOK_BUNDLE.get(),
                PUBlocks.LIME_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.LIME_BOOK_PILE.get(),
                PUBlocks.GREEN_BOOK_BUNDLE.get(),
                PUBlocks.GREEN_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.GREEN_BOOK_PILE.get(),
                PUBlocks.CYAN_BOOK_BUNDLE.get(),
                PUBlocks.CYAN_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.CYAN_BOOK_PILE.get(),
                PUBlocks.LIGHT_BLUE_BOOK_BUNDLE.get(),
                PUBlocks.LIGHT_BLUE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.LIGHT_BLUE_BOOK_PILE.get(),
                PUBlocks.BLUE_BOOK_BUNDLE.get(),
                PUBlocks.BLUE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BLUE_BOOK_PILE.get(),
                PUBlocks.PURPLE_BOOK_BUNDLE.get(),
                PUBlocks.PURPLE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.PURPLE_BOOK_PILE.get(),
                PUBlocks.MAGENTA_BOOK_BUNDLE.get(),
                PUBlocks.MAGENTA_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.MAGENTA_BOOK_PILE.get(),
                PUBlocks.PINK_BOOK_BUNDLE.get(),
                PUBlocks.PINK_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.PINK_BOOK_PILE.get()
        );
    }

    protected void registerEffectiveTools() {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .remove(
                        ModBlocks.CARROT_CRATE.get(),
                        ModBlocks.POTATO_CRATE.get(),
                        ModBlocks.BEETROOT_CRATE.get(),
                        ModBlocks.CABBAGE_CRATE.get(),
                        ModBlocks.TOMATO_CRATE.get(),
                        ModBlocks.ONION_CRATE.get()
                )
                .add(PUBlocks.PALLET.get())
                .addTag(PUTags.BASKET_BLOCKS)
                .addTag(PUTags.BARREL_BLOCKS)
                .addTag(PUTags.CRATE_BLOCKS);

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(PUTags.REINFORCED_CRATE_BLOCKS)
                .addTag(PUTags.RESOURCE_PILE_BLOCKS)
                .addTag(PUTags.RESOURCE_PALLET_BLOCKS);

        tag(ModTags.MINEABLE_WITH_KNIFE)
                .addTag(PUTags.BAG_BLOCKS);

        tag(PUTags.MINEABLE_WITH_SHEARS)
                .addTag(PUTags.BOOK_BLOCKS);

    }
}
