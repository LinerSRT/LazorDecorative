package ru.liner.decorative.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import ru.liner.decorative.render.models.Models;

public class BannerItemRenderer implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType itemRenderType) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType itemRenderType, ItemStack itemStack, ItemRendererHelper itemRendererHelper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType itemRenderType, ItemStack itemStack, Object... objects) {
        int bannerColor = ItemDye.dyeColors[itemStack.getItemDamage() & 15];
        int alpha = (bannerColor >> 24) & 0xFF;
        int red = (bannerColor >> 16) & 0xFF;
        int green = (bannerColor >> 8) & 0xFF;
        int blue = bannerColor & 0xFF;
        if (itemRenderType == ItemRenderType.INVENTORY || itemRenderType == ItemRenderType.ENTITY) {
            GL11.glPushMatrix();
            Tessellator tessellator = Tessellator.instance;
            float scale = 1.6f;
            GL11.glTranslatef(-(scale - .25f) / 2.0f, -(scale - .2f) / 2.0f, 0.0f);
            GL11.glScalef(scale, scale, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture("/textures/items/banner_base.png");
            renderItemIn2D(tessellator, 1.0f, 0.0f, 0.0f, 1.0f, 16, 16, 0.0625f);
            GL11.glColor4f(red / 255f, green / 255f, blue / 255f, 1);
            Minecraft.getMinecraft().renderEngine.bindTexture("/textures/items/banner_overlay.png");
            renderItemIn2D(tessellator, 1.0f, 0.0f, 0.0f, 1.0f, 16, 16, 0.0625f);
            GL11.glPopMatrix();
        } else if (itemRenderType == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glPushMatrix();
            Minecraft.getMinecraft().renderEngine.bindTexture("/textures/blocks/banner_base.png");
            GL11.glRotatef(180f, 0f, 0f, 1f);
            GL11.glRotatef(-225f, 0f, 1f, 0f);
            GL11.glTranslatef(0f, -0.6f, -0.7f);
            GL11.glScalef(0.7f, 0.7f, 0.7f);
            GL11.glColor4f(red / 255f, green / 255f, blue / 255f, 1);
            Models.banner.bannerSlate.rotationPointY = -32.0f;
            Models.banner.bannerSlate.render(0.0625f);
            GL11.glColor4f(1,1,1,1);
            Models.banner.bannerStand.render(0.0625f);
            Models.banner.bannerTop.render(0.0625f);
            GL11.glPopMatrix();
        } else {
            GL11.glPushMatrix();
            Minecraft.getMinecraft().renderEngine.bindTexture("/textures/blocks/banner_base.png");
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(-.6f, -1f, 0.7f);
            GL11.glRotatef(30, 1, 0, 0);
            GL11.glColor4f(red / 255f, green / 255f, blue / 255f, 1);
            Models.banner.bannerSlate.rotationPointY = -32.0f;
            Models.banner.bannerSlate.render(0.0625f);
            GL11.glColor4f(1,1,1,1);
            Models.banner.bannerStand.render(0.0625f);
            Models.banner.bannerTop.render(0.0625f);
            GL11.glPopMatrix();
        }
    }

    private static void renderItemIn2D(Tessellator tessellator, float uMin, float vMin, float uMax, float vMax, int width, int height, float f) {
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, uMin, vMax);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, uMax, vMax);
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, uMax, vMin);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, uMin, vMin);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0f - f, uMin, vMin);
        tessellator.addVertexWithUV(1.0, 1.0, 0.0f - f, uMax, vMin);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0f - f, uMax, vMax);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0f - f, uMin, vMax);
        tessellator.draw();
        float f5 = width * (uMin - uMax);
        float f6 = height * (vMax - vMin);
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);

        int k;
        float f7;
        float f8;
        for (k = 0; k < f5; ++k) {
            f7 = k / f5;
            f8 = uMin + (uMax - uMin) * f7 - 0.5f / width;
            tessellator.addVertexWithUV(f7, 0.0, 0.0f - f, f8, vMax);
            tessellator.addVertexWithUV(f7, 0.0, 0.0, f8, vMax);
            tessellator.addVertexWithUV(f7, 1.0, 0.0, f8, vMin);
            tessellator.addVertexWithUV(f7, 1.0, 0.0f - f, f8, vMin);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0f, 0.0f, 0.0f);

        float f9;
        for (k = 0; k < f5; ++k) {
            f7 = k / f5;
            f8 = uMin + (uMax - uMin) * f7 - 0.5f / width;
            f9 = f7 + 1.0f / f5;
            tessellator.addVertexWithUV(f9, 1.0, 0.0f - f, f8, vMin);
            tessellator.addVertexWithUV(f9, 1.0, 0.0, f8, vMin);
            tessellator.addVertexWithUV(f9, 0.0, 0.0, f8, vMax);
            tessellator.addVertexWithUV(f9, 0.0, 0.0f - f, f8, vMax);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 1.0f, 0.0f);

        for (k = 0; k < f6; ++k) {
            f7 = k / f6;
            f8 = vMax + (vMin - vMax) * f7 - 0.5F / height;
            f9 = f7 + 1.0F / f6;
            tessellator.addVertexWithUV(0.0, f9, 0.0, uMin, f8);
            tessellator.addVertexWithUV(1.0, f9, 0.0, uMax, f8);
            tessellator.addVertexWithUV(1.0, f9, 0.0F - f, uMax, f8);
            tessellator.addVertexWithUV(0.0, f9, 0.0F - f, uMin, f8);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for (k = 0; k < f6; ++k) {
            f7 = k / f6;
            f8 = vMax + (vMin - vMax) * f7 - 0.5F / height;
            tessellator.addVertexWithUV(1.0, f7, 0.0, uMax, f8);
            tessellator.addVertexWithUV(0.0, f7, 0.0, uMin, f8);
            tessellator.addVertexWithUV(0.0, f7, 0.0F - f, uMin, f8);
            tessellator.addVertexWithUV(1.0, f7, 0.0F - f, uMax, f8);
        }

        tessellator.draw();
    }
}
