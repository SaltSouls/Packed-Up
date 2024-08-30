package salted.packedup.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUItems;
import salted.packedup.common.tag.PUTags;

import java.util.Arrays;
import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class PURecipeBuilder extends RecipeProvider {
    public PURecipeBuilder(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // This is never used and shouldn't be used. It is only needed
        // so that I can separate the builders from the registration.
    }

    // general pathing/convenience functions
    protected static ResourceLocation recipeDir(String modID, String path) {
        return new ResourceLocation(modID, path);
    }

    private static String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    /**
     * Splits the string and returns everything before or after the split.
     *
     * @param name      the name of the {@link Block} or {@link Item} we are using
     * @param substring the string at which the split occurs
     * @param before    the half of the string gotten
     * @return The desired half of the string array.
     */
    private static String nameFromSplit(String name, String substring, boolean before) {
        if (before) {
            return Arrays.stream(name.split(substring)).findFirst().get();
        } else return Arrays.stream(name.split(substring)).toList().get(1);
    }

    // recipe builders
    // combined compacting/unpacking
    protected static void simpleCombined(ItemLike product, ItemLike resource, boolean split, Consumer<FinishedRecipe> consumer) {
        simpleCompact(resource, product, consumer);
        simpleShapeless(product, resource, split, consumer);
    }

    protected static void simpleCombined(ItemLike product, ItemLike resource, boolean before, String type, Consumer<FinishedRecipe> consumer) {
        simpleCompact(resource, product, consumer);
        simpleShapeless(product, resource, before, type, consumer);
    }

    protected static void modifiedCombined(ItemLike product, ItemLike resource, Consumer<FinishedRecipe> consumer) {
        modifiedCompact(resource, product, consumer);
        modifiedShapeless(product, resource, consumer);
    }

    // easy shaped recipes
    protected static void simpleCompact(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String item = itemName(input.asItem());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer);
    }

    protected static void modifiedCompact(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String item = itemName(input.asItem());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 1)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer);
    }

    protected static void simpleSmallCompact(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String item = itemName(input.asItem());
        String resource = itemName(output.asItem());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 1)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, recipeDir(PackedUp.MODID, resource + "_from_" + item));
    }

    protected static void simpleStacked(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String item = itemName(input.asItem());
        String resource = itemName(output.asItem());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 1)
                .pattern("#")
                .pattern("#")
                .define('#', input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, recipeDir(PackedUp.MODID, resource + "_from_" + item));
    }

    protected static void simpleSlab(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String item = itemName(input.asItem());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 6)
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer);
    }

    // easy shapeless recipes
    protected static void simpleShapeless(ItemLike input, ItemLike output, boolean split, Consumer<FinishedRecipe> consumer) {
        String item = itemName(input.asItem());
        String resource = itemName(output.asItem());
        String type = "_" + item;
        if (split) {
            type = nameFromSplit(item, resource, false);
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 9)
                .requires(input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, recipeDir(PackedUp.MODID, resource + "_from" + type));
    }

    protected static void simpleShapeless(ItemLike input, ItemLike output, boolean before, String type, Consumer<FinishedRecipe> consumer) {
        String item = itemName(input.asItem());
        String resource = itemName(output.asItem());
        String prefix = "";
        if (before) {
            prefix = nameFromSplit(item, resource, true);
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 9)
                .requires(input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, recipeDir(PackedUp.MODID, resource + "_from_" + prefix + type));
    }

    protected static void modifiedShapeless(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String item = itemName(input.asItem());
        String resource = itemName(output.asItem());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 4)
                .requires(input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, recipeDir(PackedUp.MODID, resource + "_from_" + item));
    }

    // book bundle recipes
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
    
}
