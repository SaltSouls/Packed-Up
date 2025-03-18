package salted.packedup.common.block.handlers;

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
import salted.packedup.common.block.handlers.utils.BundleUtils;
import salted.packedup.common.block.state.PUProperties;

public class BundleHandler extends BundleUtils {

    // Convert bundle to pile maintaining state information
    private InteractionResult convert(BlockState state, int layers, Level world, BlockPos pos, Player player) {
        ItemStack item = player.getMainHandItem();

        if (!(item.getItem() instanceof ShearsItem)) return InteractionResult.PASS;
        if (!player.isCreative()) { item.hurtAndBreak(1, player, user -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND)); }

        BlockState newState = state.setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING))
                .setValue(PUProperties.QUARTER_LAYERS, layers)
                .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED));

        world.setBlock(pos, newState, 0);
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.9F);
        return world.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    // Shear a book bundle or slab
    public InteractionResult shearBundle(Block block, BlockState state, Level world, BlockPos pos, Player player) {
        int size = getBundleSize(block, state);
        if (size == -1) return InteractionResult.PASS;

        Bundle bundle = getBundleType(block);
        if (bundle == null) return InteractionResult.PASS;
        return convert(bundle.getPile().withPropertiesOf(state), size, world, pos, player);
    }

    // Get the size of the bundle based on the block type
    private int getBundleSize(Block block, BlockState state) {
        if (block instanceof BookBundleBlock) return BookPileBlock.MAX_HEIGHT;
        else if (block instanceof BookBundleSlabBlock) {
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);
            return switch (type) {
                case BOTTOM -> BookPileBlock.MAX_HEIGHT / 2;
                case DOUBLE -> BookPileBlock.MAX_HEIGHT;
                default -> -1;
            };
        }
        else return -1;
    }
}