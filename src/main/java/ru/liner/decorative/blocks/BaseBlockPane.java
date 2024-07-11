package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BaseBlockPane extends BlockPane {
    protected String textureName;
    public BaseBlockPane(int blockId, String blockTexture, String blockSideTexture, Material material, boolean par5) {
        super(blockId, blockTexture, blockSideTexture, material, par5);
    }

    public BaseBlockPane setTextureName(String textureName) {
        this.textureName = textureName;
        return this;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register) {
        if(textureName != null) {
            blockIcon = register.registerIcon(textureName);
        } else {
            super.registerIcons(register);
        }
    }
}
