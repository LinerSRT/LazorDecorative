package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

public class BlockPrismarine extends BaseMultiMetaBlock {
    public BlockPrismarine(int itemId) {
        super(itemId, Material.rock);
        setHardness(1.5f);
        setResistance(10f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setBaseLocalizedName("Призмарин");
        setUnlocalizedName("prismarine");
        setTextureParent("prismarine");
        registerType("prismarine", "Призмарин", "rough");
        registerType("prismarine_dark", "Темный призмарин",  "dark");
        registerType("prismarine_bricks", "Кирпичи из призмарина", "bricks");
    }
}