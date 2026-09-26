package salted.packedup.data.models;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PURegistry;
import salted.packedup.common.registry.helpers.Organizer;
import salted.packedup.data.models.builders.PUItemBuilder;
import salted.packedup.data.utils.NameUtils;

import java.util.Set;
import java.util.stream.Collectors;

import static salted.packedup.data.utils.NameUtils.blockLocation;

public class PUItemModels extends PUItemBuilder {

    public PUItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> items = ForgeRegistries.ITEMS.getValues().stream()
                .filter(i -> PackedUp.MODID.equals(ForgeRegistries.ITEMS.getKey(i).getNamespace()))
                .collect(Collectors.toSet());

        // Layered blocks
        takeAll(items, PURegistry.PALLET.get().asItem())
                .forEach(item -> quarterSlabBasedModel(item, NameUtils::palletLocation));

        Item[] turfLayerItems = {
                PURegistry.GRASS_TURF_LAYER.get().asItem(),
                PURegistry.PODZOL_TURF_LAYER.get().asItem(),
                PURegistry.MYCELIUM_TURF_LAYER.get().asItem()
        };
        takeAll(items, turfLayerItems).forEach(item -> quarterSlabBasedModel(item, NameUtils::turfLocation));

        // Books
        Item[] defaultBookItems = {
                PURegistry.BOOK_BUNDLE.get().asItem(),
                PURegistry.BOOK_BUNDLE_SLAB.get().asItem()
        };
        takeAll(items, defaultBookItems).forEach(this::bookBasedModel);
        takeAll(items, PURegistry.BOOK_PILE.get().asItem())
                .forEach(item -> quarterSlabBasedModel(item, NameUtils::bookLocation));

        for (Organizer.ColoredBookSet set : PURegistry.COLORED_BOOKS.values()) {
            takeAll(items, set.getBundle().asItem(), set.getSlab().asItem()).forEach(this::bookBasedModel);
            takeAll(items, set.getPile().asItem()).forEach(item -> quarterSlabBasedModel(item, NameUtils::bookLocation));
        }

        // Industrial spools
        for (var entry : PURegistry.COLORED_SPOOLS.values()) {
            takeAll(items, entry.get().asItem()).forEach(this::industrialSpoolBasedModel);
        }

        // Category folders
        categoryModels(items, NameUtils::crateLocation,
                PURegistry.RESOURCE_CRATE_ENTRIES,
                PURegistry.REINFORCED_CRATE_ENTRIES,
                PURegistry.MISC_CRATE_ENTRIES,
                PURegistry.PRODUCE_CRATE_ENTRIES,
                PURegistry.MUSHROOM_CRATE_ENTRIES);
        takeAll(items, PURegistry.CRATE_LID.get().asItem(), PURegistry.REINFORCED_CRATE_LID.get().asItem())
                .forEach(item -> blockBasedModel(item, NameUtils::crateLocation));

        categoryModels(items, NameUtils::bagLocation,
                PURegistry.RESOURCE_BAG_ENTRIES,
                PURegistry.PRODUCE_BAG_ENTRIES,
                PURegistry.MISC_BAG_ENTRIES);

        categoryModels(items, NameUtils::barrelLocation, PURegistry.BARREL_ENTRIES);
        categoryModels(items, NameUtils::basketLocation, PURegistry.BASKET_ENTRIES);
        categoryModels(items, NameUtils::drumLocation, PURegistry.DRUM_BARREL_ENTRIES);
        categoryModels(items, NameUtils::pileLocation, PURegistry.PILE_ENTRIES);
        categoryModels(items, NameUtils::palletLocation, PURegistry.PALLET_ENTRIES);

        Item[] turfItems = {
                PURegistry.GRASS_TURF.get().asItem(),
                PURegistry.PODZOL_TURF.get().asItem(),
                PURegistry.MYCELIUM_TURF.get().asItem()
        };
        takeAll(items, turfItems).forEach(item -> blockBasedModel(item, NameUtils::turfLocation));

        Item[] thatchItems = {
                PURegistry.GRASS_THATCH.get().asItem(),
                PURegistry.GRASS_THATCH_STAIRS.get().asItem(),
                PURegistry.GRASS_THATCH_SLAB.get().asItem()
        };
        takeAll(items, thatchItems).forEach(item -> blockBasedModel(item, NameUtils::thatchLocation));

        takeAll(items, PURegistry.GRASS_BALE.get().asItem())
                .forEach(item -> blockBasedModel(item, name -> blockLocation("bundle/" + name)));

        takeAll(items, PURegistry.FLUID_GAUGE.get().asItem()).forEach(this::generatedModel);

        if (!items.isEmpty()) {
            throw new IllegalStateException("No item model rule for: "
                    + items.stream().map(NameUtils::itemName).sorted().toList()
                    + ". Add its category to PUItemModels.");
        }
    }
}
