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

    /**
     * Strips trailing underscores from the given name
     *
     * @param name The original string to strip.
     * @return     The name, with trailing underscores removed.
     */
    public static String stripUnderscores(String name) {
        int start = 0;
        int end = name.length();

        while (start < end && name.charAt(start) == '_') start++;
        while (end > start && name.charAt(end - 1) == '_') end--;

        return name.substring(start, end);
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
     * Creates a {@link ResourceLocation} for an item within the mod's namespace.
     *
     * @param path  The path of the item.
     * @return      A {@link ResourceLocation} for the item.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation itemLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "item/" + path);
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
     * Creates a {@link ResourceLocation} for a crate-related block within the mod's namespace.
     *
     * @param path  The path of the crate block.
     * @return      A {@link ResourceLocation} for the crate block.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation crateLocation(String path) {
        return blockLocation("crate/" + path);
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
     * Creates a {@link ResourceLocation} for a spool-related block within the mod's namespace.
     *
     * @param path  The path of the spool block.
     * @return      A {@link ResourceLocation} for the spool block.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation spoolLocation(String path) {
        return blockLocation("spool/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a bag-related block within the mod's namespace.
     *
     * @param path  The path within the bag folder.
     * @return      A {@link ResourceLocation} under {@code block/bag/}.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation bagLocation(String path) {
        return blockLocation("bag/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a barrel-related block within the mod's namespace.
     *
     * @param path  The path within the barrel folder.
     * @return      A {@link ResourceLocation} under {@code block/barrel/}.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation barrelLocation(String path) {
        return blockLocation("barrel/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a basket-related block within the mod's namespace.
     *
     * @param path  The path within the basket folder.
     * @return      A {@link ResourceLocation} under {@code block/basket/}.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation basketLocation(String path) {
        return blockLocation("basket/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a pallet-related block within the mod's namespace.
     *
     * @param path  The path within the pallet folder.
     * @return      A {@link ResourceLocation} under {@code block/pallet/}.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation palletLocation(String path) {
        return blockLocation("pallet/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a pile-related block within the mod's namespace.
     *
     * @param path  The path within the pile folder.
     * @return      A {@link ResourceLocation} under {@code block/pile/}.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation pileLocation(String path) {
        return blockLocation("pile/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a turf-related block within the mod's namespace.
     *
     * @param path  The path within the turf folder.
     * @return      A {@link ResourceLocation} under {@code block/turf/}.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation turfLocation(String path) {
        return blockLocation("turf/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a thatch-related block within the mod's namespace.
     *
     * @param path  The path within the thatch folder.
     * @return      A {@link ResourceLocation} under {@code block/thatch/}.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation thatchLocation(String path) {
        return blockLocation("thatch/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a gauge-related block within the mod's namespace.
     *
     * @param path  The path within the gauge folder.
     * @return      A {@link ResourceLocation} under {@code block/gauge/}.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation gaugeLocation(String path) {
        return blockLocation("gauge/" + path);
    }

    /**
     * Creates a {@link ResourceLocation} for a drum-related block within the mod's namespace.
     *
     * @param path  The path within the drum folder.
     * @return      A {@link ResourceLocation} under {@code block/drum/}.
     */
    @Contract("_ -> new")
    public static @NotNull ResourceLocation drumLocation(String path) {
        return blockLocation("drum/" + path);
    }

}
