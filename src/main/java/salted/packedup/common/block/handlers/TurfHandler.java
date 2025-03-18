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
    private static final double BASE_OFFSET = 1.1D;
    private static final double OFFSET_FACTOR = 0.34D;
    private static final double OFFSET_ADJUSTMENT = 0.1D;
    private static final int BONEMEAL_ATTEMPTS = 128;
    private static final int TARGET_ATTEMPTS = 16;
    private static final int BONEMEAL_CHANCE = 10;
    private static final int FEATURE_CHANCE = 8;
    private static final int SPREAD_ATTEMPTS = 4;

    // Shovel interaction
    public InteractionResult shovelTurf(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.getMainHandItem().is(ItemTags.SHOVELS)) return InteractionResult.PASS;

        Block block = state.getBlock();
        ItemStack blockItem;
        BlockState newState;

        if (block instanceof TurfLayerBlock && state.getValue(PUProperties.QUARTER_LAYERS) > 1) {
            blockItem = block.asItem().getDefaultInstance();
            newState = state.setValue(PUProperties.QUARTER_LAYERS, state.getValue(PUProperties.QUARTER_LAYERS) - 1)
                    .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED));
        } else if (block instanceof TurfBlock) {
            Block layerBlock = getTurfLayer(block);
            blockItem = layerBlock.asItem().getDefaultInstance();
            newState = layerBlock.defaultBlockState()
                    .setValue(PUProperties.QUARTER_LAYERS, 3)
                    .setValue(BlockStateProperties.WATERLOGGED, false);
        } else {
            return InteractionResult.PASS;
        }

        world.setBlockAndUpdate(pos, newState);
        if (!player.isCreative()) popResourceFromFace(world, pos, hit.getDirection(), blockItem);

        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
        return world.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    // Check if the block is bonemealable
    public boolean isBonemealable(BlockState state, LevelReader world, BlockPos pos) {
        Turf turf = getTurfType(state.getBlock());
        return turf == Turf.GRASS
                && (state.getBlock() != Turf.GRASS.getTurfLayer() || state.getValue(PUProperties.QUARTER_LAYERS) == 4)
                && world.getBlockState(pos.above()).isAir();
    }

    // Apply bonemeal to the turf
    public void bonemealTurf(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.above();
        Registry<PlacedFeature> featureRegistry = world.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        Optional<Holder.Reference<PlacedFeature>> optional = featureRegistry.getHolder(VegetationPlacements.GRASS_BONEMEAL);

        bonemeal:
        for (int i = 0; i < BONEMEAL_ATTEMPTS; ++i) {
            BlockPos targetPos = abovePos;

            for (int j = 0; j < i / TARGET_ATTEMPTS; ++j) {
                if (!isValidBonemealTarget(world, targetPos)) continue bonemeal;
                targetPos = getRandomOffsetPos(abovePos, random, i);
            }

            BlockState targetState = world.getBlockState(targetPos);
            if (targetState.is(state.getBlock()) && random.nextInt(BONEMEAL_CHANCE) == 0) {
                ((BonemealableBlock) state.getBlock()).performBonemeal(world, random, targetPos, targetState);
            }

            if (world.getBlockState(targetPos).isAir()) {
                placeFeature(world, random, targetPos, optional);
            }
        }
    }

    private boolean isValidBonemealTarget(ServerLevel world, BlockPos pos) {
        BlockState belowState = world.getBlockState(pos.below());
        Turf turf = getTurfType(belowState.getBlock());
        return turf == Turf.GRASS && !world.getBlockState(pos).isCollisionShapeFullBlock(world, pos);
    }

    private BlockPos getRandomOffsetPos(BlockPos pos, RandomSource random, int attempt) {
        int x = random.nextInt(3) - 1;
        int y = (random.nextInt(3) - 1) * random.nextInt(3) / 2;
        int z = random.nextInt(3) - 1;
        return pos.offset(x, y, z);
    }

    private void placeFeature(ServerLevel world, RandomSource random, BlockPos pos, Optional<Holder.Reference<PlacedFeature>> optional) {
        Holder<PlacedFeature> holder = random.nextInt(FEATURE_CHANCE) == 0
                ? getFlowerFeature(world, pos)
                : optional.orElse(null);

        if (holder == null) return;
        holder.value().place(world, world.getChunkSource().getGenerator(), random, pos);
    }

    private Holder<PlacedFeature> getFlowerFeature(ServerLevel world, BlockPos pos) {
        Holder<Biome> biome = world.getBiome(pos);
        List<ConfiguredFeature<?, ?>> flowerFeatures = biome.value().getGenerationSettings().getFlowerFeatures();
        return flowerFeatures.isEmpty() ? null : ((RandomPatchConfiguration) flowerFeatures.get(0).config()).feature();
    }

    // Mycelium turf particles
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

    private double calcParticleOffset(BlockState state) {
        if (state.getBlock() != Turf.MYCELIUM.getTurfLayer()) return BASE_OFFSET;

        int layers = state.getValue(PUProperties.QUARTER_LAYERS);
        return layers < 4 ? (OFFSET_FACTOR * layers) - (layers * OFFSET_ADJUSTMENT - OFFSET_ADJUSTMENT) : BASE_OFFSET;
    }

    // Turf spreading function
    public void spreadGrass(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        Turf turf = getTurfType(state.getBlock());
        if (turf == Turf.PODZOL || !world.isAreaLoaded(pos, 3)) return;

        BlockState sourceState = getTurfSource(state);
        if (world.getMaxLocalRawBrightness(pos.above()) < 9) return;

        for (int i = 0; i < SPREAD_ATTEMPTS; ++i) {
            BlockPos targetPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            BlockState targetState = world.getBlockState(targetPos);

            if (targetState.is(Blocks.DIRT) && canPropagate(sourceState, world, targetPos)) {
                world.setBlockAndUpdate(targetPos, sourceState.setValue(BlockStateProperties.SNOWY, world.getBlockState(targetPos.above()).is(Blocks.SNOW)));
            }
        }
    }
}
