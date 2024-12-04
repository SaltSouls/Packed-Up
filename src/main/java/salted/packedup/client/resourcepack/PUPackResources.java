package salted.packedup.client.resourcepack;

import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;

public class PUPackResources extends PathPackResources {
    protected final IModFile modFile;
    protected final String sourcePath;

    public PUPackResources(String packId, IModFile modFile, String sourcePath) {
        super(packId, true, modFile.findResource(sourcePath));
        this.modFile = modFile;
        this.sourcePath = sourcePath;
    }

}