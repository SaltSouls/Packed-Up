package salted.packedup.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PURegistry;
import salted.packedup.common.registry.helpers.Organizer;
import salted.packedup.common.tag.PUTags;

import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;
import static salted.packedup.data.utils.ConditionalUtils.Not;
import static salted.packedup.data.utils.ConditionalUtils.modLoaded;

public class PUCraftingRecipes extends PURecipeBuilder {

    public PUCraftingRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        register(consumer);
    }

    public void register(Consumer<FinishedRecipe> consumer) {
        recipesCompacting(consumer);
        recipesModifiedVanilla(consumer);
        recipesUnique(consumer);
    }

    // Recipe Categories
    private void recipesCompacting(Consumer<FinishedRecipe> consumer) {
        // Produce Baskets
        registerBasketRecipes(consumer);

        // Fish Barrels
        registerFishBarrelRecipes(consumer);

        // Resource Crates
        registerResourceCrateRecipes(consumer);

        // Reinforced Crates
        registerReinforcedCrateRecipes(consumer);

        // Misc Crates
        registerMiscCrateRecipes(consumer);

        // Produce Crates
        registerProduceCrateRecipes(consumer);

        // Resource Bags
        registerResourceBagRecipes(consumer);

        // Material Bags
        registerMaterialBagRecipes(consumer);

        // Piles
        registerPileRecipes(consumer);

        // Resource Pallets
        registerResourcePalletRecipes(consumer);

        // Book Blocks
        registerBookBlockRecipes(consumer);

        // Turf Blocks
        registerTurfBlockRecipes(consumer);

        // Grass Bale
        registerGrassBaleRecipes(consumer);

        // Grass Thatch
        registerGrassThatchRecipes(consumer);

        // Drum Barrels
        registerDrumBarrelRecipes(consumer);
    }

    private void recipesModifiedVanilla(Consumer<FinishedRecipe> consumer) {
        modifiedCombined(Items.GLOWSTONE, Items.GLOWSTONE_DUST, 4, PackedUp.MODID, consumer);
        modifiedCombined(Items.NETHER_WART_BLOCK, Items.NETHER_WART, 4, PackedUp.MODID, consumer);
    }

    private void recipesUnique(Consumer<FinishedRecipe> consumer) {
        // Crate Lid
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PURegistry.CRATE_LID.get().asItem(), 1)
                .pattern(" I ")
                .pattern("ITI")
                .pattern(" I ")
                .define('T', ItemTags.WOODEN_TRAPDOORS)
                .define('I', Tags.Items.NUGGETS_IRON)
                .unlockedBy("has_wooden_trapdoor", has(ItemTags.WOODEN_TRAPDOORS))
                .unlockedBy("has_iron_nugget", has(Tags.Items.NUGGETS_IRON))
                .save(consumer);

        // Reinforced Crate Lid
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, PURegistry.REINFORCED_CRATE_LID.get().asItem(), 1)
                .requires(PURegistry.CRATE_LID.get().asItem())
                .requires(Tags.Items.INGOTS_IRON)
                .unlockedBy("has_crate_lid", hasItems(PURegistry.CRATE_LID.get().asItem()))
                .save(consumer);

        // Pallet
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PURegistry.PALLET.get().asItem(), 12)
                .pattern("SSS")
                .pattern("###")
                .pattern("SSS")
                .define('S', Items.STICK)
                .define('#', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_wooden_slabs", has(ItemTags.WOODEN_SLABS))
                .save(consumer);

        // Fluid Gauge
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PURegistry.FLUID_GAUGE.get().asItem(), 1)
                .pattern("G")
                .pattern("#")
                .pattern("I")
                .define('G', Tags.Items.GLASS_PANES)
                .define('#', Items.COMPARATOR)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_comparator", has(Items.COMPARATOR))
                .save(consumer);
    }

    // Helper Methods for Recipe Registration
    private void registerBasketRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.SWEET_BERRY_BASKET.get().asItem(), Items.SWEET_BERRIES, false, "basket", consumer);
        simpleCombined(PURegistry.GLOW_BERRY_BASKET.get().asItem(), Items.GLOW_BERRIES, false, "basket", consumer);
        simpleCombined(PURegistry.APPLE_BASKET.get().asItem(), Items.APPLE, true, consumer);
        simpleCombined(PURegistry.GOLDEN_APPLE_BASKET.get().asItem(), Items.GOLDEN_APPLE, true, consumer);
    }

    private void registerFishBarrelRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.COD_BARREL.get().asItem(), Items.COD, true, consumer);
        simpleCombined(PURegistry.SALMON_BARREL.get().asItem(), Items.SALMON, true, consumer);
    }

    private void registerResourceCrateRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.COBBLESTONE_CRATE.get().asItem(), Items.COBBLESTONE, true, consumer);
        simpleCombined(PURegistry.COBBLED_DEEPSLATE_CRATE.get().asItem(), Items.COBBLED_DEEPSLATE, true, consumer);
        simpleCombined(PURegistry.ANDESITE_CRATE.get().asItem(), Items.ANDESITE, true, consumer);
        simpleCombined(PURegistry.DIORITE_CRATE.get().asItem(), Items.DIORITE, true, consumer);
        simpleCombined(PURegistry.GRANITE_CRATE.get().asItem(), Items.GRANITE, true, consumer);
        simpleCombined(PURegistry.TUFF_CRATE.get().asItem(), Items.TUFF, true, consumer);
        simpleCombined(PURegistry.BLACKSTONE_CRATE.get().asItem(), Items.BLACKSTONE, true, consumer);
        simpleCombined(PURegistry.BASALT_CRATE.get().asItem(), Items.BASALT, true, consumer);
        simpleCombined(PURegistry.NETHERRACK_CRATE.get().asItem(), Items.NETHERRACK, true, consumer);
        simpleCombined(PURegistry.RAW_COPPER_CRATE.get().asItem(), Items.RAW_COPPER_BLOCK, false, "crate", consumer);
        simpleCombined(PURegistry.RAW_IRON_CRATE.get().asItem(), Items.RAW_IRON_BLOCK, false, "crate", consumer);
        simpleCombined(PURegistry.RAW_GOLD_CRATE.get().asItem(), Items.RAW_GOLD_BLOCK, false, "crate", consumer);
    }

    private void registerReinforcedCrateRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.REINFORCED_COBBLESTONE_CRATE.get().asItem(), PURegistry.COBBLESTONE_CRATE.get().asItem(), true, "crate", consumer);
        simpleCombined(PURegistry.REINFORCED_COBBLED_DEEPSLATE_CRATE.get().asItem(), PURegistry.COBBLED_DEEPSLATE_CRATE.get().asItem(), true, "crate", consumer);
        simpleCombined(PURegistry.REINFORCED_ANDESITE_CRATE.get().asItem(), PURegistry.ANDESITE_CRATE.get().asItem(), true, "crate", consumer);
        simpleCombined(PURegistry.REINFORCED_DIORITE_CRATE.get().asItem(), PURegistry.DIORITE_CRATE.get().asItem(), true, "crate", consumer);
        simpleCombined(PURegistry.REINFORCED_GRANITE_CRATE.get().asItem(), PURegistry.GRANITE_CRATE.get().asItem(), true, "crate", consumer);
        simpleCombined(PURegistry.REINFORCED_TUFF_CRATE.get().asItem(), PURegistry.TUFF_CRATE.get().asItem(), true, "crate", consumer);
        simpleCombined(PURegistry.REINFORCED_BLACKSTONE_CRATE.get().asItem(), PURegistry.BLACKSTONE_CRATE.get().asItem(), true, "crate", consumer);
        simpleCombined(PURegistry.REINFORCED_BASALT_CRATE.get().asItem(), PURegistry.BASALT_CRATE.get().asItem(), true, "crate", consumer);
        simpleCombined(PURegistry.REINFORCED_NETHERRACK_CRATE.get().asItem(), PURegistry.NETHERRACK_CRATE.get().asItem(), true, "crate", consumer);
    }

    private void registerMiscCrateRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.GUNPOWDER_CRATE.get().asItem(), Items.GUNPOWDER, true, consumer);
        simpleCombined(PURegistry.QUARTZ_CRATE.get().asItem(), Items.QUARTZ, true, consumer);
        simpleCombined(PURegistry.AMETHYST_CRATE.get().asItem(), Items.AMETHYST_SHARD, false, "crate", consumer);
        simpleCombined(PURegistry.ECHO_SHARD_CRATE.get().asItem(), Items.ECHO_SHARD, true, consumer);
    }

    private void registerProduceCrateRecipes(Consumer<FinishedRecipe> consumer) {
        simpleConditionalCombined(PURegistry.CARROT_CRATE.get().asItem(), Items.CARROT, true, Not(modLoaded("farmersdelight")), consumer);
        simpleCombined(PURegistry.GOLDEN_CARROT_CRATE.get().asItem(), Items.GOLDEN_CARROT, true, consumer);
        simpleConditionalCombined(PURegistry.POTATO_CRATE.get().asItem(), Items.POTATO, true, Not(modLoaded("farmersdelight")), consumer);
        simpleConditionalCombined(PURegistry.BEETROOT_CRATE.get().asItem(), Items.BEETROOT, true, Not(modLoaded("farmersdelight")), consumer);
        simpleCombined(PURegistry.EGG_CRATE.get().asItem(), Items.EGG, true, consumer);
        simpleCombined(PURegistry.RED_MUSHROOM_CRATE.get().asItem(), Items.RED_MUSHROOM, true, consumer);
        simpleCombined(PURegistry.BROWN_MUSHROOM_CRATE.get().asItem(), Items.BROWN_MUSHROOM, true, consumer);
        simpleCombined(PURegistry.CRIMSON_FUNGUS_CRATE.get().asItem(), Items.CRIMSON_FUNGUS, true, consumer);
        simpleCombined(PURegistry.WARPED_FUNGUS_CRATE.get().asItem(), Items.WARPED_FUNGUS, true, consumer);
    }

    private void registerResourceBagRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.DIRT_BAG.get().asItem(), Items.DIRT, true, consumer);
        simpleCombined(PURegistry.ROOTED_DIRT_BAG.get().asItem(), Items.ROOTED_DIRT, true, consumer);
        simpleCombined(PURegistry.COARSE_DIRT_BAG.get().asItem(), Items.COARSE_DIRT, true, consumer);
        simpleCombined(PURegistry.GRAVEL_BAG.get().asItem(), Items.GRAVEL, true, consumer);
    }

    private void registerMaterialBagRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.COCOA_BEAN_BAG.get().asItem(), Items.COCOA_BEANS, false, "bag", consumer);
        simpleCombined(PURegistry.SUGAR_BAG.get().asItem(), Items.SUGAR, true, consumer);
        simpleCombined(PURegistry.NETHER_WART_BAG.get().asItem(), Items.NETHER_WART, true, consumer);
        simpleCombined(PURegistry.GLOWSTONE_DUST_BAG.get().asItem(), Items.GLOWSTONE_DUST, true, consumer);
        simpleCombined(PURegistry.ENDER_PEARL_BAG.get().asItem(), Items.ENDER_PEARL, true, consumer);
    }

    private void registerPileRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.BRICK_PILE.get().asItem(), Items.BRICK, true, consumer);
        simpleCombined(PURegistry.NETHER_BRICK_PILE.get().asItem(), Items.NETHER_BRICK, true, consumer);
        simpleCombined(PURegistry.STONE_PILE.get().asItem(), Items.STONE, true, consumer);
        simpleCombined(PURegistry.DEEPSLATE_PILE.get().asItem(), Items.DEEPSLATE, true, consumer);
        simpleCombined(PURegistry.CALCITE_PILE.get().asItem(), Items.CALCITE, true, consumer);
    }

    private void registerResourcePalletRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.BRICK_PALLET.get().asItem(), PURegistry.BRICK_PILE.get().asItem(), false, "pallet", consumer);
        simpleCombined(PURegistry.NETHER_BRICK_PALLET.get().asItem(), PURegistry.NETHER_BRICK_PILE.get().asItem(), false, "pallet", consumer);
        simpleCombined(PURegistry.STONE_PALLET.get().asItem(), PURegistry.STONE_PILE.get().asItem(), false, "pallet", consumer);
        simpleCombined(PURegistry.DEEPSLATE_PALLET.get().asItem(), PURegistry.DEEPSLATE_PILE.get().asItem(), false, "pallet", consumer);
        simpleCombined(PURegistry.CALCITE_PALLET.get().asItem(), PURegistry.CALCITE_PILE.get().asItem(), false, "pallet", consumer);
        simpleCombined(PURegistry.COPPER_PALLET.get().asItem(), Items.COPPER_BLOCK, false, "pallet", consumer);
        simpleCombined(PURegistry.IRON_PALLET.get().asItem(), Items.IRON_BLOCK, false, "pallet", consumer);
        simpleCombined(PURegistry.GOLD_PALLET.get().asItem(), Items.GOLD_BLOCK, false, "pallet", consumer);
        simpleCombined(PURegistry.DIAMOND_PALLET.get().asItem(), Items.DIAMOND_BLOCK, false, "pallet", consumer);
        simpleCombined(PURegistry.EMERALD_PALLET.get().asItem(), Items.EMERALD_BLOCK, false, "pallet", consumer);
        simpleCombined(PURegistry.NETHERITE_PALLET.get().asItem(), Items.NETHERITE_BLOCK, false, "pallet", consumer);
    }

    private void registerBookBlockRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCompact(Items.BOOK, PURegistry.BOOK_BUNDLE.get().asItem(), consumer);
        simpleShapeless(PUTags.BOOK_BUNDLES, Items.BOOK, false, consumer);

        // Book Bundle Recipes
        simpleSmallCompact(PURegistry.BOOK_PILE.get().asItem(), PURegistry.BOOK_BUNDLE.get().asItem(), consumer);
        for (Organizer.ColoredBookSet set : PURegistry.COLORED_BOOKS.values()) {
            simpleSmallCompact(set.getPile().asItem(), set.getBundle().asItem(), consumer);
        }

        // Book Slab Recipes
        simpleSlab(PURegistry.BOOK_BUNDLE.get().asItem(), PURegistry.BOOK_BUNDLE_SLAB.get().asItem(), consumer);
        for (Organizer.ColoredBookSet set : PURegistry.COLORED_BOOKS.values()) {
            simpleSmallCompact(set.getBundle().asItem(), set.getSlab().asItem(), consumer);
        }
        // Book Slab from Pile Recipes
        simpleStacked(PURegistry.BOOK_PILE.get().asItem(), PURegistry.BOOK_BUNDLE_SLAB.get().asItem(), consumer);
        for (Organizer.ColoredBookSet set : PURegistry.COLORED_BOOKS.values()) {
            simpleSmallCompact(set.getPile().asItem(), set.getSlab().asItem(), consumer);
        }

        // Colored Book Recipes
        for (Organizer.ColoredBookSet set : PURegistry.COLORED_BOOKS.values()) {
            bookBundleDyeing(set.getColor().getTag(), set.getBundle().asItem(), consumer);
        }
    }

    private void registerTurfBlockRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.GRASS_TURF.get().asItem(), Blocks.GRASS_BLOCK, false, consumer);
        simpleCombined(PURegistry.PODZOL_TURF.get().asItem(), Blocks.PODZOL, false, consumer);
        simpleCombined(PURegistry.MYCELIUM_TURF.get().asItem(), Blocks.MYCELIUM, false, consumer);

        // Turf Blocks from Layer
        simpleSmallCompact(PURegistry.GRASS_TURF_LAYER.get().asItem(), PURegistry.GRASS_TURF.get().asItem(), consumer);
        simpleSmallCompact(PURegistry.PODZOL_TURF_LAYER.get().asItem(), PURegistry.PODZOL_TURF.get().asItem(), consumer);
        simpleSmallCompact(PURegistry.MYCELIUM_TURF_LAYER.get().asItem(), PURegistry.MYCELIUM_TURF.get().asItem(), consumer);
    }

    private void registerGrassBaleRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PURegistry.GRASS_BALE.get().asItem(), Blocks.GRASS, false, consumer);
        simpleSmallCompact(Blocks.TALL_GRASS, PURegistry.GRASS_BALE.get().asItem(), consumer);
    }

    private void registerGrassThatchRecipes(Consumer<FinishedRecipe> consumer) {
        simpleThatch(PURegistry.GRASS_BALE.get().asItem(), PURegistry.GRASS_THATCH.get().asItem(), consumer);
        simpleStairs(PURegistry.GRASS_THATCH.get().asItem(), PURegistry.GRASS_THATCH_STAIRS.get().asItem(), consumer);
        simpleSlab(PURegistry.GRASS_THATCH.get().asItem(), PURegistry.GRASS_THATCH_SLAB.get().asItem(), consumer);
    }

    private void registerDrumBarrelRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCompact(Items.BUCKET, PURegistry.DRUM_BARREL.get().asItem(), consumer);
        simpleShapeless(PUTags.DRUM_BARRELS, Items.BUCKET, false, consumer);

        // Colored Drum Barrel Recipes
        Organizer.COLORED_DRUMS.forEach((color, drum) ->
                drumBarrelDyeing(color.getTag(), drum.get().asItem(), consumer));
    }

}
