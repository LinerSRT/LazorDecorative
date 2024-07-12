package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BaseMetaBlock extends Block {
    @SideOnly(Side.CLIENT)
    protected String textureName;
    @SideOnly(Side.CLIENT)
    protected String baseLocalizedName;

    public BaseMetaBlock(int itemId, Material material) {
        super(itemId, material);
    }

    public BaseMetaBlock setTextureName(String textureName) {
        this.textureName = textureName;
        return this;
    }

    public BaseMetaBlock setBaseLocalizedName(String baseLocalizedName) {
        this.baseLocalizedName = baseLocalizedName;
        return this;
    }

    public String getBaseLocalizedName() {
        return baseLocalizedName;
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
