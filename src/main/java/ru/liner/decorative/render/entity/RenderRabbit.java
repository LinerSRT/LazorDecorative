package ru.liner.decorative.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import ru.liner.decorative.render.models.Models;

public class RenderRabbit extends RenderLiving {
    public RenderRabbit() {
        super(Models.rabbit, 0.3f);
    }

    @Override
    public void doRenderLiving(EntityLiving entityLiving, double par2, double par4, double par6, float par8, float par9) {
        Minecraft.getMinecraft().renderEngine.bindTexture(entityLiving.texture);
        super.doRenderLiving(entityLiving, par2, par4, par6, par8, par9);
    }
}
