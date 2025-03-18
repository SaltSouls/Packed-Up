package salted.packedup.data.recipes;

import com.google.common.collect.Sets;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUItems;

import java.util.Set;
import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;
import static salted.packedup.data.utils.ConditionalUtils.*;

public class PUCraftingRecipes extends PURecipeBuilder {

    public PUCraftingRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        register(consumer);
    }

    public void register(Consumer<FinishedRecipe> consumer) {
        recipesCompacting(consumer);
        recipesModifiedVanilla(consumer);
        recipesUnique(consumer);
    }

    // ============================================================
    // Recipe Categories
    // ============================================================

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
    }

    private void recipesModifiedVanilla(Consumer<FinishedRecipe> consumer) {
        modifiedCombined(Items.GLOWSTONE, Items.GLOWSTONE_DUST, 4, PackedUp.MODID, consumer);
        modifiedCombined(Items.NETHER_WART_BLOCK, Items.NETHER_WART, 4, PackedUp.MODID, consumer);
    }

    private void recipesUnique(Consumer<FinishedRecipe> consumer) {
        // Crate Lid
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PUItems.CRATE_LID.get(), 1)
                .pattern(" I ")
                .pattern("ITI")
                .pattern(" I ")
                .define('T', ItemTags.WOODEN_TRAPDOORS)
                .define('I', Tags.Items.NUGGETS_IRON)
                .unlockedBy("has_wooden_trapdoor", has(ItemTags.WOODEN_TRAPDOORS))
                .unlockedBy("has_iron_nugget", has(Tags.Items.NUGGETS_IRON))
                .save(consumer);

        // Reinforced Crate Lid
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, PUItems.REINFORCED_CRATE_LID.get(), 1)
                .requires(PUItems.CRATE_LID.get())
                .requires(Tags.Items.INGOTS_IRON)
                .unlockedBy("has_crate_lid", hasItems(PUItems.CRATE_LID.get()))
                .save(consumer);

        // Pallet
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PUItems.PALLET.get(), 12)
                .pattern("lll")
                .pattern("###")
                .pattern("lll")
                .define('l', Items.STICK)
                .define('#', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_wooden_slabs", has(ItemTags.WOODEN_SLABS))
                .save(consumer);

        // Colored Book Blocks
        registerColoredBookBlockRecipes(consumer);

        // Book Slabs
        registerBookSlabRecipes(consumer);

        // Grass Thatch
        registerGrassThatchRecipes(consumer);
    }

    // ============================================================
    // Helper Methods for Recipe Registration
    // ============================================================

    private void registerBasketRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.SWEET_BERRY_BASKET.get(), Items.SWEET_BERRIES, false, "basket", consumer);
        simpleCombined(PUItems.GLOW_BERRY_BASKET.get(), Items.GLOW_BERRIES, false, "basket", consumer);
        simpleCombined(PUItems.APPLE_BASKET.get(), Items.APPLE, true, consumer);
        simpleCombined(PUItems.GOLDEN_APPLE_BASKET.get(), Items.GOLDEN_APPLE, true, consumer);
    }

    private void registerFishBarrelRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.COD_BARREL.get(), Items.COD, true, consumer);
        simpleCombined(PUItems.SALMON_BARREL.get(), Items.SALMON, true, consumer);
    }

    private void registerResourceCrateRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.COBBLESTONE_CRATE.get(), Items.COBBLESTONE, true, consumer);
        simpleCombined(PUItems.COBBLED_DEEPSLATE_CRATE.get(), Items.COBBLED_DEEPSLATE, true, consumer);
        simpleCombined(PUItems.ANDESITE_CRATE.get(), Items.ANDESITE, true, consumer);
        simpleCombined(PUItems.DIORITE_CRATE.get(), Items.DIORITE, true, consumer);
        simpleCombined(PUItems.GRANITE_CRATE.get(), Items.GRANITE, true, consumer);
        simpleCombined(PUItems.TUFF_CRATE.get(), Items.TUFF, true, consumer);
        simpleCombined(PUItems.BLACKSTONE_CRATE.get(), Items.BLACKSTONE, true, consumer);
        simpleCombined(PUItems.BASALT_CRATE.get(), Items.BASALT, true, consumer);
        simpleCombined(PUItems.NETHERRACK_CRATE.get(), Items.NETHERRACK, true, consumer);
        simpleCombined(PUItems.RAW_COPPER_CRATE.get(), Items.RAW_COPPER_BLOCK, false, "crate", consumer);
        simpleCombined(PUItems.RAW_IRON_CRATE.get(), Items.RAW_IRON_BLOCK, false, "crate", consumer);
        simpleCombined(PUItems.RAW_GOLD_CRATE.get(), Items.RAW_GOLD_BLOCK, false, "crate", consumer);
    }

    private void registerReinforcedCrateRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.REINFORCED_COBBLESTONE_CRATE.get(), PUItems.COBBLESTONE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_COBBLED_DEEPSLATE_CRATE.get(), PUItems.COBBLED_DEEPSLATE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_ANDESITE_CRATE.get(), PUItems.ANDESITE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_DIORITE_CRATE.get(), PUItems.DIORITE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_GRANITE_CRATE.get(), PUItems.GRANITE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_TUFF_CRATE.get(), PUItems.TUFF_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_BLACKSTONE_CRATE.get(), PUItems.BLACKSTONE_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_BASALT_CRATE.get(), PUItems.BASALT_CRATE.get(), true, "crate", consumer);
        simpleCombined(PUItems.REINFORCED_NETHERRACK_CRATE.get(), PUItems.NETHERRACK_CRATE.get(), true, "crate", consumer);
    }

    private void registerMiscCrateRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.GUNPOWDER_CRATE.get(), Items.GUNPOWDER, true, consumer);
        simpleCombined(PUItems.QUARTZ_CRATE.get(), Items.QUARTZ, true, consumer);
        simpleCombined(PUItems.AMETHYST_CRATE.get(), Items.AMETHYST_SHARD, false, "crate", consumer);
        simpleCombined(PUItems.ECHO_SHARD_CRATE.get(), Items.ECHO_SHARD, true, consumer);
    }

    private void registerProduceCrateRecipes(Consumer<FinishedRecipe> consumer) {
        simpleConditionalCombined(PUItems.CARROT_CRATE.get(), Items.CARROT, true, Not(modLoaded("farmersdelight")), consumer);
        simpleCombined(PUItems.GOLDEN_CARROT_CRATE.get(), Items.GOLDEN_CARROT, true, consumer);
        simpleConditionalCombined(PUItems.POTATO_CRATE.get(), Items.POTATO, true, Not(modLoaded("farmersdelight")), consumer);
        simpleConditionalCombined(PUItems.BEETROOT_CRATE.get(), Items.BEETROOT, true, Not(modLoaded("farmersdelight")), consumer);
        simpleCombined(PUItems.EGG_CRATE.get(), Items.EGG, true, consumer);
        simpleCombined(PUItems.RED_MUSHROOM_CRATE.get(), Items.RED_MUSHROOM, true, consumer);
        simpleCombined(PUItems.BROWN_MUSHROOM_CRATE.get(), Items.BROWN_MUSHROOM, true, consumer);
        simpleCombined(PUItems.CRIMSON_FUNGUS_CRATE.get(), Items.CRIMSON_FUNGUS, true, consumer);
        simpleCombined(PUItems.WARPED_FUNGUS_CRATE.get(), Items.WARPED_FUNGUS, true, consumer);
    }

    private void registerResourceBagRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.DIRT_BAG.get(), Items.DIRT, true, consumer);
        simpleCombined(PUItems.ROOTED_DIRT_BAG.get(), Items.ROOTED_DIRT, true, consumer);
        simpleCombined(PUItems.COARSE_DIRT_BAG.get(), Items.COARSE_DIRT, true, consumer);
        simpleCombined(PUItems.GRAVEL_BAG.get(), Items.GRAVEL, true, consumer);
    }

    private void registerMaterialBagRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.COCOA_BEAN_BAG.get(), Items.COCOA_BEANS, false, "bag", consumer);
        simpleCombined(PUItems.SUGAR_BAG.get(), Items.SUGAR, true, consumer);
        simpleCombined(PUItems.NETHER_WART_BAG.get(), Items.NETHER_WART, true, consumer);
        simpleCombined(PUItems.GLOWSTONE_DUST_BAG.get(), Items.GLOWSTONE_DUST, true, consumer);
        simpleCombined(PUItems.ENDER_PEARL_BAG.get(), Items.ENDER_PEARL, true, consumer);
    }

    private void registerPileRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.BRICK_PILE.get(), Items.BRICK, true, consumer);
        simpleCombined(PUItems.NETHER_BRICK_PILE.get(), Items.NETHER_BRICK, true, consumer);
        simpleCombined(PUItems.STONE_PILE.get(), Items.STONE, true, consumer);
        simpleCombined(PUItems.DEEPSLATE_PILE.get(), Items.DEEPSLATE, true, consumer);
        simpleCombined(PUItems.CALCITE_PILE.get(), Items.CALCITE, true, consumer);
    }

    private void registerResourcePalletRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.BRICK_PALLET.get(), PUItems.BRICK_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.NETHER_BRICK_PALLET.get(), PUItems.NETHER_BRICK_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.STONE_PALLET.get(), PUItems.STONE_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.DEEPSLATE_PALLET.get(), PUItems.DEEPSLATE_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.CALCITE_PALLET.get(), PUItems.CALCITE_PILE.get(), false, "pallet", consumer);
        simpleCombined(PUItems.COPPER_PALLET.get(), Items.COPPER_BLOCK, false, "pallet", consumer);
        simpleCombined(PUItems.IRON_PALLET.get(), Items.IRON_BLOCK, false, "pallet", consumer);
        simpleCombined(PUItems.GOLD_PALLET.get(), Items.GOLD_BLOCK, false, "pallet", consumer);
        simpleCombined(PUItems.DIAMOND_PALLET.get(), Items.DIAMOND_BLOCK, false, "pallet", consumer);
        simpleCombined(PUItems.EMERALD_PALLET.get(), Items.EMERALD_BLOCK, false, "pallet", consumer);
        simpleCombined(PUItems.NETHERITE_PALLET.get(), Items.NETHERITE_BLOCK, false, "pallet", consumer);
    }

    private void registerBookBlockRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCompact(Items.BOOK, PUItems.BOOK_BUNDLE.get(), consumer);
        Set<Item> bookBundles = Sets.newHashSet(
                PUItems.BOOK_BUNDLE.get(),
                PUItems.WHITE_BOOK_BUNDLE.get(),
                PUItems.LIGHT_GRAY_BOOK_BUNDLE.get(),
                PUItems.GRAY_BOOK_BUNDLE.get(),
                PUItems.BLACK_BOOK_BUNDLE.get(),
                PUItems.BROWN_BOOK_BUNDLE.get(),
                PUItems.RED_BOOK_BUNDLE.get(),
                PUItems.ORANGE_BOOK_BUNDLE.get(),
                PUItems.YELLOW_BOOK_BUNDLE.get(),
                PUItems.LIME_BOOK_BUNDLE.get(),
                PUItems.GREEN_BOOK_BUNDLE.get(),
                PUItems.CYAN_BOOK_BUNDLE.get(),
                PUItems.LIGHT_BLUE_BOOK_BUNDLE.get(),
                PUItems.BLUE_BOOK_BUNDLE.get(),
                PUItems.PURPLE_BOOK_BUNDLE.get(),
                PUItems.MAGENTA_BOOK_BUNDLE.get(),
                PUItems.PINK_BOOK_BUNDLE.get()
        );
        for (Item bundle : bookBundles) {
            simpleShapeless(bundle, Items.BOOK, false, consumer);
        }

        // Book blocks from pile
        registerBookBlockFromPileRecipes(consumer);
    }

    private void registerBookBlockFromPileRecipes(Consumer<FinishedRecipe> consumer) {
        simpleSmallCompact(PUItems.BOOK_PILE.get(), PUItems.BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.WHITE_BOOK_PILE.get(), PUItems.WHITE_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.LIGHT_GRAY_BOOK_PILE.get(), PUItems.LIGHT_GRAY_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.GRAY_BOOK_PILE.get(), PUItems.GRAY_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.BLACK_BOOK_PILE.get(), PUItems.BLACK_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.BROWN_BOOK_PILE.get(), PUItems.BROWN_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.RED_BOOK_PILE.get(), PUItems.RED_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.ORANGE_BOOK_PILE.get(), PUItems.ORANGE_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.YELLOW_BOOK_PILE.get(), PUItems.YELLOW_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.LIME_BOOK_PILE.get(), PUItems.LIME_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.GREEN_BOOK_PILE.get(), PUItems.GREEN_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.CYAN_BOOK_PILE.get(), PUItems.CYAN_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.LIGHT_BLUE_BOOK_PILE.get(), PUItems.LIGHT_BLUE_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.BLUE_BOOK_PILE.get(), PUItems.BLUE_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.PURPLE_BOOK_PILE.get(), PUItems.PURPLE_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.MAGENTA_BOOK_PILE.get(), PUItems.MAGENTA_BOOK_BUNDLE.get(), consumer);
        simpleSmallCompact(PUItems.PINK_BOOK_PILE.get(), PUItems.PINK_BOOK_BUNDLE.get(), consumer);
    }

    private void registerTurfBlockRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.GRASS_TURF.get(), Blocks.GRASS_BLOCK, false, consumer);
        simpleCombined(PUItems.PODZOL_TURF.get(), Blocks.PODZOL, false, consumer);
        simpleCombined(PUItems.MYCELIUM_TURF.get(), Blocks.MYCELIUM, false, consumer);

        // Turf blocks from layer
        simpleSmallCompact(PUItems.GRASS_TURF_LAYER.get(), PUItems.GRASS_TURF.get(), consumer);
        simpleSmallCompact(PUItems.PODZOL_TURF_LAYER.get(), PUItems.PODZOL_TURF.get(), consumer);
        simpleSmallCompact(PUItems.MYCELIUM_TURF_LAYER.get(), PUItems.MYCELIUM_TURF.get(), consumer);
    }

    private void registerGrassBaleRecipes(Consumer<FinishedRecipe> consumer) {
        simpleCombined(PUItems.GRASS_BALE.get(), Blocks.GRASS, false, consumer);
        simpleSmallCompact(Blocks.TALL_GRASS, PUItems.GRASS_BALE.get(), consumer);
    }

    private void registerColoredBookBlockRecipes(Consumer<FinishedRecipe> consumer) {
        bookBundleDyeing(Tags.Items.DYES_WHITE, PUItems.WHITE_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_LIGHT_GRAY, PUItems.LIGHT_GRAY_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_GRAY, PUItems.GRAY_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_BLACK, PUItems.BLACK_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_BROWN, PUItems.BROWN_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_RED, PUItems.RED_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_ORANGE, PUItems.ORANGE_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_YELLOW, PUItems.YELLOW_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_LIME, PUItems.LIME_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_GREEN, PUItems.GREEN_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_CYAN, PUItems.CYAN_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_LIGHT_BLUE, PUItems.LIGHT_BLUE_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_BLUE, PUItems.BLUE_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_PURPLE, PUItems.PURPLE_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_MAGENTA, PUItems.MAGENTA_BOOK_BUNDLE.get(), consumer);
        bookBundleDyeing(Tags.Items.DYES_PINK, PUItems.PINK_BOOK_BUNDLE.get(), consumer);
    }

    private void registerBookSlabRecipes(Consumer<FinishedRecipe> consumer) {
        simpleSlab(PUItems.BOOK_BUNDLE.get(), PUItems.BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.WHITE_BOOK_BUNDLE.get(), PUItems.WHITE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.LIGHT_GRAY_BOOK_BUNDLE.get(), PUItems.LIGHT_GRAY_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.GRAY_BOOK_BUNDLE.get(), PUItems.GRAY_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.BLACK_BOOK_BUNDLE.get(), PUItems.BLACK_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.BROWN_BOOK_BUNDLE.get(), PUItems.BROWN_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.RED_BOOK_BUNDLE.get(), PUItems.RED_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.ORANGE_BOOK_BUNDLE.get(), PUItems.ORANGE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.YELLOW_BOOK_BUNDLE.get(), PUItems.YELLOW_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.LIME_BOOK_BUNDLE.get(), PUItems.LIME_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.GREEN_BOOK_BUNDLE.get(), PUItems.GREEN_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.CYAN_BOOK_BUNDLE.get(), PUItems.CYAN_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.LIGHT_BLUE_BOOK_BUNDLE.get(), PUItems.LIGHT_BLUE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.BLUE_BOOK_BUNDLE.get(), PUItems.BLUE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.PURPLE_BOOK_BUNDLE.get(), PUItems.PURPLE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.MAGENTA_BOOK_BUNDLE.get(), PUItems.MAGENTA_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleSlab(PUItems.PINK_BOOK_BUNDLE.get(), PUItems.PINK_BOOK_BUNDLE_SLAB.get(), consumer);

        // Book bundle slab from pile
        registerBookSlabFromPileRecipes(consumer);
    }

    private void registerBookSlabFromPileRecipes(Consumer<FinishedRecipe> consumer) {
        simpleStacked(PUItems.BOOK_PILE.get(), PUItems.BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.WHITE_BOOK_PILE.get(), PUItems.WHITE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.LIGHT_GRAY_BOOK_PILE.get(), PUItems.LIGHT_GRAY_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.GRAY_BOOK_PILE.get(), PUItems.GRAY_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.BLACK_BOOK_PILE.get(), PUItems.BLACK_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.BROWN_BOOK_PILE.get(), PUItems.BROWN_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.RED_BOOK_PILE.get(), PUItems.RED_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.ORANGE_BOOK_PILE.get(), PUItems.ORANGE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.YELLOW_BOOK_PILE.get(), PUItems.YELLOW_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.LIME_BOOK_PILE.get(), PUItems.LIME_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.GREEN_BOOK_PILE.get(), PUItems.GREEN_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.CYAN_BOOK_PILE.get(), PUItems.CYAN_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.LIGHT_BLUE_BOOK_PILE.get(), PUItems.LIGHT_BLUE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.BLUE_BOOK_PILE.get(), PUItems.BLUE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.PURPLE_BOOK_PILE.get(), PUItems.PURPLE_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.MAGENTA_BOOK_PILE.get(), PUItems.MAGENTA_BOOK_BUNDLE_SLAB.get(), consumer);
        simpleStacked(PUItems.PINK_BOOK_PILE.get(), PUItems.PINK_BOOK_BUNDLE_SLAB.get(), consumer);
    }

    private void registerGrassThatchRecipes(Consumer<FinishedRecipe> consumer) {
        simpleThatch(PUItems.GRASS_BALE.get(), PUItems.GRASS_THATCH.get(), consumer);
        simpleStairs(PUItems.GRASS_THATCH.get(), PUItems.GRASS_THATCH_STAIRS.get(), consumer);
        simpleSlab(PUItems.GRASS_THATCH.get(), PUItems.GRASS_THATCH_SLAB.get(), consumer);
    }

}
