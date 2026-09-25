package salted.packedup.data.models.builders.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import salted.packedup.common.block.CrateLidBlock;
import salted.packedup.data.models.PUBlockStates;
import salted.packedup.data.models.builders.PUBlockBuilder;

import static salted.packedup.data.utils.NameUtils.*;

public class CrateModelBuilder {
    private final PUBlockBuilder provider;

    public CrateModelBuilder(PUBlockStates provider) {
        this.provider = provider;
    }

    public BlockModelBuilder resourceCrate(Block block, boolean alt) {
        String name = blockName(block);
        String blockModel = crateLocation(name).toString();
        String resource = nameFromSplit(name, "_crate", true);

        if (alt) {
            return provider.models().withExistingParent(blockModel, provider.parent("crate/template/crate"))
                    .texture("top", crateLocation(name + "_top"));
        }
        return provider.models().withExistingParent(blockModel, provider.parent("crate/template/resource_crate"))
                .texture("pile", crateLocation("pile/" + resource + "_pile"));
    }

    public BlockModelBuilder reinforcedCrate(Block block, boolean alt) {
        String name = blockName(block);
        String blockModel = crateLocation(name).toString();
        String resource = nameFromSplit(name, "_crate", true);
        resource = nameFromSplit(resource, "reinforced_", false);

        if (alt) {
            return provider.models().withExistingParent(blockModel, provider.parent("crate/template/reinforced_crate"))
                    .texture("top", crateLocation(name + "_top"));
        }
        return provider.models().withExistingParent(blockModel, provider.parent("crate/template/reinforced_resource_crate"))
                .texture("pile", crateLocation("pile/" + resource + "_pile"));
    }

    public void simpleCrate(Block block) {
        String name = blockName(block);

        provider.simpleBlock(block, provider.models().cubeBottomTop(crateLocation(name).toString(),
                crateLocation(name + "_side"),
                crateLocation("crate_bottom"),
                crateLocation(name + "_top")));
    }

    public void mushroomCrate(Block block) {
        String name = blockName(block);
        String blockModel = crateLocation(name).toString();

        provider.simpleBlock(block, provider.models().withExistingParent(blockModel, provider.parent("crate/template/mushroom_crate"))
                .texture("top", crateLocation(name + "_top"))
                .texture("side", crateLocation(name + "_side"))
                .texture("mush", crateLocation(name + "_mushrooms")));
    }

    public BlockModelBuilder crateLid(CrateLidBlock block, Half half) {
        String name = nameFromSplit(blockName(block), "_lid", true);
        String type = "_lid";
        String blockModel = crateLocation(name + type).toString();
        String parentModel;

        if (half.equals(Half.TOP)) {
            parentModel = "crate/template/crate_lid_top";
            blockModel = blockModel + "_top";
        } else {
            parentModel = "crate/template/crate_lid";
        }

        return provider.models().withExistingParent(blockModel, provider.parent(parentModel))
                .texture("top", crateLocation(name + type + "_top"))
                .texture("side", crateLocation(name + type + "_side"))
                .texture("bottom", crateLocation(name + "_bottom"));
    }

    public void simpleCrateLid(CrateLidBlock block, Property<?>... ignored) {
        provider.variantBuilder(block).forAllStatesExcept(state -> {
            Half half = state.getValue(BlockStateProperties.HALF);

            return ConfiguredModel.builder()
                    .modelFile(crateLid(block, half))
                    .rotationY(provider.defaultRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)))
                    .build();
        }, ignored);
    }
}
