package salted.packedup.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PURegistry;
import salted.packedup.common.registry.helpers.Organizer;
import salted.packedup.common.tag.PUTags;

import java.util.concurrent.CompletableFuture;

public class PUItemTags extends ItemTagsProvider {
    public PUItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, PackedUp.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.registerItemTags();
    }

    protected void registerItemTags() {
        tag(PUTags.DRUM_STRIKERS).add(
                Items.STICK,
                Items.BLAZE_ROD,
                Items.BONE,
                Items.BAMBOO);

        var bookBuilder = tag(PUTags.BOOK_BUNDLES);
        bookBuilder.add(PURegistry.BOOK_BUNDLE.get().asItem());
        for (Organizer.ColoredBookSet set : PURegistry.COLORED_BOOKS.values()) {
            bookBuilder.add(set.bundle().get().asItem());
        }

        var drumBuilder = tag(PUTags.DRUM_BARRELS);
        PURegistry.DRUM_BARREL_ENTRIES.forEach(entry -> drumBuilder.add(entry.get().asItem()));
    }
}
