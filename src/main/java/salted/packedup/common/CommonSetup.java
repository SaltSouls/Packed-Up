package salted.packedup.common;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // one day I'll use this for something... maybe.
        });
    }

}
