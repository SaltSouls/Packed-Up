package salted.packedup.data.models.builders.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import salted.packedup.data.models.PUBlockStates;
import salted.packedup.data.models.builders.PUBlockBuilder;

import static salted.packedup.data.utils.NameUtils.*;

public class PileModelBuilder {
    private final PUBlockBuilder provider;

    public PileModelBuilder(PUBlockStates provider) {
        this.provider = provider;
    }

    public BlockModelBuilder resourcePile(Block block) {
        String name = blockName(block);
        String resource = nameFromSplit(name, "_pile", true);
        String blockModel = pileLocation(name).toString();

        return provider.models().withExistingParent(blockModel, provider.parent("pile/template/pile"))
                .texture("top", palletLocation(resource + "_pallet_top"))
                .texture("front", pileLocation(name + "_front"))
                .texture("side", pileLocation(name + "_side"));
    }

    public BlockModelBuilder resourcePallet(Block block) {
        String name = blockName(block);
        String blockModel = palletLocation(name).toString();

        return provider.models().withExistingParent(blockModel, provider.parent("pallet/template/resource_pallet"))
                .texture("top", palletLocation(name + "_top"))
                .texture("front", palletLocation(name + "_front"))
                .texture("side", palletLocation(name + "_side"));
    }
}
