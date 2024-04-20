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
                // resources
                PUBlocks.COBBLESTONE_CRATE.get(),
                PUBlocks.COBBLED_DEEPSLATE_CRATE.get(),
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
                PUBlocks.EGG_CRATE.get()
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

        tag(ModTags.MINEABLE_WITH_KNIFE)
                .addTag(PUTags.BAG_BLOCKS);
    }
}
