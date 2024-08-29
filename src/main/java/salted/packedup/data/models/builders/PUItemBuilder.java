package salted.packedup.data.models.builders;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import salted.packedup.PackedUp;

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

    // general pathing/convenience functions
    protected String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    public ResourceLocation blockLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "block/" + path);
    }

    public ResourceLocation bookLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "block/book/" + path);
    }

    public ResourceLocation itemLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "item/" + path);
    }

    // item builders
    public void bookBasedModel(Item item) {
        withExistingParent(itemName(item), bookLocation(itemName(item)));
    }

    public void quarterSlabBasedModel(Item item, Boolean isBook) {
        if (!isBook) { withExistingParent(itemName(item), blockLocation(itemName(item)) + "_layer0"); }
        else { withExistingParent(itemName(item), bookLocation(itemName(item)) + "_layer0"); }
    }

    public void blockBasedModel(Item item) {
        withExistingParent(itemName(item), blockLocation(itemName(item)));
    }
}
