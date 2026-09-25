package salted.packedup.data.models.builders;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import salted.packedup.PackedUp;
import salted.packedup.data.utils.NameUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static salted.packedup.data.utils.NameUtils.*;

public class PUItemBuilder extends ItemModelProvider {

    public PUItemBuilder(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PackedUp.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // This is never used and shouldn't be used. It is only needed
        // so that I can separate the builders from the registration.
    }

    public void generatedModel(Item item) {
        String name = itemName(item);
        withExistingParent(name, mcLoc("item/generated")).texture("layer0", itemLocation(name));
    }

    // item builders
    public void bookBasedModel(Item item) {
        withExistingParent(itemName(item), bookLocation(itemName(item)));
    }

    public void industrialSpoolBasedModel(Item item) {
        withExistingParent(itemName(item), spoolLocation(itemName(item) + "_vertical"));
    }

    /**
     * Item model for a layered (quarter slab) block, using the block's first layer model.
     *
     * @param location Resolves the model folder, e.g. {@code NameUtils::palletLocation}.
     */
    public void quarterSlabBasedModel(Item item, Function<String, ResourceLocation> location) {
        String name = itemName(item);
        if (name.contains("_layer")) { name = nameFromSplit(name, "_layer", true); }
        withExistingParent(itemName(item), location.apply(name) + "_layer0");
    }

    public void blockBasedModel(Item item) {
        blockBasedModel(item, NameUtils::blockLocation);
    }

    /**
     * Item model that inherits from the block model in a category folder.
     *
     * @param location Resolves the model folder, e.g. {@code NameUtils::crateLocation}.
     */
    public void blockBasedModel(Item item, Function<String, ResourceLocation> location) {
        withExistingParent(itemName(item), location.apply(itemName(item)));
    }

    /**
     * Generates block-based item models for every entry in the given category lists,
     * pointing at the category's model folder.
     */
    @SafeVarargs
    protected final void categoryModels(Set<Item> items, Function<String, ResourceLocation> location,
                                        List<RegistryEntry<? extends Block>>... categories) {
        for (List<RegistryEntry<? extends Block>> category : categories) {
            Item[] categoryItems = category.stream().map(e -> e.get().asItem()).toArray(Item[]::new);
            takeAll(items, categoryItems).forEach(item -> blockBasedModel(item, location));
        }
    }

    // Huge thanks to vectorwing for these methods
    @SafeVarargs
    public static <T> Collection<T> takeAll(Set<? extends T> set, T... items) {
        List<T> list = Arrays.asList(items);

        for(T item : items) {
            if (!set.contains(item)) {
                LOGGER.warn("Item {} not found in set", item);
            }
        }

        if (!set.removeAll(list)) {
            LOGGER.warn("takeAll array didn't yield anything ({})", Arrays.toString(items));
        }

        return list;
    }

    public static <T> Collection<T> takeAll(Set<T> src, Predicate<T> pred) {
        List<T> items = new ArrayList<>();
        Iterator<T> iter = src.iterator();

        while(iter.hasNext()) {
            T item = iter.next();
            if (pred.test(item)) {
                iter.remove();
                items.add(item);
            }
        }

        if (items.isEmpty()) {
            LOGGER.warn("takeAll predicate yielded nothing", new Throwable());
        }

        return items;
    }
}
