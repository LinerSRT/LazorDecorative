package ru.liner.decorative.render.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import ru.liner.decorative.enity.passive.EntityRabbit;
import ru.liner.decorative.utils.GlStateManager;

public class ModelRabbit extends ModelBase {

    
    ModelRenderer rabbitLeftFoot;

    
    ModelRenderer rabbitRightFoot;

    
    ModelRenderer rabbitLeftThigh;

    
    ModelRenderer rabbitRightThigh;

    
    ModelRenderer rabbitBody;

    
    ModelRenderer rabbitLeftArm;

    
    ModelRenderer rabbitRightArm;

    
    ModelRenderer rabbitHead;

    
    ModelRenderer rabbitRightEar;

    
    ModelRenderer rabbitLeftEar;

    
    ModelRenderer rabbitTail;

    
    ModelRenderer rabbitNose;

    
    private float field_178701_m = 0.0f;

    
    private float field_178699_n = 0.0f;

    public ModelRabbit() {
        setTextureOffset("head.main", 0, 0);
        setTextureOffset("head.nose", 0, 24);
        setTextureOffset("head.ear1", 0, 10);
        setTextureOffset("head.ear2", 6, 10);
        this.rabbitLeftFoot = new ModelRenderer(this, 26, 24);
        this.rabbitLeftFoot.addBox(-1.0f, 5.5f, -3.7f, 2, 1, 7);
        this.rabbitLeftFoot.setRotationPoint(3.0f, 17.5f, 3.7f);
        this.rabbitLeftFoot.mirror = true;
        setRotationOffset(this.rabbitLeftFoot, 0.0f, 0.0f, 0.0f);
        this.rabbitRightFoot = new ModelRenderer(this, 8, 24);
        this.rabbitRightFoot.addBox(-1.0f, 5.5f, -3.7f, 2, 1, 7);
        this.rabbitRightFoot.setRotationPoint(-3.0f, 17.5f, 3.7f);
        this.rabbitRightFoot.mirror = true;
        setRotationOffset(this.rabbitRightFoot, 0.0f, 0.0f, 0.0f);
        this.rabbitLeftThigh = new ModelRenderer(this, 30, 15);
        this.rabbitLeftThigh.addBox(-1.0f, 0.0f, 0.0f, 2, 4, 5);
        this.rabbitLeftThigh.setRotationPoint(3.0f, 17.5f, 3.7f);
        this.rabbitLeftThigh.mirror = true;
        setRotationOffset(this.rabbitLeftThigh, -0.34906584f, 0.0f, 0.0f);
        this.rabbitRightThigh = new ModelRenderer(this, 16, 15);
        this.rabbitRightThigh.addBox(-1.0f, 0.0f, 0.0f, 2, 4, 5);
        this.rabbitRightThigh.setRotationPoint(-3.0f, 17.5f, 3.7f);
        this.rabbitRightThigh.mirror = true;
        setRotationOffset(this.rabbitRightThigh, -0.34906584f, 0.0f, 0.0f);
        this.rabbitBody = new ModelRenderer(this, 0, 0);
        this.rabbitBody.addBox(-3.0f, -2.0f, -10.0f, 6, 5, 10);
        this.rabbitBody.setRotationPoint(0.0f, 19.0f, 8.0f);
        this.rabbitBody.mirror = true;
        setRotationOffset(this.rabbitBody, -0.34906584f, 0.0f, 0.0f);
        this.rabbitLeftArm = new ModelRenderer(this, 8, 15);
        this.rabbitLeftArm.addBox(-1.0f, 0.0f, -1.0f, 2, 7, 2);
        this.rabbitLeftArm.setRotationPoint(3.0f, 17.0f, -1.0f);
        this.rabbitLeftArm.mirror = true;
        setRotationOffset(this.rabbitLeftArm, -0.17453292f, 0.0f, 0.0f);
        this.rabbitRightArm = new ModelRenderer(this, 0, 15);
        this.rabbitRightArm.addBox(-1.0f, 0.0f, -1.0f, 2, 7, 2);
        this.rabbitRightArm.setRotationPoint(-3.0f, 17.0f, -1.0f);
        this.rabbitRightArm.mirror = true;
        setRotationOffset(this.rabbitRightArm, -0.17453292f, 0.0f, 0.0f);
        this.rabbitHead = new ModelRenderer(this, 32, 0);
        this.rabbitHead.addBox(-2.5f, -4.0f, -5.0f, 5, 4, 5);
        this.rabbitHead.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitHead.mirror = true;
        setRotationOffset(this.rabbitHead, 0.0f, 0.0f, 0.0f);
        this.rabbitRightEar = new ModelRenderer(this, 52, 0);
        this.rabbitRightEar.addBox(-2.5f, -9.0f, -1.0f, 2, 5, 1);
        this.rabbitRightEar.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitRightEar.mirror = true;
        setRotationOffset(this.rabbitRightEar, 0.0f, -0.2617994f, 0.0f);
        this.rabbitLeftEar = new ModelRenderer(this, 58, 0);
        this.rabbitLeftEar.addBox(0.5f, -9.0f, -1.0f, 2, 5, 1);
        this.rabbitLeftEar.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitLeftEar.mirror = true;
        setRotationOffset(this.rabbitLeftEar, 0.0f, 0.2617994f, 0.0f);
        this.rabbitTail = new ModelRenderer(this, 52, 6);
        this.rabbitTail.addBox(-1.5f, -1.5f, 0.0f, 3, 3, 2);
        this.rabbitTail.setRotationPoint(0.0f, 20.0f, 7.0f);
        this.rabbitTail.mirror = true;
        setRotationOffset(this.rabbitTail, -0.3490659f, 0.0f, 0.0f);
        this.rabbitNose = new ModelRenderer(this, 32, 9);
        this.rabbitNose.addBox(-0.5f, -2.5f, -5.5f, 1, 1, 1);
        this.rabbitNose.setRotationPoint(0.0f, 16.0f, -1.0f);
        this.rabbitNose.mirror = true;
        setRotationOffset(this.rabbitNose, 0.0f, 0.0f, 0.0f);
    }

    
    private void setRotationOffset(ModelRenderer modelRenderer, float f, float f2, float f3) {
        modelRenderer.rotateAngleX = f;
        modelRenderer.rotateAngleY = f2;
        modelRenderer.rotateAngleZ = f3;
    }

    @Override 
    
    public void render(Entity entity, float f, float f2, float f3, float f4, float f5, float f6) {
        setRotationAngles(f, f2, f3, f4, f5, f6, entity);
        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.85f / 1.5f, 0.85f / 1.5f, 0.85f / 1.5f);
            GlStateManager.translate(0.0f, 22.0f * f6, 2.0f * f6);
            this.rabbitHead.render(f6);
            this.rabbitLeftEar.render(f6);
            this.rabbitRightEar.render(f6);
            this.rabbitNose.render(f6);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.6f / 1.5f, 0.6f / 1.5f, 0.6f / 1.5f);
            GlStateManager.translate(0.0f, 36.0f * f6, 0.0f);
            this.rabbitLeftFoot.render(f6);
            this.rabbitRightFoot.render(f6);
            this.rabbitLeftThigh.render(f6);
            this.rabbitRightThigh.render(f6);
            this.rabbitBody.render(f6);
            this.rabbitLeftArm.render(f6);
            this.rabbitRightArm.render(f6);
            this.rabbitTail.render(f6);
            GlStateManager.popMatrix();
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.6f, 0.6f, 0.6f);
        GlStateManager.translate(0.0f, 16.0f * f6, 0.0f);
        this.rabbitLeftFoot.render(f6);
        this.rabbitRightFoot.render(f6);
        this.rabbitLeftThigh.render(f6);
        this.rabbitRightThigh.render(f6);
        this.rabbitBody.render(f6);
        this.rabbitLeftArm.render(f6);
        this.rabbitRightArm.render(f6);
        this.rabbitHead.render(f6);
        this.rabbitRightEar.render(f6);
        this.rabbitLeftEar.render(f6);
        this.rabbitTail.render(f6);
        this.rabbitNose.render(f6);
        GlStateManager.popMatrix();
    }

    @Override 
    
    public void setRotationAngles(float f, float f2, float f3, float f4, float f5, float f6, Entity entity) {
        ModelRenderer modelRenderer = this.rabbitNose;
        ModelRenderer modelRenderer2 = this.rabbitHead;
        ModelRenderer modelRenderer3 = this.rabbitRightEar;
        float f7 = f5 * 0.017453292f;
        this.rabbitLeftEar.rotateAngleX = f7;
        modelRenderer3.rotateAngleX = f7;
        modelRenderer2.rotateAngleX = f7;
        modelRenderer.rotateAngleX = f7;
        ModelRenderer modelRenderer4 = this.rabbitNose;
        float f8 = f4 * 0.017453292f;
        this.rabbitHead.rotateAngleY = f8;
        modelRenderer4.rotateAngleY = f8;
        this.rabbitRightEar.rotateAngleY = this.rabbitNose.rotateAngleY - 0.2617994f;
        this.rabbitLeftEar.rotateAngleY = this.rabbitNose.rotateAngleY + 0.2617994f;
        this.field_178701_m = MathHelper.sin(((EntityRabbit) entity).getJumpCompletion(f3 - entity.ticksExisted) * 3.1415927f);
        ModelRenderer modelRenderer5 = this.rabbitLeftThigh;
        ModelRenderer modelRenderer6 = this.rabbitRightThigh;
        float f9 = ((this.field_178701_m * 50.0f) - 21.0f) * 0.017453292f;
        modelRenderer6.rotateAngleX = f9;
        modelRenderer5.rotateAngleX = f9;
        ModelRenderer modelRenderer7 = this.rabbitLeftFoot;
        ModelRenderer modelRenderer8 = this.rabbitRightFoot;
        float f10 = this.field_178701_m * 50.0f * 0.017453292f;
        modelRenderer8.rotateAngleX = f10;
        modelRenderer7.rotateAngleX = f10;
        ModelRenderer modelRenderer9 = this.rabbitLeftArm;
        ModelRenderer modelRenderer10 = this.rabbitRightArm;
        float f11 = ((this.field_178701_m * (-40.0f)) - 11.0f) * 0.017453292f;
        modelRenderer10.rotateAngleX = f11;
        modelRenderer9.rotateAngleX = f11;
    }

    @Override 
    
    public void setLivingAnimations(EntityLiving entityLivingBase, float f, float f2, float f3) {
        super.setLivingAnimations(entityLivingBase, f, f2, f3);
        this.field_178701_m = MathHelper.sin(((EntityRabbit) entityLivingBase).getJumpCompletion(f3) * 3.1415927f);
    }
}