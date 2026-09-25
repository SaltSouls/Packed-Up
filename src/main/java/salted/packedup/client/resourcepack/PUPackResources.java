package salted.packedup.client.resourcepack;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import salted.packedup.PackedUp;

public class PUPackResources extends PathPackResources {
    protected final IModFile modFile;
    protected final String sourcePath;

    /**
     * Create the built-in pack id to register
     *
     * @param packId     The unique pack id of the resource pack.
     * @param modFile    The {@link IModFile} containing the resource pack.
     * @param sourcePath The path to the resource pack location.
     */
    protected PUPackResources(String packId, IModFile modFile, String sourcePath) {
        super(packId, true, modFile.findResource(sourcePath));
        this.modFile = modFile;
        this.sourcePath = sourcePath;
    }

    /**
     * Adds a built-in resource pack to the game.
     *
     * @param event   The {@link AddPackFindersEvent} to which the pack will be added.
     * @param name    The display name of the resource pack.
     * @param modFile The {@link IModFile} containing the resource pack.
     */
    protected static void addResourcePack(AddPackFindersEvent event, String name, IModFile modFile) {
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
     *
     * @param name The display name of the resource pack.
     * @return The formatted pack ID.
     */
    private static String formatPackId(String name) {
        return name.toLowerCase()
                .replace(' ', '_')
                .replace("'", "");
    }

}