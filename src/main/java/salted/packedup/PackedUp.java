package salted.packedup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import salted.packedup.common.CommonSetup;
import salted.packedup.common.Config;
import salted.packedup.common.registry.PUCreativeTab;
import salted.packedup.common.registry.PULangs;
import salted.packedup.common.registry.PURegistry;
import salted.packedup.common.registry.PUSounds;

@Mod(PackedUp.MODID)
public class PackedUp {

    public static final String MODID = "packedup";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public PackedUp() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);
        modEventBus.addListener(CommonSetup::init);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);

        // without this there is no milk fluid at all, so no tank can hold it
        ForgeMod.enableMilkFluid();

        PURegistry.register();
        PUSounds.register();
        PULangs.register();
        PUCreativeTab.CREATIVE_TAB.register(modEventBus);
    }

    public static ResourceLocation resLoc(String path) {
        return new ResourceLocation(MODID, path);
    }
}