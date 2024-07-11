package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

@SuppressWarnings("unchecked")
public class BlockPlanks extends BaseMultiBlock {
    public BlockPlanks(int blockId) {
        super(blockId, Material.wood, Type.PLANKS);
        setHardness(2.0F);
        setResistance(5.0F);
        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(Block.soundWoodFootstep);
        setUnlocalizedName("newPlanks");
        setTextureName("planks");
        registerType("acacia", "из акации");
        registerType("big_oak", "из большого дуба");
    }

    @Override
    public boolean isWood(World world, int x, int y, int z) {
        return true;
    }
}
