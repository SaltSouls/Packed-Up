package salted.packedup.data.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import salted.packedup.PackedUp;

import java.util.Set;
import java.util.stream.Collectors;

import static vectorwing.farmersdelight.data.ItemModels.takeAll;

public class PUItemModels extends ItemModelProvider {
    public PUItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PackedUp.MODID, existingFileHelper);
    }

    // general pathing/convenience functions
    private String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    public ResourceLocation blockLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "block/" + path);
    }

    public ResourceLocation itemLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "item/" + path);
    }

    // model registration
    @Override
    protected void registerModels() {
        Set<Item> items = ForgeRegistries.ITEMS.getValues().stream().filter(i -> PackedUp.MODID.equals(ForgeRegistries.ITEMS.getKey(i).getNamespace()))
                .collect(Collectors.toSet());

        // using FDs function for creating item models for blocks
        takeAll(items, i -> i instanceof BlockItem).forEach(item -> blockBasedModel(item, ""));
    }

    public void blockBasedModel(Item item, String suffix) {
        withExistingParent(itemName(item), blockLocation(itemName(item) + suffix));
    }
}
