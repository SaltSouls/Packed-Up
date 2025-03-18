package salted.packedup.common.block.handlers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.phys.BlockHitResult;
import salted.packedup.common.block.TurfBlock;
import salted.packedup.common.block.TurfLayerBlock;
import salted.packedup.common.block.handlers.utils.TurfUtils;
import salted.packedup.common.block.state.PUProperties;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.level.block.Block.popResourceFromFace;

public class TurfHandler extends TurfUtils {

    // Constants
    private static final double BASE_OFFSET = 1.1D;       // Base vertical offset for particle spawning
    private static final double OFFSET_FACTOR = 0.34D;    // Factor used to calculate particle offset based on layers
    private static final double OFFSET_ADJUSTMENT = 0.1D; // Adjustment value for fine-tuning particle offset
    private static final int BONEMEAL_ATTEMPTS = 128;     // Number of attempts to apply bonemeal effects
    private static final int TARGET_ATTEMPTS = 16;        // Number of attempts to find a valid target position for bonemeal
    private static final int BONEMEAL_CHANCE = 10;        // Chance (1 in 10) for bonemeal to succeed on a target block
    private static final int FEATURE_CHANCE = 8;          // Chance (1 in 8) for a feature (e.g., flower) to be placed
    private static final int SPREAD_ATTEMPTS = 4;         // Number of attempts for grass to spread to nearby blocks

    /**
     * Handles the interaction when a player uses a shovel on a {@link TurfBlock}.
     * Reduces the layer count of the turf or converts it to a different type, depending on the current state.
     *
     * @param state  The current {@link BlockState}.
     * @param world  The world ({@link Level}) where the interaction occurs.
     * @param pos    The {@link BlockPos} of the block.
     * @param player The {@link Player} performing the interaction.
     * @param hit    The {@link BlockHitResult} of the interaction.
     * @return An {@link InteractionResult} indicating the outcome of the interaction (SUCCESS, CONSUME, or PASS).
     */
    public InteractionResult shovelTurf(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.getMainHandItem().is(ItemTags.SHOVELS)) return InteractionResult.PASS;

        Block block = state.getBlock();
        ItemStack blockItem;
        BlockState newState;

        // If the block is a TurfLayerBlock and has more than one layer, reduce the layer count
        if (block instanceof TurfLayerBlock && state.getValue(PUProperties.QUARTER_LAYERS) > 1) {
            blockItem = block.asItem().getDefaultInstance();
            newState = state.setValue(PUProperties.QUARTER_LAYERS, state.getValue(PUProperties.QUARTER_LAYERS) - 1)
                    .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED));

        } // If the block is a TurfBlock, convert it to a TurfLayerBlock with 3 layers
        else if (block instanceof TurfBlock) {
            Block layerBlock = getTurfLayer(block);
            blockItem = layerBlock.asItem().getDefaultInstance();
            newState = layerBlock.defaultBlockState()
                    .setValue(PUProperties.QUARTER_LAYERS, 3)
                    .setValue(BlockStateProperties.WATERLOGGED, false);
        }
        else { return InteractionResult.PASS; }

        // Update the block state in the world with the new state, drop block item if not in creative, and play shovel flattening sound
        world.setBlockAndUpdate(pos, newState);
        if (!player.isCreative()) popResourceFromFace(world, pos, hit.getDirection(), blockItem);
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);

        // Return SUCCESS on the client side, CONSUME on the server side
        return world.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    /**
     * Checks if a block is eligible for bonemeal application.
     * Only grass turf with full layers and air above can be bonemealed.
     *
     * @param state The current {@link BlockState}.
     * @param world The world ({@link LevelReader}) where the block is located.
     * @param pos   The {@link BlockPos} of the block.
     * @return True if the block is bonemealable, otherwise false.
     */
    public boolean isBonemealable(BlockState state, LevelReader world, BlockPos pos) {
        Turf turf = getTurfType(state.getBlock());
        // Only grass turf with full layers and air above can be bonemealed
        return turf == Turf.GRASS
                && (state.getBlock() != Turf.GRASS.getTurfLayer() || state.getValue(PUProperties.QUARTER_LAYERS) == 4)
                && world.getBlockState(pos.above()).isAir();
    }

    /**
     * Applies bonemeal to the {@link TurfBlock}, attempting to grow grass or place features like flowers.
     *
     * @param world  The world ({@link ServerLevel}) where the bonemeal is applied.
     * @param random A {@link RandomSource} for determining outcomes.
     * @param pos    The {@link BlockPos} of the block.
     * @param state  The current {@link BlockState}.
     */
    public void bonemealTurf(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.above();
        Registry<PlacedFeature> featureRegistry = world.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        Optional<Holder.Reference<PlacedFeature>> optional = featureRegistry.getHolder(VegetationPlacements.GRASS_BONEMEAL);

        // Attempt to apply bonemeal multiple times
        bonemeal:
        for (int i = 0; i < BONEMEAL_ATTEMPTS; ++i) {
            BlockPos targetPos = abovePos;

            // Adjust the target position based on the number of attempts
            for (int j = 0; j < i / TARGET_ATTEMPTS; ++j) {
                if (!isValidBonemealTarget(world, targetPos)) continue bonemeal;
                targetPos = getRandomOffsetPos(abovePos, random);
            }

            // Apply bonemeal to the target block with a certain chance
            BlockState targetState = world.getBlockState(targetPos);
            if (targetState.is(state.getBlock()) && random.nextInt(BONEMEAL_CHANCE) == 0) {
                ((BonemealableBlock) state.getBlock()).performBonemeal(world, random, targetPos, targetState);
            }

            // If the target position is air, place a feature (e.g., grass, flower)
            if (world.getBlockState(targetPos).isAir()) {
                placeFeature(world, random, targetPos, optional);
            }
        }
    }

    /**
     * Checks if a position is a valid target for bonemeal application.
     * Only grass turf with non-full collision shape is valid.
     *
     * @param world The world ({@link ServerLevel}) where the check is performed.
     * @param pos   The {@link BlockPos} to check.
     * @return True if the position is a valid bonemeal target, otherwise false.
     */
    private boolean isValidBonemealTarget(ServerLevel world, BlockPos pos) {
        BlockState belowState = world.getBlockState(pos.below());
        Turf turf = getTurfType(belowState.getBlock());
        // Only blocks in the grass turf enum with non-full collision shapes are valid
        return turf == Turf.GRASS && !world.getBlockState(pos).isCollisionShapeFullBlock(world, pos);
    }

    /**
     * Generates a random offset position around the given position.
     *
     * @param pos    The base {@link BlockPos}.
     * @param random A {@link RandomSource} for generating offsets.
     * @return A new BlockPos with a random offset.
     */
    private BlockPos getRandomOffsetPos(BlockPos pos, RandomSource random) {
        int x = random.nextInt(3) - 1;
        int y = (random.nextInt(3) - 1) * random.nextInt(3) / 2;
        int z = random.nextInt(3) - 1;
        return pos.offset(x, y, z);
    }

    /**
     * Places a feature (e.g., grass, flower) at the given position.
     *
     * @param world  The world ({@link ServerLevel}) where the feature is placed.
     * @param random A {@link RandomSource} for determining which feature to place.
     * @param pos    The {@link BlockPos} where the feature will be placed.
     * @param optional An {@link Optional} for the default feature to place.
     */
    private void placeFeature(ServerLevel world, RandomSource random, BlockPos pos, Optional<Holder.Reference<PlacedFeature>> optional) {
        Holder<PlacedFeature> holder = random.nextInt(FEATURE_CHANCE) == 0
                ? getFlowerFeature(world, pos)
                : optional.orElse(null);

        if (holder == null) return;
        holder.value().place(world, world.getChunkSource().getGenerator(), random, pos);
    }

    /**
     * Retrieves a flower feature based on the biome at the given position.
     *
     * @param world The world ({@link ServerLevel}) where the biome is checked.
     * @param pos The {@link BlockPos} to check for the biome.
     * @return A {@link Holder} for the flower feature, or null if no flower feature is available.
     */
    private Holder<PlacedFeature> getFlowerFeature(ServerLevel world, BlockPos pos) {
        Holder<Biome> biome = world.getBiome(pos);
        List<ConfiguredFeature<?, ?>> flowerFeatures = biome.value().getGenerationSettings().getFlowerFeatures();
        return flowerFeatures.isEmpty() ? null : ((RandomPatchConfiguration) flowerFeatures.get(0).config()).feature();
    }

    /**
     * Spawns mycelium particles at the given position.
     *
     * @param state  The current {@link BlockState}.
     * @param world  The world ({@link Level}) where the particles are spawned.
     * @param pos    The {@link BlockPos} where the particles are spawned.
     * @param random A {@link RandomSource} for determining particle spawn chance.
     */
    public void spawnMyceliumParticles(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (getTurfType(state.getBlock()) != Turf.MYCELIUM) return;

        double yOffset = calcParticleOffset(state);
        if (random.nextInt(10) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + yOffset;
            double z = pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.MYCELIUM, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Calculates the vertical offset for particle spawning based on the number of layers.
     *
     * @param state The current {@link BlockState}.
     * @return The calculated vertical offset.
     */
    private double calcParticleOffset(BlockState state) {
        if (state.getBlock() != Turf.MYCELIUM.getTurfLayer()) return BASE_OFFSET;

        int layers = state.getValue(PUProperties.QUARTER_LAYERS);
        return layers < 4 ? (OFFSET_FACTOR * layers) - (layers * OFFSET_ADJUSTMENT - OFFSET_ADJUSTMENT) : BASE_OFFSET;
    }

    /**
     * Spreads grass to nearby dirt blocks, simulating natural grass spread.
     *
     * @param state  The current {@link BlockState}.
     * @param world  The world ({@link ServerLevel}) where the grass is spreading.
     * @param pos    The {@link BlockPos} of the block.
     * @param random A {@link RandomSource} for determining spread attempts.
     */
    public void spreadGrass(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        Turf turf = getTurfType(state.getBlock());
        if (turf == Turf.PODZOL || !world.isAreaLoaded(pos, 3)) return;

        BlockState sourceState = getTurfSource(state);
        if (world.getMaxLocalRawBrightness(pos.above()) < 9) return;

        // Attempt to spread grass to nearby blocks
        for (int i = 0; i < SPREAD_ATTEMPTS; ++i) {
            BlockPos targetPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            BlockState targetState = world.getBlockState(targetPos);

            if (targetState.is(Blocks.DIRT) && canPropagate(sourceState, world, targetPos)) {
                world.setBlockAndUpdate(targetPos, sourceState.setValue(BlockStateProperties.SNOWY, world.getBlockState(targetPos.above()).is(Blocks.SNOW)));
            }
        }
    }
}
