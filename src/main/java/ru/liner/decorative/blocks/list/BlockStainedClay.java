package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemDye;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

public class BlockStainedClay extends BaseMultiMetaBlock {
    public BlockStainedClay(int blockID) {
        super(blockID, Material.cloth);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(1.25f);
        setResistance(7f);
        setStepSound(Block.soundStoneFootstep);
        setUnlocalizedName("clay.stained");
        setBaseLocalizedName("Обожженая глина");
        setTextureParent("hardened_clay_stained");
        for (int i = 0; i < ItemDye.dyeColorNames.length; i++) {
            registerType(ItemDye.dyeColorNames[i], String.format("%s %s", Decorative.colorNames[i + ItemDye.dyeColorNames.length], getBaseLocalizedName().toLowerCase()),  ItemDye.dyeColorNames[i]);
        }
    }
}
