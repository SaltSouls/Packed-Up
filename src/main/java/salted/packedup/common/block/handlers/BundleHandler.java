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

    /**
     * Converts a bundle or slab into a pile while maintaining block state information.
     *
     * @param state  The current {@link BlockState} of the {@link BookBundleBlock} or {@link BookBundleSlabBlock}.
     * @param layers The number of layers the {@link BookPileBlock} should have.
     * @param world  The world ({@link Level}) where the {@link Block} is located.
     * @param pos    The {@link BlockPos} of the {@link Block}.
     * @param player The {@link Player} performing the action.
     * @return An {@link InteractionResult} indicating the outcome of the interaction (SUCCESS, CONSUME, or PASS).
     */
    private InteractionResult convert(BlockState state, int layers, Level world, BlockPos pos, Player player) {
        ItemStack item = player.getMainHandItem();
        if (!(item.getItem() instanceof ShearsItem)) return InteractionResult.PASS;

        // Damage the shears if the player is not in creative mode
        if (!player.isCreative()) { item.hurtAndBreak(1, player, user -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND)); }

        // Create a new block state for the pile, maintaining the original block's properties
        BlockState newState = state.setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING))
                .setValue(PUProperties.QUARTER_LAYERS, layers) // Set the number of layers for the pile
                .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)); // Maintain waterlogged state

        // Update the block in the world with the new state and play shearing sound
        world.setBlock(pos, newState, 0);
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 0.9F);

        // Return SUCCESS on the client side, CONSUME on the server side
        return world.isClientSide ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
    }

    /**
     * Handles the shearing interaction for the bundle or slab block.
     * Determines the size of the resulting pile and converts the block.
     *
     * @param block  The {@link Block} being sheared (bundle or slab).
     * @param state  The current {@link BlockState}.
     * @param world  The world ({@link Level}) where the block is located.
     * @param pos    The {@link BlockPos} of the block.
     * @param player The {@link Player} performing the action.
     * @return An {@link InteractionResult} indicating the outcome of the interaction (SUCCESS, CONSUME, or PASS).
     */
    public InteractionResult shearBundle(Block block, BlockState state, Level world, BlockPos pos, Player player) {
        // Get the size (number of layers) for the resulting pile
        int size = getBundleSize(block, state);
        if (size == -1) return InteractionResult.PASS;

        // Get the bundle type (e.g., base, colored) based on the block
        Bundle bundle = getBundleType(block);
        if (bundle == null) return InteractionResult.PASS;

        // Convert the bundle to a pile, maintaining the original block's properties
        return convert(bundle.getPile().withPropertiesOf(state), size, world, pos, player);
    }

    /**
     * Determines the size of the resulting pile based on the bundle or slab type.
     *
     * @param block The {@link Block} being sheared.
     * @param state The current {@link BlockState}.
     * @return The number of layers for the {@link BookPileBlock}, or -1 if the {@link Block} type is invalid.
     */
    private int getBundleSize(Block block, BlockState state) {
        if (block instanceof BookBundleBlock) { return BookPileBlock.MAX_HEIGHT; }
        else if (block instanceof BookBundleSlabBlock) {
            // Determine the size based on the slab type
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);
            return switch (type) {
                case BOTTOM -> BookPileBlock.MAX_HEIGHT / 2; // Half-sized slab
                case DOUBLE -> BookPileBlock.MAX_HEIGHT;     // Full-sized slab
                default -> -1;                               // Invalid slab type (e.g., TOP)
            };
        }
        else { return -1; }
    }
}