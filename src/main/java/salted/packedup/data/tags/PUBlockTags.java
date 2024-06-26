package salted.packedup.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUBlocks;
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
                PUBlocks.BROWN_MUSHROOM_CRATE.get()
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
                PUBlocks.GLOWSTONE_DUST_BAG.get()
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
                        ModBlocks.ONION_CRATE.get())
                .addTag(PUTags.BASKET_BLOCKS)
                .addTag(PUTags.BARREL_BLOCKS)
                .addTag(PUTags.CRATE_BLOCKS);

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(PUTags.REINFORCED_CRATE_BLOCKS)
                .addTag(PUTags.RESOURCE_PILE_BLOCKS)
                .addTag(PUTags.RESOURCE_PALLET_BLOCKS);

        tag(ModTags.MINEABLE_WITH_KNIFE)
                .addTag(PUTags.BAG_BLOCKS);
    }
}
