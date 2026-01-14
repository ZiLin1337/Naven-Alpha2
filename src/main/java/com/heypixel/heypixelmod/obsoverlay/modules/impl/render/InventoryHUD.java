package com.heypixel.heypixelmod.obsoverlay.modules.impl.render;

import com.heypixel.heypixelmod.obsoverlay.events.api.EventTarget;
import com.heypixel.heypixelmod.obsoverlay.events.impl.EventRenderSkia;
import com.heypixel.heypixelmod.obsoverlay.modules.Category;
import com.heypixel.heypixelmod.obsoverlay.modules.Module;
import com.heypixel.heypixelmod.obsoverlay.modules.ModuleInfo;
import com.heypixel.heypixelmod.obsoverlay.ui.HUDEditor;
import com.heypixel.heypixelmod.obsoverlay.utils.skia.Skia;
import com.heypixel.heypixelmod.obsoverlay.utils.skia.font.Fonts;
import com.heypixel.heypixelmod.obsoverlay.values.ValueBuilder;
import com.heypixel.heypixelmod.obsoverlay.values.impl.DragValue;
import com.heypixel.heypixelmod.obsoverlay.values.impl.FloatValue;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Shader;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

@ModuleInfo(name = "InventoryHUD", cnName = "背包显示", description = "显示你背包中的物品", category = Category.RENDER)
public class InventoryHUD extends Module {

    private final DragValue dragValue = ValueBuilder.create(this, "Position")
            .setDefaultX(200f)
            .setDefaultY(100f)
            .build()
            .getDragValue();

    public FloatValue fontSize = ValueBuilder.create(this, "Font Size")
            .setDefaultFloatValue(18.0F) // 稍微调小默认字体以适配紧凑布局
            .setMinFloatValue(10.0F)
            .setMaxFloatValue(40.0F)
            .setFloatStep(0.5F)
            .build()
            .getFloatValue();

    @EventTarget
    public void onRenderSkia(EventRenderSkia event) {
        if (mc.screen instanceof HUDEditor) {
            // 编辑模式逻辑
            return;
        }

        float x = dragValue.getX();
        float y = dragValue.getY();

        float width = 170.0f;
        float height = 73.0f;
        float currentFontSize = fontSize.getCurrentValue();

        // 调整圆角大小 (原为 6.0f)
        float radius = 3.0f;

        Font font = Fonts.getMiSans(currentFontSize);

        // --- 颜色定义 ---
        // 蓝色 (起始色 & 字体色)
        Color startColor = new Color(65, 165, 255);
        // 粉色 (结束色) - 类似截图中的粉色
        Color endColor = new Color(255, 110, 210);

        try (Paint layerPaint = new Paint()) {
            layerPaint.setAlpha(255);
            Skia.getCanvas().saveLayer(Rect.makeXYWH(x - 10, y - 10, width + 20, height + 20), layerPaint);

            // 1. 背景绘制 (使用新的 radius)
            Skia.drawShadow(x, y, width, height, radius);
            Skia.drawRoundedBlur(x, y, width, height, radius);
            Skia.drawRoundedRect(x, y, width, height, radius, new Color(0, 0, 0, 50));

            // 2. 顶部渐变条 (蓝色 -> 粉色)
            try (Paint gradientPaint = new Paint()) {
                gradientPaint.setShader(Shader.makeLinearGradient(
                        x, y,
                        x + width, y,
                        new int[]{startColor.getRGB(), endColor.getRGB()},
                        null
                ));
                // 绘制渐变条，高度 3px，圆角与背景一致
                Skia.getCanvas().drawRRect(RRect.makeXYWH(x, y, width, 3.0f, radius), gradientPaint);
            }

            // 3. 标题文字
            Skia.drawText("Inventory", x + 6.0f, y + 14.0f, startColor, font);

            Skia.restore();
        }

        // 4. 物品绘制 (GuiGraphics)
        if (mc.player != null) {
            GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());

            float itemStartX = x + 6.0f;
            // 物品起始 Y 坐标，保持在标题下方
            float itemStartY = y + 20.0f;

            renderInvRow(guiGraphics, 9, 17, itemStartX, itemStartY);
            renderInvRow(guiGraphics, 18, 26, itemStartX, itemStartY + 18);
            renderInvRow(guiGraphics, 27, 35, itemStartX, itemStartY + 36);

            mc.renderBuffers().bufferSource().endBatch();
        }

        dragValue.setWidth(width);
        dragValue.setHeight(height);
    }

    private void renderInvRow(GuiGraphics guiGraphics, int startSlot, int endSlot, float startX, float y) {
        float currentX = startX;
        for (int i = startSlot; i <= endSlot; i++) {
            if (i < mc.player.getInventory().items.size()) {
                ItemStack stack = mc.player.getInventory().items.get(i);
                if (!stack.isEmpty()) {
                    drawItem(guiGraphics, stack, currentX, y);
                }
            }
            currentX += 18.0f;
        }
    }

    private void drawItem(GuiGraphics guiGraphics, ItemStack stack, float x, float y) {
        guiGraphics.pose().pushPose();
        try {
            guiGraphics.renderItem(stack, (int) x, (int) y);
            guiGraphics.renderItemDecorations(mc.font, stack, (int) x, (int) y);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            guiGraphics.pose().popPose();
        }
    }
}
