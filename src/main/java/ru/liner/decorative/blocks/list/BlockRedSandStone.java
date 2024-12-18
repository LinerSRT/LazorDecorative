package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import ru.liner.decorative.blocks.FamilarityType;
import ru.liner.decorative.blocks.IBlockFamily;

public class BlockRedSandStone extends BaseMultiMetaBlock implements IBlockFamily {
    public BlockRedSandStone(int itemId) {
        super(itemId, Material.rock);
        setHardness(1.5f);
        setResistance(10f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setBaseLocalizedName("Призмарин");
        setUnlocalizedName("sandstone");
        setTextureParent("red_sandstone");
        registerType("sandstone_smooth", "Гладкий красный песчаник",  "smooth");
        registerType("sandstone_chiseled", "Резной красный песчаник",  "carved");
        registerType("sandstone_normal", "Красный песчаник",  "normal");
        registerTexture("sandstone_chiseled", "smooth", 0, 1);
        registerTexture("sandstone_smooth", "smooth", 0, 1);
    }

    @Override
    public FamilarityType[] getFamiliarityWith() {
        return new FamilarityType[]{
                FamilarityType.SLAB,
                FamilarityType.STAIR
        };
    }
}
