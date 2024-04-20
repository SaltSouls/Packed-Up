package salted.packedup.data.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUItems;

import java.util.Arrays;
import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class CraftingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        recipesCompacting(consumer);
        recipesModifiedVanilla(consumer);
    }

    private static String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    /**
     * Splits the string and returns everything before or after the split.
     *
     * @param name      the name of the {@link Block} we are using
     * @param substring the string at which the split occurs
     * @param before    the half of the string gotten
     * @return The desired half of the string array.
     */
    private static String nameFromSplit(String name, String substring, Boolean before) {
        if (before) {
            return Arrays.stream(name.split(substring)).findFirst().get();
        } else return Arrays.stream(name.split(substring)).toList().get(1);
    }

    private static void recipesCompacting(Consumer<FinishedRecipe> consumer) {
        // produce baskets
        simpleCombined(PUItems.SWEET_BERRY_BASKET.get(), Items.SWEET_BERRIES, "basket", consumer);
        simpleCombined(PUItems.GLOW_BERRY_BASKET.get(), Items.GLOW_BERRIES, "basket", consumer);
        simpleCombined(PUItems.APPLE_BASKET.get(), Items.APPLE, consumer);
        simpleCombined(PUItems.GOLDEN_APPLE_BASKET.get(), Items.GOLDEN_APPLE, consumer);
        // fish barrels
        simpleCombined(PUItems.COD_BARREL.get(), Items.COD, consumer);
        simpleCombined(PUItems.SALMON_BARREL.get(), Items.SALMON, consumer);
        // resource crates
        simpleCombined(PUItems.COBBLESTONE_CRATE.get(), Items.COBBLESTONE, consumer);
        simpleCombined(PUItems.COBBLED_DEEPSLATE_CRATE.get(), Items.COBBLED_DEEPSLATE, consumer);
        simpleCombined(PUItems.RAW_COPPER_CRATE.get(), Items.RAW_COPPER_BLOCK, "crate", consumer);
        simpleCombined(PUItems.RAW_IRON_CRATE.get(), Items.RAW_IRON_BLOCK, "crate", consumer);
        simpleCombined(PUItems.RAW_GOLD_CRATE.get(), Items.RAW_GOLD_BLOCK, "crate", consumer);
        simpleCombined(PUItems.GUNPOWDER_CRATE.get(), Items.GUNPOWDER, consumer);
        // produce crates
        simpleCombined(PUItems.GOLDEN_CARROT_CRATE.get(), Items.GOLDEN_CARROT, consumer);
        simpleCombined(PUItems.EGG_CRATE.get(), Items.EGG, consumer);
        // resource bags
        simpleCombined(PUItems.DIRT_BAG.get(), Items.DIRT, consumer);
        simpleCombined(PUItems.ROOTED_DIRT_BAG.get(), Items.ROOTED_DIRT, consumer);
        simpleCombined(PUItems.COARSE_DIRT_BAG.get(), Items.COARSE_DIRT, consumer);
        simpleCombined(PUItems.GRAVEL_BAG.get(), Items.GRAVEL, consumer);
        // material bags
        simpleCombined(PUItems.COCOA_BEAN_BAG.get(), Items.COCOA_BEANS, "bag", consumer);
        simpleCombined(PUItems.SUGAR_BAG.get(), Items.SUGAR, consumer);
        simpleCombined(PUItems.NETHER_WART_BAG.get(), Items.NETHER_WART, consumer);
        simpleCombined(PUItems.GLOWSTONE_DUST_BAG.get(), Items.GLOWSTONE_DUST, consumer);
    }

    private static void recipesModifiedVanilla(Consumer<FinishedRecipe> consumer) {
        modifiedCombined(Items.GLOWSTONE, Items.GLOWSTONE_DUST, consumer);
        modifiedCombined(Items.NETHER_WART_BLOCK, Items.NETHER_WART, consumer);
    }

    // combined compacting/unpacking
    private static void simpleCombined(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        simpleCompact(input, output, consumer);
        simpleShapeless(input, output, consumer);
    }

    private static void simpleCombined(ItemLike input, ItemLike output, String type, Consumer<FinishedRecipe> consumer) {
        simpleCompact(input, output, consumer);
        simpleShapeless(input, output, type, consumer);
    }

    private static void modifiedCombined(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        modifiedCompact(input, output, consumer);
        modifiedShapeless(input, output, consumer);
    }

    // easy shaped recipes
    private static void simpleCompact(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String resource = itemName((Item) output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, input, 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', output)
                .unlockedBy("has" + resource, hasItems(input))
                .save(consumer);
    }

    private static void modifiedCompact(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String resource = itemName((Item) output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, input, 1)
                .pattern("##")
                .pattern("##")
                .define('#', output)
                .unlockedBy("has" + resource, hasItems(input))
                .save(consumer);
    }

    // easy shapeless recipes
    private static void simpleShapeless(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String item = itemName((Item) input);
        String resource = itemName((Item) output);
        String type = nameFromSplit(item, resource, false);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 9)
                .requires(input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, new ResourceLocation(PackedUp.MODID, resource + "_from" + type));
    }

    private static void simpleShapeless(ItemLike input, ItemLike output, String type, Consumer<FinishedRecipe> consumer) {
        String item = itemName((Item) input);
        String resource = itemName((Item) output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 9)
                .requires(input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, new ResourceLocation(PackedUp.MODID, resource + "_from_" + type));
    }

    private static void modifiedShapeless(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String item = itemName((Item) input);
        String resource = itemName((Item) output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 4)
                .requires(input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, new ResourceLocation(PackedUp.MODID, resource + "_from_" + input));
    }


}
