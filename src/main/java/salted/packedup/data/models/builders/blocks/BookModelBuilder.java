package salted.packedup.data.models.builders.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import salted.packedup.common.block.HorizontalBlock;
import salted.packedup.common.block.HorizontalSlabBlock;
import salted.packedup.common.block.QuarterSlabBlock;
import salted.packedup.data.models.builders.PUBlockBuilder;

import static salted.packedup.data.utils.NameUtils.*;

public class BookModelBuilder {
    private final PUBlockBuilder provider;

    public BookModelBuilder(PUBlockBuilder provider) {
        this.provider = provider;
    }

    // Book Piles
    private BlockModelBuilder bookPile(QuarterSlabBlock block, int layer, boolean isColored, boolean isAlt, int variant) {
        String name = blockName(block);
        String suffix = "_layer" + layer;
        String prefix = "";

        if (!isColored) {
            if (isAlt) { prefix = "alt" + variant + "/"; }
            String parentModel = "book/template/book_pile" + suffix;

            return provider.models().withExistingParent(bookLocation(prefix + name) + suffix, provider.parent(parentModel))
                    .texture("top", bookLocation(prefix + name + "_top" + suffix))
                    .texture("front", bookLocation(prefix + name + "_front"))
                    .texture("left", bookLocation(prefix + name + "_left"))
                    .texture("right", bookLocation(prefix + name + "_right"))
                    .texture("back", bookLocation(prefix + name + "_back"))
                    .texture("bottom", bookLocation(prefix + name + "_bottom"));
        } else {
            String parentModel = "book/template/colored_book_pile" + suffix;

            return provider.models().withExistingParent(bookLocation(name) + suffix, provider.parent(parentModel))
                    .texture("top", bookLocation(name + "_top"))
                    .texture("front", bookLocation(name + "_front"))
                    .texture("left", bookLocation(name + "_left"))
                    .texture("right", bookLocation(name + "_right"))
                    .texture("bottom", bookLocation(name + "_bottom"));
        }
    }

    public void simpleColoredBookPile(QuarterSlabBlock block, Property<?>... ignored) {
        provider.variantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int layers = state.getValue(layersProperty) - 1;

            return ConfiguredModel.builder()
                    .modelFile(bookPile(block, layers, true, false, 0))
                    .rotationY(provider.defaultRotation(dir))
                    .build();
        }, ignored);
    }

    public void simpleBookPile(QuarterSlabBlock block, Property<?>... ignored) {
        provider.variantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int layers = state.getValue(layersProperty) - 1;
            int rotation = provider.defaultRotation(dir);

            return ConfiguredModel.builder()
                    .modelFile(bookPile(block, layers, false, false, 0)).rotationY(rotation).weight(35)
                    .nextModel()
                    .modelFile(bookPile(block, layers, false, true, 1)).rotationY(rotation).weight(25)
                    .nextModel()
                    .modelFile(bookPile(block, layers, false, true, 2)).rotationY(rotation).weight(25)
                    .nextModel()
                    .modelFile(bookPile(block, layers, false, true, 3)).rotationY(rotation).weight(5)
                    .nextModel()
                    .modelFile(bookPile(block, layers, false, true, 4)).rotationY(rotation).weight(5)
                    .nextModel()
                    .modelFile(bookPile(block, layers, false, true, 5)).rotationY(rotation).weight(5)
                    .build();
        }, ignored);
    }

    // Book Bundles
    private BlockModelBuilder bookBundle(HorizontalBlock block, boolean isColored, boolean isAlt, int variant) {
        String temp = blockName(block);
        String name = nameFromSplit(temp, "_bundle", true) + "_pile";
        String prefix = "";

        if (!isColored) {
            if (isAlt) { prefix = "alt" + variant + "/"; }
            String parentModel = "book/template/book_bundle";

            return provider.models().withExistingParent(bookLocation(prefix + blockName(block)).toString(), provider.parent(parentModel))
                    .texture("top", bookLocation(prefix + name + "_top_layer3"))
                    .texture("front", bookLocation(prefix + name + "_front"))
                    .texture("left", bookLocation(prefix + name + "_left"))
                    .texture("right", bookLocation(prefix + name + "_right"))
                    .texture("back", bookLocation(prefix + name + "_back"))
                    .texture("bottom", bookLocation(prefix + name + "_bottom"));
        } else {
            String parentModel = "book/template/colored_book_bundle";

            return provider.models().withExistingParent(bookLocation(prefix + blockName(block)).toString(), provider.parent(parentModel))
                    .texture("top", bookLocation(name + "_top"))
                    .texture("front", bookLocation(name + "_front"))
                    .texture("left", bookLocation(name + "_left"))
                    .texture("right", bookLocation(name + "_right"))
                    .texture("bottom", bookLocation(name + "_bottom"));
        }
    }

    public void simpleColoredBookBundle(HorizontalBlock block) {
        provider.variantBuilder(block).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            return ConfiguredModel.builder()
                    .modelFile(bookBundle(block, true, false, 0))
                    .rotationY(provider.defaultRotation(dir))
                    .build();
        });
    }

    public void simpleBookBundle(HorizontalBlock block) {
        provider.variantBuilder(block).forAllStates(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int rotation = provider.defaultRotation(dir);

            return ConfiguredModel.builder()
                    .modelFile(bookBundle(block, false, false, 0)).rotationY(rotation).weight(35)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 1)).rotationY(rotation).weight(25)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 2)).rotationY(rotation).weight(25)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 3)).rotationY(rotation).weight(5)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 4)).rotationY(rotation).weight(5)
                    .nextModel()
                    .modelFile(bookBundle(block, false, true, 5)).rotationY(rotation).weight(5)
                    .build();
        });
    }

    // Book Bundle Slabs
    private BlockModelBuilder bookBundleSlab(HorizontalSlabBlock block, SlabType type, boolean isColored, boolean isAlt, int variant) {
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
            if (isAlt) { prefix = "alt" + variant + "/"; }
            String parentModel = "book/template/book_bundle_slab" + slabType;

            return provider.models().withExistingParent(bookLocation(prefix + blockName(block)) + slabType, provider.parent(parentModel))
                    .texture("top", bookLocation(prefix + name + "_top" + layer))
                    .texture("front", bookLocation(prefix + name + "_front"))
                    .texture("left", bookLocation(prefix + name + "_left"))
                    .texture("right", bookLocation(prefix + name + "_right"))
                    .texture("back", bookLocation(prefix + name + "_back"))
                    .texture("bottom", bookLocation(prefix + name + "_bottom" + suffix));
        } else {
            String parentModel = "book/template/colored_book_bundle_slab" + slabType;

            return provider.models().withExistingParent(bookLocation(blockName(block)) + slabType, provider.parent(parentModel))
                    .texture("top", bookLocation(name + "_top"))
                    .texture("front", bookLocation(name + "_front"))
                    .texture("left", bookLocation(name + "_left"))
                    .texture("right", bookLocation(name + "_right"))
                    .texture("bottom", bookLocation(name + "_bottom"));
        }
    }

    public void simpleColoredBookBundleSlab(HorizontalSlabBlock block, Property<?>... ignored) {
        provider.variantBuilder(block).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);

            return ConfiguredModel.builder()
                    .modelFile(bookBundleSlab(block, type, true, false, 0))
                    .rotationY(provider.defaultRotation(dir))
                    .build();
        }, ignored);
    }

    public void simpleBookBundleSlab(HorizontalSlabBlock block, Property<?>... ignored) {
        provider.variantBuilder(block).forAllStatesExcept(state -> {
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);
            int rotation = provider.defaultRotation(dir);

            return ConfiguredModel.builder()
                    .modelFile(bookBundleSlab(block, type, false, false, 0)).rotationY(rotation).weight(35)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 1)).rotationY(rotation).weight(25)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 2)).rotationY(rotation).weight(25)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 3)).rotationY(rotation).weight(5)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 4)).rotationY(rotation).weight(5)
                    .nextModel()
                    .modelFile(bookBundleSlab(block, type, false, true, 5)).rotationY(rotation).weight(5)
                    .build();
        }, ignored);
    }
}
