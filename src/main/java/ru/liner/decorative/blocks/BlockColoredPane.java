package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import ru.liner.decorative.materials.Materials;

import java.util.List;

@SuppressWarnings("unchecked")
public class BlockColoredPane extends BaseBlock {
    @SideOnly(Side.CLIENT)
    private Icon[] iconList;
    @SideOnly(Side.CLIENT)
    private Icon[] sidedTextures;

    public BlockColoredPane(int blockId) {
        this(blockId, Materials.materialColored);
    }

    public BlockColoredPane(int blockId, Material material) {
        super(blockId, material);
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        return iconList[metadata % iconList.length];
    }

    public Icon getSideIcon(int side, int metadata) {
        return sidedTextures[metadata % sidedTextures.length];
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
        this.sidedTextures = new Icon[16];
        for (int i = 0; i < iconList.length; i++) {
            iconList[i] = register.registerIcon(String.format("%s_%s", textureName, ItemDye.dyeColorNames[getDyeFromBlock(i)]));
            sidedTextures[i] = register.registerIcon(String.format("%s_pane_top_%s", textureName, ItemDye.dyeColorNames[getDyeFromBlock(i)]));
        }
    }
    public final boolean canThisPaneConnectToThisBlockID(int par1) {
        return Block.opaqueCubeLookup[par1] || par1 == this.blockID || par1 == Block.glass.blockID;
    }
    public boolean canPaneConnectTo(IBlockAccess access, int x, int y, int z, ForgeDirection dir) {
        return this.canThisPaneConnectToThisBlockID(access.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) || access.isBlockSolidOnSide(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite(), false);
    }
}
