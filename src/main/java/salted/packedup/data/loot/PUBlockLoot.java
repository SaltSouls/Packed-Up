package salted.packedup.data.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import salted.packedup.common.registry.PUBlocks;

import java.util.HashSet;
import java.util.Set;

public class PUBlockLoot extends BlockLootSubProvider {
    private final Set<Block> generatedLootTables = new HashSet<>();

    public PUBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(PUBlocks.CRATE_LID.get());
        dropSelf(PUBlocks.REINFORCED_CRATE_LID.get());
        dropSelf(PUBlocks.COBBLESTONE_CRATE.get());
        dropSelf(PUBlocks.REINFORCED_COBBLESTONE_CRATE.get());
        dropSelf(PUBlocks.COBBLED_DEEPSLATE_CRATE.get());
        dropSelf(PUBlocks.REINFORCED_COBBLED_DEEPSLATE_CRATE.get());
        dropSelf(PUBlocks.ANDESITE_CRATE.get());
        dropSelf(PUBlocks.REINFORCED_ANDESITE_CRATE.get());
        dropSelf(PUBlocks.DIORITE_CRATE.get());
        dropSelf(PUBlocks.REINFORCED_DIORITE_CRATE.get());
        dropSelf(PUBlocks.GRANITE_CRATE.get());
        dropSelf(PUBlocks.REINFORCED_GRANITE_CRATE.get());
        dropSelf(PUBlocks.TUFF_CRATE.get());
        dropSelf(PUBlocks.REINFORCED_TUFF_CRATE.get());
        dropSelf(PUBlocks.BLACKSTONE_CRATE.get());
        dropSelf(PUBlocks.REINFORCED_BLACKSTONE_CRATE.get());
        dropSelf(PUBlocks.BASALT_CRATE.get());
        dropSelf(PUBlocks.REINFORCED_BASALT_CRATE.get());
        dropSelf(PUBlocks.RAW_IRON_CRATE.get());
        dropSelf(PUBlocks.RAW_COPPER_CRATE.get());
        dropSelf(PUBlocks.RAW_GOLD_CRATE.get());
        dropSelf(PUBlocks.GUNPOWDER_CRATE.get());
        dropSelf(PUBlocks.DIRT_BAG.get());
        dropSelf(PUBlocks.ROOTED_DIRT_BAG.get());
        dropSelf(PUBlocks.COARSE_DIRT_BAG.get());
        dropSelf(PUBlocks.GRAVEL_BAG.get());
        dropSelf(PUBlocks.APPLE_BASKET.get());
        dropSelf(PUBlocks.GOLDEN_APPLE_BASKET.get());
        dropSelf(PUBlocks.SWEET_BERRY_BASKET.get());
        dropSelf(PUBlocks.GLOW_BERRY_BASKET.get());
        dropSelf(PUBlocks.COCOA_BEAN_BAG.get());
        dropSelf(PUBlocks.SUGAR_BAG.get());
        dropSelf(PUBlocks.NETHER_WART_BAG.get());
        dropSelf(PUBlocks.GLOWSTONE_DUST_BAG.get());
        dropSelf(PUBlocks.GOLDEN_CARROT_CRATE.get());
        dropSelf(PUBlocks.EGG_CRATE.get());
        dropSelf(PUBlocks.RED_MUSHROOM_CRATE.get());
        dropSelf(PUBlocks.BROWN_MUSHROOM_CRATE.get());
        dropSelf(PUBlocks.COD_BARREL.get());
        dropSelf(PUBlocks.SALMON_BARREL.get());
        dropSelf(PUBlocks.BRICK_PILE.get());
        dropSelf(PUBlocks.BRICK_PALLET.get());
        dropSelf(PUBlocks.NETHER_BRICK_PILE.get());
        dropSelf(PUBlocks.NETHER_BRICK_PALLET.get());
        dropSelf(PUBlocks.STONE_PILE.get());
        dropSelf(PUBlocks.STONE_PALLET.get());
        dropSelf(PUBlocks.DEEPSLATE_PILE.get());
        dropSelf(PUBlocks.DEEPSLATE_PALLET.get());
        dropSelf(PUBlocks.CALCITE_PILE.get());
        dropSelf(PUBlocks.CALCITE_PALLET.get());
        dropSelf(PUBlocks.COPPER_PALLET.get());
        dropSelf(PUBlocks.IRON_PALLET.get());
        dropSelf(PUBlocks.GOLD_PALLET.get());
        dropSelf(PUBlocks.NETHERITE_PALLET.get());
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
