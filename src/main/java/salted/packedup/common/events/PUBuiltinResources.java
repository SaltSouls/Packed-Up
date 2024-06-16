package salted.packedup.common.events;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import salted.packedup.PackedUp;
import salted.packedup.client.PUPackResources;

@EventBusSubscriber(modid = PackedUp.MODID, bus = EventBusSubscriber.Bus.MOD)
public class PUBuiltinResources {

    // Huge thanks to simibubi and team for this!
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFileInfo modFileInfo = ModList.get().getModFileById(PackedUp.MODID);

            if (modFileInfo == null) {
                PackedUp.LOGGER.error("Couldn't find Packed Up mod; built-in resource packs will be missing!");
                return;
            }

            IModFile modFile = modFileInfo.getFile();
            event.addRepositorySource(consumer -> {
                Pack pack = Pack.readMetaAndCreate(
                        PackedUp.resLoc("no_piles").toString(),
                        Component.literal("No Piles"),
                        false,
                        id -> new PUPackResources(id, modFile, "resourcepacks/no_piles"),
                        PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                PackedUp.LOGGER.debug("Pack = {}", pack);
                if (pack != null) {
                    consumer.accept(pack);
                }
            });
        }
    }
}
