package salted.packedup.data.models;

import com.google.common.collect.Sets;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import salted.packedup.PackedUp;
import salted.packedup.common.block.CrateLidBlock;
import salted.packedup.common.registry.PUBlocks;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;


public class BlocksStates extends BlockStateProvider {
    private static final int DEFAULT_HORIZONTAL_OFFSET = 180;
    static Logger log = PackedUp.LOGGER;

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
     * @param before    the position at which the split occurs
     * @return The desired half of the string array.
     */
    private String nameFromSplit(String name, String substring, Boolean before) {
        if (before) {
            return Arrays.stream(name.split(substring)).findFirst().get();
        } else return Arrays.stream(name.split(substring)).toList().get(1);
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
        // crate lids
        Set<Block> lids = Sets.newHashSet(
                PUBlocks.CRATE_LID.get(),
                PUBlocks.REINFORCED_CRATE_LID.get()
        );
        for (Block lid : lids) {
            this.horizontalBlock(lid, $ -> crateLid(lid), CrateLidBlock.WATERLOGGED);
        }
        // resource crates
        Set<Block> resourceCrates = Sets.newHashSet(
                PUBlocks.COBBLESTONE_CRATE.get(),
                PUBlocks.COBBLED_DEEPSLATE_CRATE.get(),
                PUBlocks.ANDESITE_CRATE.get(),
                PUBlocks.DIORITE_CRATE.get(),
                PUBlocks.GRANITE_CRATE.get(),
                PUBlocks.TUFF_CRATE.get(),
                PUBlocks.BLACKSTONE_CRATE.get(),
                PUBlocks.BASALT_CRATE.get(),
                PUBlocks.RAW_COPPER_CRATE.get(),
                PUBlocks.RAW_IRON_CRATE.get(),
                PUBlocks.RAW_GOLD_CRATE.get()
        );
        for (Block crate : resourceCrates) {
            this.simpleBlock(crate, withRandomRotation(resourceCrate(crate, false)));
        }
        // reinforced
        Set<Block> reinforcedCrates = Sets.newHashSet(
                PUBlocks.REINFORCED_COBBLESTONE_CRATE.get(),
                PUBlocks.REINFORCED_COBBLED_DEEPSLATE_CRATE.get(),
                PUBlocks.REINFORCED_ANDESITE_CRATE.get(),
                PUBlocks.REINFORCED_DIORITE_CRATE.get(),
                PUBlocks.REINFORCED_GRANITE_CRATE.get(),
                PUBlocks.REINFORCED_TUFF_CRATE.get(),
                PUBlocks.REINFORCED_BLACKSTONE_CRATE.get(),
                PUBlocks.REINFORCED_BASALT_CRATE.get()
        );
        for (Block crate : reinforcedCrates) {
            this.simpleBlock(crate, withRandomRotation(reinforcedCrate(crate, false)));
        }
        // misc crates
        String gunpowderCrate = blockName(PUBlocks.GUNPOWDER_CRATE.get());
        this.simpleBlock(PUBlocks.GUNPOWDER_CRATE.get(), withRandomRotation(
                models().withExistingParent(gunpowderCrate, parent("template_crate"))
                        .texture("top", blockLocation("gunpowder_crate_top"))));

        // produce crates
        this.simpleCrate(PUBlocks.GOLDEN_CARROT_CRATE.get());
        this.simpleCrate(PUBlocks.EGG_CRATE.get());

        // mushroom crates
        Set<Block> mushroomCrates = Sets.newHashSet(
                PUBlocks.RED_MUSHROOM_CRATE.get(),
                PUBlocks.BROWN_MUSHROOM_CRATE.get()
        );
        for (Block crate : mushroomCrates) {
            String crateName = blockName(crate);
            this.simpleBlock(crate, models().withExistingParent(crateName, parent(crateName)));
        }

        // resource bags
        Set<Block> resourceBags = Sets.newHashSet(
                PUBlocks.DIRT_BAG.get(),
                PUBlocks.ROOTED_DIRT_BAG.get(),
                PUBlocks.COARSE_DIRT_BAG.get(),
                PUBlocks.GRAVEL_BAG.get()
        );
        for (Block bag : resourceBags) {
            this.simpleBlock(bag, resourceBag(bag, false));
        }

        // produce baskets
        this.simpleBasket(PUBlocks.APPLE_BASKET.get());
        this.simpleBasket(PUBlocks.GOLDEN_APPLE_BASKET.get());
        this.simpleBasket(PUBlocks.SWEET_BERRY_BASKET.get());
        this.simpleBlock(PUBlocks.GLOW_BERRY_BASKET.get(), existingModel(PUBlocks.GLOW_BERRY_BASKET.get()));

        // material bags
        this.simpleBag(PUBlocks.COCOA_BEAN_BAG.get());
        this.simpleBag(PUBlocks.SUGAR_BAG.get());
        this.simpleBag(PUBlocks.NETHER_WART_BAG.get());
        this.simpleBlock(PUBlocks.GLOWSTONE_DUST_BAG.get(), existingModel(PUBlocks.GLOWSTONE_DUST_BAG.get()));

        // fish barrels
        this.simpleBarrel(PUBlocks.COD_BARREL.get());
        this.simpleBarrel(PUBlocks.SALMON_BARREL.get());

        // brick piles
        Set<Block> brickPiles = Sets.newHashSet(
                PUBlocks.BRICK_PILE.get(),
                PUBlocks.NETHER_BRICK_PILE.get(),
                PUBlocks.STONE_PILE.get(),
                PUBlocks.DEEPSLATE_PILE.get(),
                PUBlocks.CALCITE_PILE.get()
        );
        for (Block bricks : brickPiles) {
            this.horizontalBlock(bricks, brickPile(bricks));
        }

        // resource pallets
        Set<Block> resourcePallets = Sets.newHashSet(
                PUBlocks.BRICK_PALLET.get(),
                PUBlocks.NETHER_BRICK_PALLET.get(),
                PUBlocks.STONE_PALLET.get(),
                PUBlocks.DEEPSLATE_PALLET.get(),
                PUBlocks.CALCITE_PALLET.get(),
                PUBlocks.COPPER_PALLET.get(),
                PUBlocks.IRON_PALLET.get(),
                PUBlocks.GOLD_PALLET.get(),
                PUBlocks.NETHERITE_PALLET.get()
        );
        for (Block pallet : resourcePallets) {
            this.horizontalBlock(pallet, resourcePallet(pallet));
        }
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

    public BlockModelBuilder brickPile(Block block) {
        String name = blockName(block);
        String resource = nameFromSplit(name, "_pile", true);

        return models().withExistingParent(name, parent("template_brick_pile"))
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
}
