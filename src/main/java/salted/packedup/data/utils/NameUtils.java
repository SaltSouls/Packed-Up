package salted.packedup.data.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import salted.packedup.PackedUp;

import java.util.Objects;

public class NameUtils {

    // ============================================================
    // General Naming Functions
    // ============================================================

    /**
     * Retrieves the registry path of a block.
     *
     * @param block The {@link Block} to get the name of.
     * @return      The path of the block's registry name.
     * @throws      NullPointerException If the block is not registered.
     */
    public static @NotNull String blockName(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    /**
     * Retrieves the registry path of an item.
     *
     * @param item  The {@link Item} to get the name of.
     * @return      The path of the item's registry name.
     * @throws      NullPointerException If the item is not registered.
     */
    public static @NotNull String itemName(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

    /**
     * Splits a string and returns the part before or after a specified substring.
     *
     * @param name      The original string to split.
     * @param substring The substring to split the name by.
     * @param before    If true, returns the part before the substring; otherwise, returns the part after.
     * @return          The desired part of the string, or the original string if the substring is not found.
     */
    public static String nameFromSplit(String name, String substring, boolean before) {
        String[] parts = name.split(substring);
        return before ? (parts.length > 0 ? parts[0] : name) : (parts.length > 1 ? parts[1] : name);
    }

    // ============================================================
    // Resource Location Functions
    // ============================================================

    /**
     * Creates a {@link ResourceLocation} for a block within the mod's namespace.
     *
     * @param path  The path of the block.
     * @return      A {@link ResourceLocation} for the block.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation blockLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "block/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a book-related block within the mod's namespace.
     *
     * @param path  The path of the book block.
     * @return      A {@link ResourceLocation} for the book block.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation bookLocation(String path) {
        return blockLocation("book/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a log-related block within the mod's namespace.
     *
     * @param path  The path of the log block.
     * @return      A {@link ResourceLocation} for the log block.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation logLocation(String path) {
        return blockLocation("log/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a block within the Farmer's Delight mod namespace.
     *
     * @param path  The path of the block.
     * @return      A {@link ResourceLocation} for the block.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation fdBlockLocation(String path) {
        return new ResourceLocation("farmersdelight", "block/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a block within the Minecraft namespace.
     *
     * @param path  The path of the block.
     * @return      A {@link ResourceLocation} for the block.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation mcBlockLocation(String path) {
        return new ResourceLocation("block/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for an item within the mod's namespace.
     *
     * @param path  The path of the item.
     * @return      A {@link ResourceLocation} for the item.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation itemLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "item/" + path);
    }
}
