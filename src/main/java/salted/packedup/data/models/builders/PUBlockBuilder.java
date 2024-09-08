package salted.packedup.data.models.builders;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;
import salted.packedup.PackedUp;
import salted.packedup.common.block.*;
import salted.packedup.common.registry.PUBlocks;

import java.util.function.Function;

import static salted.packedup.data.utils.NameUtils.*;

public class PUBlockBuilder extends BlockStateProvider {
    private static final int DEFAULT_HORIZONTAL_OFFSET = 180;
    static Logger log = PackedUp.LOGGER;

    public PUBlockBuilder(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PackedUp.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // This is never used and shouldn't be used. It is only needed
        // so that I can separate the builders from the registration.
    }

    // general pathing/convenience functions
    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(blockLocation(blockName(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(blockLocation(path), models().existingFileHelper);
    }

    public String parent(String model) {
        return existingModel(model).getLocation().toString();
    }

    public int defaultRotation(Direction dir) {
        return ((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360;
    }

    // block builders
    public ConfiguredModel[] withRandomRotation(BlockModelBuilder model) {
        return ConfiguredModel.allYRotations(model, 0, false);
    }

    public void simpleBasket(Block block) {
        String name = blockName(block);
        String blockModel = blockLocation(name).toString();
        String parentModel = "cube_bottom_top";

        simpleBlock(block, models().withExistingParent(blockModel, parentModel)
                .texture("particle", blockLocation(name + "_top"))
                .texture("bottom", blockLocation("basket_bottom"))
                .texture("side", blockLocation(name + "_side"))
                .texture("top", blockLocation(name + "_top"))
        );
    }

    public void simpleBarrel(Block block) {
        String name = blockName(block);
        String blockModel = blockLocation(name).toString();
        String parentModel = "cube_bottom_top";

        simpleBlock(block, models().withExistingParent(blockModel, parentModel)
                .texture("particle", blockLocation(name + "_top"))
                .texture("bottom", mcBlockLocation("barrel_bottom"))
                .texture("side", mcBlockLocation("barrel_side"))
                .texture("top", blockLocation(name + "_top"))
        );
    }

    public BlockModelBuilder resourceBag(Block block, boolean alt) {
        String name = blockName(block);
        String resource = nameFromSplit(name, "_bag", true);

        if (alt) {
            return models().withExistingParent(name, parent("template/bag_all"))
                    .texture("top", blockLocation(name + "_top"));
        }
        return models().withExistingParent(name, parent("template/resource_bag"))
                .texture("pile", blockLocation(resource + "_pile"));
    }

    public void simpleBag(Block block) {
        String name = blockName(block);
        String blockModel = blockLocation(name).toString();
        String parentModel = "template/bag";

        simpleBlock(block, models().withExistingParent(blockModel, parent(parentModel))
                .texture("top", blockLocation(name + "_top")));
    }

    public BlockModelBuilder crateLid(Block block) {
        String name = blockName(block);
        String blockModel = blockLocation(name).toString();
        String parentModel = "template/crate_lid";

        return models().withExistingParent(blockModel, parent(parentModel))
                .texture("top", blockLocation(name + "_top"))
                .texture("side", blockLocation(name + "_side"))
                .texture("bottom", blockLocation(name + "_bottom"));
    }

    public BlockModelBuilder resourceCrate(Block block, boolean alt) {
        String name = blockName(block);
        String blockModel = blockLocation(name).toString();
        String resource = nameFromSplit(name, "_crate", true);

        if (alt) {
            return models().withExistingParent(blockModel, parent("template/crate"))
                    .texture("top", blockLocation(name + "_top"));
        }
        return models().withExistingParent(blockModel, parent("template/resource_crate"))
                .texture("pile", blockLocation(resource + "_pile"));
    }

    public BlockModelBuilder reinforcedCrate(Block block, boolean alt) {
        String name = blockName(block);
        String blockModel = blockLocation(name).toString();
        String resource = nameFromSplit(name, "_crate", true);
        resource = nameFromSplit(resource, "reinforced_", false);

        if (alt) {
            return models().withExistingParent(blockModel, parent("template/reinforced_crate"))
                    .texture("top", blockLocation(name + "_top"));
        }
        return models().withExistingParent(blockModel, parent("template/reinforced_resource_crate"))
                .texture("pile", blockLocation(resource + "_pile"));
    }

    public BlockModelBuilder resourcePile(Block block) {
        String name = blockName(block);
        String resource = nameFromSplit(name, "_pile", true);
        String blockModel = blockLocation(name).toString();
        String parentModel = "template/resource_pile";

        return models().withExistingParent(blockModel, parent(parentModel))
                .texture("top", blockLocation(resource + "_pallet_top"))
                .texture("front", blockLocation(name + "_front"))
                .texture("side", blockLocation(name + "_side"));
    }

    public BlockModelBuilder resourcePallet(Block block) {
        String name = blockName(block);
        String blockModel = blockLocation(name).toString();
        String parentModel = "template/resource_pallet";

        return models().withExistingParent(blockModel, parent(parentModel))
                .texture("top", blockLocation(name + "_top"))
                .texture("front", blockLocation(name + "_front"))
                .texture("side", blockLocation(name + "_side"));
    }

    public void simpleCrate(Block block) {
        String name = blockName(block);

        simpleBlock(block, models().cubeBottomTop(blockName(block),
                blockLocation(name + "_side"),
                fdBlockLocation("crate_bottom"),
                blockLocation(name + "_top")));
    }

    public void simpleMushroomCrate(Block block) {
        String name = blockName(block);
        String blockModel = blockLocation(name).toString();
        String parentModel = "template/mushroom_crate";

        simpleBlock(block, models().withExistingParent(blockModel, parent(parentModel))
                .texture("top", blockLocation(name + "_top"))
                .texture("side", blockLocation(name + "_side"))
                .texture("mush", blockLocation(name + "_mushrooms")));
    }

    // horizontal blocks
    public void horizontalBlock(Block block, Function<BlockState, ModelFile> modelFunc, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                .modelFile(modelFunc.apply(state))
                .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                .build(), ignored);
    }

    public void horizontalQuarterSlabBlock(QuarterSlabBlock block, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int layers = state.getValue(layersProperty);

            String suffix = "_layer" + (layers - 1);

            return ConfiguredModel.builder()
                    .modelFile(existingModel(blockName(block) + suffix))
                    .rotationY(defaultRotation(dir))
                    .build();
        }, ignored);
    }

    // quarter slab blocks
    public BlockModelBuilder quarterSlabBlock(QuarterSlabBlock block, int layer, boolean bottomTop) {
        String name = nameFromSplit(blockName(block), "_layer", true);
        String suffix = "_layer" + layer;
        String parentModel = "template/quarter_slab" + suffix;
        ResourceLocation topTexture;

        // special rules for turf blocks
        if (block instanceof TurfLayerBlock) {
            topTexture = mcBlockLocation(nameFromSplit(name, "_turf", true) + "_top");
        } else { topTexture = blockLocation(name + "_top"); }

        if (bottomTop) {
            return models().withExistingParent(blockLocation(name) + suffix, parent(parentModel))
                    .texture("top", topTexture)
                    .texture("side", blockLocation(name + "_side"))
                    .texture("bottom", blockLocation(name + "_bottom"));
        }
        return models().withExistingParent(blockLocation(name) + suffix, parent(parentModel))
                .texture("top", topTexture)
                .texture("side", blockLocation(name + "_side"))
                .texture("bottom", topTexture);
    }

    public void simpleQuarterSlabBlock(QuarterSlabBlock block, boolean randomRotation, boolean bottomTop, Property<?>... ignored) {
        String name = nameFromSplit(blockName(block), "_layer", true);

        getVariantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            int layers = state.getValue(layersProperty);
            ResourceLocation topTexture;
            ModelFile model;

            // special rules for turf blocks
            if(layers < 4) { model = quarterSlabBlock(block, layers - 1, bottomTop); }
            else if(bottomTop && layers == 4) {
                if (state.getBlock() instanceof TurfLayerBlock) {
                    topTexture = mcBlockLocation(nameFromSplit(name, "_turf", true) + "_top");
                } else { topTexture = blockLocation(name + "_top"); }
                model = models().cubeBottomTop(name, blockLocation(name + "_side"), blockLocation(name + "_bottom"), topTexture);
            } else {
                topTexture = blockLocation(name + "_top");
                model = models().cubeTop(name, blockLocation(name + "_side"), topTexture);
            }

            if (randomRotation) { return ConfiguredModel.allYRotations(model, 0, false); }
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        }, ignored);
    }

    // book blocks
    public BlockModelBuilder bookPile(QuarterSlabBlock block, int layer, boolean isColored, boolean isAlt, int variant) {
        String name = blockName(block);
        String suffix = "_layer" + layer;
        String prefix = "";

        if (!isColored) {
            if (isAlt) { prefix = "alt" + variant + "/"; }
            String parentModel = "book/template/" + "book_pile" + suffix;

            return models().withExistingParent(bookLocation(prefix + name) + suffix, parent(parentModel))
                    .texture("top", bookLocation(prefix + name + "_top" + suffix))
                    .texture("front", bookLocation(prefix + name + "_front"))
                    .texture("left", bookLocation(prefix + name + "_left"))
                    .texture("right", bookLocation(prefix + name + "_right"))
                    .texture("back", bookLocation(prefix + name + "_back"))
                    .texture("bottom", bookLocation(prefix + name + "_bottom"));
        } else {
            String parentModel = "book/template/colored_book_pile" + suffix;

            return models().withExistingParent(bookLocation(name) + suffix, parent(parentModel))
                    .texture("top", bookLocation(name + "_top"))
                    .texture("front", bookLocation(name + "_front"))
                    .texture("left", bookLocation(name + "_left"))
                    .texture("right", bookLocation(name + "_right"))
                    .texture("bottom", bookLocation(name + "_bottom"));
        }
    }

    public void simpleColoredBookPile(QuarterSlabBlock block, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int layers = state.getValue(layersProperty);

            return ConfiguredModel.builder()
                    .modelFile(bookPile(block, layers - 1, true, false, 0))
                    .rotationY(defaultRotation(dir))
                    .build();
        }, ignored);
    }

    public void simpleBookPile(QuarterSlabBlock block, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int layers = state.getValue(layersProperty);
            int rotation = defaultRotation(dir);

            return ConfiguredModel.builder()
                    .modelFile(bookPile(block, layers - 1, false, false, 0))
                    .rotationY(rotation)
                    .weight(30)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 1))
                    .rotationY(rotation)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 2))
                    .rotationY(rotation)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 3))
                    .rotationY(rotation)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 4))
                    .rotationY(rotation)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 5))
                    .rotationY(rotation)
                    .weight(5)
                    .build();
        }, ignored);
    }

    public BlockModelBuilder bookBundle(HorizontalBlock block, boolean isColored, boolean isAlt, int variant) {
        String temp = blockName(block);
        String name = nameFromSplit(temp, "_bundle", true) + "_pile";
        String prefix = "";

        if (!isColored) {
            if (isAlt) { prefix = "alt" + variant + "/"; }
            String parentModel = "book/template/book_bundle";

            return models().withExistingParent(bookLocation(prefix + blockName(block)).toString(), parent(parentModel))
                    .texture("top", bookLocation(prefix + name + "_top_layer3"))
                    .texture("front", bookLocation(prefix + name + "_front"))
                    .texture("left", bookLocation(prefix + name + "_left"))
                    .texture("right", bookLocation(prefix + name + "_right"))
                    .texture("back", bookLocation(prefix + name + "_back"))
                    .texture("bottom", bookLocation(prefix + name + "_bottom"));
        } else {
            String parentModel = "book/template/colored_book_bundle";

            return models().withExistingParent(bookLocation(prefix + blockName(block)).toString(), parent(parentModel))
                    .texture("top", bookLocation(name + "_top"))
                    .texture("front", bookLocation(name + "_front"))
                    .texture("left", bookLocation(name + "_left"))
                    .texture("right", bookLocation(name + "_right"))
                    .texture("bottom", bookLocation(name + "_bottom"));
        }
    }

    public void simpleColoredBookBundle(HorizontalBlock block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            return ConfiguredModel.builder()
                    .modelFile(bookBundle(block, true, false, 0))
                    .rotationY(defaultRotation(dir))
                    .build();
        });
    }

    public void simpleBookBundle(HorizontalBlock block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int rotation = defaultRotation(dir);

            return ConfiguredModel.builder().modelFile(bookBundle(block, false, false, 0))
                    .rotationY(rotation)
                    .weight(30)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 1))
                    .rotationY(rotation)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 2))
                    .rotationY(rotation)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 3))
                    .rotationY(rotation)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 4))
                    .rotationY(rotation)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 5))
                    .rotationY(rotation)
                    .weight(5)
                    .build();
        });
    }

    public BlockModelBuilder bookBundleSlab(HorizontalSlabBlock block, SlabType type, boolean isColored, boolean isAlt, int variant) {
        String temp = blockName(block);
        String name = nameFromSplit(temp, "_bundle", true) + "_pile";
        String slabType = "";
        String layer = "_layer1";
        String prefix = "";
        String suffix = "";

        if (type == SlabType.TOP) {
            slabType = "_top";
            layer = "_layer3";
            suffix = "_slab_top";
        }
        if (type == SlabType.DOUBLE) {
            slabType = "_double";
            layer = "_layer3";
        }

        if (!isColored) {
            if (isAlt) {
                prefix = "alt" + variant + "/";
            }
            String parentModel = "book/template/book_bundle_slab" + slabType;

            return models().withExistingParent(bookLocation(prefix + blockName(block)) + slabType, parent(parentModel))
                    .texture("top", bookLocation(prefix + name + "_top" + layer))
                    .texture("front", bookLocation(prefix + name + "_front"))
                    .texture("left", bookLocation(prefix + name + "_left"))
                    .texture("right", bookLocation(prefix + name + "_right"))
                    .texture("back", bookLocation(prefix + name + "_back"))
                    .texture("bottom", bookLocation(prefix + name + "_bottom" + suffix));
        } else {
            String parentModel = "book/template/colored_book_bundle_slab" + slabType;

            return models().withExistingParent(bookLocation(blockName(block)) + slabType, parent(parentModel))
                    .texture("top", bookLocation(name + "_top"))
                    .texture("front", bookLocation(name + "_front"))
                    .texture("left", bookLocation(name + "_left"))
                    .texture("right", bookLocation(name + "_right"))
                    .texture("bottom", bookLocation(name + "_bottom"));
        }
    }

    public void simpleColoredBookBundleSlab(HorizontalSlabBlock block, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);

            return ConfiguredModel.builder()
                    .modelFile(bookBundleSlab(block, type, true, false, 0))
                    .rotationY(defaultRotation(dir))
                    .build();
        }, ignored);
    }

    public void simpleBookBundleSlab(HorizontalSlabBlock block, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);
            int rotation = defaultRotation(dir);

            return ConfiguredModel.builder()
                    .modelFile(bookBundleSlab(block, type, false, false, 0))
                    .rotationY(rotation)
                    .weight(30)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 1))
                    .rotationY(rotation)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 2))
                    .rotationY(rotation)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 3))
                    .rotationY(rotation)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 4))
                    .rotationY(rotation)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 5))
                    .rotationY(rotation)
                    .weight(5)
                    .build();
        }, ignored);
    }

    // turf blocks
    public BlockModelBuilder turfBlock(QuarterSlabBlock block, int layer, boolean tint) {
        String name = nameFromSplit(blockName(block), "_layer", true);
        String topName = nameFromSplit(name, "_turf", true);
        ResourceLocation topTexture;
        String suffix = "_layer" + layer;
        String parentModel;
        if (tint) { parentModel = "template/tinted_overlay_block"; }
        else { parentModel = "template/overlay_block"; }
        String blockModel = blockLocation(name).toString();

        if (layer < 3) {
            if (tint) { parentModel = "template/tinted_turf" + suffix; }
            else { parentModel = "template/turf" + suffix; }
            blockModel = blockModel + suffix;
        }

        // special rules for grass turf
        if (block.defaultBlockState().is(PUBlocks.GRASS_TURF_LAYER.get())) {
            topTexture = mcBlockLocation(topName + "_block_top");
        }
        else { topTexture = mcBlockLocation(topName + "_top"); }

        return models().withExistingParent(blockModel, parent(parentModel))
                .texture("top", topTexture)
                .texture("side", blockLocation(name + "_side"))
                .texture("overlay", blockLocation(name + "_overlay"));
    }

    public void simpleTurfBlock(QuarterSlabBlock block, boolean tint, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            int layers = state.getValue(layersProperty);

            return ConfiguredModel.allYRotations(turfBlock(block, layers - 1, tint), 0, false);
        }, ignored);
    }

    // overlay blocks
    public BlockModelBuilder overlayBlock(Block block, boolean tint) {
        String name = blockName(block);
        String parentModel;
        if (tint) { parentModel = "template/tinted_overlay_block"; }
        else { parentModel = "template/overlay_block"; }
        String blockModel = blockLocation(name).toString();

        return models().withExistingParent(blockModel, parent(parentModel))
                .texture("top", blockLocation(name + "_top"))
                .texture("side", blockLocation(name + "_side"))
                .texture("overlay", blockLocation(name + "_overlay"));
    }

    public void grassBaleBlock(RotatedPillarBlock block) {
        String name = blockName(block);

        getVariantBuilder(block).forAllStates(state -> {
            Direction.Axis dir = state.getValue(BlockStateProperties.AXIS);

            if (dir == Direction.Axis.X) { return ConfiguredModel.builder().modelFile(existingModel(name + "_horizontal")).rotationY(90).build(); }
            else if (dir == Direction.Axis.Y) { return ConfiguredModel.builder().modelFile(existingModel(name)).build(); }
            else return ConfiguredModel.builder().modelFile(existingModel(name + "_horizontal")).build();
        });

    }
}