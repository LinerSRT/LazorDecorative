package ru.liner.decorative.render.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.Entity;

public class ModelBed extends ModelBase {
    public final RenderEngine renderEngine = Minecraft.getMinecraft().renderEngine;
    public final ModelRenderer head;
    public final ModelRenderer foot;

    public ModelBed() {
        textureWidth = 128;
        textureHeight = 64;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 4.0F, -3.0F);
        head.cubeList.add(new ModelBox(head, 48, 0, 5.0F, 2.0F, 24.0F, 3, 3, 3, 0.0F));
        head.cubeList.add(new ModelBox(head, 0, 0, -8.0F, -4.0F, 11.0F, 16, 16, 16, 0.0F));
        head.cubeList.add(new ModelBox(head, 48, 0, -8.0F, 2.0F, 24.0F, 3, 3, 3, 0.0F));

        foot = new ModelRenderer(this);
        foot.setRotationPoint(0.0F, 4.0F, -19.0F);
        foot.cubeList.add(new ModelBox(foot, 48, 0, 5.0F, 2.0F, 11.0F, 3, 3, 3, 0.0F));
        foot.cubeList.add(new ModelBox(foot, 64, 0, -8.0F, -4.0F, 11.0F, 16, 16, 16, 0.0F));
        foot.cubeList.add(new ModelBox(foot, 48, 0, -8.0F, 2.0F, 11.0F, 3, 3, 3, 0.0F));
    }




    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        head.render(f5);
        foot.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}