package salted.packedup.data.tags;

import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import salted.packedup.PackedUp;
import salted.packedup.common.tag.PUTags;

import java.util.concurrent.CompletableFuture;

public class PUFluidTags extends FluidTagsProvider {
    public PUFluidTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PackedUp.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(PUTags.DRUM_DRIPPING_FAST).add(Fluids.WATER);
        this.tag(PUTags.DRUM_DRIPPING_SLOW).addTags(PUTags.MOLTEN);
        this.tag(PUTags.MOLTEN).add(Fluids.LAVA);
    }

    private void registerFluidTags() {

    }
}
