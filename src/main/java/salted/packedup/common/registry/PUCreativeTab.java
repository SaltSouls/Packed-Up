package salted.packedup.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import salted.packedup.PackedUp;

public class PUCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PackedUp.MODID);

    public static final RegistryObject<CreativeModeTab> TAB_PACKED_UP = CREATIVE_TAB.register(PackedUp.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.packedup"))
                    .icon(() -> new ItemStack(PUBlocks.COBBLESTONE_CRATE.get()))
                    .displayItems((parameters, output) -> PUItems.TAB_ITEMS.forEach((item) -> output.accept(item.get())))
                    .build());
}
