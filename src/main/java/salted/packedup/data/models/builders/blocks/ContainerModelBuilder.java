package salted.packedup.data.models.builders.blocks;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import salted.packedup.common.block.DrumBarrelBlock;
import salted.packedup.common.block.FluidGaugeBlock;
import salted.packedup.data.models.PUBlockStates;
import salted.packedup.data.models.builders.PUBlockBuilder;

import static salted.packedup.data.utils.NameUtils.*;

public class ContainerModelBuilder {
    private final PUBlockBuilder provider;

    public ContainerModelBuilder(PUBlockStates provider) {
        this.provider = provider;
    }

    public void simpleBasket(Block block) {
        String name = blockName(block);
        String blockModel = basketLocation(name).toString();

        provider.simpleBlock(block, provider.models().withExistingParent(blockModel, "cube_bottom_top")
                .texture("particle", basketLocation(name + "_top"))
                .texture("bottom", basketLocation("basket_bottom"))
                .texture("side", basketLocation(name + "_side"))
                .texture("top", basketLocation(name + "_top")));
    }

    public void simpleBarrel(Block block) {
        String name = blockName(block);
        String blockModel = barrelLocation(name).toString();

        provider.simpleBlock(block, provider.models().withExistingParent(blockModel, "cube_bottom_top")
                .texture("particle", barrelLocation(name + "_top"))
                .texture("bottom", mcBlockLocation("barrel_bottom"))
                .texture("side", mcBlockLocation("barrel_side"))
                .texture("top", barrelLocation(name + "_top")));
    }

    public void simpleDrumBarrel(Block block) {
        String name = blockName(block);
        String texture = nameFromSplit(name, "_barrel", true);

        ResourceLocation side = drumLocation(texture + "_side");
        ResourceLocation top = drumLocation(texture + "_top");
        ResourceLocation bottom = drumLocation(texture + "_bottom");

        BlockModelBuilder upright = provider.models()
                .withExistingParent(drumLocation(name).toString(), "cube_bottom_top")
                .texture("side", side)
                .texture("top", top)
                .texture("bottom", bottom);

        BlockModelBuilder inverted = provider.models()
                .withExistingParent(drumLocation(name + "_down").toString(), "cube_bottom_top")
                .texture("side", side)
                .texture("top", bottom)
                .texture("bottom", top);

        BlockModelBuilder horizontal = provider.models()
                .withExistingParent(drumLocation(name + "_horizontal").toString(), drumLocation("template/drum_barrel_horizontal"))
                .texture("front", top)
                .texture("side", side)
                .texture("back", bottom);

        provider.getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(DrumBarrelBlock.FACING);

            if (facing == Direction.UP) { return ConfiguredModel.builder().modelFile(upright).build(); }
            if (facing == Direction.DOWN) { return ConfiguredModel.builder().modelFile(inverted).build(); }

            return ConfiguredModel.builder()
                    .modelFile(horizontal)
                    .rotationY(((int) facing.toYRot() + 180) % 360)
                    .build();
        });
    }

    public void fluidGauge(Block block) {
        provider.getVariantBuilder(block).forAllStatesExcept(state -> {
            int fill = state.getValue(FluidGaugeBlock.FILL);
            Direction facing = state.getValue(FluidGaugeBlock.FACING);
            String face = switch (state.getValue(FluidGaugeBlock.FACE)) {
                case FLOOR -> "top";
                case CEILING -> "bottom";
                case WALL -> "side";
            };

            String name = "fluid_gauge_" + face;
            String dial = String.format("fluid_gauge_%02d", fill);

            BlockModelBuilder model = provider.models()
                    .withExistingParent(gaugeLocation(name + "_" + String.format("%02d", fill)).toString(),
                            provider.parent("gauge/" + name))
                    .texture("display", gaugeLocation(dial));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) facing.toYRot() + 180) % 360)
                    .build();
        }, BlockStateProperties.WATERLOGGED);
    }

    public BlockModelBuilder resourceBag(Block block, boolean alt) {
        String name = blockName(block);
        String resource = nameFromSplit(name, "_bag", true);
        String blockModel = bagLocation(name).toString();

        if (alt) {
            return provider.models().withExistingParent(blockModel, provider.parent("bag/template/bag"))
                    .texture("top", bagLocation(name + "_top"));
        }
        return provider.models().withExistingParent(blockModel, provider.parent("bag/template/resource_bag"))
                .texture("pile", bagLocation("pile/" + resource + "_pile"));
    }

    public void simpleBag(Block block) {
        String name = blockName(block);
        String blockModel = bagLocation(name).toString();

        provider.simpleBlock(block, provider.models().withExistingParent(blockModel, provider.parent("bag/template/bag"))
                .texture("top", bagLocation(name + "_top")));
    }
}
