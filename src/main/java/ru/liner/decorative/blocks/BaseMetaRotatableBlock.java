package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseMetaRotatableBlock extends BaseMetaBlock {
    @SideOnly(Side.CLIENT)
    protected String sideTexture;
    @SideOnly(Side.CLIENT)
    protected String topTexture;
    @SideOnly(Side.CLIENT)
    protected Icon[] textures;

    public BaseMetaRotatableBlock(int itemId, Material material) {
        super(itemId, material);
    }

    public BaseMetaRotatableBlock setSideTexture(String sideTexture) {
        this.sideTexture = sideTexture;
        return this;
    }

    public BaseMetaRotatableBlock setTopTexture(String topTexture) {
        this.topTexture = topTexture;
        return this;
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        int orientation = metadata & 12;
        if (orientation == 0 && (side == 1 || side == 0))
            return textures[1];
        if (orientation == 4 && (side == 5 || side == 4))
            return textures[1];
        if (orientation == 8 && (side == 2 || side == 3))
            return textures[1];
        return textures[0];
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        int finalMetadata;
        switch (side) {
            case 2:
            case 3:
                finalMetadata = (metadata & 3) | 8;
                break;
            case 4:
            case 5:
                finalMetadata = (metadata & 3) | 4;
                break;
            default:
                finalMetadata = (metadata & 3);
        }
        world.setBlockMetadataWithNotify(x, y, z, finalMetadata, 2);
        return finalMetadata;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register) {
        textures = new Icon[2];
        textures[0] = register.registerIcon(sideTexture);
        textures[1] = register.registerIcon(topTexture);
    }


    public int metaExcludeOrientation(int metadata) {
        return metadata & 3;
    }
    @Override
    public int damageDropped(int metadata) {
        return metaExcludeOrientation(metadata);
    }
}
