package salted.packedup.common;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import salted.packedup.common.block.handlers.utils.FluidUtils;

public class CommonSetup {
    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(FluidUtils::buildBucketMap);
    }

}
