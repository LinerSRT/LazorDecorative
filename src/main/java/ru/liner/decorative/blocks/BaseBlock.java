package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BaseBlock extends Block {
    protected String textureName;

    public BaseBlock(int itemId, Material material) {
        super(itemId, material);
    }

    public BaseBlock setTextureName(String textureName) {
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
