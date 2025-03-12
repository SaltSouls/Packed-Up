package salted.packedup.data.models;

import com.google.common.collect.Sets;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUItems;
import salted.packedup.data.models.builders.PUItemBuilder;

import java.util.Set;
import java.util.stream.Collectors;

public class PUItemModels extends PUItemBuilder {

    public PUItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    // model registration
    @Override
    protected void registerModels() {
        Set<Item> items = ForgeRegistries.ITEMS.getValues().stream().filter(i -> PackedUp.MODID.equals(ForgeRegistries.ITEMS.getKey(i).getNamespace())).collect(Collectors.toSet());

        // layered blocks
        Set<Item> layerItems = Sets.newHashSet(
                PUItems.PALLET.get(),
                PUItems.GRASS_TURF_LAYER.get(),
                PUItems.PODZOL_TURF_LAYER.get(),
                PUItems.MYCELIUM_TURF_LAYER.get()
        );
        takeAll(items, layerItems.toArray(new Item[0])).forEach(item -> quarterSlabBasedModel(item, false));

        // book blocks
        Set<Item> bookItems = Sets.newHashSet(
                PUItems.BOOK_BUNDLE.get(),
                PUItems.BOOK_BUNDLE_SLAB.get(),
                PUItems.WHITE_BOOK_BUNDLE.get(),
                PUItems.WHITE_BOOK_BUNDLE_SLAB.get(),
                PUItems.LIGHT_GRAY_BOOK_BUNDLE.get(),
                PUItems.LIGHT_GRAY_BOOK_BUNDLE_SLAB.get(),
                PUItems.GRAY_BOOK_BUNDLE.get(),
                PUItems.GRAY_BOOK_BUNDLE_SLAB.get(),
                PUItems.BLACK_BOOK_BUNDLE.get(),
                PUItems.BLACK_BOOK_BUNDLE_SLAB.get(),
                PUItems.BROWN_BOOK_BUNDLE.get(),
                PUItems.BROWN_BOOK_BUNDLE_SLAB.get(),
                PUItems.RED_BOOK_BUNDLE.get(),
                PUItems.RED_BOOK_BUNDLE_SLAB.get(),
                PUItems.ORANGE_BOOK_BUNDLE.get(),
                PUItems.ORANGE_BOOK_BUNDLE_SLAB.get(),
                PUItems.YELLOW_BOOK_BUNDLE.get(),
                PUItems.YELLOW_BOOK_BUNDLE_SLAB.get(),
                PUItems.LIME_BOOK_BUNDLE.get(),
                PUItems.LIME_BOOK_BUNDLE_SLAB.get(),
                PUItems.GREEN_BOOK_BUNDLE.get(),
                PUItems.GREEN_BOOK_BUNDLE_SLAB.get(),
                PUItems.CYAN_BOOK_BUNDLE.get(),
                PUItems.CYAN_BOOK_BUNDLE_SLAB.get(),
                PUItems.LIGHT_BLUE_BOOK_BUNDLE.get(),
                PUItems.LIGHT_BLUE_BOOK_BUNDLE_SLAB.get(),
                PUItems.BLUE_BOOK_BUNDLE.get(),
                PUItems.BLUE_BOOK_BUNDLE_SLAB.get(),
                PUItems.PURPLE_BOOK_BUNDLE.get(),
                PUItems.PURPLE_BOOK_BUNDLE_SLAB.get(),
                PUItems.MAGENTA_BOOK_BUNDLE.get(),
                PUItems.MAGENTA_BOOK_BUNDLE_SLAB.get(),
                PUItems.PINK_BOOK_BUNDLE.get(),
                PUItems.PINK_BOOK_BUNDLE_SLAB.get()
        );
        takeAll(items, bookItems.toArray(new Item[0])).forEach(this::bookBasedModel);

        // book pile blocks
        Set<Item> bookPileItems = Sets.newHashSet(
                PUItems.BOOK_PILE.get(),
                PUItems.WHITE_BOOK_PILE.get(),
                PUItems.LIGHT_GRAY_BOOK_PILE.get(),
                PUItems.GRAY_BOOK_PILE.get(),
                PUItems.BLACK_BOOK_PILE.get(),
                PUItems.BROWN_BOOK_PILE.get(),
                PUItems.RED_BOOK_PILE.get(),
                PUItems.ORANGE_BOOK_PILE.get(),
                PUItems.YELLOW_BOOK_PILE.get(),
                PUItems.LIME_BOOK_PILE.get(),
                PUItems.GREEN_BOOK_PILE.get(),
                PUItems.CYAN_BOOK_PILE.get(),
                PUItems.LIGHT_BLUE_BOOK_PILE.get(),
                PUItems.BLUE_BOOK_PILE.get(),
                PUItems.PURPLE_BOOK_PILE.get(),
                PUItems.MAGENTA_BOOK_PILE.get(),
                PUItems.PINK_BOOK_PILE.get()
        );
        takeAll(items, bookPileItems.toArray(new Item[0])).forEach(item -> quarterSlabBasedModel(item, true));

        // block based items
        takeAll(items, i -> i instanceof BlockItem).forEach(this::blockBasedModel);
    }


}
