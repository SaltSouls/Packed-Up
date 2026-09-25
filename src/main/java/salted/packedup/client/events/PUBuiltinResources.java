package salted.packedup.client.events;

import net.minecraft.server.packs.PackType;
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
public class PUBuiltinResources extends PUPackResources {
    /**
     * Create the built-in pack id to register
     *
     * @param packId     The unique pack id of the resource pack.
     * @param modFile    The {@link IModFile} containing the resource pack.
     * @param sourcePath The path to the resource pack location.
     */
    public PUBuiltinResources(String packId, IModFile modFile, String sourcePath) { super(packId, modFile, sourcePath); }

    // Huge thanks to simibubi and team for this!
    /**
     * Registers built-in resource packs when the AddPackFindersEvent is fired.
     * This method is triggered during mod initialization to add custom resource packs.
     *
     * @param event The {@link AddPackFindersEvent} triggered by the mod loading process.
     */
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        // Only proceed if the event is for client-side resource packs
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            // Retrieve the mod file information for Packed Up
            IModFileInfo modFileInfo = ModList.get().getModFileById(PackedUp.MODID);
            if (isModFileInvalid(modFileInfo)) return;

            // Add the built-in resource packs
            addResourcePack(event, "No Piles", modFileInfo.getFile());
            addResourcePack(event, "Farmer's Textures", modFileInfo.getFile());
            addResourcePack(event, "Alt Textures", modFileInfo.getFile());
        }
    }

    /**
     * Checks if the mod file is invalid or not found.
     *
     * @param modFileInfo The {@link IModFileInfo} to check.
     * @return True if the mod file is invalid or not found, otherwise false.
     */
    private static boolean isModFileInvalid(IModFileInfo modFileInfo) {
        if (modFileInfo == null) {
            PackedUp.LOGGER.error("Could not find Packed Up mod file; built-in resource packs will be missing!");
            return true;
        }
        return false;
    }
}
