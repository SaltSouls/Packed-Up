package salted.packedup.data.models.builders.blocks;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import salted.packedup.common.block.QuarterSlabBlock;
import salted.packedup.common.block.TurfBlock;
import salted.packedup.common.block.TurfLayerBlock;
import salted.packedup.common.registry.PURegistry;
import salted.packedup.data.models.PUBlockStates;
import salted.packedup.data.models.builders.PUBlockBuilder;

import static salted.packedup.data.utils.NameUtils.*;

public class OverlayModelBuilder {
    private final PUBlockBuilder provider;

    public OverlayModelBuilder(PUBlockStates provider) {
        this.provider = provider;
    }

    /**
     * Folder for the overlay-based blocks. Turf models and textures live in
     * {@code block/turf/}, thatch models and textures in {@code block/thatch/}.
     */
    private static String overlayFolder(String baseName) {
        return baseName.contains("_turf") ? "turf/" : "thatch/";
    }

    private static ResourceLocation overlayTexture(String baseName, String suffix) {
        return blockLocation(overlayFolder(baseName) + baseName + suffix);
    }

    private static String overlayModel(String baseName, String modelName) {
        return blockLocation(overlayFolder(baseName) + modelName).toString();
    }

    // ============================================================
    // Overlay Full Blocks
    // ============================================================
    public BlockModelBuilder simpleOverlayBlock(Block block, boolean tint) {
        String name = blockName(block);
        String parentModel;
        ResourceLocation topTexture;
        if (tint) { parentModel = "template/tinted_overlay_block"; }
        else { parentModel = "template/overlay_block"; }
        String blockModel = overlayModel(name, name);

        if (block instanceof TurfBlock) {
            String parentBlock = nameFromSplit(name, "_turf", true);
            if (block.defaultBlockState().is(PURegistry.GRASS_TURF.get())) {
                topTexture = mcBlockLocation(parentBlock + "_block_top");
            } else {
                topTexture = mcBlockLocation(parentBlock + "_top");
            }
        } else {
            topTexture = overlayTexture(name, "_top");
        }

        return provider.models().withExistingParent(blockModel, provider.parent(parentModel))
                .texture("top", topTexture)
                .texture("side", overlayTexture(name, "_side"))
                .texture("overlay", overlayTexture(name, "_overlay"));
    }

    // ============================================================
    // Turf Layers
    // ============================================================
    private BlockModelBuilder turfBlock(QuarterSlabBlock block, int layer, boolean tint) {
        String name = nameFromSplit(blockName(block), "_layer", true);
        String topName = nameFromSplit(name, "_turf", true);
        ResourceLocation topTexture;
        String suffix = "_layer" + layer;
        String parentModel;
        String blockModel = overlayModel(name, name) + suffix;

        if (layer == 1) {
            if (tint) { parentModel = "template/tinted_overlay_slab"; }
            else { parentModel = "template/overlay_slab"; }
        } else {
            if (tint) { parentModel = "turf/template/tinted_turf" + suffix; }
            else { parentModel = "turf/template/turf" + suffix; }
        }

        if (block.defaultBlockState().is(PURegistry.GRASS_TURF_LAYER.get())) {
            topTexture = mcBlockLocation(topName + "_block_top");
        } else {
            topTexture = mcBlockLocation(topName + "_top");
        }

        return provider.models().withExistingParent(blockModel, provider.parent(parentModel))
                .texture("top", topTexture)
                .texture("side", overlayTexture(name, "_side"))
                .texture("overlay", overlayTexture(name, "_overlay"));
    }

    public void simpleTurfBlock(QuarterSlabBlock block, boolean tint, Property<?>... ignored) {
        String parentBlock = nameFromSplit(blockName(block), "_layer", true);

        provider.variantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            int layers = state.getValue(layersProperty);

            if (layers == 4) { return ConfiguredModel.allYRotations(provider.existingModel(overlayFolder(parentBlock) + parentBlock), 0, false); }
            else return ConfiguredModel.allYRotations(turfBlock(block, layers - 1, tint), 0, false);
        }, ignored);
    }

    // ============================================================
    // Quarter Slab Blocks (podzol turf layer)
    // ============================================================
    private BlockModelBuilder quarterSlabBlock(QuarterSlabBlock block, int layer, boolean bottomTop) {
        String name = nameFromSplit(blockName(block), "_layer", true);
        String suffix = "_layer" + layer;
        String parentModel;
        ResourceLocation topTexture;
        ResourceLocation bottomTexture;

        if (block instanceof TurfLayerBlock) {
            String parentBlock = nameFromSplit(name, "_turf", true);
            topTexture = mcBlockLocation(parentBlock + "_top");
        } else {
            topTexture = overlayTexture(name, "_top");
        }
        if (bottomTop) { bottomTexture = overlayTexture(name, "_bottom"); }
        else { bottomTexture = topTexture; }

        if (layer == 1) { parentModel = "block/slab"; }
        else parentModel = provider.parent("template/quarter_slab" + suffix);

        return provider.models().withExistingParent(overlayModel(name, name) + suffix, parentModel)
                .texture("top", topTexture)
                .texture("side", overlayTexture(name, "_side"))
                .texture("bottom", bottomTexture);
    }

    public void simpleQuarterSlabBlock(QuarterSlabBlock block, boolean randomRotation, boolean bottomTop, Property<?>... ignored) {
        String name = nameFromSplit(blockName(block), "_layer", true);

        provider.variantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            int layers = state.getValue(layersProperty);

            if (layers == 4) {
                var fullModel = provider.existingModel(overlayFolder(name) + name);
                if (randomRotation) { return ConfiguredModel.allYRotations(fullModel, 0, false); }
                else return ConfiguredModel.builder().modelFile(fullModel).build();
            }
            var model = quarterSlabBlock(block, layers - 1, bottomTop);

            if (randomRotation) { return ConfiguredModel.allYRotations(model, 0, false); }
            else return ConfiguredModel.builder().modelFile(model).build();
        }, ignored);
    }

    // ============================================================
    // Grass Bale
    // ============================================================
    public void grassBaleBlock(RotatedPillarBlock block) {
        String name = "bundle/" + blockName(block);

        provider.variantBuilder(block).forAllStates(state -> {
            Direction.Axis dir = state.getValue(BlockStateProperties.AXIS);

            if (dir == Direction.Axis.X) { return ConfiguredModel.builder().modelFile(provider.existingModel(name + "_horizontal")).rotationY(90).build(); }
            else if (dir == Direction.Axis.Y) { return ConfiguredModel.builder().modelFile(provider.existingModel(name)).build(); }
            else return ConfiguredModel.builder().modelFile(provider.existingModel(name + "_horizontal")).build();
        });
    }

    // ============================================================
    // Overlay Stairs
    // ============================================================
    private BlockModelBuilder overlayStairBlock(StairBlock block, StairsShape shape, Half half) {
        String name = blockName(block);
        String parentBlock = nameFromSplit(name, "_stairs", true);
        String parentModel;
        String blockModel = overlayModel(parentBlock, name);

        if (shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT) {
            parentModel = "template/overlay_inner_stairs";
            blockModel = blockModel + "_inner";
        } else if (shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT) {
            parentModel = "template/overlay_outer_stairs";
            blockModel = blockModel + "_outer";
        } else { parentModel = "template/overlay_stairs"; }

        if (half == Half.TOP) {
            parentModel = parentModel + "_top";
            blockModel = blockModel + "_top";
        }

        return provider.models().withExistingParent(blockModel, provider.parent(parentModel))
                .texture("top", overlayTexture(parentBlock, "_top"))
                .texture("side", overlayTexture(parentBlock, "_side"))
                .texture("overlay", overlayTexture(parentBlock, "_overlay"));
    }

    public void simpleOverlayStairsBlock(StairBlock block) {
        provider.variantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(StairBlock.FACING);
            Half half = state.getValue(StairBlock.HALF);
            StairsShape shape = state.getValue(StairBlock.SHAPE);

            int yRot = (int) facing.getClockWise().toYRot();
            if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) { yRot += 270; }
            if (shape != StairsShape.STRAIGHT && half == Half.TOP) { yRot += 90; }

            yRot %= 360;
            boolean uvlock = yRot != 0 || half == Half.TOP;
            return ConfiguredModel.builder()
                    .modelFile(overlayStairBlock(block, shape, half))
                    .rotationY(yRot)
                    .uvLock(uvlock)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }

    // ============================================================
    // Overlay Slabs
    // ============================================================
    private BlockModelBuilder overlaySlabBlock(SlabBlock block, SlabType type) {
        String name = blockName(block);
        String parentBlock = nameFromSplit(name, "_slab", true);
        String parentModel = "template/overlay_slab";
        String blockModel = overlayModel(parentBlock, name);

        if (type == SlabType.TOP) {
            parentModel = parentModel + "_top";
            blockModel = blockModel + "_top";
        }

        return provider.models().withExistingParent(blockModel, provider.parent(parentModel))
                .texture("top", overlayTexture(parentBlock, "_top"))
                .texture("side", overlayTexture(parentBlock, "_side"))
                .texture("overlay", overlayTexture(parentBlock, "_overlay"));
    }

    public void simpleOverlaySlabBlock(SlabBlock block) {
        provider.variantBuilder(block).forAllStatesExcept(state -> {
            String name = blockName(block);
            String parentBlock = nameFromSplit(name, "_slab", true);
            SlabType type = state.getValue(SlabBlock.TYPE);

            if (type == SlabType.DOUBLE) {
                return ConfiguredModel.builder().modelFile(provider.existingModel(overlayFolder(parentBlock) + parentBlock)).build();
            } else {
                return ConfiguredModel.builder().modelFile(overlaySlabBlock(block, type)).build();
            }
        }, BlockStateProperties.WATERLOGGED);
    }
}
