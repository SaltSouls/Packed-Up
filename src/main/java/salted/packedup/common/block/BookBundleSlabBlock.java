package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import static salted.packedup.common.util.BundleUtils.shearBundle;

public class BookBundleSlabBlock extends HorizontalSlabBlock {

    public BookBundleSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isCrouching()) { return InteractionResult.PASS; }
        Block block = state.getBlock();
        Item item = player.getItemInHand(hand).getItem();

        if (!(item == Items.SHEARS)) { return InteractionResult.PASS; }
        return shearBundle(block, state, world, pos, player);
    }
}
