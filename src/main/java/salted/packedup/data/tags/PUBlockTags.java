package salted.packedup.data.tags;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PURegistry;
import salted.packedup.common.registry.helpers.Organizer;
import salted.packedup.common.tag.PUTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PUBlockTags extends BlockTagsProvider {
    public PUBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PackedUp.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.updateVanillaTags();
        this.registerModTags();
        this.registerEffectiveTools();
    }

    private ResourceLocation fdBlock(String block) {
        return ResourceLocation.fromNamespaceAndPath("farmersdelight", block);
    }

    /** Adds all blocks from a group list to a tag builder. */
    private void addGroup(IntrinsicTagAppender<Block> builder, List<RegistryEntry<? extends Block>> group) {
        for (var entry : group) {
            builder.add(entry.get());
        }
    }

    protected void updateVanillaTags() {
        tag(BlockTags.DIRT)
                .addTag(PUTags.TURF_BLOCKS);

        tag(BlockTags.MUSHROOM_GROW_BLOCK).add(
                PURegistry.MYCELIUM_TURF.get(),
                PURegistry.MYCELIUM_TURF_LAYER.get(),
                PURegistry.PODZOL_TURF.get(),
                PURegistry.PODZOL_TURF_LAYER.get()
        );

        tag(BlockTags.MOOSHROOMS_SPAWNABLE_ON).add(
                PURegistry.MYCELIUM_TURF.get(),
                PURegistry.MYCELIUM_TURF_LAYER.get()
        );

        tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(
                PURegistry.CRATE_LID.get(),
                PURegistry.REINFORCED_CRATE_LID.get()
        );

        tag(BlockTags.NEEDS_STONE_TOOL)
                .addTag(PUTags.REINFORCED_CRATE_BLOCKS)
                .addTag(PUTags.DRUM_BARREL_BLOCKS)
                .add(
                        PURegistry.COPPER_PALLET.get(),
                        PURegistry.IRON_PALLET.get()
                );

        tag(BlockTags.NEEDS_IRON_TOOL).add(
                PURegistry.GOLD_PALLET.get(),
                PURegistry.DIAMOND_PALLET.get(),
                PURegistry.EMERALD_PALLET.get()
        );

        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                PURegistry.NETHERITE_PALLET.get()
        );
    }

    protected void registerModTags() {
        tag(PUTags.STRAW_BLOCKS).remove(fdBlock("rice_bag"));

        tag(PUTags.STRAW_BLOCKS).add(
                PURegistry.GRASS_BALE.get(),
                PURegistry.GRASS_THATCH.get(),
                PURegistry.GRASS_THATCH_STAIRS.get(),
                PURegistry.GRASS_THATCH_SLAB.get()
        );

        tag(PUTags.CAMPFIRE_SIGNAL_SMOKE).add(
                PURegistry.GRASS_BALE.get(),
                PURegistry.GRASS_THATCH.get()
        );

        tag(PUTags.TURF_BLOCKS).add(
                PURegistry.GRASS_TURF.get(),
                PURegistry.GRASS_TURF_LAYER.get(),
                PURegistry.PODZOL_TURF.get(),
                PURegistry.PODZOL_TURF_LAYER.get(),
                PURegistry.MYCELIUM_TURF.get(),
                PURegistry.MYCELIUM_TURF_LAYER.get()
        );

        addGroup(tag(PUTags.BASKET_BLOCKS), PURegistry.BASKET_ENTRIES);
        addGroup(tag(PUTags.BARREL_BLOCKS), PURegistry.BARREL_ENTRIES);

        // crates: lids + resource + produce + mushroom + misc
        var crateTag = tag(PUTags.CRATE_BLOCKS);
        crateTag.add(PURegistry.CRATE_LID.get());
        addGroup(crateTag, PURegistry.RESOURCE_CRATE_ENTRIES);
        addGroup(crateTag, PURegistry.PRODUCE_CRATE_ENTRIES);
        addGroup(crateTag, PURegistry.MUSHROOM_CRATE_ENTRIES);
        addGroup(crateTag, PURegistry.MISC_CRATE_ENTRIES);
        crateTag.addOptional(fdBlock("carrot_crate"))
                .addOptional(fdBlock("potato_crate"))
                .addOptional(fdBlock("beetroot_crate"))
                .addOptional(fdBlock("cabbage_crate"))
                .addOptional(fdBlock("tomato_crate"))
                .addOptional(fdBlock("onion_crate"));

        // reinforced crates
        var reinforcedTag = tag(PUTags.REINFORCED_CRATE_BLOCKS);
        reinforcedTag.add(PURegistry.REINFORCED_CRATE_LID.get());
        addGroup(reinforcedTag, PURegistry.REINFORCED_CRATE_ENTRIES);

        addGroup(tag(PUTags.RESOURCE_PILE_BLOCKS), PURegistry.PILE_ENTRIES);
        addGroup(tag(PUTags.RESOURCE_PALLET_BLOCKS), PURegistry.PALLET_ENTRIES);

        // bags
        var bagTag = tag(PUTags.BAG_BLOCKS);
        addGroup(bagTag, PURegistry.RESOURCE_BAG_ENTRIES);
        addGroup(bagTag, PURegistry.PRODUCE_BAG_ENTRIES);
        addGroup(bagTag, PURegistry.MISC_BAG_ENTRIES);
        bagTag.addOptional(fdBlock("rice_bag"));

        // books: default + all colored
        var bookTag = tag(PUTags.BOOK_BLOCKS);
        bookTag.add(PURegistry.BOOK_BUNDLE.get(), PURegistry.BOOK_BUNDLE_SLAB.get(), PURegistry.BOOK_PILE.get());
        for (Organizer.ColoredBookSet set : PURegistry.COLORED_BOOKS.values()) {
            bookTag.add(set.bundle().get(), set.slab().get(), set.pile().get());
        }

        var drumTag = tag(PUTags.DRUM_BARREL_BLOCKS);
        addGroup(drumTag, PURegistry.DRUM_BARREL_ENTRIES);
    }

    protected void registerEffectiveTools() {
        tag(BlockTags.MINEABLE_WITH_AXE)
                .remove(
                        fdBlock("carrot_crate"),
                        fdBlock("potato_crate"),
                        fdBlock("beetroot_crate"),
                        fdBlock("cabbage_crate"),
                        fdBlock("tomato_crate"),
                        fdBlock("onion_crate")
                )
                .add(PURegistry.PALLET.get())
                .addTag(PUTags.BASKET_BLOCKS)
                .addTag(PUTags.BARREL_BLOCKS)
                .addTag(PUTags.CRATE_BLOCKS);

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(PUTags.REINFORCED_CRATE_BLOCKS)
                .addTag(PUTags.RESOURCE_PILE_BLOCKS)
                .addTag(PUTags.RESOURCE_PALLET_BLOCKS)
                .addTag(PUTags.DRUM_BARREL_BLOCKS);

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(PUTags.TURF_BLOCKS);

        tag(PUTags.MINEABLE_WITH_KNIFE)
                .addTag(PUTags.BAG_BLOCKS);

        tag(PUTags.MINEABLE_WITH_SHEARS)
                .addTag(PUTags.BOOK_BLOCKS);
    }
}
