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
            if (isModFileInvalid(modFileInfo)) { return; }

            // Add the built-in resource packs
            addResourcePack(event, "No Piles", modFileInfo.getFile());
            addResourcePack(event, "Farmer's Textures", modFileInfo.getFile());
            addResourcePack(event, "Alt Textures", modFileInfo.getFile());
        }
    }

    /**
     * Adds a built-in resource pack to the game.
     *
     * @param event   The {@link AddPackFindersEvent} to which the pack will be added.
     * @param name    The display name of the resource pack.
     * @param modFile The {@link IModFile} containing the resource pack.
     */
    private static void addResourcePack(AddPackFindersEvent event, String name, IModFile modFile) {
        // Generate a unique pack ID by formatting the name
        String packId = formatPackId(name);

        // Register the pack with the event's repository source
        event.addRepositorySource(consumer -> {
            Pack pack = Pack.readMetaAndCreate(
                    PackedUp.resLoc(packId).toString(),
                    Component.literal(name),
                    false,
                    id -> new PUPackResources(id, modFile, "resourcepacks/" + packId),
                    PackType.CLIENT_RESOURCES,
                    Pack.Position.TOP,
                    PackSource.BUILT_IN
            );

            // Log and accept the pack if it was successfully created
            if (pack != null) {
                PackedUp.LOGGER.debug("Registered resource pack: {}", pack.getId());
                consumer.accept(pack);
            }
        });
    }

    /**
     * Formats a display name into a valid pack ID.
     * Converts the name to lowercase, replaces spaces with underscores, and removes apostrophes.
     *
     * @param name The display name of the resource pack.
     * @return The formatted pack ID.
     */
    private static String formatPackId(String name) {
        return name.toLowerCase()
                .replace(' ', '_')
                .replace("'", "");
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
