package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import ru.liner.decorative.materials.Materials;

import java.util.List;

@SuppressWarnings("unchecked")
public class BlockColored extends BaseBlock {
    @SideOnly(Side.CLIENT)
    private Icon[] iconList;

    public BlockColored(int blockId) {
        super(blockId, Materials.materialColored);
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        return iconList[metadata % iconList.length];

    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    public static int getBlockFromDye(int metadata) {
        return ~metadata & 15;
    }

    public static int getDyeFromBlock(int metadata) {
        return ~metadata & 15;
    }

    @Override
    public void getSubBlocks(int itemId, CreativeTabs creativeTabs, List itemList) {
        for (int i = 0; i < 16; i++)
            itemList.add(new ItemStack(itemId, 1, i));
    }

    @Override
    public void registerIcons(IconRegister register) {
        this.iconList = new Icon[16];
        for (int i = 0; i < iconList.length; i++) {
            iconList[i] = register.registerIcon(textureName + "_" + ItemDye.dyeColorNames[getDyeFromBlock(i)]);
        }
    }
}
