package salted.packedup.common.registry.lang;

import salted.packedup.common.registry.helpers.Translations;

public class Ukrainian extends Translations {

    @Override
    public String locale() {
        return "uk_ua";
    }

    @Override
    protected void addTranslations() {
        // ============================================================
        // Creative tab
        // ============================================================
        lang("itemGroup.packedup", "Packed Up");

        // ============================================================
        // Baskets
        // ============================================================
        name("sweet_berry_basket", "Кошик солодких ягід");
        name("glow_berry_basket", "Кошик сяйних ягід");
        name("apple_basket", "Кошик яблук");
        name("golden_apple_basket", "Кошик золотих яблук");

        // ============================================================
        // Barrels
        // ============================================================
        name("cod_barrel", "Діжка тріски");
        name("salmon_barrel", "Діжка лососів");

        // ============================================================
        // Bags
        // ============================================================
        name("cocoa_bean_bag", "Мішок какао-бобів");
        name("sugar_bag", "Мішок цукру");
        name("nether_wart_bag", "Мішок незерських наростів");
        name("glowstone_dust_bag", "Мішок світлокам'яного пилу");
        name("dirt_bag", "Мішок ґрунту");
        name("rooted_dirt_bag", "Мішок кореневого ґрунту");
        name("coarse_dirt_bag", "Мішок загрубілого ґрунту");
        name("gravel_bag", "Мішок гравію");

        // ============================================================
        // Crates
        // ============================================================
        name("golden_carrot_crate", "Ящик золотої моркви");
        name("egg_crate", "Ящик яєць");
        name("red_mushroom_crate", "Ящик червоних грибів");
        name("brown_mushroom_crate", "Ящик коричневих грибів");
        name("crate_lid", "Кришка ящика");
        name("reinforced_crate_lid", "Посилена кришка ящика");
        name("gunpowder_crate", "Ящик пороху");
        name("cobblestone_crate", "Ящик кругляку");
        name("reinforced_cobblestone_crate", "Посилений ящик кругляку");
        name("cobbled_deepslate_crate", "Ящик глибосланцевого кругляку");
        name("reinforced_cobbled_deepslate_crate", "Посилений ящик глибосланцевого кругляку");
        name("andesite_crate", "Ящик андезиту");
        name("reinforced_andesite_crate", "Посилений ящик андезиту");
        name("diorite_crate", "Ящик діориту");
        name("reinforced_diorite_crate", "Посилений ящик діориту");
        name("granite_crate", "Ящик граніту");
        name("reinforced_granite_crate", "Посилений ящик граніту");
        name("tuff_crate", "Ящик туфу");
        name("reinforced_tuff_crate", "Посилений ящик туфу");
        name("blackstone_crate", "Ящик чорнокаменю");
        name("reinforced_blackstone_crate", "Посилений ящик чорнокаменю");
        name("basalt_crate", "Ящик базальту");
        name("reinforced_basalt_crate", "Посилений ящик базальту");
        name("raw_copper_crate", "Ящик необробленої міді");
        name("raw_iron_crate", "Ящик необробленого заліза");
        name("raw_gold_crate", "Ящик необробленого золота");

        // ============================================================
        // Piles and pallets
        // ============================================================
        name("brick_pile", "Купа цеглин");
        name("brick_pallet", "Палета цеглин");
        name("nether_brick_pile", "Купа незерських цеглин");
        name("nether_brick_pallet", "Палета незерських цеглин");
        name("stone_pile", "Купа каменю");
        name("stone_pallet", "Палета каменю");
        name("deepslate_pile", "Купа глибосланцю");
        name("deepslate_pallet", "Палета глибосланцю");
        name("calcite_pile", "Купа кальциту");
        name("calcite_pallet", "Палета кальциту");
        name("copper_pallet", "Палета міді");
        name("iron_pallet", "Палета заліза");
        name("gold_pallet", "Палета золота");
        name("netherite_pallet", "Палета незериту");
    }
}
