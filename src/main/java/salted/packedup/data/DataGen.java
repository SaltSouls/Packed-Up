package salted.packedup.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import salted.packedup.PackedUp;
import salted.packedup.data.loot.PUBlockLoot;
import salted.packedup.data.models.PUBlockStates;
import salted.packedup.data.models.PUItemModels;
import salted.packedup.data.recipes.PUCraftingRecipes;
import salted.packedup.data.tags.PUBlockTags;
import salted.packedup.data.tags.PUItemTags;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = PackedUp.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        PUBlockTags blockTags = new PUBlockTags(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new PUItemTags(output, lookupProvider, blockTags.contentsGetter(), helper));

        PUBlockStates blockStates = new PUBlockStates(output, helper);
        generator.addProvider(event.includeServer(), blockStates);
        generator.addProvider(event.includeServer(), new PUItemModels(output, blockStates.models().existingFileHelper));

        generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(PUBlockLoot::new, LootContextParamSets.BLOCK)
        )));

        generator.addProvider(event.includeServer(), new PUCraftingRecipes(output));

    }
}
