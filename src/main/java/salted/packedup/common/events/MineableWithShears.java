package salted.packedup.common.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import salted.packedup.PackedUp;
import salted.packedup.common.tag.PUTags;

@Mod.EventBusSubscriber(modid = PackedUp.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MineableWithShears {

    private static boolean isCorrectTool(ItemStack item, BlockState state) {
        if(!item.canPerformAction(ToolActions.SHEARS_DIG)) { return false; }
        return state.is(PUTags.MINEABLE_WITH_SHEARS);
    }

    @SubscribeEvent
    public static void miningSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack item = player.getMainHandItem();
        BlockState state = event.getState();

        if(!isCorrectTool(item, state)) { return; }
        if(player.isSwimming()){ return; }

        event.setNewSpeed(event.getOriginalSpeed() * 5.0f);
    }
}
