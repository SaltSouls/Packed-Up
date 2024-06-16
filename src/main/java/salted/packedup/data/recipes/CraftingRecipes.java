package salted.packedup.data.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUItems;

import java.util.Arrays;
import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class CraftingRecipes {
    protected static ResourceLocation recipeDir(String modID, String path) {
        return new ResourceLocation(modID, path);
    }

    public static void register(Consumer<FinishedRecipe> consumer) {
        recipesCompacting(consumer);
        recipesUnique(consumer);
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
        simpleCombined(PUItems.SWEET_BERRY_BASKET.get(), Items.SWEET_BERRIES, false, "basket", consumer);
        simpleCombined(PUItems.GLOW_BERRY_BASKET.get(), Items.GLOW_BERRIES, false, "basket", consumer);
        simpleCombined(PUItems.APPLE_BASKET.get(), Items.APPLE, consumer);
        simpleCombined(PUItems.GOLDEN_APPLE_BASKET.get(), Items.GOLDEN_APPLE, consumer);
        // fish barrels
        simpleCombined(PUItems.COD_BARREL.get(), Items.COD, consumer);
        simpleCombined(PUItems.SALMON_BARREL.get(), Items.SALMON, consumer);
        // resource crates
        simpleCombined(PUItems.COBBLESTONE_CRATE.get(), Items.COBBLESTONE, consumer);
        simpleCombined(PUItems.COBBLED_DEEPSLATE_CRATE.get(), Items.COBBLED_DEEPSLATE, consumer);
        simpleCombined(PUItems.ANDESITE_CRATE.get(), Items.ANDESITE, consumer);
        simpleCombined(PUItems.DIORITE_CRATE.get(), Items.DIORITE, consumer);
        simpleCombined(PUItems.GRANITE_CRATE.get(), Items.GRANITE, consumer);
        simpleCombined(PUItems.TUFF_CRATE.get(), Items.TUFF, consumer);
        simpleCombined(PUItems.BLACKSTONE_CRATE.get(), Items.BLACKSTONE, consumer);
        simpleCombined(PUItems.BASALT_CRATE.get(), Items.BASALT, consumer);
        simpleCombined(PUItems.RAW_COPPER_CRATE.get(), Items.RAW_COPPER_BLOCK, false, "crate", consumer);
        simpleCombined(PUItems.RAW_IRON_CRATE.get(), Items.RAW_IRON_BLOCK, false, "crate", consumer);
        simpleCombined(PUItems.RAW_GOLD_CRATE.get(), Items.RAW_GOLD_BLOCK, false, "crate", consumer);
        // reinforced crates
        simpleCombined(PUItems.REINFORCED_COBBLESTONE_CRATE.get(), PUItems.COBBLESTONE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_COBBLED_DEEPSLATE_CRATE.get(), PUItems.COBBLED_DEEPSLATE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_ANDESITE_CRATE.get(), PUItems.ANDESITE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_DIORITE_CRATE.get(), PUItems.DIORITE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_GRANITE_CRATE.get(), PUItems.GRANITE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_TUFF_CRATE.get(), PUItems.TUFF_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_BLACKSTONE_CRATE.get(), PUItems.BLACKSTONE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_BASALT_CRATE.get(), PUItems.BASALT_CRATE.get(), true, "crate", consumer);
        // misc crates
        simpleCombined(PUItems.GUNPOWDER_CRATE.get(), Items.GUNPOWDER, consumer);
        // produce crates
        simpleCombined(PUItems.GOLDEN_CARROT_CRATE.get(), Items.GOLDEN_CARROT, consumer);
        simpleCombined(PUItems.EGG_CRATE.get(), Items.EGG, consumer);
        simpleCombined(PUItems.RED_MUSHROOM_CRATE.get(), Items.RED_MUSHROOM, consumer);
        simpleCombined(PUItems.BROWN_MUSHROOM_CRATE.get(), Items.BROWN_MUSHROOM, consumer);
        // resource bags
        simpleCombined(PUItems.DIRT_BAG.get(), Items.DIRT, consumer);
        simpleCombined(PUItems.ROOTED_DIRT_BAG.get(), Items.ROOTED_DIRT, consumer);
        simpleCombined(PUItems.COARSE_DIRT_BAG.get(), Items.COARSE_DIRT, consumer);
        simpleCombined(PUItems.GRAVEL_BAG.get(), Items.GRAVEL, consumer);
        // material bags
        simpleCombined(PUItems.COCOA_BEAN_BAG.get(), Items.COCOA_BEANS, false, "bag", consumer);
        simpleCombined(PUItems.SUGAR_BAG.get(), Items.SUGAR, consumer);
        simpleCombined(PUItems.NETHER_WART_BAG.get(), Items.NETHER_WART, consumer);
        simpleCombined(PUItems.GLOWSTONE_DUST_BAG.get(), Items.GLOWSTONE_DUST, consumer);
        // brick piles
        simpleCombined(PUItems.BRICK_PILE.get(), Items.BRICK, consumer);
        simpleCombined(PUItems.NETHER_BRICK_PILE.get(), Items.NETHER_BRICK, consumer);
        simpleCombined(PUItems.STONE_PILE.get(), Items.STONE, consumer);
        simpleCombined(PUItems.DEEPSLATE_PILE.get(), Items.DEEPSLATE, consumer);
        simpleCombined(PUItems.CALCITE_PILE.get(), Items.CALCITE, consumer);
        // resource pallets
        simpleCombined(PUItems.BRICK_PALLET.get(), PUItems.BRICK_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.NETHER_BRICK_PALLET.get(), PUItems.NETHER_BRICK_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.STONE_PALLET.get(), PUItems.STONE_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.DEEPSLATE_PALLET.get(), PUItems.DEEPSLATE_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.CALCITE_PALLET.get(), PUItems.CALCITE_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.COPPER_PALLET.get(), Items.COPPER_BLOCK, false, "pallet", consumer);
        simpleCombined(PUItems.IRON_PALLET.get(), Items.IRON_BLOCK, false, "pallet", consumer);
        simpleCombined(PUItems.GOLD_PALLET.get(), Items.GOLD_BLOCK, false, "pallet", consumer);
        simpleCombined(PUItems.NETHERITE_PALLET.get(), Items.NETHERITE_BLOCK, false, "pallet", consumer);
    }

    private static void recipesModifiedVanilla(Consumer<FinishedRecipe> consumer) {
        modifiedCombined(Items.GLOWSTONE, Items.GLOWSTONE_DUST, consumer);
        modifiedCombined(Items.NETHER_WART_BLOCK, Items.NETHER_WART, consumer);
    }

    private static void recipesUnique(Consumer<FinishedRecipe> consumer) {
        // crate lid
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PUItems.CRATE_LID.get(), 1)
                .pattern(" I ")
                .pattern("ITI")
                .pattern(" I ")
                .define('T', ItemTags.WOODEN_TRAPDOORS)
                .define('I', Tags.Items.NUGGETS_IRON)
                .unlockedBy("has_iron_nugget", hasItems(Items.IRON_NUGGET))
                .save(consumer);
        // reinforced crate lid
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, PUItems.REINFORCED_CRATE_LID.get(), 1)
                .requires(PUItems.CRATE_LID.get())
                .requires(Tags.Items.INGOTS_IRON)
                .unlockedBy("has_iron_ingot", hasItems(Items.IRON_INGOT))
                .save(consumer);
    }

    // combined compacting/unpacking
    private static void simpleCombined(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        simpleCompact(input, output, consumer);
        simpleShapeless(input, output, consumer);
    }

    private static void simpleCombined(ItemLike input, ItemLike output, Boolean before, String type, Consumer<FinishedRecipe> consumer) {
        simpleCompact(input, output, consumer);
        simpleShapeless(input, output, before, type, consumer);
    }

    private static void modifiedCombined(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        modifiedCompact(input, output, consumer);
        modifiedShapeless(input, output, consumer);
    }

    // easy shaped recipes
    private static void simpleCompact(ItemLike output, ItemLike input, Consumer<FinishedRecipe> consumer) {
        String resource = itemName((Item) input);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy("has_" + resource, hasItems(input))
                .save(consumer);
    }

    private static void modifiedCompact(ItemLike output, ItemLike input, Consumer<FinishedRecipe> consumer) {
        String resource = itemName((Item) input);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, output, 1)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy("has_" + resource, hasItems(input))
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
                .save(consumer, recipeDir(PackedUp.MODID, resource + "_from" + type));
    }

    private static void simpleShapeless(ItemLike input, ItemLike output, boolean before, String type, Consumer<FinishedRecipe> consumer) {
        String item = itemName((Item) input);
        String resource = itemName((Item) output);
        String prefix = "";
        if (before) {
            prefix = nameFromSplit(item, resource, true);
        }

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 9)
                .requires(input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, recipeDir(PackedUp.MODID, resource + "_from_" + prefix + type));
    }

    private static void modifiedShapeless(ItemLike input, ItemLike output, Consumer<FinishedRecipe> consumer) {
        String item = itemName((Item) input);
        String resource = itemName((Item) output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, output, 4)
                .requires(input)
                .unlockedBy("has_" + item, hasItems(input))
                .save(consumer, recipeDir(PackedUp.MODID, resource + "_from_" + input));
    }

}
