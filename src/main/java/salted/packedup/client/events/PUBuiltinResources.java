package salted.packedup.client.events;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import salted.packedup.PackedUp;
import salted.packedup.client.resourcepack.PUPackResources;

@Mod.EventBusSubscriber(modid = PackedUp.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PUBuiltinResources {

    // Huge thanks to simibubi and team for this!
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFileInfo modFileInfo = ModList.get().getModFileById(PackedUp.MODID);
            if (packError(modFileInfo)) { return; }
            IModFile modFile = modFileInfo.getFile();

            addPack(event, "No Piles", PackSource.BUILT_IN, modFile);
            addPack(event, "Farmer's Textures", PackSource.BUILT_IN, modFile);
            addPack(event, "Alt Textures", PackSource.BUILT_IN, modFile);
        }
    }

    private static void addPack(AddPackFindersEvent event, String name, PackSource packSource, IModFile modFile) {
        // This can probably be done better but eh...
        String packId = name.toLowerCase()
                .replace(' ', '_')
                .replace("'", "");

        event.addRepositorySource(consumer -> {
            Pack pack = Pack.readMetaAndCreate(
                    PackedUp.resLoc(packId).toString(),
                    Component.literal(name),
                    false,
                    id -> new PUPackResources(id, modFile, "resourcepacks/" + packId),
                    PackType.CLIENT_RESOURCES, Pack.Position.TOP, packSource
            );

            if (pack != null) {
                PackedUp.LOGGER.debug("Pack = {}", pack.getId());
                consumer.accept(pack);
            }
        });
    }

    private static Boolean packError(IModFileInfo modFileInfo) {
        if (!(modFileInfo == null)) return false;
        PackedUp.LOGGER.error("Couldn't find Packed Up mod; built-in resource packs will be missing!");
        return true;
    }
}
