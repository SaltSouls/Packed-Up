package salted.packedup.common.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import salted.packedup.PackedUp;

import java.util.List;

import static salted.packedup.common.registry.helpers.Organizer.TAB_ORDER;

public class PUCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PackedUp.MODID);

    public static final RegistryObject<CreativeModeTab> TAB_PACKED_UP = CREATIVE_TAB.register(PackedUp.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.packedup"))
                    .icon(() -> new ItemStack(PURegistry.COBBLESTONE_CRATE.get()))
                    .displayItems((parameters, output) -> {
                        for (TabCategory category : TabCategory.values()) {
                            List<RegistryEntry<? extends Block>> entries = TAB_ORDER.get(category);
                            if (entries == null) continue;
                            for (RegistryEntry<? extends Block> entry : entries) {
                                output.accept(entry.get());
                            }
                        }
                    }).build());
}
