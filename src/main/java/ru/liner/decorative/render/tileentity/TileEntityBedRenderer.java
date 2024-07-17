package ru.liner.decorative.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemDye;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import ru.liner.decorative.render.models.Models;
import ru.liner.decorative.tile.TileEntityBed;

public class TileEntityBedRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        TileEntityBed bed = (TileEntityBed) tileEntity;
        String texture = String.format("bed_%s", ItemDye.dyeColorNames[bed.getColor()]);
        //TODO Provides glitchy rendering when two near beds placed cross by cross :(
        renderHeadPart(texture, x, y, z, bed.getRenderingRotation());
        renderFootPart(texture, x, y, z, bed.getRenderingRotation());
    }


    private void renderHeadPart(String texture, double x, double y, double z, float rotation) {
        GL11.glPushMatrix();
        GL11.glTranslatef(((float) x) + 0.5f, ((float) y) + (0.55f), ((float) z) + 0.5f);
        GL11.glRotatef(180, 1, 0, 1);
        GL11.glRotatef(90 - rotation, 0, 1, 0);
        bindBedTexture(texture);
        Models.bed.head.render(0.0625f);
        GL11.glPopMatrix();
    }

    private void renderFootPart(String texture, double x, double y, double z, float rotation) {
        GL11.glPushMatrix();
        GL11.glTranslatef(((float) x) + 0.5f, ((float) y) + (0.55f), ((float) z) + 0.5f);
        GL11.glRotatef(180, 1, 0, 1);
        GL11.glRotatef(90 - rotation, 0, 1, 0);
        bindBedTexture(texture);
        Models.bed.foot.render(0.0625f);
        GL11.glPopMatrix();
    }

    private void bindBedTexture(String texture) {
        Models.bed.renderEngine.bindTexture(String.format("/textures/entity/bed/%s.png", texture));
    }
}
