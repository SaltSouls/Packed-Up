package salted.packedup.common.registry.lang;

import net.minecraft.world.item.DyeColor;
import salted.packedup.common.registry.helpers.Translations;

public class English extends Translations {

    @Override
    public String locale() {
        return "en_us";
    }

    @Override
    protected void addTranslations() {
        // ============================================================
        // Creative tab
        // ============================================================
        lang("itemGroup.packedup", "Packed Up");

        // ============================================================
        // Books
        // ============================================================
        name("book_bundle", "Bundle of Books");
        name("book_bundle_slab", "Half Bundle of Books");
        name("book_pile", "Pile of Books");

        for (DyeColor color : DyeColor.values()) {
            String path = color.getName();
            String dye = titleCase(path);

            name(path + "_book_bundle", "Bundle of " + dye + " Books");
            name(path + "_book_bundle_slab", "Half Bundle of " + dye + " Books");
            name(path + "_book_pile", "Pile of " + dye + " Books");
        }

        // ============================================================
        // Baskets
        // ============================================================
        name("sweet_berry_basket", "Basket of Sweet Berries");
        name("glow_berry_basket", "Basket of Glow Berries");
        name("apple_basket", "Basket of Apples");
        name("golden_apple_basket", "Basket of Golden Apples");

        // ============================================================
        // Barrels
        // ============================================================
        name("cod_barrel", "Barrel of Cod");
        name("salmon_barrel", "Barrel of Salmon");

        // ============================================================
        // Bags
        // ============================================================
        name("cocoa_bean_bag", "Bag of Cocoa Beans");
        name("sugar_bag", "Bag of Sugar");
        name("nether_wart_bag", "Bag of Nether Wart");
        name("glowstone_dust_bag", "Bag of Glowstone Dust");
        name("ender_pearl_bag", "Bag of Ender Pearls");
        name("dirt_bag", "Bag of Dirt");
        name("rooted_dirt_bag", "Bag of Rooted Dirt");
        name("coarse_dirt_bag", "Bag of Coarse Dirt");
        name("gravel_bag", "Bag of Gravel");

        // ============================================================
        // Grass and thatch
        // ============================================================
        name("grass_bale", "Grass Bale");
        name("grass_thatch", "Grass Thatch");
        name("grass_thatch_stairs", "Grass Thatch Stairs");
        name("grass_thatch_slab", "Grass Thatch Slab");

        // ============================================================
        // Turf
        // ============================================================
        name("grass_turf", "Grass Turf");
        name("grass_turf_layer", "Grass Turf Layer");
        name("podzol_turf", "Podzol Turf");
        name("podzol_turf_layer", "Podzol Turf Layer");
        name("mycelium_turf", "Mycelium Turf");
        name("mycelium_turf_layer", "Mycelium Turf Layer");

        // ============================================================
        // Crates
        // ============================================================
        name("golden_carrot_crate", "Golden Carrot Crate");
        name("egg_crate", "Egg Crate");
        name("red_mushroom_crate", "Red Mushroom Crate");
        name("brown_mushroom_crate", "Brown Mushroom Crate");
        name("crimson_fungus_crate", "Crimson Fungus Crate");
        name("warped_fungus_crate", "Warped Fungus Crate");
        name("crate_lid", "Crate Lid");
        name("reinforced_crate_lid", "Reinforced Crate Lid");
        name("gunpowder_crate", "Gunpowder Crate");
        name("cobblestone_crate", "Cobblestone Crate");
        name("reinforced_cobblestone_crate", "Reinforced Cobblestone Crate");
        name("cobbled_deepslate_crate", "Cobbled Deepslate Crate");
        name("reinforced_cobbled_deepslate_crate", "Reinforced Cobbled Deepslate Crate");
        name("andesite_crate", "Andesite Crate");
        name("reinforced_andesite_crate", "Reinforced Andesite Crate");
        name("diorite_crate", "Diorite Crate");
        name("reinforced_diorite_crate", "Reinforced Diorite Crate");
        name("granite_crate", "Granite Crate");
        name("reinforced_granite_crate", "Reinforced Granite Crate");
        name("tuff_crate", "Tuff Crate");
        name("reinforced_tuff_crate", "Reinforced Tuff Crate");
        name("blackstone_crate", "Blackstone Crate");
        name("reinforced_blackstone_crate", "Reinforced Blackstone Crate");
        name("basalt_crate", "Basalt Crate");
        name("reinforced_basalt_crate", "Reinforced Basalt Crate");
        name("netherrack_crate", "Netherrack Crate");
        name("reinforced_netherrack_crate", "Reinforced Netherrack Crate");
        name("quartz_crate", "Nether Quartz Crate");
        name("amethyst_crate", "Amethyst Crate");
        name("echo_shard_crate", "Echo Shard Crate");
        name("raw_copper_crate", "Raw Copper Crate");
        name("raw_iron_crate", "Raw Iron Crate");
        name("raw_gold_crate", "Raw Gold Crate");

        // ============================================================
        // Piles and pallets
        // ============================================================
        name("pallet", "Pallet");
        name("brick_pile", "Pile of Bricks");
        name("brick_pallet", "Pallet of Bricks");
        name("nether_brick_pile", "Pile of Nether Bricks");
        name("nether_brick_pallet", "Pallet of Nether Bricks");
        name("stone_pile", "Pile of Stone");
        name("stone_pallet", "Pallet of Stone");
        name("deepslate_pile", "Pile of Deepslate");
        name("deepslate_pallet", "Pallet of Deepslate");
        name("calcite_pile", "Pile of Calcite");
        name("calcite_pallet", "Pallet of Calcite");
        name("copper_pallet", "Pallet of Copper");
        name("iron_pallet", "Pallet of Iron");
        name("gold_pallet", "Pallet of Gold");
        name("diamond_pallet", "Pallet of Diamonds");
        name("emerald_pallet", "Pallet of Emeralds");
        name("netherite_pallet", "Pallet of Netherite");

        // ============================================================
        // Fluid Gauge
        // ============================================================
        name("fluid_gauge", "Fluid Gauge");

        // ============================================================
        // Drum Barrels
        // ============================================================
        name("drum_barrel", "Drum Barrel");
        for (DyeColor color : DyeColor.values()) {
            name("block.packedup." + color.getName() + "_drum_barrel", titleCase(color.getName()) + " Drum Barrel");
        }

        // ============================================================
        // Tooltips
        // ============================================================
        tooltip("drum_barrel.empty", "Empty");
        tooltip("drum_barrel.fluid", "Contains: %1$s");
        tooltip("fluid_gauge.title", "Fluid Container Info:");
        tooltip("fluid_gauge.empty", "Empty");
        tooltip("fluid_gauge.unreadable", "No container");
        tooltip("fluid_gauge.fluid", "Contains: %1$s");

        // ============================================================
        // Subtitles
        // ============================================================
        subtitle("drum_barrel.knock", "Drum thuds");
        subtitle("drum_barrel.slosh", "Drum sloshes");
        subtitle("drum_barrel.warble", "Drum bubbles");
    }
}
