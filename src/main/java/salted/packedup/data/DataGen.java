package salted.packedup.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import salted.packedup.PackedUp;
import salted.packedup.common.registry.PULangs;
import salted.packedup.common.registry.helpers.Translations;
import salted.packedup.data.lang.PULanguages;
import salted.packedup.data.models.PUBlockStates;
import salted.packedup.data.models.PUItemModels;
import salted.packedup.data.recipes.PUCraftingRecipes;
import salted.packedup.data.tags.PUBlockTags;
import salted.packedup.data.tags.PUFluidTags;
import salted.packedup.data.tags.PUItemTags;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = PackedUp.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(@NotNull GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        PUBlockTags blockTags = new PUBlockTags(output, lookupProvider, helper);
        PUFluidTags fluidTags = new PUFluidTags(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), fluidTags);
        generator.addProvider(event.includeServer(), new PUItemTags(output, lookupProvider, blockTags.contentsGetter(), helper));

        PUBlockStates blockStates = new PUBlockStates(output, helper);
        generator.addProvider(event.includeServer(), blockStates);
        generator.addProvider(event.includeServer(), new PUItemModels(output, blockStates.models().existingFileHelper));

        generator.addProvider(event.includeServer(), new PUCraftingRecipes(output));

        for (Translations translations : PULangs.TRANSLATED) {
            generator.addProvider(event.includeClient(), new PULanguages(output, translations));
        }
    }
}
