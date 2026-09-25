package salted.packedup.client.events.decorators;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;
import net.minecraftforge.fluids.FluidStack;
import salted.packedup.common.Config;
import salted.packedup.common.block.entity.DrumBarrelBlockEntity;
import salted.packedup.common.item.DrumBarrelItem;

public class DrumFillDecorator implements IItemDecorator {
    public static final DrumFillDecorator INSTANCE = new DrumFillDecorator();

    private static final int SHADOW = 0xFF000000;
    private static final int FILL = 0xFF3B8ED0;

    @Override
    public boolean render(GuiGraphics graphics, Font font, ItemStack stack, int x, int y) {
        if (!Config.stackFilledDrums()) return false;

        FluidStack fluid = DrumBarrelItem.getFluid(stack);
        if (fluid.isEmpty()) return false;
        if (stack.getCount() <= 1) return false;


        float filled = fluid.getAmount() / (float) DrumBarrelBlockEntity.CAPACITY;
        int height = Mth.clamp(Math.round(filled * 13.0F), 1, 13);

        // 2x13 backdrop down the left edge, then the fill growing up from the bottom
        graphics.fill(RenderType.guiOverlay(), x + 1, y + 2, x + 3, y + 15, SHADOW);
        graphics.fill(RenderType.guiOverlay(), x + 1, y + 15 - height, x + 2, y + 14, FILL);
        return true;
    }
}
