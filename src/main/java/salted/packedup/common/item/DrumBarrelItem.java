package salted.packedup.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;
import salted.packedup.common.Config;
import salted.packedup.common.block.entity.DrumBarrelBlockEntity;

import java.util.List;

public class DrumBarrelItem extends BlockItem {
    private static final int BAR_COLOR = 0x3B8ED0;

    public DrumBarrelItem(Block block, Properties props) { super(block, props); }

    public static FluidStack getFluid(ItemStack stack) {
        CompoundTag tag = BlockItem.getBlockEntityData(stack);
        if (tag == null || !tag.contains("Tank")) return FluidStack.EMPTY;
        return FluidStack.loadFluidStackFromNBT(tag.getCompound("Tank"));
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        if (getFluid(stack).isEmpty()) return 64;
        return Config.stackFilledDrums() ? Config.maxDrumStack() : 1;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return !getFluid(stack).isEmpty() && stack.getCount() <= 1;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        float filled = getFluid(stack).getAmount() / (float) DrumBarrelBlockEntity.CAPACITY;
        return Mth.clamp(Math.round(filled * 13.0F), 1, 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        FluidStack fluid = getFluid(stack);
        if (fluid.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.packedup.drum_barrel.empty").withStyle(ChatFormatting.DARK_GRAY));
            return;
        }

        tooltip.add(Component.translatable("tooltip.packedup.drum_barrel.fluid", fluid.getDisplayName()).withStyle(ChatFormatting.DARK_GRAY));
    }
}
