package salted.packedup.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.jetbrains.annotations.NotNull;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUItems;
import salted.packedup.common.tag.PUTags;

import java.util.function.Consumer;

import static salted.packedup.data.utils.ConditionalUtils.hasItems;
import static salted.packedup.data.utils.NameUtils.itemName;
import static salted.packedup.data.utils.NameUtils.nameFromSplit;

public class PURecipeBuilder extends RecipeProvider {
    // Constants for recipe directories
    private static final String MISC_DIR = "recipes/misc/";
    private static final String DECORATIONS_DIR = "recipes/decorations/";

    public PURecipeBuilder(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        // This is never used and shouldn't be used. It is only needed
        // so that I can separate the builders from the registration.
    }

    // ============================================================
    // General Pathing and Convenience Functions
    // ============================================================

    /**
     * Creates a ResourceLocation for a recipe directory.
     *
     * @param modID The mod ID.
     * @param path  The path to the recipe.
     * @return A ResourceLocation for the recipe directory.
     */
    protected static ResourceLocation recipeDir(String modID, String path) {
        return new ResourceLocation(modID, path);
    }

    // ============================================================
    // Recipe Builders
    // ============================================================

    // --------------------------
    // Combined Recipes
    // --------------------------

    /**
     * Creates both a packing and unpacking recipe for the given input and output items.
     *
     * @param product  The output item ({@link ItemLike}).
     * @param resource The input item ({@link ItemLike}).
     * @param split    Whether to split the product name for the unpacking recipe.
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleCombined(ItemLike product, ItemLike resource, boolean split, Consumer<FinishedRecipe> consumer) {
        simpleCompact(resource, product, consumer);
        simpleShapeless(product, resource, split, consumer);
    }

    /**
     * Creates both a packing and unpacking recipe for the given input and output items, with a condition.
     *
     * @param product  The output item ({@link ItemLike}).
     * @param resource The input item ({@link ItemLike}).
     * @param split    Whether to split the product name for the unpacking recipe.
     * @param condition The condition ({@link ICondition}) for the recipe.
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleConditionalCombined(ItemLike product, ItemLike resource, boolean split, ICondition condition, Consumer<FinishedRecipe> consumer) {
        simpleConditionalCompact(resource, product, condition, consumer);
        simpleConditionalShapeless(product, resource, split, condition, consumer);
    }

    /**
     * Creates both a packing and unpacking recipe for the given input and output items, with a custom type.
     *
     * @param product  The output item ({@link ItemLike}).
     * @param resource The input item ({@link ItemLike}).
     * @param before   Whether to split the product name before or after the type.
     * @param type     The custom type for the recipe.
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleCombined(ItemLike product, ItemLike resource, boolean before, String type, Consumer<FinishedRecipe> consumer) {
        simpleCompact(resource, product, consumer);
        simpleShapeless(product, resource, before, type, consumer);
    }

    /**
     * Creates both a packing and unpacking recipe for the given input and output items, with a custom type and condition.
     *
     * @param product  The output item ({@link ItemLike}).
     * @param resource The input item ({@link ItemLike}).
     * @param before   Whether to split the product name before or after the type.
     * @param type     The custom type for the recipe.
     * @param condition The condition ({@link ICondition}) for the recipe.
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleConditionalCombined(ItemLike product, ItemLike resource, boolean before, String type, ICondition condition, Consumer<FinishedRecipe> consumer) {
        simpleConditionalCompact(resource, product, condition, consumer);
        simpleConditionalShapeless(product, resource, before, type, condition, consumer);
    }

    /**
     * Creates both a packing and unpacking recipe for the given input and output items, modifying a vanilla recipe.
     *
     * @param product  The output item ({@link ItemLike}).
     * @param resource The input item ({@link ItemLike}).
     * @param modID    The mod ID for the recipe.
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void modifiedCombined(ItemLike product, ItemLike resource, int count, String modID, Consumer<FinishedRecipe> consumer) {
        modifiedCompact(resource, product, consumer);
        modifiedShapeless(product, resource, count, modID, consumer);
    }

    /**
     * Creates both a packing and unpacking recipe for the given input and output items, modifying a vanilla recipe with a condition.
     *
     * @param product  The output item ({@link ItemLike}).
     * @param resource The input item ({@link ItemLike}).
     * @param modID    The mod ID for the recipe.
     * @param condition The condition ({@link ICondition}) for the recipe.
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void modifiedConditionalCombined(ItemLike product, ItemLike resource, int count, String modID, ICondition condition, Consumer<FinishedRecipe> consumer) {
        modifiedConditionalCompact(resource, product, condition, consumer);
        modifiedConditionalShapeless(product, resource, count, modID, condition, consumer);
    }

    // --------------------------
    // Shaped Recipes
    // --------------------------

    /**
     * Creates a simple 3x3 compacting recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleCompact(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        shapedRecipe(input, output, consumer, "###", "###", "###");
    }

    /**
     * Creates a simple 3x3 compacting recipe with a condition.
     *
     * @param input     The input item ({@link ItemLike}).
     * @param output    The output item ({@link ItemLike}).
     * @param condition The condition ({@link ICondition}) for the recipe.
     * @param consumer  The {@link FinishedRecipe} consumer.
     */
    protected static void simpleConditionalCompact(ItemLike input, ItemLike output, ICondition condition, Consumer<FinishedRecipe> consumer) {
        ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(recipe -> simpleCompact(input, output, recipe))
                .generateAdvancement(recipeDir(PackedUp.MODID, DECORATIONS_DIR + itemName(output.asItem())))
                .build(consumer, recipeDir(PackedUp.MODID, itemName(output.asItem())));
    }

    /**
     * Creates a modified 2x2 compacting recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void modifiedCompact(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        modifiedShapedRecipe(input, output, consumer, "##", "##");
    }

    /**
     * Creates a modified 2x2 compacting recipe with a condition.
     *
     * @param input     The input item ({@link ItemLike}).
     * @param output    The output item ({@link ItemLike}).
     * @param condition The condition ({@link ICondition}) for the recipe.
     * @param consumer  The {@link FinishedRecipe} consumer.
     */
    protected static void modifiedConditionalCompact(ItemLike input, ItemLike output, ICondition condition, Consumer<FinishedRecipe> consumer) {
        ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(recipe -> modifiedCompact(input, output, recipe))
                .generateAdvancement(recipeDir("minecraft", DECORATIONS_DIR + itemName(input.asItem())))
                .build(consumer, recipeDir("minecraft", itemName(input.asItem())));
    }

    /**
     * Creates a simple 2x2 compacting recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleSmallCompact(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        buildShapedRecipe(input, output, 1, consumer, recipeDir(PackedUp.MODID, itemName(output.asItem()) + "_from_" + itemName(input.asItem())), "##", "##");
    }

    /**
     * Creates a simple stacked recipe (2x1).
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleStacked(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        buildShapedRecipe(input, output, 1, consumer, recipeDir(PackedUp.MODID, itemName(output.asItem()) + "_from_" + itemName(input.asItem())), "#", "#");
    }

    /**
     * Creates a simple slab recipe (3x1).
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleSlab(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        buildShapedRecipe(input, output, 6, consumer, "###");
    }

    /**
     * Creates a simple stairs recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleStairs(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        buildShapedRecipe(input, output, 4, consumer, "#  ", "## ", "###");
    }

    // --------------------------
    // Shapeless Recipes
    // --------------------------

    /**
     * Creates a simple shapeless unpacking recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param split    Whether to split the product name.
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleShapeless(ItemLike input, ItemLike output, boolean split, Consumer<FinishedRecipe> consumer) {
        shapelessRecipe(input, output, consumer, split ? nameFromSplit(itemName(input.asItem()), itemName(output.asItem()), false) : "_" + itemName(input.asItem()));
    }

    /**
     * Creates a simple shapeless unpacking recipe with a condition.
     *
     * @param input     The input item ({@link ItemLike}).
     * @param output    The output item ({@link ItemLike}).
     * @param split     Whether to split the product name.
     * @param condition The condition ({@link ICondition}) for the recipe.
     * @param consumer  The {@link FinishedRecipe} consumer.
     */
    protected static void simpleConditionalShapeless(ItemLike input, ItemLike output, boolean split, ICondition condition, Consumer<FinishedRecipe> consumer) {
        String type = split ? nameFromSplit(itemName(input.asItem()), itemName(output.asItem()), false) : "_" + itemName(input.asItem());
        String recipeName = itemName(output.asItem()) + "_from" + type;

        ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(recipe -> simpleShapeless(input, output, split, recipe))
                .generateAdvancement(recipeDir(PackedUp.MODID, MISC_DIR + recipeName))
                .build(consumer, recipeDir(PackedUp.MODID, recipeName));
    }

    /**
     * Creates a simple shapeless unpacking recipe with a custom type.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param before   Whether to split the product name before or after the type.
     * @param type     The custom type for the recipe.
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleShapeless(ItemLike input, ItemLike output, boolean before, String type, Consumer<FinishedRecipe> consumer) {
        String prefix = before ? nameFromSplit(itemName(input.asItem()), itemName(output.asItem()), true) : "";
        shapelessRecipe(input, output, consumer, "_" + prefix + type);
    }

    /**
     * Creates a simple shapeless unpacking recipe with a custom type and condition.
     *
     * @param input     The input item ({@link ItemLike}).
     * @param output    The output item ({@link ItemLike}).
     * @param before    Whether to split the product name before or after the type.
     * @param type      The custom type for the recipe.
     * @param condition The condition ({@link ICondition}) for the recipe.
     * @param consumer  The {@link FinishedRecipe} consumer.
     */
    protected static void simpleConditionalShapeless(ItemLike input, ItemLike output, boolean before, String type, ICondition condition, Consumer<FinishedRecipe> consumer) {
        String prefix = before ? nameFromSplit(itemName(input.asItem()), itemName(output.asItem()), true) : "";
        String recipeName = itemName(output.asItem()) + "_from_" + prefix + type;

        ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(recipe -> simpleShapeless(input, output, before, type, recipe))
                .generateAdvancement(recipeDir(PackedUp.MODID, MISC_DIR + recipeName))
                .build(consumer, recipeDir(PackedUp.MODID, recipeName));
    }

    /**
     * Creates a modified shapeless unpacking recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param modID    The mod ID for the recipe.
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void modifiedShapeless(ItemLike input, ItemLike output, int count, String modID, Consumer<FinishedRecipe> consumer) {
        buildShapelessRecipe(input, output, count, consumer, recipeDir(modID, itemName(output.asItem()) + "_from_" + itemName(input.asItem())));
    }

    /**
     * Creates a modified shapeless unpacking recipe with a condition.
     *
     * @param input     The input item ({@link ItemLike}).
     * @param output    The output item ({@link ItemLike}).
     * @param modID     The mod ID for the recipe.
     * @param condition The condition ({@link ICondition}) for the recipe.
     * @param consumer  The {@link FinishedRecipe} consumer.
     */
    protected static void modifiedConditionalShapeless(ItemLike input, ItemLike output, int count, String modID, ICondition condition, Consumer<FinishedRecipe> consumer) {
        String recipeName = itemName(output.asItem()) + "_from_" + itemName(input.asItem());

        ConditionalRecipe.builder().addCondition(condition)
                .addRecipe(recipe -> modifiedShapeless(input, output, count, modID, recipe))
                .generateAdvancement(recipeDir(modID, MISC_DIR + recipeName))
                .build(consumer, recipeDir(modID, recipeName));
    }

    // --------------------------
    // Book Bundle Recipes
    // --------------------------

    /**
     * Creates a book bundle dyeing recipe.
     *
     * @param dye      The dye tag.
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void bookBundleDyeing(TagKey<Item> dye, ItemLike output, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 8)
                .pattern("###")
                .pattern("#o#")
                .pattern("###")
                .define('#', PUTags.BOOK_BUNDLES)
                .define('o', dye)
                .unlockedBy("has_book_bundle", hasItems(PUItems.BOOK_BUNDLE.get()))
                .save(consumer);
    }

    // --------------------------
    // Smelting Recipes
    // --------------------------


    /**
     * Creates the various thatch cooking recipes.
     *
     * @param input     The input item ({@link ItemLike}).
     * @param output    The output item ({@link ItemLike}).
     * @param consumer  The {@link FinishedRecipe} consumer.
     */
    protected static void simpleThatch(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        simpleFurnace(input, output, consumer);
        simpleCampfire(input, output, consumer);
        simpleSmoking(input, output, consumer);
    }

    /**
     * Creates a simple furnace smelting recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleFurnace(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        buildCookingRecipe(input, output, consumer, "smelting", 200);
    }

    /**
     * Creates a simple smoking recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleSmoking(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        buildCookingRecipe(input, output, consumer, "smoking", 100);
    }

    /**
     * Creates a simple blasting recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleBlasting(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        buildCookingRecipe(input, output, consumer, "blasting", 100);
    }

    /**
     * Creates a simple campfire cooking recipe.
     *
     * @param input    The input item ({@link ItemLike}).
     * @param output   The output item ({@link ItemLike}).
     * @param consumer The {@link FinishedRecipe} consumer.
     */
    protected static void simpleCampfire(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        buildCookingRecipe(input, output, consumer, "campfire_cooking", 600);
    }

    // ============================================================
    // Helper Methods
    // ============================================================

    private static void shapedRecipe(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer, String... patterns) {
        buildShapedRecipe(input, output, 1, consumer, recipeDir(PackedUp.MODID, itemName(output.asItem())), patterns);
    }

    private static void modifiedShapedRecipe(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer, String... patterns) {
        buildShapedRecipe(input, output, 1, consumer, patterns);
    }

    private static void buildShapedRecipe(ItemLike input, ItemLike output, int count, Consumer<FinishedRecipe> consumer, ResourceLocation recipeName, String... patterns) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, count);
        for (String pattern : patterns) {
            builder.pattern(pattern);
        }
        builder.define('#', input)
                .unlockedBy("has_" + itemName(input.asItem()), hasItems(input))
                .save(consumer, recipeName);
    }

    private static void buildShapedRecipe(ItemLike input, ItemLike output, int count, Consumer<FinishedRecipe> consumer, String... patterns) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, count);
        for (String pattern : patterns) {
            builder.pattern(pattern);
        }
        builder.define('#', input)
                .unlockedBy("has_" + itemName(input.asItem()), hasItems(input))
                .save(consumer);
    }

    private static void shapelessRecipe(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer, String suffix) {
        buildShapelessRecipe(input, output, consumer, recipeDir(PackedUp.MODID, itemName(output.asItem()) + "_from_" + suffix));
    }

    private static void buildShapelessRecipe(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer, ResourceLocation recipeName) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 9)
                .requires(input)
                .unlockedBy("has_" + itemName(input.asItem()), hasItems(input))
                .save(consumer, recipeName);
    }

    private static void buildShapelessRecipe(ItemLike input, ItemLike output, int count, Consumer<FinishedRecipe> consumer, ResourceLocation recipeName) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, count)
                .requires(input)
                .unlockedBy("has_" + itemName(input.asItem()), hasItems(input))
                .save(consumer, recipeName);
    }

    private static void buildCookingRecipe(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer, String type, int cookingTime) {
        String itemOut = PackedUp.resLoc(itemName(output.asItem())).toString();
        Ingredient ingredient = Ingredient.of(input);

        switch (type) {
            case "smelting" -> SimpleCookingRecipeBuilder.smelting(ingredient, RecipeCategory.DECORATIONS, output, 0.35F, cookingTime)
                    .unlockedBy("has_" + itemName(input.asItem()), hasItems(input))
                    .save(consumer);
            case "smoking" -> SimpleCookingRecipeBuilder.smoking(ingredient, RecipeCategory.DECORATIONS, output, 0.35F, cookingTime)
                    .unlockedBy("has_" + itemName(input.asItem()), hasItems(input))
                    .save(consumer, itemOut + "_from_smoking");
            case "blasting" -> SimpleCookingRecipeBuilder.blasting(ingredient, RecipeCategory.DECORATIONS, output, 0.35F, cookingTime)
                    .unlockedBy("has_" + itemName(input.asItem()), hasItems(input))
                    .save(consumer, itemOut + "_from_blasting");
            case "campfire_cooking" -> SimpleCookingRecipeBuilder.campfireCooking(ingredient, RecipeCategory.DECORATIONS, output, 0.35F, cookingTime)
                    .unlockedBy("has_" + itemName(input.asItem()), hasItems(input))
                    .save(consumer, itemOut + "_from_campfire_cooking");
        }
    }

}