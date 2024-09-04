package salted.packedup.data.loot;

import com.google.common.collect.Sets;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import salted.packedup.common.block.HorizontalSlabBlock;
import salted.packedup.common.block.QuarterSlabBlock;
import salted.packedup.common.registry.PUBlocks;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PUBlockLoot extends BlockLootSubProvider {
    private final Set<Block> generatedLootTables = new HashSet<>();

    public PUBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // blocks that drop themselves
        Set<Block> blocks = Sets.newHashSet(
                PUBlocks.CRATE_LID.get(),
                PUBlocks.REINFORCED_CRATE_LID.get(),
                PUBlocks.COBBLESTONE_CRATE.get(),
                PUBlocks.REINFORCED_COBBLESTONE_CRATE.get(),
                PUBlocks.COBBLED_DEEPSLATE_CRATE.get(),
                PUBlocks.REINFORCED_COBBLED_DEEPSLATE_CRATE.get(),
                PUBlocks.ANDESITE_CRATE.get(),
                PUBlocks.REINFORCED_ANDESITE_CRATE.get(),
                PUBlocks.DIORITE_CRATE.get(),
                PUBlocks.REINFORCED_DIORITE_CRATE.get(),
                PUBlocks.GRANITE_CRATE.get(),
                PUBlocks.REINFORCED_GRANITE_CRATE.get(),
                PUBlocks.TUFF_CRATE.get(),
                PUBlocks.REINFORCED_TUFF_CRATE.get(),
                PUBlocks.BLACKSTONE_CRATE.get(),
                PUBlocks.REINFORCED_BLACKSTONE_CRATE.get(),
                PUBlocks.NETHERRACK_CRATE.get(),
                PUBlocks.REINFORCED_NETHERRACK_CRATE.get(),
                PUBlocks.BASALT_CRATE.get(),
                PUBlocks.REINFORCED_BASALT_CRATE.get(),
                PUBlocks.RAW_IRON_CRATE.get(),
                PUBlocks.RAW_COPPER_CRATE.get(),
                PUBlocks.RAW_GOLD_CRATE.get(),
                PUBlocks.GUNPOWDER_CRATE.get(),
                PUBlocks.QUARTZ_CRATE.get(),
                PUBlocks.AMETHYST_CRATE.get(),
                PUBlocks.ECHO_SHARD_CRATE.get(),
                PUBlocks.DIRT_BAG.get(),
                PUBlocks.ROOTED_DIRT_BAG.get(),
                PUBlocks.COARSE_DIRT_BAG.get(),
                PUBlocks.GRAVEL_BAG.get(),
                PUBlocks.APPLE_BASKET.get(),
                PUBlocks.GOLDEN_APPLE_BASKET.get(),
                PUBlocks.SWEET_BERRY_BASKET.get(),
                PUBlocks.GLOW_BERRY_BASKET.get(),
                PUBlocks.COCOA_BEAN_BAG.get(),
                PUBlocks.SUGAR_BAG.get(),
                PUBlocks.NETHER_WART_BAG.get(),
                PUBlocks.GLOWSTONE_DUST_BAG.get(),
                PUBlocks.ENDER_PEARL_BAG.get(),
                PUBlocks.GOLDEN_CARROT_CRATE.get(),
                PUBlocks.EGG_CRATE.get(),
                PUBlocks.RED_MUSHROOM_CRATE.get(),
                PUBlocks.BROWN_MUSHROOM_CRATE.get(),
                PUBlocks.COD_BARREL.get(),
                PUBlocks.SALMON_BARREL.get(),
                PUBlocks.BRICK_PILE.get(),
                PUBlocks.BRICK_PALLET.get(),
                PUBlocks.NETHER_BRICK_PILE.get(),
                PUBlocks.NETHER_BRICK_PALLET.get(),
                PUBlocks.STONE_PILE.get(),
                PUBlocks.STONE_PALLET.get(),
                PUBlocks.DEEPSLATE_PILE.get(),
                PUBlocks.DEEPSLATE_PALLET.get(),
                PUBlocks.CALCITE_PILE.get(),
                PUBlocks.CALCITE_PALLET.get(),
                PUBlocks.COPPER_PALLET.get(),
                PUBlocks.IRON_PALLET.get(),
                PUBlocks.GOLD_PALLET.get(),
                PUBlocks.DIAMOND_PALLET.get(),
                PUBlocks.EMERALD_PALLET.get(),
                PUBlocks.NETHERITE_PALLET.get(),
                PUBlocks.BOOK_BUNDLE.get(),
                PUBlocks.WHITE_BOOK_BUNDLE.get(),
                PUBlocks.LIGHT_GRAY_BOOK_BUNDLE.get(),
                PUBlocks.GRAY_BOOK_BUNDLE.get(),
                PUBlocks.BLACK_BOOK_BUNDLE.get(),
                PUBlocks.BROWN_BOOK_BUNDLE.get(),
                PUBlocks.RED_BOOK_BUNDLE.get(),
                PUBlocks.ORANGE_BOOK_BUNDLE.get(),
                PUBlocks.YELLOW_BOOK_BUNDLE.get(),
                PUBlocks.LIME_BOOK_BUNDLE.get(),
                PUBlocks.GREEN_BOOK_BUNDLE.get(),
                PUBlocks.CYAN_BOOK_BUNDLE.get(),
                PUBlocks.LIGHT_BLUE_BOOK_BUNDLE.get(),
                PUBlocks.BLUE_BOOK_BUNDLE.get(),
                PUBlocks.PURPLE_BOOK_BUNDLE.get(),
                PUBlocks.MAGENTA_BOOK_BUNDLE.get(),
                PUBlocks.PINK_BOOK_BUNDLE.get(),
                PUBlocks.GRASS_TURF.get(),
                PUBlocks.PODZOL_TURF.get(),
                PUBlocks.MYCELIUM_TURF.get(),
                PUBlocks.GRASS_BUNDLE.get(),
                PUBlocks.GRASS_THATCH.get()
        );
        for (Block block : blocks) {
            dropSelf(block);
        }
        // slabs
        Set<Block> slabs = Sets.newHashSet(
                PUBlocks.BOOK_BUNDLE_SLAB.get(),
                PUBlocks.WHITE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.LIGHT_GRAY_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.GRAY_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BLACK_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BROWN_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.RED_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.ORANGE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.YELLOW_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.LIME_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.GREEN_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.CYAN_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.LIGHT_BLUE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.BLUE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.PURPLE_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.MAGENTA_BOOK_BUNDLE_SLAB.get(),
                PUBlocks.PINK_BOOK_BUNDLE_SLAB.get()
        );
        for (Block slab : slabs) {
            createHorizontalSlabItemTable(slab);
        }
        // quarter slabs
        Set<Block> quarterSlabs = Sets.newHashSet(
                PUBlocks.PALLET.get(),
                PUBlocks.BOOK_PILE.get(),
                PUBlocks.WHITE_BOOK_PILE.get(),
                PUBlocks.LIGHT_GRAY_BOOK_PILE.get(),
                PUBlocks.GRAY_BOOK_PILE.get(),
                PUBlocks.BLACK_BOOK_PILE.get(),
                PUBlocks.BROWN_BOOK_PILE.get(),
                PUBlocks.RED_BOOK_PILE.get(),
                PUBlocks.ORANGE_BOOK_PILE.get(),
                PUBlocks.YELLOW_BOOK_PILE.get(),
                PUBlocks.LIME_BOOK_PILE.get(),
                PUBlocks.GREEN_BOOK_PILE.get(),
                PUBlocks.CYAN_BOOK_PILE.get(),
                PUBlocks.LIGHT_BLUE_BOOK_PILE.get(),
                PUBlocks.BLUE_BOOK_PILE.get(),
                PUBlocks.PURPLE_BOOK_PILE.get(),
                PUBlocks.MAGENTA_BOOK_PILE.get(),
                PUBlocks.PINK_BOOK_PILE.get(),
                PUBlocks.GRASS_TURF_LAYER.get(),
                PUBlocks.PODZOL_TURF_LAYER.get(),
                PUBlocks.MYCELIUM_TURF_LAYER.get()
        );
        for (Block quarterSlab : quarterSlabs) {
            createQuarterSlabItemTable(quarterSlab);
        }
    }

    // quarter slab loot tables
    protected LootTable.Builder createQuarterSlabDrops(Block slab) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(slab, LootItem.lootTableItem(slab).apply(List.of(2, 3, 4), (layers) ->
                SetItemCountFunction.setCount(ConstantValue.exactly(layers.floatValue())).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(slab).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(QuarterSlabBlock.LAYERS, layers)))
        ))));
    }
    protected void createQuarterSlabItemTable(Block slab) {
        add(slab, this::createQuarterSlabDrops);
    }

    // horizontal slab loot tables
    protected LootTable.Builder createHorizontalSlabDrops(Block slab) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(slab, LootItem.lootTableItem(slab).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(slab).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HorizontalSlabBlock.TYPE, SlabType.DOUBLE)))
        ))));
    }
    protected void createHorizontalSlabItemTable(Block slab) {
        add(slab, this::createHorizontalSlabDrops);
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}