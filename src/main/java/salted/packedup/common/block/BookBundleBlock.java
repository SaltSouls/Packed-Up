package salted.packedup.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import salted.packedup.common.block.handlers.BundleHandler;

public class BookBundleBlock extends HorizontalBlock {

    public BookBundleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isCrouching()) { return InteractionResult.PASS; }
        Block block = this.asBlock();

        BundleHandler manager = new BundleHandler();
        return manager.shearBundle(block, state, world, pos, player);
    }
}
