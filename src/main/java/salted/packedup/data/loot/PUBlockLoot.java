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
        dropSelf(PUBlocks.COBBLESTONE_CRATE.get());
        dropSelf(PUBlocks.COBBLED_DEEPSLATE_CRATE.get());
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
        dropSelf(PUBlocks.COD_BARREL.get());
        dropSelf(PUBlocks.SALMON_BARREL.get());
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
