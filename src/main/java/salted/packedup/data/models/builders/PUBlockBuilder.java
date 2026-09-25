package salted.packedup.data.models.builders;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import salted.packedup.PackedUp;
import salted.packedup.common.block.HorizontalQuarterSlabBlock;

import java.util.function.Function;

import static salted.packedup.data.utils.NameUtils.blockLocation;
import static salted.packedup.data.utils.NameUtils.blockName;

public class PUBlockBuilder extends BlockStateProvider {
    public static final int DEFAULT_HORIZONTAL_OFFSET = 180;

    public PUBlockBuilder(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PackedUp.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // This is never used and shouldn't be used. It is only needed
        // so that I can separate the builders from the registration.
    }

    // ============================================================
    // Shared Utilities
    // ============================================================
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
        return ((int) dir.toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360;
    }

    public int defaultAxis(Direction.Axis axis) {
        return switch (axis) {
            case X -> 270 % 360;
            case Z, Y -> 0;
        };
    }

    public ConfiguredModel[] withRandomRotation(BlockModelBuilder model) {
        return ConfiguredModel.allYRotations(model, 0, false);
    }

    /** Exposes the protected getVariantBuilder for sub-builders. */
    public VariantBlockStateBuilder variantBuilder(Block block) {
        return getVariantBuilder(block);
    }

    // ============================================================
    // Shared Horizontal / Quarter-Slab Blockstate Logic
    // ============================================================
    public void horizontalBlock(Block block, Function<BlockState, ModelFile> modelFunc, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> ConfiguredModel.builder()
                .modelFile(modelFunc.apply(state))
                .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + DEFAULT_HORIZONTAL_OFFSET) % 360)
                .build(), ignored);
    }

    /**
     * @param folder The model folder under {@code block/}, including the trailing slash (e.g. {@code "pallet/"}).
     */
    public void horizontalQuarterSlabBlock(HorizontalQuarterSlabBlock block, String folder, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            IntegerProperty layersProperty = block.getQuarterLayers();
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int layers = state.getValue(layersProperty);

            String suffix = "_layer" + (layers - 1);

            return ConfiguredModel.builder()
                    .modelFile(existingModel(folder + blockName(block) + suffix))
                    .rotationY(defaultRotation(dir))
                    .build();
        }, ignored);
    }
}
