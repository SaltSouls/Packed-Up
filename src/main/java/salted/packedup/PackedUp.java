package salted.packedup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import salted.packedup.common.registry.PUBlocks;
import salted.packedup.common.registry.PUCreativeTab;
import salted.packedup.common.registry.PUItems;

@Mod(PackedUp.MODID)
public class PackedUp {

    public static final String MODID = "packedup";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public PackedUp() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);
//        modEventBus.addListener(CommonSetup::init);

        PUBlocks.BLOCKS.register(modEventBus);
        PUItems.ITEMS.register(modEventBus);
        PUCreativeTab.CREATIVE_TAB.register(modEventBus);
    }

    public static ResourceLocation resLoc(String path) {
        return new ResourceLocation(MODID, "path");
    }
}