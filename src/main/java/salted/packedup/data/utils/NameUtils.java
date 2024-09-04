package salted.packedup.data.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import salted.packedup.PackedUp;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.Arrays;

public class NameUtils {

    // general naming functions
    public static String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    public static String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    /**
     * Splits the string and returns everything before or after the split.
     *
     * @param name      the name of the {@link Block} or {@link Item} we are using
     * @param substring the string at which the split occurs
     * @param before    the position at which the split occurs
     * @return The desired half of the string array.
     */
    public static String nameFromSplit(String name, String substring, boolean before) {
        if (before) { return Arrays.stream(name.split(substring)).findFirst().get();
        } else return Arrays.stream(name.split(substring)).toList().get(1);
    }

    // resource location functions
    public static ResourceLocation blockLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "block/" + path);
    }

    public static ResourceLocation bookLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "block/book/" + path);
    }

    public static ResourceLocation fdBlockLocation(String path) {
        return new ResourceLocation(FarmersDelight.MODID, "block/" + path);
    }

    public static ResourceLocation mcBlockLocation(String path) {
        return new ResourceLocation("block/" + path);
    }

    public static ResourceLocation itemLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "item/" + path);
    }
}
