package ru.liner.decorative.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.list.BlockStainedGlassPane;

public class StainedGlassPaneRenderer implements ISimpleBlockRenderingHandler {
    Tessellator tessellator;

    @Override // cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (modelID == Decorative.STAINED_GLASS_PANE_RENDER_ID && block instanceof BlockStainedGlassPane) {
            renderer.renderBlockAsItem(block, metadata, 1f);
        }
    }

    @Override // cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
        if (modelID == Decorative.STAINED_GLASS_PANE_RENDER_ID && block instanceof BlockStainedGlassPane)
            return renderColoredGlassPane(world, x, y, z, (BlockStainedGlassPane) block, renderer);
        return false;
    }

    @Override // cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
    public boolean shouldRender3DInInventory() {
        return false;
    }

    @Override // cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
    public int getRenderId() {
        return Decorative.STAINED_GLASS_PANE_RENDER_ID;
    }

    @SideOnly(Side.CLIENT)
    private boolean renderColoredGlassPane(IBlockAccess world, int i, int j, int k, BlockStainedGlassPane blockPane, RenderBlocks renderer) {
        int l = renderer.blockAccess.getHeight();
        this.tessellator = Tessellator.instance;
        this.tessellator.setBrightness(blockPane.getMixedBrightnessForBlock(renderer.blockAccess, i, j, k));
        int n = blockPane.colorMultiplier(renderer.blockAccess, i, j, k);
        float f1 = ((n >> 16) & 255) / 255.0f;
        float f2 = ((n >> 8) & 255) / 255.0f;
        float f3 = (n & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float f4 = (((f1 * 30.0f) + (f2 * 59.0f)) + (f3 * 11.0f)) / 100.0f;
            float f5 = ((f1 * 30.0f) + (f2 * 70.0f)) / 100.0f;
            float f6 = ((f1 * 30.0f) + (f3 * 70.0f)) / 100.0f;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }
        this.tessellator.setColorOpaque_F(0.8f * f1, 0.8f * f2, 0.8f * f3);
        int metadata = renderer.blockAccess.getBlockMetadata(i, j, k);
        Icon icon = blockPane.getIcon(0, metadata);
        Icon icon1 = blockPane.getSideIcon(0, metadata);
        double u0 = icon.getMinU();
        double uM = icon.getInterpolatedU(8.0d);
        double u1 = icon.getMaxU();
        double v0 = icon.getMinV();
        double v1 = icon.getMaxV();
        double d5 = icon1.getInterpolatedU(7.0d);
        double d6 = icon1.getInterpolatedU(9.0d);
        double d7 = icon1.getMinV();
        double d8 = icon1.getInterpolatedV(8.0d);
        double d9 = icon1.getMaxV();
        double i0 = i;
        double iM = i + 0.5d;
        double i1 = i + 1;
        double j0 = j;
        double j1 = j + 1.0d;
        double k0 = k;
        double kM = k + 0.5d;
        double k1 = k + 1;
        double d16 = (i + 0.5d) - 0.0625d;
        double d17 = i + 0.5d + 0.0625d;
        double d18 = (k + 0.5d) - 0.0625d;
        double d19 = k + 0.5d + 0.0625d;
        boolean connectNorth = blockPane.canPaneConnectTo(renderer.blockAccess, i, j, k, ForgeDirection.NORTH);
        boolean connectSouth = blockPane.canPaneConnectTo(renderer.blockAccess, i, j, k, ForgeDirection.SOUTH);
        boolean connectWest = blockPane.canPaneConnectTo(renderer.blockAccess, i, j, k, ForgeDirection.WEST);
        boolean connectEast = blockPane.canPaneConnectTo(renderer.blockAccess, i, j, k, ForgeDirection.EAST);
        boolean connectAny = connectWest || connectEast || connectNorth || connectSouth;
        boolean connectUp = blockPane.shouldSideBeRendered(renderer.blockAccess, i, j + 1, k, 1);
        boolean connectDown = blockPane.shouldSideBeRendered(renderer.blockAccess, i, j - 1, k, 0);
        if ((!connectWest || !connectEast) && connectAny) {
            if (connectWest && !connectEast) {
                this.tessellator.addVertexWithUV(i0, j0, kM, uM, v1);
                this.tessellator.addVertexWithUV(i0, j1, kM, uM, v0);
                this.tessellator.addVertexWithUV(iM, j1, kM, u0, v0);
                this.tessellator.addVertexWithUV(iM, j0, kM, u0, v1);
                if (!connectSouth && !connectNorth) {
                    this.tessellator.addVertexWithUV(iM, j1, d19, d5, d7);
                    this.tessellator.addVertexWithUV(iM, j0, d19, d5, d9);
                    this.tessellator.addVertexWithUV(iM, j0, d18, d6, d9);
                    this.tessellator.addVertexWithUV(iM, j1, d18, d6, d7);
                }
                if (connectUp || (j < l - 1 && renderer.blockAccess.isAirBlock(i - 1, j + 1, k))) {
                    this.tessellator.addVertexWithUV(i0, j1 + 0.01d, d19, d6, d8);
                    this.tessellator.addVertexWithUV(iM, j1 + 0.01d, d19, d6, d9);
                    this.tessellator.addVertexWithUV(iM, j1 + 0.01d, d18, d5, d9);
                    this.tessellator.addVertexWithUV(i0, j1 + 0.01d, d18, d5, d8);
                }
                if (connectDown || (j > 1 && renderer.blockAccess.isAirBlock(i - 1, j - 1, k))) {
                    this.tessellator.addVertexWithUV(i0, j - 0.01d, d19, d6, d8);
                    this.tessellator.addVertexWithUV(iM, j - 0.01d, d19, d6, d9);
                    this.tessellator.addVertexWithUV(iM, j - 0.01d, d18, d5, d9);
                    this.tessellator.addVertexWithUV(i0, j - 0.01d, d18, d5, d8);
                }
            } else if (!connectWest && connectEast) {
                this.tessellator.addVertexWithUV(i1, j0, kM, uM, v1);
                this.tessellator.addVertexWithUV(i1, j1, kM, uM, v0);
                this.tessellator.addVertexWithUV(iM, j1, kM, u1, v0);
                this.tessellator.addVertexWithUV(iM, j0, kM, u1, v1);
                if (!connectSouth && !connectNorth) {
                    this.tessellator.addVertexWithUV(iM, j1, d18, d5, d7);
                    this.tessellator.addVertexWithUV(iM, j0, d18, d5, d9);
                    this.tessellator.addVertexWithUV(iM, j0, d19, d6, d9);
                    this.tessellator.addVertexWithUV(iM, j1, d19, d6, d7);
                }
                if (connectUp || (j < l - 1 && renderer.blockAccess.isAirBlock(i + 1, j + 1, k))) {
                    this.tessellator.addVertexWithUV(iM, j1 + 0.01d, d19, d6, d7);
                    this.tessellator.addVertexWithUV(i1, j1 + 0.01d, d19, d6, d8);
                    this.tessellator.addVertexWithUV(i1, j1 + 0.01d, d18, d5, d8);
                    this.tessellator.addVertexWithUV(iM, j1 + 0.01d, d18, d5, d7);
                }
                if (connectDown || (j > 1 && renderer.blockAccess.isAirBlock(i + 1, j - 1, k))) {
                    this.tessellator.addVertexWithUV(iM, j - 0.01d, d19, d6, d7);
                    this.tessellator.addVertexWithUV(i1, j - 0.01d, d19, d6, d8);
                    this.tessellator.addVertexWithUV(i1, j - 0.01d, d18, d5, d8);
                    this.tessellator.addVertexWithUV(iM, j - 0.01d, d18, d5, d7);
                }
            }
        } else {
            this.tessellator.addVertexWithUV(i0, j1, kM, u1, v0);
            this.tessellator.addVertexWithUV(i0, j0, kM, u1, v1);
            this.tessellator.addVertexWithUV(i1, j0, kM, u0, v1);
            this.tessellator.addVertexWithUV(i1, j1, kM, u0, v0);
            if (connectUp) {
                this.tessellator.addVertexWithUV(i0, j1 + 0.01d, d19, d6, d9);
                this.tessellator.addVertexWithUV(i1, j1 + 0.01d, d19, d6, d7);
                this.tessellator.addVertexWithUV(i1, j1 + 0.01d, d18, d5, d7);
                this.tessellator.addVertexWithUV(i0, j1 + 0.01d, d18, d5, d9);
            } else {
                if (j < l - 1 && renderer.blockAccess.isAirBlock(i - 1, j + 1, k)) {
                    this.tessellator.addVertexWithUV(i0, j1 + 0.01d, d19, d6, d8);
                    this.tessellator.addVertexWithUV(iM, j1 + 0.01d, d19, d6, d9);
                    this.tessellator.addVertexWithUV(iM, j1 + 0.01d, d18, d5, d9);
                    this.tessellator.addVertexWithUV(i0, j1 + 0.01d, d18, d5, d8);
                }
                if (j < l - 1 && renderer.blockAccess.isAirBlock(i + 1, j + 1, k)) {
                    this.tessellator.addVertexWithUV(iM, j1 + 0.01d, d19, d6, d7);
                    this.tessellator.addVertexWithUV(i1, j1 + 0.01d, d19, d6, d8);
                    this.tessellator.addVertexWithUV(i1, j1 + 0.01d, d18, d5, d8);
                    this.tessellator.addVertexWithUV(iM, j1 + 0.01d, d18, d5, d7);
                }
            }
            if (connectDown) {
                this.tessellator.addVertexWithUV(i0, j - 0.01d, d19, d6, d9);
                this.tessellator.addVertexWithUV(i1, j - 0.01d, d19, d6, d7);
                this.tessellator.addVertexWithUV(i1, j - 0.01d, d18, d5, d7);
                this.tessellator.addVertexWithUV(i0, j - 0.01d, d18, d5, d9);
            } else {
                if (j > 1 && renderer.blockAccess.isAirBlock(i - 1, j - 1, k)) {
                    this.tessellator.addVertexWithUV(i0, j - 0.01d, d19, d6, d8);
                    this.tessellator.addVertexWithUV(iM, j - 0.01d, d19, d6, d9);
                    this.tessellator.addVertexWithUV(iM, j - 0.01d, d18, d5, d9);
                    this.tessellator.addVertexWithUV(i0, j - 0.01d, d18, d5, d8);
                }
                if (j > 1 && renderer.blockAccess.isAirBlock(i + 1, j - 1, k)) {
                    this.tessellator.addVertexWithUV(iM, j - 0.01d, d19, d6, d7);
                    this.tessellator.addVertexWithUV(i1, j - 0.01d, d19, d6, d8);
                    this.tessellator.addVertexWithUV(i1, j - 0.01d, d18, d5, d8);
                    this.tessellator.addVertexWithUV(iM, j - 0.01d, d18, d5, d7);
                }
            }
        }
        if ((!connectNorth || !connectSouth) && connectAny) {
            if (connectNorth && !connectSouth) {
                this.tessellator.addVertexWithUV(iM, j1, kM, u0, v0);
                this.tessellator.addVertexWithUV(iM, j0, kM, u0, v1);
                this.tessellator.addVertexWithUV(iM, j0, k0, uM, v1);
                this.tessellator.addVertexWithUV(iM, j1, k0, uM, v0);
                if (!connectEast && !connectWest) {
                    this.tessellator.addVertexWithUV(d16, j1, kM, d5, d7);
                    this.tessellator.addVertexWithUV(d16, j0, kM, d5, d9);
                    this.tessellator.addVertexWithUV(d17, j0, kM, d6, d9);
                    this.tessellator.addVertexWithUV(d17, j1, kM, d6, d7);
                }
                if (connectUp || (j < l - 1 && renderer.blockAccess.isAirBlock(i, j + 1, k - 1))) {
                    this.tessellator.addVertexWithUV(d16, j1 + 0.005d, k0, d6, d7);
                    this.tessellator.addVertexWithUV(d16, j1 + 0.005d, kM, d6, d8);
                    this.tessellator.addVertexWithUV(d17, j1 + 0.005d, kM, d5, d8);
                    this.tessellator.addVertexWithUV(d17, j1 + 0.005d, k0, d5, d7);
                }
                if (connectDown || (j > 1 && renderer.blockAccess.isAirBlock(i, j - 1, k - 1))) {
                    this.tessellator.addVertexWithUV(d16, j - 0.005d, k0, d6, d7);
                    this.tessellator.addVertexWithUV(d16, j - 0.005d, kM, d6, d8);
                    this.tessellator.addVertexWithUV(d17, j - 0.005d, kM, d5, d8);
                    this.tessellator.addVertexWithUV(d17, j - 0.005d, k0, d5, d7);
                    return true;
                }
                return true;
            }
            if (!connectNorth && connectSouth) {
                this.tessellator.addVertexWithUV(iM, j1, k1, uM, v0);
                this.tessellator.addVertexWithUV(iM, j0, k1, uM, v1);
                this.tessellator.addVertexWithUV(iM, j0, kM, u1, v1);
                this.tessellator.addVertexWithUV(iM, j1, kM, u1, v0);
                if (!connectEast && !connectWest) {
                    this.tessellator.addVertexWithUV(d16, j1, kM, d5, d7);
                    this.tessellator.addVertexWithUV(d16, j0, kM, d5, d9);
                    this.tessellator.addVertexWithUV(d17, j0, kM, d6, d9);
                    this.tessellator.addVertexWithUV(d17, j1, kM, d6, d7);
                }
                if (connectUp || (j < l - 1 && renderer.blockAccess.isAirBlock(i, j + 1, k + 1))) {
                    this.tessellator.addVertexWithUV(d16, j1 + 0.005d, k1, d5, d8);
                    this.tessellator.addVertexWithUV(d16, j1 + 0.005d, kM, d5, d9);
                    this.tessellator.addVertexWithUV(d17, j1 + 0.005d, kM, d6, d9);
                    this.tessellator.addVertexWithUV(d17, j1 + 0.005d, k1, d6, d8);
                }
                if (connectDown || (j > 1 && renderer.blockAccess.isAirBlock(i, j - 1, k + 1))) {
                    this.tessellator.addVertexWithUV(d16, j - 0.005d, k1, d5, d8);
                    this.tessellator.addVertexWithUV(d16, j - 0.005d, kM, d5, d9);
                    this.tessellator.addVertexWithUV(d17, j - 0.005d, kM, d6, d9);
                    this.tessellator.addVertexWithUV(d17, j - 0.005d, k1, d6, d8);
                    return true;
                }
                return true;
            }
            return true;
        }
        this.tessellator.addVertexWithUV(iM, j1, k1, u0, v0);
        this.tessellator.addVertexWithUV(iM, j0, k1, u0, v1);
        this.tessellator.addVertexWithUV(iM, j0, k0, u1, v1);
        this.tessellator.addVertexWithUV(iM, j1, k0, u1, v0);
        if (connectUp) {
            this.tessellator.addVertexWithUV(d17, j1 + 0.005d, k1, d6, d9);
            this.tessellator.addVertexWithUV(d17, j1 + 0.005d, k0, d6, d7);
            this.tessellator.addVertexWithUV(d16, j1 + 0.005d, k0, d5, d7);
            this.tessellator.addVertexWithUV(d16, j1 + 0.005d, k1, d5, d9);
        } else {
            if (j < l - 1 && renderer.blockAccess.isAirBlock(i, j + 1, k - 1)) {
                this.tessellator.addVertexWithUV(d16, j1 + 0.005d, k0, d6, d7);
                this.tessellator.addVertexWithUV(d16, j1 + 0.005d, kM, d6, d8);
                this.tessellator.addVertexWithUV(d17, j1 + 0.005d, kM, d5, d8);
                this.tessellator.addVertexWithUV(d17, j1 + 0.005d, k0, d5, d7);
            }
            if (j < l - 1 && renderer.blockAccess.isAirBlock(i, j + 1, k + 1)) {
                this.tessellator.addVertexWithUV(d16, j1 + 0.005d, kM, d5, d8);
                this.tessellator.addVertexWithUV(d16, j1 + 0.005d, k1, d5, d9);
                this.tessellator.addVertexWithUV(d17, j1 + 0.005d, k1, d6, d9);
                this.tessellator.addVertexWithUV(d17, j1 + 0.005d, kM, d6, d8);
            }
        }
        if (connectDown) {
            this.tessellator.addVertexWithUV(d17, j - 0.005d, k1, d6, d9);
            this.tessellator.addVertexWithUV(d17, j - 0.005d, k0, d6, d7);
            this.tessellator.addVertexWithUV(d16, j - 0.005d, k0, d5, d7);
            this.tessellator.addVertexWithUV(d16, j - 0.005d, k1, d5, d9);
            return true;
        }
        if (j > 1 && renderer.blockAccess.isAirBlock(i, j - 1, k - 1)) {
            this.tessellator.addVertexWithUV(d16, j - 0.005d, k0, d6, d7);
            this.tessellator.addVertexWithUV(d16, j - 0.005d, kM, d6, d8);
            this.tessellator.addVertexWithUV(d17, j - 0.005d, kM, d5, d8);
            this.tessellator.addVertexWithUV(d17, j - 0.005d, k0, d5, d7);
        }
        if (j > 1 && renderer.blockAccess.isAirBlock(i, j - 1, k + 1)) {
            this.tessellator.addVertexWithUV(d16, j - 0.005d, kM, d5, d8);
            this.tessellator.addVertexWithUV(d16, j - 0.005d, k1, d5, d9);
            this.tessellator.addVertexWithUV(d17, j - 0.005d, k1, d6, d9);
            this.tessellator.addVertexWithUV(d17, j - 0.005d, kM, d6, d8);
            return true;
        }
        return true;
    }
}