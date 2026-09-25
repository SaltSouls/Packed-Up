package salted.packedup.common.registry.helpers;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import salted.packedup.PackedUp;
import salted.packedup.common.block.*;
import salted.packedup.common.block.state.PUProperties;
import salted.packedup.common.item.DrumBarrelItem;
import salted.packedup.common.registry.PUSoundsTypes;
import salted.packedup.common.registry.TabCategory;

import java.util.List;

public class RegistryHelper extends Organizer {
    public static final Registrate REGISTRATE = Registrate.create(PackedUp.MODID);


    // ============================================================
    // Datagen Fixes
    // ============================================================
    protected static <T extends Block> BlockBuilder<T, Registrate> noDatagenItem(BlockBuilder<T, Registrate> builder) {
        return builder.blockstate(NonNullBiConsumer.noop()).item().model(NonNullBiConsumer.noop()).build();
    }

    protected static <T extends Block, I extends BlockItem> BlockBuilder<T, Registrate> noDatagenItem(BlockBuilder<T, Registrate> builder, NonNullBiFunction<? super T, Item.Properties, ? extends I> factory) {
        return builder.blockstate(NonNullBiConsumer.noop()).item(factory).model(NonNullBiConsumer.noop()).build();
    }

    // --- Loot Utilities ---
    private static LootTable.Builder slabLoot(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                .hasProperty(BlockStateProperties.SLAB_TYPE, SlabType.DOUBLE))))));
    }

    protected static LootTable.Builder quarterSlabLoot(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block)
                .apply(List.of(2, 3, 4), layers -> SetItemCountFunction
                .setCount(ConstantValue.exactly(layers.floatValue()))
                .when(LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties()
                .hasProperty(PUProperties.QUARTER_LAYERS, layers))))));
    }

    private static LootTable.Builder drumLoot(Block block) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block)
                .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                .copy("Tank", "BlockEntityTag.Tank")))
                .when(ExplosionCondition.survivesExplosion()));
    }

    // ============================================================
    // Simple Registration Types
    // ============================================================
    protected static RegistryEntry<Block> resourceCrate(String name, NonNullSupplier<Block> base, MapColor color) {
        RegistryEntry<Block> entry = categorized(TabCategory.RESOURCE_CRATES,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(base)
                        .properties(p -> p
                        .mapColor(color)
                        .strength(4.0F, 8.0F)
                        .sound(PUSoundsTypes.RESOURCE_CRATE))).register());

        RESOURCE_CRATE_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> miscResourceCrate(String name, MapColor color, SoundType sound) {
        RegistryEntry<Block> entry = categorized(TabCategory.MISC_CRATES,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(() -> Blocks.STONE)
                        .properties(p -> p
                        .mapColor(color)
                        .strength(4.0F, 8.0F)
                        .sound(sound))).register());

        MISC_CRATE_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> miscCrate(String name, MapColor color, float hardness, float blast, SoundType sound) {
        RegistryEntry<Block> entry = categorized(TabCategory.MISC_CRATES,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(() -> Blocks.STONE)
                        .properties(p -> p
                        .mapColor(color)
                        .strength(hardness, blast)
                        .sound(sound))).register());

        MISC_CRATE_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> reinforcedCrate(String name, NonNullSupplier<Block> base, MapColor color) {
        RegistryEntry<Block> entry = categorized(TabCategory.REINFORCED_CRATES,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(base)
                        .properties(p -> p
                        .mapColor(color)
                        .strength(4.0F, 16.0F)
                        .sound(PUSoundsTypes.RESOURCE_CRATE))).register());

        REINFORCED_CRATE_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> produceCrate(String name) {
        RegistryEntry<Block> entry = categorized(TabCategory.PRODUCE_CRATES,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(() -> Blocks.OAK_PLANKS)
                        .properties(p -> p
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD))).register());

        PRODUCE_CRATE_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> mushroomCrate(String name) {
        RegistryEntry<Block> entry = categorized(TabCategory.PRODUCE_CRATES,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(() -> Blocks.OAK_PLANKS)
                        .properties(p -> p
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.WOOD))).register());

        MUSHROOM_CRATE_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> basket(String name, MapColor color, SoundType sound) {
        RegistryEntry<Block> entry = categorized(TabCategory.BASKETS,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(() -> Blocks.STONE)
                        .properties(p -> p
                        .mapColor(color)
                        .strength(1.0F)
                        .sound(sound))).register());

        BASKET_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> basket(String name, MapColor color, SoundType sound, int light) {
        RegistryEntry<Block> entry = categorized(TabCategory.BASKETS,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(() -> Blocks.STONE)
                        .properties(p -> p
                        .mapColor(color)
                        .strength(1.0F)
                        .sound(sound)
                        .lightLevel(s -> light))).register());

        BASKET_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> fishBarrel(String name, NonNullSupplier<Block> base) {
        RegistryEntry<Block> entry = categorized(TabCategory.BARRELS,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(base)
                        .properties(p -> p
                        .strength(2.0F, 3.0F)
                        .sound(PUSoundsTypes.FISH_BARREL))).register());

        BARREL_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> produceBag(String name, MapColor color, SoundType sound) {
        RegistryEntry<Block> entry = categorized(TabCategory.PRODUCE_BAGS,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(() -> Blocks.STONE)
                        .properties(p -> p
                        .mapColor(color)
                        .strength(1.0F)
                        .sound(sound))).register());

        PRODUCE_BAG_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> miscBag(String name, MapColor color, SoundType sound) {
        RegistryEntry<Block> entry = categorized(TabCategory.MISC_BAGS,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(() -> Blocks.STONE)
                        .properties(p -> p
                        .mapColor(color)
                        .strength(1.0F)
                        .sound(sound))).register());

        MISC_BAG_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> miscBag(String name, MapColor color, SoundType sound, int light) {
        RegistryEntry<Block> entry = categorized(TabCategory.MISC_BAGS,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(() -> Blocks.STONE)
                        .properties(p -> p
                        .mapColor(color)
                        .strength(1.0F)
                        .sound(sound)
                        .lightLevel(s -> light))).register());

        MISC_BAG_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<Block> resourceBag(String name, NonNullSupplier<Block> base, SoundType sound) {
        RegistryEntry<Block> entry = categorized(TabCategory.RESOURCE_BAGS,
                noDatagenItem(REGISTRATE.block(name, Block::new)
                        .initialProperties(base)
                        .properties(p -> p
                        .strength(1.0F)
                        .sound(sound))).register());

        RESOURCE_BAG_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<HorizontalBlock> pile(String name, NonNullSupplier<Block> base, float hardness, float blast) {
        RegistryEntry<HorizontalBlock> entry = categorized(TabCategory.PILES,
                noDatagenItem(REGISTRATE.block(name, HorizontalBlock::new)
                        .initialProperties(base)
                        .properties(p -> p.strength(hardness, blast))
                        .loot((lt, block) -> lt
                        .add(block, quarterSlabLoot(block)))).register());

        PILE_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<HorizontalBlock> pallet(String name, NonNullSupplier<Block> base, float hardness, float blast) {
        RegistryEntry<HorizontalBlock> entry = categorized(TabCategory.PALLETS,
                noDatagenItem(REGISTRATE.block(name, HorizontalBlock::new)
                        .initialProperties(base)
                        .properties(p -> p.strength(hardness, blast)))
                        .register());

        PALLET_ENTRIES.add(entry);
        return entry;
    }

    protected static RegistryEntry<HorizontalBlock> fireResistantPallet(String name, NonNullSupplier<Block> base, float hardness, float blast) {
        RegistryEntry<HorizontalBlock> entry = categorized(TabCategory.PALLETS,
                REGISTRATE.block(name, HorizontalBlock::new)
                        .initialProperties(base)
                        .properties(p -> p.strength(hardness, blast))
                        .blockstate(NonNullBiConsumer.noop())
                        .item()
                        .properties(Item.Properties::fireResistant)
                        .model(NonNullBiConsumer.noop()).build().register());

        PALLET_ENTRIES.add(entry);
        return entry;
    }

    public static RegistryEntry<BookBundleBlock> bookBundle(String name, NonNullSupplier<Block> wool) {
        return categorized(TabCategory.BOOKS,
                noDatagenItem(REGISTRATE.block(name, BookBundleBlock::new)
                        .initialProperties(wool)
                        .properties(p -> p.sound(PUSoundsTypes.BOOKS)))
                        .register());
    }

    public static RegistryEntry<BookBundleSlabBlock> bookBundleSlab(String name, NonNullSupplier<Block> wool) {
        return categorized(TabCategory.BOOKS,
                noDatagenItem(REGISTRATE.block(name, BookBundleSlabBlock::new)
                        .initialProperties(wool)
                        .properties(p -> p.sound(PUSoundsTypes.BOOKS))
                        .loot((lt, block) -> lt
                        .add(block, slabLoot(block)))).register());
    }

    public static RegistryEntry<BookPileBlock> bookPile(String name, NonNullSupplier<Block> wool) {
        return categorized(TabCategory.BOOKS,
                noDatagenItem(REGISTRATE.block(name, BookPileBlock::new)
                        .initialProperties(wool)
                        .properties(p -> p.sound(PUSoundsTypes.BOOKS))
                        .loot((lt, block) -> lt
                        .add(block, quarterSlabLoot(block)))).register());
    }

    public static void coloredBookSet(DyeColor color) {
        String prefix = color.getName();
        NonNullSupplier<Block> wool = () -> getWoolBlock(color);
        ColoredBookSet set = new ColoredBookSet(
                color,
                bookBundle(prefix + "_book_bundle", wool),
                bookBundleSlab(prefix + "_book_bundle_slab", wool),
                bookPile(prefix + "_book_pile", wool));

        COLORED_BOOKS.put(color, set);
    }

    protected static void industrialSpool(DyeColor color) {
        NonNullSupplier<Block> wool = () -> getWoolBlock(color);
        RegistryEntry<IndustrialSpoolBlock> entry = categorized(TabCategory.SPOOLS,
                noDatagenItem(REGISTRATE.block(color.getName() + "_industrial_string_spool", IndustrialSpoolBlock::new)
                        .initialProperties(wool)
                        .properties(p -> p.sound(SoundType.WOOD)))
                        .register());

        COLORED_SPOOLS.put(color, entry);
    }

    public static RegistryEntry<DrumBarrelBlock> drumBarrel(String name, MapColor color) {
        RegistryEntry<DrumBarrelBlock> entry = categorized(TabCategory.DRUMS,
                noDatagenItem(REGISTRATE.block(name, DrumBarrelBlock::new)
                        .properties(p -> p
                        .mapColor(color)
                        .sound(SoundType.METAL)
                        .strength(3.0F, 6.0F))
                        .loot((lt, block) -> lt
                        .add(block, drumLoot(block))),
                        DrumBarrelItem::new).register());

        DRUM_BARREL_ENTRIES.add(entry);
        return entry;
    }

    public static void coloredDrumBarrel(DyeColor color) {
        COLORED_DRUMS.put(color, drumBarrel(color.getName() + "_drum_barrel", color.getMapColor()));
    }

    public static RegistryEntry<FluidGaugeBlock> fluidGauge(String name) {
        return categorized(TabCategory.GAUGE,
                noDatagenItem(REGISTRATE.block(name, FluidGaugeBlock::new)
                        .properties(p -> p
                        .mapColor(MapColor.METAL)
                        .sound(SoundType.LANTERN)
                        .strength(1.0F)
                        .noOcclusion()
                        .noCollission()))
                        .register());
    }

}
