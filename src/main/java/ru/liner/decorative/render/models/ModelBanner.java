package ru.liner.decorative.render.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderEngine;

public class ModelBanner extends ModelBase {
    private final RenderEngine renderEngine;
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

    public void bindSlateTexture(String texture) {
        renderEngine.bindTexture(String.format("/textures/entity/banner/%s.png", texture));
    }
}
