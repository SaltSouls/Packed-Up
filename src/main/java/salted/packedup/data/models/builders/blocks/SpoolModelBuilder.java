package salted.packedup.data.models.builders.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import salted.packedup.common.block.ConnectedPillarBlock;
import salted.packedup.common.block.IndustrialSpoolBlock;
import salted.packedup.common.block.state.properties.PillarShape;
import salted.packedup.data.models.PUBlockStates;
import salted.packedup.data.models.builders.PUBlockBuilder;

import static salted.packedup.data.utils.NameUtils.*;

public class SpoolModelBuilder {
    private final PUBlockBuilder provider;

    public SpoolModelBuilder(PUBlockStates provider) {
        this.provider = provider;
    }

    private BlockModelBuilder pillarBlock(ConnectedPillarBlock block, PillarShape shape, Direction.Axis axis) {
        String name = blockName(block);
        String parentName = nameFromSplit(name, "industrial", false);
        String suffix, texSuffix;
        boolean isHorizontal = axis == Direction.Axis.X || axis == Direction.Axis.Z;
        boolean isVertical = axis == Direction.Axis.Y;

        if (isHorizontal) { suffix = "_horizontal"; }
        else { suffix = "_vertical"; }

        if (shape == PillarShape.TOP && isVertical) {
            suffix += "_upper";
            texSuffix = "_upper";
        } else if (shape == PillarShape.BOTTOM && isVertical) {
            suffix += "_lower";
            texSuffix = "_lower";
        } else if (shape == PillarShape.LEFT && isHorizontal) {
            suffix += "_left";
            texSuffix = "_upper";
        } else if (shape == PillarShape.RIGHT && isHorizontal) {
            suffix += "_right";
            texSuffix = "_lower";
        } else if (shape == PillarShape.MIDDLE) {
            suffix += "_middle";
            texSuffix = "_middle";
        } else {
            texSuffix = "";
        }

        String parentModel = "spool/template/industrial" + parentName + suffix;

        return provider.models().withExistingParent(spoolLocation(name) + suffix, provider.parent(parentModel))
                .texture("string", spoolLocation(name) + texSuffix);
    }

    public void simpleIndustrialSpool(IndustrialSpoolBlock block, Property<?>... ignored) {
        provider.variantBuilder(block).forAllStatesExcept(state -> {
            EnumProperty<PillarShape> pillarShape = block.getShape();
            Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
            PillarShape shape = state.getValue(pillarShape);

            return ConfiguredModel.builder()
                    .modelFile(pillarBlock(block, shape, axis))
                    .rotationY(provider.defaultAxis(axis))
                    .build();
        }, ignored);
    }
}
