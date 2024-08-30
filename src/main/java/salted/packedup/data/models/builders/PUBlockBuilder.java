package salted.packedup.data.models.builders;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import salted.packedup.PackedUp;
import salted.packedup.common.block.HorizontalBlock;
import salted.packedup.common.block.HorizontalSlabBlock;
import salted.packedup.common.block.QuarterSlabBlock;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.Arrays;
import java.util.function.Function;

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
    protected String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    /**
     * Splits the string and returns everything before or after the split.
     *
     * @param name      the name of the {@link Block} or {@link Item} we are using
     * @param substring the string at which the split occurs
     * @param before    the position at which the split occurs
     * @return The desired half of the string array.
     */
    private String nameFromSplit(String name, String substring, boolean before) {
        if (before) {
            return Arrays.stream(name.split(substring)).findFirst().get();
        } else return Arrays.stream(name.split(substring)).toList().get(1);
    }

    public ResourceLocation blockLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "block/" + path);
    }

    public ResourceLocation bookLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "block/book/" + path);
    }

    public ResourceLocation fdBlockLocation(String path) {
        return new ResourceLocation(FarmersDelight.MODID, "block/" + path);
    }

    public ResourceLocation mcBlockLocation(String path) {
        return new ResourceLocation("block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(blockLocation(blockName(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(blockLocation(path), models().existingFileHelper);
    }

    public String parent(String model) {
        return existingModel(model).getLocation().toString();
    }

    // block builders
    public ConfiguredModel[] withRandomRotation(BlockModelBuilder model) {
        return ConfiguredModel.allYRotations(model, 0, false);
    }

    public void simpleBasket(Block block) {
        String name = blockName(block);

        this.simpleBlock(block, models().withExistingParent(name, "cube_bottom_top")
                .texture("particle", blockLocation(name + "_top"))
                .texture("bottom", blockLocation("basket_bottom"))
                .texture("side", blockLocation(name + "_side"))
                .texture("top", blockLocation(name + "_top"))
        );
    }

    public void simpleBarrel(Block block) {
        String name = blockName(block);

        this.simpleBlock(block, models().withExistingParent(name, "cube_bottom_top")
                .texture("particle", blockLocation(name + "_top"))
                .texture("bottom", mcBlockLocation("barrel_bottom"))
                .texture("side", mcBlockLocation("barrel_side"))
                .texture("top", blockLocation(name + "_top"))
        );
    }

    public BlockModelBuilder resourceBag(Block block, boolean alt) {
        log.debug("resourceBag alt: {}", alt);
        String name = blockName(block);
        log.debug("name: {}", name);
        String resource = nameFromSplit(name, "_bag", true);
        log.debug("resource: {}", resource);

        if (alt) {
            return models().withExistingParent(name, parent("template_bag"))
                    .texture("top", blockLocation(name + "_top"));
        }
        return models().withExistingParent(name, parent("template_resource_bag"))
                .texture("pile", blockLocation(resource + "_pile"));
    }

    public void simpleBag(Block block) {
        String name = blockName(block);

        this.simpleBlock(block, this.models().withExistingParent(name, parent("template_bag"))
                .texture("top", blockLocation(name + "_top")));
    }

    public BlockModelBuilder crateLid(Block block) {
        String name = blockName(block);
        return models().withExistingParent(name, parent("template_crate_lid"))
                .texture("top", blockLocation(name + "_top"))
                .texture("side", blockLocation(name + "_side"))
                .texture("bottom", blockLocation(name + "_bottom"));
    }

    public BlockModelBuilder resourceCrate(Block block, boolean alt) {
        log.debug("resourceCrate alt: {}", alt);
        String name = blockName(block);
        log.debug("name: {}", name);
        String resource = nameFromSplit(name, "_crate", true);
        log.debug("resource: {}", resource);

        if (alt) {
            return models().withExistingParent(name, parent("template_crate"))
                    .texture("top", blockLocation(name + "_top"));
        }
        return models().withExistingParent(name, parent("template_resource_crate"))
                .texture("pile", blockLocation(resource + "_pile"));
    }

    public BlockModelBuilder reinforcedCrate(Block block, boolean alt) {
        log.debug("reinforcedCrate alt: {}", alt);
        String name = blockName(block);
        log.debug("name: {}", name);
        String temp = nameFromSplit(name, "_crate", true);
        String resource = nameFromSplit(temp, "reinforced_", false);
        log.debug("resource: {}", resource);

        if (alt) {
            return models().withExistingParent(name, parent("template_reinforced_crate"))
                    .texture("top", blockLocation(name + "_top"));
        }
        return models().withExistingParent(name, parent("template_reinforced_resource_crate"))
                .texture("pile", blockLocation(resource + "_pile"));
    }

    public BlockModelBuilder resourcePile(Block block) {
        String name = blockName(block);
        String resource = nameFromSplit(name, "_pile", true);

        return models().withExistingParent(name, parent("template_resource_pile"))
                .texture("top", blockLocation(resource + "_pallet_top"))
                .texture("front", blockLocation(name + "_front"))
                .texture("side", blockLocation(name + "_side"));
    }

    public BlockModelBuilder resourcePallet(Block block) {
        String name = blockName(block);

        return models().withExistingParent(name, parent("template_resource_pallet"))
                .texture("top", blockLocation(name + "_top"))
                .texture("front", blockLocation(name + "_front"))
                .texture("side", blockLocation(name + "_side"));
    }

    public void simpleCrate(Block block) {
        String name = blockName(block);

        this.simpleBlock(block, models().cubeBottomTop(blockName(block),
                blockLocation(name + "_side"),
                fdBlockLocation("crate_bottom"),
                blockLocation(name + "_top")));
    }

    public void horizontalBlock(Block block, Function<BlockState, ModelFile> modelFunc, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                .modelFile(modelFunc.apply(state))
                .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                .build(), ignored);
    }

    public void quarterSlabBlock(QuarterSlabBlock block, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int layers = state.getValue(layersProperty);

            String suffix = "_layer" + (layers - 1);

            return ConfiguredModel.builder()
                    .modelFile(existingModel(blockName(block) + suffix))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .build();
        }, ignored);
    }

    public BlockModelBuilder bookPile(QuarterSlabBlock block, int layer, boolean isColored, boolean isAlt, int variant) {
        String name = blockName(block);
        String suffix = "_layer" + layer;
        String prefix = "";

        if (!isColored) {
            if (isAlt) {
                prefix = "alt" + variant + "/";
            }
            String parentModel = "book/" + "template_book_pile" + suffix;

            return models().withExistingParent(bookLocation(prefix + name) + suffix, parent(parentModel))
                    .texture("top", bookLocation(prefix + name + "_top" + suffix))
                    .texture("front", bookLocation(prefix + name + "_front"))
                    .texture("left", bookLocation(prefix + name + "_left"))
                    .texture("right", bookLocation(prefix + name + "_right"))
                    .texture("back", bookLocation(prefix + name + "_back"))
                    .texture("bottom", bookLocation(prefix + name + "_bottom"));
        } else {
            String parentModel = "book/template_colored_book_pile" + suffix;

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
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .build();
        }, ignored);
    }

    public void simpleBookPile(QuarterSlabBlock block, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int layers = state.getValue(layersProperty);

            return ConfiguredModel.builder()
                    .modelFile(bookPile(block, layers - 1, false, false, 0))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(30)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 1))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 2))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 3))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 4))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookPile(block, layers - 1, false, true, 5))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(5)
                    .build();
        }, ignored);
    }

    public BlockModelBuilder bookBundle(HorizontalBlock block, boolean isColored, boolean isAlt, int variant) {
        String temp = blockName(block);
        String name = nameFromSplit(temp, "_bundle", true) + "_pile";
        String prefix = "";

        if (!isColored) {
            if (isAlt) {
                prefix = "alt" + variant + "/";
            }
            String parentModel = "book/" + "template_book_bundle";

            return models().withExistingParent(bookLocation(prefix + blockName(block)).toString(), parent(parentModel))
                    .texture("top", bookLocation(prefix + name + "_top_layer3"))
                    .texture("front", bookLocation(prefix + name + "_front"))
                    .texture("left", bookLocation(prefix + name + "_left"))
                    .texture("right", bookLocation(prefix + name + "_right"))
                    .texture("back", bookLocation(prefix + name + "_back"))
                    .texture("bottom", bookLocation(prefix + name + "_bottom"));
        } else {
            String parentModel = "book/template_colored_book_bundle";

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
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .build();
        });
    }

    public void simpleBookBundle(HorizontalBlock block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            return ConfiguredModel.builder().modelFile(bookBundle(block, false, false, 0))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(30)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 1))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 2))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 3))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 4))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 5))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
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
            String parentModel = "book/" + "template_book_bundle_slab" + slabType;

            return models().withExistingParent(bookLocation(prefix + blockName(block)) + slabType, parent(parentModel))
                    .texture("top", bookLocation(prefix + name + "_top" + layer))
                    .texture("front", bookLocation(prefix + name + "_front"))
                    .texture("left", bookLocation(prefix + name + "_left"))
                    .texture("right", bookLocation(prefix + name + "_right"))
                    .texture("back", bookLocation(prefix + name + "_back"))
                    .texture("bottom", bookLocation(prefix + name + "_bottom" + suffix));
        } else {
            String parentModel = "book/" + "template_colored_book_bundle_slab" + slabType;

            return models().withExistingParent(bookLocation(blockName(block)) + slabType, parent(parentModel))
                    .texture("top", bookLocation(name + "_top"))
                    .texture("front", bookLocation(name + "_front"))
                    .texture("left", bookLocation(name + "_left"))
                    .texture("right", bookLocation(name + "_right"))
                    .texture("bottom", bookLocation(name + "_bottom"));
        }
    }

    public void simpleColoredBookBundle(HorizontalSlabBlock block, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);

            return ConfiguredModel.builder()
                    .modelFile(bookBundleSlab(block, type, true, false, 0))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .build();
        }, ignored);
    }

    public void simpleBookBundleSlab(HorizontalSlabBlock block, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);

            return ConfiguredModel.builder()
                    .modelFile(bookBundleSlab(block, type, false, false, 0))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(30)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 1))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 2))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(25)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 3))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 4))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(5)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 5))
                    .rotationY(((int) (dir).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                    .weight(5)
                    .build();
        }, ignored);
    }
}