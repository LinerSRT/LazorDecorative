package ru.liner.decorative.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemDye;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import ru.liner.decorative.render.models.Models;
import ru.liner.decorative.tile.TileEntityBanner;

public class BannerBlockRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        TileEntityBanner tileEntityBanner = (TileEntityBanner) tileEntity;
        int rotation = tileEntityBanner.getRotation();
        boolean isStandBanner = tileEntityBanner.isStandBanner();
        if (isStandBanner) {
            GL11.glTranslatef(((float) x) + 0.5f, ((float) y) + (0.75f * 0.6666667f), ((float) z) + 0.5f);
            GL11.glRotatef(-((rotation* 360) / 16.0f) + 180, 0.0f, 1.0f, 0.0f);
            Models.banner.bannerStand.showModel = true;
        } else {
            float angle = 0.0f;
            if (rotation == 2) {
                angle = 180.0f;
            }
            if (rotation == 1) {
                angle = 90.0f;
            }
            if (rotation == 3) {
                angle = -90.0f;
            }
            GL11.glTranslatef(((float) x) + 0.5f, ((float) y) - (0.25f * 0.6666667f), ((float) z) + 0.5f);
            GL11.glRotatef(-angle, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.3125f, -0.4375f);
            Models.banner.bannerStand.showModel = false;
        }
        float randomness = (float) (((x + y + z) % 100) / 2);
        Models.banner.bannerSlate.rotateAngleX = (-0.0125f + (0.01f * MathHelper.cos(tileEntity.getWorldObj().getWorldTime() * 0.1f + randomness))) * 3.1415927f;

        GL11.glPushMatrix();
        GL11.glScalef(0.6666667f, -0.6666667f, -0.6666667f);
        Models.banner.renderBanner(ItemDye.dyeColors[tileEntityBanner.getBlockMetadata() & 15], tileEntityBanner.getPatternType());
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
