package ru.liner.decorative.render.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderEngine;
import org.lwjgl.opengl.GL11;
import ru.liner.decorative.tile.TileEntityBanner;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL14.glBlendEquation;

public class ModelBanner extends ModelBase {
    private RenderEngine renderEngine;
    public ModelRenderer bannerSlate;
    public ModelRenderer bannerStand;
    public ModelRenderer bannerTop;

    public ModelBanner() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bannerSlate = new ModelRenderer(this, 0, 0);
        this.bannerSlate.addBox(-10.0f, 0.0f, -2.0f, 20, 40, 1, 0.0f);
        this.bannerStand = new ModelRenderer(this, 44, 0);
        this.bannerStand.addBox(-1.0f, -30.0f, -1.0f, 2, 42, 2, 0.0f);
        this.bannerTop = new ModelRenderer(this, 0, 42);
        this.bannerTop.addBox(-10.0f, -32.0f, -1.0f, 20, 2, 2, 0.0f);
        this.renderEngine = Minecraft.getMinecraft().renderEngine;
    }

    public void renderBanner() {
        this.bannerSlate.rotationPointY = -32.0f;
        this.bannerSlate.render(0.0625f);
        this.bannerStand.render(0.0625f);
        this.bannerTop.render(0.0625f);
    }

    public void renderBanner(int color, TileEntityBanner.PatternType pattern) {
        GL11.glPushMatrix();

        bannerSlate.rotationPointY = -32.0f;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, 1);
        renderEngine.bindTexture("/textures/blocks/banner_base.png");
        bannerSlate.render(0.0625f);


        glEnable(GL_BLEND);
        glBlendFunc(GL_DST_COLOR, GL_DST_COLOR);
        glBlendEquation(GL_FUNC_ADD);
        renderEngine.bindTexture(String.format("/textures/entity/banner/%s.png", pattern.getPatternName()));
        bannerSlate.render(0.0625f);
        GL11.glDisable(GL11.GL_BLEND);


        GL11.glColor4f(1, 1, 1, 1);
        renderEngine.bindTexture("/textures/blocks/banner_base.png");
        bannerStand.render(0.0625f);
        bannerTop.render(0.0625f);

        GL11.glPopMatrix();
    }


}
