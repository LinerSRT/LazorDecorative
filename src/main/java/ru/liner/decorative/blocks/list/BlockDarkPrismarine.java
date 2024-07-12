package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMetaBlock;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

public class BlockDarkPrismarine extends BaseMultiMetaBlock {
    public BlockDarkPrismarine(int itemId) {
        super(itemId, Material.rock);
        setHardness(1.5f);
        setResistance(10f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setBaseLocalizedName("Призмарин");
        setUnlocalizedName("prismarine");
        setTextureParent("prismarine");
        registerType("prismarine_dark", "Призмарин", 2, "dark");
        registerType("prismarine_bricks", "Кирпичи из призмарина", 2, "bricks");
    }
}
