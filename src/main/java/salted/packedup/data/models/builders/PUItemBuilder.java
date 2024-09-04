package salted.packedup.data.models.builders;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;
import salted.packedup.PackedUp;

import static salted.packedup.data.utils.NameUtils.*;

public class PUItemBuilder extends ItemModelProvider {
    static Logger log = PackedUp.LOGGER;

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
}
