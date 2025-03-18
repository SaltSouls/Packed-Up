package salted.packedup.common.block.handlers.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import salted.packedup.common.block.TurfBlock;
import salted.packedup.common.block.TurfLayerBlock;
import salted.packedup.common.registry.PUBlocks;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TurfUtils {

    // Map for fast lookups
    private static final Map<Block, Turf> TURF_MAP = Arrays.stream(Turf.values())
            .flatMap(turf -> Arrays.stream(new Block[]{turf.getTurfBlock(), turf.getTurfLayer()})
                    .map(block -> Map.entry(block, turf)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    // Lookup method
    protected Turf getTurfType(Block block) {
        return TURF_MAP.get(block);
    }

    // Get turf layer
    protected Block getTurfLayer(Block block) {
        Turf turf = getTurfType(block);
        return turf != null ? turf.getTurfLayer() : null;
    }

    // Get turf source
    protected BlockState getTurfSource(BlockState state) {
        Block block = state.getBlock();
        Turf turf = getTurfType(block);
        return turf != null ? turf.getSource() : state;
    }

    // Check if turf can propagate
    protected boolean canPropagate(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return canBeGrass(state, world, pos) && !world.getFluidState(blockpos).is(FluidTags.WATER);
    }

    // Copy of forge's functions
    private boolean canBeGrass(BlockState state, LevelReader worldReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = worldReader.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1)  return true;
        else if (blockstate.getFluidState().getAmount() == 8)  return false;
        else {
            int lightLevel = LightEngine.getLightBlockInto(worldReader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(worldReader, blockpos));
            return lightLevel < worldReader.getMaxLightLevel();
        }
    }

    // Enum definition
    protected enum Turf {
        GRASS(PUBlocks.GRASS_TURF.get(), PUBlocks.GRASS_TURF_LAYER.get(), Blocks.GRASS_BLOCK),
        MYCELIUM(PUBlocks.MYCELIUM_TURF.get(), PUBlocks.MYCELIUM_TURF_LAYER.get(), Blocks.MYCELIUM),
        PODZOL(PUBlocks.PODZOL_TURF.get(), PUBlocks.PODZOL_TURF_LAYER.get(), Blocks.PODZOL);

        private final TurfBlock turfBlock;
        private final TurfLayerBlock turfLayer;
        private final Block source;

        Turf(TurfBlock turfBlock, TurfLayerBlock turfLayer, Block source) {
            this.turfBlock = turfBlock;
            this.turfLayer = turfLayer;
            this.source = source;
        }

        public TurfBlock getTurfBlock() {
            return this.turfBlock;
        }

        public TurfLayerBlock getTurfLayer() {
            return this.turfLayer;
        }

        public BlockState getSource() {
            return source.defaultBlockState();
        }
    }
}
