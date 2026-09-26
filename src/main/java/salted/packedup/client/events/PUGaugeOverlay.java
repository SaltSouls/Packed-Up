package salted.packedup.client.events;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import salted.packedup.PackedUp;
import salted.packedup.common.block.entity.FluidGaugeBlockEntity;
import salted.packedup.common.registry.PURegistry;

import java.awt.*;

@Mod.EventBusSubscriber(modid = PackedUp.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PUGaugeOverlay {
    // fade-in animation
    private static final int ICON = 16;
    private static final int PADDING = 4;
    private static final int LINE_GAP = 1;
    private static final int MIN_INDENT = ICON + PADDING;
    private static final int OFFSET = 26;
    private static final float FADE_MS = 350.0F;
    private static final float SLIDE = 8.0F;
    private static final double RANGE = 2.5D;
    // tooltip colors
    private static final int BACKGROUND = 0xC0100010;
    private static final int BORDER_TOP = 0x505000FF;
    private static final int BORDER_BOTTOM = 0x5028007F;
    private static final int WHITE = ChatFormatting.WHITE.getColor();
    private static final int LIGHT_GRAY = ChatFormatting.GRAY.getColor();
    private static final int GRAY = ChatFormatting.DARK_GRAY.getColor();
    private static final int GOLD = ChatFormatting.GOLD.getColor();

    @Nullable
    private static BlockPos lastGauge;
    private static long shownAt;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.CROSSHAIR.type()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.options.hideGui) return;

        FluidGaugeBlockEntity gauge = lookingAt(mc);
        if (gauge == null) {
            lastGauge = null;
            return;
        }

        // wall clock, since this runs per frame rather than per tick
        long now = System.currentTimeMillis();
        if (!gauge.getBlockPos().equals(lastGauge)) {
            lastGauge = gauge.getBlockPos();
            shownAt = now;
        }

        float fade = Mth.clamp((now - shownAt) / FADE_MS, 0.0F, 1.0F);
        render(event.getGuiGraphics(), mc, gauge, fade);
    }

    @Nullable
    private static FluidGaugeBlockEntity lookingAt(Minecraft mc) {
        if (!(mc.hitResult instanceof BlockHitResult hit)) return null;
        if (mc.level == null || mc.player == null) return null;
        if (mc.player.getEyePosition().distanceToSqr(hit.getLocation()) > RANGE * RANGE) return null;
        return mc.level.getBlockEntity(hit.getBlockPos()) instanceof FluidGaugeBlockEntity gauge ? gauge : null;
    }

    private static void render(GuiGraphics graphics, Minecraft mc, FluidGaugeBlockEntity gauge, float fade) {
        Font font = mc.font;
        FluidStack fluid = gauge.getContents();
        int capacity = gauge.getCapacity();

        Component title = Component.translatable("tooltip.packedup.fluid_gauge.title");
        Component label = label(fluid, capacity);

        float eased = 1.0F - (1.0F - fade) * (1.0F - fade);
        int alpha = Mth.clamp((int) (eased * 255.0F), 0, 255) << 24;
        float slide = (1.0F - eased) * SLIDE;

        boolean hasFluid = !fluid.isEmpty() && capacity > 0;
        int lines = capacity > 0 ? 2 : 1;

        int swatch = lines * font.lineHeight + (lines - 1) * LINE_GAP;
        int indent = hasFluid ? swatch + PADDING : MIN_INDENT;

        int titleWidth = ICON + PADDING + font.width(title);
        int labelWidth = indent + font.width(label);
        int amountWidth = capacity > 0 ? indent + amountWidth(font, fluid, capacity) : 0;

        int content = Math.max(titleWidth, Math.max(labelWidth, amountWidth));
        int width = content + PADDING * 2;

        int height = PADDING * 2 + Math.max(ICON, font.lineHeight) + lines * (font.lineHeight + LINE_GAP);

        int x = graphics.guiWidth() / 2 + OFFSET;
        int y = (graphics.guiHeight() - height) / 2;

        graphics.pose().pushPose();
        graphics.pose().translate(slide, 0.0F, 400.0F);

        panel(graphics, x, y, width, height, alpha);

        int textY = y + PADDING + (ICON - font.lineHeight) / 2;
        graphics.renderFakeItem(icon(), x + (PADDING + 1), y + PADDING);
        graphics.drawString(font, title, x + PADDING + ICON + PADDING, textY, faded(WHITE, alpha), true);

        int lineY = y + PADDING + Math.max(ICON, font.lineHeight) + LINE_GAP;
        if (hasFluid) swatch(graphics, fluid, x + PADDING, lineY, swatch, alpha);

        graphics.drawString(font, label, x + PADDING + indent, lineY, faded(LIGHT_GRAY, alpha), true);

        if (capacity > 0) {
            lineY += font.lineHeight + LINE_GAP;
            amounts(graphics, font, fluid, capacity, x + PADDING + indent, lineY, alpha);
        }

        graphics.pose().popPose();
    }

    /** Amount in gold, the rest in grey, the way a fill readout normally reads. */
    private static void amounts(GuiGraphics graphics, Font font, FluidStack fluid, int capacity, int x, int y, int alpha) {
        Component held = Component.literal(String.format("%,d", fluid.getAmount()) + "mB");
        Component slash = Component.literal(" / ");
        Component total = Component.literal(String.format("%,d", capacity) + "mB");

        graphics.drawString(font, held, x, y, faded(GOLD, alpha), true);
        x += font.width(held);

        graphics.drawString(font, slash, x, y, faded(LIGHT_GRAY, alpha), true);
        x += font.width(slash);

        graphics.drawString(font, total, x, y, faded(GRAY, alpha), true);
    }

    private static void swatch(GuiGraphics graphics, FluidStack fluid, int x, int y, int size, int alpha) {
        IClientFluidTypeExtensions ext = IClientFluidTypeExtensions.of(fluid.getFluid());
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(ext.getStillTexture(fluid));

        int tint = ext.getTintColor(fluid);
        graphics.setColor(
                (tint >> 16 & 0xFF) / 255.0F,
                (tint >> 8 & 0xFF) / 255.0F,
                (tint & 0xFF) / 255.0F,
                (alpha >>> 24) / 255.0F);

        graphics.blit(x, y, 0, size, size, sprite);
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static int amountWidth(Font font, FluidStack fluid, int capacity) {
        return font.width(String.format("%,d", fluid.getAmount()) + "mB / " + String.format("%,d", capacity) + "mB");
    }

    private static void panel(GuiGraphics graphics, int x, int y, int width, int height, int alpha) {
        int background = faded(BACKGROUND, scaleAlpha(BACKGROUND, alpha));
        int top = faded(BORDER_TOP, scaleAlpha(BORDER_TOP, alpha));
        int bottom = faded(BORDER_BOTTOM, scaleAlpha(BORDER_BOTTOM, alpha));

        int left = x + 1;
        int right = x + width - 1;
        int up = y + 1;
        int down = y + height - 1;

        graphics.fill(left, y, right, up, background);
        graphics.fill(left, down, right, y + height, background);
        graphics.fill(x, up, x + width, down, background);

        graphics.fillGradient(left, up + 1, left + 1, down - 1, top, bottom);
        graphics.fillGradient(right - 1, up + 1, right, down - 1, top, bottom);
        graphics.fill(left, up, right, up + 1, top);
        graphics.fill(left, down - 1, right, down, bottom);
    }

    private static int faded(int color, int alpha) {
        int a = Math.max(alpha >>> 24, 4);
        return (color & 0x00FFFFFF) | (a << 24);
    }

    private static int scaleAlpha(int color, int alpha) {
        int own = (color >>> 24);
        int faded = (alpha >>> 24);

        return (Mth.clamp(own * faded / 255, 0, 255)) << 24;
    }

    private static Component label(FluidStack fluid, int capacity) {
        if (capacity <= 0) return Component.translatable("tooltip.packedup.fluid_gauge.unreadable");
        if (fluid.isEmpty()) return Component.translatable("tooltip.packedup.fluid_gauge.empty");
        Component name = fluid.getDisplayName().copy().withStyle(style -> style.withColor(GOLD & 0x00FFFFFF));

        return Component.translatable("tooltip.packedup.fluid_gauge.fluid", name);
    }

    private static ItemStack icon() {
        return new ItemStack(PURegistry.FLUID_GAUGE.get());
    }
}
