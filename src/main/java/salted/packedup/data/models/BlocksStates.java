package salted.packedup.data.models;

import com.google.common.collect.Sets;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PUBlocks;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.Arrays;
import java.util.Set;


public class BlocksStates extends BlockStateProvider {
    public BlocksStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PackedUp.MODID, exFileHelper);
    }

    // general pathing/convenience functions
    private String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    /**
     * Splits the string and returns everything before or after the split.
     *
     * @param name      the name of the {@link Block} we are using
     * @param substring the string at which the split occurs
     * @return The desired half of the string array.
     */
    private String nameFromSplit(String name, String substring) {
        return Arrays.stream(name.split(substring)).findFirst().get();
    }

    public ResourceLocation blockLocation(String path) {
        return new ResourceLocation(PackedUp.MODID, "block/" + path);
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

    // model/block state registration
    @Override
    protected void registerStatesAndModels() {
        // resource crates
        Set<Block> resourceCrates = Sets.newHashSet(
                PUBlocks.COBBLESTONE_CRATE.get(),
                PUBlocks.COBBLED_DEEPSLATE_CRATE.get(),
                PUBlocks.RAW_COPPER_CRATE.get(),
                PUBlocks.RAW_IRON_CRATE.get(),
                PUBlocks.RAW_GOLD_CRATE.get()
        );
        for (Block crate : resourceCrates) {
            this.simpleBlock(crate, withRandomRotation(resourceCrate(crate)));
        }
        this.simpleBlock(PUBlocks.GUNPOWDER_CRATE.get(), withRandomRotation(
                models().cubeBottomTop(
                        "gunpowder_crate",
                        blockLocation("crate_side"),
                        fdBlockLocation("crate_bottom"),
                        blockLocation("gunpowder_crate_top"))));

        // produce crates
        simpleCrate(PUBlocks.GOLDEN_CARROT_CRATE.get());
        simpleCrate(PUBlocks.EGG_CRATE.get());

        // resource bags
        Set<Block> resourceBags = Sets.newHashSet(
                PUBlocks.DIRT_BAG.get(),
                PUBlocks.ROOTED_DIRT_BAG.get(),
                PUBlocks.COARSE_DIRT_BAG.get(),
                PUBlocks.GRAVEL_BAG.get()
        );
        for (Block bag : resourceBags) {
            this.simpleBlock(bag, resourceBag(bag));
        }

        // produce baskets
        simpleBasket(PUBlocks.APPLE_BASKET.get());
        simpleBasket(PUBlocks.GOLDEN_APPLE_BASKET.get());
        simpleBasket(PUBlocks.SWEET_BERRY_BASKET.get());
        this.simpleBlock(PUBlocks.GLOW_BERRY_BASKET.get(), existingModel(PUBlocks.GLOW_BERRY_BASKET.get()));

        // material bags
        simpleBag(PUBlocks.COCOA_BEAN_BAG.get());
        simpleBag(PUBlocks.SUGAR_BAG.get());
        simpleBag(PUBlocks.NETHER_WART_BAG.get());
        this.simpleBlock(PUBlocks.GLOWSTONE_DUST_BAG.get(), existingModel(PUBlocks.GLOWSTONE_DUST_BAG.get()));

        // fish barrels
        simpleBarrel(PUBlocks.COD_BARREL.get());
        simpleBarrel(PUBlocks.SALMON_BARREL.get());
    }

    // custom model/state functions
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

    public BlockModelBuilder resourceBag(Block block) {
        String name = blockName(block);
        String resource = nameFromSplit(name, "_bag");

        return models().withExistingParent(name, parent("template_bag"))
                .texture("pile", blockLocation(resource + "_pile"));
    }

    public void simpleBag(Block block) {
        String name = blockName(block);

        this.simpleBlock(block, models().withExistingParent(name, "cube")
                .texture("particle", blockLocation(name + "_top"))
                .texture("down", fdBlockLocation("rice_bag_bottom"))
                .texture("up", blockLocation(name + "_top"))
                .texture("north", fdBlockLocation("rice_bag_side_tied"))
                .texture("south", fdBlockLocation("rice_bag_side_tied"))
                .texture("east", fdBlockLocation("rice_bag_side"))
                .texture("west", fdBlockLocation("rice_bag_side"))
        );
    }

    public BlockModelBuilder resourceCrate(Block block) {
        String name = blockName(block);
        String resource = nameFromSplit(name, "_crate");

        return models().withExistingParent(name, parent("template_crate"))
                .texture("pile", blockLocation(resource + "_pile"));
    }

    public void simpleCrate(Block block) {
        String name = blockName(block);

        this.simpleBlock(block,
                models().cubeBottomTop(blockName(block), blockLocation(name + "_side"), fdBlockLocation("crate_bottom"), blockLocation(name + "_top")));
    }
}
