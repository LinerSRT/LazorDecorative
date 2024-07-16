package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import ru.liner.decorative.blocks.BlockFamilyTypes;
import ru.liner.decorative.blocks.IBlockFamily;

public class BlockPurpur extends BaseMultiMetaBlock implements IBlockFamily {
    public BlockPurpur(int itemId) {
        super(itemId, Material.rock);
        setHardness(1.5f);
        setResistance(10f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setBaseLocalizedName("Пурпурный блок");
        setUnlocalizedName("purpur");
        setTextureParent("purpur");
        registerType("purpur_block", "Пурпур-блок", "block");
        registerType("purpur_pillar", "Пурпуровый пилон", "pillar");
        registerTexture("purpur_pillar", "pillar_top", 0, 1);
    }

    @Override
    public BlockFamilyTypes[] getFamiliarityWith() {
        return new BlockFamilyTypes[]{
                BlockFamilyTypes.STAIR,
                BlockFamilyTypes.SLAB
        };
    }
}
