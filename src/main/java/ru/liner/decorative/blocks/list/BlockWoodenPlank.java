package ru.liner.decorative.blocks.list;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

public class BlockWoodenPlank extends BaseMultiMetaBlock {
    public BlockWoodenPlank(int blockID) {
        super(blockID, Material.rock);
        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(BlockWoodenPlank.soundWoodFootstep);
        setHardness(2f);
        setResistance(5f);
        setBaseLocalizedName("Доски");
        setTextureParent("planks");
        setUnlocalizedName("planks");
        registerType("acacia", "Доски из акации",  "acacia");
        registerType("big_oak", "Доски из темного дуба",  "big_oak");
    }

    @Override
    public boolean isWood(World world, int x, int y, int z) {
        return true;
    }
}
