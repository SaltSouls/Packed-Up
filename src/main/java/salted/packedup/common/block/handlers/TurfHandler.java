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

    public InteractionResult shovelTurf(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack item = player.getMainHandItem();
        Block block = state.getBlock();
        ItemStack blockItem;

        if (!item.is(ItemTags.SHOVELS)) return InteractionResult.PASS;
        if (block instanceof TurfLayerBlock) {
            if (!(state.getValue(PUProperties.QUARTER_LAYERS) > 1)) return InteractionResult.PASS;
            blockItem = block.asItem().getDefaultInstance();

            world.setBlockAndUpdate(pos, state.setValue(PUProperties.QUARTER_LAYERS, state.getValue(PUProperties.QUARTER_LAYERS) - 1)
                    .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)));
            if (!player.isCreative()) { popResourceFromFace(world, pos, hit.getDirection(), blockItem); }
        }
        else if (block instanceof TurfBlock) {
            Block layerBlock = getTurfLayer(block);
            blockItem = layerBlock.asItem().getDefaultInstance();

            world.setBlockAndUpdate(pos, layerBlock.withPropertiesOf(layerBlock.defaultBlockState()).setValue(PUProperties.QUARTER_LAYERS, 3)
                    .setValue(BlockStateProperties.WATERLOGGED, false));
            if (!player.isCreative()) { popResourceFromFace(world, pos, hit.getDirection(), blockItem); }
        }

        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (world.isClientSide) { return InteractionResult.SUCCESS; }
        return InteractionResult.CONSUME;
    }

    // bonemeal functions
    public boolean isBonemealable(BlockState state, LevelReader world, BlockPos pos) {
        Block block = state.getBlock();
        if (!Turf.GRASS.contains(state.getBlock())) return false;
        if (block == Turf.GRASS.getTurfLayer() && !(state.getValue(PUProperties.QUARTER_LAYERS) == 4)) return false;

        return world.getBlockState(pos.above()).isAir();
    }

    public static void bonemealTurf(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.above();
        Registry<PlacedFeature> feature = world.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        Optional<Holder.Reference<PlacedFeature>> optional = feature.getHolder(VegetationPlacements.GRASS_BONEMEAL);

        bonemeal:
        for (int i = 0; i < 128; ++i) {
            BlockPos blockpos1 = blockpos;

            for (int j = 0; j < i / 16; ++j) {
                int x = random.nextInt(3) - 1;
                int y = (random.nextInt(3) - 1) * random.nextInt(3) / 2;
                int z = random.nextInt(3) - 1;
                BlockState blockState1 = world.getBlockState(blockpos1.below());
                boolean belowIsThis = Turf.GRASS.contains(blockState1.getBlock());
                boolean isFullBlock = world.getBlockState(blockpos1).isCollisionShapeFullBlock(world, blockpos1);

                blockpos1 = blockpos1.offset(x, y, z);
                if (!belowIsThis || isFullBlock) continue bonemeal;
            }

            BlockState blockstate2 = world.getBlockState(blockpos1);
            if (blockstate2.is(state.getBlock()) && random.nextInt(10) == 0) {
                ((BonemealableBlock) state.getBlock()).performBonemeal(world, random, blockpos1, blockstate2);
            }

            if (!blockstate2.isAir()) continue;
            Holder<PlacedFeature> holder;
            if (random.nextInt(8) == 0) {
                Holder<Biome> biome = world.getBiome(blockpos1);

                List<ConfiguredFeature<?, ?>> list = biome.value().getGenerationSettings().getFlowerFeatures();
                if (list.isEmpty()) continue;
                holder = ((RandomPatchConfiguration)list.get(0).config()).feature();
            } else {
                if (optional.isEmpty()) continue;
                holder = optional.get();
            }

            holder.value().place(world, world.getChunkSource().getGenerator(), random, blockpos1);
        }
    }

    // mycelium turf particles
    public void spawnMyceliumParticles(BlockState state, Level world, BlockPos pos, RandomSource random) {
        Block block = state.getBlock();

        if (!Turf.MYCELIUM.contains(block)) return;
        double yOffset = 1.1D;

        // set proper y offset for particles based on layer height
        if (block == Turf.MYCELIUM.getTurfLayer()) {
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

    // turf spreading functions
    // doesn't need to check for light as it will never convert to dirt
    public void spreadGrass(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (Turf.PODZOL.contains(state.getBlock())) return; // return early as podzol can't spread
        if (!world.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
        if (!(world.getMaxLocalRawBrightness(pos.above()) >= 9)) return;
        BlockState blockstate = getTurfSource(state);

        for (int i = 0; i < 4; ++i) {
            int x = random.nextInt(3) - 1;
            int y = random.nextInt(5) - 3;
            int z = random.nextInt(3) - 1;

            BlockPos blockpos = pos.offset(x, y, z);
            if (world.getBlockState(blockpos).is(Blocks.DIRT) && canPropagate(blockstate, world, blockpos)) {
                    world.setBlockAndUpdate(blockpos, blockstate.setValue(BlockStateProperties.SNOWY, world.getBlockState(blockpos.above()).is(Blocks.SNOW)));
            }
        }
    }
}
