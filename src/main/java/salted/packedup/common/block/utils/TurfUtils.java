package salted.packedup.common.block.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.BlockHitResult;
import salted.packedup.common.block.TurfBlock;
import salted.packedup.common.block.TurfLayerBlock;
import salted.packedup.common.block.state.PUProperties;
import salted.packedup.common.registry.PUBlocks;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.level.block.Block.popResourceFromFace;

public class TurfUtils {

    public static InteractionResult shovelTurf(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack item = player.getMainHandItem();
        Block block = state.getBlock();
        ItemStack blockItem;

        if (!item.is(ItemTags.SHOVELS)) return InteractionResult.PASS;
        if (block instanceof TurfLayerBlock) {
            if (!(state.getValue(PUProperties.QUARTER_LAYERS) > 1)) return InteractionResult.PASS;
            blockItem = state.getBlock().asItem().getDefaultInstance();

            world.setBlockAndUpdate(pos, state.setValue(PUProperties.QUARTER_LAYERS, state.getValue(PUProperties.QUARTER_LAYERS) - 1)
                    .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)));
            if (!player.isCreative()) { popResourceFromFace(world, pos, hit.getDirection(), blockItem); }
        }
        else if (block instanceof TurfBlock) {
            Block layerBlock = getTurfLayer(state);
            blockItem = layerBlock.asItem().getDefaultInstance();

            world.setBlockAndUpdate(pos, layerBlock.withPropertiesOf(layerBlock.defaultBlockState()).setValue(PUProperties.QUARTER_LAYERS, 3)
                    .setValue(BlockStateProperties.WATERLOGGED, false));
            if (!player.isCreative()) { popResourceFromFace(world, pos, hit.getDirection(), blockItem); }
        }

        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (world.isClientSide) { return InteractionResult.SUCCESS; }
        return InteractionResult.CONSUME;
    }

    private static Block getTurfLayer(BlockState state) {
        Block block = state.getBlock();

        if (state.is(PUBlocks.GRASS_TURF.get())) { return PUBlocks.GRASS_TURF_LAYER.get(); }
        if (state.is(PUBlocks.PODZOL_TURF.get())) { return PUBlocks.PODZOL_TURF_LAYER.get(); }
        if (state.is(PUBlocks.MYCELIUM_TURF.get())) { return PUBlocks.MYCELIUM_TURF_LAYER.get(); }
        else return block;
    }

    // bonemeal functions
    public static boolean isBonemealable(BlockState state, LevelReader world, BlockPos pos) {
        if (!isTurf(state, PUBlocks.GRASS_TURF.get(), PUBlocks.GRASS_TURF_LAYER.get())) return false;
        if (state.is(PUBlocks.GRASS_TURF_LAYER.get()) && !(state.getValue(PUProperties.QUARTER_LAYERS) == 4)) return false;

        return world.getBlockState(pos.above()).isAir();
    }

    public static void bonemealTurf(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.above();
        Registry<PlacedFeature> feature = world.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        Optional<Holder.Reference<PlacedFeature>> optional = feature.getHolder(VegetationPlacements.GRASS_BONEMEAL);

        bonemeal:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;

            for(int j = 0; j < i / 16; ++j) {
                int x = random.nextInt(3) - 1;
                int y = (random.nextInt(3) - 1) * random.nextInt(3) / 2;
                int z = random.nextInt(3) - 1;
                BlockState blockState1 = world.getBlockState(blockpos1.below());
                boolean belowIsThis = isTurf(blockState1, PUBlocks.GRASS_TURF.get(), PUBlocks.GRASS_TURF_LAYER.get());
                boolean isFullBlock = world.getBlockState(blockpos1).isCollisionShapeFullBlock(world, blockpos1);

                blockpos1 = blockpos1.offset(x, y, z);
                if (!belowIsThis || isFullBlock) continue bonemeal;
            }

            BlockState blockstate2 = world.getBlockState(blockpos1);
            if (blockstate2.is(state.getBlock()) && random.nextInt(10) == 0) {
                ((BonemealableBlock) state.getBlock()).performBonemeal(world, random, blockpos1, blockstate2);
            }

            if (blockstate2.isAir()) {
                Holder<PlacedFeature> holder;
                if (random.nextInt(8) == 0) {
                    Holder<Biome> biome = world.getBiome(blockpos1);

                    List<ConfiguredFeature<?, ?>> list = biome.value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) continue;
                    holder = ((RandomPatchConfiguration)list.get(0).config()).feature();
                } else {
                    if (!optional.isPresent()) continue;
                    holder = optional.get();
                }

                holder.value().place(world, world.getChunkSource().getGenerator(), random, blockpos1);
            }
        }
    }

    // mycelium turf particles
    public static void spawnMyceliumParticles(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!isTurf(state, PUBlocks.MYCELIUM_TURF.get(), PUBlocks.MYCELIUM_TURF_LAYER.get())) return;
        double yOffset = 1.1D;

        // set proper y offset for particles based on layer height
        if (state.is(PUBlocks.MYCELIUM_TURF_LAYER.get())) {
            int layers = state.getValue(PUProperties.QUARTER_LAYERS);
            if (layers < 4) { yOffset = (0.34D * layers) - ((layers * 0.1D) - 0.1D); }
        }

        if (random.nextInt(10) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + yOffset;
            double z = pos.getZ() + random.nextDouble();

            world.addParticle(ParticleTypes.MYCELIUM, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    // turf grass spreading functions
    // doesn't need to check for light as it will never convert to dirt
    public static void spreadGrass(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (isTurf(state, PUBlocks.PODZOL_TURF.get(), PUBlocks.PODZOL_TURF_LAYER.get())) return; // return early as podzol can't spread
        if (!world.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
        if (!(world.getMaxLocalRawBrightness(pos.above()) >= 9)) return;
        BlockState blockstate = getTurfSource(state);

        for(int i = 0; i < 4; ++i) {
            int x = random.nextInt(3) - 1;
            int y = random.nextInt(5) - 3;
            int z = random.nextInt(3) - 1;

            BlockPos blockpos = pos.offset(x, y, z);
            if (world.getBlockState(blockpos).is(Blocks.DIRT) && canPropagate(blockstate, world, blockpos)) {
                    world.setBlockAndUpdate(blockpos, blockstate.setValue(BlockStateProperties.SNOWY, world.getBlockState(blockpos.above()).is(Blocks.SNOW)));
            }
        }
    }

    // hehe turf go brrr
    public static BlockState getTurfSource(BlockState state) {
        if (isTurf(state, PUBlocks.GRASS_TURF.get(), PUBlocks.GRASS_TURF_LAYER.get())) { return Blocks.GRASS_BLOCK.defaultBlockState(); }
        if (isTurf(state, PUBlocks.MYCELIUM_TURF.get(), PUBlocks.MYCELIUM_TURF_LAYER.get())) { return Blocks.MYCELIUM.defaultBlockState(); }
        else return state;
    }

    // copy of forge's functions
    private static boolean canBeGrass(BlockState state, LevelReader worldReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = worldReader.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1)  return true;
        else if (blockstate.getFluidState().getAmount() == 8)  return false;
        else {
            int lightLevel = LightEngine.getLightBlockInto(worldReader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(worldReader, blockpos));
            return lightLevel < worldReader.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return canBeGrass(state, world, pos) && !world.getFluidState(blockpos).is(FluidTags.WATER);
    }

    // once again, not needed, but makes things cleaner
    private static boolean isTurf(BlockState state, TurfBlock turf, TurfLayerBlock turfLayer) {
        Block block = state.getBlock();
        return block == turf || block == turfLayer;
    }
}
