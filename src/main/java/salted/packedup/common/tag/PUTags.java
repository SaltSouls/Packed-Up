package salted.packedup.common.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import salted.packedup.PackedUp;

public class PUTags {
    public static final TagKey<Item> BOOK_BUNDLES = modItemTag("books");
    public static final TagKey<Block> BASKET_BLOCKS = modBlockTag("baskets");
    public static final TagKey<Block> BARREL_BLOCKS = modBlockTag("barrels");
    public static final TagKey<Block> CRATE_BLOCKS = modBlockTag("crates");
    public static final TagKey<Block> REINFORCED_CRATE_BLOCKS = modBlockTag("reinforced_crates");
    public static final TagKey<Block> BAG_BLOCKS = modBlockTag("bags");
    public static final TagKey<Block> RESOURCE_PILE_BLOCKS = modBlockTag("piles");
    public static final TagKey<Block> RESOURCE_PALLET_BLOCKS = modBlockTag("pallets");

    // tag creators
    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(new ResourceLocation(PackedUp.MODID, path));
    }

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(PackedUp.MODID, path));
    }
}