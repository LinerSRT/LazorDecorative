package ru.liner.decorative.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import ru.liner.decorative.render.models.Models;
import ru.liner.decorative.tile.TileEntityBanner;
import ru.liner.decorative.utils.Pair;

import static org.lwjgl.opengl.GL11.*;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        TileEntityBanner banner = (TileEntityBanner) tileEntity;
        GL11.glPushMatrix();
        GL11.glTranslatef(((float) x) + 0.5f, ((float) y) + (0.75f * 0.6666667f), ((float) z) + 0.5f);
        GL11.glRotatef(banner.getRenderingRotation(), 0.0f, 1.0f, 0.0f);
        if (banner.isStandBanner()) {
            Models.banner.bannerStand.showModel = true;
        } else {
            GL11.glTranslatef(0.0f, -0.3125f, -0.4375f);
            Models.banner.bannerStand.showModel = false;
        }
        Models.banner.bannerSlate.rotateAngleX = (-0.0125f + (0.01f * MathHelper.cos(tileEntity.getWorldObj().getWorldTime() * 0.1f + ((float) (((x + y + z) % 100) / 2.5f))))) * 3.1415927f;
        GL11.glScalef(0.6666667f, -0.6666667f, -0.6666667f);
        Models.banner.bannerSlate.rotationPointY = -32.0f;
        bindTextureByName("/textures/blocks/banner_base.png");
        GL11.glColor4f(1, 1, 1, 1);
        Models.banner.bannerStand.render(0.0625f);
        Models.banner.bannerTop.render(0.0625f);
        for (Pair<TileEntityBanner.PatternType, Integer> patternData : banner.getPatternList()) {
            bindBannerSlateTexture(patternData.key.getPatternName());
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            int bannerColor = banner.getDyeColor(patternData.value);
            GL11.glColor4f(
                    (bannerColor >> 16 & 255) / 255.0f,
                    (bannerColor >> 8 & 255) / 255.0f,
                    (bannerColor & 255) / 255.0f,
                    1f
            );
            Models.banner.bannerSlate.render(0.0625f);
            glDisable(GL_BLEND);
        }

        GL11.glPopMatrix();
    }

    private void bindBannerSlateTexture(String texture){
        Models.banner.bindSlateTexture(texture);
    }
}
