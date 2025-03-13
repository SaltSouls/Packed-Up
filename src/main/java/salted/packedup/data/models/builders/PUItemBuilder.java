package salted.packedup.data.models.builders;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import salted.packedup.PackedUp;

import java.util.*;
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

    // item builders
    public void bookBasedModel(Item item) {
        withExistingParent(itemName(item), bookLocation(itemName(item)));
    }

    public void quarterSlabBasedModel(Item item, boolean isBook) {
        if (!isBook) {
            String name = itemName(item);
            if(name.contains("_layer")) { name =nameFromSplit(name, "_layer", true); }
            withExistingParent(itemName(item), blockLocation(name) + "_layer0");
        }
        else { withExistingParent(itemName(item), bookLocation(itemName(item)) + "_layer0"); }
    }

    public void blockBasedModel(Item item) {
        withExistingParent(itemName(item), blockLocation(itemName(item)));
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
