package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.liner.decorative.Decorative;

import java.util.Random;

public class BlockLog extends BaseMultiBlock{
    public BlockLog(int blockId) {
        super(blockId, Material.wood, Type.LOG);
        registerType("acacia", "Акация");
        registerType("big_oak", "Большой дуб");
        setRotatedPillar(true);
        setCreativeTab(CreativeTabs.tabBlock);
        setHardness(2f);
        setStepSound(Block.soundWoodFootstep);
        setTextureName("new_log");
        setUnlocalizedName("log");
    }


    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        byte b0 = 4;
        int j1 = b0 + 1;
        if (par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1)) {
            for (int k1 = -b0; k1 <= b0; ++k1) {
                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        int j2 = par1World.getBlockId(par2 + k1, par3 + l1, par4 + i2);
                        if (Block.blocksList[j2] != null) {
                            Block.blocksList[j2].beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isWood(World world, int x, int y, int z) {
        return true;
    }

    @Override
    protected ItemStack createStackedBlock(int metadata) {
        return new ItemStack(blockID, 1, metaExcludeOrientation(metadata));
    }

    public int damageDropped(int par1) {
        return metaExcludeOrientation(par1);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public int idDropped(int par1, Random par2Random, int par3) {
        return Decorative.planksBlock.blockID;
    }
}
