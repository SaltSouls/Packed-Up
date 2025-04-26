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

    /**
     * Map for fast lookups: Maps blocks ({@link TurfBlock} and {@link TurfLayerBlock}) to their corresponding {@link Turf} type.
     * This allows for quick retrieval of the Turf type based on a {@link Block}.
     */
    private static final Map<Block, Turf> TURF_MAP = Arrays.stream(Turf.values())
            .flatMap(turf -> Arrays.stream(new Block[]{turf.getTurfBlock(), turf.getTurfLayer()})
                    .map(block -> Map.entry(block, turf)))                // Create a Map.Entry for each block and its corresponding Turf
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // Collect into a Map<Block, Turf>

    /**
     * Retrieves the turf type associated with a given block.
     *
     * @param block The {@link Block} to look up.
     * @return The {@link Turf} type associated with the block, or null if the block is not a valid {@link TurfBlock} or {@link TurfLayerBlock}.
     */
    protected Turf getTurfType(Block block) {
        return TURF_MAP.get(block);
    }

    /**
     * Gets the turf layer block associated with a given turf block.
     *
     * @param block The {@link TurfBlock} to look up.
     * @return The {@link TurfLayerBlock}, or null if the block is not a valid turf block.
     */
    protected Block getTurfLayer(Block block) {
        Turf turf = getTurfType(block); // Get the Turf type for the block
        return turf != null ? turf.getTurfLayer() : null;
    }

    /**
     * Gets the source block state for a given turf block.
     *
     * @param state The {@link BlockState} of the {@link TurfBlock}.
     * @return The source block state, or the original state if the block is not a valid turf block.
     */
    protected BlockState getTurfSource(BlockState state) {
        Block block = state.getBlock(); // Get the block from the state
        Turf turf = getTurfType(block); // Get the Turf type for the block
        return turf != null ? turf.getSource().defaultBlockState() : state;
    }

    /**
     * Checks if a turf block can spread to nearby blocks.
     *
     * @param state The {@link BlockState} of the turf block.
     * @param world The world ({@link LevelReader}) where the block is located.
     * @param pos   The {@link BlockPos} of the block.
     * @return True if the turf can propagate, false otherwise.
     */
    protected boolean canPropagate(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockpos = pos.above(); // Get the block position above the current position
        return canBeGrass(state, world, pos) && !world.getFluidState(blockpos).is(FluidTags.WATER);
    }

    /**
     * This is a copy of Forge's logic.
     * <p>
     * Checks if a block can be replaced by grass or similar turf blocks.
     *
     * @param state The {@link BlockState} of the turf block.
     * @param world The world ({@link LevelReader}) where the block is located.
     * @param pos   The {@link BlockPos} of the block.
     * @return True if the {@link Block} can be replaced by grass, false otherwise.
     */
    private boolean canBeGrass(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockpos = pos.above();                       // Get the block position above the current position
        BlockState blockstate = world.getBlockState(blockpos); // Get the block state at the above position

        // Check if the block above is snow with a single layer
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1) { return true; }
        // Check if the block above is fully waterlogged
        else if (blockstate.getFluidState().getAmount() == 8) { return false; }
        // Check light levels to determine if grass can spread
        else {
            int lightLevel = LightEngine.getLightBlockInto(world, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(world, blockpos));
            return lightLevel < world.getMaxLightLevel();
        }
    }

    /**
     * Enum representing different types of turf blocks and their associated layers and source blocks.
     * Each Turf type has a corresponding {@link TurfBlock}, {@link TurfLayerBlock}, and source {@link Block}.
     */
    protected enum Turf {
        GRASS(PUBlocks.GRASS_TURF.get(), PUBlocks.GRASS_TURF_LAYER.get(), Blocks.GRASS_BLOCK),
        MYCELIUM(PUBlocks.MYCELIUM_TURF.get(), PUBlocks.MYCELIUM_TURF_LAYER.get(), Blocks.MYCELIUM),
        PODZOL(PUBlocks.PODZOL_TURF.get(), PUBlocks.PODZOL_TURF_LAYER.get(), Blocks.PODZOL);

        private final TurfBlock turfBlock;
        private final TurfLayerBlock turfLayer;
        private final Block source;

        /**
         * Constructor for the {@link Turf} enum.
         *
         * @param turfBlock The {@link Block} representing the {@link TurfBlock}.
         * @param turfLayer The {@link Block} representing the {@link TurfLayerBlock}.
         * @param source    The {@link Block} representing the source {@link Block} that the turf represents.
         */
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
        public Block getSource() {
            return this.source.defaultBlockState().getBlock();
        }
    }
}
