package salted.packedup.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUItems;
import salted.packedup.common.tag.PUTags;

import java.util.concurrent.CompletableFuture;

public class PUItemTags extends ItemTagsProvider {
    public PUItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, PackedUp.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerItemTags();
    }

    protected void registerItemTags() {
        tag(PUTags.BOOK_BUNDLES).add(
                PUItems.BOOK_BUNDLE.get(),
                PUItems.WHITE_BOOK_BUNDLE.get(),
                PUItems.LIGHT_GRAY_BOOK_BUNDLE.get(),
                PUItems.GRAY_BOOK_BUNDLE.get(),
                PUItems.BLACK_BOOK_BUNDLE.get(),
                PUItems.BROWN_BOOK_BUNDLE.get(),
                PUItems.RED_BOOK_BUNDLE.get(),
                PUItems.ORANGE_BOOK_BUNDLE.get(),
                PUItems.YELLOW_BOOK_BUNDLE.get(),
                PUItems.LIME_BOOK_BUNDLE.get(),
                PUItems.GREEN_BOOK_BUNDLE.get(),
                PUItems.CYAN_BOOK_BUNDLE.get(),
                PUItems.LIGHT_BLUE_BOOK_BUNDLE.get(),
                PUItems.BLUE_BOOK_BUNDLE.get(),
                PUItems.PURPLE_BOOK_BUNDLE.get(),
                PUItems.MAGENTA_BOOK_BUNDLE.get(),
                PUItems.PINK_BOOK_BUNDLE.get());
    }
}
