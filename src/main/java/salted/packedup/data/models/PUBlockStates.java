package salted.packedup.data.models;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.data.ExistingFileHelper;
import salted.packedup.common.registry.PURegistry;
import salted.packedup.common.registry.helpers.Organizer;
import salted.packedup.data.models.builders.PUBlockBuilder;
import salted.packedup.data.models.builders.blocks.*;

import static salted.packedup.data.utils.NameUtils.*;

public class PUBlockStates extends PUBlockBuilder {

    public PUBlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        var crates     = new CrateModelBuilder(this);
        var containers = new ContainerModelBuilder(this);
        var books      = new BookModelBuilder(this);
        var overlays   = new OverlayModelBuilder(this);
        var piles      = new PileModelBuilder(this);
        var spools     = new SpoolModelBuilder(this);

        // crate lids
        crates.simpleCrateLid(PURegistry.CRATE_LID.get(), BlockStateProperties.WATERLOGGED);
        crates.simpleCrateLid(PURegistry.REINFORCED_CRATE_LID.get(), BlockStateProperties.WATERLOGGED);

        // resource crates
        for (var entry : PURegistry.RESOURCE_CRATE_ENTRIES) {
            this.simpleBlock(entry.get(), withRandomRotation(crates.resourceCrate(entry.get(), false)));
        }

        // reinforced crates
        for (var entry : PURegistry.REINFORCED_CRATE_ENTRIES) {
            this.simpleBlock(entry.get(), withRandomRotation(crates.reinforcedCrate(entry.get(), false)));
        }

        // misc crates (alt top texture)
        for (var entry : PURegistry.MISC_CRATE_ENTRIES) {
            this.simpleBlock(entry.get(), withRandomRotation(crates.resourceCrate(entry.get(), true)));
        }

        // produce crates
        for (var entry : PURegistry.PRODUCE_CRATE_ENTRIES) {
            crates.simpleCrate(entry.get());
        }

        // mushroom crates
        for (var entry : PURegistry.MUSHROOM_CRATE_ENTRIES) {
            crates.mushroomCrate(entry.get());
        }

        // drum barrels
        for (var entry : PURegistry.DRUM_BARREL_ENTRIES) {
            containers.simpleDrumBarrel(entry.get());
        }

        // fluid gauge
        containers.fluidGauge(PURegistry.FLUID_GAUGE.get());

        // resource bags
        for (var entry : PURegistry.RESOURCE_BAG_ENTRIES) {
            this.simpleBlock(entry.get(), containers.resourceBag(entry.get(), false));
        }

        // produce baskets
        containers.simpleBasket(PURegistry.APPLE_BASKET.get());
        containers.simpleBasket(PURegistry.GOLDEN_APPLE_BASKET.get());
        containers.simpleBasket(PURegistry.SWEET_BERRY_BASKET.get());
        this.simpleBlock(PURegistry.GLOW_BERRY_BASKET.get(), existingModel("basket/" + blockName(PURegistry.GLOW_BERRY_BASKET.get())));

        // material bags
        containers.simpleBag(PURegistry.COCOA_BEAN_BAG.get());
        containers.simpleBag(PURegistry.SUGAR_BAG.get());
        containers.simpleBag(PURegistry.NETHER_WART_BAG.get());
        this.simpleBlock(PURegistry.GLOWSTONE_DUST_BAG.get(), existingModel("bag/" + blockName(PURegistry.GLOWSTONE_DUST_BAG.get())));
        containers.simpleBag(PURegistry.ENDER_PEARL_BAG.get());

        // fish barrels
        for (var entry : PURegistry.BARREL_ENTRIES) {
            containers.simpleBarrel(entry.get());
        }

        // piles
        for (var entry : PURegistry.PILE_ENTRIES) {
            this.horizontalBlock(entry.get(), piles.resourcePile(entry.get()));
        }

        // pallet
        this.horizontalQuarterSlabBlock(PURegistry.PALLET.get(), "pallet/", BlockStateProperties.WATERLOGGED);

        // resource pallets
        for (var entry : PURegistry.PALLET_ENTRIES) {
            this.horizontalBlock(entry.get(), piles.resourcePallet(entry.get()));
        }

        // default book variants
        books.simpleBookBundle(PURegistry.BOOK_BUNDLE.get());
        books.simpleBookBundleSlab(PURegistry.BOOK_BUNDLE_SLAB.get(), BlockStateProperties.WATERLOGGED);
        books.simpleBookPile(PURegistry.BOOK_PILE.get(), BlockStateProperties.WATERLOGGED);

        // colored book variants
        for (Organizer.ColoredBookSet set : PURegistry.COLORED_BOOKS.values()) {
            books.simpleColoredBookBundle(set.getBundle());
            books.simpleColoredBookBundleSlab(set.getSlab(), BlockStateProperties.WATERLOGGED);
            books.simpleColoredBookPile(set.getPile(), BlockStateProperties.WATERLOGGED);
        }

        // turf
        this.simpleBlock(PURegistry.GRASS_TURF.get(), withRandomRotation(overlays.simpleOverlayBlock(PURegistry.GRASS_TURF.get(), true)));
        overlays.simpleTurfBlock(PURegistry.GRASS_TURF_LAYER.get(), true, BlockStateProperties.WATERLOGGED);

        this.simpleBlock(PURegistry.MYCELIUM_TURF.get(), withRandomRotation(overlays.simpleOverlayBlock(PURegistry.MYCELIUM_TURF.get(), false)));
        overlays.simpleTurfBlock(PURegistry.MYCELIUM_TURF_LAYER.get(), false, BlockStateProperties.WATERLOGGED);

        String podzolTurf = blockName(PURegistry.PODZOL_TURF.get());
        this.simpleBlock(PURegistry.PODZOL_TURF.get(), withRandomRotation(models().cubeBottomTop(turfLocation(podzolTurf).toString(), turfLocation(podzolTurf + "_side"), turfLocation(podzolTurf + "_bottom"), mcBlockLocation("podzol_top"))));
        overlays.simpleQuarterSlabBlock(PURegistry.PODZOL_TURF_LAYER.get(), true, true, BlockStateProperties.WATERLOGGED);

        // grass bundle/thatch
        overlays.grassBaleBlock(PURegistry.GRASS_BALE.get());
        this.simpleBlock(PURegistry.GRASS_THATCH.get(), overlays.simpleOverlayBlock(PURegistry.GRASS_THATCH.get(), false));
        overlays.simpleOverlayStairsBlock(PURegistry.GRASS_THATCH_STAIRS.get());
        overlays.simpleOverlaySlabBlock(PURegistry.GRASS_THATCH_SLAB.get());

        // industrial spools
        for (var entry : PURegistry.COLORED_SPOOLS.values()) {
            spools.simpleIndustrialSpool(entry.get(), BlockStateProperties.WATERLOGGED);
        }
    }

}
