package salted.packedup.common.block.managers;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import salted.packedup.common.block.BookBundleBlock;
import salted.packedup.common.block.BookBundleSlabBlock;
import salted.packedup.common.block.BookPileBlock;
import salted.packedup.common.block.managers.utils.BundleUtils;
import salted.packedup.common.block.state.PUProperties;

public class BundleManager extends BundleUtils {

    private InteractionResult convert(BlockState state, int layers, Level world, BlockPos pos, Player player) {
        ItemStack item = player.getMainHandItem();

        if (!(item.getItem() instanceof ShearsItem)) { return InteractionResult.PASS; }
        if (!player.isCreative()) { item.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND)); }

        world.setBlock(pos, state.setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING))
                .setValue(PUProperties.QUARTER_LAYERS, layers)
                .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)), 0);
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.9F);
        if (world.isClientSide) { return InteractionResult.SUCCESS; }
        return InteractionResult.CONSUME;
    }

    public InteractionResult shearBundle(Block block, BlockState state, Level world, BlockPos pos, Player player) {
        int size;

        if (block instanceof BookBundleBlock) { size = BookPileBlock.MAX_HEIGHT; }
        else if (block instanceof BookBundleSlabBlock) {
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);

            if (type.equals(SlabType.BOTTOM)) { size = BookPileBlock.MAX_HEIGHT / 2; }
            else if (type.equals(SlabType.DOUBLE)) { size = BookPileBlock.MAX_HEIGHT; }
            else return InteractionResult.PASS;
        } else return InteractionResult.PASS;

        Bundle bundle = getBundleType(block);
        switch (bundle) {
            case BASE, WHITE, LIGHT_GRAY, GRAY, BLACK, BROWN, RED, ORANGE, YELLOW, LIME, GREEN, CYAN, LIGHT_BLUE,
                 BLUE, PURPLE, MAGENTA, PINK -> { return convert(bundle.getPile().withPropertiesOf(state), size, world, pos, player); }
            default -> { return InteractionResult.PASS; }
        }
    }
}